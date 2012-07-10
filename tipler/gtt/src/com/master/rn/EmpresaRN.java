package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.EmpresaBD;
import com.master.ed.EmpresaED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.util.Excecoes;
import com.master.util.JavaUtil;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author Regis
 * @serial Empresas
 * @serialData 06/2009
 */
public class EmpresaRN extends Transacao {

    public EmpresaRN() {
    }

	/**
	 * Recebe Transa��o e a mantem aberta
	 * @param executasql
	 */
	public EmpresaRN(final ExecutaSQL executasql) {
		super(executasql);
	}
	
    public EmpresaED inclui(EmpresaED ed) throws Excecoes {

        try {
        	
            inicioTransacao();
            ed = new EmpresaBD(sql).inclui(ed);
            fimTransacao(true);
            return ed;
        } catch (final Excecoes e) {
            abortaTransacao();
            throw e;
        } catch (final RuntimeException e) {
            abortaTransacao();
            throw e;
        }
    }

    public void altera(final EmpresaED ed) throws Excecoes {

        try {
            inicioTransacao();
            new EmpresaBD(sql).altera(ed);
            fimTransacao(true);
        } catch (final Excecoes e) {
            abortaTransacao();
            throw e;
        } catch (final RuntimeException e) {
            abortaTransacao();
            throw e;
        }
    }
    
    public void delete(final EmpresaED ed) throws Excecoes {

        try {
            inicioTransacao();
            new EmpresaBD(sql).deleta(ed);
            fimTransacao(true);
        } catch (final Excecoes e) {
            abortaTransacao();
            throw e;
        } catch (final RuntimeException e) {
            abortaTransacao();
            throw e;
        }
    }

    public ArrayList<EmpresaED> lista(final EmpresaED ed) throws Excecoes {
    	ArrayList<EmpresaED> lista = new ArrayList<EmpresaED>();
        try {
            inicioTransacao();
            if (Utilitaria.doValida(ed.getDm_Modulo())) {
            	if ("STIB".equals(ed.getDm_Modulo())) {
            		lista = new EmpresaBD(sql).listaClientesModulo(ed);
            	} else  
            	if ("STAS".equals(ed.getDm_Modulo())) {
            		lista = new EmpresaBD(sql).listaClientesModulo(ed);
            	} else  
            	if ("STIF".equals(ed.getDm_Modulo())) {
            		lista = new EmpresaBD(sql).listaClientesModulo(ed);
            	}
            } else {
            	lista = new EmpresaBD(sql).lista(ed);
            }
            return lista;
        } finally {
            fimTransacao(false);
        }
    }

    public void lista(final EmpresaED ed, final HttpServletRequest request, final String nmObj) throws Excecoes {

        try {
            inicioTransacao();
            final ArrayList lista = new EmpresaBD(sql).lista(ed);
            request.setAttribute(nmObj, lista);
        } finally {
            fimTransacao(false);
        }
    }

    public EmpresaED getByRecord(final EmpresaED ed) throws Excecoes {

        try {
            inicioTransacao();
            return new EmpresaBD(sql).getByRecord(ed);
        } finally {
            fimTransacao(false);
        }
    }

    public void relatorio(final EmpresaED ed, final HttpServletRequest request, final HttpServletResponse response) throws Excecoes {
        try {
            inicioTransacao();
            String nm_Filtro = "";
            final ArrayList lista = new EmpresaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de empresas no ed para enviar pro relat�rio 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt001"); // Seta o nome do relat�rio
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNr_Cnpj())) {
				nm_Filtro+="Cnpj=" + ed.getNr_Cnpj();
			}
			if (Utilitaria.doValida(ed.getNm_Razao_Social())) {
				nm_Filtro+=" Raz�o Social=" + ed.getNm_Razao_Social();
			}
			if (ed.getOid_Empresa_Gambiarra()>0) {
				final EmpresaED coED = new EmpresaED();
				coED.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
				nm_Filtro+=" Concession�ria=" + getByRecord(coED).getNm_Razao_Social();
			}
			ed.setDescFiltro(nm_Filtro);
			
