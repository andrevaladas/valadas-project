package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stib_InspecaoBD;
import com.master.bd.Stib_Inspecao_ItemBD;
import com.master.ed.Stib_Inspecao_ItemED;
import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;

/**
 * @author Régis Steigleder
 * @serial STIB - Itens inspecionados
 * @serialData 04/2012
 */
public class Stib_Inspecao_ItemRN extends Transacao {
	
	public Stib_Inspecao_ItemRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stib_Inspecao_ItemRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stib_Inspecao_ItemED inclui (Stib_Inspecao_ItemED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stib_Inspecao_ItemED toReturn = new Stib_Inspecao_ItemBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stib_Inspecao_ItemED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_Inspecao_ItemBD (this.sql).altera(ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Stib_Inspecao_ItemED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_Inspecao_ItemBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Stib_Inspecao_ItemED> lista (Stib_Inspecao_ItemED ed) throws Excecoes {
		inicioTransacao ();
		try {
			ArrayList<Stib_Inspecao_ItemED> lstItensInspecao = new Stib_Inspecao_ItemBD (this.sql).lista (ed);
			return lstItensInspecao;
		}
		finally {
			this.fimTransacao (false);
		}
	}
	
	public Stib_Inspecao_ItemED getByRecord (Stib_Inspecao_ItemED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stib_Inspecao_ItemBD (this.sql).getByRecord (ed);
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
		Stib_Inspecao_ItemED ed = (Stib_Inspecao_ItemED)Obj;
		// Pega a lingua do usuário para busca dos dados
		UsuarioED usuario = (UsuarioED)request.getSession(true).getAttribute("usuario");
		ed.setDm_Lingua(usuario.getDm_Lingua());
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Inspecao() + "' /></ret>");
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Stib_Inspecao_ItemED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stib_Inspecao_ItemED edVolta = new Stib_Inspecao_ItemED();
			edVolta = (Stib_Inspecao_ItemED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Inspecao_Item ='" + edVolta.getOid_Inspecao_Item() + "' ";
				saida += "oid_Inspecao ='" + edVolta.getOid_Inspecao() + "' ";
				saida += "nr_Ordem ='" + edVolta.getNr_Ordem() + "' ";
				saida += "nm_Item_Inspecionado ='" + edVolta.getNm_Item_Inspecionado()+ "' ";
				saida += "dm_Tipo ='" + edVolta.getDm_Tipo() + "' ";
				saida += "dm_Situacao ='" + edVolta.isDm_Situacao() + "' ";
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
