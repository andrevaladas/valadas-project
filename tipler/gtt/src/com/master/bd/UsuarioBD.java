package com.master.bd;

//import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.master.ed.UsuarioED;
import com.master.util.Excecoes;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;

public class UsuarioBD {

    private ExecutaSQL executasql;
    Utilitaria util = new Utilitaria(executasql);

    public UsuarioBD(ExecutaSQL sql) {
        this.executasql = sql;
        util = new Utilitaria(this.executasql);
    }

    public UsuarioED inclui(UsuarioED ed) throws Excecoes {
        String sql = null;
        try {
            ResultSet cursor = executasql.executarConsulta("SELECT MAX(OID_Usuario) FROM Usuarios");
			while (cursor.next()) {
				int oid = cursor.getInt(1);
				ed.setOid_Usuario(new Integer(oid + 1));
			}
			// Busca o tipo de empresa do usuario para determinar o perfil
			//sql = "select dm_Tipo_Empresa from Empresas where oid_Empresa = " + ed.getOid_Empresa();
            //ResultSet res = this.executasql.executarConsulta(sql);
            //String dmTipoEmpresa= null;
            //ed.setOid_Menu_Perfil(3);
            //if (res.first()) {
	        //    dmTipoEmpresa = res.getString("dm_Tipo_Empresa");
			//	if ("T".equals(dmTipoEmpresa)) 
			//		ed.setOid_Menu_Perfil(1);
			//	else if ("C".equals(dmTipoEmpresa)) 
			//		ed.setOid_Menu_Perfil(2);
            //}		
            sql = " INSERT INTO " +
            	  " Usuarios ( " +
            	  " OID_Usuario " +
            	  ", NM_Usuario " +
            	  ", DT_STAMP " +
            	  ", USUARIO_STAMP " +
            	  ", DM_STAMP " +
            	  ", nm_Senha " +
            	  ", oid_Empresa " +
            	  ",oid_Menu_Perfil " ;
        	sql+= " ) VALUES " +
             	  "(" +
            	  ed.getOid_Usuario() +
            	  ",'" + ed.getNm_Usuario() +
            	  "','" + ed.getDt_stamp() +
            	  "','" + ed.getUsuario_Stamp() +
            	  "','" + ed.getDm_Stamp() +
            	  "','" + ed.getNm_Senha() + "' " +
            	  ", " + ed.getOid_Empresa() +
            	  ", " + ed.getOid_Menu_Perfil() ; // Pegar o perfil conforme o tipo de empresa 1=T Tipler, 2=C Concessionaria 3=U Cliente
            sql+= ")";
           	executasql.executarUpdate(sql);
        }
        catch (Exception exc) {
            Excecoes excecoes = new Excecoes();
            excecoes.setClasse(this.getClass().getName());
            excecoes.setMensagem("Erro ao incluir Usuario");
            excecoes.setMetodo("inclui(Usuario_ED)");
            excecoes.setExc(exc);
            throw excecoes;
        }
        return ed;
    }

    public void altera(UsuarioED ed) throws Excecoes {
        String sql = null;
        try {
            sql = " update Usuarios set ";
            sql += " NM_Usuario = '" + ed.getNm_Usuario() + "', ";
            sql += " DT_STAMP = '" + ed.getDt_stamp() + "', ";
            sql += " USUARIO_STAMP = '" + ed.getUsuario_Stamp() + "', ";
            sql += " NM_Email = '" + ed.getNm_Email() + "', ";
            sql += " DM_Situacao = '" + ed.getDM_Situacao() + "', ";

            sql += " DM_STAMP = '" + ed.getDm_Stamp() + "', ";
            sql += " nm_Senha = '" + ed.getNm_Senha().toUpperCase() + "', "; 
            if (Utilitaria.doValida(ed.getDM_Perfil() ) ) {
            	sql += " DM_Perfil = '" + ed.getDM_Perfil() + "' ,";
            }
            sql += " oid_Menu_Perfil = " + ed.getOid_Menu_Perfil() + ", ";
            
            if (ed.getOid_Empresa()==0)
            	sql += " oid_Empresa = 1 ";
            else
            	sql += " oid_Empresa = " + ed.getOid_Empresa() + " ";
            sql += " where oid_Usuario = " + ed.getOid_Usuario();
            executasql.executarUpdate(sql);
        } catch (Exception exc) {
            Excecoes excecoes = new Excecoes();
            excecoes.setClasse(this.getClass().getName());
            excecoes.setMensagem("Erro ao alterar Usuario");
            excecoes.setMetodo("altera(UsuarioED)");
            excecoes.setExc(exc);
            throw excecoes;
        }
    }
    
    public void trocaSenha(UsuarioED ed) throws Excecoes {
    	String sql = null;
    	try {
    		sql = "Update " +
    		"Usuarios " +
    		"set " +
    		"nm_Senha = '" + ed.getNm_Senha().toUpperCase() + "' " +
    		"where " +
    		"oid_Usuario = " + ed.getOid_Usuario();
    		executasql.executarUpdate(sql);
    	} catch (Exception exc) {
    		Excecoes excecoes = new Excecoes();
    		excecoes.setClasse(this.getClass().getName());
    		excecoes.setMensagem("Erro ao trocar senha do Usuario");
    		excecoes.setMetodo("trocaSenha(UsuarioED)");
    		excecoes.setExc(exc);
    		throw excecoes;
    	}
    }