    		final HashMap map = new HashMap();
    		// Este parametro esconde os campos telefone e nome do contato e mostra nome da concession�ria quando for T inverte quanfor dor C
    		//map.put("dm_Tipo_Consulta", ed.getDm_Tipo_Consulta());
    		map.put("dm_Tipo_Consulta", ed.getDm_Tipo_Consulta());
    		ed.setHashMap(map);
    		
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
        } finally {
            fimTransacao(false);
        }
    }

    private void relatorioConcessionarias(final EmpresaED ed, final HttpServletRequest request, final HttpServletResponse response) throws Excecoes {
        try {
            inicioTransacao();
            String nm_Filtro = "";
            final ArrayList lista = new EmpresaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de empresas no ed para enviar pro relat�rio 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt002"); // Seta o nome do relat�rio
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNr_Cnpj())) {
				nm_Filtro+="Cnpj=" + ed.getNr_Cnpj();
			}
			if (Utilitaria.doValida(ed.getNm_Razao_Social())) {
				nm_Filtro+=" Raz�o Social=" + ed.getNm_Razao_Social();
			}
			ed.setDescFiltro(nm_Filtro);
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
        } finally {
            fimTransacao(false);
        }
    }

    /**
     * processaRL
     * Processa solicita��o de relatorio OL retornando sempre um PDF.
     * @param rel = Qual o relatorio a ser chamado
     * @param Obj = Um bean populado com parametros para a geracao do relatorio
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws Excecoes
     */
    
    public void processaRL(final String rel, final Object Obj, final HttpServletRequest request, final HttpServletResponse response)
	throws ServletException, IOException, Excecoes {
    	//Extrai o bean com os campos da request colados
    	final EmpresaED ed = (EmpresaED)Obj;
    	ed.setDm_Tipo_Consulta(Utilitaria.getTipoEmpresa(request));
    	ed.setRequest(request);
    	if ("1".equals(rel)) {
    		relatorio(ed, request, response);	
    	} else
    	if ("2".equals(rel)) {
    		relatorioConcessionarias(ed, request, response);	
    	}    		

}
    
    /**
     * processaOL
     * Processa solicita��o de tela OL retornando sempre arquivo XML com a seguinte estrutura.
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
    public void processaOL(final String acao, final Object Obj, final HttpServletRequest request, final HttpServletResponse response)
    	throws ServletException, IOException, Excecoes {
    	//Extrai o bean com os campos da request colados
    	EmpresaED ed = (EmpresaED)Obj;
    	
    	// Busca o tipo de empresa do usuario logado
    	ed.setDm_Tipo_Consulta(Utilitaria.getTipoEmpresa(request));
    	//Prepara a sa�da
    	final PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao)) {
			ed.setOid_Concessionaria(ed.getOid_Empresa());
    		if (checkDuplo(ed,acao)) {
    			if ("C".equals(ed.getDm_Tipo_Empresa()) ) {
					out.println("<ret><item oknok='Concession�ria j� existe com este CNPJ!'/></ret>");
				} else {
					out.println("<ret><item oknok='Cliente j� existe com este CNPJ/CPF!'/></ret>");
				}
    		} else {
    			ed = inclui(ed);
	    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Empresa() + "' /></ret>");
    		}
    	} else 
		if ("A".equals(acao)) {
			if ("T".equals(ed.getDm_Tipo_Consulta()) ) {
				ed.setOid_Concessionaria(ed.getOid_Concessionaria());
			} else {
				ed.setOid_Concessionaria(ed.getOid_Empresa());
			}	
    		if (checkDuplo(ed,acao)) {  
    			out.println("<ret><item oknok='N�o pode alterar o cnpj!'/></ret>");
    		} else {
    			ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
    			altera(ed);
    			out.println("<ret><item oknok='AOK' /></ret>");
    		}
		}else
		if ("C".equals(acao)) {
			if (ed.getOid_Empresa_Gambiarra()>0) {
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			}
			final EmpresaED edVolta = getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Empresa n�o encontrada!' /></ret>");
			}
		} else
		if ("CC".equals(acao)) { // Busca concessionaria no lookup exato - tela fat001C
			if (ed.getOid_Empresa_Gambiarra()>0) {
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			}
			final EmpresaED edVolta = getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Empresa n�o encontrada!' /></ret>");
			}
		} else
		// Lookup de cliente quando concessionaria logada - Sempre tem que ter uma concessionaria na variavel cliente dm_tipo_empresa = 	
		if ("CU".equals(acao)) { // Busca cliente no lookup exato - tela gtt101C
			if ("C".equals(ed.getDm_Tipo_Consulta())) {
				ed.setOid_Concessionaria(ed.getOid_Empresa());
			}
			if (ed.getOid_Cliente()>0) {
				ed.setOid_Empresa(ed.getOid_Cliente());
			} 
			final EmpresaED edVolta = getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Cliente n�o encontrado!' /></ret>");
			}
		} else
			
		if ("CG".equals(acao)) {
			ed.setOid_Empresa_Gambiarra(0);
			ed.setOid_Empresa(0);
			final EmpresaED edVolta = getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Empresa n�o encontrada!' /></ret>");
			}
		} else			
		if ("D".equals(acao)) {
			if (ed.getOid_Empresa_Gambiarra()>0) {
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			}
			if (checkEmUso(ed)) {
				out.println("<ret><item oknok='Imposs�vel excluir! Empresa em uso!' /></ret>");
			} else {
				delete(ed);
				out.println("<ret><item oknok='DOK' /></ret>");
			}
		} else 
		if ("lookup".equals(acao)) {
			// *****************************************************************************************
			// Se esta requisicao veio da tela gtt311 - consulta garantia de outras concessionarias
			// altera a variavel dm_Tipo_Consulta para perfil Tipler para poder pegar todos os clientes
			if (ed.getOid_Concessionaria()==999) { ed.setDm_Tipo_Consulta("T"); }
			// *****************************************************************************************
			final ArrayList<EmpresaED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					final EmpresaED edVolta = lst.get(i);
					saida = "<item ";
					saida += "oid_Empresa='" + edVolta.getOid_Empresa() + "' ";
					saida += "oid_Empresa_Gambiarra='" + edVolta.getOid_Empresa() + "' ";
					saida += "oid_Cliente='" + edVolta.getOid_Empresa() + "' ";
					saida += "nr_Cnpj='" + edVolta.getNr_Cnpj() + "' ";
					saida += "nm_Razao_Social='" + JavaUtil.preperaString(edVolta.getNm_Razao_Social()) + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Empresa n�o encontrada !'/></ret>");
			}
		} else {
		out.println("<cad>");
		String saida=null;
		ArrayList lst = new ArrayList();
		lst = this.lista(ed);
		for (int i=0; i<lst.size(); i++){
			EmpresaED edVolta = new EmpresaED();
			edVolta = (EmpresaED)lst.get(i);
			if ("L".equals(acao)) {
				saida=montaRegistro(edVolta);
			}else
			if ("CB".equals(acao) || "CBC".equals(acao)) {
				if ( i==0 && "CBC".equals(acao) ) {
					saida = "<item ";
					saida += "value='0'>TODOS</item>";
					out.println(saida);
				}
				saida = "<item ";
				saida += "value='" + edVolta.getOid_Empresa() + "'>";
				if (edVolta.getNm_Razao_Social().length() > 30) {
					saida +=  edVolta.getNm_Razao_Social().substring(0, 30);
				} else {
					saida +=  edVolta.getNm_Razao_Social();
				}
				saida += "</item>";
			}
			out.println(saida);
		}
		out.println("</cad>");
		}
    	out.flush();
    	out.close();
    }
    
    private String montaRegistro( final EmpresaED edVolta ){
    	String saida;
    	saida = "<item ";
		saida += "oid_Empresa='" + edVolta.getOid_Empresa() + "' ";
		saida += "oid_Empresa_Gambiarra='" + edVolta.getOid_Empresa() + "' ";
		saida += "nr_Cnpj='" + edVolta.getNr_Cnpj() + "' ";
		saida += "nm_Razao_Social='" + edVolta.getNm_Razao_Social() + "' ";
		saida += "nm_Endereco='" + edVolta.getNm_Endereco() + "' ";
		saida += "nm_Bairro='" + edVolta.getNm_Bairro() + "' ";
		saida += "nr_Cep='" + edVolta.getNr_Cep() + "' ";
		saida += "nm_Inscricao_Estadual='" + edVolta.getNm_Inscricao_Estadual() + "' ";
		saida += "nm_Cidade='" + edVolta.getNm_Cidade() + "' ";
		saida += "cd_Estado='" + edVolta.getCd_Estado() + "' ";
		saida += "cd_Pais='" + edVolta.getCd_Pais() + "' ";
		saida += "nr_Telefone='" + edVolta.getNr_Telefone() + "' ";
		saida += "nr_Fax='" + edVolta.getNr_Fax() + "' ";
		saida += "nm_Email='" + edVolta.getNm_Email() + "' ";
		saida += "dm_Tipo_Frota='" + edVolta.getDm_Tipo_Frota() + "' ";
		saida += "nm_Contato='" + edVolta.getNm_Contato() + "' ";
		saida += "dm_Tipo_Empresa='" + edVolta.getDm_Tipo_Empresa() + "' ";
		saida += "oid_Concessionaria='" + edVolta.getOid_Concessionaria() + "' ";
		saida += "oid_Regional='" + edVolta.getOid_Regional() + "' ";
		saida += "nm_Regional='" + edVolta.getNm_Regional() + "' ";
		
		saida += "oid_Cliente='" + edVolta.getOid_Empresa() + "' ";
		
		saida += "nm_Concessionaria='" + edVolta.getNm_Concessionaria() + "' ";
		saida += "msg_Stamp='" + edVolta.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
    }

    public boolean checkDuplo ( final EmpresaED ed, final String acao) throws Excecoes {
    	EmpresaED edChk = new EmpresaED();
    	edChk.setNr_Cnpj(ed.getNr_Cnpj());
    	edChk.setOid_Concessionaria(ed.getOid_Concessionaria());
		edChk = getByRecord(edChk);
    	if ("I".equals(acao) && Utilitaria.doValida(edChk.getNr_Cnpj())) {
			return true;
		} else
    	if ("A".equals(acao) && !(ed.getNr_Cnpj().equals(edChk.getNr_Cnpj()) )) {
			return true;
		} else {
			return false;
		}
    }
    
    public boolean checkEmUso ( final EmpresaED ed ) throws Excecoes {
		try {
			inicioTransacao();
			if ("C".equals(ed.getDm_Tipo_Empresa())) {
				final EmpresaED empED = new EmpresaED();
				empED.setOid_Empresa(ed.getOid_Empresa());
				empED.setDm_Tipo_Consulta("C"); 
			empED.setDm_Tipo_Empresa("A");
				return this.lista(empED).size()>0?true:false;
			} else
			if ("U".equals(ed.getDm_Tipo_Empresa())) {
				final Recapagem_GarantidaED rgED = new Recapagem_GarantidaED();
				rgED.setOid_Cliente(ed.getOid_Empresa());
				return (new Recapagem_GarantidaRN(sql).lista(rgED).size()>0 ? true : false);	
			}
			return false;
        } finally {
            fimTransacao(false);
        }
    }
    
    protected void finalize() throws Throwable {
        if (sql != null) {
			abortaTransacao();
		}
    }

}