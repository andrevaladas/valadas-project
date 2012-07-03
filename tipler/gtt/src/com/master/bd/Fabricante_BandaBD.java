/*
 * Created on 11/11/2004
 *
 */
package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Fabricante_BandaED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis Steigleder
 * @serial Fabricantes de 
 * @serialData 06/2007
 */
public class Fabricante_BandaBD extends BancoUtil {

	private ExecutaSQL executaSQL;

	String sql=null;

	public Fabricante_BandaBD (ExecutaSQL executaSQL) {
		super(executaSQL);
		this.executaSQL = executaSQL;
	}

	public Fabricante_BandaED inclui (Fabricante_BandaED ed) throws Excecoes {
		try {
			//ed.setOid_Fabricante_Banda (getAutoIncremento ("oid_Fabricante_Banda" , "Fabricantes_Bandas"));
			sql = "INSERT INTO Fabricantes_Bandas (" +
			//"oid_Fabricante_Banda " +
			"cd_Fabricante_Banda " +
			",nm_Fabricante_Banda " +
			",dm_Stamp" +
		  	",dt_Stamp" +
		  	",usuario_Stamp" ;
			sql+=") values ( '" + 
			//ed.getOid_Fabricante_Banda () + ", '" + 
			(doValida(ed.getCd_Fabricante_Banda()) ? ed.getCd_Fabricante_Banda(): "" ) + "'" +
			", '" + ed.getNm_Fabricante_Banda () + "'" ;
			sql+= ",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" ;
			sql+=")";
			executaSQL.executarUpdate (sql);
			// Pega o Oid
			ed.setOid_Fabricante_Banda(getSeq("Fabricantes_Bandas_oid_Fabricante_Banda_seq"));
			return ed;
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (Fabricante_BandaED ed)");
		}
	}

	public void altera (Fabricante_BandaED ed) throws Excecoes {
		try {
			sql = "UPDATE Fabricantes_Bandas SET " +
			"cd_Fabricante_Banda = '" + (doValida(ed.getCd_Fabricante_Banda ()) ? ed.getCd_Fabricante_Banda () : "" ) + "'" +
			",nm_Fabricante_Banda = '" + ed.getNm_Fabricante_Banda () + "' " +
			",dm_Stamp = 'A'" +
  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' " +
			"WHERE " +
			"oid_Fabricante_Banda = " + ed.getOid_Fabricante_Banda ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (Fabricante_BandaED ed)");
		}
	}

	public void delete (Fabricante_BandaED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Fabricantes_Bandas " +
			"WHERE " +
			"oid_Fabricante_Banda = " + ed.getOid_Fabricante_Banda ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (Fabricante_BandaED ed)");
		}
	}

	public ArrayList<Fabricante_BandaED> lista (Fabricante_BandaED ed) throws Excecoes {
		ArrayList<Fabricante_BandaED> list = new ArrayList<Fabricante_BandaED> ();
		try {
			sql = "SELECT * " +
	  		",f.usuario_Stamp as usu_Stmp " +
	  		",f.dt_Stamp as dt_Stmp " +
	  		",f.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Fabricantes_Bandas as f " +
			"WHERE 1=1 ";
			if (ed.getOid_Fabricante_Banda () > 0) 
				sql += " and f.oid_Fabricante_Banda = " + ed.getOid_Fabricante_Banda ();
			else {
				if (doValida(ed.getCd_Fabricante_Banda())) 
					sql += " and f.cd_Fabricante_Banda like '%" + ed.getCd_Fabricante_Banda () + "%'";
				if (doValida(ed.getNm_Fabricante_Banda())) 
					sql += " and f.nm_Fabricante_Banda like '%" + ed.getNm_Fabricante_Banda () + "%'";
			}
			sql += " ORDER BY " +
			"f.nm_Fabricante_Banda";
			ResultSet rs = executaSQL.executarConsulta (sql);
			while (rs.next ()) {
				list.add (populaRegistro(rs));
			}
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Fabricante_BandaED ed)");
		}
		return list;
	}

	public Fabricante_BandaED getByRecord(Fabricante_BandaED ed) throws Excecoes {
		
		try {
			ArrayList<Fabricante_BandaED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Fabricante_BandaED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Fabricante_BandaED ed)");
		}
		return new Fabricante_BandaED();
	}


	public Fabricante_BandaED populaRegistro(ResultSet res) throws SQLException {
		Fabricante_BandaED ed = new Fabricante_BandaED();
		ed.setOid_Fabricante_Banda(res.getInt("oid_Fabricante_Banda"));
		ed.setCd_Fabricante_Banda(res.getString("cd_Fabricante_Banda"));
		ed.setNm_Fabricante_Banda(res.getString("nm_Fabricante_Banda"));
	    ed.setUsuario_Stamp(res.getString("usu_Stmp"));
	    ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
	    return ed;	  
	}
}