    public void deleta(UsuarioED ed) throws Excecoes {
        String sql = null;
        try {
            sql = "delete from Usuarios WHERE oid_Usuario = ";
            sql += ed.getOid_Usuario();
            executasql.executarUpdate(sql);
        }
        catch (Exception exc) {
            Excecoes excecoes = new Excecoes();
            excecoes.setClasse(this.getClass().getName());
            excecoes.setMensagem("Erro ao excluir Usuario");
            excecoes.setMetodo("deleta(UsuarioED");
            excecoes.setExc(exc);
            throw excecoes;
        }
    }

    public ArrayList lista(UsuarioED ed) throws Excecoes {
        String sql = null;
        ArrayList list = new ArrayList();
        try {
            sql = " SELECT " +
            	  " *, " +
            	  " u.oid_Empresa as oid_emp " +
                  " FROM  " +
                  " Usuarios as u " +
                  " left join Empresas as e on u.oid_empresa = e.oid_empresa " +
                  " left join Menus_Perfis as m on u.oid_Menu_Perfil = m.oid_Menu_Perfil  " +
                  " WHERE  " +
                  " 1=1 " ;
            sql+= " AND u.oid_Usuario > 0 ";
            //if (ed.getOid_Empresa() > 0) {
            //    sql += " and u.oid_Empresa = " + ed.getOid_Empresa() + " ";
            //}
            if (ed.getOid_Empresa_Gambiarra() > 0) {
                sql += " and u.oid_Empresa = " + ed.getOid_Empresa_Gambiarra() + " ";
            }
            if (ed.getOid_Menu_Perfil() > 0) {
                sql += " and u.Oid_Menu_Perfil = " + ed.getOid_Menu_Perfil() + " ";
            }
            if (ed.getOid_Usuario() != null && ed.getOid_Usuario().intValue() > 0) {
                sql += " and u.oid_Usuario = '" + ed.getOid_Usuario() + "'";
            }
            if (Utilitaria.doValida(ed.getNm_Usuario())) {
                sql += " and u.nm_Usuario LIKE '" + ed.getNm_Usuario() + "%'";
            }
            sql += " order by nm_usuario";
            ResultSet res = this.executasql.executarConsulta(sql);
            while (res.next())
            {
                UsuarioED edVolta = new UsuarioED();
                edVolta.setOid_Usuario(new Integer(res.getString("oid_Usuario")));
                edVolta.setNm_Usuario(res.getString("nm_Usuario"));
                edVolta.setDM_Administrador(" ");
                if ("S".equals(res.getString("dm_administrador"))){
                  edVolta.setDM_Administrador("*");
                }
                edVolta.setNm_Razao_Social(res.getString("NM_Razao_Social"));
                edVolta.setDM_Perfil(res.getString("DM_Perfil"));
                edVolta.setOid_Empresa(res.getLong("oid_emp"));
            	edVolta.setNm_Menu_Perfil(res.getString("nm_Menu_Perfil"));
            	edVolta.setOid_Menu_Perfil(res.getLong("oid_Menu_Perfil"));
            	edVolta.setNm_Senha(res.getString("nm_Senha"));
                list.add(edVolta);
            }
        } catch (Exception exc) {
            Excecoes excecoes = new Excecoes();
            excecoes.setClasse(this.getClass().getName());
            excecoes.setMensagem("Erro no método listar");
            excecoes.setMetodo("lista(Usuario_ED");
            excecoes.setExc(exc);
            throw excecoes;
        }
        return list;
    }

    public UsuarioED getByRecord(UsuarioED ed) throws Excecoes {
    	String sql = null;
    	UsuarioED edVolta = new UsuarioED();
    	try {
           	sql = "Select " +
       			  "* " +
       			  "from " +
       			  "usuarios " +
       			  "left join menus_perfis on usuarios.oid_menu_perfil = menus_perfis.oid_menu_perfil " +
       			  "Where 1=1 ";
            if(ed.getOid_Usuario().intValue() > 0)
                sql = sql + " and OID_Usuario = " + ed.getOid_Usuario();
            else
                sql = sql + " and NM_Usuario = '" + ed.getNm_Usuario() + "' and " + "NM_Senha = '" + ed.getNm_Senha() + "'";
            ResultSet res = this.executasql.executarConsulta(sql);
            while (res.next()) {
                edVolta = new UsuarioED();
                edVolta.setOid_Usuario(new Integer(res.getInt("oid_Usuario")));
                edVolta.setNm_Usuario(res.getString("nm_Usuario"));
                edVolta.setNm_Email(res.getString("NM_Email"));
                edVolta.setNm_Menu_Perfil(res.getString("nm_Menu_Perfil"));
                edVolta.setOid_Menu_Perfil(res.getLong("oid_Menu_Perfil"));
                edVolta.setNm_Senha(res.getString("nm_Senha"));
                edVolta.setOid_Empresa(res.getInt("oid_Empresa"));
            }
        }
        catch(Exception exc)
        {
            Excecoes excecoes = new Excecoes();
            excecoes.setClasse(getClass().getName());
            excecoes.setMensagem("Erro ao recuperar registro! [" + exc.getMessage() + "]");
            excecoes.setMetodo("getByRecord");
            excecoes.setExc(exc);
            throw excecoes;
        }
        return edVolta;
    }

