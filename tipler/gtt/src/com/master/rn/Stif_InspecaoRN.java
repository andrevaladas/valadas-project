package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_InspecaoBD;
import com.master.bd.Stif_ParametroBD;
import com.master.bd.Stif_Pneu_InspecaoBD;
import com.master.bd.Stif_Veiculo_InspecaoBD;
import com.master.ed.Stif_InspecaoED;
import com.master.ed.Stif_ParametroED;
import com.master.ed.Stif_Pneu_InspecaoED;
import com.master.ed.Stif_Veiculo_InspecaoED;
import com.master.ed.UsuarioED;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.OLUtil;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial STIF - Inspeção
 * @since 06/2012
 */
public class Stif_InspecaoRN extends Transacao {

	public Stif_InspecaoRN() {
		super();
	}

	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stif_InspecaoRN(ExecutaSQL executasql) {
		super(executasql);
	}

    @SuppressWarnings("unchecked")
	public Stif_InspecaoED inclui(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();

            /** Configura parâmetros de cotação Pneu */
            Stif_ParametroED parametro = new Stif_ParametroED();
            parametro.setOid_Empresa(ed.getOid_Empresa());
            parametro = new Stif_ParametroBD(this.sql).getByRecord(parametro);
            if (parametro !=  null) {
            	ed.setVl_pneu_novo(parametro.getVl_pneu_novo());
    			ed.setVl_pneu_r1(parametro.getVl_pneu_r1());
    			ed.setVl_pneu_r2(parametro.getVl_pneu_r2());
    			ed.setVl_pneu_r3(parametro.getVl_pneu_r3());
    			ed.setVl_pneu_r4(parametro.getVl_pneu_r4());
            }

            /** veiculos para inspeção */
            final List<Stif_Veiculo_InspecaoED> veiculosInspecao = new OLUtil().pegaArraydaRequest(ed.getArray());
            ed.setNr_Veiculos(veiculosInspecao.size());

            /** Grava Inspeção */
            final Stif_InspecaoED inspecaoED = new Stif_InspecaoBD(this.sql).inclui(ed);

            /** Grava Veículos da Inspeção */
        	for (final Stif_Veiculo_InspecaoED veiculoInspecaoED : veiculosInspecao) {
        		veiculoInspecaoED.setOid_Inspecao(inspecaoED.getOid_Inspecao());
        		veiculoInspecaoED.setMasterDetails(inspecaoED);
        		new Stif_Veiculo_InspecaoBD(this.sql).inclui(veiculoInspecaoED);
        	}
            this.fimTransacao(true);
            return inspecaoED;
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

    public void altera(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_InspecaoBD(this.sql).altera(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

    /**
     * Excluir toda relacao de dados vinculada a Inspecao
     */
    public void deleta(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            /** Excluir toda relacao de dados vinculada a Inspecao */
            final Stif_Veiculo_InspecaoED filterVeiculo = new Stif_Veiculo_InspecaoED();
            filterVeiculo.setOid_Inspecao(ed.getOid_Inspecao());
            final List<Stif_Veiculo_InspecaoED> veiculos = new Stif_Veiculo_InspecaoBD(this.sql).lista(filterVeiculo);
            for (final Stif_Veiculo_InspecaoED veiculo : veiculos) {
            	final Stif_Pneu_InspecaoED filterPneu = new Stif_Pneu_InspecaoED();  
            	filterPneu.setOid_Veiculo_Inspecao(veiculo.getOid_Veiculo_Inspecao());
            	final List<Stif_Pneu_InspecaoED> pneus = new Stif_Pneu_InspecaoBD(this.sql).lista(filterPneu);
            	for (final Stif_Pneu_InspecaoED pneu : pneus) {
            		/** Exclui Pneus e Problemas */
            		new Stif_Pneu_InspecaoRN(this.sql).deleta(pneu);
				}
            	/** Exclui Constatacoes e Veiculo Inspecao */
        		new Stif_Veiculo_InspecaoRN(this.sql).deleta(veiculo);
			}
            /** Exclui Inspecao */
            new Stif_InspecaoBD(this.sql).deleta(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void encerrar(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_InspecaoBD(this.sql).encerrar(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void reabrir(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_InspecaoBD(this.sql).reabrir(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

     public List<Stif_InspecaoED> lista(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            List<Stif_InspecaoED> lista = new Stif_InspecaoBD(sql).lista(ed);
            return lista;
        } finally {
            this.fimTransacao(false);
        }
    }
    
    public Stif_InspecaoED getByRecord(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_InspecaoBD(this.sql).getByRecord(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

    public boolean verificaPneuConfigurado(Stif_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_InspecaoBD(this.sql).verificaPneuConfigurado(ed);
        } finally {
            this.fimTransacao(false);
        }
    }
    
    public void relatorio(Stif_InspecaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            List<Stif_InspecaoED> lista = new Stif_InspecaoBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("tif009"); // Seta o nome do relatório
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
    	Stif_InspecaoED ed = (Stif_InspecaoED)Obj;
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
    	Stif_InspecaoED ed = (Stif_InspecaoED)Obj;
    	
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	final UsuarioED usuario = (UsuarioED)request.getSession(true).getAttribute("usuario");
    	if ("I".equals(acao)) {
    		if (usuario == null) {
    			out.println("<ret><item oknok='Usuário não logado!' /></ret>");
    		} else {
    			ed.setOid_Usuario(usuario.getOid_Usuario().longValue());
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Inspecao() + "' /></ret>");
    		}
    	} else 
		if ("A".equals(acao)) {
    		if (usuario == null) {
    			out.println("<ret><item oknok='Usuário não logado!' /></ret>");
    		} else {
    			ed.setOid_Usuario(usuario.getOid_Usuario().longValue());
    			this.altera(ed);
    			out.println("<ret><item oknok='AOK'/></ret>");
    		}
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK'/></ret>");
		} else
		if ("E".equals(acao)) {
			if (usuario == null) {
    			out.println("<ret><item oknok='Usuário não logado!' /></ret>");
    		} else {
    			/** Verifica se existe algum pneu configurado para a inspeção */
    			if (this.verificaPneuConfigurado(ed)) {
	    			ed.setOid_Usuario(usuario.getOid_Usuario().longValue());
	    			ed.setDt_Encerramento(Data.getDataDMY());
	    			this.encerrar(ed);
	    			out.println("<ret><item oknok='EOK' /></ret>");
    			} else {
    				out.println("<ret><item oknok='A inspeção selecionada não possui nenhum pneu registrado!' /></ret>");
    			}
    		}
		} else 
		if ("R".equals(acao)) {
			if (usuario == null) {
    			out.println("<ret><item oknok='Usuário não logado!' /></ret>");
    		} else {
    			ed.setOid_Usuario(usuario.getOid_Usuario().longValue());
    			this.reabrir(ed);
    			out.println("<ret><item oknok='ROK'/></ret>");
    		}
		} else
		if ("C".equals(acao)) {
			final Stif_InspecaoED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Inspecao() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Registro não encontrado!' /></ret>");
			}
		} else {
			out.println("<cad>");
			final List<Stif_InspecaoED> result = this.lista(ed);
			for (final Stif_InspecaoED edVolta : result) {
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
    private String montaRegistro(final Stif_InspecaoED ed){
    	String saida;
		saida = "<item ";
		saida += "oid_Inspecao='" + ed.getOid_Inspecao() + "' ";
		saida += "oid_Cliente='" + ed.getOid_Cliente() + "' ";
		saida += "nr_Veiculos='" + ed.getNr_Veiculos() + "' ";
		saida += "nr_Veiculos_Text='" + JavaUtil.LFill(String.valueOf(ed.getNr_Veiculos()), 2, true) + " veículos inspecionados" + "' ";
		saida += "dt_Inspecao='" + FormataData.formataDataBT(ed.getDt_Inspecao()) + "' ";
		saida += "dt_Encerramento='" + FormataData.formataDataBT(ed.getDt_Encerramento()) + "' ";
		saida += "vl_pneu_novo = '" + FormataValor.formataValorBT(ed.getVl_pneu_novo(), 2) + "' ";
		saida += "vl_pneu_r1 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r1(), 2) + "' ";
		saida += "vl_pneu_r2 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r2(), 2) + "' ";
		saida += "vl_pneu_r3 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r3(), 2) + "' ";
		saida += "vl_pneu_r4 = '" + FormataValor.formataValorBT(ed.getVl_pneu_r4(), 2) + "' ";
		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}