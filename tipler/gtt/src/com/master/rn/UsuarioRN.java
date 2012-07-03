package com.master.rn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.bd.UsuarioBD;
import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.bd.Transacao;

public class UsuarioRN extends Transacao {

    public UsuarioRN() {}

    public UsuarioED inclui(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        ed = new UsuarioBD(this.sql).inclui(ed);
        this.fimTransacao(true);
        return ed;
    }

    public void altera(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        new UsuarioBD(this.sql).altera(ed);
        this.fimTransacao(true);
    }

    public void trocaSenha(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        new UsuarioBD(this.sql).trocaSenha(ed);
        this.fimTransacao(true);
    }

    public void deleta(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        new UsuarioBD(this.sql).deleta(ed);
        this.fimTransacao(true);
    }

    public ArrayList lista(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        ArrayList lista = new UsuarioBD(sql).lista(ed);
        this.fimTransacao(false);
        return lista;
    }

    public UsuarioED getByRecord(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        ed = new UsuarioBD(this.sql).getByRecord(ed);
        this.fimTransacao(false);
        return ed;
    }


    /**
     * valida senha, usuario, empresa stgp 
     * @param ed
     * @return
     * @throws Excecoes
     */
    public UsuarioED getByRecord_Encrypt_Stgp(UsuarioED ed) throws Excecoes {
        this.inicioTransacao();
        ed = new UsuarioBD(this.sql).getByRecord_Encrypt_Stgp(ed);
        this.fimTransacao(false);
        return ed;
    }

    
    public UsuarioED getAdminByCNPJ(UsuarioED ed) throws Excecoes {
    	this.inicioTransacao();
    	ed = new UsuarioBD(this.sql).getAdminByCNPJ(ed);
    	fimTransacao(false);
    	return ed;
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
    	UsuarioED ed = (UsuarioED)Obj;
    	//Prepara a saída
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
    	if ("I".equals(acao) ) {
    		ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());  // Usado pela tela manu004C porque o OLUtils altera o oid_empresa colocando o oid da empresa logada
    		ed = this.inclui(ed);
    		out.println("<ret><item oknok='IOK' oid='" + ed.getOid_Usuario() + "'/></ret>");
    	} else 
		if ("C".equals(acao)) {
			//Pega o oid do usuario da session
            UsuarioED usuario = (UsuarioED)request.getSession(true).getAttribute("usuario");
            ed.setOid_Usuario(usuario.getOid_Usuario());
			UsuarioED edVolta = this.getByRecord(ed);
			if (edVolta.getOid_Usuario().intValue() > 0) {
				out.println("<cad>");
				out.println(this.montaRegistro(edVolta));
				out.println("</cad>");
			} else
				out.println("<ret><item oknok='Usuario não encontrado!' /></ret>");
		} else
		if ("A".equals(acao)) {
			ed.setOid_Empresa(ed.getOid_Empresa_Gambiarra());  // Usado pela tela manu004C porque o OLUtils altera o oid_empresa colocando o oid da empresa logada
			this.altera(ed);
			out.println("<ret><item oknok='AOK' /></ret>");
		} else 
		if ("TS".equals(acao)) { // Troca a senha
			this.trocaSenha(ed);
			out.println("<ret><item oknok='TSOK' /></ret>");
		} else 
		if ("D".equals(acao)) {
			this.deleta(ed);
			out.println("<ret><item oknok='DOK' /></ret>");
		} else {
			String saida = null;
			out.println("<cad>");
			if ("L".equals(acao)) {
				ArrayList lst = new ArrayList();
				
				
				lst = this.lista(ed);
				for (int i=0; i<lst.size(); i++){
					UsuarioED edVolta = new UsuarioED();
					edVolta = (UsuarioED)lst.get(i);
					saida=this.montaRegistro(edVolta);
					out.println(saida);
				}
			} else
			if ("CB".equals(acao)) {
				ArrayList lst = new ArrayList();
				lst = this.lista(ed);
				for (int i=0; i<lst.size(); i++){
					UsuarioED edVolta = new UsuarioED();
					edVolta = (UsuarioED)lst.get(i);
					saida = "<item ";
					saida += "value='" + edVolta.getOid_Usuario() + "'>";
					saida +=  edVolta.getNm_Usuario();
					saida += "</item>";
					out.println(saida);
				}
			} else
			if ("CBC".equals(acao)) {
				ed.setOid_Empresa_Gambiarra(ed.getOid_Empresa());
				ArrayList lst = new ArrayList();
				lst = this.lista(ed);
				saida = "<item ";
				saida += "value='0'>";
				saida +=  "TODOS";
				saida += "</item>";
				out.println(saida);

				for (int i=0; i<lst.size(); i++){
					UsuarioED edVolta = new UsuarioED();
					edVolta = (UsuarioED)lst.get(i);
					saida = "<item ";
					saida += "value='" + edVolta.getOid_Usuario() + "'>";
					saida +=  edVolta.getNm_Usuario();
					saida += "</item>";
					out.println(saida);
				}
			}
	
			out.println("</cad>");
		}
    	out.flush();
    	out.close();
    }

    private String montaRegistro( UsuarioED edVolta ){
    	String saida;
		saida = "<item ";
		saida += "oid_Usuario='" + edVolta.getOid_Usuario() + "' ";
		saida += "oid_Sistema='" + edVolta.getOid_Sistema() + "' ";
		saida += "oid_Menu_Perfil='" + edVolta.getOid_Menu_Perfil() + "' ";
		saida += "nm_Usuario='" + edVolta.getNm_Usuario() + "' ";
		saida += "nm_Menu_Perfil='" + edVolta.getNm_Menu_Perfil() + "' ";
		saida += "nm_Senha='" + edVolta.getNm_Senha() + "' ";
		saida += "oid_Empresa_Gambiarra='" + edVolta.getOid_Empresa() + "' ";
		saida += "nm_Razao_Social='" + edVolta.getNm_Razao_Social() + "' ";
		
		saida += ">";
		saida += "</item>";
		return saida;
    }
    
}