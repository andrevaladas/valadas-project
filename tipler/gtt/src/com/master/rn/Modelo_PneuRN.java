/*
 * Created on 12/06/2009
 *
 */
package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.Modelo_PneuBD;
import com.master.ed.Modelo_PneuED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.JavaUtil;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Régis Steigleder
 * @serial Modelos de pneus
 * @serialData 06/2009
 */
public class Modelo_PneuRN extends Transacao {

	public Modelo_PneuRN() { }
	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public Modelo_PneuRN(ExecutaSQL executasql) {
		super(executasql);
	}
  public Modelo_PneuED inclui (Modelo_PneuED ed) throws Excecoes {
    inicioTransacao ();
    try {
      Modelo_PneuED toReturn = new Modelo_PneuBD (this.sql).inclui(ed);
      fimTransacao (true);
      return toReturn;
    }
    catch (Excecoes e) {
      abortaTransacao ();
      throw e;
    }
  }

  public void altera (Modelo_PneuED ed) throws Excecoes {
    inicioTransacao ();
    try {
      new Modelo_PneuBD (this.sql).altera (ed);
      fimTransacao (true);
    }
    catch (Excecoes e) {
      abortaTransacao ();
      throw e;
    }
  }

  public void delete (Modelo_PneuED ed) throws Excecoes {
    inicioTransacao ();
    try {
      new Modelo_PneuBD (this.sql).delete (ed);
      fimTransacao (true);
    }
    catch (Excecoes e) {
      abortaTransacao ();
      throw e;
    }
  }

  public ArrayList lista (Modelo_PneuED ed) throws Excecoes {
    inicioTransacao ();
    try {
      return new Modelo_PneuBD (this.sql).lista (ed);
    }
    finally {
      this.fimTransacao (false);
    }

  }
  public ArrayList listaLookUp (Modelo_PneuED ed) throws Excecoes {
	    inicioTransacao ();
	    try {
	      return new Modelo_PneuBD (this.sql).listaLookUp (ed);
	    }
	    finally {
	      this.fimTransacao (false);
	    }

  }
  
  public Modelo_PneuED getByRecord (Modelo_PneuED ed , int oid) throws Excecoes {
    inicioTransacao ();
    try {
      return new Modelo_PneuBD (this.sql).getByRecord (ed,oid);
    }
    finally {
      fimTransacao (false);
    }
  }

  public Modelo_PneuED getByRecord (int oid) throws Excecoes {
	    inicioTransacao ();
	    try {
	      return new Modelo_PneuBD (this.sql).getByRecord (oid);
	    }
	    finally {
	      fimTransacao (false);
	    }
  }

    public Modelo_PneuED getByRecord (Modelo_PneuED ed ) throws Excecoes {
        inicioTransacao ();
        try {
          return new Modelo_PneuBD (this.sql).getByRecord (ed);
        }
        finally {
          fimTransacao (false);
        }
  }

