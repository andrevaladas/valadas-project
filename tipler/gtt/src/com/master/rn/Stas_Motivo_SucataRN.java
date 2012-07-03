package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stas_Motivo_SucataBD;
import com.master.ed.Stas_Motivo_SucataED;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
/**
 * @author Régis Steigleder
 * @serial STAS - Itens a inspecionar
 * @serialData 05/2012
 */
public class Stas_Motivo_SucataRN extends Transacao {
	
	public Stas_Motivo_SucataRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stas_Motivo_SucataRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stas_Motivo_SucataED inclui (Stas_Motivo_SucataED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stas_Motivo_SucataED toReturn = new Stas_Motivo_SucataBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stas_Motivo_SucataED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Motivo_SucataBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Stas_Motivo_SucataED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Motivo_SucataBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Stas_Motivo_SucataED> lista (Stas_Motivo_SucataED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Motivo_SucataBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Stas_Motivo_SucataED getByRecord (Stas_Motivo_SucataED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Motivo_SucataBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	
	public byte[] getImagem(Stas_Motivo_SucataED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Motivo_SucataBD (this.sql).getImagem(ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public void setImagem(String pArquivo) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Motivo_SucataBD(this.sql).setImagem(1,pArquivo);
		}
		finally {
			fimTransacao (true);
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
		Stas_Motivo_SucataED ed = (Stas_Motivo_SucataED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Motivo_Sucata() + "' /></ret>");
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
			ArrayList<Stas_Motivo_SucataED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Stas_Motivo_SucataED edVolta = (Stas_Motivo_SucataED)lst.get(i);
					saida = "<item ";
					saida += "oid_Motivo_Sucata ='" + edVolta.getOid_Motivo_Sucata() + "' ";
					saida += "cd_Motivo_Sucata ='" + edVolta.getCd_Motivo_Sucata() + "' ";
					saida += "nm_Motivo_Sucata ='" + edVolta.getNm_Motivo_Sucata() + "' ";
					saida += "tx_Motivo_Sucata ='" + edVolta.getTx_Motivo_Sucata() + "' ";
					saida += "tx_Recomendacao ='" + edVolta.getTx_Recomendacao() + "' ";
					saida += "nm_Motivo_Sucata_E ='" + edVolta.getNm_Motivo_Sucata_E() + "' ";
					saida += "tx_Motivo_Sucata_E ='" + edVolta.getTx_Motivo_Sucata_E() + "' ";
					saida += "tx_Recomendacao_E ='" + edVolta.getTx_Recomendacao_E() + "' ";					
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
		ArrayList<Stas_Motivo_SucataED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stas_Motivo_SucataED edVolta = new Stas_Motivo_SucataED();
			edVolta = (Stas_Motivo_SucataED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Motivo_Sucata ='" + edVolta.getOid_Motivo_Sucata() + "' ";
				saida += "cd_Motivo_Sucata ='" + edVolta.getCd_Motivo_Sucata() + "' ";
				saida += "nm_Motivo_Sucata ='" + edVolta.getNm_Motivo_Sucata() + "' ";
				saida += "tx_Motivo_Sucata ='" + edVolta.getTx_Motivo_Sucata() + "' ";
				saida += "tx_Recomendacao ='" + edVolta.getTx_Recomendacao() + "' ";
				saida += "nm_Motivo_Sucata_E ='" + edVolta.getNm_Motivo_Sucata_E() + "' ";
				saida += "tx_Motivo_Sucata_E ='" + edVolta.getTx_Motivo_Sucata_E() + "' ";
				saida += "tx_Recomendacao_E ='" + edVolta.getTx_Recomendacao_E() + "' ";
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

	public boolean checkEmUso ( Stas_Motivo_SucataED ed ) throws Excecoes {
		try {
			boolean achei=false;
			// Procura na recapagem
			this.inicioTransacao();
			Stas_Motivo_SucataED recED = new Stas_Motivo_SucataED();
			recED.setOid_Motivo_Sucata(ed.getOid_Motivo_Sucata());
			//achei=(new Recapagem_GarantidaRN(this.sql).lista(recED).size()>0 ? true : false);
			achei=true;
			return (achei); 
		} finally {
			this.fimTransacao(false);
		}
	}

}
