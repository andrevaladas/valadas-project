package com.master.ed.relatorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.WindowConstants;

import com.master.ed.RelatorioED;
import com.master.relatorio.relatorioJasper.ObjetoJRDataSource;
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

			final HashMap<String,String> map = new HashMap<String,String>();
			map.put("RELATORIO", "tif201");
			map.put("USUARIO", "USUARIO");
			map.put("DESC_FILTER", "FILTRO");
			map.put("EMPRESA", "EMPRESA");
			map.put("TITULO", "TITULO");
			map.put("BASE_PATH", "TESTE");
    		map.put("PATH_SUBLIST", Parametro_FixoED.getInstancia().getPATH_RELATORIOS());
    		map.put("PATH_IMAGENS", Parametro_FixoED.getInstancia().getPATH_IMAGENS());
    		map.put("PATH_RELATORIOS", Parametro_FixoED.getInstancia().getPATH_RELATORIOS());

			final ObjetoJRDataSource objJR = new ObjetoJRDataSource();
            objJR.setArrayED(new InspecoesList().getVeiculoTest());

			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, objJR);
			final JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			viewer.setVisible(true);
		} catch (final JRException e) {
			e.printStackTrace();
		}
	}
}