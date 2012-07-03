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

import com.master.bd.BandaBD;
import com.master.ed.BandaED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;
/**
 * @author Régis Steigleder
 * @serial DModelos de pneus
 * @serialData 06/2007
 */
public class BandaRN extends Transacao {
	
	public BandaRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public BandaRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public BandaED inclui (BandaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			BandaED toReturn = new BandaBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (BandaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new BandaBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void delete (BandaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new BandaBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList lista (BandaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new BandaBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public BandaED getByRecord (BandaED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new BandaBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	
	public byte[] getImagem(BandaED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new BandaBD (this.sql).getImagem(ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public void setImagem(String pArquivo) throws Excecoes {
		inicioTransacao ();
		try {
			new BandaBD (this.sql).setImagem(1,pArquivo);
		}
		finally {
			fimTransacao (true);
		}
	}

	public void relatorio(BandaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			System.out.println(basePath);
			
			String nm_Filtro = "";
			ArrayList lista = new BandaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt003"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			BancoUtil bu = new BancoUtil();
			if (bu.doValida(ed.getCd_Banda()))
				nm_Filtro+=" Código=" + ed.getCd_Banda();
			if (bu.doValida(ed.getNm_Banda()))
				nm_Filtro+=" Desenho=" + ed.getNm_Banda();
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
		} finally {
			this.fimTransacao(false);
		}
	}

	public void relatorioCompleto(BandaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
		try {
			this.inicioTransacao();
			String nm_Filtro = "";
			ArrayList lista = new BandaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("ban021"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			BancoUtil bu = new BancoUtil();
			if (bu.doValida(ed.getCd_Banda()))
				nm_Filtro+=" Código=" + ed.getCd_Banda();
			if (bu.doValida(ed.getNm_Banda()))
				nm_Filtro+=" Desenho=" + ed.getNm_Banda();
			if (ed.getOid_Fabricante_Banda()>0)
				nm_Filtro+=" Fabricante=" + ed.getNm_Fabricante_Banda();
			if (bu.doValida(ed.getDm_Aplicacao()))
				nm_Filtro+=" Aplicação=" + ed.getNm_Aplicacao();
			
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
		BandaED ed = (BandaED)Obj;
		ed.setRequest(request);
		if ("1".equals(rel)) {
		   this.relatorio(ed, request, response);	
		} else 
		if ("2".equals(rel)) {
			   this.relatorioCompleto(ed, request, response);	
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
	public void processaOL(String acao, Object Obj, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Excecoes {
		//Extrai o bean com os campos da request colados
		BandaED ed = (BandaED)Obj;
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			if (checkDuplo(ed,acao)) {  
				out.println("<ret><item oknok='Banda já existente com esta descrição !'/></ret>");
			} else {
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Banda() + "' /></ret>");
			}    		
		} else 
		if ("A".equals(acao)) {
			if (checkDuplo(ed,acao)) {  
				out.println("<ret><item oknok='Banda já existente com esta descrição !'/></ret>");
			} else {
				this.altera(ed);
				out.println("<ret><item oknok='AOK' /></ret>");
			}
		} else 
		if ("D".equals(acao)) {
			if (checkEmUso(ed)) {
				out.println("<ret><item oknok='Impossível excluir! Banda em uso!' /></ret>");
			} else {
				this.delete(ed);
				out.println("<ret><item oknok='DOK' /></ret>");
			}
		} else	
		if ("lookup".equals(acao)) {
			ArrayList lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					BandaED edVolta = (BandaED)lst.get(i);
					saida = "<item ";
					saida += "oid_Banda	='" + edVolta.getOid_Banda() + "' ";
					saida += "cd_Banda='" + JavaUtil.preperaString(edVolta.getCd_Banda()) + "' ";
					saida += "nm_Banda='" + JavaUtil.preperaString(edVolta.getNm_Banda()) + " " + FormataValor.formataValorBT(edVolta.getNr_Profundidade(),1) + "' ";
					saida += "nm_Fabricante='" + JavaUtil.preperaString(edVolta.getNm_Fabricante_Banda()) + "' ";
					saida += "nm_Tipo_Pneu='" + JavaUtil.preperaString(edVolta.getNm_Tipo_Pneu()) + "' ";
					saida += "nm_Aplicacao='" + JavaUtil.preperaString(edVolta.getNm_Aplicacao()) + "' ";
					saida += "nm_Eixo='" + JavaUtil.preperaString(edVolta.getNm_Eixo()) + "' ";
					saida += "dm_Fora_Uso='" + edVolta.getDm_Fora_Uso() + "' ";
					saida += "nm_Fora_Uso='" + ("true".equals(edVolta.getDm_Fora_Uso())?"Fora de uso":" ") + "' ";
					saida += "tx_Descricao='" + JavaUtil.preperaString(edVolta.getTx_Descricao()) + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Banda não encontrada !'/></ret>");
			}		
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList lst = new ArrayList();
		lst = this.lista(ed);
		
		for (int i=0; i<lst.size(); i++){
			BandaED edVolta = new BandaED();
			edVolta = (BandaED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Banda='" + edVolta.getOid_Banda() + "' ";
				saida += "cd_Banda='" + edVolta.getCd_Banda() + "' ";
				saida += "nm_Banda='" + edVolta.getNm_Banda() + "' ";
				saida += "tx_Descricao='" + edVolta.getTx_Descricao() + "' ";
				saida += "nm_Fabricante_Banda='" + edVolta.getNm_Fabricante_Banda() + "' ";
				saida += "nr_Profundidade='" + FormataValor.formataValorBT(edVolta.getNr_Profundidade(),1) + "' ";
				saida += "nr_Largura='" + FormataValor.formataValorBT(edVolta.getNr_Largura(),1) + "' ";
				saida += "oid_Fabricante_Banda='" + edVolta.getOid_Fabricante_Banda() + "' ";
				saida += "dm_Aplicacao='" + edVolta.getDm_Aplicacao() + "' ";
				saida += "dm_Tipo_Pneu='" + edVolta.getDm_Tipo_Pneu() + "' ";
				saida += "dm_Eixo='" + edVolta.getDm_Eixo() + "' ";
				saida += "nm_Aplicacao='" + edVolta.getNm_Aplicacao() + "' ";
				saida += "nm_Tipo_Pneu='" + edVolta.getNm_Tipo_Pneu() + "' ";
				saida += "nm_Eixo='" + edVolta.getNm_Eixo() + "' ";
				saida += "dm_Fora_Uso='" + edVolta.getDm_Fora_Uso() + "' ";
				saida += "nm_Fora_Uso='" + ("true".equals(edVolta.getDm_Fora_Uso())?"Fora de uso":" ") + "' ";
				saida += "dm_Substituir='" + edVolta.getDm_Substituir() + "' ";
				saida += "oid_Banda_Substituta='" + edVolta.getOid_Banda_Substituta() + "' ";
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

	public boolean checkDuplo ( BandaED ed, String acao) throws Excecoes {
		boolean ret = false;
		BandaED edChk = new BandaED();
		edChk.setCd_Banda(ed.getCd_Banda());
		edChk = this.getByRecord(edChk);
		
		System.out.println("edin="+ed.getOid_Banda()+" qbr="+edChk.getOid_Banda() );
		
		if ("I".equals(acao) && edChk.getOid_Banda()>0)
			ret = true;
		else
			if ("A".equals(acao) && edChk.getOid_Banda()>0 ) {
				if (ed.getOid_Banda()!=edChk.getOid_Banda() )
					ret = true ;
			}
		return ret;
	}

	public boolean checkEmUso ( BandaED ed ) throws Excecoes {
		try {
			boolean achei=false;
			// Procura na recapagem
			this.inicioTransacao();
			Recapagem_GarantidaED recED = new Recapagem_GarantidaED();
			recED.setOid_Banda(ed.getOid_Banda());
			achei=(new Recapagem_GarantidaRN(this.sql).lista(recED).size()>0 ? true : false);
			return (achei); 
		} finally {
			this.fimTransacao(false);
		}
	}

}
