package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.BandaBD;
import com.master.bd.Pneu_NovoBD;
import com.master.bd.Stas_Pneu_SucateadoBD;
import com.master.ed.BandaED;
import com.master.ed.Pneu_NovoED;
import com.master.ed.Stas_Pneu_SucateadoED;
import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial STAS - Pneus sucateados
 * @serialData 05/2012
 */
/**
 * @author Regis
 *
 */
public class Stas_Pneu_SucateadoRN extends Transacao {
	
	public Stas_Pneu_SucateadoRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stas_Pneu_SucateadoRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stas_Pneu_SucateadoED inclui (Stas_Pneu_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stas_Pneu_SucateadoED toReturn = new Stas_Pneu_SucateadoBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stas_Pneu_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Pneu_SucateadoBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	
	public void delete (Stas_Pneu_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Pneu_SucateadoBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Stas_Pneu_SucateadoED> lista (Stas_Pneu_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Pneu_SucateadoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Stas_Pneu_SucateadoED getByRecord (Stas_Pneu_SucateadoED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Pneu_SucateadoBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	/*
	 * Dado um pneus sucateado vai buscar a profundidade inicial
	 * Se for um pneu recapado busca da banda se for pneu novo busca do pneu novo
	 * Recebe  Stas_Pneu_SucateadoED
	 * retorna double com o mm procurado
	 */
	private double getProfundidadeInicial (Stas_Pneu_SucateadoED ed ) throws Excecoes {
		inicioTransacao ();
		double mm =0.0;
		try {
			if (ed.getOid_Banda()>0) {
				BandaED bdED = new BandaED();
				bdED.setOid_Banda(ed.getOid_Banda());
				bdED = new BandaBD(this.sql).getByRecord(bdED);		
				mm = bdED.getNr_Profundidade();
			} else 
			if (ed.getOid_Modelo_Pneu()>0) {
				Pneu_NovoED pnED = new Pneu_NovoED();
				pnED.setOid_Pneu_Dimensao(ed.getOid_Pneu_Dimensao());
				pnED.setOid_Modelo_Pneu(ed.getOid_Modelo_Pneu());
				pnED = new Pneu_NovoBD(this.sql).getByRecord(pnED);		
				mm = pnED.getNr_Mm_Profundidade();
			}	
			return mm;
		}
		finally {
			fimTransacao (false);
		}
	}
    public void relatorioInspecao(Stas_Pneu_SucateadoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
        	/**
        	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
        	
        	ed.setDm_Lingua(usuED.getDm_Lingua());
        	
            this.inicioTransacao();
            String nm_Filtro = "";
            
            // Cria a lista para a inspeção
            ArrayList<Stas_Pneu_SucateadoED> lstEdVazio = new ArrayList<Stas_Pneu_SucateadoED>();
            
            // Busca a inspeção
            Stas_Pneu_SucateadoED inspLinha1 = new Stas_Pneu_SucateadoBD(this.sql).getByRecord(ed);
            
            // Cria e busca a lista para o resumo final
            Stas_Pneu_SucateadoED resumoED = new Stas_Pneu_SucateadoED();
            resumoED.setOid_Cliente(inspLinha1.getOid_Cliente());
            resumoED.setDt_Inicio(inspLinha1.getDt_Inicio());
            resumoED.setDm_Lingua(usuED.getDm_Lingua());
            ArrayList<Stas_Pneu_SucateadoED> lstResumo =new Stas_Pneu_SucateadoBD(sql).listaResumo(resumoED);

            // Busca a lista dos itens inspecionados
            Stas_Analise_ItemED iItens = new Stas_Analise_ItemED();
            iItens.setOid_Inspecao(ed.getOid_Inspecao());
            ArrayList<Stas_Analise_ItemED> lstItens = new Stas_Analise_ItemBD(sql).lista(iItens);
            
            // Coloca a lista dos itens na inspeção
            inspLinha1.setSublista(lstItens);
            inspLinha1.setSublista1(lstResumo);
            // Coloca a inspeção na lista
            lstEdVazio.add(inspLinha1);
            //            
            ed.setLista(lstEdVazio); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("tib201"); // Seta o nome do relatório

			HashMap<String,String> map = new HashMap<String,String>();
    		map.put("PATH_SUBLIST", Parametro_FixoED.getInstancia().getPATH_RELATORIOS());
    		map.put("TITULO",("E".equals(usuED.getDm_Lingua())?"Inspeción de Gomeria":"Inspeção de Borracharia")); // coloca o titulo na lingua correta
    		map.put("LBL_ITENS_INSPECIONADOS",("E".equals(usuED.getDm_Lingua())?"ITENS DE LA INSPECIÓN":"ITENS INSPECIONADOS")); // 
    		map.put("LBL_CONFORMIDADE",("E".equals(usuED.getDm_Lingua())?"CONFORMIDAD":"CONFORMIDADE")); //
    		map.put("LBL_RESUMO",("E".equals(usuED.getDm_Lingua())?"RESUMÉM - HISTÓRICO":"RESUMO - HISTÓRICO")); //
    		map.put("LBL_CLASSIFICACAO",("E".equals(usuED.getDm_Lingua())?"Classificación":"Classificação")); //
    		map.put("LBL_MAQUINAS",("E".equals(usuED.getDm_Lingua())?"Máquinas - Equipamientos":"Máquinas - Equipamentos")); //
    		map.put("LBL_PESSOAL",("E".equals(usuED.getDm_Lingua())?"Pessoal - Mano de obra":"Pessoal - Mão de obra")); //
    		map.put("LBL_GERAL",("E".equals(usuED.getDm_Lingua())?"G E N E R A L":"G E R A L")); //
    		map.put("LBL_DATA",("E".equals(usuED.getDm_Lingua())?"Fecha":"Data")); //
    		ed.setHashMap(map);
    		//
    		 
        	*/
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
    	Stas_Pneu_SucateadoED ed = (Stas_Pneu_SucateadoED)Obj;
    	ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorioInspecao(ed, request, response);	
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
		Stas_Pneu_SucateadoED ed = (Stas_Pneu_SucateadoED)Obj;
		// Pega a lingua do usuário 
		UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
		ed.setDm_Lingua(usuED.getDm_Lingua());
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			if (this.getByRecord(ed).getOid_Pneu_Sucateado()==0) {
				double mmInicial = this.getProfundidadeInicial(ed);
				if ( ed.getNr_Mm_Medido() > mmInicial ) {
					String retTexto=null;
					if ("E".equals(usuED.getDm_Lingua())) retTexto="Mm medido maior que o inicial. Mm inicial = " ;
					else retTexto="Mm medido maior que o inicial. Mm inicial = ";
					out.println("<cad><ret oknok='"+retTexto + FormataValor.formataValorBT(mmInicial,1)+ "!' /></cad>");
				} else {
					ed.setNr_Mm_Inicial(mmInicial);
					ed = this.inclui(ed);
					out.println("<cad>");
					out.println("<ret oknok='IOK' oid='" + ed.getOid_Pneu_Sucateado() + "' dm_Gravou_Dimensao='" + ed.isDm_Gravou_Dimensao() + "' />");
					ed = this.getByRecord(ed);
					out.println(montaSaida(ed));
					out.println("</cad>");
				}
			} else {
				out.println("<cad><ret oknok='Pneu já cadastrado para essa análise!' /></cad>");
			}
		} else 
		if ("A".equals(acao)) {
			// Busca um pneu com o mesmo fogo na analise
			Stas_Pneu_SucateadoED qbrED = new Stas_Pneu_SucateadoED();
			qbrED.setOid_Analise(ed.getOid_Analise());
			qbrED.setNr_Fogo(ed.getNr_Fogo());
			qbrED = this.getByRecord(qbrED);
			
			if (qbrED.getOid_Pneu_Sucateado()>0 && (qbrED.getOid_Pneu_Sucateado()!=ed.getOid_Pneu_Sucateado())) {
				out.println("<cad><ret oknok='Pneu já cadastrado para essa análise!' /></cad>");
			} else {
				this.altera(ed);
				out.println("<cad><ret oknok='AOK' /></cad>");
			}
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<cad><ret oknok='DOK' /></cad>");
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Stas_Pneu_SucateadoED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stas_Pneu_SucateadoED edVolta = new Stas_Pneu_SucateadoED();
			edVolta = (Stas_Pneu_SucateadoED)lst.get(i);
			if ("L".equals(acao)) {
				saida = montaSaida(edVolta);
			} 
			out.println(saida);
		}
		out.println("</cad>");
	}
	out.flush();
	out.close();
	}
	private String montaSaida(Stas_Pneu_SucateadoED edVolta) {
		String saida = "<item ";
		saida += "oid_Pneu_Sucateado ='" + edVolta.getOid_Pneu_Sucateado() + "' ";
		saida += "oid_Analise ='" + edVolta.getOid_Analise() + "' ";
		saida += "oid_Pneu_Dimensao ='" + edVolta.getOid_Pneu_Dimensao() + "' ";
		saida += "oid_Fabricante_Pneu ='" + edVolta.getOid_Fabricante_Pneu() + "' ";
		saida += "oid_Modelo_Pneu ='" + edVolta.getOid_Modelo_Pneu() + "' ";
		saida += "oid_Fabricante_Banda ='" + edVolta.getOid_Fabricante_Banda() + "' ";
		saida += "oid_Banda ='" + edVolta.getOid_Banda() + "' ";
		saida += "oid_Banda_Dimensao ='" + edVolta.getOid_Banda_Dimensao() + "' ";
		saida += "oid_Motivo_Sucata ='" + edVolta.getOid_Motivo_Sucata() + "' ";
		saida += "nr_Vida ='" + edVolta.getNr_Vida() + "' ";
		saida += "nr_Fogo ='" + edVolta.getNr_Fogo() + "' ";
		saida += "nr_Mm_Inicial ='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Inicial(), 1) + "' ";
		saida += "nr_Mm_Medido ='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Medido(), 1) + "' ";
		saida += "nr_Mm_Restante ='" + FormataValor.formataValorBT(edVolta.getNr_Mm_Inicial()-edVolta.getNr_Mm_Medido(), 1) + "' ";
		saida += "vl_Pneu ='" + FormataValor.formataValorBT(edVolta.getVl_Pneu(), 2) + "' ";
		saida += "nm_Pneu_Dimensao ='" + edVolta.getNm_Pneu_Dimensao() + "' ";
		saida += "nm_Modelo_Pneu ='" + edVolta.getNm_Modelo_Pneu() + "' ";
		saida += "nm_Banda ='" + edVolta.getNm_Banda() + "' ";
		saida += "nm_Motivo_Sucata ='" + edVolta.getNm_Motivo_Sucata() + "' ";
		saida += "nm_Vida ='" + edVolta.getNm_Vida() + "' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}

}
