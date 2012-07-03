package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_ConstatacaoBD;
import com.master.bd.Stif_Veiculo_InspecaoBD;
import com.master.ed.Stif_ConstatacaoED;
import com.master.ed.Stif_Pneu_InspecaoED;
import com.master.ed.Stif_Veiculo_InspecaoED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.OLUtil;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial STIF - Veículos Inspeção
 * @since 06/2012
 */
public class Stif_Veiculo_InspecaoRN extends Transacao {

	public Stif_Veiculo_InspecaoRN() {
		super();
	}

	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stif_Veiculo_InspecaoRN(ExecutaSQL executasql) {
		super(executasql);
	}

	public Stif_Veiculo_InspecaoED inclui(Stif_Veiculo_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            final Stif_Veiculo_InspecaoED inspecaoED = new Stif_Veiculo_InspecaoBD(this.sql).inclui(ed);
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

	@SuppressWarnings("unchecked")
    public void altera(Stif_Veiculo_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new Stif_Veiculo_InspecaoBD(this.sql).altera(ed);

            /** Excluir constatacoes existentes */
            new Stif_ConstatacaoBD(this.sql).deletaAllFromVeiculoInspecao(ed.getOid_Veiculo_Inspecao());
            /** Grava Constatações */
            final List<Stif_ConstatacaoED> constatacoes = new OLUtil().pegaArraydaRequest(ed.getArray());
        	for (final Stif_ConstatacaoED constatacaoED : constatacoes) {
        		constatacaoED.setMasterDetails(ed);
        		new Stif_ConstatacaoBD(this.sql).inclui(constatacaoED);
        	}

            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void deleta(Stif_Veiculo_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            /** Excluir constatacoes existentes */
            new Stif_ConstatacaoBD(this.sql).deletaAllFromVeiculoInspecao(ed.getOid_Veiculo_Inspecao());
            /** Excluir Veiculo Inspecao */
            new Stif_Veiculo_InspecaoBD(this.sql).deleta(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

     public List<Stif_Veiculo_InspecaoED> lista(Stif_Veiculo_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_Veiculo_InspecaoBD(sql).lista(ed);
        } finally {
            this.fimTransacao(false);
        }
    }
    
    public Stif_Veiculo_InspecaoED getByRecord(Stif_Veiculo_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_Veiculo_InspecaoBD(this.sql).getByRecord(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

    public byte[] getImagem(long oidVeiculoInspcao) throws Excecoes {
		try {
			this.inicioTransacao();
			return new Stif_Veiculo_InspecaoBD(this.sql).getImagem(oidVeiculoInspcao);
		} finally {
			this.fimTransacao(false);
		}
	}

	public void saveImagem(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			this.inicioTransacao();
			new Stif_Veiculo_InspecaoBD(this.sql).saveImagem(ed);
		} finally {
			this.fimTransacao(true);
		}
	}

    public void relatorio(Stif_Veiculo_InspecaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            List<Stif_Veiculo_InspecaoED> lista = new Stif_Veiculo_InspecaoBD(sql).lista(ed);
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
    	Stif_Veiculo_InspecaoED ed = (Stif_Veiculo_InspecaoED)Obj;
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
    	Stif_Veiculo_InspecaoED ed = (Stif_Veiculo_InspecaoED)Obj;
    	
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao)) {
    		ed = this.inclui(ed);
    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Veiculo_Inspecao() + "' /></ret>");
    	} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else 
		if ("IMG".equals(acao)) {
			this.saveImagem(ed);
			out.println("<ret><item oknok='IMGOK'/></ret>");
		} else 
		if ("C".equals(acao)) {
			final Stif_Veiculo_InspecaoED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Inspecao() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Registro não encontrado!' /></ret>");
			}
		} else {
			out.println("<cad>");
			final List<Stif_Veiculo_InspecaoED> resultList = this.lista(ed);
			for (final Stif_Veiculo_InspecaoED veiculoInspecao : resultList) {
				out.println(montaRegistro(veiculoInspecao));
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
    private String montaRegistro(final Stif_Veiculo_InspecaoED ed){
    	String saida;
		saida = "<item ";
		saida += "oid_Veiculo_Inspecao='" + ed.getOid_Veiculo_Inspecao() + "' ";
		saida += "oid_Inspecao='" + ed.getOid_Inspecao() + "' ";
		saida += "oid_Veiculo='" + ed.getOid_Veiculo() + "' ";
		saida += "nr_Odometro='" + ed.getNr_Odometro() + "' ";
		saida += "tx_Observacao='" + JavaUtil.getValue(ed.getTx_Observacao()) + "' ";
		saida += "tx_Observacao_Imagem='" + JavaUtil.getValue(ed.getTx_Observacao_Imagem()) + "' ";
		if (ed.getVeiculoED() != null) {
			saida += "nr_Frota='" + ed.getVeiculoED().getNr_Frota() + "' ";
			saida += "nr_Placa='" + ed.getVeiculoED().getNr_Placa() + "' ";
			saida += "oid_Cliente='" + ed.getVeiculoED().getOid_Cliente() + "' ";
			saida += "dm_Tipo_Veiculo='" + ed.getVeiculoED().getDm_Tipo_Veiculo() + "' ";
			saida += "nm_Marca_Veiculo='" + ed.getVeiculoED().getNm_Marca_Veiculo() + "' ";
			saida += "nm_Modelo_Veiculo='" + ed.getVeiculoED().getNm_Modelo_Veiculo() + "' ";
			saida += "nr_Ano_Fabricacao='" + FormataValor.formataValorBT(ed.getVeiculoED().getNr_Ano_Fabricacao(),0) + "' ";
			saida += "nr_Ano_Modelo='" + FormataValor.formataValorBT(ed.getVeiculoED().getNr_Ano_Modelo(),0) + "' ";
			saida += "dm_Tipo_Piso='" + ed.getVeiculoED().getDm_Tipo_Piso() + "' ";
			saida += "dm_Tipo_Severidade='" + ed.getVeiculoED().getDm_Tipo_Severidade() + "' ";
			saida += "nm_Rota='" + JavaUtil.getValue(ed.getVeiculoED().getNm_Rota()) + "' ";
			saida += "dm_Tipo_Chassis='" + ed.getVeiculoED().getDm_Tipo_Chassis() + "' ";
	    }

		try {
			/** Retorna Posições Pneus Configuradas do Veículo */
			final Stif_Pneu_InspecaoED pneu = new Stif_Pneu_InspecaoED();
			pneu.setOid_Veiculo_Inspecao(ed.getOid_Veiculo_Inspecao());
			final List<Stif_Pneu_InspecaoED> pneusInspecao = new Stif_Pneu_InspecaoRN().lista(pneu);
			for (final Stif_Pneu_InspecaoED stifPneuInspecaoED : pneusInspecao) {
				saida += stifPneuInspecaoED.getDm_Posicao()+"='"+stifPneuInspecaoED.getOid_Pneu_Inspecao()+"' ";
				saida += stifPneuInspecaoED.getDm_Posicao()+"_Nr_Fogo"+"='"+stifPneuInspecaoED.getNr_Fogo()+"' ";
				saida += stifPneuInspecaoED.getDm_Posicao()+"_Dm_Vida_Pneu"+"='"+stifPneuInspecaoED.getDm_Vida_Pneu_Descricao()+"' ";
			}
		} catch (Excecoes e) {}

		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}