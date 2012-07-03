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

import com.master.bd.Stib_ParametroBD;
import com.master.ed.Stib_ParametroED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;
/**
 * @author Régis Steigleder
 * @serial Parametros STIB
 * @serialData 04/2012
 */
public class Stib_ParametroRN extends Transacao {
	
	public Stib_ParametroRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stib_ParametroRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public void altera (Stib_ParametroED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_ParametroBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}


	public Stib_ParametroED getByRecord (Stib_ParametroED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stib_ParametroBD (this.sql).getByRecord (ed);
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
		Stib_ParametroED ed = (Stib_ParametroED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			
			Stib_ParametroED edVolta = this.getByRecord(ed);
			String saida = "<item ";
			saida += "nm_Parametro	='" + edVolta.getNm_Parametro() + "' ";
			saida += "nr_Percentual_Faixa1_De='" + edVolta.getNr_Percentual_Faixa1_De() + "' ";
			saida += "nr_Percentual_Faixa1_Ate='" + edVolta.getNr_Percentual_Faixa1_Ate() + "' ";
			saida += "nr_Percentual_Faixa2_De='" + edVolta.getNr_Percentual_Faixa2_De() + "' ";
			saida += "nr_Percentual_Faixa2_Ate='" + edVolta.getNr_Percentual_Faixa2_Ate() + "' ";			
			saida += "nr_Percentual_Faixa3_De='" + edVolta.getNr_Percentual_Faixa3_De() + "' ";
			saida += "nr_Percentual_Faixa3_Ate='" + edVolta.getNr_Percentual_Faixa3_Ate() + "' ";			
			saida += "/>";
			out.println("<cad>");
			out.println(saida);
			out.println("</cad>");
		}
		out.flush();
		out.close();
	}

}