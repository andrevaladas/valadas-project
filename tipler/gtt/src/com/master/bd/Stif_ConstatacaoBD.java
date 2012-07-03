package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_ConstatacaoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial STIF- Constatações
 * @since 06/2012
 */
public class Stif_ConstatacaoBD extends BancoUtil {

	public Stif_ConstatacaoBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_ConstatacaoED inclui(Stif_ConstatacaoED ed) throws Excecoes {
		try {
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Stif_Constatacoes (");
			insert.append("	 oid_Veiculo_Inspecao");
			insert.append("	,oid_Perda_Percentual");
			insert.append("	,nr_Eixo");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Veiculo_Inspecao()); 
			insert.append("," + ed.getOid_Perda_Percentual());
			insert.append("," + ed.getNr_Eixo());
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_ConstatacaoED ed)");
		}
	}

	public void altera(Stif_ConstatacaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Constatacoes SET");
			update.append("	 nr_Eixo = " + ed.getNr_Eixo());
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Constatacao = " + ed.getOid_Constatacao());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_ConstatacaoED ed)");
		}
	}

	public void deleta(Stif_ConstatacaoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Constatacoes ");
			delete.append(" WHERE oid_Constatacao = " + ed.getOid_Constatacao()); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_ConstatacaoED ed)");
		}
	}
	public void deletaAllFromVeiculoInspecao(long oidVeiculoInspecao) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Constatacoes ");
			delete.append(" WHERE oid_Veiculo_Inspecao = " + oidVeiculoInspecao); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_ConstatacaoED ed)");
		}
	}

	public List<Stif_ConstatacaoED> lista(Stif_ConstatacaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT * ");
			query.append(" FROM Stif_Constatacoes c");
			query.append(" WHERE c.oid_Veiculo_Inspecao = " + ed.getOid_Veiculo_Inspecao());
			query.append(" ORDER BY c.nr_Eixo");

			final List<Stif_ConstatacaoED> list = new ArrayList<Stif_ConstatacaoED>();
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}
	
	public Stif_ConstatacaoED getByRecord(Stif_ConstatacaoED ed) throws Excecoes {
		try {
			final List<Stif_ConstatacaoED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_ConstatacaoED ed)");
		}
		return new Stif_ConstatacaoED();
	}

	private Stif_ConstatacaoED populaRegistro(ResultSet rs) throws SQLException {
		final Stif_ConstatacaoED ed = (Stif_ConstatacaoED) beanProcessor.toBean(rs, Stif_ConstatacaoED.class);
		ed.formataMsgStamp();
		return ed;
	}
}
