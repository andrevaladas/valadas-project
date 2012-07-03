package jFiles; 
import java.sql.*;
import java.util.*;
public class ManipulaUsuario extends DbTransacao {

    
    public ManipulaUsuario() {
    
    }
    
    
    public void incluirUsuario(Usuario usuario){
        
        try {
        	iniciaTransacaoBD();
        	String sqlAux = "select max(idUsuario) as result from USUARIOS_BUG_TRACKING";
            ResultSet rsAux = executaSql(sqlAux);
            long id = 0;
            if (rsAux != null && rsAux.next()){
            	id = rsAux.getLong("result");
            	id++;
            }

            String sql = "INSERT INTO USUARIOS_BUG_TRACKING (idUsuario, nome, unidade, isHabilitado, userName, password, email" ;

            if (usuario.getIdTipoUsuario()!= null && usuario.getIdTipoUsuario().longValue() != 0){
    			sql+=", idTipoUsuario";
    		}
			
            sql+= ") VALUES ( (max(USUARIOS_BUG_TRACKING.idusuario)+1) , '" + usuario.getNome() + "', '" + usuario.getUnidade() + "', " + usuario.getIsHabilitado().booleanValue() + ", '" + usuario.getUserName() + "', '" + usuario.getPassword() + "', '" + usuario.getEmail() + "'";
    		
            if (usuario.getIdTipoUsuario()!= null && usuario.getIdTipoUsuario().longValue() != 0){
    			sql+= ", "+ usuario.getIdTipoUsuario().longValue();
    		}
   			sql+=")";
            System.out.println(sql);
            this.executaUpdate(sql);
            
        } catch (Exception ex) {

            ex.printStackTrace();
        
        } finally {
        	finalizaTransacaoBD();
        }
    
    }

    public void alterarUsuario(Usuario usuario){
        
        Usuario usuAtual = getUsuarioById(usuario.getIdUsuario().longValue());

        try {
        	iniciaTransacaoBD();
            String sql = "UPDATE USUARIOS_BUG_TRACKING " +
            		" set nome = '" + usuario.getNome() + "', " +
            		" unidade = '" + usuario.getUnidade() + "', " +
            		" userName = '" + usuario.getUserName() + "', ";
            		if (usuario.getPassword() != null && !usuario.getPassword().equals("")){
            			sql += " password = '" + usuario.getPassword() + "', ";
            		}
            		if (usuario.getIsHabilitado() != null){
            			sql += " isHabilitado = " + usuario.getIsHabilitado().booleanValue() + ", ";
            		}
            		if (usuario.getIdTipoUsuario() != null){
            			sql += " idTipoUsuario = " + usuario.getIdTipoUsuario().longValue() + ", ";
            		}
					sql += " email = '" +usuario.getEmail() +
						"' where idUsuario = " + usuario.getIdUsuario();
            
			//System.out.println("SQL_UPDATE = " + sql);
			executaUpdate(sql);
 			if (usuAtual.getIsHabilitado().booleanValue() != usuario.getIsHabilitado().booleanValue() && usuario.getIsHabilitado().booleanValue() == true){
                            Email email = new Email();
                            //System.out.println("vai mandar e-mail");
                            try {
                                String assunto = "InfoTracking - Acesso liberado.";
				String para = usuAtual.getEmail();
				String de = "m.macedo@deltaguialogistica.com.br";
				String mensagem ="Seu acesso ao sistema ja foi liberado. \n\n";
                                mensagem += "Administracao InfoTracking. \n Desenvolvimento de Sistema. \n Delta Guia Logistica.";
				email.sendMail(assunto, para, de, mensagem);
                            } catch (Exception ex) {
                                    ex.printStackTrace();
                            }
                        }
        } catch (Exception ex) {

            ex.printStackTrace();
        
        } finally {
        	iniciaTransacaoBD();
        }
    
    }

