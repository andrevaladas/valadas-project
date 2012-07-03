// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name: ObjetoJRDataSource.java

package com.master.relatorio.relatorioJasper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

public class ObjetoJRDataSource implements JRDataSource {

    public ObjetoJRDataSource() {
        index = -1;
    }

    public ArrayList getArrayED() {
        return arrayED;
    }

    public void setArrayED(ArrayList aArrayED) {
        arrayED = aArrayED;
    }

    public void getJRDataSource() {
    }

    public boolean next() throws JRException {
        index++;
        return arrayED == null ? false : index < arrayED.size();
    }

    public Object getFieldValue(JRField nome_Campo) throws JRException {
        Method method = null;
        Object ref = null;
        Object obj = null;
        try {
            String fieldName = nome_Campo.getName();
            Object objetoED = arrayED.get(index);
            fieldName = String.valueOf(String.valueOf(fieldName.substring(0, 1).toUpperCase())) + String.valueOf(String.valueOf(fieldName.substring(1)));
            method = objetoED.getClass().getMethod("get".concat(String.valueOf(String.valueOf(fieldName))), null);
            obj = method.invoke(objetoED, null);
            if (obj instanceof Calendar)
                obj = ((Calendar) obj).getTime();
            if (obj instanceof ArrayList) {
                ObjetoJRDataSource subreport = new ObjetoJRDataSource();
                subreport.setArrayED((ArrayList) obj);
                ObjetoJRDataSource objetojrdatasource = subreport;
                ObjetoJRDataSource objetojrdatasource1 = objetojrdatasource;
                return objetojrdatasource1;
            } else {
                Object obj1 = obj;
                Object obj2 = obj1;
                return obj2;
            }
        } catch (Exception exc) {
            throw new JRException("Campo atual: ".concat(String.valueOf(String.valueOf(nome_Campo.getName()))), exc);
        }
    }

    private ArrayList arrayED;
    private int index;
}