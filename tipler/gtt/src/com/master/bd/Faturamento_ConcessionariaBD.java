/*
 * Created on 10/06/2009
 */
package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Faturamento_ConcessionariaED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis Steigleder
 * @serial Faturamento Concessionaria
 * @serialData 06/2009
 */
public class Faturamento_ConcessionariaBD extends BancoUtil {

	private ExecutaSQL executaSQL;

	String sql=null;

	public Faturamento_ConcessionariaBD (ExecutaSQL executaSQL) {
		super(executaSQL);
		this.executaSQL = executaSQL;
	}

	public Faturamento_ConcessionariaED inclui (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			ed.setDt_stamp(Data.getDataDMY());
			//ed.setOid_Faturamento_Concessionaria (getAutoIncremento ("oid_Faturamento_Concessionaria" , "Faturamentos_Concessionarias"));
			sql = "INSERT INTO Faturamentos_Concessionarias (" +
				//"oid_Faturamento_Concessionaria " +
				"oid_Concessionaria" +
				",dm_Mes_Ano " +
				",vl_Faturamento " +
				",dm_Stamp " +
	            ",dt_Stamp " +
		  	    ",usuario_Stamp"+				
				") " +
				"values " +
				"(" + 
				//ed.getOid_Faturamento_Concessionaria () + ", " + 
				ed.getOid_Concessionaria() +
				", '" + ed.getDm_Mes_Ano() + "'" +
				", " + ed.getVl_Faturamento() +
				",'I'" +
		  		",'" + ed.getDt_stamp() + "'" +
		  		",'" + ed.getUsuario_Stamp() + "'" +
		  		")";
			executaSQL.executarUpdate (sql);
			ed.setOid_Faturamento_Concessionaria(getSeq("Faturamentos_Concessionarias_oid_Faturamento_Concessionaria_seq"));
			return ed;
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (Faturamento_ConcessionariaED ed)");
		}
	}

	public void altera (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			sql = "UPDATE Faturamentos_Concessionarias SET " +
				"dm_Mes_Ano = '" + ed.getDm_Mes_Ano() + "' "+
				",vl_Faturamento = " + ed.getVl_Faturamento() + 
				",dm_Stamp = 'A'" +
	  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
	  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' " +
				"WHERE " +
				"oid_Faturamento_Concessionaria = " + ed.getOid_Faturamento_Concessionaria ();
				executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (Faturamento_ConcessionariaED ed)");
		}
	}

	public void delete (Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Faturamentos_Concessionarias " +
				"WHERE " +
				"oid_Faturamento_Concessionaria = " + ed.getOid_Faturamento_Concessionaria ();
				executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (Faturamento_ConcessionariaED ed)");
		}
	}

	public ArrayList<Faturamento_ConcessionariaED> lista (Faturamento_ConcessionariaED ed) throws Excecoes {
		ArrayList<Faturamento_ConcessionariaED> list = new ArrayList<Faturamento_ConcessionariaED> ();
		try {
			sql = "SELECT * " +
				",f.usuario_Stamp as usu_Stmp " +
				",f.dt_Stamp as dt_Stmp " +
				",f.dm_Stamp as dm_Stmp " +
				"FROM " +
				"Faturamentos_Concessionarias as f left join Empresas as e on f.oid_Concessionaria = e.oid_Empresa " +
				"WHERE 1=1 ";
			if (ed.getOid_Faturamento_Concessionaria () > 0) 
				sql += " and oid_Faturamento_Concessionaria = " + ed.getOid_Faturamento_Concessionaria ();
			else { 
				if (ed.getOid_Concessionaria() > 0) 
					sql+= " and f.oid_Concessionaria = " + ed.getOid_Concessionaria();
				if (doValida(ed.getDm_Mes_Ano())) 
					sql += " and f.dm_Mes_Ano = '" + ed.getDm_Mes_Ano() + "'";
				sql += " ORDER BY " +
				"nm_Razao_Social, substr(dm_Mes_Ano,4,4)||substr(dm_Mes_Ano,1,2) ";
			}
			ResultSet rs = executaSQL.executarConsulta (sql);
			while (rs.next ()) {
				list.add (populaRegistro(rs));
			}
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Faturamento_ConcessionariaED ed)");
		}
		return list;
	}
	
	public Faturamento_ConcessionariaED getUltimo(Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			sql = "SELECT * " +
			",f.usuario_Stamp as usu_Stmp " +
			",f.dt_Stamp as dt_Stmp " +
			",f.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Faturamentos_Concessionarias as f left join Empresas as e on f.oid_Concessionaria = e.oid_Empresa " +
			"WHERE 1=1 ";
			if (ed.getOid_Faturamento_Concessionaria () > 0) 
				sql += " and oid_Faturamento_Concessionaria = " + ed.getOid_Faturamento_Concessionaria ();
			else { 
				if (ed.getOid_Concessionaria() > 0) 
					sql+= " and f.oid_Concessionaria = " + ed.getOid_Concessionaria();
				if (doValida(ed.getDm_Mes_Ano())) 
					sql += " and f.dm_Mes_Ano <= '" + ed.getDm_Mes_Ano() + "'";
				sql += " ORDER BY " +
				"nm_Razao_Social, substr(dm_Mes_Ano,4,4)||substr(dm_Mes_Ano,1,2) desc ";
			}
			ResultSet rs = executaSQL.executarConsulta (sql);
			if (rs.next()) {
				return (populaRegistro(rs));
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getUltimo(Faturamento_ConcessionariaED ed)");
		}
		return new Faturamento_ConcessionariaED();
	}

	public Faturamento_ConcessionariaED getFaturamento(Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			Faturamento_ConcessionariaED fatED = new Faturamento_ConcessionariaED();
			// Tenta pegar do último mes menor ou igual ao solicitado
			fatED = this.getUltimo(ed);
			if (fatED.getOid_Faturamento_Concessionaria()>0) {
				return fatED;
			} else {
				// Pega o último da empresa sem o mes 
				ed.setDm_Mes_Ano(null);
				return this.getUltimo(ed);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getUltimo(Faturamento_ConcessionariaED ed)");
		}
	}
	
	public Faturamento_ConcessionariaED getByRecord(Faturamento_ConcessionariaED ed) throws Excecoes {
		try {
			ArrayList<Faturamento_ConcessionariaED> lista = this.lista (ed);
			if(!lista.isEmpty()) {
				return (Faturamento_ConcessionariaED) lista.get(0);
			}	
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Faturamento_ConcessionariaED ed)");
		}
		return new Faturamento_ConcessionariaED();
	}
	
	public Faturamento_ConcessionariaED populaRegistro(ResultSet res) throws SQLException {
		Faturamento_ConcessionariaED ed = new Faturamento_ConcessionariaED();
		ed.setOid_Faturamento_Concessionaria(res.getLong("oid_Faturamento_Concessionaria"));
		ed.setOid_Concessionaria(res.getLong("oid_Concessionaria"));
		ed.setDm_Mes_Ano(res.getString("dm_Mes_Ano"));
		ed.setVl_Faturamento(res.getDouble("vl_Faturamento"));
		ed.setNm_Razao_Social(res.getString("nm_Razao_Social"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}
	
}