package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_ParametroBD;
import com.master.ed.Stif_ParametroED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.Transacao;

/**
 * @author André Valadas
 * @serial Parametros STIF
 * @serialData 06/20012
 */
public class Stif_ParametroRN extends Transacao {

	public Stif_ParametroED inclui(Stif_ParametroED ed) throws Excecoes {
		inicioTransacao();
		try {
			Stif_ParametroED toReturn = new Stif_ParametroBD(this.sql).inclui(ed);
			fimTransacao(true);
			return toReturn;
		} catch (Excecoes e) {
			abortaTransacao();
			throw e;
		}
	}

	public void alteraCotacao(Stif_ParametroED ed) throws Excecoes {
		inicioTransacao();
		try {
			new Stif_ParametroBD(this.sql).alteraCotacao(ed);
			fimTransacao(true);
		} catch (Excecoes e) {
			abortaTransacao();
			throw e;
		}
	}
	public void alteraIncidencias(Stif_ParametroED ed) throws Excecoes {
		inicioTransacao();
		try {
			new Stif_ParametroBD(this.sql).alteraIncidencias(ed);
			fimTransacao(true);
		} catch (Excecoes e) {
			abortaTransacao();
			throw e;
		}
	}

	public Stif_ParametroED getByRecord(Stif_ParametroED ed) throws Excecoes {
		inicioTransacao();
		try {
			return new Stif_ParametroBD(this.sql).getByRecord(ed);
		} finally {
			fimTransacao(false);
		}
	}

	/**
	 * processaOL Processa solicitação de tela OL retornando sempre arquivo XML
	 * com a seguinte estrutura. <cad> <item campo=valor /> </cad>
	 * 
	 * @param acao
	 * @param Obj
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws Excecoes
	 */
	public void processaOL(String acao, Object Obj, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Excecoes {
		// Extrai o bean com os campos da request colados
		Stif_ParametroED ed = (Stif_ParametroED) Obj;
		// Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");

		if (acao.startsWith("A")) {
			Stif_ParametroED prED = this.getByRecord(ed);
			if (prED.getOid_stif_parametro() < 1) {
				acao = "I"; // Se não encontrou vai incluir
			}
		}

		if ("I".equals(acao)) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' /></ret>");
		} else if ("AC".equals(acao)) {
			ed.setDm_Stamp("A");
			this.alteraCotacao(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else if ("AI".equals(acao)) {
			ed.setDm_Stamp("A");
			this.alteraIncidencias(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else if ("C".equals(acao)) {
			Stif_ParametroED paED = this.getByRecord(ed);
			if (paED.getOid_stif_parametro() > 0) {
				out.println("<cad>");
				out.println(this.montaRegistro(paED));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Parametros não encontrados!' /></ret>");
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * @param out
	 * @return
	 */
	private String montaRegistro(Stif_ParametroED ed) {
		String saida;
		saida = "<item ";
		saida += "oid_stif_parametro='" + ed.getOid_stif_parametro() + "' ";

		saida += "vl_pneu_novo = '" + FormataValor.formataValorBT(ed.getVl_pneu_novo(), 2) + "' ";
		saida += "vl_pneu_r1 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r1(), 2) + "' ";
		saida += "vl_pneu_r2 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r2(), 2) + "' ";
		saida += "vl_pneu_r3 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r3(), 2) + "' ";
		saida += "vl_pneu_r4 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r4(), 2) + "' ";

		saida += "pc_peso_0_5 = '" + FormataValor.formataValorBT(ed.getPc_peso_0_5(), 2) + "' ";
		saida += "pc_peso_6_10 = '" + FormataValor.formataValorBT(ed.getPc_peso_6_10(), 2) + "' ";
		saida += "pc_peso_11_15 = '" + FormataValor.formataValorBT(ed.getPc_peso_11_15(), 2) + "' ";
		saida += "pc_peso_16_20 = '" + FormataValor.formataValorBT(ed.getPc_peso_16_20(), 2) + "' ";
		saida += "pc_peso_21_25 = '" + FormataValor.formataValorBT(ed.getPc_peso_21_25(), 2) + "' ";
		saida += "pc_peso_26_30 = '" + FormataValor.formataValorBT(ed.getPc_peso_26_30(), 2) + "' ";
		saida += "pc_peso_31_35 = '" + FormataValor.formataValorBT(ed.getPc_peso_31_35(), 2) + "' ";
		saida += "pc_peso_36_40 = '" + FormataValor.formataValorBT(ed.getPc_peso_36_40(), 2) + "' ";
		saida += "pc_peso_41_45 = '" + FormataValor.formataValorBT(ed.getPc_peso_41_45(), 2) + "' ";
		saida += "pc_peso_46_50 = '" + FormataValor.formataValorBT(ed.getPc_peso_46_50(), 2) + "' ";
		saida += "pc_peso_acima_50 = '" + FormataValor.formataValorBT(ed.getPc_peso_acima_50(), 2) + "' ";

		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}
}