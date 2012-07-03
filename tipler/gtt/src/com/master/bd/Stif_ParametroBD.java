package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.master.ed.Stif_ParametroED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial Parametros STIF
 * @serialData 06/2012
 */
public class Stif_ParametroBD extends BancoUtil {

	String query = null;
	public Stif_ParametroBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_ParametroED inclui(Stif_ParametroED ed) throws Excecoes {
		try {
			query = "INSERT INTO Stif_Parametros (" +
				"oid_empresa,"+
				"oid_stif_parametro,"+
				//Cotação de Pneus
				"vl_pneu_novo,"+
				"vl_pneu_r1,"+
				"vl_pneu_r2,"+
				"vl_pneu_r3,"+
				"vl_pneu_r4,"+
				//Pesos para incidências
				"pc_peso_0_5,"+
				"pc_peso_6_10,"+
				"pc_peso_11_15,"+
				"pc_peso_16_20,"+
				"pc_peso_21_25,"+
				"pc_peso_26_30,"+
				"pc_peso_31_35,"+
				"pc_peso_36_40,"+
				"pc_peso_41_45,"+
				"pc_peso_46_50,"+
				"pc_peso_acima_50,"+
				"dm_Stamp, " +
	            "dt_Stamp, " +
		  	    "usuario_Stamp"+
				") " +
				" VALUES (" +
				ed.getOid_Empresa() +
				"," + ed.getOid_stif_parametro() +
				"," + ed.getVl_pneu_novo() + 
				"," + ed.getVl_pneu_r1() + 
				"," + ed.getVl_pneu_r2() + 
				"," + ed.getVl_pneu_r3() + 
				"," + ed.getVl_pneu_r4() + 
				//Pesos para incidências
				"," + ed.getPc_peso_0_5() + 
				"," + ed.getPc_peso_6_10() + 
				"," + ed.getPc_peso_11_15() + 
				"," + ed.getPc_peso_16_20() + 
				"," + ed.getPc_peso_21_25() + 
				"," + ed.getPc_peso_26_30() + 
				"," + ed.getPc_peso_31_35() + 
				"," + ed.getPc_peso_36_40() + 
				"," + ed.getPc_peso_41_45() + 
				"," + ed.getPc_peso_46_50() + 
				"," + ed.getPc_peso_acima_50() + 
				",'I'" +
		  		",'" + ed.getDt_stamp() + "'" +
		  		",'" + ed.getUsuario_Stamp() + "'" +
		  		")";
			sql.executarUpdate(query);
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_ParametroED ed)");
		}
	}

	public void alteraCotacao(Stif_ParametroED ed) throws Excecoes {
		try {
			query = "UPDATE Stif_Parametros SET " +
			//Cotação de Pneus
			"vl_pneu_novo = " + ed.getVl_pneu_novo()+
			",vl_pneu_r1 = " + ed.getVl_pneu_r1()+
			",vl_pneu_r2 = " + ed.getVl_pneu_r2()+
			",vl_pneu_r3 = " + ed.getVl_pneu_r3()+
			",vl_pneu_r4 = " + ed.getVl_pneu_r4()+
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			" WHERE " +
			" oid_stif_parametro = " + ed.getOid_stif_parametro();
			sql.executarUpdate(query);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_ParametroED ed)");
		}
	}
	
	public void alteraIncidencias(Stif_ParametroED ed) throws Excecoes {
		try {
			query = "UPDATE Stif_Parametros SET " +
			//Pesos para incidências
			"pc_peso_0_5 = " + ed.getPc_peso_0_5()+
			",pc_peso_6_10 = " + ed.getPc_peso_6_10()+
			",pc_peso_11_15 = " + ed.getPc_peso_11_15()+
			",pc_peso_16_20 = " + ed.getPc_peso_16_20()+
			",pc_peso_21_25 = " + ed.getPc_peso_21_25()+
			",pc_peso_26_30 = " + ed.getPc_peso_26_30()+
			",pc_peso_31_35 = " + ed.getPc_peso_31_35()+
			",pc_peso_36_40 = " + ed.getPc_peso_36_40()+
			",pc_peso_41_45 = " + ed.getPc_peso_41_45()+
			",pc_peso_46_50 = " + ed.getPc_peso_46_50()+
			",pc_peso_acima_50 = " + ed.getPc_peso_acima_50()+
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			" WHERE " +
			" oid_stif_parametro = " + ed.getOid_stif_parametro();
			sql.executarUpdate(query);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_ParametroED ed)");
		}
	}

	public void delete(Stif_ParametroED ed) throws Excecoes {
		try {
			query = "DELETE FROM Stif_Parametros " ;
			sql.executarUpdate(query);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stif_ParametroED ed)");
		}
	}

	public Stif_ParametroED getByRecord(Stif_ParametroED ed) throws Excecoes {
		Stif_ParametroED prED = new Stif_ParametroED();
		try {
			query = "SELECT * FROM Stif_Parametros " +
				"WHERE oid_empresa = " + ed.getOid_Empresa();
			ResultSet res = this.sql.executarConsulta(query);
			if (res.next()) {
				prED = populaRegistro(res);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_ParametroED ed)");
		}
		return prED;
	}

	private Stif_ParametroED populaRegistro(ResultSet res) throws SQLException {
		Stif_ParametroED ed = (Stif_ParametroED) beanProcessor.toBean(res, Stif_ParametroED.class);
		ed.formataMsgStamp();
		return ed;
	}
}