    public void habilitarUsuario(Vector ids, Boolean bHab){
    	String sql = "";
        try {
        	iniciaTransacaoBD();
        	for (int i = 0; i < ids.size(); i++){
        		
        		sql = "UPDATE USUARIOS_BUG_TRACKING " +
            		" set isHabilitado=" + bHab.booleanValue() + "" +
					" where idUsuario = " + ((Long) ids.elementAt(i)).longValue();
            
        		//System.out.println("SQL_UPDATE = " + sql);
        		executaUpdate(sql);
        	}
        } catch (Exception ex) {

            ex.printStackTrace();
        
        } finally {
        	finalizaTransacaoBD();
        }
    
    }
    public void excluirUsuario(Vector ids){
    	String sql = "";
    	//System.out.println("vai excluir vector = " + ids);
        try {
        	iniciaTransacaoBD();
        	for (int i = 0; ids !=null && i < ids.size(); i++){
        		
        		sql = "DELETE FROM USUARIOS_BUG_TRACKING" +
					  " where idUsuario = " + ((Long) ids.elementAt(i)).longValue();
            
        		//System.out.println("SQL_DELETE = " + sql);
        		executaUpdate(sql);
        	}
        } catch (Exception ex) {

            ex.printStackTrace();
        
        } finally {
        	finalizaTransacaoBD();
        }
    
    }
    
    
    public Vector getUsuarios(){
    
        Vector retorno = new Vector();
        try {
        	iniciaTransacaoBD();
            String sql = "SELECT * FROM USUARIOS_BUG_TRACKING ORDER BY NOME";
            ResultSet rs = executaSql(sql);
            
            while(rs.next()){
                
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(new Long(rs.getLong("idUsuario")));
                usuario.setIdTipoUsuario(new Long(rs.getLong("idTipoUsuario")));
                usuario.setEmail(rs.getString("email"));
                usuario.setIsHabilitado(new Boolean(rs.getBoolean("isHabilitado")));
                usuario.setNome(rs.getString("nome"));
                usuario.setPassword(rs.getString("password"));
                usuario.setUnidade(rs.getString("unidade"));
                usuario.setUserName(rs.getString("userName"));
                //System.out.println("Java = " + usuario.getIsHabilitado().booleanValue());
                retorno.addElement(usuario);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return retorno;
    
    }
    
    public Usuario getUsuarioById(long id){
    
        Usuario retorno = new Usuario();
        
        try {
        	iniciaTransacaoBD();
            String sql = "SELECT * FROM USUARIOS_BUG_TRACKING WHERE idusuario=" + id + " ORDER BY NOME";
            ResultSet rs = executaSql(sql);
            
            while(rs.next()){
                
                retorno.setIdUsuario(new Long(rs.getLong("idUsuario")));
                retorno.setIdTipoUsuario(new Long(rs.getLong("idTipoUsuario")));
                retorno.setEmail(rs.getString("email"));
                retorno.setIsHabilitado(new Boolean(rs.getBoolean("isHabilitado")));
                retorno.setNome(rs.getString("nome"));
                retorno.setPassword(rs.getString("password"));
                retorno.setUnidade(rs.getString("unidade"));
                retorno.setUserName(rs.getString("userName"));

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return retorno;
    
    }


    
    public Vector getAdministradores(){
    
        Usuario usuario = new Usuario();
        Vector vRetorno = new Vector();
        try {
            iniciaTransacaoBD();
            String sql = "SELECT * FROM USUARIOS_BUG_TRACKING WHERE idtipousuario = 4";
            ResultSet rs = executaSql(sql);
            
            while(rs.next()){
                usuario = new Usuario();
                usuario.setIdUsuario(new Long(rs.getLong("idUsuario")));
                usuario.setIdTipoUsuario(new Long(rs.getLong("idTipoUsuario")));
                usuario.setEmail(rs.getString("email"));
                usuario.setIsHabilitado(new Boolean(rs.getBoolean("isHabilitado")));
                usuario.setNome(rs.getString("nome"));
                usuario.setPassword(rs.getString("password"));
                usuario.setUnidade(rs.getString("unidade"));
                usuario.setUserName(rs.getString("userName"));
                vRetorno.addElement(usuario);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return vRetorno;
    
    }
    
    
    public Vector getPerfis(){
        
        Vector retorno = new Vector();
        
        try {
        	iniciaTransacaoBD();
            String sql = "SELECT * FROM PERFIL_USUARIO_BUG_TRACKING ORDER BY nmPerfilUsuario";
            ResultSet rs = executaSql(sql);
            
            while(rs.next()){
                PerfilUsuario perfilUsuario = new PerfilUsuario();
                perfilUsuario.setIdPerfil(new Long(rs.getLong("idPerfilUsuario")));
                perfilUsuario.setNmPerfil(rs.getString("nmPerfilUsuario"));
                perfilUsuario.setDsPerfil(rs.getString("dsPerfilUsuario"));
                retorno.addElement(perfilUsuario);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return retorno;
        
    }
    
    public Vector getAcessosByUsuario(long idUsuario){

    	Vector retorno = new Vector();
        
        try {
        	iniciaTransacaoBD();
            String sql = "SELECT A.NMACESSO FROM Usuarios_BUG_TRACKING as U, PERFIL_ACESSO_BUG_TRACKING AS P, ACESSO_BUG_TRACKING AS A where U.idUsuario = " + idUsuario + " AND P.IDPERFIL = U.IDTIPOUSUARIO AND P.IDACESSO = A.IDACESSO ";
            ResultSet rs = executaSql(sql);
            
            while(rs.next()){
                retorno.addElement(rs.getString("NMACESSO"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	finalizaTransacaoBD();
        }
        
        return retorno;
        
    }

    public UsuarioLogado executaLogin(String userName, String password){
    
        UsuarioLogado usuario = new UsuarioLogado();
        try {
        	iniciaTransacaoBD();
            String sql = "SELECT * FROM USUARIOS_BUG_TRACKING WHERE userName='" + userName + "' AND password='" + password + "'";
            //System.out.println("SQL = " + sql);
            ResultSet rs = executaSql(sql);
            if (rs.next()){

            	Boolean b = new Boolean(rs.getBoolean("isHabilitado"));

            	if ( b.booleanValue() == true) {
                	usuario.setIdUsuario(new Long(rs.getLong("idUsuario")));
                	usuario.setIdTipoUsuario(new Long(rs.getLong("idTipoUsuario")));
                	usuario.setEmail(rs.getString("email"));
                	usuario.setIsHabilitado(b);
                	usuario.setNome(rs.getString("nome"));
                	usuario.setPassword(rs.getString("password"));
                	usuario.setUnidade(rs.getString("unidade"));
                	usuario.setUserName(rs.getString("userName"));
                    usuario.setAcessos(this.getAcessosByUsuario(usuario.getIdUsuario().longValue()));
                }
            
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	finalizaTransacaoBD();
        }

        return usuario;
    
    }

}
