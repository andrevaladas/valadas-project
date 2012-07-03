/*
 * Created on 11/11/2004
 *
 */
package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.RegionalED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis Steigleder
 * @serial Regional
 * @serialData 01/2010
 */
public class RegionalBD extends BancoUtil {

	private ExecutaSQL executaSQL;

	String sql=null;

	public RegionalBD (ExecutaSQL executaSQL) {
		super(executaSQL);
		this.executaSQL = executaSQL;
	}

	public RegionalED inclui (RegionalED ed) throws Excecoes {
		try {
			ed.setDt_stamp(Data.getDataDMY());
			sql = "INSERT INTO regionais (" +
			//"oid_Fabricante_Pneu " +
			"cd_Regional " +
			",nm_Regional " +
			",dm_Stamp" +
			",dt_Stamp" +
			",usuario_Stamp"+
			") " +
			"values ('" + 
			ed.getCd_Regional() + "'" +
			", '" + ed.getNm_Regional () + "'" +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executaSQL.executarUpdate (sql);
			ed.setOid_Regional(getSeq("regionais_oid_Regional_seq"));
			return ed;
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (RegionalED ed)");
		}
	}

	public void altera (RegionalED ed) throws Excecoes {
		try {
			sql = "UPDATE regionais SET " +
			"cd_Regional = " + ed.getCd_Regional () +
			",nm_Regional = '" + ed.getNm_Regional () + "' " +
			",dm_Stamp = 'A'" +
  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' " +
			"WHERE " +
			"oid_Regional = " + ed.getOid_Regional ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (RegionalED ed)");
		}
	}

	public void delete (RegionalED ed) throws Excecoes {
		try {
			sql = "DELETE FROM regionais " +
			"WHERE " +
			"oid_Regional = " + ed.getOid_Regional ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (RegionalED ed)");
		}
	}

	public ArrayList lista (RegionalED ed) throws Excecoes {
		ArrayList list = new ArrayList ();
		try {
			sql = "SELECT * " +
	  		",regionais.usuario_Stamp as usu_Stmp " +
	  		",regionais.dt_Stamp as dt_Stmp " +
	  		",regionais.dm_Stamp as dm_Stmp " +
			"FROM " +
			"regionais " +
			"WHERE 1=1 ";
			if (ed.getOid_Regional () > 0) 
				sql += " and oid_Regional = " + ed.getOid_Regional ();
			if (doValida(ed.getCd_Regional ())) 
				sql += " and cd_Regional = '" + ed.getCd_Regional () + "'";
			if (doValida(ed.getNm_Regional())) 
				sql += " and nm_Regional like '%" + ed.getNm_Regional () + "%'";
			sql += " ORDER BY " +
			"nm_Regional";
			ResultSet rs = executaSQL.executarConsulta (sql);
			while (rs.next ()) {
				list.add (populaRegistro(rs));
			}
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (RegionalED ed)");
		}
		return list;
	}
	
	public RegionalED getByRecord(RegionalED ed) throws Excecoes {
		try {
			ArrayList lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (RegionalED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(RegionalED ed)");
		}
		return new RegionalED();
	}

	public RegionalED populaRegistro(ResultSet res) throws SQLException {
		RegionalED ed = new RegionalED();
		ed.setOid_Regional(res.getInt("oid_Regional"));
		ed.setCd_Regional(res.getString("cd_Regional"));
		ed.setNm_Regional(res.getString("nm_Regional"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}
	
}