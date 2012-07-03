package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_ProblemaBD;
import com.master.ed.Stif_ProblemaED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial STIF Problemas
 * @since 06/2012
 */
public class Stif_ProblemaRN extends Transacao {

	public Stif_ProblemaED inclui(Stif_ProblemaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stif_ProblemaED toReturn = new Stif_ProblemaBD (sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		} catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stif_ProblemaED ed) throws Excecoes {
		inicioTransacao();
		try {
			new Stif_ProblemaBD (sql).altera (ed);
			this.fimTransacao (true);
		} catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (Stif_ProblemaED ed) throws Excecoes {
		inicioTransacao();
		try {
			new Stif_ProblemaBD (sql).delete (ed);
			fimTransacao (true);
		} catch (Excecoes e) {
			abortaTransacao();
			throw e;
		}
	}

	public List<Stif_ProblemaED> lista (Stif_ProblemaED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Stif_ProblemaBD (sql).lista (ed);
		} finally {
			fimTransacao (false);
		}
	}

	public Stif_ProblemaED getByRecord (Stif_ProblemaED ed) throws Excecoes {
		try {
			this.inicioTransacao ();
			return new Stif_ProblemaBD (sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

    public void relatorio(Stif_ProblemaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            List<Stif_ProblemaED> lista = new Stif_ProblemaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("ban021"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (JavaUtil.doValida(ed.getNm_problema())) {
				nm_Filtro+=" Descrição=" + ed.getNm_problema();
			}
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
    	Stif_ProblemaED ed = (Stif_ProblemaED)Obj;
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
    	Stif_ProblemaED ed = (Stif_ProblemaED)Obj;
    	//Prepara a saída
    	ed.setMasterDetails(request);

    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Registro já existente com este código!'/></ret>");
    		} else {
	    		ed = this.inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_stif_problema() + "' /></ret>");
    		}
    	} else 
		if ("A".equals(acao)) {
			if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Registro já existente com esta código!'/></ret>");
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
			List<Stif_ProblemaED> lst = this.lista(ed);
			for (int i=0; i<lst.size(); i++){
				Stif_ProblemaED edVolta = (Stif_ProblemaED)lst.get(i);
				if ("L".equals(acao)) {
					saida = "<item ";
					saida += "oid_stif_problema='" + edVolta.getOid_stif_problema() + "' ";
					saida += "ordem='" + edVolta.getOrdem()+ "' ";
					saida += "cd_problema='" + edVolta.getCd_problema() + "' ";
					saida += "nm_problema='" + edVolta.getNm_problema() + "' ";
					saida += "pc_perda ='" + FormataValor.formataValorBT(edVolta.getPc_perda(), 2) + "' ";
					saida += "dm_tipo='" + edVolta.getDm_tipo() + "' ";
					saida += "dm_problema='" + JavaUtil.getValueDef(edVolta.getDm_problema(), "N") + "' ";
					saida += "checkItem='" + edVolta.isCheckItem() + "' "; //Campo utilizado nas gris de Stif_Pneu_Inspecao - tif104
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
 
    public boolean checkDuplo(Stif_ProblemaED ed, String acao) throws Excecoes {
    	Stif_ProblemaED check = new Stif_ProblemaED();
		check.setCd_problema(ed.getCd_problema());
		check.setDm_tipo(ed.getDm_tipo());
		check = this.getByRecord(check);
    	if ("I".equals(acao) && check.getOid_stif_problema() > 0) {
    		return true;
        } else {
        	if ("A".equals(acao) && check.getOid_stif_problema() >0 ) {
    			if (ed.getOid_stif_problema() != check.getOid_stif_problema()) {
    				return true;
    			}
        	}
    	}
    	return false;
    }
    
    protected void finalize() throws Throwable {
        if (this.sql != null) {
            this.abortaTransacao();
        }
    }
}