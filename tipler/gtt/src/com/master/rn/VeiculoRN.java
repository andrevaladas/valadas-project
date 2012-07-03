package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.VeiculoBD;
import com.master.ed.VeiculoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;
import com.master.util.bd.Transacao;
import com.master.util.rl.JasperRL;

/**
 * @author André Valadas
 * @serial Cadastro de Veículos
 * @since 06/2012
 */
public class VeiculoRN extends Transacao {

	public VeiculoRN() {
		super();
	}

	/**
	 * Recebe Transação e a mantem aberta
	 * @param executasql
	 */
	public VeiculoRN(ExecutaSQL executasql) {
		super(executasql);
	}

    public VeiculoED inclui(VeiculoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            ed = new VeiculoBD(this.sql).inclui(ed);
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

    public void altera(VeiculoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new VeiculoBD(this.sql).altera(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }
    
    public void deleta(VeiculoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            new VeiculoBD(this.sql).deleta(ed);
            this.fimTransacao(true);
        } catch (Excecoes e) {
            this.abortaTransacao();
            throw e;
        } catch (RuntimeException e) {
            this.abortaTransacao();
            throw e;
        }
    }

     public List<VeiculoED> lista(VeiculoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            List<VeiculoED> lista = new VeiculoBD(sql).lista(ed);
            return lista;
        } finally {
            this.fimTransacao(false);
        }
    }
    
    public VeiculoED getByRecord(VeiculoED ed) throws Excecoes {
        try {
            this.inicioTransacao();
            return new VeiculoBD(this.sql).getByRecord(ed);
        } finally {
            this.fimTransacao(false);
        }
    }

