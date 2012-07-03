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

import com.master.bd.Movimento_Conta_CorrenteBD;
import com.master.ed.Movimento_Conta_CorrenteED;
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
public class Movimento_Conta_CorrenteRN extends Transacao {

	private double vl_Saldo=0;
	
	public Movimento_Conta_CorrenteRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Movimento_Conta_CorrenteRN(ExecutaSQL executasql) {
		super(executasql);
	}

	public Movimento_Conta_CorrenteED inclui (Movimento_Conta_CorrenteED ed) throws Excecoes {
		inicioTransacao ();
		try {
			// Complementa com data, hora
			Movimento_Conta_CorrenteED toReturn = new Movimento_Conta_CorrenteBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Movimento_Conta_CorrenteED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Movimento_Conta_CorrenteBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Movimento_Conta_CorrenteED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Movimento_Conta_CorrenteBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void descontaCreditos (Movimento_Conta_CorrenteED ed) throws Excecoes {
		inicioTransacao ();
		try {
			// Cria registro de débito do desconta da fatura e atualiza os creditos com o oid do lançamento
			//Coloca o crédito do desconto da nf/fatura na conta corrente da concessionaria.
			Movimento_Conta_CorrenteED mccDED = new Movimento_Conta_CorrenteED();
			mccDED.setOid_Concessionaria(ed.getOid_Concessionaria());
			mccDED.setVl_Movimento_Conta_Corrente(ed.getVl_Movimento_Conta_Corrente());
			mccDED.setDt_Movimento_Conta_Corrente(DataHora.getDataDMY());
			mccDED.setNr_Fatura_Tipler(ed.getNr_Fatura_Tipler());
			mccDED.setDm_Debito_Credito("D");
			mccDED.setDm_Tipo_Movimento("D");
			mccDED.setDm_Bloqueado("N");
			mccDED.setTx_Descricao("REF. DESCONTO NF FATURA " + ed.getNr_Fatura_Tipler() );
			mccDED = new Movimento_Conta_CorrenteBD(this.sql).inclui(mccDED);
			// Atualiza os registros com o oid do débito
			ed.setOid_Movimento_Conta_Corrente_Debito(mccDED.getOid_Movimento_Conta_Corrente());
			new Movimento_Conta_CorrenteBD (this.sql).descontaCreditos (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Movimento_Conta_CorrenteED> lista (Movimento_Conta_CorrenteED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Movimento_Conta_CorrenteBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public ArrayList<Movimento_Conta_CorrenteED> listaSaldoMes (Movimento_Conta_CorrenteED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Movimento_Conta_CorrenteBD (this.sql).listaSaldoMes(ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Movimento_Conta_CorrenteED getByRecord (Movimento_Conta_CorrenteED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Movimento_Conta_CorrenteBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}


	public double getSaldoHistorico (Movimento_Conta_CorrenteED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Movimento_Conta_CorrenteBD (this.sql).getSaldoHistorico(ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public void relatorio(Movimento_Conta_CorrenteED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro = "";
			this.vl_Saldo=0;
			if (ed.getOid_Concessionaria()==0) // Se for uma consulta oriunda de uma concessionaria logada esse campo vem zerado
				ed.setOid_Concessionaria(ed.getOid_Empresa()); // Dai coloca a concessionaria logada no campo para as consultas
			Movimento_Conta_CorrenteED mccSED = new  Movimento_Conta_CorrenteED();
			mccSED.setOid_Concessionaria(ed.getOid_Concessionaria());
			mccSED.setDt_Movimento_Conta_Corrente(ed.getDt_Movimento_Conta_Corrente_Inicial());
			mccSED.setVl_Saldo(new Movimento_Conta_CorrenteBD(sql).getSaldoHistorico(mccSED));
			this.vl_Saldo+=mccSED.getVl_Saldo();
			mccSED.setDm_Debito_Credito(this.vl_Saldo>0?"C":"D");
			mccSED.setDt_Movimento_Conta_Corrente("Saldo");
			ArrayList<Movimento_Conta_CorrenteED> newLista = new ArrayList<Movimento_Conta_CorrenteED>();
			newLista.add(mccSED);
			ArrayList<Movimento_Conta_CorrenteED> lista = new Movimento_Conta_CorrenteBD(sql).lista(ed);
			for (int i=0; i<lista.size(); i++){
				Movimento_Conta_CorrenteED edVolta = new Movimento_Conta_CorrenteED();
				edVolta = (Movimento_Conta_CorrenteED)lista.get(i);
				if ("C".equals(edVolta.getDm_Debito_Credito()))
					this.vl_Saldo+=edVolta.getVl_Movimento_Conta_Corrente();
				else
					this.vl_Saldo-=edVolta.getVl_Movimento_Conta_Corrente();
				edVolta.setVl_Saldo(this.vl_Saldo);
				newLista.add(edVolta);
			}	
			ed.setLista(newLista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt304"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNm_Concessionaria()))
				nm_Filtro+="Concessionária=" + ed.getNm_Concessionaria();
			nm_Filtro+=" Período de=" + ed.getDt_Movimento_Conta_Corrente_Inicial();
			nm_Filtro+=" até=" + ed.getDt_Movimento_Conta_Corrente_Final();
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}
	public void relatorioMovimentosBloqueados(Movimento_Conta_CorrenteED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro = "";
			if (ed.getOid_Concessionaria()==0) // Se for uma consulta oriunda de uma concessionaria logada esse campo vem zerado
				ed.setOid_Concessionaria(ed.getOid_Empresa()); // Dai coloca a concessionaria logada no campo para as consultas
			ArrayList<Movimento_Conta_CorrenteED> lista = new Movimento_Conta_CorrenteBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt305"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNm_Concessionaria()))
				nm_Filtro+="Concessionária=" + ed.getNm_Concessionaria();
			nm_Filtro+=" Período de=" + ed.getDt_Movimento_Conta_Corrente_Inicial();
			nm_Filtro+=" até=" + ed.getDt_Movimento_Conta_Corrente_Inicial();
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioCreditosAbertos(Movimento_Conta_CorrenteED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro = "";
			if (ed.getOid_Concessionaria()==0) // Se for uma consulta oriunda de uma concessionaria logada esse campo vem zerado
				ed.setOid_Concessionaria(ed.getOid_Empresa()); // Dai coloca a concessionaria logada no campo para as consultas
			ArrayList<Movimento_Conta_CorrenteED> lista = new Movimento_Conta_CorrenteBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt306"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNm_Concessionaria()))
				nm_Filtro+="Concessionária=" + ed.getNm_Concessionaria();
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioSaldosMes(Movimento_Conta_CorrenteED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			ed.setDt_Movimento_Conta_Corrente(Data.getUltimoDiaDoMes("01/"+ed.getDm_Mes_Ano()));
			String nm_Filtro = "";
			ArrayList<Movimento_Conta_CorrenteED> lista = new Movimento_Conta_CorrenteBD(sql).listaSaldoMes(ed);
			ed.setLista(lista); // Joga a lista no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt318"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNm_Regional()))
				nm_Filtro+="Regional=" + ed.getNm_Regional() + " -" ; 
			else
				nm_Filtro+="Regional=TODOS -" ;
			nm_Filtro+=" Mês=" + ed.getDm_Mes_Ano();
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
		Movimento_Conta_CorrenteED ed = (Movimento_Conta_CorrenteED)Obj;
		ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorio(ed, request, response);	
		} else 
		if ("2".equals(rel)) {
			this.relatorioMovimentosBloqueados(ed, request, response);	
		} else 
		if ("3".equals(rel)) {
			this.relatorioCreditosAbertos(ed, request, response);
		} else 
		if ("4".equals(rel)) {
			this.relatorioSaldosMes(ed, request, response);
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
		Movimento_Conta_CorrenteED ed = (Movimento_Conta_CorrenteED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);
	
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' oid_Movimento_Conta_Corrente='" + ed.getOid_Movimento_Conta_Corrente()+ "' /></ret>");
		} else 
		if ("A".equals(acao)) {
			// Pega o oid do usuario 
	    	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
	    	ed.setOid_Usuario_Desbloqueio(usuED.getOid_Usuario().longValue());
	    	ed.setDt_Desbloqueio(DataHora.getDataDMY());
			this.altera(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Indenizacao() + "' /></ret>");
		} else 
		if ("C".equals(acao)) {
			Movimento_Conta_CorrenteED edVolta = this.getByRecord(ed);
			out.println("<cad>");
			out.println(montaRegistro(edVolta));
			out.println("</cad>");
			//}
		} else 
		if ("DC".equals(acao)) {
			this.descontaCreditos(ed);
			out.println("<ret><item oknok='DCOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else 
		if ("LSM".equals(acao)) { //  Lista saldo mensal
			String saida="";
			ed.setDt_Movimento_Conta_Corrente(Data.getUltimoDiaDoMes("01/"+ed.getDm_Mes_Ano()));
			ArrayList<Movimento_Conta_CorrenteED> lst = this.listaSaldoMes(ed);
			out.println("<cad>");
	    	Iterator<Movimento_Conta_CorrenteED> i = lst.iterator();
		    while(i.hasNext()) {
		    	Movimento_Conta_CorrenteED mcED = i.next();
		    	saida = "<item ";
		    	saida += "nm_Regional='" +mcED.getNm_Regional() + "' ";
		    	saida += "nm_Concessionaria='" +mcED.getNm_Concessionaria() + "' ";
		    	saida += "vl_Saldo='" +FormataValor.formataValorBT(mcED.getVl_Saldo(),2) + "' ";
		    	saida += "dm_Debito_Credito='" +mcED.getDm_Debito_Credito() + "' ";
		    	saida +="/>";
		    	out.println(saida); 
		    }
			out.println("</cad>");
		} else {
		out.println("<cad>");
		String saida = null;
		// - Atenção	-----------------------------------
		// Se for uma consulta oriunda de uma concessionaria logada esse campo vem zerado	
		if (ed.getOid_Concessionaria()==0) // Compo só informado em uma lookup de tela 
			ed.setOid_Concessionaria(ed.getOid_Empresa()); // Dai coloca a concessionaria logada no campo para as consultas			
		//-------------------------------------------------
		if ( "LA".equals(acao) ) {
			this.vl_Saldo=0;
		}else	
		if ( "LS".equals(acao) ) {
			this.vl_Saldo=0;
			Movimento_Conta_CorrenteED mccSED = new  Movimento_Conta_CorrenteED();
			mccSED.setOid_Concessionaria(ed.getOid_Concessionaria());
			mccSED.setDt_Movimento_Conta_Corrente(ed.getDt_Movimento_Conta_Corrente_Inicial());
			mccSED.setVl_Saldo(this.getSaldoHistorico(mccSED));
			this.vl_Saldo+=mccSED.getVl_Saldo();
			mccSED.setDm_Debito_Credito(this.vl_Saldo>0?"C":"D");
			out.println(montaRegistroSaldoTotal(mccSED,"SALDO"));
		} 
		ArrayList<Movimento_Conta_CorrenteED> lst = new ArrayList<Movimento_Conta_CorrenteED>();
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Movimento_Conta_CorrenteED edVolta = new Movimento_Conta_CorrenteED();
			edVolta = (Movimento_Conta_CorrenteED)lst.get(i);
			if ("L".equals(acao) || "LS".equals(acao) || "LA".equals(acao)) {
				if ( "LS".equals(acao) || "LA".equals(acao ) ) {
					if ("C".equals(edVolta.getDm_Debito_Credito()))
						this.vl_Saldo+=edVolta.getVl_Movimento_Conta_Corrente();
					else
						this.vl_Saldo-=edVolta.getVl_Movimento_Conta_Corrente();
					edVolta.setVl_Saldo(this.vl_Saldo);
				}	
				saida = montaRegistro(edVolta);
			} 
			out.println(saida);
		}
		if ( "LA".equals(acao) && lst.size()>0) {
			Movimento_Conta_CorrenteED mccSED = new  Movimento_Conta_CorrenteED();
			this.vl_Saldo+=mccSED.getVl_Saldo();
			mccSED.setDm_Debito_Credito(this.vl_Saldo>0?"C":"D");
			mccSED.setVl_Saldo(this.vl_Saldo);
			out.println(montaRegistroSaldoTotal(mccSED,"TOTAL"));
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
	private String montaRegistro(Movimento_Conta_CorrenteED edVolta) throws ParseException {
		String saida;
		saida = "<item ";
		saida += "oid_Movimento_Conta_Corrente='" + edVolta.getOid_Movimento_Conta_Corrente() + "' ";
		saida += "oid_Indenizacao='" + edVolta.getOid_Indenizacao() + "' ";
		saida += "oid_Concessionaria='" + edVolta.getOid_Concessionaria() + "' ";
		saida += "oid_Usuario_Desbloqueio='" + edVolta.getOid_Usuario_Desbloqueio() + "' ";
		saida += "dt_Movimento_Conta_Corrente='" + edVolta.getDt_Movimento_Conta_Corrente() + "' ";
		saida += "dt_Desbloqueio='" + edVolta.getDt_Desbloqueio() + "' ";
		saida += "dm_Debito_Credito='" + edVolta.getDm_Debito_Credito() + "' ";
		saida += "dm_Tipo_Movimento='" + edVolta.getDm_Tipo_Movimento() + "' ";
		saida += "nm_Tipo_Movimento='" + edVolta.getNm_Tipo_Movimento() + "' ";
		saida += "dm_Bloqueado='" + edVolta.getDm_Bloqueado() + "' ";
		saida += "tx_Bloqueio='" + edVolta.getTx_Bloqueio() + "' ";
		saida += "tx_Descricao='" + edVolta.getTx_Descricao() + "' ";
		saida += "nr_Fatura_Tipler='" + FormataValor.formataValorBT(edVolta.getNr_Fatura_Tipler(),0) + "' ";
		saida += "oid_Movimento_Conta_Corrente_Debito='" + edVolta.getOid_Movimento_Conta_Corrente_Debito() + "' ";
		saida += "vl_Movimento_Conta_Corrente='" + FormataValor.formataValorBT(edVolta.getVl_Movimento_Conta_Corrente(),2) + "' ";
		saida += "vl_Saldo='" + FormataValor.formataValorBT(edVolta.getVl_Saldo(),2) + "' ";
		saida += "nm_Concessionaria='" + edVolta.getNm_Concessionaria() + "' ";
		saida += "nm_Usuario_Tecnico='" + edVolta.getNm_Usuario_Tecnico() + "' ";
		saida += "dt_Aprovacao='" + edVolta.getDt_Aprovacao() + "' ";
		saida += "dm_Aprovacao='" + ("S".equals(edVolta.getDm_Aprovacao())?"SIM":"N".equals(edVolta.getDm_Aprovacao())?"NÃO":"NÃO INSPECIONADO") + "' ";
		saida += "dm_Desconta='false' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}

	private String montaRegistroSaldoTotal(Movimento_Conta_CorrenteED edVolta,String tx_Tipo) throws ParseException {
		String saida;
		saida = "<item ";
		saida += "oid_Movimento_Conta_Corrente='0' ";
		saida += "dt_Movimento_Conta_Corrente='"+tx_Tipo+"' ";
		saida += "dm_Debito_Credito='"+edVolta.getDm_Debito_Credito()+"' ";
		saida += "dm_Tipo_Movimento='' ";
		saida += "tx_Descricao='' ";
		if ("SALDO".equals(tx_Tipo)){
			saida += "vl_Movimento_Conta_Corrente='' ";
			saida += "vl_Saldo='" + FormataValor.formataValorBT(edVolta.getVl_Saldo(),2) + "' ";
		} else {
			saida += "vl_Movimento_Conta_Corrente='" + FormataValor.formataValorBT(edVolta.getVl_Saldo(),2) + "' ";
			saida += "vl_Saldo='' ";
		}
		saida += "/>";
		return saida;
	}


}
