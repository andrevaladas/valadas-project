package jFiles;   

import javax.naming.*;
import javax.sql.*;

import java.sql.*;


public class DataBase { 

    public DataBase() {

    }
    
    public static Connection retornaConecxao(){

		
 		try{
      		Context ctx = new InitialContext();
			if(ctx == null ) 
				throw new Exception("Sem Contexto");
			
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgresConn");
      		System.out.println("teste 2");
			
			if (ds != null) {
				return ds.getConnection();
			} else {
				return null;
			}
	    	
	    }catch(Exception e) {
	      e.printStackTrace();
	    }
	
        return null;


/*
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch(Exception ex) {
            System.out.println("*** Erro ao carregar o drive ***");
            ex.printStackTrace();
            return null;
        }
        
        try {
        	conn = DriverManager.getConnection("jdbc:postgresql://192.168.10.215:5432/panazzolo", "postgres", "panazzolo");
            //conn = DriverManager.getConnection("jdbc:odbc:BugTrackingODBC","","");
        } catch(Exception ex) {
            System.out.println("*** Erro na conexão com o banco ***");
            ex.printStackTrace();
            return null;
        }
        return conn;
  */      
    }

}
