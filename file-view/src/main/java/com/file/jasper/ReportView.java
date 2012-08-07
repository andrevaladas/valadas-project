package com.file.jasper;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import com.file.text.InputFileDeclared;

public class ReportView {

	public ReportView(final File selectedFile) {
		System.out.println(selectedFile.getName());
		final InputFileDeclared inputFileDeclared = new InputFileDeclared(selectedFile);
		final List<String> loadStrings = inputFileDeclared.loadStrings();
		//System.out.println(loadStrings.toString());

		JasperReport report = null;

		try {
			final InputStream fileImputStream = ClassLoader.getSystemResourceAsStream("valadas.jrxml");
			System.out.println("#################### 01: "+fileImputStream);
			report = JasperCompileManager.compileReport(fileImputStream);
		} catch (final Exception e1) {
			e1.printStackTrace();
		}

		final Map<String, Object> simpleMasterMap = new HashMap<String, Object>();
		simpleMasterMap.put("master", loadStrings.get(0));
		final List<Map<String, Object>> simpleMasterList = new ArrayList<Map<String, Object>>();
		simpleMasterList.add(simpleMasterMap);
		final JRDataSource dataSource = new JRMapCollectionDataSource(simpleMasterList);

		JasperPrint print = null;
		try {
			print = JasperFillManager.fillReport(report, null, dataSource);
		} catch (final JRException e) {
			e.printStackTrace();
		}
		JasperViewer.viewReport(print, true);
	}
}
