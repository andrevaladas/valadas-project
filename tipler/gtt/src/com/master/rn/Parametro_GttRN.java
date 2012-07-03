/*
 * Created on 12/11/2004
 *
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Parametro_GttBD;
import com.master.ed.Parametro_GttED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.Transacao;

/**
 * @author Régis Steigleder
 * @serial Parametros GTT
 * @serialData 06/2007
 */

public class Parametro_GttRN extends Transacao {

	public Parametro_GttED inclui (Parametro_GttED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Parametro_GttED toReturn = new Parametro_GttBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Parametro_GttED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Parametro_GttBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public Parametro_GttED getByRecord (Parametro_GttED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Parametro_GttBD (this.sql).getByRecord (ed);
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
	public void processaOL(String acao, Object Obj, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException, Excecoes {
		//Extrai o bean com os campos da request colados
		Parametro_GttED ed = (Parametro_GttED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		
		if ("A".equals(acao)) { 
			Parametro_GttED prED = this.getByRecord(ed);
			if (prED.getOid_Parametro_Gtt() > 0) 
				acao="A"; 	// Se encontrou vai atualizar
			else
				acao="I";	// Se não encontrou vai incluir
		}
		
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' /></ret>");
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			Parametro_GttED paED = this.getByRecord(ed);
			if (paED.getOid_Parametro_Gtt() > 0) {
				out.println("<cad>");
				out.println(this.montaRegistro(paED));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Parametros não encontrados!' /></ret>");
		}	
		out.flush();
		out.close();
	}

	/**
	 * @param out
	 * @return
	 */
	private String montaRegistro(Parametro_GttED paED ) {
		String saida;
		saida = "<item ";
		saida += "oid_Parametro_Gtt='" + paED.getOid_Parametro_Gtt() + "' ";
		saida += "nr_Twi='" + FormataValor.formataValorBT(paED.getNr_Twi(),1) + "' ";
		saida += "nr_Perc_Perda_0_30='" + FormataValor.formataValorBT(paED.getNr_Perc_Perda_0_30(),1) + "' ";
		saida += "nr_Perc_Perda_31_60='" + FormataValor.formataValorBT(paED.getNr_Perc_Perda_31_60(),1) + "' ";
		saida += "nr_Perc_Perda_61_90='" + FormataValor.formataValorBT(paED.getNr_Perc_Perda_61_90(),1) + "' ";
		saida += "nr_Perc_Perda_91_120='" + FormataValor.formataValorBT(paED.getNr_Perc_Perda_91_120(),1) + "' ";
		saida += "msg_Stamp='" + paED.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}

}
