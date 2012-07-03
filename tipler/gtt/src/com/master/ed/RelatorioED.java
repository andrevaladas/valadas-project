/*
 * Created on 16/11/2004
 */
package com.master.ed;

import javax.servlet.http.*;

/**
 * @author Andre Valadas
 */

public class RelatorioED  extends RelatorioBaseED {
	
  private static final long serialVersionUID = 2914989480483021706L;
	
  public RelatorioED () {
    super ();
  }
  public RelatorioED (HttpServletResponse response , String nomeRelatorio) {
    super (response , nomeRelatorio);
  }
}