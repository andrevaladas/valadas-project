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
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public EmpresaRN(ExecutaSQL executasql) {
		super(executasql);
	}
	
    public EmpresaED inclui(EmpresaED ed) throws Excecoes {

        try {
        	
            this.inicioTransacao();
            ed = new EmpresaBD(this.sql).inclui(ed);
            this.fimTransacao(true);
            return ed;
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

    public void altera(EmpresaED ed) throws Excecoes {

        try {
            this.inicioTransacao();
            new EmpresaBD(this.sql).altera(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void delete(EmpresaED ed) throws Excecoes {

        try {
            this.inicioTransacao();
            new EmpresaBD(this.sql).deleta(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

    public ArrayList<EmpresaED> lista(EmpresaED ed) throws Excecoes {
    	ArrayList<EmpresaED> lista = new ArrayList<EmpresaED>();
        try {
            this.inicioTransacao();
            if (Utilitaria.doValida(ed.getDm_Modulo())) {
            	if ("STIB".equals(ed.getDm_Modulo())) {
            		lista = new EmpresaBD(sql).listaClientesModulo(ed);
            	} else  
            	if ("STAS".equals(ed.getDm_Modulo())) {
            		lista = new EmpresaBD(sql).listaClientesModulo(ed);
            	}
            } else {
            	lista = new EmpresaBD(sql).lista(ed);
            }
            return lista;
        } finally {
            this.fimTransacao(false);
        }
    }

    public void lista(EmpresaED ed, HttpServletRequest request, String nmObj) throws Excecoes {

        try {
            this.inicioTransacao();
            ArrayList lista = new EmpresaBD(sql).lista(ed);
            request.setAttribute(nmObj, lista);
        } finally {
            this.fimTransacao(false);
        }
    }

    public EmpresaED getByRecord(EmpresaED ed) throws Excecoes {

        try {
            this.inicioTransacao();
            return new EmpresaBD(this.sql).getByRecord(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

    public void relatorio(EmpresaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList lista = new EmpresaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de empresas no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt001"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNr_Cnpj()))
				nm_Filtro+="Cnpj=" + ed.getNr_Cnpj();
			if (Utilitaria.doValida(ed.getNm_Razao_Social()))
				nm_Filtro+=" Razão Social=" + ed.getNm_Razao_Social();
			if (ed.getOid_Empresa_Gambiarra()>0) {
				EmpresaED coED = new EmpresaED();
				coED.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
				nm_Filtro+=" Concessionária=" + this.getByRecord(coED).getNm_Razao_Social();
			}
			ed.setDescFiltro(nm_Filtro);
			
    		HashMap map = new HashMap();
    		// Este parametro esconde os campos telefone e nome do contato e mostra nome da concessionária quando for T inverte quanfor dor C
    		//map.put("dm_Tipo_Consulta", ed.getDm_Tipo_Consulta());
    		map.put("dm_Tipo_Consulta", ed.getDm_Tipo_Consulta());
    		ed.setHashMap(map);
    		
			new JasperRL(ed).geraRelatorio(); // Chama o relatorio passando o ed
        } finally {
            this.fimTransacao(false);
        }
    }

    private void relatorioConcessionarias(EmpresaED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            ArrayList lista = new EmpresaBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de empresas no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("gtt002"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			if (Utilitaria.doValida(ed.getNr_Cnpj()))
				nm_Filtro+="Cnpj=" + ed.getNr_Cnpj();
			if (Utilitaria.doValida(ed.getNm_Razao_Social()))
				nm_Filtro+=" Razão Social=" + ed.getNm_Razao_Social();
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
    	EmpresaED ed = (EmpresaED)Obj;
    	ed.setDm_Tipo_Consulta(Utilitaria.getTipoEmpresa(request));
    	ed.setRequest(request);
    	if ("1".equals(rel)) {
    		this.relatorio(ed, request, response);	
    	} else
    	if ("2".equals(rel)) {
    		this.relatorioConcessionarias(ed, request, response);	
    	}    		

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
    	EmpresaED ed = (EmpresaED)Obj;
    	
    	// Busca o tipo de empresa do usuario logado
    	ed.setDm_Tipo_Consulta(Utilitaria.getTipoEmpresa(request));
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao)) {
			ed.setOid_Concessionaria(ed.getOid_Empresa());
    		if (checkDuplo(ed,acao)) {
    			if ("C".equals(ed.getDm_Tipo_Empresa()) ) 
    				out.println("<ret><item oknok='Concessionária já existe com este CNPJ!'/></ret>");
    			else
    				out.println("<ret><item oknok='Cliente já existe com este CNPJ/CPF!'/></ret>");
    		} else {
    			ed = this.inclui(ed);
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
    			out.println("<ret><item oknok='Não pode alterar o cnpj!'/></ret>");
    		} else {
    			ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
    			this.altera(ed);
    			out.println("<ret><item oknok='AOK' /></ret>");
    		}
		}else
		if ("C".equals(acao)) {
			if (ed.getOid_Empresa_Gambiarra()>0) // Usado pelo lookup da tela de cadastro de usuarios mnu004
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			EmpresaED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Empresa não encontrada!' /></ret>");
		} else
		if ("CC".equals(acao)) { // Busca concessionaria no lookup exato - tela fat001C
			if (ed.getOid_Empresa_Gambiarra()>0) // Usado pelo lookup da tela de cadastro de usuarios mnu004
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			EmpresaED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Empresa não encontrada!' /></ret>");
		} else
		// Lookup de cliente quando concessionaria logada - Sempre tem que ter uma concessionaria na variavel cliente dm_tipo_empresa = 	
		if ("CU".equals(acao)) { // Busca cliente no lookup exato - tela gtt101C
			if ("C".equals(ed.getDm_Tipo_Consulta()))
				ed.setOid_Concessionaria(ed.getOid_Empresa());
			if (ed.getOid_Cliente()>0)
				ed.setOid_Empresa(ed.getOid_Cliente()); 
			EmpresaED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Cliente não encontrado!' /></ret>");
		} else
			
		if ("CG".equals(acao)) {
			ed.setOid_Empresa_Gambiarra(0);
			ed.setOid_Empresa(0);
			EmpresaED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Empresa() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Empresa não encontrada!' /></ret>");
		} else			
		if ("D".equals(acao)) {
			if (ed.getOid_Empresa_Gambiarra()>0) 
				ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());
			if (checkEmUso(ed)) {
				out.println("<ret><item oknok='Impossível excluir! Empresa em uso!' /></ret>");
			} else {
				this.delete(ed);
				out.println("<ret><item oknok='DOK' /></ret>");
			}
		} else 
		if ("lookup".equals(acao)) {
			// *****************************************************************************************
			// Se esta requisicao veio da tela gtt311 - consulta garantia de outras concessionarias
			// altera a variavel dm_Tipo_Consulta para perfil Tipler para poder pegar todos os clientes
			if (ed.getOid_Concessionaria()==999) { ed.setDm_Tipo_Consulta("T"); }
			// *****************************************************************************************
			ArrayList<EmpresaED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					EmpresaED edVolta = (EmpresaED)lst.get(i);
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
				out.println("<ret><item oknok='Empresa não encontrada !'/></ret>");
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
				saida=this.montaRegistro(edVolta);
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
    
    private String montaRegistro( EmpresaED edVolta ){
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

    public boolean checkDuplo ( EmpresaED ed, String acao) throws Excecoes {
    	EmpresaED edChk = new EmpresaED();
    	edChk.setNr_Cnpj(ed.getNr_Cnpj());
    	edChk.setOid_Concessionaria(ed.getOid_Concessionaria());
		edChk = this.getByRecord(edChk);
    	if ("I".equals(acao) && Utilitaria.doValida(edChk.getNr_Cnpj()))
    		return true;
    	else
    	if ("A".equals(acao) && !(ed.getNr_Cnpj().equals(edChk.getNr_Cnpj()) ))
    		return true;
    	else
    		return false;
    }
    
    public boolean checkEmUso ( EmpresaED ed ) throws Excecoes {
		try {
			this.inicioTransacao();
			if ("C".equals(ed.getDm_Tipo_Empresa())) {
				EmpresaED empED = new EmpresaED();
				empED.setOid_Empresa(ed.getOid_Empresa());
				empED.setDm_Tipo_Consulta("C"); 
			empED.setDm_Tipo_Empresa("A");
				return this.lista(empED).size()>0?true:false;
			} else
			if ("U".equals(ed.getDm_Tipo_Empresa())) {
				Recapagem_GarantidaED rgED = new Recapagem_GarantidaED();
				rgED.setOid_Cliente(ed.getOid_Empresa());
				return (new Recapagem_GarantidaRN(this.sql).lista(rgED).size()>0 ? true : false);	
			}
			return false;
        } finally {
            this.fimTransacao(false);
        }
    }
    
    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }

}