    public void relatorio(VeiculoED ed, HttpServletRequest request, HttpServletResponse response) throws Excecoes {
        try {
            this.inicioTransacao();
            String nm_Filtro = "";
            List<VeiculoED> lista = new VeiculoBD(sql).lista(ed);
			ed.setLista(lista); // Joga a lista de veiculos no ed para enviar pro relatório 
			ed.setResponse(response);
			ed.setNomeRelatorio("pns013"); // Seta o nome do relatório
			// Monta a descricao do filtro utilizado
			BancoUtil bu = new BancoUtil();
			if (bu.doValida(ed.getNr_Frota()))
				nm_Filtro+=" Frota=" + ed.getNr_Frota();
			if (bu.doValida(ed.getNr_Placa()))
				nm_Filtro+=" Placa=" + ed.getNr_Placa();
			if (bu.doValida(ed.getNm_Modelo_Veiculo()))
				nm_Filtro+=" Modelo=" + ed.getNm_Modelo_Veiculo();
			if (bu.doValida(ed.getNm_Marca_Veiculo()))
				nm_Filtro+=" Marca=" + ed.getNm_Marca_Veiculo();
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
    	VeiculoED ed = (VeiculoED)Obj;
    	ed.setRequest(request);
		if ("1".equals(rel)) {
			this.relatorio(ed, request, response);	
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
    	VeiculoED ed = (VeiculoED)Obj;
    	
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		if (checkDuploFrota(ed,acao)) {  
    			out.println("<ret><item oknok='Veículo já existente com este número!'/></ret>");
    		} else {
	    		if (checkDuploPlaca(ed,acao) ) {  
	    			out.println("<ret><item oknok='Veículo já existente com esta Placa!'/></ret>");
	    		} else {
		    		ed = this.inclui(ed);
		    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Veiculo() + "' /></ret>");
	    		}
    		}
    	} else 
		if ("A".equals(acao)) {
    		if (checkDuploFrota(ed,acao) ) { 
    			out.println("<ret><item oknok='Veículo já existente com este número!'/></ret>");
    		} else {
	    		if (checkDuploPlaca(ed,acao) ) {
	    			out.println("<ret><item oknok='Veículo já existente com esta Placa!'/></ret>");
	    		} else {
					this.altera(ed);
					out.println("<ret><item oknok='AOK' /></ret>");
	    		}
    		}
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else 
		if ("C".equals(acao)) {
			VeiculoED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Veiculo() > 0) {
				out.println("<cad>");
				out.println(montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Veiculo não encontrado!' /></ret>");
		} else 
		if ("lookup".equals(acao)) {
			List<VeiculoED> lst = this.lista(ed);
			if (!lst.isEmpty()) {
				String saida = null;
				out.println("<cad>");
				for (int i=0; i<lst.size(); i++){
					VeiculoED edVolta = (VeiculoED)lst.get(i);
					saida = "<item ";
					saida += "oid_Veiculo='" + edVolta.getOid_Veiculo() + "' ";
					saida += "nr_Frota='" + edVolta.getNr_Frota() + "' ";
					saida += "nr_Placa='" + edVolta.getNr_Placa() + "' ";
					saida += "/>";
					out.println(saida);
				}
				out.println("</cad>");
			} else {
				out.println("<ret><item oknok='Veículo não encontrado!'/></ret>");
			}
		} else {
			out.println("<cad>");
			String saida=null;
			List<VeiculoED> lst = this.lista(ed);
			for (int i=0; i<lst.size(); i++){
				VeiculoED edVolta = new VeiculoED();
				edVolta = (VeiculoED)lst.get(i);
				if ("L".equals(acao) || "LSF".equals(acao)) {
					saida = montaRegistro(edVolta);
				}else
					if ("CB".equals(acao) || "CBC".equals(acao)) {
						if ( i==0 && "CBC".equals(acao) ) {
							saida = "<item ";
							saida += "value='0'>TODOS</item>";
							out.println(saida);
						}
						saida = "<item ";
						saida += "value='" + edVolta.getOid_Veiculo() + "'>";
						saida +=  edVolta.getNr_Frota();
						saida += "</item>";
					}
				out.println(saida);
			}
			out.println("</cad>");
		}
    	out.flush();
    	out.close();
    }
	
    /**
	 * @param ed
	 * @return
	 */		
    private String montaRegistro( VeiculoED ed ){
    	String saida;
		saida = "<item ";
		saida += "oid_Veiculo='" + ed.getOid_Veiculo() + "' ";
		saida += "nr_Frota='" + ed.getNr_Frota() + "' ";
		saida += "nr_Placa='" + ed.getNr_Placa() + "' ";
		saida += "oid_Cliente='" + ed.getOid_Cliente() + "' ";
		saida += "dm_Tipo_Veiculo='" + ed.getDm_Tipo_Veiculo() + "' ";
		saida += "nm_Marca_Veiculo='" + ed.getNm_Marca_Veiculo() + "' ";
		saida += "nm_Modelo_Veiculo='" + ed.getNm_Modelo_Veiculo() + "' ";
		saida += "nr_Ano_Fabricacao='" + FormataValor.formataValorBT(ed.getNr_Ano_Fabricacao(),0) + "' ";
		saida += "nr_Ano_Modelo='" + FormataValor.formataValorBT(ed.getNr_Ano_Modelo(),0) + "' ";
		saida += "dm_Tipo_Piso='" + ed.getDm_Tipo_Piso() + "' ";
		saida += "dm_Tipo_Severidade='" + ed.getDm_Tipo_Severidade() + "' ";
		saida += "nm_Rota='" + ed.getNm_Rota() + "' ";
		saida += "dm_Tipo_Chassis='" + ed.getDm_Tipo_Chassis() + "' ";
		saida += "msg_Stamp='" + ed.getMsg_Stamp() + "' ";
		saida += "/>";
		return saida;
    }
	
    public boolean checkDuploFrota(VeiculoED ed, String acao) throws Excecoes {
    	final VeiculoED check = new VeiculoED();
		check.setNr_Frota(ed.getNr_Frota());
		return checkDuplo(ed, check, acao);
    }

    public boolean checkDuploPlaca(VeiculoED ed, String acao) throws Excecoes {
    	final VeiculoED check = new VeiculoED();
		check.setNr_Placa(ed.getNr_Placa());
		return checkDuplo(ed, check, acao);
    }

    private boolean checkDuplo(VeiculoED ed, VeiculoED check, String acao) throws Excecoes {
    	check.setOid_Empresa(ed.getOid_Empresa());
    	check = this.getByRecord(check);
    	if ("I".equals(acao) && check.getOid_Veiculo() > 0) {
    		return true;
    	} else if ("A".equals(acao) && check.getOid_Veiculo() > 0) {
			if (ed.getOid_Veiculo() != check.getOid_Veiculo()) {
				return true;
			}
    	}
    	return false;
    }

    protected void finalize() throws Throwable {
        if (this.sql != null)
            this.abortaTransacao();
    }
}