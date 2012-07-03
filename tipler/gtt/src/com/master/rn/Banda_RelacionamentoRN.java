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

import com.master.bd.Banda_RelacionamentoBD;
import com.master.ed.Banda_RelacionamentoED;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
/**
 * @author Régis Steigleder
 * @serial Bandas dimensoes
 * @serialData 01/2010
 */
public class Banda_RelacionamentoRN extends Transacao {
	
	public Banda_RelacionamentoRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Banda_RelacionamentoRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Banda_RelacionamentoED inclui (Banda_RelacionamentoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Banda_RelacionamentoED toReturn = new Banda_RelacionamentoBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (ArrayList lista) throws Excecoes {
		inicioTransacao ();
		try {
			new Banda_RelacionamentoBD (this.sql).delete (lista);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList lista (Banda_RelacionamentoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Banda_RelacionamentoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public ArrayList getRelacionamentos (Banda_RelacionamentoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Banda_RelacionamentoBD (this.sql).getRelacionamentos (ed);
		}
		finally {
			this.fimTransacao (false);
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
		Banda_RelacionamentoED ed = (Banda_RelacionamentoED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ArrayList lista = this.getRelacionamentos(ed);
			if (lista.size()>0) {
				out.println("<ret><item oknok='Relacionamento já existente !' /></ret>");
			} else {
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Banda_Relacionamento() + "' /></ret>");
			}
		} else 
		if ("D".equals(acao)) {
			ArrayList lista = this.getRelacionamentos(ed);
			this.delete(lista);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else	
		if ("lookup".equals(acao)) {
			ArrayList lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Banda_RelacionamentoED edVolta = (Banda_RelacionamentoED)lst.get(i);
					saida = "<item ";
					saida += "oid_Banda_Relacionamento ='" + edVolta.getOid_Banda_Relacionamento() + "' ";
					saida += "oid_Banda_1 ='" + edVolta.getOid_Banda_1() + "' ";
					saida += "oid_Banda_2 ='" + edVolta.getOid_Banda_2() + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Relacionamento não encontrado !'/></ret>");
			}		
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList lst = new ArrayList();
		lst = this.lista(ed);
		
		for (int i=0; i<lst.size(); i++){
			Banda_RelacionamentoED edVolta = new Banda_RelacionamentoED();
			edVolta = (Banda_RelacionamentoED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Banda_Relacionamento='" + edVolta.getOid_Banda_Relacionamento() + "' ";
				saida += "oid_Banda_1='" + edVolta.getOid_Banda_1() + "' ";
				saida += "nm_Banda_1='" + edVolta.getNm_Banda_1() + "' ";
				saida += "nm_Fabricante_Banda_1='" + edVolta.getNm_Fabricante_Banda_1() + "' ";
				saida += "oid_Banda_2='" + edVolta.getOid_Banda_2() + "' ";
				saida += "nm_Banda_2='" + edVolta.getNm_Banda_2() + "' ";
				saida += "nm_Fabricante_Banda_2='" + edVolta.getNm_Fabricante_Banda_2() + "' ";
				saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
				saida += "/>";
			} 			
			out.println(saida);
		}
		out.println("</cad>");
	}
	out.flush();
	out.close();
	}

}
