package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Percentual_ReposicaoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Percentual de Reposicao GTT
 * @serialData 06/2009
 */
public class Percentual_ReposicaoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Percentual_ReposicaoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Percentual_ReposicaoED inclui(Percentual_ReposicaoED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Percentuais_Reposicoes (" +
				"oid_Percentual_Reposicao,"+
				"nr_Perc_Desgaste_De, " +
				"nr_Perc_Desgaste_Ate, " +
				"nr_Perc_Reforma, " +
				"nr_Perc_Carcaca_Vida_1, " +
				"nr_Perc_Carcaca_Vida_2, " +
				"nr_Perc_Carcaca_Vida_3, " +
				"dm_Stamp, " +
	            "dt_Stamp, " +
		  	    "usuario_Stamp"+
				") " +
				" VALUES (" +
				ed.getOid_Percentual_Reposicao() +
				"," + ed.getNr_Perc_Desgaste_De() +  
				"," + ed.getNr_Perc_Desgaste_Ate() +
				"," + ed.getNr_Perc_Reforma() + 
				"," + ed.getNr_Perc_Carcaca_Vida_1() +
				"," + ed.getNr_Perc_Carcaca_Vida_2() +
				"," + ed.getNr_Perc_Carcaca_Vida_3() +
				",'I'" +
		  		",'" + ed.getDt_stamp() + "'" +
		  		",'" + ed.getUsuario_Stamp() + "'" +
		  		")";
			executasql.executarUpdate(sql);
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Percentual_ReposicaoED ed)");
		}
	}

	public void altera(Percentual_ReposicaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Percentuais_Reposicoes SET " +
				"nr_Perc_Desgaste_De = " + ed.getNr_Perc_Desgaste_De() + 
				",nr_Perc_Desgaste_Ate = " + ed.getNr_Perc_Desgaste_Ate() +
				",nr_Perc_Reforma = " + ed.getNr_Perc_Reforma()+
				",nr_Perc_Carcaca_Vida_1 = " + ed.getNr_Perc_Carcaca_Vida_1() +
				",nr_Perc_Carcaca_Vida_2 = " + ed.getNr_Perc_Carcaca_Vida_2() +
				",nr_Perc_Carcaca_Vida_3 = " + ed.getNr_Perc_Carcaca_Vida_3() +
				",dt_Stamp = '" + ed.getDt_stamp() + "' " +
				",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
				",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
				" WHERE " +
				" oid_Percentual_Reposicao = " + ed.getOid_Percentual_Reposicao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Percentual_ReposicaoED ed)");
		}
	}

	public void delete(Percentual_ReposicaoED ed) throws Excecoes {
		try {
			sql = "DELETE " +
				"FROM Percentuais_Reposicoes " ;
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Percentual_ReposicaoED ed)");
		}
	}
	
	public ArrayList lista(Percentual_ReposicaoED ed) throws Excecoes {

		ArrayList list = new ArrayList();
		try {
			sql = "SELECT * " +
  			",pr.usuario_Stamp as usu_Stmp " +
  			",pr.dt_Stamp as dt_Stmp " +
  			",pr.dm_Stamp as dm_Stmp " +
  			"FROM Percentuais_Reposicoes as pr " +
			"WHERE 1 = 1 " ;
			if (ed.getOid_Percentual_Reposicao()>0) 
				sql += "and pr.oid_Percentual_Reposicao = " + ed.getOid_Percentual_Reposicao();
			if (ed.getNr_Perc_Busca()> 0 )
				sql +=" and " + ed.getNr_Perc_Busca() + " between nr_Perc_Desgaste_De and nr_Perc_Desgaste_Ate ";
			sql += "ORDER BY " +
			"pr.nr_perc_desgaste_de, pr.nr_perc_desgaste_ate";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(BandaED ed)");
		}
	}

	public Percentual_ReposicaoED getByRecord(Percentual_ReposicaoED ed) throws Excecoes {
		Percentual_ReposicaoED prED = new Percentual_ReposicaoED();
		try {
			sql = "SELECT * " +
	  			",Percentuais_Reposicoes.usuario_Stamp as usu_Stmp " +
	  			",Percentuais_Reposicoes.dt_Stamp as dt_Stmp " +
	  			",Percentuais_Reposicoes.dm_Stamp as dm_Stmp " +
	  			"FROM Percentuais_Reposicoes " +
				"WHERE 1=1 " ;
				if (ed.getOid_Percentual_Reposicao()>0)
					sql +=" and oid_Percentual_Reposicao = " + ed.getOid_Percentual_Reposicao();
				else
					sql +=" and " + ed.getNr_Perc_Busca() + " between nr_Perc_Desgaste_De and nr_Perc_Desgaste_Ate";
			ResultSet res = this.executasql.executarConsulta(sql);
			if (res.next()) {
				prED = populaRegistro(res);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Percentual_ReposicaoED ed)");
		}
		return prED;
	}
	
	private Percentual_ReposicaoED populaRegistro(ResultSet res) throws SQLException {
		Percentual_ReposicaoED ed = new Percentual_ReposicaoED();
		ed.setOid_Percentual_Reposicao(res.getInt("oid_Percentual_Reposicao"));
		ed.setNr_Perc_Desgaste_De(res.getDouble("nr_Perc_Desgaste_De"));
		ed.setNr_Perc_Desgaste_Ate(res.getDouble("nr_Perc_Desgaste_Ate"));
		ed.setNr_Perc_Reforma(res.getDouble("nr_Perc_Reforma"));
		ed.setNr_Perc_Carcaca_Vida_1(res.getDouble("nr_Perc_Carcaca_Vida_1"));
		ed.setNr_Perc_Carcaca_Vida_2(res.getDouble("nr_Perc_Carcaca_Vida_2"));
		ed.setNr_Perc_Carcaca_Vida_3(res.getDouble("nr_Perc_Carcaca_Vida_3"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
