/*
 * Created on 12/11/2004
 *
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Banda_DimensaoBD;
import com.master.ed.Banda_DimensaoED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
/**
 * @author Régis Steigleder
 * @serial Bandas dimensoes
 * @serialData 01/2010
 */
public class Banda_DimensaoRN extends Transacao {
	
	public Banda_DimensaoRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Banda_DimensaoRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Banda_DimensaoED inclui (Banda_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Banda_DimensaoED toReturn = new Banda_DimensaoBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Banda_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Banda_DimensaoBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void converte (Banda_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Banda_DimensaoBD (this.sql).converte (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public void delete (Banda_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Banda_DimensaoBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList lista (Banda_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Banda_DimensaoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Banda_DimensaoED getByRecord (Banda_DimensaoED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Banda_DimensaoBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
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
	 */
	public void processaOL(String acao, Object Obj, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Excecoes {
		//Extrai o bean com os campos da request colados
		Banda_DimensaoED ed = (Banda_DimensaoED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Banda_Dimensao() + "' /></ret>");
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("X".equals(acao)) {
			this.converte(ed);
			out.println("CONVERSAO OK");
		} else 			
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else	
		if ("lookup".equals(acao)) {
			ArrayList lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Banda_DimensaoED edVolta = (Banda_DimensaoED)lst.get(i);
					saida = "<item ";
					saida += "oid_Banda_Dimensao ='" + edVolta.getOid_Banda_Dimensao() + "' ";
					saida += "nr_Largura ='" + FormataValor.formataValorBT(edVolta.getNr_Largura(),1) + "' ";
					saida += "nr_Profundidade ='" + FormataValor.formataValorBT(edVolta.getNr_Profundidade(),1) + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Banda não encontrada !'/></ret>");
			}		
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList lst = new ArrayList();
		lst = this.lista(ed);
		
		for (int i=0; i<lst.size(); i++){
			Banda_DimensaoED edVolta = new Banda_DimensaoED();
			edVolta = (Banda_DimensaoED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Banda_Dimensao='" + edVolta.getOid_Banda_Dimensao() + "' ";
				saida += "oid_Banda='" + edVolta.getOid_Banda() + "' ";
				saida += "nr_Profundidade='" + FormataValor.formataValorBT(edVolta.getNr_Profundidade(),1) + "' ";
				saida += "nr_Largura='" + FormataValor.formataValorBT(edVolta.getNr_Largura(),0) + "' ";
				saida += "nr_Peso='" + FormataValor.formataValorBT(edVolta.getNr_Peso(),2) + "' ";
				saida += "nr_Peso_Metro='" + FormataValor.formataValorBT(edVolta.getNr_Peso_Metro(),2) + "' ";
				saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
				saida += "/>";
			} else
			if ("CB".equals(acao) || "CBC".equals(acao)) {
				if ( i==0 && "CBC".equals(acao) ) {
					saida = "<item ";
					saida += "value='0'>TODOS</item>";
					out.println(saida);
				}
				saida = "<item ";
				saida += "value='" + edVolta.getOid_Banda_Dimensao() + "' nr_Largura='" + FormataValor.formataValorBT(edVolta.getNr_Largura(),0) + "' nr_Profundidade='" + FormataValor.formataValorBT(edVolta.getNr_Profundidade(),1) +  "'>";
				saida +=  "L:" + FormataValor.formataValorBT(edVolta.getNr_Largura(),0) + " mm P:" + FormataValor.formataValorBT(edVolta.getNr_Profundidade(),1) + " mm";				saida += "</item>";
			}

			out.println(saida);
		}
		out.println("</cad>");
	}
	out.flush();
	out.close();
	}

	public boolean checkEmUso ( Banda_DimensaoED ed ) throws Excecoes {
		try {
			boolean achei=false;
			// Procura na recapagem
			this.inicioTransacao();
			Recapagem_GarantidaED recED = new Recapagem_GarantidaED();
			recED.setOid_Banda(ed.getOid_Banda());
			achei=(new Recapagem_GarantidaRN(this.sql).lista(recED).size()>0 ? true : false);
			return (achei); 
		} finally {
			this.fimTransacao(false);
		}
	}

}
