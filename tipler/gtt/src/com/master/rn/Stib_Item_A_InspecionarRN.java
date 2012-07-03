package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stib_Item_A_InspecionarBD;
import com.master.ed.Stib_Item_A_InspecionarED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
/**
 * @author Régis Steigleder
 * @serial STIB - Itens a inspecionar
 * @serialData 03/2012
 */
public class Stib_Item_A_InspecionarRN extends Transacao {
	
	public Stib_Item_A_InspecionarRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stib_Item_A_InspecionarRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stib_Item_A_InspecionarED inclui (Stib_Item_A_InspecionarED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stib_Item_A_InspecionarED toReturn = new Stib_Item_A_InspecionarBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stib_Item_A_InspecionarED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_Item_A_InspecionarBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Stib_Item_A_InspecionarED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_Item_A_InspecionarBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Stib_Item_A_InspecionarED> lista (Stib_Item_A_InspecionarED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stib_Item_A_InspecionarBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Stib_Item_A_InspecionarED getByRecord (Stib_Item_A_InspecionarED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stib_Item_A_InspecionarBD (this.sql).getByRecord (ed);
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
		Stib_Item_A_InspecionarED ed = (Stib_Item_A_InspecionarED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Item_A_Inspecionar() + "' /></ret>");
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else	
		if ("lookup".equals(acao)) {
			ArrayList<Stib_Item_A_InspecionarED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Stib_Item_A_InspecionarED edVolta = (Stib_Item_A_InspecionarED)lst.get(i);
					saida = "<item ";
					saida += "oid_Item_A_Inspecionar ='" + edVolta.getOid_Item_A_Inspecionar() + "' ";
					saida += "cd_Item_A_Inspecionar ='" + edVolta.getCd_Item_A_Inspecionar() + "' ";
					saida += "nr_Ordem ='" + FormataValor.formataValorBT(edVolta.getNr_Ordem(),0) + "' ";
					saida += "nm_Item_A_Inspecionar ='" + edVolta.getNm_Item_A_Inspecionar() + "' ";
					saida += "nm_Item_A_Inspecionar_E ='" + edVolta.getNm_Item_A_Inspecionar_E() + "' ";
					saida += "dm_Tipo ='" + edVolta.getDm_Tipo() + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Item não encontrado !'/></ret>");
			}		
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Stib_Item_A_InspecionarED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stib_Item_A_InspecionarED edVolta = new Stib_Item_A_InspecionarED();
			edVolta = (Stib_Item_A_InspecionarED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Item_A_Inspecionar ='" + edVolta.getOid_Item_A_Inspecionar() + "' ";
				saida += "cd_Item_A_Inspecionar ='" + edVolta.getCd_Item_A_Inspecionar() + "' ";
				saida += "nr_Ordem ='" + FormataValor.formataValorBT(edVolta.getNr_Ordem(),0) + "' ";
				saida += "nm_Item_A_Inspecionar ='" + edVolta.getNm_Item_A_Inspecionar() + "' ";
				saida += "nm_Item_A_Inspecionar_E ='" + edVolta.getNm_Item_A_Inspecionar_E() + "' ";
				saida += "dm_Tipo ='" + edVolta.getDm_Tipo() + "' ";
				saida += "nm_Tipo ='" + edVolta.getNm_Tipo() + "' ";
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

	public boolean checkEmUso ( Stib_Item_A_InspecionarED ed ) throws Excecoes {
		try {
			boolean achei=false;
			// Procura na recapagem
			this.inicioTransacao();
			Stib_Item_A_InspecionarED recED = new Stib_Item_A_InspecionarED();
			recED.setOid_Item_A_Inspecionar(ed.getOid_Item_A_Inspecionar());
			//achei=(new Recapagem_GarantidaRN(this.sql).lista(recED).size()>0 ? true : false);
			achei=true;
			return (achei); 
		} finally {
			this.fimTransacao(false);
		}
	}

}
