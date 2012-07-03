package com.master.rn;

import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.PieChartDataSet;
import org.jCharts.nonAxisChart.PieChart2D;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PieChart2DProperties;
import org.jCharts.types.PieLabelType;

import com.master.bd.Stas_AnaliseBD;
import com.master.bd.Stas_Pneu_SucateadoBD;
import com.master.ed.Stas_AnaliseED;
import com.master.ed.Stas_Motivo_SucataED;
import com.master.ed.Stas_Pneu_SucateadoED;
import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.Utilitaria;
import com.master.util.Valor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.ed.Parametro_FixoED;
import com.master.util.rl.JasperRL;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author Régis Steigleder
 * @serial STAS - Análise
 * @serialData 05/2012
 */
public class Stas_AnaliseRN extends Transacao {
	
	public Stas_AnaliseRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Stas_AnaliseRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
	public Stas_AnaliseED inclui (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			Stas_AnaliseED toReturn = new Stas_AnaliseBD (this.sql).inclui (ed);
			fimTransacao (true);
			return toReturn;
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void altera (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			if (Utilitaria.doValida(ed.getDt_Fim())) {
				new Stas_AnaliseBD (this.sql).altera (ed);
			}
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void alteraRelatorio (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			// Converte de string para long
			ed.setOid_Analise(Long.parseLong(ed.getOid_Analise_In()));
			new Stas_AnaliseBD (this.sql).alteraRelatorio(ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public void reabrir (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_AnaliseBD (this.sql).reabrirAnalise(ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	
	public void delete (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			new Stas_AnaliseBD (this.sql).delete (ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void encerrar (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			calcularSemTransacao(ed);
			new Stas_AnaliseBD(this.sql).encerra(ed);
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}

	public void calcular (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			calcularSemTransacao(ed) ;
			fimTransacao (true);
		}
		catch (Excecoes e) {
			abortaTransacao ();
			throw e;
		}
	}
	private void calcularSemTransacao(Stas_AnaliseED ed) throws Excecoes {
		double vlPerdido=0;
		// Pega os pneus sucateados da analise
		Stas_Pneu_SucateadoED psED = new Stas_Pneu_SucateadoED();
		ArrayList<Stas_Pneu_SucateadoED> lstPS = new ArrayList<Stas_Pneu_SucateadoED>();
		psED.setOid_Analise(ed.getOid_Analise());
		Stas_Pneu_SucateadoBD psBD = new Stas_Pneu_SucateadoBD(this.sql);
		lstPS = psBD.lista(psED);
		Iterator<Stas_Pneu_SucateadoED> psIt = lstPS.iterator();
		// calcula o valor da perda de cada um dos pneus da análise
		while(psIt.hasNext()) {
			psED = psIt.next();
			if (psED.getNr_Vida()==0) {
				// Pneu novo
				double vlBorracha = psED.getVl_Pneu_Novo() - psED.getVl_Carcaca_R1();
				if ( psED.getNr_Mm_Medido()>psED.getNr_Twi())
					vlPerdido=psED.getVl_Carcaca_R1() + ( vlBorracha / ( psED.getNr_Mm_Inicial()-psED.getNr_Twi() ) * (psED.getNr_Mm_Medido()-psED.getNr_Twi()) );
				else
					vlPerdido=psED.getVl_Carcaca_R1();
			} else {
				double vlCarcaca=0;
				// Pega o valor da carcaça cfe a vida
				if (psED.getNr_Vida()==1)
					vlCarcaca=psED.getVl_Carcaca_R2();
				else if (psED.getNr_Vida()==2)
					vlCarcaca=psED.getVl_Carcaca_R3();
				else if (psED.getNr_Vida()==3)
					vlCarcaca=psED.getVl_Carcaca_R4();
				else if (psED.getNr_Vida()==4)
					vlCarcaca=psED.getVl_Carcaca_R5();
				else
					vlCarcaca=psED.getVl_Carcaca_R5();
				// Calcula o valor
				if ( psED.getNr_Mm_Medido()>psED.getNr_Twi())
					vlPerdido=vlCarcaca + ( psED.getVl_Recapagem() / ( psED.getNr_Mm_Inicial()-psED.getNr_Twi() ) * (psED.getNr_Mm_Medido()-psED.getNr_Twi()) );
				else
					vlPerdido=vlCarcaca;
			}
			vlPerdido=Valor.round(vlPerdido, 2);
			// Atualiza o valor da sucata 
			psED.setVl_Pneu(vlPerdido);
			psED.setUsuario_Stamp(ed.getUsuario_Stamp());
			psED.setDm_Stamp("A");
			psED.setDt_stamp(ed.getDt_stamp());
			psBD.altera(psED);
		}
	}
	
	public ArrayList<Stas_AnaliseED> lista (Stas_AnaliseED ed) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_AnaliseBD (this.sql).lista (ed);
		}
		finally {
			this.fimTransacao (false);
		}
	}

	public Stas_AnaliseED getByRecord (Stas_AnaliseED ed ) throws Excecoes {
		inicioTransacao ();
		try {
			return new Stas_AnaliseBD (this.sql).getByRecord (ed);
		}
		finally {
			fimTransacao (false);
		}
	}
	
	public PieChart2D criaGraficoAnalise() throws ChartDataException {

		PieChart2DProperties properties;
		LegendProperties legendProperties;
		ChartProperties chartProperties;

		int width = 850;
		int height = 360;
		
		properties = new PieChart2DProperties();
		properties.setPieLabelType(PieLabelType.VALUE_LABELS);
		
		legendProperties = new LegendProperties();
		legendProperties.setNumColumns( 1 );
		legendProperties.setPlacement( LegendProperties.RIGHT );
		
		chartProperties = new ChartProperties();
		
		double[] data = new double[]{20, 30, 50};
		Paint[] paints = new Paint[]{Color.blue, Color.red, Color.yellow};
		String[] labels = {"Piriri xcxcxcc pororo", "Caraca do xcxcxcc careca", "blha bla blha"};
		PieChartDataSet pieDS = new PieChartDataSet( "Análise de sucata!", data, labels, paints, properties );


		PieChart2D pieChart2D = new PieChart2D( pieDS, legendProperties, chartProperties, width, height );
		
		return pieChart2D;
	}
	
    public void relatorioAnalise(Stas_AnaliseED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
        	this.inicioTransacao();
            /**
        	 * O que tem que fazer aqui:
        	 * Buscar o registro da analise.
        	 * Buscar os pneus analisados, agrupar e gerar o relatório
        	 * 
        	 * O relatório terá uma lista com os motivos classificados e seus textos de descricção causa e recomendação alem da foto.	
        	 * Cada motivo tera uma tabela com o somatorio dos mm gastos e do valor perdido para cada vida e uma segunda tabela com o percetual para a marca ou banda utilizada
        	 * Ao final uma grade geral e dois gráficos ? de quantidade e valor indicando os motivosnuma terceira tabela relativa aos gráficos. 
        	 **/
        	Stas_AnaliseBD asBD = new Stas_AnaliseBD(sql);
        	
        	UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
        	ed.setDm_Lingua(usuED.getDm_Lingua());

        	String nm_Filtro = "";
            
            // Pega os totais das analises
            Stas_AnaliseED edTot = asBD.getTotalAnalise(ed);
            
            // Pega a lista dos motivos
            ArrayList<Stas_Motivo_SucataED> lstEdMotivo = asBD.getMotivosAnalise(ed, edTot);
            // Para cada motivo e seus pneus sucateados 
            Iterator<Stas_Motivo_SucataED> iMs = lstEdMotivo.iterator();
            while (iMs.hasNext()) {
            	System.out.println("aqui vamos calcular a tabela " );
            	Stas_Motivo_SucataED msED = iMs.next();
            	// Pega a lista por marca
                ArrayList<Stas_Motivo_SucataED> lstEdMarca = asBD.getMotivosMarca(ed, msED);
                msED.setSublista(lstEdMarca);
            }
            
            //Cria a lista para a analise
            ArrayList<Stas_AnaliseED> lstEdVazio = new ArrayList<Stas_AnaliseED>();

            // Busca a analise
            Stas_AnaliseED analiseED = new Stas_AnaliseED();
            if (!ed.getOid_Analise_In().contains(",")) {
            	analiseED.setOid_Analise(Long.parseLong(ed.getOid_Analise_In()));
            	analiseED=asBD.getByRecord(analiseED);
            } else {
            	// Pega da session
            }
            // Coloca a lista dos motivos na sublista
            analiseED.setSublista(lstEdMotivo);
            // Adiciona a analise com a sublista na lista	
            lstEdVazio.add(analiseED);
            
            ed.setLista(lstEdVazio); // Joga a lista com a unica análise no ed básico para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("tas201"); // Seta o nome do relatório
			
			HashMap<String,String> map = new HashMap<String,String>();
    		map.put("PATH_SUBLIST", Parametro_FixoED.getInstancia().getPATH_RELATORIOS());
    		map.put("TITULO",("E".equals(usuED.getDm_Lingua())?"Análise de Scrap":"Análise de Sucata")); // coloca o titulo na lingua correta
    		map.put("LBL_TABELA_MARCA",("E".equals(usuED.getDm_Lingua())?"Percentual por marca":"Percentual por marca")); //
    		map.put("LBL_ITENS_INSPECIONADOS",("E".equals(usuED.getDm_Lingua())?"ITENS DE LA INSPECIÓN":"ITENS INSPECIONADOS")); // 
    		map.put("LBL_CONFORMIDADE",("E".equals(usuED.getDm_Lingua())?"CONFORMIDAD":"CONFORMIDADE")); //
    		map.put("LBL_RESUMO",("E".equals(usuED.getDm_Lingua())?"RESUMÉM - HISTÓRICO":"RESUMO - HISTÓRICO")); //
    		map.put("LBL_CLASSIFICACAO",("E".equals(usuED.getDm_Lingua())?"Classificación":"Classificação")); //
    		map.put("LBL_MAQUINAS",("E".equals(usuED.getDm_Lingua())?"Máquinas - Equipamientos":"Máquinas - Equipamentos")); //
    		map.put("LBL_PESSOAL",("E".equals(usuED.getDm_Lingua())?"Pessoal - Mano de obra":"Pessoal - Mão de obra")); //
    		map.put("LBL_GERAL",("E".equals(usuED.getDm_Lingua())?"G E N E R A L":"G E R A L")); //
    		map.put("LBL_DATA",("E".equals(usuED.getDm_Lingua())?"Fecha":"Data")); //
    		ed.setHashMap(map);
        	
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
    	Stas_AnaliseED ed = (Stas_AnaliseED)Obj;
    	ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorioAnalise(ed, request, response);	
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
		Stas_AnaliseED ed = (Stas_AnaliseED)Obj;
		// Pega a lingua do usuário 
		UsuarioED usuED = (UsuarioED)request.getSession(true).getAttribute("usuario");
		ed.setDm_Lingua(usuED.getDm_Lingua());
		//Prepara a saída
		ed.setMasterDetails(request);
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if ("I".equals(acao) ) {
			if (this.getByRecord(ed).getOid_Analise()>0) {
				String retTexto=null;
				if ("E".equals(usuED.getDm_Lingua())) retTexto="Ya existe una análise abierta para este cliente!";
				else retTexto="Já existe uma análise aberta para este cliente!";
				out.println("<ret><item oknok='"+retTexto+"' /></ret>");
			} else {
				ed.setOid_Usuario(usuED.getOid_Usuario());
				ed = this.inclui(ed);
				out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Analise() + "' /></ret>");
			}	
		} else 
		if ("A".equals(acao)) {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("AR".equals(acao)) {
			if (!ed.getOid_Analise_In().contains(",")) {
				this.alteraRelatorio(ed);
			} else {
				// grava na session pois esse não será gravado no banco
			}
			out.println("<ret><item oknok='AROK' /></ret>");
		} else 
		if ("R".equals(acao)) {
			Stas_AnaliseED iED = new Stas_AnaliseED();
			iED.setOid_Cliente(ed.getOid_Cliente());
			iED.setDt_Inicio(ed.getDt_Inicio());
			if (this.getByRecord(iED).getOid_Analise()>0) {
				String retTexto=null;
				if ("E".equals(usuED.getDm_Lingua())) retTexto="Ya existe una inspección abierta para este cliente!";
				else retTexto="Já existe uma inspeção aberta para este cliente!";
				out.println("<ret><item oknok='"+retTexto+"' /></ret>");
			} else {
				this.reabrir(ed);
				out.println("<ret><item oknok='ROK' /></ret>");
			}
		} else 			
		if ("E".equals(acao)) {
			this.encerrar(ed);
			out.println("<cad><ret oknok='EOK' /></cad>");
		} else	
		if ("C".equals(acao)) {
			this.calcular(ed);
			out.println("<cad><ret oknok='COK' /></cad>");
		} else	
		if ("D".equals(acao)) {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else	
		if ("lookup".equals(acao)) {
			ArrayList<Stas_AnaliseED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					Stas_AnaliseED edVolta = (Stas_AnaliseED)lst.get(i);
					saida = "<item ";
					saida += "oid_Analise ='" + edVolta.getOid_Analise() + "' ";
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
		ArrayList<Stas_AnaliseED> lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			Stas_AnaliseED edVolta = new Stas_AnaliseED();
			edVolta = (Stas_AnaliseED)lst.get(i);
			if ("L".equals(acao)) {
				saida = "<item ";
				saida += "oid_Analise ='" + edVolta.getOid_Analise() + "' ";
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
				saida += "nr_Quantidade_Pneus ='" + edVolta.getNr_Quantidade_Pneus() + "' ";
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
