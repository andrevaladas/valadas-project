/*
 * Created on 12/11/2004
 *
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.PneuBD;
import com.master.ed.PneuED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.OLUtil;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Ralph - Alterado - Rotinas Laszlo //
 * @author PRS - Alterado - Rotinas Laszlo //
 * @author Cristian - Alterado - Rotinas Laszlo //
 */
public class PneuRN extends Transacao {

	public PneuRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public PneuRN(ExecutaSQL executasql) {
		super(executasql);
	}

	BancoUtil bu = new BancoUtil();
	
	public PneuED inclui(PneuED ed) throws Excecoes {
		this.inicioTransacao();
		try {
			PneuED toReturn = new PneuBD(sql).inclui(ed);
			this.fimTransacao(true);
			return toReturn;
		} catch (RuntimeException e) {
			this.abortaTransacao();
			throw e;
		} catch (Excecoes e) {
			this.abortaTransacao();
			throw e;
		}
	}

	public void altera(PneuED ed) throws Excecoes {
		inicioTransacao();
		try {
			new PneuBD(sql).altera(ed);
			fimTransacao(true);
		} catch (Excecoes e) {
			abortaTransacao();
			throw e;
		}
	}

	public void delete(PneuED ed) throws Excecoes {
		inicioTransacao();
		try {
			new PneuBD(sql).delete(ed);
			fimTransacao(true);
		} catch (Excecoes e) {
			abortaTransacao();
			throw e;
		}
	}

	public ArrayList<PneuED> lista(PneuED ed) throws Excecoes {
		try {
			inicioTransacao();
			ArrayList<PneuED> lista = new PneuBD(sql).lista(ed);
			return lista;
		} finally {
			fimTransacao(false);
		}

	}

	public ArrayList<PneuED> lista_Consulta(PneuED ed) throws Excecoes {
		try {
			inicioTransacao();
			ArrayList<PneuED> lista = new PneuBD(sql).lista_Consulta(ed);
			return lista;
		} finally {
			fimTransacao(false);
		}

	}
	
	public ArrayList<PneuED> listaPorVeiculo(PneuED ed) throws Excecoes {
		try {
			inicioTransacao();
			ArrayList<PneuED> lista = new PneuBD(sql).listaPorVeiculo(ed);
			return lista;
		} finally {
			fimTransacao(false);
		}

	}

	public ArrayList<PneuED> listaPar(PneuED ed) throws Excecoes {
		try {
			inicioTransacao();
			ArrayList<PneuED> listaPar = new PneuBD(sql).listaPar(ed);
			return listaPar;
		} finally {
			fimTransacao(false);
		}

	}
	
	public PneuED getByRecord(int oid) throws Excecoes {
		inicioTransacao();
		try {
			return new PneuBD(this.sql).getByRecord(oid);
		} finally {
			fimTransacao(false);
		}
	}

	public PneuED getByRecord(PneuED ed) throws Excecoes {
		inicioTransacao();
		try {
			return new PneuBD(this.sql).getByRecord(ed);
		} finally {
			fimTransacao(false);
		}
	}

	public PneuED getByRecordOL(PneuED ed) throws Excecoes {
		inicioTransacao();
		try {
			return new PneuBD(this.sql).getByRecordOL(ed);
		} finally {
			fimTransacao(false);
		}
	}

	public PneuED getByCodigo(String codigo) throws Excecoes {
		inicioTransacao();
		try {
			return new PneuBD(this.sql).getByCodigo(codigo);
		} finally {
			fimTransacao(false);
		}
	}

	public PneuED getByNrFabrica(String nrFabrica) throws Excecoes {
		inicioTransacao();
		try {
			return new PneuBD(this.sql).getByNrFabrica(nrFabrica);
		} finally {
			fimTransacao(false);
		}
	}

	public void geraRelPneus(PneuED ed, HttpServletResponse res)
			throws Excecoes {
		inicioTransacao();
		try {
			new PneuBD(this.sql).geraRelPneus(ed, res);
		} finally {
			fimTransacao(false);
		}
	}

	//************L A S Z L O*****************************************

