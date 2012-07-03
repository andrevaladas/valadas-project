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

import com.master.bd.Pneu_NovoBD;
import com.master.ed.Pneu_NovoED;
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
public class Pneu_NovoRN extends Transacao {

	public Pneu_NovoED inclui (Pneu_NovoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Pneu_NovoED toReturn = new Pneu_NovoBD (sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Pneu_NovoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Pneu_NovoBD (sql).altera (ed);
			this.fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Pneu_NovoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Pneu_NovoBD (sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Pneu_NovoED> lista (Pneu_NovoED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Pneu_NovoBD (sql).lista (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public Pneu_NovoED getByRecord (Pneu_NovoED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Pneu_NovoBD (sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

    public void relatorio(Pneu_NovoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList<Pneu_NovoED> lista = new Pneu_NovoBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("ban020"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			BancoUtil bu = new BancoUtil();
			//if (bu.doValida(ed.getNm_Pneu_Novo()))
			//	nm_Filtro+=" Descrição=" + ed.getNm_Pneu_Novo();
			//ed.setDescFiltro(nm_Filtro);
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
    	Pneu_NovoED ed = (Pneu_NovoED)Obj;
    	ed.setRequest(request);
	//if ("1".equals(rel)) {
		this.relatorio(ed, request, response);	
	//} 
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
    	Pneu_NovoED ed = (Pneu_NovoED)Obj;
    	//Prepara a saída
    	ed.setMasterDetails(request);
    	
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Pneu já existente com este código !'/></ret>");
    		} else {
	    		ed = this.inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Pneu_Novo() + "' /></ret>");
    		}    		
    	} else 
		if ("A".equals(acao)) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Pneu já existente com este código !'/></ret>");
    		} else {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
    		}
		} else 
		if ("D".equals(acao)) {
			if (checkEmUso(ed)) {
				out.println("<ret><item oknok='Impossível excluir! Pneu em uso!' /></ret>");
			} else {
				this.delete(ed);
				out.println("<ret><item oknok='DOK' /></ret>");
			}	
		} else 	
		if ("lookup".equals(acao)) {
			ArrayList<Pneu_NovoED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Pneu_NovoED edVolta = (Pneu_NovoED)lst.get(i);
					saida = "<item ";
					saida += "oid_Pneu_Novo ='" + edVolta.getOid_Pneu_Novo() + "' ";
					saida += "nm_Pneu_Novo ='" + edVolta.getNm_Fabricante_Pneu() + "' ";
					saida += "nm_Pneu_Novo ='" + edVolta.getNm_Modelo_Pneu()+ "' ";
					saida += "nr_Mm_Profundidade ='" + edVolta.getNr_Mm_Profundidade() + "' ";
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
		ArrayList<Pneu_NovoED> lst = new ArrayList<Pneu_NovoED>();
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Pneu_NovoED edVolta = new Pneu_NovoED();
			edVolta = (Pneu_NovoED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Pneu_Novo='" + edVolta.getOid_Pneu_Novo() + "' ";
				saida += "cd_Pneu_Novo='" + edVolta.getCd_Pneu_Novo() + "' ";
				saida += "oid_Fabricante_Pneu='" + edVolta.getOid_Fabricante_Pneu() + "' ";
				saida += "nm_Fabricante_Pneu='" + edVolta.getNm_Fabricante_Pneu() + "' ";
				saida += "oid_Modelo_Pneu='" + edVolta.getOid_Modelo_Pneu() + "' ";
				saida += "cd_Modelo_Pneu='" + edVolta.getCd_Modelo_Pneu() + "' ";
				saida += "nm_Modelo_Pneu='" + edVolta.getNm_Modelo_Pneu() + "' ";
				saida += "oid_Pneu_Dimensao='" + edVolta.getOid_Pneu_Dimensao() + "' ";
				saida += "cd_Pneu_Dimensao='" + edVolta.getCd_Pneu_Dimensao() + "' ";
				saida += "nm_Pneu_Dimensao='" + edVolta.getNm_Pneu_Dimensao() + "' ";
				saida += "nr_Mm_Profundidade ='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Profundidade(), 1) + "' ";
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
    
    public boolean checkDuplo ( Pneu_NovoED ed, String acao) throws Excecoes {
    	boolean ret = false;
    	Pneu_NovoED edChk = new Pneu_NovoED();
		edChk.setCd_Pneu_Novo(ed.getCd_Pneu_Novo());
		edChk = this.getByRecord(edChk);
    	if ("I".equals(acao) && edChk.getOid_Pneu_Novo()>0)
    		ret = true;
    	else
        	if ("A".equals(acao) && edChk.getOid_Pneu_Novo()>0 ) {
    			if (ed.getOid_Pneu_Novo()!=edChk.getOid_Pneu_Novo() )
    				ret = true ;
    	}
    	return ret;
    }

    public boolean checkEmUso ( Pneu_NovoED ed ) throws Excecoes {
		try {
			//BandaED bandaED = new BandaED();
			//bandaED.setOid_Pneu_Novo(ed.getOid_Pneu_Novo());
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