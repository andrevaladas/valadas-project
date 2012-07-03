package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_ConstatacaoBD;
import com.master.ed.Stif_ConstatacaoED;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial STIF - Constatações
 * @since 06/2012
 */
public class Stif_ConstatacaoRN extends Transacao {

	public Stif_ConstatacaoRN() {
		super();
	}

	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stif_ConstatacaoRN(ExecutaSQL executasql) {
		super(executasql);
	}

	public Stif_ConstatacaoED inclui(Stif_ConstatacaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            final Stif_ConstatacaoED edVolta = new Stif_ConstatacaoBD(this.sql).inclui(ed);
            this.fimTransacao(true);
            return edVolta;
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

    public void altera(Stif_ConstatacaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_ConstatacaoBD(this.sql).altera(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void deleta(Stif_ConstatacaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_ConstatacaoBD(this.sql).deleta(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

     public List<Stif_ConstatacaoED> lista(Stif_ConstatacaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_ConstatacaoBD(sql).lista(ed);
        } finally {
            this.fimTransacao(false);
        }
    }
    
    public Stif_ConstatacaoED getByRecord(Stif_ConstatacaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_ConstatacaoBD(this.sql).getByRecord(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

    public void relatorio(Stif_ConstatacaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            List<Stif_ConstatacaoED> lista = new Stif_ConstatacaoBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("tif103"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
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
    	Stif_ConstatacaoED ed = (Stif_ConstatacaoED)Obj;
    	ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorio(ed, request, response);	
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
    	Stif_ConstatacaoED ed = (Stif_ConstatacaoED)Obj;
    	
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao)) {
    		ed = this.inclui(ed);
    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Constatacao() + "' /></ret>");
    	} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
			out.println("<cad>");
			final List<Stif_ConstatacaoED> resultList = this.lista(ed);
			for (Stif_ConstatacaoED edVolta : resultList) {
				out.println(montaRegistro(edVolta));
			}
			out.println("</cad>");
		}
    	out.flush();
    	out.close();
    }

    /**
	 * @param ed
	 * @return
	 */		
    private String montaRegistro(final Stif_ConstatacaoED ed){
    	String saida;
		saida = "<item ";
		saida += "oid_Constatacao='" + ed.getOid_Constatacao() + "' ";
		saida += "oid_Veiculo_Inspecao='" + ed.getOid_Veiculo_Inspecao() + "' ";
		saida += "oid_Perda_Percentual='" + ed.getOid_Perda_Percentual() + "' ";
		saida += "nr_Eixo='" + ed.getNr_Eixo() + "' ";
		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}