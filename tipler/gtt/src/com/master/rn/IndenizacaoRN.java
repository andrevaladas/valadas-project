/*
 * Created on 11/06/2009
 *
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.BandaBD;
import com.master.bd.IndenizacaoBD;
import com.master.bd.Movimento_Conta_CorrenteBD;
import com.master.bd.Parametro_GttBD;
import com.master.bd.Percentual_ReposicaoBD;
import com.master.bd.Recapagem_GarantidaBD;
import com.master.ed.BandaED;
import com.master.ed.EmpresaED;
import com.master.ed.Faturamento_ConcessionariaED;
import com.master.ed.IndenizacaoED;
import com.master.ed.Movimento_Conta_CorrenteED;
import com.master.ed.Parametro_GttED;
import com.master.ed.Percentual_ReposicaoED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.ed.UsuarioED;
import com.master.util.Data;
import com.master.util.DataHora;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial Recapagens Garantidas
 * @serialData 06/2009
 */
public class IndenizacaoRN extends Transacao {

	public IndenizacaoRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public IndenizacaoRN(ExecutaSQL executasql) {
		super(executasql);
	}
	public IndenizacaoED inclui (IndenizacaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			String msg=null;
			// Complementa com data, hora
			ed.setDt_Indenizacao(DataHora.getDataDMY());
			ed.setHr_Indenizacao(DataHora.getHoraHM());
			// Inclui a indenização
			IndenizacaoED toReturn = new IndenizacaoBD (this.sql).inclui (ed);
			toReturn = new IndenizacaoBD(this.sql).getByRecord(toReturn);
			if ( !Utilitaria.doValida(toReturn.getDm_Rejeicao()) ) { // Se não foi rejeitado vai gravar o crédito na conta corrente
				msg=inclui_Movimento_CC(toReturn);
			}	
			fimTransacao (true);
			toReturn.setTx_Aprovacao_Motivo(msg);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	/**
	 * Inclui movimento na conta corrente
	 * return retorna msg relativa ao bloqueio se houver
	 * @param toReturn
	 * @throws Excecoes
	 */
	private String inclui_Movimento_CC(IndenizacaoED toReturn) throws Excecoes {
		String msg=null;
		String dmBloqueio=null;
		// Inclui na conta corrente um credito no valor calculado para a indenização
		// Busca o último faturamento da concessionaria
		Faturamento_ConcessionariaED fcED = new Faturamento_ConcessionariaED();
		fcED.setOid_Concessionaria(toReturn.getOid_Empresa());
		fcED = new Faturamento_ConcessionariaRN(this.sql).getUltimo(fcED);
		
		// Busca o somatória dos creditos em aberto da concessionaria
		Movimento_Conta_CorrenteED somaMccED = new Movimento_Conta_CorrenteED();
		somaMccED.setOid_Concessionaria(toReturn.getOid_Empresa());
		//somaMccED.setDm_Debito_Credito("C");
		//somaMccED.setDm_Descontado("N");
		somaMccED.setVl_Saldo(new Movimento_Conta_CorrenteRN(this.sql).getSaldoHistorico(somaMccED));
		// Verifica se os creditos ainda em aberto + o valor da indenização entrante é superior a o último faturamente registrado.
		double vlSaldo = somaMccED.getVl_Saldo()+toReturn.getVl_Indenizacao();
		double vlLimite=fcED.getVl_Faturamento()*0.02;
		if ((vlSaldo) > (vlLimite)){
			dmBloqueio="S";
			msg="Saldo de créditos (" + FormataValor.formataValorBT(vlSaldo, 2) + ") superou o limite estipulado. (" + FormataValor.formataValorBT(vlLimite, 2) +")"; 
		} else {
			dmBloqueio="N";
		}
		Movimento_Conta_CorrenteED mccED = new Movimento_Conta_CorrenteED();
		mccED.setTx_Bloqueio(msg);
		mccED.setDm_Bloqueado(dmBloqueio);
		mccED.setDm_Tipo_Movimento("I");
		mccED.setOid_Indenizacao(toReturn.getOid_Indenizacao());
		mccED.setOid_Concessionaria(toReturn.getOid_Empresa());
		mccED.setDt_Movimento_Conta_Corrente(toReturn.getDt_Indenizacao());
		mccED.setDm_Debito_Credito("C");
		mccED.setTx_Descricao("REF PNEU " + toReturn.getNr_Fogo() + " NF " + toReturn.getNr_Nota_Fiscal() + " DT " + toReturn.getDt_Registro() );
		mccED.setVl_Movimento_Conta_Corrente(toReturn.getVl_Indenizacao());
		// Inclui em movimento contacorente
		mccED = new Movimento_Conta_CorrenteRN(this.sql).inclui(mccED);
		return msg;
	}
	
	public void altera (IndenizacaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			// Altera a indenização
			new IndenizacaoBD (this.sql).altera (ed);
			// Se não aprovado cria um débito na CC e atualiza o oid do debito no registrodo crédito
			if ("N".equals( ed.getDm_Aprovacao()) ) {
				Movimento_Conta_CorrenteBD mccBD = new Movimento_Conta_CorrenteBD(this.sql); 
				IndenizacaoED iED = new IndenizacaoBD(this.sql).getByRecord(ed);

				// Pega o lancamento de crédito da conta corrente dessa indenização
				Movimento_Conta_CorrenteED mccCED = new Movimento_Conta_CorrenteED();
				mccCED.setOid_Indenizacao(ed.getOid_Indenizacao());
				mccCED = mccBD.getByRecord(mccCED);
				
				//Coloca um débito na conta corrente da concessionaria.
				Movimento_Conta_CorrenteED mccDED = new Movimento_Conta_CorrenteED();
				mccDED.setOid_Indenizacao(ed.getOid_Indenizacao());
				mccDED.setOid_Concessionaria(iED.getOid_Empresa());
				mccDED.setVl_Movimento_Conta_Corrente(iED.getVl_Indenizacao());
				mccDED.setDt_Movimento_Conta_Corrente(ed.getDt_Aprovacao());
				mccDED.setDm_Debito_Credito("D");
				mccDED.setDm_Tipo_Movimento("N");
				mccDED.setDm_Bloqueado("N");
				mccDED.setTx_Descricao("REF PNEU " + iED.getNr_Fogo() +" NF " + iED.getNr_Nota_Fiscal() +" IND.NÃO APROVADA. DT IND. " + iED.getDt_Indenizacao());
				mccDED = mccBD.inclui(mccDED);
				
				// Atualiza o oid do débito criado acima no lancamento de crédito original.
				mccCED.setOid_Movimento_Conta_Corrente_Debito(mccDED.getOid_Movimento_Conta_Corrente());
				mccBD.alteraOidDebito(mccCED); 
			}
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (IndenizacaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new IndenizacaoBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<IndenizacaoED> lista (IndenizacaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new IndenizacaoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public IndenizacaoED getByRecord (IndenizacaoED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new IndenizacaoBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	// Calcula indenização para a recapagem garantida.
	public IndenizacaoED calcula_Indenizacao (IndenizacaoED ed ) throws Excecoes, ParseException {
		inicioTransacao ();
		IndenizacaoED edVolta = new IndenizacaoED();
		edVolta.setDm_Rejeicao(null);
		try {
			// Verifica se há dano não coberto
			if (this.testa_Dano_Nao_Coberto(ed)) {
				edVolta.setDm_Rejeicao("NC"); // Houve dano não coberto
			} else {	
				double perc_Indenizacao=0;
				//Pega o parametro para cálculo
				Parametro_GttED par = new Parametro_GttED();
				par.setOid_Parametro_Gtt(1);
				par = new Parametro_GttBD(this.sql).getByRecord(par);
				// Pega a recapagem garantida
				Recapagem_GarantidaED rec = new Recapagem_GarantidaED();
				rec.setOid_Recapagem_Garantida(ed.getOid_Recapagem_Garantida());
				rec = new Recapagem_GarantidaBD(this.sql).getByRecord(rec);
				edVolta.setNr_Dot(rec.getNr_Dot());
				// Verifica se expirou a garantia pelo tempo do DOT
				if ("S".equals(new Recapagem_GarantidaRN(this.sql).getExpirou_Garantia(rec)) ) {
					edVolta.setDm_Rejeicao("ED"); // Dot expirou
				} else {
					// Perda total
					if ("true".equals(ed.getDm_Perda_Total())) {
						// Calcula por tempo ( perda total )
						int dif = DataHora.diferencaDias(rec.getDt_Registro(), DataHora.getDataDMY());
						if (dif >= 0 && dif <= 30)
							perc_Indenizacao = par.getNr_Perc_Perda_0_30();
						else if (dif >= 31 && dif <= 60)
							perc_Indenizacao = par.getNr_Perc_Perda_31_60();
						else if (dif >= 61 && dif <= 90)
							perc_Indenizacao = par.getNr_Perc_Perda_61_90();
						else if (dif >= 91 && dif <= 120)
							perc_Indenizacao = par.getNr_Perc_Perda_91_120();
						// Monta o ed de retorno
						edVolta.setDt_Registro(rec.getDt_Registro());
						edVolta.setDt_Indenizacao(DataHora.getDataDMY());
						edVolta.setNr_Dias_Perda_Total(dif);
						edVolta.setNr_Perc_Perda_Total(perc_Indenizacao);
						edVolta.setVl_Indenizacao(perc_Indenizacao * rec.getVl_Servico() / 100 );
						if (edVolta.getVl_Indenizacao()==0) { 
							edVolta.setDm_Rejeicao("ET"); // Se não teve indenização foi expirado por tempo na perda total
						}
					} else { // Calcula por desgaste
						// Pega a banda para usar a profundidade
						BandaED bnd = new BandaED();
						bnd.setOid_Banda(rec.getOid_Banda());
						bnd = new BandaBD(this.sql).getByRecord(bnd);
						// Calcula o desgate sofrido
						double desgaste=((1-((ed.getNr_MM()-par.getNr_Twi())/(bnd.getNr_Profundidade()-par.getNr_Twi())))*100) ;
						edVolta.setNr_Perc_Desgaste(desgaste);
						// Pega Percentual de reposição referente ao desgaste
						Percentual_ReposicaoED per = new Percentual_ReposicaoED();
						per.setNr_Perc_Busca(desgaste);
						per = new Percentual_ReposicaoBD(this.sql).getByRecord(per);
						// Calcula a indenização
						perc_Indenizacao=per.getNr_Perc_Reforma();
						edVolta.setNr_Perc_Reforma(perc_Indenizacao);
						if (rec.getNr_Vida()==1)
							edVolta.setNr_Perc_Carcaca(per.getNr_Perc_Carcaca_Vida_1());
						else if (rec.getNr_Vida()==2)
							edVolta.setNr_Perc_Carcaca(per.getNr_Perc_Carcaca_Vida_2());
						else if (rec.getNr_Vida()==3)
							edVolta.setNr_Perc_Carcaca(per.getNr_Perc_Carcaca_Vida_3());
						//Soma as duas coisas reforma + carcaca
						perc_Indenizacao+=edVolta.getNr_Perc_Carcaca();
						edVolta.setNr_Perc_Perda_Total(perc_Indenizacao);
						edVolta.setVl_Indenizacao(perc_Indenizacao * rec.getVl_Servico() / 100 );
						if (edVolta.getVl_Indenizacao()==0) { 
							edVolta.setDm_Rejeicao("DE"); // Se não teve indenização então foi desgaste excessivo
						}	
					}
				}	
			}	
			if (edVolta.getVl_Indenizacao()==0)
				edVolta.setDm_Aceita_Por_Parametro("N");
			else
				edVolta.setDm_Aceita_Por_Parametro("S");

			return edVolta;
		}
		finally {
			fimTransacao (false);
		}
	}
	
	public boolean testa_Dano_Nao_Coberto (IndenizacaoED ed) {
		boolean testa_Dano=false;
		if ("true".equals(ed.getDm_Baixa_Alta_Pressao())) testa_Dano=true;
		if ("true".equals(ed.getDm_Dano_Acidente())) testa_Dano=true;
		if ("true".equals(ed.getDm_Excesso_Carga())) testa_Dano=true;
		if ("true".equals(ed.getDm_Fora_Aplicacao_Recomendada())) testa_Dano=true;
		if ("true".equals(ed.getDm_Mau_Estado())) testa_Dano=true;
		if ("true".equals(ed.getDm_Montagem_Desmontagem_Inadequada())) testa_Dano=true;
		if ("true".equals(ed.getDm_Substancia_Quimica())) testa_Dano=true;
		if ("true".equals(ed.getDm_Tubless_Com_Camara())) testa_Dano=true;
		return testa_Dano;
	}
	public void relatorioCessao(IndenizacaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			// Busca os dados da concessionaria indenizadora que pode não ser a mesma que recapou o pneu
			EmpresaED conIndenizadora = new EmpresaED();
			conIndenizadora.setOid_Empresa(ed.getOid_Empresa());
			conIndenizadora = new EmpresaRN(this.sql).getByRecord(conIndenizadora);
			// Busca a indenização para pegar o oid_Recapagem_Garantida
			IndenizacaoED indenizacaoED = new IndenizacaoED();
			indenizacaoED = this.getByRecord(ed);
			// Busca a recapagem garantida
			Recapagem_GarantidaED rgED = new Recapagem_GarantidaED();
			rgED.setOid_Recapagem_Garantida( indenizacaoED.getOid_Recapagem_Garantida() );
			ArrayList<Recapagem_GarantidaED> lista = new Recapagem_GarantidaRN(this.sql).lista(rgED);
			Recapagem_GarantidaED edVolta = new Recapagem_GarantidaED();
			edVolta = (Recapagem_GarantidaED) lista.get(0);
			String txt="Declaramos que, para qualquer fim, de fato e de direito, os produtos declarados " +
					   "neste documento são de minha propriedade e/ou responsabilidade e que não estão envolvidos " +
					   "em qualquer evento ou foi causa de qualquer acidente que tenha causado danos de natureza " +
					   "pessoal ou patrimonial, para qualquer parte, a mim ou a terceiros. Estou ciente de " +
					   "que serei comunicado do resultado da análise do produto e que, se por ventura o produto " +
					   "não seja aceito, terei o prazo de 60 ( sessenta dias ) para a retirada do mesmo. " +
					   "Após o término deste prazo o produto poderá ser destruido ou destinado a outro qualquer fim.";

			edVolta.setTx_Indenizacao(txt);
			edVolta.setNm_Cidade(conIndenizadora.getNm_Cidade());
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt102C"); // Seta o nome do relatório
			
    		HashMap<String, String> map  = new HashMap<String, String>();
    		map.put("vl_Indenizacao", FormataValor.formataValorBT(indenizacaoED.getVl_Indenizacao(),2));
    		ed.setHashMap(map);
    		
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioRejeicao(IndenizacaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			String tx_Motivo=null;
			this.inicioTransacao();
			Recapagem_GarantidaED rgED = new Recapagem_GarantidaED();
			// Busca a indenizacao para pegar o oid_Recapagem_Garantida
			IndenizacaoED inED = this.getByRecord(ed);
			rgED.setOid_Recapagem_Garantida( inED.getOid_Recapagem_Garantida() );
			
			if ("ED".equals(inED.getDm_Rejeicao())){ // Expirado por DOT
				tx_Motivo = "Expirou o prazo de validade da garantia. ";
			} else
			if ("DE".equals(inED.getDm_Rejeicao())){ // Desgaste excessivo
				tx_Motivo = "Houve desgaste excessivo. Profundidade medida = " +FormataValor.formataValorBT(inED.getNr_MM(),1)  + " MM.";
			}
			if ("ET".equals(inED.getDm_Rejeicao())){ // Expirou o tempo para Perda total
				tx_Motivo = "Expirou o tempo para perda total. 120 dias a contar da data da recapagem.";
			}
			if ("NC".equals(inED.getDm_Rejeicao())){ // Dano não coberto
				tx_Motivo = "Dano não coberto. ";
				String txt=null;
				if ("true".equals(inED.getDm_Baixa_Alta_Pressao()))
					txt="Existem sinais de emprego com baixa ou alta pressão";
				if ("true".equals(inED.getDm_Dano_Acidente()))
						txt="Existem sinais de presença de danos acidentais.";
				if ("true".equals(inED.getDm_Excesso_Carga()))
						txt="Existem sinais de emprego de excesso de carga";
				if ("true".equals(inED.getDm_Fora_Aplicacao_Recomendada()))
						txt="Existem sinais de uso fora da aplicação recomendada";
				if ("true".equals(inED.getDm_Mau_Estado()))
						txt="Existem sinais de uso de câmaras de ar, protetores, rodas e aros em mau estado.";
				if ("true".equals(inED.getDm_Montagem_Desmontagem_Inadequada()))
						txt="Existem danos oriundos de montagem ou desmontagem inadequadas.";
				if ("true".equals(inED.getDm_Substancia_Quimica()))
					txt="Existem sinais de danos por substâncias quimicas.";
				if ("true".equals(inED.getDm_Tubless_Com_Camara()))
					txt="O pneu é tipo 'tubless' e veio com câmara de ar.";
				tx_Motivo+=" "+txt;
			}
			ed.setDescFiltro(tx_Motivo);
			ArrayList<Recapagem_GarantidaED> lista = new Recapagem_GarantidaRN(this.sql).lista(rgED);
			Recapagem_GarantidaED rgEDx = (Recapagem_GarantidaED) lista.get(0);
			rgEDx.setTx_Laudo(inED.getTx_Laudo());	
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt102R"); // Seta o nome do relatório
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioInspecao(IndenizacaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro=null;
			if (ed.getOid_Empresa_Gambiarra()>0)
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			else
				ed.setOid_Empresa(0);
			//  Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNm_Razao_Social()))
				nm_Filtro=" Concessionária=" + ed.getNm_Razao_Social();			
			ArrayList<IndenizacaoED> lista = this.lista(ed);
			ed.setLista(lista); // Joga a lista no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt302"); // Seta o nome do relatório
			
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}
	public void relatorioInspecaoRealizada(IndenizacaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro="";
			if (ed.getOid_Empresa_Gambiarra()>0)
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			else
				ed.setOid_Empresa(0);
			//  Monta a descricao do filtro utilizado
			if (!"ESCAPACABECALHO".equals(ed.getNm_Razao_Social())) {
				if (Utilitaria.doValida(ed.getNm_Razao_Social()))
					nm_Filtro =" Concessionária=" + ed.getNm_Razao_Social();
				else
					nm_Filtro =" Concessionária=TODOS" ;
				if (ed.getOid_Usuario_Tecnico()>0)
					nm_Filtro+=" Técnico="+ed.getOid_Usuario_Tecnico();
				else
					nm_Filtro+=" Técnico=TODOS";
			}
			if ("T".equals(ed.getDm_Aprovacao()))
				nm_Filtro+=" Situação aprovação=TODOS";
			else 
				nm_Filtro+=" Situação aprovação="+("S".equals(ed.getDm_Aprovacao())?"SIM":"NÃO");
			nm_Filtro+=" Período de="+ed.getDt_Aprovacao_Inicial();
			nm_Filtro+=" até="+ed.getDt_Aprovacao_Final();
			ArrayList<IndenizacaoED> lista = this.lista(ed);
			ed.setLista(lista); // Joga a lista no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt303"); // Seta o nome do relatório
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioIndenizacoesMes(IndenizacaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			ed.setDt_Indenizacao_Inicial("01/"+ed.getDm_Mes_Ano());
			ed.setDt_Indenizacao_Final(Data.getUltimoDiaDoMes(ed.getDt_Indenizacao_Inicial()));
			ed.setDm_Ordenacao("RC");
			ed.setOid_Empresa(0);
			String nm_Filtro="";
			if (Utilitaria.doValida(ed.getNm_Regional()))
				nm_Filtro+="Regional="+ed.getNm_Regional() + " - ";
			else
				nm_Filtro+="Regional=TODOS - ";	
			nm_Filtro+="Mes"+ed.getDm_Mes_Ano();
			ArrayList<IndenizacaoED> lista = this.lista(ed);
			ed.setLista(lista); // Joga a lista no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt319"); // Seta o nome do relatório
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	
	/**
	 * processaRL
	 * Processa solicitação de relatorio OL retornando sempre um PDF.
	 * @param rel = Qual o relatorio a ser chamado
	 * @param Obj = Um bean populado com parametros para a geracao do relatorio
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws Excecoes
	 */
	public void processaRL(String rel, Object Obj, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException, Excecoes {
		//Extrai o bean com os campos da request colados
		IndenizacaoED ed = (IndenizacaoED)Obj;
		ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorioCessao(ed, request, response);	
		} else 
		if ("2".equals(rel)) {
			this.relatorioRejeicao(ed, request, response);	
		} else 
		if ("3".equals(rel)) {
			this.relatorioInspecao(ed, request, response);	
		} else
		if ("4".equals(rel)) {
			this.relatorioInspecaoRealizada(ed, request, response);	
		} else 
		if ("5".equals(rel)) {
			this.relatorioIndenizacoesMes(ed, request, response);	
		}
	}

	/**
	 * processaOL
	 * Processa solicitação de tela OL retornando sempre arquivo XML com a seguinte estrutura.
	 * <cad>
	 * 		<item campo=valor />
	 * </cad>
	 * @param acao
	 * @param Obj
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws Excecoes
	 * @throws ParseException 
	 */
	public void processaOL(String acao, Object Obj, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Excecoes, ParseException {
		//Extrai o bean com os campos da request colados
		IndenizacaoED ed = (IndenizacaoED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);
	
		//Busca o tipo de empresa do usuario logado
		String tpEmp = Utilitaria.getTipoEmpresa(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			if (this.checkDuplo(ed))
				out.println("<ret><item oknok='Já existe indenização registrada para este certificado !' /></ret>");
			else {
				IndenizacaoED edVolta = this.calcula_Indenizacao(ed);
				// Complementa o ed com os dados da indenização
				ed.setNr_Perc_Desgaste(edVolta.getNr_Perc_Desgaste());
				ed.setNr_Perc_Reforma(edVolta.getNr_Perc_Reforma());
				ed.setNr_Perc_Carcaca(edVolta.getNr_Perc_Carcaca());
				ed.setNr_Perc_Perda_Total(edVolta.getNr_Perc_Perda_Total());
				ed.setNr_Dias_Perda_Total(edVolta.getNr_Dias_Perda_Total());
				ed.setNr_Perc_Perda_Total(edVolta.getNr_Perc_Perda_Total());
				ed.setVl_Indenizacao(edVolta.getVl_Indenizacao());
				ed.setDm_Aceita_Por_Parametro(edVolta.getDm_Aceita_Por_Parametro());
				ed.setDm_Rejeicao(edVolta.getDm_Rejeicao());
				ed.setNr_Dot(edVolta.getNr_Dot());
				ed.setDt_Registro_Recapagem(edVolta.getDt_Registro()) ;
				ed.setNr_Dias_Perda_Total(ed.getNr_Dias_Perda_Total());
				ed = this.inclui(ed);
				String saida="";
				saida+="<ret>" ;
					saida+="<item " ;
						saida+="oknok='IOK' " ;
						saida+="oid_Indenizacao='" + ed.getOid_Indenizacao() + "' " ;
						saida+="msg='" + ed.getTx_Aprovacao_Motivo() + "' " ;
						saida+="rejeicao='" + ed.getDm_Rejeicao() + "' " ;
					saida+="/>" ;
				saida+="</ret>";
				out.println(saida);
			}
		} else 
		if ("A".equals(acao)) {
			// Pega o oid do usuario 
	    	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
	    	ed.setOid_Usuario_Tecnico(usuED.getOid_Usuario().longValue());
	    	ed.setDt_Aprovacao(DataHora.getDataDMY());
	    	ed.setHr_Aprovacao(DataHora.getHoraHM());
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			IndenizacaoED edVolta = this.getByRecord(ed);
			out.println("<cad>");
			out.println(montaRegistro(edVolta));
			out.println("</cad>");
			//}
		} else 
		if ("CI".equals(acao)) {
			IndenizacaoED edVolta = this.calcula_Indenizacao(ed);
			out.println("<cad>");
			String saida=null;
			saida = "<item ";
			saida += "oid_Indenizacao='SIM' ";
			saida += "dt_Indenizacao='" + edVolta.getDt_Indenizacao() + "' ";
			saida += "dt_Registro='" + edVolta.getDt_Registro() + "' ";
			saida += "nr_Dias_Perda_Total='" + edVolta.getNr_Dias_Perda_Total() + "' ";
			saida += "vl_Indenizacao='" + FormataValor.formataValorBT(edVolta.getVl_Indenizacao(),2) + "' ";
			saida += "nr_Perc_Perda_Total='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Perda_Total(),2) + "' ";
			saida += "nr_Perc_Desgaste='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Desgaste(),2) + "' ";
			saida += "nr_Perc_Reforma='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Reforma(),2) + "' ";
			saida += "nr_Perc_Carcaca='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Carcaca(),2) + "' ";
			saida += "vl_Servico='" + FormataValor.formataValorBT(edVolta.getVl_Servico(),2) + "' ";
			saida += "dm_Perda_Total='" + ed.getDm_Perda_Total() + "' ";
			saida += "dm_Aceita_Por_Parametro='" + edVolta.getDm_Aceita_Por_Parametro() + "' ";
			saida +="/>";
			out.println(saida);
			out.println("</cad>");
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
			out.println("<cad>");	
			String saida = null;
			ArrayList<IndenizacaoED> lst = new ArrayList<IndenizacaoED>();
			// Se for a Tipler logada faz o esquema de colcoar a empresa gambiarra ( lookup da tela ) no oid da empresa
			// caso contrário vai a empresa logada mesmo ( concessionaria ) buscar sua consuta
			if ( "T".equals(tpEmp) ) {
				if (ed.getOid_Empresa_Gambiarra()>0)
					ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
				else
					ed.setOid_Empresa(0);
			}
			if ("LIM".equals(acao)) { // Monta as datas de inicio e fim da pesquisa.
				ed.setDt_Indenizacao_Inicial("01/"+ed.getDm_Mes_Ano());
				ed.setDt_Indenizacao_Final(Data.getUltimoDiaDoMes(ed.getDt_Indenizacao_Inicial()));
				ed.setDm_Ordenacao("RC");
			}
			lst = this.lista(ed);
			for (int i=0; i<lst.size(); i++){
				IndenizacaoED edVolta = new IndenizacaoED();
				edVolta = (IndenizacaoED)lst.get(i);
				if ("L".equals(acao) || "LIM".equals(acao)) {
					saida = montaRegistro(edVolta);
				} 
				out.println(saida);
			}
			out.println("</cad>");
		}
	out.flush();
	out.close();
	}

	/**
	 * @param saida
	 * @param edVolta
	 * @return
	 * @throws ParseException 
	 */
	private String montaRegistro(IndenizacaoED edVolta) throws ParseException {
		String saida;
		saida = "<item ";
		saida += "oid_Indenizacao='" + edVolta.getOid_Indenizacao() + "' ";
		saida += "oid_Recapagem_Garantida='" + edVolta.getOid_Recapagem_Garantida() + "' ";
		saida += "dt_Indenizacao='" + edVolta.getDt_Indenizacao() + "' ";
		saida += "dm_Dano_Acidente='" + edVolta.getDm_Dano_Acidente() + "' ";
		saida += "dm_Excesso_Carga='" + edVolta.getDm_Excesso_Carga() + "' ";
		saida += "dm_Baixa_Alta_Pressao='" + edVolta.getDm_Baixa_Alta_Pressao() + "' ";
		saida += "dm_Fora_Aplicacao_Recomendada='" + edVolta.getDm_Fora_Aplicacao_Recomendada() + "' ";
		saida += "dm_Montagem_Desmontagem_Inadequada='" + edVolta.getDm_Montagem_Desmontagem_Inadequada() + "' ";
		saida += "dm_Mau_Estado='" + edVolta.getDm_Mau_Estado() + "' ";
		saida += "dm_Tubless_Com_Camara='" + edVolta.getDm_Tubless_Com_Camara() + "' ";
		saida += "dm_Substancia_Quimica='" + edVolta.getDm_Substancia_Quimica() + "' ";
		saida += "dm_Perda_Total='" + edVolta.getDm_Perda_Total() + "' ";
		saida += "nr_MM='" + FormataValor.formataValorBT(edVolta.getNr_MM(),1) + "' ";
		saida += "nr_Perc_Perda_Total='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Perda_Total(),2) + "' ";
		saida += "nr_Perc_Reforma='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Reforma(),2) + "' ";
		saida += "nr_Perc_Carcaca='" + FormataValor.formataValorBT(edVolta.getNr_Perc_Carcaca(),2) + "' ";
		saida += "vl_Indenizacao='" + FormataValor.formataValorBT(edVolta.getVl_Indenizacao(),2) + "' ";
		saida += "dm_Aprovacao='" + edVolta.getDm_Aprovacao() + "' ";
		saida += "dm_Aceita_Por_Parametro='" + edVolta.getDm_Aceita_Por_Parametro() + "' ";
		saida += "dt_Aprovacao='" + edVolta.getDt_Aprovacao() + "' ";
		saida += "tx_Laudo='" + edVolta.getTx_Laudo() + "' ";
		saida += "oid_Usuario_Tecnico='" + edVolta.getOid_Usuario_Tecnico() + "' ";
		saida += "tx_Aprovacao_Motivo='" + edVolta.getTx_Aprovacao_Motivo() + "' ";
		saida += "nr_Fogo='" + edVolta.getNr_Fogo() + "' ";
		saida += "nr_Nota_Fiscal='" + FormataValor.formataValorBT(edVolta.getNr_Nota_Fiscal(),0) + "' ";
		saida += "dt_Registro='" + edVolta.getDt_Registro() + "' ";
		saida += "dm_Tipo_Pneu='" + ("R".equals(edVolta.getDm_Tipo_Pneu())?"RADIAL":"DIAGONAL") + "' ";
		saida += "nm_Modelo_Pneu='" + edVolta.getNm_Modelo_Pneu() + "' ";
		saida += "nm_Fabricante_Pneu='" + edVolta.getNm_Fabricante_Pneu() + "' ";
		saida += "nm_Banda='" + edVolta.getNm_Banda() + "' ";
		saida += "nm_Concessionaria='" + edVolta.getNm_Concessionaria() + "' ";
		saida += "nm_Razao_Social='" + edVolta.getNm_Razao_Social() + "' ";
		saida += "nm_Regional='" + edVolta.getNm_Regional() + "' ";
		saida += "nm_Usuario_Tecnico='" + edVolta.getNm_Usuario_Tecnico() + "' ";
		saida += "nm_Aprovacao='" + edVolta.getNm_Aprovacao() + "' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}
	
	public boolean checkDuplo ( IndenizacaoED ed) throws Excecoes {
		boolean ret = false;
		IndenizacaoED edChk = new IndenizacaoED();
		edChk.setOid_Recapagem_Garantida(ed.getOid_Recapagem_Garantida());
		//if (this.getByRecord(edChk).getOid_Recapagem_Garantida()>0) {
		//	ret = true;
		//}
		return ret;
	}
	
}