    /** 
     *  Relatório de pneus vendidos 
     * @param ed
     * @return PDF
     * @throws Excecoes
     */    
    public void relatorioVenda(PneuED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
    	BancoUtil bu = new BancoUtil();
    	try {
    		this.inicioTransacao();
    		String nm_Filtro = "";
    		ed.setDm_Virada("true");
    		ed.setDt_Sucateamento("SUCATEAMENTO");
    		ArrayList<PneuED> lista = new PneuBD(sql).lista(ed);
    		ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
    		ed.setResponse(response);
    		ed.setNomeRelatorio("pns018"); // Seta o nome do relatório
    		// Monta a descricao do filtro utilizado
    		if (bu.doValida(ed.getNr_Fogo()))
    			nm_Filtro+=" Pneu=" + ed.getNr_Fogo();
    		if (bu.doValida(ed.getDt_Venda()))
    			nm_Filtro+=" Data=" + ed.getDt_Venda();
    		if (bu.doValida(ed.getTx_Comentario_Venda()))
    			nm_Filtro+=" Comentário=" + ed.getTx_Comentario_Venda();
    		ed.setDescFiltro(nm_Filtro);
    		new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
    	} finally {
    		this.fimTransacao(false);
    	}
    }
    
    /** 
     *  Relatório de pneus em recapagens 
     * @param ed
     * @return PDF
     * @throws Excecoes
     */    
    public void relatorioConsulta_Pneus_Recapagem(PneuED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
    	BancoUtil bu = new BancoUtil();
    	try {
    		this.inicioTransacao();
    		String nm_Filtro = "";
    		ed.setDt_Remessa_Recapagem("RECAPAGEM");
    		ArrayList<PneuED> lista = new PneuBD(sql).lista(ed);
    		ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
    		ed.setResponse(response);
    		ed.setNomeRelatorio("pns303"); // Seta o nome do relatório
    		// Monta a descricao do filtro utilizado
    		if (bu.doValida(ed.getNm_Fornecedor()))
    			nm_Filtro+=" Fornecedor=" + ed.getNm_Fornecedor();
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
    	PneuED ed = (PneuED)Obj;
    	ed.setRequest(request);
    	
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
    @SuppressWarnings("unchecked")
	public void processaOL(String acao, Object Obj, HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException, Excecoes {
    	//Extrai o bean com os campos da request colados
    	PneuED ed = (PneuED)Obj;
    	//Prepara a saída
    	ed.setMasterDetails(request);
    	//ArrayList lstCL = new ArrayList();
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	
    	if ("I".equals(acao) ) { // Incluir pneu
			if (checkDuploPneu(ed,acao)) 
				out.println("<ret><item oknok='Já existente pneu com este nr Fogo !'/></ret>");
			else {
	    		ed = this.inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Pneu() + "'/></ret>");
			}
    	} else
		if ("A".equals(acao)) { // Alterar pneu
			if (checkDuploPneu(ed,acao)) 
				out.println("<ret><item oknok='Já existente pneu com este nr Fogo !'/></ret>");
			else {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
			}
		} else 
		if ("D".equals(acao)) { // Deletar pneu
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else
		if ("C".equals(acao)) { // Consultar um pneu
			PneuED edVolta = this.getByRecordOL(ed);
			if (Utilitaria.doValida(edVolta.getNr_Fogo())) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Pneu não encontrado!' /></ret>");
		} else	
		if ("C102".equals(acao)) { // Consultar um pneu usado exclusivamente pelo pns102L 
			PneuED edVolta = this.getByRecordOL(ed);
			if (Utilitaria.doValida(edVolta.getNr_Fogo())) {
				ArrayList<String> lst = this.statusPneu(ed);
				if ("E".equals((String)lst.get(0))) { // Se o pneu não está no estoque dá o erro
					out.println("<cad>");
					out.println(montaRegistro(edVolta));
					out.println("</cad>");
				} else {
					out.println("<ret><item oknok='" + (String)lst.get(1) + "!'/></ret>");
				}
			} else
				out.println("<ret><item oknok='Pneu não encontrado!' /></ret>");
		} else  {
			out.println("<cad>");
			String saida=null; 
			ArrayList lst = new ArrayList();
			if ("L".equals(acao)) { // Lista de pneus
				lst = this.lista(ed);
			} else
			if ("LP".equals(acao)) { // Lista Pares Para o pneu
				lst = this.listaPar(ed);
			} else
				
			if ("LVEI".equals(acao)) { // Lista Pares Para o pneu
				System.out.println("lista por veiculo");
				lst = this.listaPorVeiculo(ed);
			} else
				
			
			if ("LR".equals(acao)) { // Lista de pneus em recapagem
				ed.setDt_Remessa_Recapagem("RECAPAGEM");
				lst = this.lista(ed);
			} else
			if ("LS".equals(acao)) { // Lista de pneus em sucateados
				ed.setDt_Sucateamento("SUCATEAMENTO");
				lst = this.lista(ed);
			} else
			
			if ("LCL".equals(acao)) { // Lista de pneus com calculo de retirada
				lst = this.calculaRetirada(ed);
			} 
			for (int i=0; i<lst.size(); i++){
				PneuED edVolta = new PneuED();
				edVolta = (PneuED)lst.get(i);
				if ("L".equals(acao) || "LR".equals(acao) || "LS".equals(acao) || "LPM".equals(acao)  ||
					"LE".equals(acao)|| "LPV".equals(acao) || "LGNC".equals(acao) || "LCL".equals(acao) ||
					"LPPM".equals(acao)  || "LP".equals(acao) || "LRR".equals(acao) || "LVEI".equals(acao) ){ // Processa listas	
					saida = montaRegistro(edVolta);
				}else
				if ("CB".equals(acao)) { // Lista combo
					saida = "<item ";
					saida += "value='" + edVolta.getOid_Pneu()+ "'>";
					saida +=  edVolta.getNr_Fogo();
					saida += "</item>";
				}
				out.println(saida);
			}
			out.println("</cad>");
		}
    	out.flush();
    	out.close();
    }

	/**
	 * @param ed
	 * @throws Excecoes
	 */
	@SuppressWarnings("unchecked")
	private ArrayList calculaRetirada(PneuED ed) throws Excecoes {
		PneuED edVolta = new PneuED();
		OLUtil ol = new OLUtil();
		ArrayList lst = ol.pegaArraydaRequest(ed.getArray());
		try {
    		this.inicioTransacao();
			for (int x=0;x<lst.size();x++) {
				PneuED pneuED = (PneuED)lst.get(x);
				edVolta.setOid_Pneu(pneuED.getOid_Pneu());
				edVolta = new PneuBD(this.sql).getByRecordOL(edVolta);
				pneuED.setNr_Fogo(edVolta.getNr_Fogo());
				pneuED.setDM_Posicao(edVolta.getDM_Posicao());
				pneuED.setNr_Hodometro_Veiculo(edVolta.getNr_Hodometro_Veiculo());
				pneuED.setNr_Km_Acumulada_Veiculo(edVolta.getNr_Km_Acumulada_Veiculo());
				pneuED.setMM_Atual(edVolta.getMM_Atual());
				pneuED.setMm_Gastos(pneuED.getNr_Mm_Inicial() - pneuED.getMM());
				pneuED.setMm_Restantes(pneuED.getMM() - ed.getTwi());
				pneuED.setKm_Mm((ed.getNr_Km_Acumulada_Veiculo()-pneuED.getNr_Km_Vida_N0()) / pneuED.getMm_Gastos());
				pneuED.setKm_Restantes(pneuED.getKm_Mm() * pneuED.getMm_Restantes());
				pneuED.setNr_Km_No_Veiculo(ed.getNr_Km_Acumulada_Veiculo() - edVolta.getNr_Km_Acumulada_Veiculo());
			}
			this.fimTransacao(false);
		} catch (Excecoes e) {
			this.abortaTransacao();
			throw e;
		}
		return lst;
	}

    private String montaRegistro(PneuED edVolta) throws Excecoes {
    	String saida;
		saida =  "<item ";
		saida += "oid_Pneu='" + edVolta.getOid_Pneu() + "' ";
		saida += "oid_Fornecedor='" + edVolta.getOid_Fornecedor() + "' ";
		saida += "oid_Fornecedor_Recapagem='" + edVolta.getOid_Fornecedor_Recapagem() + "' ";
		saida += "oid_Fabricante_Pneu='" + edVolta.getOid_Fabricante_Pneu() + "' ";
		saida += "oid_Tipo_Pneu='" + edVolta.getOid_Tipo_Pneu() + "' ";
		saida += "oid_Modelo_Pneu='" + edVolta.getOid_Modelo_Pneu() + "' ";
		saida += "oid_Dimensao_Pneu='" + edVolta.getOid_Dimensao_Pneu() + "' ";
		saida += "oid_Empresa='" + edVolta.getOid_Empresa()         + "' ";
		saida += "oid_Local_Estoque='" + edVolta.getOid_Local_Estoque() + "' ";
		saida += "oid_Veiculo='" + edVolta.getOid_Veiculo() + "' ";
		saida += "CD_Pneu='" + edVolta.getCD_Pneu() + "' ";
		saida += "CD_Modelo_Pneu='" + edVolta.getCD_Modelo_Pneu() + "' ";
		saida += "cd_Item_Estoque='" + edVolta.getCd_Item_Estoque() + "' ";
		saida += "NR_Fabrica='" + edVolta.getNR_Fabrica() + "' ";

		saida += "nr_Frota='" + edVolta.getNR_Frota() + "' ";
		saida += "nr_Km_Acumulada='" + FormataValor.formataValorBT(edVolta.getNr_Km_Acumulada(),1) + "' ";
		saida += "nr_Km_Acumulada_Veiculo='" + FormataValor.formataValorBT(edVolta.getNr_Km_Acumulada_Veiculo(),1) + "' ";
		saida += "nr_Hodometro_Veiculo='" + FormataValor.formataValorBT(edVolta.getNr_Hodometro_Veiculo(),1) + "' ";
		saida += "nm_Tipo_Veiculo='" + edVolta.getNm_Tipo_Veiculo() + "' ";
		
		saida += "nr_Nota_Fiscal='" + edVolta.getNr_Nota_Fiscal() + "' ";
		saida += "nr_Serie='" + edVolta.getNr_Serie() + "' ";
		saida += "nr_Vida='" + edVolta.getNr_Vida() + "' ";
		saida += "nr_Perimetro='" + FormataValor.formataValorBT(edVolta.getNr_Perimetro(),0) + "' ";
		saida += "KM_Atual='" + edVolta.getKM_Atual() + "' ";
		saida += "MM_Atual='" + FormataValor.formataValorBT(edVolta.getMM_Atual(),1) + "' ";
		saida += "nr_Mm_Inicial='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Inicial(),1) + "' ";
		saida += "nr_Mm_Saida='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Saida(),1) + "' ";
		saida += "DM_Situacao='" + edVolta.getDM_Situacao() + "' ";
		saida += "dm_Controle_Parcial ='" + edVolta.getDm_Controle_Parcial() + "' ";
		saida += "DM_Localizacao='" + edVolta.getDM_Localizacao() + "' ";
		saida += "DM_Posicao='" + edVolta.getDM_Posicao() + "' ";
		saida += "dm_Eixo='" + edVolta.getDm_Eixo() + "' ";
		saida += "nr_Fogo='" + edVolta.getNr_Fogo() + "' ";
		saida += "nm_Fabricante_Pneu='" + edVolta.getNm_Fabricante_Pneu() + "' ";
		saida += "nm_Dimensao_Pneu='" + edVolta.getNm_Dimensao_Pneu() + "' ";
		saida += "nm_Modelo_Pneu='" + edVolta.getNm_Modelo_Pneu() + "' ";
		saida += "nm_Tipo_Pneu='" + edVolta.getNm_Tipo_Pneu() + "' ";
		saida += "nm_Fornecedor_Recapagem='" + edVolta.getNm_Fornecedor_Recapagem() + "' ";
		saida += "nm_Motivo_Sucateamento='" + edVolta.getNm_Motivo_Sucateamento() + "' ";
		saida += "nm_Local_Estoque='" + edVolta.getNm_Local_Estoque() + "' ";
		saida += "nm_Contato_Recapagem='" + edVolta.getNm_Contato_Recapagem() + "' ";
		saida += "nm_Razao_Social='" + JavaUtil.preperaString(edVolta.getNm_Razao_Social()) + "' ";
		saida += "tx_Lonas='" + edVolta.getTx_Lonas() + "' ";
		saida += "tx_Dot='" + edVolta.getTx_Dot() + "' ";
		saida += "Desc_Eixo='" + edVolta.getDesc_Eixo() + "' ";
		saida += "Desc_Localizacao_Pneu='" + edVolta.getDesc_Localizacao_Pneu() + "' ";
		saida += "dt_Recusa_Recapagem='" + edVolta.getDt_Recusa_Recapagem() + "' ";
		saida += "tx_Motivo_Recusa_Recapagem='" + edVolta.getTx_Motivo_Recusa_Recapagem() + "' ";
		saida += "nr_Nota_Fiscal_Recusa_Recapagem='" + edVolta.getNr_Nota_Fiscal_Recusa_Recapagem() + "' ";
		saida += "nm_Responsavel_Recusa_Recapagem='" + edVolta.getNm_Responsavel_Recusa_Recapagem() + "' ";
		
		saida += "tx_Situacao='" + edVolta.getTx_Situacao() + "' ";
		saida += "dt_Nota_Fiscal='" + edVolta.getDt_Nota_Fiscal() + "' ";
		saida += "dt_Sucateamento='" + edVolta.getDt_Sucateamento() + "' ";
		
		if (JavaUtil.doValida(edVolta.getDt_Venda())) {
			saida += "dt_Venda='" + edVolta.getDt_Venda() + "' ";
		}else{
			saida += "dt_Venda='" + "" + "' ";
		}
		if (edVolta.getVl_Venda() > 0){
			saida += "vl_Venda='" + FormataValor.formataValorBT(edVolta.getVl_Venda(),2) + "' ";			
		}else{ 
			saida += "vl_Venda='" + "" + "' ";			
		}
		if (JavaUtil.doValida(edVolta.getTx_Comentario_Venda())) {
			saida += "tx_Comentario_Venda='" + edVolta.getTx_Comentario_Venda() + "' ";
		}else{
			saida += "tx_Comentario_Venda='" + "" + "' ";
		}		
		
		saida += "dt_Entrada='" + edVolta.getDt_Entrada() + "' ";
		saida += "dt_Estoque='" + edVolta.getDt_Estoque() + "' ";
		saida += "DiasEmEstoque='" + edVolta.getDiasEmEstoque() + "' ";		
		saida += "dt_Remessa_Recapagem='" + edVolta.getDt_Remessa_Recapagem() + "' ";
		saida += "dt_Promessa_Retorno_Recapagem='" + edVolta.getDt_Promessa_Retorno_Recapagem() + "' ";
		saida += "vl_Preco='" + FormataValor.formataValorBT(edVolta.getVl_Preco(),2) + "' ";
		
		saida += "vl_Negociado_Recapagem='" + FormataValor.formataValorBT(edVolta.getVl_Negociado_Recapagem(), 2) + "' ";
		saida += "nr_Os_Recapagem='" + edVolta.getNr_Os_Recapagem() + "' ";
		
		saida += "nm_Fornecedor='" + edVolta.getNm_Fornecedor() + "' ";
		// Dados da última recapagem - Vem da tabela Recapagens
		saida += "nm_Fabricante_Ultima_Recapagem='" + edVolta.getNm_Fabricante_Ultima_Recapagem() + "' ";
		saida += "nm_Modelo_Pneu_Ultima_Recapagem='" + edVolta.getNm_Modelo_Pneu_Ultima_Recapagem() + "' ";
				
		// Campos para a lista de calculo previsão de retirada
		if (edVolta.getMm_Gastos() >= 0)
			saida += "mm_Gastos='" + FormataValor.formataValorBT(edVolta.getMm_Gastos(), 1) + "' ";
		if (edVolta.getMm_Restantes() >= 0)
			saida += "mm_Restantes='" + FormataValor.formataValorBT(edVolta.getMm_Restantes(), 1) + "' ";
		if (edVolta.getKm_Restantes() >= 0)
			saida += "km_Restantes='" + FormataValor.formataValorBT(edVolta.getKm_Restantes(), 1) + "' ";
		//
		if (edVolta.getKm_Mm() >= 0)
			saida += "km_Mm='" + FormataValor.formataValorBT(edVolta.getKm_Mm(), 1) + "' ";
		if (edVolta.getMM() > 0)
			saida += "MM='" + FormataValor.formataValorBT(edVolta.getMM(), 1) + "' ";
		//if (edVolta.getNr_Km_Vida_N0()> 0) 
			saida += "nr_Km_Vida_N0='" + FormataValor.formataValorBT(edVolta.getNr_Km_Vida_N0(), 1) + "' ";
		if (edVolta.getNr_Km_No_Veiculo()> 0) 
			saida += "nr_Km_No_Veiculo='" + FormataValor.formataValorBT(edVolta.getNr_Km_No_Veiculo(), 1) + "' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		
		saida += "/>";

		return saida;
    }
    
    public boolean checkDuploPneu ( PneuED ed, String acao) throws Excecoes {
    	boolean ret = false;
    	PneuED edChk = new PneuED();
		edChk.setOid_Empresa(ed.getOid_Empresa());
		edChk.setNr_Fogo(ed.getNr_Fogo());
		edChk = this.getByRecordOL(edChk);
    	if ("I".equals(acao) && edChk.getOid_Pneu()>0)
    		ret = true;
    	else
    	if ("A".equals(acao) && edChk.getOid_Pneu()>0 ) {
			if (ed.getOid_Pneu()!=edChk.getOid_Pneu()) 
				ret = true ;
    	}
    	return ret;
    }

    /**
     * Situacao do pneu
     * Dado um pneu no ed retorna um array com duas posicoes:
     * na posicao zero letra que determina o stauts do pneu
     * 	E = Estoque
     * 	M = Montado na frota
     *  N = Pneu inexistente
     *  R = em Recapagem
     *  S = Sucateado
     *  V = Vendido
     *  Na posição um um texto explicativo para msg de erro.
     * @param ed
     * @return
     * @throws Excecoes
     */
    public ArrayList<String> statusPneu ( PneuED ed ) throws Excecoes {
    	
    	BancoUtil bu = new BancoUtil();
    	String status="", texto="";
    	PneuED edChk = new PneuED();
		edChk.setOid_Empresa(ed.getOid_Empresa());
		
		if ( bu.doValida(ed.getNr_Fogo()) ) {// Se foi informado o pneu ( as trocas podem ser feitas sem um pneu entrar no lugar do que saiu ) 
			edChk.setNr_Fogo(ed.getNr_Fogo());
			edChk.setOid_Pneu(ed.getOid_Pneu()); // Se o pneu ja foi valida na consulta ( onblur do campo nr_fogo ) entao ja tem o oid resta verificar situacao... 
			edChk = this.getByRecordOL(edChk);
	    	if (edChk.getOid_Pneu()==0) {
	    		status="N";
	    		texto="Não existe pneu com este número";
	    	} else {
	    		// Pega o oid do pneu e a vida 
	    		ed.setOid_Pneu(edChk.getOid_Pneu());
	    		ed.setNr_Vida(edChk.getNr_Vida());
	    		// Testa outras situacoes
	    		if (bu.doValida(edChk.getDt_Estoque())) {
	    			status="E";
	    			texto="Pneu no estoque " + edChk.getNm_Local_Estoque();
	    			ed.setOid_Local_Estoque(edChk.getOid_Local_Estoque()); // Guarda o oid do estoque para colocar no movimento
	    		}
	    		if (bu.doValida(edChk.getDt_Entrada())) {
	    			status="F";
	    			texto="Pneu está no veiculo " + edChk.getNR_Frota();
	    		}
	    		if (bu.doValida(edChk.getDt_Remessa_Recapagem())) {
	    			status="R";
	    			texto="Pneu está na recapagem " + edChk.getNm_Fornecedor_Recapagem();
	    		}
	    		if (bu.doValida(edChk.getDt_Sucateamento())) {
	    			status="S";
	    			texto="Pneu sucateado em " + edChk.getDt_Sucateamento();
	    		}
	    		if (bu.doValida(edChk.getDt_Venda())) {
	    			status="V";
	    			texto="Pneu vendido em " + edChk.getDt_Venda();
	    		}
	    	}
    	} else {
			status="E";
			texto="Não foi informado pneu para a troca"; // Somente comentario
    	}
    	ArrayList<String> ret = new ArrayList<String>();
		ret.add(status);
		ret.add(texto);
    	return ret;
    }
   
    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
	}
}