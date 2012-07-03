/*
 * Created on 11/06/2009
 *
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Recapagem_GarantidaBD;
import com.master.ed.IndenizacaoED;
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
public class Recapagem_GarantidaRN extends Transacao {

	public Recapagem_GarantidaRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Recapagem_GarantidaRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Recapagem_GarantidaED inclui (Recapagem_GarantidaED ed) throws Excecoes, ParseException {
		inicioTransacao ();
		try {
			// Complementa com data, hora e oid_concessionaria
			ed.setDt_Registro(DataHora.getDataDMY());
			ed.setHr_Registro(DataHora.getHoraHM());
			ed.setDt_Emissao_Certificado(DataHora.getDataDMY());
			ed.setHr_Emissao_Certificado(DataHora.getHoraHM());			
			ed.setOid_Concessionaria(ed.getOid_Empresa());
			ed.setDt_Validade_Garantia(this.getValidade_Garantia(ed));
			//ed.setDt_Validade_Garantia(Data.getSomaMesesData(Data.getDataDMY(), 24));
			Recapagem_GarantidaED toReturn = new Recapagem_GarantidaBD (this.sql).inclui (ed); // Karla em 13/01/2010
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public Recapagem_GarantidaED altera (Recapagem_GarantidaED ed) throws Excecoes, ParseException {
		inicioTransacao ();
		try {
			// Pega o registro original e guarda ...	
			Recapagem_GarantidaED edAlt = new Recapagem_GarantidaED();
			edAlt.setOid_Recapagem_Garantida(ed.getOid_Recapagem_Garantida());
			edAlt= new Recapagem_GarantidaBD(this.sql).getByRecord(edAlt);
			// Complementa com data, hora e oid_concessionaria 
			ed.setDt_Registro(edAlt.getDt_Registro());
			ed.setHr_Registro(edAlt.getHr_Registro());
			ed.setDt_Emissao_Certificado(DataHora.getDataDMY());
			ed.setHr_Emissao_Certificado(DataHora.getHoraHM());
			ed.setOid_Concessionaria(ed.getOid_Empresa());
			//Recalcula a data da validade
			ed.setDt_Validade_Garantia(this.getValidade_Garantia(ed));
            //ed.setDt_Validade_Garantia(Data.getSomaMesesData(Data.getDataDMY(), 24));
			// Pega o oid da substituida
			ed.setOid_Recapagem_Garantida_Substituta(edAlt.getOid_Recapagem_Garantida());
			Recapagem_GarantidaED toReturn = new Recapagem_GarantidaBD (this.sql).inclui (ed);
			
			// Pega o oid da substituta
			edAlt.setOid_Recapagem_Garantida_Substituta(toReturn.getOid_Recapagem_Garantida());
			new Recapagem_GarantidaBD (this.sql).altera (edAlt);
			
			fimTransacao (true);
			
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Recapagem_GarantidaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Recapagem_GarantidaBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Recapagem_GarantidaED> lista (Recapagem_GarantidaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Recapagem_GarantidaBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}
	}

	public Recapagem_GarantidaED getByRecord (Recapagem_GarantidaED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Recapagem_GarantidaBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	
	public ArrayList<Recapagem_GarantidaED> getResumoPeriodo (Recapagem_GarantidaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Recapagem_GarantidaBD (this.sql).getResumoPeriodo(ed);
		}
		finally {
			this.fimTransacao (false);
		}
	}

	
	public void relatorioCertificado(Recapagem_GarantidaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			ArrayList<Recapagem_GarantidaED> lista = new Recapagem_GarantidaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt101"); // Seta o nome do relatório
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioRecapagensGarantidas(Recapagem_GarantidaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro="";
			
			if ("311".equals(ed.getDm_Tela_Consulta())) {
				// Se a consulta for feita pela Tipler coloca a empresa que esta sendo pesquisada na concessionaria
				ed.setOid_Concessionaria(ed.getOid_Empresa_Gambiarra());
			} else {
				//Se a consulta for feita pelo concessionário coloca a empresa no concessionario
				ed.setOid_Concessionaria(ed.getOid_Empresa());
			}
			
			ArrayList<Recapagem_GarantidaED> lista = new Recapagem_GarantidaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			if (Utilitaria.doValida(ed.getNr_Fogo()))
				nm_Filtro+=" Nº Fogo/Série="+ed.getNr_Fogo();
			if (ed.getNr_Nota_Fiscal()>0)
				nm_Filtro+=" Nº Fota Fiscal="+ed.getNr_Nota_Fiscal();
			if (Utilitaria.doValida(ed.getNm_Razao_Social_Cliente()))
				nm_Filtro+=" Cliente="+ed.getNm_Razao_Social_Cliente();
			
			nm_Filtro+=" Período de="+ed.getDt_Registro_Inicial();
			nm_Filtro+=" até="+ed.getDt_Registro_Final();
			ed.setDescFiltro(nm_Filtro);
			if ("311".equals(ed.getDm_Tela_Consulta())) {
				ed.setNomeRelatorio("gtt311"); // Seta o nome do relatório
			} else {
				ed.setNomeRelatorio("gtt301"); // Seta o nome do relatório
			}
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioResumoPeriodo(Recapagem_GarantidaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro="";

			ArrayList<Recapagem_GarantidaED> lista = this.getResumoPeriodo(ed);
			ed.setLista(lista); // Joga a lista no ed para enviar pro relatório 
			ed.setResponse(response);
			nm_Filtro+=" Mês = "+ed.getDm_Mes_Ano();
			ed.setDescFiltro(nm_Filtro);
			ed.setNomeRelatorio("gtt317"); // Seta o nome do relatório
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
		Recapagem_GarantidaED ed = (Recapagem_GarantidaED)Obj;
		ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorioCertificado(ed, request, response);	
		} else
		if ("2".equals(rel)) {
			this.relatorioRecapagensGarantidas(ed, request, response);	
		} else
		if ("3".equals(rel)) {
			this.relatorioResumoPeriodo(ed, request, response);	
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
		Recapagem_GarantidaED ed = (Recapagem_GarantidaED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);
	
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			if (this.checkDuplo(ed))
				out.println("<ret><item oknok='Já existe garantia registrada para este Cliente, NF e Pneu !' /></ret>");
			else {
				// Pega o oid do usuario 
		    	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
		    	ed.setOid_Usuario(usuED.getOid_Usuario().longValue());
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Recapagem_Garantida() + "' /></ret>");
			}
		} else 
		if ("A".equals(acao)) {
			// Pega o oid do usuario 
	    	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
	    	ed.setOid_Usuario(usuED.getOid_Usuario().longValue());
			ed = this.altera(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Recapagem_Garantida() + "' /></ret>");
		} else 
		if ("C".equals(acao)) {
			Recapagem_GarantidaED edVolta = this.getByRecord(ed);
			out.println("<cad>");
			out.println(montaRegistro(edVolta));
			out.println("</cad>");
			//}
		} else 
		if ("CC".equals(acao)) {
			//ed.setOid_Concessionaria(ed.getOid_Empresa());
			Recapagem_GarantidaED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Recapagem_Garantida()>0) {
				IndenizacaoED indED = new IndenizacaoED();
				indED.setOid_Recapagem_Garantida(edVolta.getOid_Recapagem_Garantida());
				indED = new IndenizacaoRN(this.sql).getByRecord(indED);
				if (indED.getOid_Indenizacao()==0) {
					if( !"S".equals(edVolta.getDm_Substituida())) {
						out.println("<cad>");
						if (edVolta.getOid_Concessionaria()==ed.getOid_Empresa())
							edVolta.setDm_Esconde_Preco("N");
						else
							edVolta.setDm_Esconde_Preco("S");
						System.out.println("edVolta.getDm_Esconde_Preco()="+edVolta.getDm_Esconde_Preco());
						out.println(montaRegistro(edVolta));
						out.println("</cad>");
					} else {
						Recapagem_GarantidaED subED = new Recapagem_GarantidaED();
						subED.setOid_Recapagem_Garantida(edVolta.getOid_Recapagem_Garantida_Substituta());
						subED = this.getByRecord(subED);
						out.println("<ret><item oknok='Certificado substituido em "+ subED.getDt_Registro()+" ! Novo certificado "+ subED.getNr_Certificado() +", Pneu " + subED.getNr_Fogo()+".'/></ret>");
					}
				} else {
					out.println("<ret><item oknok='Garantia já indenizada em " + indED.getDt_Indenizacao() + " ! Pneu " + indED.getNr_Fogo() + ", Cliente "+ indED.getNm_Razao_Social() + ".' /></ret>");
				}
			} else {
				out.println("<ret><item oknok='Certificado não encontrado!' /></ret>");
			}
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else 
		if ("R".equals(acao)) {
			String saida="";
			ArrayList<Recapagem_GarantidaED> lst = this.getResumoPeriodo(ed);
			out.println("<cad>");
		    	Iterator<Recapagem_GarantidaED> i = lst.iterator();
			    while(i.hasNext()) {
			    	Recapagem_GarantidaED rgED = i.next();
			    	saida = "<item ";
			    	saida += "nm_Regional='" +rgED.getNm_Regional() + "' ";
			    	saida += "nm_Razao_Social_Concessionaria='" +rgED.getNm_Razao_Social_Concessionaria() + "' ";
			    	saida += "nr_Total_Recapagens_Garantidas='" +rgED.getNr_Total_Recapagens_Garantidas() + "' ";
			    	saida += "nr_Total_Indenizacoes_Periodo='" +rgED.getNr_Total_Indenizacoes_Periodo() + "' ";
			    	saida += "vl_Total_Indenizado_Periodo='" +FormataValor.formataValorBT(rgED.getVl_Total_Indenizado_Periodo(),2) + "' ";
			    	saida += "nr_Total_Indenizacoes_Nao_Inspecionadas='" +rgED.getNr_Total_Indenizacoes_Nao_Inspecionadas() + "' ";
			    	saida += "nr_Total_Indenizacoes_Inspecionadas='" +rgED.getNr_Total_Indenizacoes_Inspecionadas() + "' ";
			    	saida += "nr_Total_Indenizacoes_Aprovadas='" +rgED.getNr_Total_Indenizacoes_Aprovadas() + "' ";
			    	saida += "nr_Total_Indenizacoes_Reprovadas='" +rgED.getNr_Total_Indenizacoes_Reprovadas() + "' ";
			    	saida += "nr_Total_Indenizacoes_Rejeitadas='" +rgED.getNr_Total_Indenizacoes_Rejeitadas() + "' ";
			    	saida += "nr_Perc_Faturamento='" +FormataValor.formataValorBT(rgED.getNr_Perc_Faturamento(),2) + "' ";
			    	saida +="/>";
			    	out.println(saida); 
			    }
			out.println("</cad>");
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Recapagem_GarantidaED> lst = new ArrayList<Recapagem_GarantidaED>();
		
		if ("311".equals(ed.getDm_Tela_Consulta())) {
			// Se a consulta for feita pela Tipler coloca a empresa que esta sendo pesquisada na concessionaria
			ed.setOid_Concessionaria(ed.getOid_Empresa_Gambiarra());
		} else {
			//Se a consulta for feita pelo concessionário coloca a empresa no concessionario
			ed.setOid_Concessionaria(ed.getOid_Empresa());
		}
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Recapagem_GarantidaED edVolta = new Recapagem_GarantidaED();
			edVolta = (Recapagem_GarantidaED)lst.get(i);
			if ("L".equals(acao)) {
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
	private String montaRegistro(Recapagem_GarantidaED edVolta) throws ParseException {
		String saida;
		saida = "<item ";
		saida += "oid_Recapagem_Garantida='" + edVolta.getOid_Recapagem_Garantida() + "' ";
		saida += "oid_Usuario='" + edVolta.getOid_Usuario() + "' ";
		saida += "oid_Cliente='" + edVolta.getOid_Cliente() + "' ";
		saida += "oid_Concessionaria='" + edVolta.getOid_Concessionaria() + "' ";
		saida += "oid_Fabricante_Pneu='" + edVolta.getOid_Fabricante_Pneu() + "' ";
		saida += "oid_Modelo_Pneu='" + edVolta.getOid_Modelo_Pneu() + "' ";
		saida += "oid_Banda='" + edVolta.getOid_Banda() + "' ";
		saida += "dt_Registro='" + edVolta.getDt_Registro() + "' ";
		saida += "hr_Registro='" + edVolta.getHr_Registro() + "' ";
		saida += "nr_Nota_Fiscal='" + edVolta.getNr_Nota_Fiscal() + "' ";
		saida += "vl_Servico='" + FormataValor.formataValorBT(edVolta.getVl_Servico(), 2) + "' ";
		saida += "dm_Reparo='" + edVolta.getDm_Reparo() + "' ";
		saida += "nr_Quantidade_Reparo='" + FormataValor.formataValorBT(edVolta.getNr_Quantidade_Reparo(),0) + "' ";
		saida += "nr_Fogo='" + edVolta.getNr_Fogo() + "' ";
		saida += "dm_Tipo_Pneu='" + edVolta.getDm_Tipo_Pneu() + "' ";
		saida += "nr_Dot='" + edVolta.getNr_Dot() + "' ";
		saida += "nr_Indice_Carga='" + edVolta.getNr_Indice_Carga() + "' ";
		saida += "nr_Vida='" + edVolta.getNr_Vida() + "' ";
		saida += "dm_Dano_Acidente='" + edVolta.getDm_Dano_Acidente()+ "' ";
		saida += "dm_Excesso_Carga='" + edVolta.getDm_Excesso_Carga()+ "' ";
		saida += "dm_Baixa_Alta_Pressa='" + edVolta.getDm_Baixa_Alta_Pressao()+ "' ";
		saida += "dm_Fora_Aplicacao_Recomendadae='" + edVolta.getDm_Fora_Aplicacao_Recomendada()+ "' ";
		saida += "dm_Montagem_Desmontagem_Inadequada='" + edVolta.getDm_Montagem_Desmontagem_Inadequada()+ "' ";
		saida += "dm_Mau_estado='" + edVolta.getDm_Mau_estado()+ "' ";
		saida += "dm_Tubless_Com_Camara='" + edVolta.getDm_Tubless_Com_Camara()+ "' ";
		saida += "dm_Substancia_Quimica='" + edVolta.getDm_Substancia_Quimica()+ "' ";
		saida += "dt_Validade_Garantia='" + edVolta.getDt_Validade_Garantia() + "' ";
		saida += "nr_Certificado='" + edVolta.getNr_Certificado() + "' ";
		saida += "dm_Substituida='" + edVolta.getDm_Substituida()+ "' ";
		saida += "oid_Recapagem_Garantida_Substituta='" + edVolta.getOid_Recapagem_Garantida_Substituta() + "' ";
		saida += "dm_Asterisco='" + (edVolta.getOid_Recapagem_Garantida_Substituta() > 0?"*":"") + "' ";
		saida += "nm_Usuario='" + edVolta.getNm_Usuario() + "' ";
		saida += "nm_Razao_Social_Cliente='" + edVolta.getNm_Razao_Social_Cliente() + "' ";
		saida += "nr_Cnpj_Cliente='" + edVolta.getNr_Cnpj_Cliente() + "' ";
		saida += "nm_Razao_Social_Concessionaria='" + edVolta.getNm_Razao_Social_Concessionaria() + "' ";
		saida += "nm_Banda='" + edVolta.getNm_Banda() + "' ";
		saida += "nm_Fabricante_Pneu='" + edVolta.getNm_Fabricante_Pneu() + "' ";
		saida += "nm_Modelo_Pneu='" + edVolta.getNm_Modelo_Pneu() + "' ";
		saida += "nm_Vida='" + edVolta.getNm_Vida() + "' ";
		saida += "dm_Indenizada='" + edVolta.getDm_Indenizada() + "' ";
		saida += "nr_MM='" + FormataValor.formataValorBT(edVolta.getNr_MM(), 1) + "' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		saida += "dm_Esconde_Preco='" + edVolta.getDm_Esconde_Preco() + "' ";
		saida += "dm_Expirou='" + this.getExpirou_Garantia(edVolta) + "' ";
		saida += "/>";
		return saida;
	}
	
	public boolean checkDuplo ( Recapagem_GarantidaED ed) throws Excecoes {
		boolean ret = false;
		Recapagem_GarantidaED edChk = new Recapagem_GarantidaED();
		edChk.setOid_Cliente(ed.getOid_Cliente());
		edChk.setOid_Concessionaria(ed.getOid_Empresa());
		edChk.setNr_Nota_Fiscal(ed.getNr_Nota_Fiscal());
		edChk.setNr_Fogo(ed.getNr_Fogo());
		edChk.setOid_Modelo_Pneu(ed.getOid_Modelo_Pneu());
		if (this.getByRecord(edChk).getOid_Recapagem_Garantida()>0) {
			ret = true;
		}
		return ret;
	}

	public String getExpirou_Garantia (Recapagem_GarantidaED ed) throws ParseException {
		String str="N";
		try {
			if ( DataHora.comparaData(ed.getDt_Validade_Garantia(), "<", DataHora.getDataDMY()) )
				str="S";
		} catch (Excecoes e) {
			e.printStackTrace();
		}
		return str;
	}
	
	// Fazer aqui o calculo do termino da garantia dependente do numero do dot
	// É o menor dos dois : Data da garantia calculada e data vencimento do DOT 
	// Pega a data da recapageme coloca o pr=eriodo de dias em cima 
	// Compara com a data do encimento do dot pega a menor
	public String getValidade_Garantia (Recapagem_GarantidaED ed) throws ParseException  {
		String dt_Limite_Garantia=null;
		String dt_Limite_Dot=null;
		String dataOut=null;
		int dias;
		
		if ("R".equals(ed.getDm_Tipo_Pneu())) 
			dias = 1095;
		else 
			dias = 548;
		// Pega a data da recapagem e calcula a data do vecimento da garantia
		try {
			dt_Limite_Garantia=DataHora.getSomaDiaData(ed.getDt_Registro(),dias);
		} catch (Excecoes e) {
			e.printStackTrace();
		}
		// Pega o dot e calcula a validade
		int semana = Integer.parseInt(ed.getNr_Dot().substring(0, 2));
		String ano = ed.getNr_Dot().substring(2, 4); 
		semana=semana*7;
		String dt2 = "20" + ano + "/" + semana;
		String dt = DataHora.getJulianaToDDMMYY(dt2);
		try {
			dt_Limite_Dot = DataHora.getSomaDiaData(dt,1825);
		} catch (Excecoes e) {
			e.printStackTrace();
		}
		//Testa qual a data vai ser a da garantia
		try {
			if (Data.comparaData(dt_Limite_Garantia, "<", dt_Limite_Dot)){
				dataOut = dt_Limite_Garantia;
			} else{
				dataOut = dt_Limite_Dot;
			}
		} catch (Excecoes e) {
			e.printStackTrace();
		}
		return dataOut;
	}
	
	
/*	public boolean checkEmUso ( Recapagem_GarantidaED ed ) throws Excecoes {
		try {
			boolean achei=false;
			// Procura na recapagem
			this.inicioTransacao();
			RecapagemED recED = new RecapagemED();
			recED.setOid_Banda(ed.getOid_Banda());
			achei=(new RecapagemBD(this.sql).lista(recED).size()>0 ? true : false);
			return (achei); 
		} finally {
			this.fimTransacao(false);
		}
	}
*/
}