    public void relatorio(Modelo_PneuED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList lista = new Modelo_PneuBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt005"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			BancoUtil bu = new BancoUtil();
			if (bu.doValida(ed.getNM_Modelo_Pneu()))
				nm_Filtro+=" Descrição=" + ed.getNM_Modelo_Pneu();
			if (ed.getOid_Fabricante_Pneu()>0)
				nm_Filtro+=" Fabricante=" + ed.getNm_Fabricante_Pneu();
			ed.setDescFiltro(nm_Filtro);
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
    	Modelo_PneuED ed = (Modelo_PneuED)Obj;
    	ed.setRequest(request);
		//if ("1".equals(rel)) {
			this.relatorio(ed, request, response);	
		//} 
	
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
  public void processaOL(String acao, Object Obj, HttpServletRequest request, HttpServletResponse response)
  	throws ServletException, IOException, Excecoes {
  	//Extrai o bean com os campos da request colados
  	Modelo_PneuED ed = (Modelo_PneuED)Obj;
  	//Prepara a saída
  	ed.setMasterDetails(request);
  	
  	PrintWriter out = response.getWriter();
  	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
	if ("I".equals(acao) ) {
		if (checkDuplo(ed,acao)) {  
			out.println("<ret><item oknok='Modelo já existente com esta descricao !'/></ret>");
		} else {
    		ed = this.inclui(ed);
    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Modelo_Pneu() + "' /></ret>");
		}    		
	} else 
	if ("A".equals(acao)) {
		if (checkDuplo(ed,acao)) {  
			out.println("<ret><item oknok='Modelo já existente com esta descricao !'/></ret>");
		} else {
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		}
	} else 
	if ("D".equals(acao)) {
		if (checkEmUso(ed)) {
			out.println("<ret><item oknok='Impossível excluir! Modelo em uso!' /></ret>");
		} else {
			this.delete(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		}
	} else	
	if ("lookup".equals(acao)) {
		ArrayList lst = this.listaLookUp(ed);
		if (!lst.isEmpty()) {
			String saida = null;
			out.println("<cad>");
			for (int i=0; i<lst.size(); i++){
				Modelo_PneuED edVolta = (Modelo_PneuED)lst.get(i);
				saida = "<item ";
				saida += "oid_Modelo_Pneu='" + edVolta.getOid_Modelo_Pneu() + "' ";
				saida += "CD_Modelo_Pneu='" + JavaUtil.preperaString(edVolta.getNM_Modelo_Pneu()) + "' ";
				saida += "NM_Modelo_Pneu='" + JavaUtil.preperaString(edVolta.getNM_Modelo_Pneu()) + "' ";
				saida += "dm_Tipo_Pneu='" + JavaUtil.preperaString(edVolta.getDm_Tipo_Pneu()) + "' ";
				saida += "nm_Tipo_Pneu='" + edVolta.getNm_Tipo_Pneu() + "' ";
				saida += "/>";
				out.println(saida);
			}
			out.println("</cad>");
		} else {
			out.println("<ret><item oknok='Modelo de pneu não encontrado !'/></ret>");
		}		
	} else {
	out.println("<cad>");
	String saida = null;
	ArrayList lst = new ArrayList();
	lst = this.lista(ed);
	for (int i=0; i<lst.size(); i++){
		Modelo_PneuED edVolta = new Modelo_PneuED();
		edVolta = (Modelo_PneuED)lst.get(i);
		if ("L".equals(acao)) {
			saida = "<item ";
			saida += "oid_Modelo_Pneu='" + edVolta.getOid_Modelo_Pneu() + "' ";
			saida += "oid_Fabricante_Pneu='" + edVolta.getOid_Fabricante_Pneu() + "' ";
			saida += "NM_Modelo_Pneu='" + edVolta.getNM_Modelo_Pneu() + "' ";
			saida += "nm_Fabricante_Pneu='" + edVolta.getNm_Fabricante_Pneu() + "' ";
			saida += "dm_Tipo_Pneu='" + edVolta.getDm_Tipo_Pneu() + "' ";
			saida += "nm_Tipo_Pneu='" + edVolta.getNm_Tipo_Pneu() + "' ";
			saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
			saida += "/>";
		} else 			
			if ("CB".equals(acao) || "CBC".equals(acao)) {
				if ( i==0 && "CBC".equals(acao) ) {
					saida = "<item ";
					saida += "value='0'>TODOS</item>";
					out.println(saida);
				}
			saida = "<item ";
			saida += "value='" + edVolta.getOid_Modelo_Pneu() + "'>";
			saida +=  edVolta.getNM_Modelo_Pneu();
			saida += "</item>";
		}
		out.println(saida);
	}
	out.println("</cad>");
	}
  	out.flush();
  	out.close();
  }
  
  public boolean checkDuplo ( Modelo_PneuED ed, String acao) throws Excecoes {
  	boolean ret = false;
  	Modelo_PneuED edChk = new Modelo_PneuED();
		edChk.setOid_Empresa(ed.getOid_Empresa());
		edChk.setNM_Modelo_Pneu(ed.getNM_Modelo_Pneu());
		edChk = this.getByRecord(edChk);
  	if ("I".equals(acao) && edChk.getOid_Modelo_Pneu()>0)
  		ret = true;
  	else
      	if ("A".equals(acao) && edChk.getOid_Modelo_Pneu()>0 ) {
  			if (ed.getOid_Modelo_Pneu()!=edChk.getOid_Modelo_Pneu() )
  				ret = true ;
  	}
  	return ret;
  }

  public boolean checkEmUso ( Modelo_PneuED ed ) throws Excecoes {
		try {
			Recapagem_GarantidaED rgED = new Recapagem_GarantidaED();
			rgED.setOid_Modelo_Pneu(ed.getOid_Modelo_Pneu());
			this.inicioTransacao();
			return (new Recapagem_GarantidaRN(this.sql).lista(rgED).size()>0 ? true : false); 
      } finally {
          this.fimTransacao(false);
      }
  }

  public Modelo_PneuED getByCodigo (String codigo) throws Excecoes {
    inicioTransacao ();
    try {
      return new Modelo_PneuBD (this.sql).getByCodigo (codigo);
    }
    finally {
      fimTransacao (false);
    }
  }
}