     /**
     * Busca o usuario e a empresa validando se o usuario existe para ea empresa e sua senha.
     * @param ed
     * @return
     * @throws Excecoes
     */
    public UsuarioED getByRecord_Encrypt_Stgp(UsuarioED ed) throws Excecoes {
        String sql = null;
        UsuarioED edVolta = new UsuarioED();
        try {
            sql = "select " +
            	  "* " +
            	  "from " +
            	  "Usuarios as u, " +
            	  "Empresas as e " +
            	  "where " +
            	  "u.oid_Empresa = e.oid_Empresa and " +
            	  "u.nm_Usuario = '" + ed.getNm_Usuario() + "' and " +
            	  "u.nm_Senha = '" + ed.getNm_Senha() + "' and " +
            	  "e.nr_Cnpj = '" + ed.getNr_Cnpj() +"'" ;
            ResultSet res = this.executasql.executarConsulta(sql);
            while (res.next()) {
                edVolta = new UsuarioED();
                edVolta.setOid_Usuario(new Integer(res.getInt("oid_Usuario")));
                edVolta.setNm_Usuario(res.getString("nm_Usuario"));
                edVolta.setNr_Cnpj(res.getString("nr_Cnpj"));
                edVolta.setDM_Perfil(res.getString("DM_Perfil"));
                edVolta.setDM_Administrador(res.getString("DM_Administrador"));
                edVolta.setNm_Senha(res.getString("NM_Senha"));
                edVolta.setOid_Empresa(res.getLong("oid_Empresa"));
                edVolta.setNm_Razao_Social(res.getString("nm_Razao_Social"));
                edVolta.setOid_Menu_Perfil(res.getLong("oid_Menu_Perfil"));
                edVolta.setDm_Tipo_Empresa(res.getString("dm_Tipo_Empresa"));
                edVolta.setDm_Lingua(Utilitaria.doValida(res.getString("cd_Pais"))?("BR".equals(res.getString("cd_Pais").trim())?"P":"E"):"P");
                // Grava login do usuáriono sistema...
            }
        } catch (Exception exc) {
            Excecoes excecoes = new Excecoes();
            excecoes.setClasse(this.getClass().getName());
            excecoes.setMensagem("Erro ao recuperar registro! [" + exc.getMessage() + "]");
            excecoes.setMetodo("getByRecord_Encrypt_Stgp()");
            excecoes.setExc(exc);
            throw excecoes;
        }
        return edVolta;
    }

    /**
     * Rotina usada pelos sistemas Laszlo, para validar usuario ADMIN pelo cnpj.
     * @param ed
     * @return
     * @throws Excecoes
     */
    public UsuarioED getAdminByCNPJ(UsuarioED ed) throws Excecoes {
    	String sql = null;
    	UsuarioED edVolta = new UsuarioED();
    	try {
    		sql = "select " +
    		"* " +
    		"from " +
    		"Usuarios as u, " +
    		"Empresas as e " +
    		"where " +
    		"u.oid_Empresa = e.oid_Empresa and " +
    		"u.nm_Usuario = 'ADMIN' and " +
    		"e.nr_Cnpj = '" + ed.getNr_Cnpj() + "'";
            ResultSet res = this.executasql.executarConsulta(sql);
            while (res.next()) {
		    	edVolta = new UsuarioED();
		        edVolta.setNr_Cnpj(res.getString("nr_Cnpj"));
		        edVolta.setOid_Usuario(new Integer(res.getInt("oid_Usuario")));
		        edVolta.setNm_Usuario(res.getString("nm_Usuario"));
		        edVolta.setDM_Perfil(res.getString("DM_Perfil"));
		        edVolta.setDM_Administrador(res.getString("DM_Administrador"));
		        edVolta.setNm_Senha(res.getString("NM_Senha"));
		        edVolta.setOid_Empresa(res.getLong("oid_Empresa"));
		        edVolta.setOid_Menu_Perfil(res.getLong("oid_Menu_Perfil"));
		        edVolta.setNm_Razao_Social(res.getString("nm_Razao_Social"));
		    }
		}
		catch(Exception exc)
		{
		    Excecoes excecoes = new Excecoes();
		    excecoes.setClasse(getClass().getName());
		    excecoes.setMensagem("Erro ao recuperar registro! [" + exc.getMessage() + "]");
		    excecoes.setMetodo("getAdminByCNPJ()");
		    excecoes.setExc(exc);
		    throw excecoes;
		}
		return edVolta;
	};

}
