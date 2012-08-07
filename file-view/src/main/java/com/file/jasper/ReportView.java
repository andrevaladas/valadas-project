package com.file.jasper;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import com.file.text.InputFileDeclared;

/**
 * Classe para o montagem e visualização do Relatório
 * 
 * @author andrevaladas
 */
public class ReportView {

	/**
	 * Chama JasperView para exibir relatório
	 * @param selectedFile
	 */
	public ReportView(final File selectedFile) {
		try {
			final InputStream fileImputStream = ClassLoader.getSystemResourceAsStream("valadas.jrxml");
			final JasperReport report = JasperCompileManager.compileReport(fileImputStream);

			final JRDataSource dataSource = new JRMapCollectionDataSource(getReportParams(selectedFile));

			final JasperPrint print = JasperFillManager.fillReport(report, null, dataSource);
			JasperViewer.viewReport(print, true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carrega parametros do Relatório
	 * @param selectedFile
	 * @return
	 */
	private List<Map<String, Object>> getReportParams(final File selectedFile) {

		final List<Map<String, Object>> dsParamResult = new ArrayList<Map<String, Object>>();

		/** Carrega dados do arquivo */
		final InputFileDeclared inputFileDeclared = new InputFileDeclared(selectedFile);
		final List<String> loadStrings = inputFileDeclared.loadStrings();

		/** Seta os parâmetros do relatório */
		for (final String string : loadStrings) {
			final Map<String, Object> data = new HashMap<String, Object>();
			data.put("master", string);
			dsParamResult.add(data);
		}
		return dsParamResult;
	}
}