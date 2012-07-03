package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_Perda_PercentualED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial Perda Percentual
 * @since 06/2012
 */
public class Stif_Perda_PercentualBD extends BancoUtil {

	public Stif_Perda_PercentualBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_Perda_PercentualED inclui(Stif_Perda_PercentualED ed) throws Excecoes {
		try {
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Stif_Perdas_Percentuais (");
			insert.append("	 oid_Empresa");
			insert.append("	,pc_perda_padrao");
			insert.append("	,pc_perda_20");
			insert.append("	,pc_perda_25");
			insert.append("	,pc_perda_30");
			insert.append("	,pc_perda_35");
			insert.append("	,pc_perda_40");
			insert.append("	,pc_perda_45");
			insert.append("	,pc_perda_50");
			insert.append("	,pc_perda_55");
			insert.append("	,pc_perda_60");
			insert.append("	,pc_perda_65");
			insert.append("	,pc_perda_70");
			insert.append("	,pc_perda_75");
			insert.append("	,pc_perda_80");
			insert.append("	,pc_perda_85");
			insert.append("	,pc_perda_90");
			insert.append("	,pc_perda_95");
			insert.append("	,pc_perda_100");
			insert.append("	,pc_perda_105");
			insert.append("	,pc_perda_110");
			insert.append("	,pc_perda_115");
			insert.append("	,pc_perda_120");
			insert.append("	,pc_perda_125");
			insert.append("	,pc_perda_130");
			insert.append("	,pc_perda_135");
			insert.append("	,pc_perda_140");
			insert.append("	,pc_perda_145");
			insert.append("	,pc_perda_150");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Empresa());
			insert.append("," + ed.getPc_perda_padrao());
			insert.append("," + ed.getPc_perda_20());
			insert.append("," + ed.getPc_perda_25());
			insert.append("," + ed.getPc_perda_30());
			insert.append("," + ed.getPc_perda_35());
			insert.append("," + ed.getPc_perda_40());
			insert.append("," + ed.getPc_perda_45());
			insert.append("," + ed.getPc_perda_50());
			insert.append("," + ed.getPc_perda_55());
			insert.append("," + ed.getPc_perda_60());
			insert.append("," + ed.getPc_perda_65());
			insert.append("," + ed.getPc_perda_70());
			insert.append("," + ed.getPc_perda_75());
			insert.append("," + ed.getPc_perda_80());
			insert.append("," + ed.getPc_perda_85());
			insert.append("," + ed.getPc_perda_90());
			insert.append("," + ed.getPc_perda_95());
			insert.append("," + ed.getPc_perda_100());
			insert.append("," + ed.getPc_perda_105());
			insert.append("," + ed.getPc_perda_110());
			insert.append("," + ed.getPc_perda_115());
			insert.append("," + ed.getPc_perda_120());
			insert.append("," + ed.getPc_perda_125());
			insert.append("," + ed.getPc_perda_130());
			insert.append("," + ed.getPc_perda_135());
			insert.append("," + ed.getPc_perda_140());
			insert.append("," + ed.getPc_perda_145());
			insert.append("," + ed.getPc_perda_150());
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_Perda_PercentualED ed)");
		}
	}

	public void altera(Stif_Perda_PercentualED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Perdas_Percentuais SET");
			update.append("	 pc_perda_padrao = " + ed.getPc_perda_padrao()); 
			update.append("	,pc_perda_20 = " + ed.getPc_perda_20());
			update.append("	,pc_perda_25 = " + ed.getPc_perda_25());
			update.append("	,pc_perda_30 = " + ed.getPc_perda_30());
			update.append("	,pc_perda_35 = " + ed.getPc_perda_35());
			update.append("	,pc_perda_40 = " + ed.getPc_perda_40());
			update.append("	,pc_perda_45 = " + ed.getPc_perda_45());
			update.append("	,pc_perda_50 = " + ed.getPc_perda_50());
			update.append("	,pc_perda_55 = " + ed.getPc_perda_55());
			update.append("	,pc_perda_60 = " + ed.getPc_perda_60());
			update.append("	,pc_perda_65 = " + ed.getPc_perda_65());
			update.append("	,pc_perda_70 = " + ed.getPc_perda_70());
			update.append("	,pc_perda_75 = " + ed.getPc_perda_75());
			update.append("	,pc_perda_80 = " + ed.getPc_perda_80());
			update.append("	,pc_perda_85 = " + ed.getPc_perda_85());
			update.append("	,pc_perda_90 = " + ed.getPc_perda_90());
			update.append("	,pc_perda_95 = " + ed.getPc_perda_95());
			update.append("	,pc_perda_100 = " + ed.getPc_perda_100());
			update.append("	,pc_perda_105 = " + ed.getPc_perda_105());
			update.append("	,pc_perda_110 = " + ed.getPc_perda_110());
			update.append("	,pc_perda_115 = " + ed.getPc_perda_115());
			update.append("	,pc_perda_120 = " + ed.getPc_perda_120());
			update.append("	,pc_perda_125 = " + ed.getPc_perda_125());
			update.append("	,pc_perda_130 = " + ed.getPc_perda_130());
			update.append("	,pc_perda_135 = " + ed.getPc_perda_135());
			update.append("	,pc_perda_140 = " + ed.getPc_perda_140());
			update.append("	,pc_perda_145 = " + ed.getPc_perda_145());
			update.append("	,pc_perda_150 = " + ed.getPc_perda_150());
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Perda_Percentual = " + ed.getOid_Perda_Percentual());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_Perda_PercentualED ed)");
		}
	}

	public void deleta(Stif_Perda_PercentualED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Perdas_Percentuais ");
			delete.append(" WHERE oid_Perda_Percentual = " + ed.getOid_Perda_Percentual()); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_Perda_PercentualED ed)");
		}
	}

	public List<Stif_Perda_PercentualED> lista(Stif_Perda_PercentualED ed) throws Excecoes {
		List<Stif_Perda_PercentualED> list = new ArrayList<Stif_Perda_PercentualED>();
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT * FROM Stif_Perdas_Percentuais ");
			query.append(" WHERE oid_Empresa = " + ed.getOid_Empresa());
			if (ed.getPc_perda_padrao() > 0) {
				query.append(" AND pc_perda_padrao = "+ed.getPc_perda_padrao());
			}
			query.append(" ORDER BY pc_perda_padrao");
			
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}
	
	public Stif_Perda_PercentualED getByRecord(Stif_Perda_PercentualED ed) throws Excecoes {
		try {
			final List<Stif_Perda_PercentualED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_Perda_PercentualED ed)");
		}
		return new Stif_Perda_PercentualED();
	}

	private Stif_Perda_PercentualED populaRegistro(ResultSet rs) throws SQLException {
		Stif_Perda_PercentualED ed = (Stif_Perda_PercentualED) beanProcessor.toBean(rs, Stif_Perda_PercentualED.class);
		ed.formataMsgStamp();
		return ed;
	}
}
