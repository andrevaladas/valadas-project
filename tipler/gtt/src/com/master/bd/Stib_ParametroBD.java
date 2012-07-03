package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Stib_ParametroED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STIB - Itens a inspecionar
 * @serialData 04/2012
 */
public class Stib_ParametroBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stib_ParametroBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public void altera(Stib_ParametroED ed) throws Excecoes {
		try {
			
			Stib_ParametroED parED = this.getByRecord(ed);
			if (!doValida(parED.getNm_Parametro())) {
				sql = "INSERT INTO Stib_Parametros (" +
				"nm_Parametro," +
				"nr_Percentual_Faixa1_De," +
				"nr_Percentual_Faixa1_Ate," +
				"nr_Percentual_Faixa2_De," +
				"nr_Percentual_Faixa2_Ate," +
				"nr_Percentual_Faixa3_De," +
				"nr_Percentual_Faixa3_Ate " +
			    ") " +
				" VALUES (" +
				" '"+"nm_Parametro'"+
				",0"+
				",0"+
				",0"+
				",0"+
				",0"+
				",0"+
		  		")";
				executasql.executarUpdate(sql);
			}
			sql = "UPDATE Stib_Parametros SET " +
			" nr_Percentual_Faixa1_De = " + ed.getNr_Percentual_Faixa1_De() + 
			",nr_Percentual_Faixa1_Ate = " + ed.getNr_Percentual_Faixa1_Ate() + 
			",nr_Percentual_Faixa2_De = " + ed.getNr_Percentual_Faixa2_De() + 
			",nr_Percentual_Faixa2_Ate = " + ed.getNr_Percentual_Faixa2_Ate() + 
			",nr_Percentual_Faixa3_De = " + ed.getNr_Percentual_Faixa3_De() + 
			",nr_Percentual_Faixa3_Ate = " + ed.getNr_Percentual_Faixa3_Ate() +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " ;
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stib_ParametroED ed)");
		}
	}
	
	public Stib_ParametroED getByRecord(Stib_ParametroED ed) throws Excecoes {
		Stib_ParametroED parED = new Stib_ParametroED();
		try {
			sql = "SELECT * " +
			",usuario_Stamp as usu_Stmp " +
	  		",dt_Stamp as dt_Stmp " +
	  		",dm_Stamp as dm_Stmp " +
			"FROM Stib_Parametros as sp ";
			ResultSet res = this.executasql.executarConsulta(sql);
			if (res.next()) {
				parED = this.populaRegistro(res);
			}	
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stib_ParametroED ed)");
		}
		return parED;
	}
	
	private Stib_ParametroED populaRegistro(ResultSet res) throws SQLException {
		Stib_ParametroED ed = new Stib_ParametroED();
		ed.setNr_Percentual_Faixa1_De(res.getInt("nr_Percentual_Faixa1_De"));
		ed.setNr_Percentual_Faixa1_Ate(res.getInt("nr_Percentual_Faixa1_Ate"));
		ed.setNr_Percentual_Faixa2_De(res.getInt("nr_Percentual_Faixa2_De"));
		ed.setNr_Percentual_Faixa2_Ate(res.getInt("nr_Percentual_Faixa2_Ate"));
		ed.setNr_Percentual_Faixa3_De(res.getInt("nr_Percentual_Faixa3_De"));
		ed.setNr_Percentual_Faixa3_Ate(res.getInt("nr_Percentual_Faixa3_Ate"));
		ed.setNm_Parametro(res.getString("nm_Parametro"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
