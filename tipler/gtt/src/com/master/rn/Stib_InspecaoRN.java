package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Stib_InspecaoBD;
import com.master.bd.Stib_Inspecao_ItemBD;
import com.master.bd.Stib_Item_A_InspecionarBD;
import com.master.ed.Stib_InspecaoED;
import com.master.ed.Stib_Inspecao_ItemED;
import com.master.ed.Stib_Item_A_InspecionarED;
import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.OLUtil;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.ed.Parametro_FixoED;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial STIB - Inspeção
 * @serialData 04/2012
 */
public class Stib_InspecaoRN extends Transacao {
	
	public Stib_InspecaoRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stib_InspecaoRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stib_InspecaoED inclui (Stib_InspecaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stib_InspecaoED toReturn = new Stib_InspecaoBD (this.sql).inclui (ed);
			// Incluir todas os itens de inspeção
			
			// Busca a lista dos itens a inspecionar e coloca na inspeção.
			Stib_Item_A_InspecionarED iAiED = new Stib_Item_A_InspecionarED();
			ArrayList<Stib_Item_A_InspecionarED> lstItensAInspecionar = new Stib_Item_A_InspecionarBD(this.sql).lista(iAiED);
			if(!lstItensAInspecionar.isEmpty())  {
				Iterator<Stib_Item_A_InspecionarED> itIaI = lstItensAInspecionar.iterator();
				while (itIaI.hasNext()) {
					iAiED = itIaI.next();
					Stib_Inspecao_ItemED iIED = new Stib_Inspecao_ItemED();
					iIED.setOid_Inspecao(toReturn.getOid_Inspecao());
					iIED.setNr_Ordem(iAiED.getNr_Ordem());
					iIED.setNm_Item_Inspecionado("E".equals(ed.getDm_Lingua())?iAiED.getNm_Item_A_Inspecionar_E():iAiED.getNm_Item_A_Inspecionar());
					iIED.setDm_Tipo(iAiED.getDm_Tipo());
					iIED.setDm_Situacao(false);
					iIED.setDm_Stamp(iAiED.getDm_Stamp());
					iIED.setDt_stamp(iAiED.getDt_stamp());
					iIED.setUsuario_Stamp(iAiED.getUsuario_Stamp());
					new Stib_Inspecao_ItemBD(this.sql).inclui (iIED);
				}
			}
			
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void altera (Stib_InspecaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			if (Utilitaria.doValida(ed.getDt_Fim())) {
				new Stib_InspecaoBD (this.sql).altera (ed);
			}
			// Atualiza os itens pegando do array
			Stib_Inspecao_ItemBD iIBD = new Stib_Inspecao_ItemBD (this.sql);
			OLUtil olUtil = new OLUtil();
			ArrayList<Stib_Inspecao_ItemED> lstInspecaoItens = olUtil.pegaArraydaRequest(ed.getArray());
			Iterator<Stib_Inspecao_ItemED> itIaI = lstInspecaoItens.iterator();
			while (itIaI.hasNext()) {
				Stib_Inspecao_ItemED iIED = itIaI.next();
				iIED.setDm_Stamp("A");
				iIED.setDt_stamp(ed.getDt_stamp());
				iIED.setUsuario_Stamp(ed.getUsuario_Stamp());
				iIBD.altera(iIED);
			}
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void alteraRelatorio (Stib_InspecaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_InspecaoBD (this.sql).alteraRelatorio(ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public void reabrir (Stib_InspecaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_InspecaoBD (this.sql).reabrirInspecao(ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public void delete (Stib_InspecaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stib_InspecaoBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public ArrayList<Stib_InspecaoED> lista (Stib_InspecaoED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stib_InspecaoBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}

	}

	public Stib_InspecaoED getByRecord (Stib_InspecaoED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stib_InspecaoBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	
    public void relatorioInspecao(Stib_InspecaoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
        	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
        	
        	ed.setDm_Lingua(usuED.getDm_Lingua());
        	
            this.inicioTransacao();
            String nm_Filtro = "";
            
            // Cria a lista para a inspeção
            ArrayList<Stib_InspecaoED> lstEdVazio = new ArrayList<Stib_InspecaoED>();
            
            // Busca a inspeção
            Stib_InspecaoED inspLinha1 = new Stib_InspecaoBD(this.sql).getByRecord(ed);
            
            // Cria e busca a lista para o resumo final
            Stib_InspecaoED resumoED = new Stib_InspecaoED();
            resumoED.setOid_Cliente(inspLinha1.getOid_Cliente());
            resumoED.setDt_Inicio(inspLinha1.getDt_Inicio());
            resumoED.setDm_Lingua(usuED.getDm_Lingua());
            ArrayList<Stib_InspecaoED> lstResumo =new Stib_InspecaoBD(sql).listaResumo(resumoED);

            // Busca a lista dos itens inspecionados
            Stib_Inspecao_ItemED iItens = new Stib_Inspecao_ItemED();
            iItens.setOid_Inspecao(ed.getOid_Inspecao());
            ArrayList<Stib_Inspecao_ItemED> lstItens = new Stib_Inspecao_ItemBD(sql).lista(iItens);
            
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
    	Stib_InspecaoED ed = (Stib_InspecaoED)Obj;
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
		Stib_InspecaoED ed = (Stib_InspecaoED)Obj;
		// Pega a lingua do usuário 
		UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
		ed.setDm_Lingua(usuED.getDm_Lingua());
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			if (this.getByRecord(ed).getOid_Inspecao()>0) {
				String retTexto=null;
				if ("E".equals(usuED.getDm_Lingua())) retTexto="Ya existe una inspección abierta para este cliente!";
				else retTexto="Já existe uma inspeção aberta para este cliente!";
				out.println("<ret><item oknok='"+retTexto+"' /></ret>");
			} else {
				ed.setOid_Usuario(usuED.getOid_Usuario());
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Inspecao() + "' /></ret>");
			}	
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("AR".equals(acao)) {
			this.alteraRelatorio(ed);
			out.println("<ret><item oknok='AROK' /></ret>");
		} else 
		if ("R".equals(acao)) {
			Stib_InspecaoED iED = new Stib_InspecaoED();
			iED.setOid_Cliente(ed.getOid_Cliente());
			iED.setDt_Inicio(ed.getDt_Inicio());
			if (this.getByRecord(iED).getOid_Inspecao()>0) {
				String retTexto=null;
				if ("E".equals(usuED.getDm_Lingua())) retTexto="Ya existe una inspección abierta para este cliente!";
				else retTexto="Já existe uma inspeção aberta para este cliente!";
				out.println("<ret><item oknok='"+retTexto+"' /></ret>");
			} else {
				this.reabrir(ed);
				out.println("<ret><item oknok='ROK' /></ret>");
			}
		} else 			
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else	
		if ("lookup".equals(acao)) {
			ArrayList<Stib_InspecaoED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Stib_InspecaoED edVolta = (Stib_InspecaoED)lst.get(i);
					saida = "<item ";
					saida += "oid_Inspecao ='" + edVolta.getOid_Inspecao() + "' ";
					saida += "oid_Concessionaria ='" + edVolta.getOid_Concessionaria() + "' ";
					saida += "oid_Cliente ='" + edVolta.getOid_Cliente() + "' ";
					saida += "oid_Usuario ='" + edVolta.getOid_Usuario() + "' ";
					saida += "dt_Inicio ='" + edVolta.getDt_Inicio() + "' ";
					saida += "dt_Fim ='" + edVolta.getDt_Fim() + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				String retTexto=null;
				if ("E".equals(usuED.getDm_Lingua())) retTexto="Item no encontrado !";
				else retTexto="Item não encontrado !";
				out.println("<ret><item oknok='"+retTexto+"'/></ret>");
			}		
		} else {
		out.println("<cad>");
		String saida = null;
		ArrayList<Stib_InspecaoED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stib_InspecaoED edVolta = new Stib_InspecaoED();
			edVolta = (Stib_InspecaoED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Inspecao ='" + edVolta.getOid_Inspecao() + "' ";
				saida += "oid_Concessionaria ='" + edVolta.getOid_Concessionaria() + "' ";
				saida += "oid_Cliente ='" + edVolta.getOid_Cliente() + "' ";
				saida += "nm_Razao_Social ='" + edVolta.getNm_Razao_Social() + "' ";
				saida += "oid_Usuario ='" + edVolta.getOid_Usuario() + "' ";
				saida += "dt_Inicio ='" + edVolta.getDt_Inicio() + "' ";
				saida += "dt_Fim ='" + edVolta.getDt_Fim() + "' ";
				saida += "tx_Inicial ='" + edVolta.getTx_Inicial_OL() + "' ";
				saida += "tx_Final ='" + edVolta.getTx_Final_OL() + "' ";
				saida += "tx_Assinatura1 ='" + edVolta.getTx_Assinatura1_OL() + "' ";
				saida += "tx_Assinatura2 ='" + edVolta.getTx_Assinatura2_OL() + "' ";
				saida += "nm_Signatario ='" + edVolta.getNm_Signatario() + "' ";
				saida += "nm_Tecnico ='" + edVolta.getNm_Tecnico() + "' ";
				saida += "nm_Usuario ='" + edVolta.getNm_Usuario() + "' ";
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

}
