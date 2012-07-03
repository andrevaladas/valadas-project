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

import com.master.bd.Percentual_ReposicaoBD;
import com.master.ed.Percentual_ReposicaoED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial Percentual de Reposicao GTT
 * @serialData 06/2007
 */

public class Percentual_ReposicaoRN extends Transacao {

	public Percentual_ReposicaoED inclui (Percentual_ReposicaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Percentual_ReposicaoED toReturn = new Percentual_ReposicaoBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Percentual_ReposicaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Percentual_ReposicaoBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public ArrayList lista (Percentual_ReposicaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Percentual_ReposicaoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Percentual_ReposicaoED getByRecord (Percentual_ReposicaoED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Percentual_ReposicaoBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public void relatorio(Percentual_ReposicaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList lista = new Percentual_ReposicaoBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt008"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (ed.getNr_Perc_Busca()>0)
				nm_Filtro+=" Percentual=" + ed.getNr_Perc_Busca();
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
    	Percentual_ReposicaoED ed = (Percentual_ReposicaoED)Obj;
    	ed.setRequest(request);
	//if ("1".equals(rel)) {
		this.relatorio(ed, request, response);	
	//} 
    }	

	
	/**
	 * processaOL
	 * Processa solicitação de tela OL retornando sempre arquivo XML com a seguinte estrutura.
	 * <cad>
	 * 		<tem campo=valor />
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
		Percentual_ReposicaoED ed = (Percentual_ReposicaoED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		
		if ("I".equals(acao) ) {
			//ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' /></ret>");
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			Percentual_ReposicaoED prED = this.getByRecord(ed);
			if (prED.getOid_Percentual_Reposicao() > 0) {
				out.println("<cad>");
				out.println(this.montaRegistro(prED));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Percentuais não encontrados!' /></ret>");
		} else	
		if ("D".equals(acao)) {
			//this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
			out.println("<cad>");
			ArrayList lst = new ArrayList();
			lst = this.lista(ed);
			for (int i=0; i<lst.size(); i++){
				Percentual_ReposicaoED edVolta = new Percentual_ReposicaoED();
				edVolta = (Percentual_ReposicaoED)lst.get(i);
				out.println(this.montaRegistro(edVolta));
			}
			out.println("</cad>");
		}
		out.flush();
		out.close();
	}

	/**
	 * @param out
	 * @return
	 */
	private String montaRegistro(Percentual_ReposicaoED prED ) {
		String saida;
		saida = "<item ";
		saida += "oid_Percentual_Reposicao='" + prED.getOid_Percentual_Reposicao() + "' ";
		saida += "nr_Perc_Desgaste_De='" + FormataValor.formataValorBT(prED.getNr_Perc_Desgaste_De(),1) + "' ";
		saida += "nr_Perc_Desgaste_Ate='" + FormataValor.formataValorBT(prED.getNr_Perc_Desgaste_Ate(),1) + "' ";
		saida += "nr_Perc_Reforma='" + FormataValor.formataValorBT(prED.getNr_Perc_Reforma(),1) + "' ";
		saida += "nr_Perc_Carcaca_Vida_1='" + FormataValor.formataValorBT(prED.getNr_Perc_Carcaca_Vida_1(),1) + "' ";
		saida += "nr_Perc_Carcaca_Vida_2='" + FormataValor.formataValorBT(prED.getNr_Perc_Carcaca_Vida_2(),1) + "' ";
		saida += "nr_Perc_Carcaca_Vida_3='" + FormataValor.formataValorBT(prED.getNr_Perc_Carcaca_Vida_3(),1) + "' ";
		saida += "msg_Stamp='" + prED.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}

}
