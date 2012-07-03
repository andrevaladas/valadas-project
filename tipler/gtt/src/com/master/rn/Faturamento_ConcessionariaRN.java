/*
 * Created on 10/06/2009
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Faturamento_ConcessionariaBD;
import com.master.ed.Faturamento_ConcessionariaED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial Fabricantes de pneus
 * @serialData 06/2009
 */
public class Faturamento_ConcessionariaRN extends Transacao {

	
	public Faturamento_ConcessionariaRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Faturamento_ConcessionariaRN(ExecutaSQL executasql) {
		super(executasql);
	}

	public Faturamento_ConcessionariaED inclui (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			inicioTransacao ();
			Faturamento_ConcessionariaED toReturn = new Faturamento_ConcessionariaBD (sql).inclui (ed);
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

	public void altera (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			inicioTransacao ();
			new Faturamento_ConcessionariaBD (sql).altera (ed);
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

	public void delete (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			inicioTransacao ();
			new Faturamento_ConcessionariaBD (sql).delete (ed);
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
	
	public ArrayList<Faturamento_ConcessionariaED> lista (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Faturamento_ConcessionariaBD (sql).lista (ed);
		} finally {
			fimTransacao (false);
		}
	}

	public Faturamento_ConcessionariaED getByRecord (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Faturamento_ConcessionariaBD (sql).getByRecord (ed);
		} finally {
			fimTransacao (false);
		}
	}
	
	public Faturamento_ConcessionariaED getUltimo(Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Faturamento_ConcessionariaBD (sql).getUltimo(ed);
		} finally {
			fimTransacao (false);
		}
	}


    public void relatorio(Faturamento_ConcessionariaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList<Faturamento_ConcessionariaED> lista = new Faturamento_ConcessionariaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt006"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
//			if (bu.doValida(ed.getNM_Faturamento_Concessionaria()))
//				nm_Filtro+=" Descrição=" + ed.getNM_Faturamento_Concessionaria();
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
		Faturamento_ConcessionariaED ed = (Faturamento_ConcessionariaED)Obj;
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
    	Faturamento_ConcessionariaED ed = (Faturamento_ConcessionariaED)Obj;
    	//Prepara a saída
    	ed.setMasterDetails(request);
    	
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Faturamento já existente para este mês !'/></ret>");
    		} else {
	    		ed = this.inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Faturamento_Concessionaria() + "' /></ret>");
    		}    		
    	} else 
		if ("A".equals(acao)) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Faturamento já existente para este mês !'/></ret>");
    		} else {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
    		}
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Faturamento_ConcessionariaED> lst = new ArrayList<Faturamento_ConcessionariaED>();
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Faturamento_ConcessionariaED edVolta = new Faturamento_ConcessionariaED();
			edVolta = (Faturamento_ConcessionariaED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Faturamento_Concessionaria='" + edVolta.getOid_Faturamento_Concessionaria() + "' ";
				saida += "oid_Concessionaria='" + edVolta.getOid_Concessionaria() + "' ";
				saida += "dm_Mes_Ano='" + edVolta.getDm_Mes_Ano() + "' ";
				saida += "vl_Faturamento='" + FormataValor.formataValorBT(edVolta.getVl_Faturamento(),2) + "' ";
				saida += "nm_Razao_Social='" + edVolta.getNm_Razao_Social() + "' ";
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
    
    public boolean checkDuplo ( Faturamento_ConcessionariaED ed, String acao) throws Excecoes {
    	boolean ret = false;
    	Faturamento_ConcessionariaED edChk = new Faturamento_ConcessionariaED();
		edChk.setOid_Concessionaria(ed.getOid_Concessionaria());
		edChk.setDm_Mes_Ano(ed.getDm_Mes_Ano());
		edChk = this.getByRecord(edChk);
    	if ("I".equals(acao) && edChk.getOid_Faturamento_Concessionaria()>0)
    		ret = true;
    	else
        	if ("A".equals(acao) && edChk.getOid_Faturamento_Concessionaria()>0 ) {
    			if (ed.getOid_Faturamento_Concessionaria()!=edChk.getOid_Faturamento_Concessionaria() )
    				ret = true ;
    	}
    	return ret;
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}