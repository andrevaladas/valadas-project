package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_Pneu_InspecaoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial STIF- Pneus Inspeções
 * @since 06/2012
 */
public class Stif_Pneu_InspecaoBD extends BancoUtil {

	public Stif_Pneu_InspecaoBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_Pneu_InspecaoED inclui(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			ed.setOid_Pneu_Inspecao(getSeqNextval("Stif_Pneus_Inspecoes_seq"));
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Stif_Pneus_Inspecoes (");
			insert.append("	 oid_Pneu_Inspecao");
			insert.append("	,oid_Veiculo_Inspecao");
			insert.append("	,dm_Posicao");
			insert.append("	,nr_Eixo");
			insert.append("	,nr_Fogo");
			insert.append("	,dm_Vida_Pneu");
			insert.append("	,nr_Mm_Sulco");
			insert.append("	,nr_Pressao");
			insert.append("	,oid_Pneu_Dimensao");
			insert.append("	,oid_Fabricante_Pneu");
			insert.append("	,oid_Modelo_Pneu");
			insert.append("	,oid_Fabricante_Banda");
			insert.append("	,oid_Banda");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Pneu_Inspecao()); 
			insert.append("," + ed.getOid_Veiculo_Inspecao()); 
			insert.append("," + getSQLString(ed.getDm_Posicao()));
			insert.append("," + ed.getNr_Eixo());
			insert.append("," + ed.getNr_Fogo());
			insert.append("," + getSQLString(ed.getDm_Vida_Pneu()));
			insert.append("," + ed.getNr_Mm_Sulco());
			insert.append("," + ed.getNr_Pressao());
			insert.append("," + ed.getOid_Pneu_Dimensao());
			insert.append("," + ed.getOid_Fabricante_Pneu());
			insert.append("," + ed.getOid_Modelo_Pneu());
			insert.append("," + ed.getOid_Fabricante_Banda());
			insert.append("," + ed.getOid_Banda());
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_Pneu_InspecaoED ed)");
		}
	}

	public void altera(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Pneus_Inspecoes SET");
			update.append("	 nr_Fogo = " + ed.getNr_Fogo());
			update.append("	,dm_Vida_Pneu = " + getSQLString(ed.getDm_Vida_Pneu()));
			update.append("	,nr_Mm_Sulco = " + ed.getNr_Mm_Sulco());
			update.append("	,nr_Pressao = " + ed.getNr_Pressao());
			update.append("	,oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao());
			update.append("	,oid_Fabricante_Pneu = " + ed.getOid_Fabricante_Pneu());
			update.append("	,oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu());
			update.append("	,oid_Fabricante_Banda = " + ed.getOid_Fabricante_Banda());
			update.append("	,oid_Banda = " + ed.getOid_Banda());
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Pneu_Inspecao = " + ed.getOid_Pneu_Inspecao());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_Pneu_InspecaoED ed)");
		}
	}

	public void deleta(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Pneus_Inspecoes ");
			delete.append(" WHERE oid_Pneu_Inspecao = " + ed.getOid_Pneu_Inspecao()); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_Pneu_InspecaoED ed)");
		}
	}

	/**
	 * Verifica se número fogo está disponível no veículo 
	 */
	public boolean validaNrFogo(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT count(1) as rowCount");
			query.append(" FROM Stif_Pneus_Inspecoes pi");
			query.append(" WHERE pi.oid_Veiculo_Inspecao = " + ed.getOid_Veiculo_Inspecao());
			query.append("   AND pi.nr_Fogo = "+ed.getNr_Fogo());
			if (ed.getOid_Pneu_Inspecao() > 0) {
				query.append("   AND pi.oid_Pneu_Inspecao <> "+ed.getOid_Pneu_Inspecao());
			}

			final ResultSet res = sql.executarConsulta(query.toString());
			if (res.next()) {
				return res.getInt("rowCount") < 1;
			}
			return false;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"validaNrFogo()");
		}
	}
	
	public List<Stif_Pneu_InspecaoED> lista(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT * ");
			query.append(" FROM Stif_Pneus_Inspecoes i");
			query.append(" WHERE i.oid_Veiculo_Inspecao = " + ed.getOid_Veiculo_Inspecao());
			if (doValida(ed.getDm_Posicao())) {
				query.append(" AND i.dm_Posicao = '"+ed.getDm_Posicao()+"'");
			}

			final List<Stif_Pneu_InspecaoED> list = new ArrayList<Stif_Pneu_InspecaoED>();
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}
	
	public Stif_Pneu_InspecaoED getByRecord(Stif_Pneu_InspecaoED ed) throws Excecoes {
		try {
			final List<Stif_Pneu_InspecaoED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_Pneu_InspecaoED ed)");
		}
		return new Stif_Pneu_InspecaoED();
	}

	private Stif_Pneu_InspecaoED populaRegistro(ResultSet rs) throws SQLException {
		final Stif_Pneu_InspecaoED ed = (Stif_Pneu_InspecaoED) beanProcessor.toBean(rs, Stif_Pneu_InspecaoED.class);
		ed.formataMsgStamp();
		return ed;
	}
}