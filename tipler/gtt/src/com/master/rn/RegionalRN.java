/*
 * Created on 12/11/2004
 */
package com.master.rn;

import jFiles.Email;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.RegionalBD;
import com.master.ed.EmpresaED;
import com.master.ed.RegionalED;
import com.master.ed.Modelo_PneuED;
import com.master.util.Excecoes;
import com.master.util.Utilitaria;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial Regionais
 * @serialData 01/2010
 */
public class RegionalRN extends Transacao {

	public RegionalED inclui (RegionalED ed) throws Excecoes {
		try {
			inicioTransacao ();
			RegionalED toReturn = new RegionalBD (sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
		catch (RuntimeException e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (RegionalED ed) throws Excecoes {
		try {
			inicioTransacao ();
			new RegionalBD (sql).altera (ed);
			this.fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
		catch (RuntimeException e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (RegionalED ed) throws Excecoes {
		try {
			inicioTransacao ();
			new RegionalBD (sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
		catch (RuntimeException e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public ArrayList lista (RegionalED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new RegionalBD (sql).lista (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public RegionalED getByRecord (RegionalED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new RegionalBD (sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

    public void relatorio(RegionalED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList<RegionalED> lista = new RegionalBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt009"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNm_Regional()))
				nm_Filtro+=" Descrição=" + ed.getNm_Regional();
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
    	RegionalED ed = (RegionalED)Obj;
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
    	RegionalED ed = (RegionalED)Obj;
    	//Prepara a saída
    	ed.setMasterDetails(request);
    	
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Regional já existente com esta descricao !'/></ret>");
    		} else {
	    		ed = this.inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Regional() + "' /></ret>");
    		}    		
    	} else 
		if ("A".equals(acao)) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Regional já existente com esta descricao !'/></ret>");
    		} else {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
    		}
		} else 
		if ("D".equals(acao)) {
			if (checkEmUso(ed)) {
				out.println("<ret><item oknok='Impossível excluir! Regional em uso!' /></ret>");
			} else {
				this.delete(ed);
				out.println("<ret><item oknok='DOK' /></ret>");
			}			
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList lst = new ArrayList();
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			RegionalED edVolta = new RegionalED();
			edVolta = (RegionalED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Regional='" + edVolta.getOid_Regional() + "' ";
				saida += "cd_Regional='" + edVolta.getCd_Regional() + "' ";
				saida += "nm_Regional='" + edVolta.getNm_Regional() + "' ";
				saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
				saida += "/>";
			} else 			
			if ("CB".equals(acao) || "CBC".equals(acao)) {
				if ( i==0 && "CBC".equals(acao) ) {
					saida = "<item ";
					saida += "value='0'>TODOS</item>";
					out.println(saida);
				}
				saida = "<item ";
				saida += "value='" + edVolta.getOid_Regional() + "'>";
				saida +=  edVolta.getCd_Regional() + " - " + edVolta.getNm_Regional();
				saida += "</item>";
			}
			out.println(saida);
		}
		out.println("</cad>");
		}
    	out.flush();
    	out.close();
    }
    
    public boolean checkDuplo ( RegionalED ed, String acao) throws Excecoes {
    	boolean ret = false;
    	RegionalED edChk = new RegionalED();
    	edChk.setCd_Regional(ed.getCd_Regional());
		edChk.setNm_Regional(ed.getNm_Regional());
		edChk = this.getByRecord(edChk);
    	if ("I".equals(acao) && edChk.getOid_Regional()>0)
    		ret = true;
    	else
        	if ("A".equals(acao) && edChk.getOid_Regional()>0 ) {
    			if (ed.getOid_Regional()!=edChk.getOid_Regional() )
    				ret = true ;
    	}
    	return ret;
    }

    public boolean checkEmUso ( RegionalED ed ) throws Excecoes {
		try {
			EmpresaED empED = new EmpresaED();
			empED.setOid_Regional(ed.getOid_Regional());
			this.inicioTransacao();			
			return (new EmpresaRN(this.sql).lista(empED).size()>0 ? true : false); 
        } finally {
            this.fimTransacao(false);
        }
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}