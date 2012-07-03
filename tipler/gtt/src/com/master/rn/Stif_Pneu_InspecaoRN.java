package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stif_Pneu_InspecaoBD;
import com.master.bd.Stif_Problema_PneuBD;
import com.master.ed.Stif_Pneu_InspecaoED;
import com.master.ed.Stif_Problema_PneuED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.OLUtil;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial STIF - Pneus Inspeções
 * @since 06/2012
 */
public class Stif_Pneu_InspecaoRN extends Transacao {

	public Stif_Pneu_InspecaoRN() {
		super();
	}

	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stif_Pneu_InspecaoRN(ExecutaSQL executasql) {
		super(executasql);
	}

	@SuppressWarnings("unchecked")
	public Stif_Pneu_InspecaoED inclui(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			this.inicioTransacao();
			new Stif_Pneu_InspecaoBD(this.sql).inclui(ed);
			/** Grava Problemas Pneus */
			final List<Stif_Problema_PneuED> problemas = new OLUtil().pegaArraydaRequest(ed.getArray());
			for (final Stif_Problema_PneuED problemaPneu : problemas) {
				problemaPneu.setOid_Pneu_Inspecao(ed.getOid_Pneu_Inspecao());
				problemaPneu.setMasterDetails(ed);
				new Stif_Problema_PneuBD(this.sql).inclui(problemaPneu);
			}
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

	@SuppressWarnings("unchecked")
	public void altera(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			this.inicioTransacao();
			new Stif_Pneu_InspecaoBD(this.sql).altera(ed);

			/** Excluir problemas existentes */
			new Stif_Problema_PneuBD(this.sql).deletaAllFromPneuInspecao(ed.getOid_Pneu_Inspecao());

			/** Grava Problemas Pneus */
			final List<Stif_Problema_PneuED> problemas = new OLUtil().pegaArraydaRequest(ed.getArray());
			for (final Stif_Problema_PneuED problemaPneu : problemas) {
				problemaPneu.setOid_Pneu_Inspecao(ed.getOid_Pneu_Inspecao());
				problemaPneu.setMasterDetails(ed);
				new Stif_Problema_PneuBD(this.sql).inclui(problemaPneu);
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

	public void deleta(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			this.inicioTransacao();
			/** Excluir problemas existentes */
			new Stif_Problema_PneuBD(this.sql).deletaAllFromPneuInspecao(ed.getOid_Pneu_Inspecao());
			/** Excluir Pneu Inspecao */
			new Stif_Pneu_InspecaoBD(this.sql).deleta(ed);
			this.fimTransacao(true);
		} catch (Excecoes e) {
			this.abortaTransacao();
			throw e;
		} catch (RuntimeException e) {
			this.abortaTransacao();
			throw e;
		}
	}

	public boolean validaNrFogo(Stif_Pneu_InspecaoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new Stif_Pneu_InspecaoBD(this.sql).validaNrFogo(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

	public List<Stif_Pneu_InspecaoED> lista(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			this.inicioTransacao();
			return new Stif_Pneu_InspecaoBD(sql).lista(ed);
		} finally {
			this.fimTransacao(false);
		}
	}

	public Stif_Pneu_InspecaoED getByRecord(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			this.inicioTransacao();
			return new Stif_Pneu_InspecaoBD(this.sql).getByRecord(ed);
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorio(Stif_Pneu_InspecaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			List<Stif_Pneu_InspecaoED> lista = new Stif_Pneu_InspecaoBD(sql).lista(ed);
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
		Stif_Pneu_InspecaoED ed = (Stif_Pneu_InspecaoED)Obj;
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
		Stif_Pneu_InspecaoED ed = (Stif_Pneu_InspecaoED)Obj;

		//Prepara a saída
		final PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao)) {
			/** Verifica se nr Fogo não foi utilizado em outra posição deste veículo */
			if (validaNrFogo(ed)) {
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Pneu_Inspecao() + "'/></ret>");
			} else {
				out.println("<ret><item oknok='Nr Fogo já informado em outra posição deste veículo!' /></ret>");
			}
		} else 
		if ("A".equals(acao)) {
			/** Verifica se nr Fogo não foi utilizado em outra posição deste veículo */
			if (validaNrFogo(ed)) {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
			} else {
				out.println("<ret><item oknok='Nr Fogo já informado em outra posição deste veículo!' /></ret>");
			}
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			final Stif_Pneu_InspecaoED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Pneu_Inspecao() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Registro não encontrado!' /></ret>");
			}
		} else {
			out.println("<cad>");
			final List<Stif_Pneu_InspecaoED> resultList = this.lista(ed);
			for (Stif_Pneu_InspecaoED edVolta : resultList) {
				out.println(montaRegistro(edVolta));
			}
			out.println("</cad>");
		}
		out.flush();
		out.close();
	}

	private String montaRegistro(final Stif_Pneu_InspecaoED ed){
		String saida;
		saida = "<item ";
		saida += "oid_Pneu_Inspecao='" + ed.getOid_Pneu_Inspecao() + "' ";
		saida += "oid_Veiculo_Inspecao='" + ed.getOid_Veiculo_Inspecao() + "' ";
		saida += "dm_Posicao='" + ed.getDm_Posicao() + "' ";
		saida += "nr_Fogo='" + ed.getNr_Fogo() + "' ";
		saida += "dm_Vida_Pneu='" + ed.getDm_Vida_Pneu() + "' ";
		saida += "nr_Mm_Sulco='" + FormataValor.formataValorBT(ed.getNr_Mm_Sulco(),1) + "' ";
		saida += "nr_Pressao='" + ed.getNr_Pressao() + "' ";
		saida += "oid_Pneu_Dimensao='" + ed.getOid_Pneu_Dimensao() + "' ";
		saida += "oid_Fabricante_Pneu='" + ed.getOid_Fabricante_Pneu() + "' ";
		saida += "oid_Modelo_Pneu='" + ed.getOid_Modelo_Pneu() + "' ";
		saida += "oid_Fabricante_Banda='" + ed.getOid_Fabricante_Banda() + "' ";
		saida += "oid_Banda='" + ed.getOid_Banda() + "' ";
		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}

	protected void finalize() throws Throwable {
		if (this.sql != null)
			this.abortaTransacao();
	}
}