package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stas_Dimensao_SucateadoBD;
import com.master.ed.Stas_Dimensao_SucateadoED;
import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial STAS - Dimensoes sucateados
 * @serialData 05/2012
 */
/**
 * @author Regis
 *
 */
public class Stas_Dimensao_SucateadoRN extends Transacao {
	
	public Stas_Dimensao_SucateadoRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stas_Dimensao_SucateadoRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stas_Dimensao_SucateadoED inclui (Stas_Dimensao_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stas_Dimensao_SucateadoED toReturn = new Stas_Dimensao_SucateadoBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stas_Dimensao_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Dimensao_SucateadoBD (this.sql).altera (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	
	public void delete (Stas_Dimensao_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_Dimensao_SucateadoBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Stas_Dimensao_SucateadoED> lista (Stas_Dimensao_SucateadoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Dimensao_SucateadoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Stas_Dimensao_SucateadoED getByRecord (Stas_Dimensao_SucateadoED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_Dimensao_SucateadoBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}

	public void relatorioInspecao(Stas_Dimensao_SucateadoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
        	/**
        	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
        	
        	ed.setDm_Lingua(usuED.getDm_Lingua());
        	
            this.inicioTransacao();
            String nm_Filtro = "";
            
            // Cria a lista para a inspeção
            ArrayList<Stas_Dimensao_SucateadoED> lstEdVazio = new ArrayList<Stas_Dimensao_SucateadoED>();
            
            // Busca a inspeção
            Stas_Dimensao_SucateadoED inspLinha1 = new Stas_Dimensao_SucateadoBD(this.sql).getByRecord(ed);
            
            // Cria e busca a lista para o resumo final
            Stas_Dimensao_SucateadoED resumoED = new Stas_Dimensao_SucateadoED();
            resumoED.setOid_Cliente(inspLinha1.getOid_Cliente());
            resumoED.setDt_Inicio(inspLinha1.getDt_Inicio());
            resumoED.setDm_Lingua(usuED.getDm_Lingua());
            ArrayList<Stas_Dimensao_SucateadoED> lstResumo =new Stas_Dimensao_SucateadoBD(sql).listaResumo(resumoED);

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
    	Stas_Dimensao_SucateadoED ed = (Stas_Dimensao_SucateadoED)Obj;
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
		Stas_Dimensao_SucateadoED ed = (Stas_Dimensao_SucateadoED)Obj;
		// Pega a lingua do usuário 
		UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
		ed.setDm_Lingua(usuED.getDm_Lingua());
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			ed = this.inclui(ed);
			out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Dimensao_Sucateado() + "' /></ret>");
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Stas_Dimensao_SucateadoED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stas_Dimensao_SucateadoED edVolta = new Stas_Dimensao_SucateadoED();
			edVolta = (Stas_Dimensao_SucateadoED)lst.get(i);
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
	private String montaSaida(Stas_Dimensao_SucateadoED edVolta) {
		String saida = "<item ";
		saida += "oid_Dimensao_Sucateado ='" + edVolta.getOid_Dimensao_Sucateado() + "' ";
		saida += "oid_Analise ='" + edVolta.getOid_Analise() + "' ";
		saida += "oid_Pneu_Dimensao ='" + edVolta.getOid_Pneu_Dimensao() + "' ";
		saida += "nm_Pneu_Dimensao ='" + edVolta.getNm_Pneu_Dimensao() + "' ";
		saida += "nr_Twi ='" + FormataValor.formataValorBT(edVolta.getNr_Twi(), 1) + "' ";
		saida += "vl_Pneu_Novo ='" + FormataValor.formataValorBT(edVolta.getVl_Pneu_Novo(), 2) + "' ";
		saida += "vl_Recapagem ='" + FormataValor.formataValorBT(edVolta.getVl_Recapagem(), 2) + "' ";
		saida += "vl_Carcaca_R1 ='" + FormataValor.formataValorBT(edVolta.getVl_Carcaca_R1(), 2) + "' ";
		saida += "vl_Carcaca_R2 ='" + FormataValor.formataValorBT(edVolta.getVl_Carcaca_R2(), 2) + "' ";
		saida += "vl_Carcaca_R3 ='" + FormataValor.formataValorBT(edVolta.getVl_Carcaca_R3(), 2) + "' ";
		saida += "vl_Carcaca_R4 ='" + FormataValor.formataValorBT(edVolta.getVl_Carcaca_R4(), 2) + "' ";
		saida += "vl_Carcaca_R5 ='" + FormataValor.formataValorBT(edVolta.getVl_Carcaca_R5(), 2) + "' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
	}

}

