package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_Perda_PercentualBD;
import com.master.ed.Stif_Perda_PercentualED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial Perda Percentual
 * @since 06/2012
 */
public class Stif_Perda_PercentualRN extends Transacao {

	public Stif_Perda_PercentualRN() {
		super();
	}

	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stif_Perda_PercentualRN(ExecutaSQL executasql) {
		super(executasql);
	}

    public Stif_Perda_PercentualED inclui(Stif_Perda_PercentualED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            ed = new Stif_Perda_PercentualBD(this.sql).inclui(ed);
            this.fimTransacao(true);
            return ed;
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

    public void altera(Stif_Perda_PercentualED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_Perda_PercentualBD(this.sql).altera(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void deleta(Stif_Perda_PercentualED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_Perda_PercentualBD(this.sql).deleta(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

     public List<Stif_Perda_PercentualED> lista(Stif_Perda_PercentualED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            List<Stif_Perda_PercentualED> lista = new Stif_Perda_PercentualBD(sql).lista(ed);
            return lista;
        } finally {
            this.fimTransacao(false);
        }
    }
    
    public Stif_Perda_PercentualED getByRecord(Stif_Perda_PercentualED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_Perda_PercentualBD(this.sql).getByRecord(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

    public void relatorio(Stif_Perda_PercentualED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            List<Stif_Perda_PercentualED> lista = new Stif_Perda_PercentualBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de Stif_Perda_Percentuals no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("tif007"); // Seta o nome do relatório
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
    	Stif_Perda_PercentualED ed = (Stif_Perda_PercentualED)Obj;
    	ed.setRequest(request);
    	this.relatorio(ed, request, response);	
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
    	Stif_Perda_PercentualED ed = (Stif_Perda_PercentualED)Obj;
    	
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='Registro já existente com esta pressão padrão!'/></ret>");
    		} else {
    			ed = this.inclui(ed);
    			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Perda_Percentual() + "' /></ret>");
    		}
    	} else 
		if ("A".equals(acao)) {
    		if (checkDuplo(ed,acao) ) { 
    			out.println("<ret><item oknok='Registro já existente com esta pressão padrão!'/></ret>");
    		} else {
    			this.altera(ed);
    			out.println("<ret><item oknok='AOK' /></ret>");
    		}
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			Stif_Perda_PercentualED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Perda_Percentual() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Registro não encontrado!' /></ret>");
		} else {
			out.println("<cad>");
			final List<Stif_Perda_PercentualED> list = this.lista(ed);
			for (Stif_Perda_PercentualED toReturn : list) {
				if ("L".equals(acao)) {
					out.println(montaRegistro(toReturn));
				} else {
					String combobox = "<item value='" + toReturn.getOid_Perda_Percentual() + "'>";
					combobox += FormataValor.formataValorBT(toReturn.getPc_perda_padrao(),0);
					combobox += "</item>";
					out.println(combobox);
				}
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
    private String montaRegistro(Stif_Perda_PercentualED ed){
    	String saida;
		saida = "<item ";
		saida += "oid_Perda_Percentual='" + ed.getOid_Perda_Percentual() + "' ";
		saida += "pc_perda_padrao = '" + FormataValor.formataValorBT(ed.getPc_perda_padrao(),0) + "' "; 
		saida += "pc_perda_20 = '" + FormataValor.formataValorBT(ed.getPc_perda_20(),2) + "' ";
		saida += "pc_perda_25 = '" + FormataValor.formataValorBT(ed.getPc_perda_25(),2) + "' ";
		saida += "pc_perda_30 = '" + FormataValor.formataValorBT(ed.getPc_perda_30(),2) + "' ";
		saida += "pc_perda_35 = '" + FormataValor.formataValorBT(ed.getPc_perda_35(),2) + "' ";
		saida += "pc_perda_40 = '" + FormataValor.formataValorBT(ed.getPc_perda_40(),2) + "' ";
		saida += "pc_perda_45 = '" + FormataValor.formataValorBT(ed.getPc_perda_45(),2) + "' ";
		saida += "pc_perda_50 = '" + FormataValor.formataValorBT(ed.getPc_perda_50(),2) + "' ";
		saida += "pc_perda_55 = '" + FormataValor.formataValorBT(ed.getPc_perda_55(),2) + "' ";
		saida += "pc_perda_60 = '" + FormataValor.formataValorBT(ed.getPc_perda_60(),2) + "' ";
		saida += "pc_perda_65 = '" + FormataValor.formataValorBT(ed.getPc_perda_65(),2) + "' ";
		saida += "pc_perda_70 = '" + FormataValor.formataValorBT(ed.getPc_perda_70(),2) + "' ";
		saida += "pc_perda_75 = '" + FormataValor.formataValorBT(ed.getPc_perda_75(),2) + "' ";
		saida += "pc_perda_80 = '" + FormataValor.formataValorBT(ed.getPc_perda_80(),2) + "' ";
		saida += "pc_perda_85 = '" + FormataValor.formataValorBT(ed.getPc_perda_85(),2) + "' ";
		saida += "pc_perda_90 = '" + FormataValor.formataValorBT(ed.getPc_perda_90(),2) + "' ";
		saida += "pc_perda_95 = '" + FormataValor.formataValorBT(ed.getPc_perda_95(),2) + "' ";
		saida += "pc_perda_100 = '" + FormataValor.formataValorBT(ed.getPc_perda_100(),2) + "' ";
		saida += "pc_perda_105 = '" + FormataValor.formataValorBT(ed.getPc_perda_105(),2) + "' ";
		saida += "pc_perda_110 = '" + FormataValor.formataValorBT(ed.getPc_perda_110(),2) + "' ";
		saida += "pc_perda_115 = '" + FormataValor.formataValorBT(ed.getPc_perda_115(),2) + "' ";
		saida += "pc_perda_120 = '" + FormataValor.formataValorBT(ed.getPc_perda_120(),2) + "' ";
		saida += "pc_perda_125 = '" + FormataValor.formataValorBT(ed.getPc_perda_125(),2) + "' ";
		saida += "pc_perda_130 = '" + FormataValor.formataValorBT(ed.getPc_perda_130(),2) + "' ";
		saida += "pc_perda_135 = '" + FormataValor.formataValorBT(ed.getPc_perda_135(),2) + "' ";
		saida += "pc_perda_140 = '" + FormataValor.formataValorBT(ed.getPc_perda_140(),2) + "' ";
		saida += "pc_perda_145 = '" + FormataValor.formataValorBT(ed.getPc_perda_145(),2) + "' ";
		saida += "pc_perda_150 = '" + FormataValor.formataValorBT(ed.getPc_perda_150(),2) + "' ";

		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
    }
	
    public boolean checkDuplo(Stif_Perda_PercentualED ed, String acao) throws Excecoes {
    	Stif_Perda_PercentualED check = new Stif_Perda_PercentualED();
		check.setPc_perda_padrao(ed.getPc_perda_padrao());
    	check.setOid_Empresa(ed.getOid_Empresa());
    	check = this.getByRecord(check);
    	if ("I".equals(acao) && check.getOid_Perda_Percentual() > 0) {
    		return true;
    	} else if ("A".equals(acao) && check.getOid_Perda_Percentual() > 0) {
			if (ed.getOid_Perda_Percentual() != check.getOid_Perda_Percentual()) {
				return true;
			}
    	}
    	return false;
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}