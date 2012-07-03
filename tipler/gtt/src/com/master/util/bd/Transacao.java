package com.master.util.bd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Iterator;

import com.master.util.TransacaoLog;
import com.master.util.ed.Parametro_FixoED;

public class Transacao {

    protected ExecutaSQL sql;
    private boolean bParticipa;
    private static Parametro_FixoED parametro_FixoED = Parametro_FixoED.getInstancia();

    private static JDBCConnectionPool jdbcConPool = new JDBCConnectionPool(parametro_FixoED.getNM_Database_Driver(), 
            															   parametro_FixoED.getNM_Database_URL(), 
            															   parametro_FixoED.getNM_Database_Usuario(),
            															   parametro_FixoED.getNM_Database_Pwd());
    private TransacaoLog trancacoesLog = new TransacaoLog();
    
    public Transacao() {
        sql = null;
        bParticipa = false;
    }

    protected Transacao(ExecutaSQL executasql) {
        if (executasql != null) {
            sql = executasql;
            bParticipa = true;
        } else new Transacao();
    }

    protected void inicioTransacao() {
        try {
            if (!bParticipa && sql == null || sql.getConnection().isClosed())
            {
                sql = new ExecutaSQL();
                sql.setConnection(jdbcConPool.pegaConexao());
                if (Parametro_FixoED.getInstancia().isLogTransactions()) {
                	doSetPID();
                	sql.setTransacao(this);
                }
            }
            
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    protected void fimTransacao(boolean flag) {
        if (sql != null && !bParticipa) {
            if (flag)
                sql.commit();
            
            sql.close();
            jdbcConPool.soltaConexao(sql.getConnection());
            sql = null;
            writeLog();
        }
    }

    protected void abortaTransacao() {
        if (sql != null) {
            sql.rollback();
            sql.close();
            jdbcConPool.soltaConexao(sql.getConnection());
            sql = null;
        }
    }
    
    private void doSetPID() {
    	try {
			ResultSet res = sql.executarConsulta("select pg_backend_pid from pg_backend_pid()");
			try {
				if (res.next()) {
					trancacoesLog.setPid(res.getInt("pg_backend_pid"));
					trancacoesLog.setClasse(getClass().getName());
				}
			} finally {
		    	if (res != null) {
		            res.close();
		            if (res.getStatement() != null) {
		            	res.getStatement().close();
		            }
		    	}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
    }
    protected void addQuery(String sql) {
    	trancacoesLog.getComandos().add(sql);
    	writeLog();
    }
    protected void writeLog() {
    	if (Parametro_FixoED.getInstancia().isLogTransactions()) {
	    	try {
	    		String directory = "/tmp/";
		    	File file = new File(directory);
		    	if (!file.exists()) {
		    		directory = "c:/temp/";
		    		
		    	}
		    	String arquivo = directory + trancacoesLog.getPid() + ".log";
		    	file = new File(arquivo);
		    	OutputStream out = new FileOutputStream(file);
		    	String output = "";
		    	Iterator iterator = trancacoesLog.getComandos().iterator();
		    	while (iterator.hasNext()) {
		    		output += "\n" + iterator.next();
		    	}
		    	byte[] b = output.getBytes();
		    	out.write(b);
		    	out.flush();
		    	out.close();
	    	} catch (Throwable e) {
	    		e.printStackTrace();
	    	}
    	}
    }
}