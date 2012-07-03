package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.master.ed.Parametro_GttED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Parametros GTT
 * @serialData 06/2009
 */
public class Parametro_GttBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Parametro_GttBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Parametro_GttED inclui(Parametro_GttED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Parametros_Gtt (" +
				"oid_Parametro_Gtt,"+
				"nr_Perc_Perda_0_30, " +
				"nr_Perc_Perda_31_60, " +
				"nr_Perc_Perda_61_90, " +
				"nr_Perc_Perda_91_120, " +
				"nr_Twi, " +
				"dm_Stamp, " +
	            "dt_Stamp, " +
		  	    "usuario_Stamp"+
				") " +
				" VALUES (" +
				ed.getOid_Parametro_Gtt() +
				"," + ed.getNr_Perc_Perda_0_30() +  
				"," + ed.getNr_Perc_Perda_31_60() + 
				"," + ed.getNr_Perc_Perda_61_90() +
				"," + ed.getNr_Perc_Perda_91_120() +
				"," + ed.getNr_Twi() +
				",'I'" +
		  		",'" + ed.getDt_stamp() + "'" +
		  		",'" + ed.getUsuario_Stamp() + "'" +
		  		")";
			executasql.executarUpdate(sql);
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Parametro_GttED ed)");
		}
	}

	public void altera(Parametro_GttED ed) throws Excecoes {
		try {
			sql = "UPDATE Parametros_Gtt SET " +
				"nr_Perc_Perda_0_30 = " + ed.getNr_Perc_Perda_0_30() + 
				",nr_Perc_Perda_31_60 = " + ed.getNr_Perc_Perda_31_60()+
				",nr_Perc_Perda_61_90 = " + ed.getNr_Perc_Perda_61_90() +
				",nr_Perc_Perda_91_120 = " + ed.getNr_Perc_Perda_91_120() +
				",nr_Twi = " + ed.getNr_Twi() +
				",dt_Stamp = '" + ed.getDt_stamp() + "' " +
				",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
				",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
				" WHERE " +
				" oid_Parametro_Gtt = " + ed.getOid_Parametro_Gtt();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Parametro_GttED ed)");
		}
	}

	public void delete(Parametro_GttED ed) throws Excecoes {
		try {
			sql = "DELETE " +
				"FROM Parametros_Gtt " ;
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Parametro_GttED ed)");
		}
	}

	public Parametro_GttED getByRecord(Parametro_GttED ed) throws Excecoes {
		Parametro_GttED prED = new Parametro_GttED();
		try {
			sql = "SELECT * " +
	  			",Parametros_Gtt.usuario_Stamp as usu_Stmp " +
	  			",Parametros_Gtt.dt_Stamp as dt_Stmp " +
	  			",Parametros_Gtt.dm_Stamp as dm_Stmp " +
	  			"FROM Parametros_Gtt " +
				"WHERE " +
				"oid_Parametro_Gtt = " + ed.getOid_Parametro_Gtt();
			ResultSet res = this.executasql.executarConsulta(sql);
			if (res.next()) {
				prED = populaRegistro(res);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Parametro_GttED ed)");
		}
		return prED;
	}
	
	private Parametro_GttED populaRegistro(ResultSet res) throws SQLException {
		Parametro_GttED ed = new Parametro_GttED();
		ed.setOid_Parametro_Gtt(res.getInt("oid_Parametro_Gtt"));
		ed.setNr_Twi(res.getDouble("nr_Twi"));
		ed.setNr_Perc_Perda_0_30(res.getDouble("nr_Perc_Perda_0_30"));
		ed.setNr_Perc_Perda_31_60(res.getDouble("nr_Perc_Perda_31_60"));
		ed.setNr_Perc_Perda_61_90(res.getDouble("nr_Perc_Perda_61_90"));
		ed.setNr_Perc_Perda_91_120(res.getDouble("nr_Perc_Perda_91_120"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
