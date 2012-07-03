/*
 * Created on Jun 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package jFiles;
import java.sql.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DbTransacao {
    private Connection conn;
    private Statement stmt;
	
    public void iniciaTransacaoBD(){
        try {

        	conn = DataBase.retornaConecxao();
            stmt = conn.createStatement();

        } catch (Exception ex) {

            ex.printStackTrace();

        }
        
    }

    public void finalizaTransacaoBD(){
       	conn = null;
    }
    
    public void executaUpdate(String sql){
    	try {
    		stmt.executeUpdate(sql);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public ResultSet executaSql(String sql){
    	ResultSet rs = null;
    	try {
    		rs = stmt.executeQuery(sql);
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    	return rs;
    }
    public final void lockTable(String nmTable, String nmTransaction){

    	String lock = "LOCK TABLE " + nmTable + " IN ACCESS EXCLUSIVE MODE";
	Statement st = null;
	ResultSet rs = null;
		try	{
			System.out.println("conn = " + conn);
			st = conn.createStatement();
	        rs = st.executeQuery("BEGIN " + nmTransaction + ";");
	        rs = st.executeQuery(lock);
	        st.close();
	    } catch(Exception e) {
	        e.printStackTrace(); 
	    }
    }
    public final void unlockTable(String nmTransaction){

		Statement st = null;
		ResultSet rs = null;
		try	{
			System.out.println("conn = " + conn);
			st = conn.createStatement();
	  		rs = st.executeQuery("COMMIT " + nmTransaction + ";");
	    } catch(Exception e){
	        e.printStackTrace(); 
	    }
        
    }
	
}
