package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_Problema_PneuED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial STIF- Problemas Pneus
 * @since 06/2012
 */
public class Stif_Problema_PneuBD extends BancoUtil {

	public Stif_Problema_PneuBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_Problema_PneuED inclui(Stif_Problema_PneuED ed) throws Excecoes {
		try {
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Stif_Problemas_Pneus (");
			insert.append("	 oid_Pneu_Inspecao");
			insert.append("	,oid_Stif_Problema");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Pneu_Inspecao()); 
			insert.append("," + ed.getOid_Stif_Problema()); 
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_Problema_PneuED ed)");
		}
	}

	public void deletaAllFromPneuInspecao(long oidPneuInspecao) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Problemas_Pneus ");
			delete.append(" WHERE oid_Pneu_Inspecao = " + oidPneuInspecao); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_Problema_PneuED ed)");
		}
	}

	public List<Stif_Problema_PneuED> lista(Stif_Problema_PneuED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT * ");
			query.append(" FROM Stif_Problemas_Pneus i");
			query.append(" WHERE i.oid_Pneu_Inspecao = " + ed.getOid_Pneu_Inspecao());

			final List<Stif_Problema_PneuED> list = new ArrayList<Stif_Problema_PneuED>();
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}

	private Stif_Problema_PneuED populaRegistro(ResultSet rs) throws SQLException {
		final Stif_Problema_PneuED ed = (Stif_Problema_PneuED) beanProcessor.toBean(rs, Stif_Problema_PneuED.class);
		ed.formataMsgStamp();
		return ed;
	}
}