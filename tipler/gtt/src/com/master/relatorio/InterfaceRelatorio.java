// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov Date: 12/12/2003
// 15:32:27
//Home Page : http://members.fortunecity.com/neshkov/dj.html - Check often for
// new version!
//Decompiler options: packimports(3)
//Source File Name: InterfaceRelatorio.java

package com.master.relatorio;

import java.util.ArrayList;
import java.util.Map;

public abstract class InterfaceRelatorio {

    public InterfaceRelatorio() {
    }


    public abstract void listaRelatorioJasperView(Map map, ArrayList arraylist) throws Exception;
    
    public abstract void listaRelatorioParaImpressora(Map map, ArrayList arraylist) throws Exception;
    
    public abstract void listaRelatorioHTMLParaByte(Map map, ArrayList arraylist) throws Exception;
    
    public abstract void listaRelatorioPdfParaByte(Map map, ArrayList arraylist) throws Exception;

    public abstract void listaRelatorioPdfParaArquivo(Map map, ArrayList arraylist) throws Exception;

    public abstract void listaRelatorioXlsParaByte(Map map, ArrayList arraylist) throws Exception;

    public abstract void listaRelatorioCsvParaByte(Map map, ArrayList arraylist) throws Exception;

    public String getPathImagem() {
        return pathImagem;
    }

    public String getPathReport() {
        return pathReport;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public byte[] getRelatorioBytes() {
        return relatorioBytes;
    }

    public void setNomeArquivo(String nome) {
        nomeArquivo = nome;
    }

    public void setPathImagem(String path) {
        pathImagem = path;
    }

    public void setPathReport(String path) {
        pathReport = path;
    }

    public void setRelatorioBytes(byte bytes[]) {
        relatorioBytes = bytes;
    }

    public String getNomeArquivoPdf() {
        return nomeArquivoPdf;
    }

    public void setNomeArquivoPdf(String nomeArquivoPdf) {
        this.nomeArquivoPdf = nomeArquivoPdf;
    }

    public String getPathArquivoPdf() {
        return pathArquivoPdf;
    }

    public void setPathArquivoPdf(String pathArquivoPdf) {
        this.pathArquivoPdf = pathArquivoPdf;
    }

    private byte relatorioBytes[];
    private String nomeArquivo;
    private String pathImagem;
    private String pathReport;
    private String nomeArquivoPdf;
    private String pathArquivoPdf;
}