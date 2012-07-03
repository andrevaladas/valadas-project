// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov Date: 19/11/2003
// 16:00:37
// Home Page : http://members.fortunecity.com/neshkov/dj.html - Check often for
// new version!
// Decompiler options: packimports(3)
// Source File Name: Relatorio.java

package com.master.relatorio;

import java.io.FileInputStream;
import java.util.Properties;

import com.master.relatorio.relatorioJasper.RelatorioJasper;

// Referenced classes of package com.master.relatorio:
//            InterfaceRelatorio

public class Relatorio {

    private Relatorio() {
    }

    /**
     * @deprecated Method getInstance is deprecated
     */

    public static InterfaceRelatorio getInstance() throws Exception {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(System.getProperty("REPORT")));
            String report = p.getProperty("pr.Report");
            if (report.equalsIgnoreCase("JASPER")) {
                RelatorioJasper relatoriojasper = new RelatorioJasper();
                RelatorioJasper relatoriojasper1 = relatoriojasper;
                return relatoriojasper1;
            }
        } catch (Exception exc) {
            throw exc;
        }
        return null;
    }

    public static InterfaceRelatorio getInstance(int geradorRelatorio) throws Exception {
        try {
            if (geradorRelatorio == 1) {
                RelatorioJasper relatoriojasper = new RelatorioJasper();
                RelatorioJasper relatoriojasper1 = relatoriojasper;
                return relatoriojasper1;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }
        return null;
    }

    public static final int GERADOR_JASPER = 1;

}