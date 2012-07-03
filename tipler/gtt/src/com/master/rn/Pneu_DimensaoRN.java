/*
 * Created on 13/05/2012
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Pneu_DimensaoBD;
import com.master.ed.Pneu_DimensaoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial Pneus Dimensoes
 * @serialData 05/2012
 */
public class Pneu_DimensaoRN extends Transacao {

	public Pneu_DimensaoED inclui(Pneu_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Pneu_DimensaoED toReturn = new Pneu_DimensaoBD (sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera(Pneu_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Pneu_DimensaoBD (sql).altera (ed);
			this.fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Pneu_DimensaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Pneu_DimensaoBD (sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Pneu_DimensaoED> lista (Pneu_DimensaoED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Pneu_DimensaoBD (sql).lista (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public Pneu_DimensaoED getByRecord (Pneu_DimensaoED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Pneu_DimensaoBD (sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

    public void relatorio(Pneu_DimensaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList<Pneu_DimensaoED> lista = new Pneu_DimensaoBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("ban020"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			BancoUtil bu = new BancoUtil();
			if (bu.doValida(ed.getNm_Pneu_Dimensao()))
				nm_Filtro+=" Descrição=" + ed.getNm_Pneu_Dimensao();
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
    	Pneu_DimensaoED ed = (Pneu_DimensaoED)Obj;
    	ed.setRequest(request);
		this.relatorio(ed, request, response);	
    }	
	
	/**
     * processaOL
     * Processa colicitação de tela OL retornando sempre arquivo XML com a seguinte estrutura.
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
    	Pneu_DimensaoED ed = (Pneu_DimensaoED)Obj;
    	//Prepara a saída
    	ed.setMasterDetails(request);
    	
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Dimensão já existente com esta descrição !'/></ret>");
    		} else {
	    		ed = this.inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Pneu_Dimensao() + "' /></ret>");
    		}    		
    	} else 
		if ("A".equals(acao)) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Dimensão já existente com esta descrição !'/></ret>");
    		} else {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
    		}
		} else 
		if ("D".equals(acao)) {
			if (checkEmUso(ed)) {
				out.println("<ret><item oknok='Impossível excluir! Dimensção em uso!' /></ret>");
			} else {
				this.delete(ed);
				out.println("<ret><item oknok='DOK' /></ret>");
			}	
		} else 	
		if ("lookup".equals(acao)) {
			ArrayList<Pneu_DimensaoED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Pneu_DimensaoED edVolta = (Pneu_DimensaoED)lst.get(i);
					saida = "<item ";
					saida += "oid_Pneu_Dimensao ='" + edVolta.getOid_Pneu_Dimensao() + "' ";
					saida += "cd_Pneu_Dimensao ='" + edVolta.getCd_Pneu_Dimensao() + "' ";
					saida += "nm_Pneu_Dimensao ='" + edVolta.getNm_Pneu_Dimensao() + "' ";
					saida += "nr_Mm_Perimetro ='" + edVolta.getNr_Mm_Perimetro() + "' ";
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
		ArrayList<Pneu_DimensaoED> lst = new ArrayList<Pneu_DimensaoED>();
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Pneu_DimensaoED edVolta = new Pneu_DimensaoED();
			edVolta = (Pneu_DimensaoED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Pneu_Dimensao='" + edVolta.getOid_Pneu_Dimensao() + "' ";
				saida += "cd_Pneu_Dimensao='" + edVolta.getCd_Pneu_Dimensao() + "' ";
				saida += "nm_Pneu_Dimensao='" + edVolta.getNm_Pneu_Dimensao() + "' ";
				saida += "nr_Mm_Perimetro ='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Perimetro(), 1) + "' ";
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
    
    public boolean checkDuplo ( Pneu_DimensaoED ed, String acao) throws Excecoes {
    	boolean ret = false;
    	Pneu_DimensaoED edChk = new Pneu_DimensaoED();
		edChk.setNm_Pneu_Dimensao(ed.getNm_Pneu_Dimensao());
		edChk = this.getByRecord(edChk);
    	if ("I".equals(acao) && edChk.getOid_Pneu_Dimensao()>0)
    		ret = true;
    	else
        	if ("A".equals(acao) && edChk.getOid_Pneu_Dimensao()>0 ) {
    			if (ed.getOid_Pneu_Dimensao()!=edChk.getOid_Pneu_Dimensao() )
    				ret = true ;
    	}
    	return ret;
    }

    public boolean checkEmUso ( Pneu_DimensaoED ed ) throws Excecoes {
		try {
			//BandaED bandaED = new BandaED();
			//bandaED.setOid_Pneu_Dimensao(ed.getOid_Pneu_Dimensao());
			this.inicioTransacao();
			//return (new BandaBD(this.sql).lista(bandaED).size()>0 ? true : false); 
			return true;
        } finally {
            this.fimTransacao(false);
        }
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}