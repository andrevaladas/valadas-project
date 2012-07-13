package com.master.ed.relatorio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.WindowConstants;

import com.master.ed.RelatorioED;
import com.master.ed.Stif_InspecaoED;
import com.master.relatorio.relatorioJasper.ObjetoJRDataSource;
import com.master.rl.Stif_InspecaoRL;
import com.master.util.Utilitaria;
import com.master.util.ed.Parametro_FixoED;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperCompileManager;
import dori.jasper.engine.JasperFillManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JasperReport;
import dori.jasper.engine.design.JasperDesign;
import dori.jasper.engine.xml.JRXmlLoader;
import dori.jasper.view.JasperViewer;

/**
 * @author Andre Valadas
 */
public class InspecoesList extends RelatorioED {

	private static final long serialVersionUID = -6265715332144157301L;

	private List<Veiculo> veiculos = new ArrayList<Veiculo>();

	public List<Veiculo> getVeiculos() {
		return this.veiculos;
	}

	public void setVeiculos(final List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public void addVeiculo(final Veiculo veiculo) {
		getVeiculos().add(veiculo);
	}

	public ArrayList<Veiculo> getVeiculoTest() {
		final Pneu de = new Pneu();
		de.setNr_Fogo(12345);
		de.setDm_Vida_Pneu("Novo");
		de.setNr_Pressao(100);
		de.setNr_Mm_Sulco(9.5);
		de.setNm_Pneu_Dimensao("PIRELLI");
		de.setNm_Modelo_Pneu("MODELO DE");
		de.setNm_Banda("TIPLER");
		de.addNr_Problemas_Rodas("2");
		de.addNr_Problemas_Valvulas("5");
		de.addNr_Problemas_Pneus("7");
		de.addNr_Problemas_Outros("9");

		final Pneu dd = new Pneu();
		dd.setNr_Fogo(45678);
		dd.setDm_Vida_Pneu("R1");
		dd.setNr_Pressao(50);
		dd.setNr_Mm_Sulco(4.5);
		dd.setNm_Pneu_Dimensao("GODYEARRRRRRRRRRRRR");
		dd.setNm_Modelo_Pneu("MODELO DD");
		dd.setNm_Banda("TIPLER");
		dd.addNr_Problemas_Rodas("0");
		dd.addNr_Problemas_Valvulas("2");
		dd.addNr_Problemas_Pneus("4");
		dd.addNr_Problemas_Outros("6");

		final Veiculo veiculo = new Veiculo();
		veiculo.setDm_Tipo_Chassis(10);
		veiculo.setNr_Pressao_Eixo_1(100);
		veiculo.setNr_Pressao_Eixo_2(50);
		veiculo.setNr_Pressao_Eixo_3(30);
		veiculo.setNr_Pressao_Eixo_4(77);
		
		veiculo.setNr_Frota("121");
		veiculo.setNr_Odometro(90000);
		veiculo.setNr_Placa("IOV-9152");
		veiculo.setDm_Tipo_Veiculo(1);
		veiculo.setNm_Modelo_Veiculo("CHEVROLET");

		/** pneus */
		veiculo.setDe(de);
		veiculo.setDd(dd);
		addVeiculo(veiculo);

		final Veiculo veiculo2 = new Veiculo();
		veiculo2.setDm_Tipo_Chassis(10);
		veiculo2.setNr_Pressao_Eixo_1(10);
		veiculo2.setNr_Pressao_Eixo_2(20);
		veiculo2.setNr_Pressao_Eixo_3(30);
		veiculo2.setNr_Pressao_Eixo_4(40);
		veiculo2.setNr_Frota("121");
		veiculo2.setNr_Odometro(90000);
		veiculo2.setNr_Placa("IOV-7777");
		veiculo2.setDm_Tipo_Veiculo(5);
		veiculo2.setNm_Modelo_Veiculo("CHEVROLET");
		/** pneus */
		veiculo2.setDe(de);
		veiculo2.setDd(dd);
		addVeiculo(veiculo2);

		return new ArrayList<Veiculo>(getVeiculos());
	}
	
	public static void main(final String[] args) {
		try {
			final JasperDesign jasperDesign = JRXmlLoader.load("C:/cpoint/workspace/gtt/webroot/relatoriosJasper/xml/tif201.jrxml");
			final JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			final ObjetoJRDataSource veiculos = new ObjetoJRDataSource();
			final ObjetoJRDataSource pressoes = new ObjetoJRDataSource();
			final ObjetoJRDataSource valvulas = new ObjetoJRDataSource();
			final ObjetoJRDataSource rodas = new ObjetoJRDataSource();
			final ObjetoJRDataSource outros = new ObjetoJRDataSource();
			final ObjetoJRDataSource pneus = new ObjetoJRDataSource();
			final ObjetoJRDataSource totalPerdas = new ObjetoJRDataSource();
			final ObjetoJRDataSource resumos = new ObjetoJRDataSource();

			final Stif_InspecaoRL inspecaoRL = new Stif_InspecaoRL();

			/** Veículos */
			veiculos.setArrayED(new ArrayList<Veiculo>(inspecaoRL.relatorio(3)));

			/** Inspeção */
			final Stif_InspecaoED inspecaoED = inspecaoRL.getInspecaoED();

			/** Itens */
			pressoes.setArrayED(new ArrayList<Item>(inspecaoRL.getPressoes()));
			valvulas.setArrayED(new ArrayList<Item>(inspecaoRL.getValvulas()));
			rodas.setArrayED(new ArrayList<Item>(inspecaoRL.getRodas()));
			outros.setArrayED(new ArrayList<Item>(inspecaoRL.getOutros()));
			pneus.setArrayED(new ArrayList<Item>(inspecaoRL.getPneus()));
			totalPerdas.setArrayED(new ArrayList<Item>(inspecaoRL.getTotalPerdas()));
			resumos.setArrayED(new ArrayList<Resumo>(inspecaoRL.getResumos()));

			final HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("nm_Local_Data", inspecaoED.getEmpresaED().getNm_Cidade()+", "+new SimpleDateFormat("d 'de' MMMM 'de' yyyy").format(new Date()));
			map.put("nm_Razao_Social", inspecaoED.getEmpresaED().getNm_Razao_Social());
			map.put("nm_Local", inspecaoED.getEmpresaED().getNm_Cidade()+" - "+inspecaoED.getEmpresaED().getCd_Estado());
			if (Utilitaria.doValida(inspecaoED.getNm_Signatario())) {
				map.put("tx_Tratamento", inspecaoED.getNm_Signatario().toUpperCase().startsWith("SRA.", 0) ? "Prezada ":"Prezado ");
				map.put("nm_Signatario", inspecaoED.getNm_Signatario());
		    }
			map.put("tx_Inicial", inspecaoED.getTx_Inicial());
			map.put("tx_Final", inspecaoED.getTx_Final());
			map.put("tx_Assinatura1", inspecaoED.getTx_Assinatura1());
			map.put("tx_Assinatura2", inspecaoED.getTx_Assinatura2());

			map.put("countVeiculos", inspecaoRL.getCountVeiculos());
			map.put("countVeiculosComAnomalias", inspecaoRL.getCountVeiculosComAnomalias());
			map.put("countPneus", inspecaoRL.getCountPneus());
			map.put("countPneusComAnomalias", inspecaoRL.getCountPneusComAnomalias());
			map.put("valorTotalPressao", inspecaoRL.getValorTotalPressao());

			map.put("pressoes", pressoes);
			map.put("valvulas", valvulas);
			map.put("rodas", rodas);
			map.put("outros", outros);
			map.put("pneus", pneus);
			map.put("totalPerdas", totalPerdas);
			map.put("resumos", resumos);

			map.put("TITULO", "STIF - Relatório de Inspeção de Frota");
			map.put("PATH_SUBLIST", Parametro_FixoED.getInstancia().getPATH_RELATORIOS());

			map.put("RELATORIO", "tif201");
			map.put("USUARIO", "USUARIO");
			map.put("EMPRESA", "EMPRESA");
			//map.put("BASE_PATH", "TESTE");
			//map.put("PATH_IMAGENS", Parametro_FixoED.getInstancia().getPATH_IMAGENS());
			map.put("PATH_RELATORIOS", Parametro_FixoED.getInstancia().getPATH_RELATORIOS());

			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, veiculos);
			final JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			viewer.setVisible(true);
		} catch (final JRException e) {
			e.printStackTrace();
		}
	}
}