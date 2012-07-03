// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name: RelatorioJasper.java

package com.master.relatorio.relatorioJasper;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import com.master.relatorio.InterfaceRelatorio;

import dori.jasper.engine.JRBand;
import dori.jasper.engine.JRElement;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRExporter;
import dori.jasper.engine.JRExporterParameter;
import dori.jasper.engine.JasperCompileManager;
import dori.jasper.engine.JasperExportManager;
import dori.jasper.engine.JasperFillManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JasperPrintManager;
import dori.jasper.engine.JasperReport;
import dori.jasper.engine.design.JRDesignBand;
import dori.jasper.engine.design.JRDesignField;
import dori.jasper.engine.design.JRDesignStaticText;
import dori.jasper.engine.design.JasperDesign;
import dori.jasper.engine.export.JRCsvExporter;
import dori.jasper.engine.export.JRHtmlExporter;
import dori.jasper.engine.export.JRXlsExporter;
import dori.jasper.engine.xml.JRXmlLoader;
import dori.jasper.view.JasperViewer;

// Referenced classes of package com.master.relatorio.relatorioJasper:
//            ObjetoJRDataSource

public class RelatorioJasper extends InterfaceRelatorio {

    public RelatorioJasper() throws Exception {
        String arquivoReport = null;
        try {
            arquivoReport = System.getProperty("REPORT");
            if (arquivoReport != null) {
                Properties p = new Properties();
                p.load(new FileInputStream(arquivoReport));
                setPathReport(String.valueOf(String.valueOf(p.getProperty("pr.Path_Reports"))) + String.valueOf(String.valueOf(File.separator)));
                setPathImagem(String.valueOf(String.valueOf(p.getProperty("pr.Path_Imagens"))) + String.valueOf(String.valueOf(File.separator)));
            }
            setNomeArquivoPdf(null);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void listaRelatorioHTMLParaByte(Map parametros, ArrayList colecaoED) throws Exception {
        if (getPathReport() == null)
            throw new Exception("Propriedade pathReport não especificada.");
        parametros.put("PATH_IMAGENS", getPathImagem());
        parametros.put("PATH_SUBREPORT", getPathReport());
        parametros.put("P_DTHORA_EMISSAO", new Date());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRExporter exporter = new JRHtmlExporter();
        ObjetoJRDataSource objJR = new ObjetoJRDataSource();
        objJR.setArrayED(colecaoED);
        JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
        setRelatorioBytes(baos.toByteArray());
    }

    public void listaRelatorioPdfParaByte(Map parametros, ArrayList colecaoED) throws Exception {
        try {
            if (getPathReport() == null)
                throw new Exception("Propriedade pathReport não especificada.");
            parametros.put("PATH_IMAGENS", getPathImagem());
            parametros.put("PATH_SUBREPORT", getPathReport());
            parametros.put("P_DTHORA_EMISSAO", new Date());
            ObjetoJRDataSource objJR = new ObjetoJRDataSource();
            objJR.setArrayED(colecaoED);
            JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
            setRelatorioBytes(JasperExportManager.exportReportToPdf(jasperPrint));
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void listaRelatorioPdfParaArquivo(Map parametros, ArrayList colecaoED) throws Exception {
        try {
            if (getPathReport() == null)
                throw new Exception("Propriedade pathReport não especificada.");
            parametros.put("PATH_IMAGENS", getPathImagem());
            parametros.put("PATH_SUBREPORT", getPathReport());
            parametros.put("P_DTHORA_EMISSAO", new Date());
            ObjetoJRDataSource objJR = new ObjetoJRDataSource();
            objJR.setArrayED(colecaoED);
            JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
            String path;
            if (getPathArquivoPdf() != null)
                path = getPathArquivoPdf();
            else path = getPathReport();
            if (getNomeArquivoPdf() != null)
                JasperExportManager.exportReportToPdfFile(jasperPrint, String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(path)))).append(getNomeArquivoPdf()).append(".pdf"))));
            else JasperExportManager.exportReportToPdfFile(jasperPrint, String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(path)))).append(getNomeArquivo()).append(".pdf"))));
            
            //JasperPrintManager.printReport(jasperPrint, false);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public void listaRelatorioParaImpressora(Map parametros, ArrayList colecaoED) throws Exception {
        try {
            if (getPathReport() == null)
                throw new Exception("Propriedade pathReport não especificada.");
            parametros.put("PATH_IMAGENS", getPathImagem());
            parametros.put("PATH_SUBREPORT", getPathReport());
            parametros.put("P_DTHORA_EMISSAO", new Date());
            ObjetoJRDataSource objJR = new ObjetoJRDataSource();
            objJR.setArrayED(colecaoED);
            JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public void listaRelatorioJasperView(Map parametros, ArrayList colecaoED) throws Exception {
        try {
            if (getPathReport() == null)
                throw new Exception("Propriedade pathReport não especificada.");
            parametros.put("PATH_IMAGENS", getPathImagem());
            parametros.put("PATH_SUBREPORT", getPathReport());
            parametros.put("P_DTHORA_EMISSAO", new Date());
            ObjetoJRDataSource objJR = new ObjetoJRDataSource();
            objJR.setArrayED(colecaoED);
            JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
            JasperViewer jv = new JasperViewer(jasperPrint, true); 
            jv.show();
            jv.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void listaRelatorioCsvParaByte(Map parametros, ArrayList colecaoED) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRExporter exporter = new JRCsvExporter();
        ObjetoJRDataSource objJR = new ObjetoJRDataSource();
        objJR.setArrayED(colecaoED);
        JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
        setRelatorioBytes(baos.toByteArray());
    }
    
    public void listaRelatorioXlsParaByte(Map parametros, ArrayList colecaoED) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRExporter exporter = new JRXlsExporter();
        ObjetoJRDataSource objJR = new ObjetoJRDataSource();
        objJR.setArrayED(colecaoED);
        JasperPrint jasperPrint = JasperFillManager.fillReport(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getPathReport())))).append(getNomeArquivo()).append(".jasper"))), parametros, objJR);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
        setRelatorioBytes(baos.toByteArray());
    }
    
    public static void main(String[] args) {
    	try {
			//final JasperDesign jasperDesign = JRXmlLoader.load("C:/relatorios/teste.jrxml");
    		final JasperDesign jasperDesign = JRXmlLoader.load("C:/cpoint/workspace/gtt/webroot/relatoriosJasper/xml/tif201.jrxml");
			final JRBand summary = jasperDesign.getSummary();
			final JRElement pneu01 = summary.getElementByKey("pneu01");
			System.out.println(pneu01.getX());
			pneu01.setX(200);
			
			
			
			// NO DATA (No JasperViewer não mostra, na web mostraria)
			JRDesignBand jrDesignband = new JRDesignBand();
			jrDesignband.setHeight(100);
			JRDesignStaticText staticText = new JRDesignStaticText();
			staticText.setX(10);
			staticText.setY(5);
			staticText.setWidth(300);
			staticText.setHeight(15);
			staticText.setBackcolor(Color.BLACK);
			staticText.setText("Sem registros para compor o relatório!");
			jrDesignband.addElement(staticText);
			jasperDesign.setPageFooter(jrDesignband);
			
			//addField("VALADAS", String.class, jasperDesign);

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new ObjetoJRDataSource());
			//JasperPrint jasperPrint = JasperFillManager.fillReport("C:/relatorios/teste.jasper",new HashMap(),new ObjetoJRDataSource());
			JasperViewer viewer = new JasperViewer(jasperPrint,false);
			viewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
			viewer.setVisible(true);  
		} catch (JRException e) {
			e.printStackTrace();
		}
    }
    
    /**
    * Adiciona campos ao relatorio
    * @param name
    * @param clazz
    * @param jasperDesign
    * @throws JRException
    */
    private static void addField(String name, Class clazz, JasperDesign jasperDesign) throws JRException {
    	JRDesignField field = new JRDesignField();

    	field.setName(name);
    	field.setValueClass(clazz);
    	field.setDescription("VALADAS");
    	jasperDesign.addField(field);
    }
}