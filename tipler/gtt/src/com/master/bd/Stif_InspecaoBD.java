package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.EmpresaED;
import com.master.ed.Stif_InspecaoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial STIF- Inspeção
 * @since 06/2012
 */
public class Stif_InspecaoBD extends BancoUtil {

	public Stif_InspecaoBD(final ExecutaSQL sql) {
		super(sql);
	}

	public Stif_InspecaoED inclui(final Stif_InspecaoED ed) throws Excecoes {
		try {
			ed.setOid_Inspecao(getAutoIncremento("oid_Inspecao", "Stif_Inspecoes"));
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Stif_Inspecoes (");
			insert.append("	 oid_Inspecao");
			insert.append("	,oid_Empresa");
			insert.append("	,oid_Usuario");
			insert.append("	,oid_Cliente");
			insert.append("	,nr_Veiculos");
			insert.append("	,dt_Inspecao");
			//Cotação de Pneus
			insert.append("	,vl_pneu_novo");
			insert.append("	,vl_pneu_r1");
			insert.append("	,vl_pneu_r2");
			insert.append("	,vl_pneu_r3");
			insert.append("	,vl_pneu_r4");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Inspecao()); 
			insert.append("," + ed.getOid_Empresa()); 
			insert.append("," + ed.getOid_Usuario()); 
			insert.append("," + ed.getOid_Cliente());
			insert.append("," + ed.getNr_Veiculos());
			insert.append("," + getSQLString(ed.getDt_Inspecao()));
			insert.append("," + ed.getVl_pneu_novo());
			insert.append("," + ed.getVl_pneu_r1());
			insert.append("," + ed.getVl_pneu_r2());
			insert.append("," + ed.getVl_pneu_r3());
			insert.append("," + ed.getVl_pneu_r4());
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			this.sql.executarUpdate(insert.toString());
			return ed;
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_InspecaoED ed)");
		}
	}

	public void altera(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Inspecoes SET");
			update.append("	 oid_Usuario = " + ed.getOid_Usuario()); 
			update.append("	,vl_pneu_novo = " + ed.getVl_pneu_novo());
			update.append("	,vl_pneu_r1 = " + ed.getVl_pneu_r1());
			update.append("	,vl_pneu_r2 = " + ed.getVl_pneu_r2());
			update.append("	,vl_pneu_r3 = " + ed.getVl_pneu_r3());
			update.append("	,vl_pneu_r4 = " + ed.getVl_pneu_r4());
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao());
			this.sql.executarUpdate(update.toString());
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_InspecaoED ed)");
		}
	}
	
	public void alteraRelatorio(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Inspecoes SET");
			update.append("	 tx_Inicial = " + getSQLString(ed.getTx_Inicial())); 
			update.append("	,tx_Final = " + getSQLString(ed.getTx_Final()));
			update.append("	,tx_Assinatura1 = " + getSQLString(ed.getTx_Assinatura1()));
			update.append("	,tx_Assinatura2 = " + getSQLString(ed.getTx_Assinatura2()));
			update.append("	,nm_Signatario = " + getSQLString(ed.getNm_Signatario()));
			update.append("	,nm_Tecnico = " + getSQLString(ed.getNm_Tecnico()));
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao());
			this.sql.executarUpdate(update.toString());
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"alteraRelatorio(Stif_InspecaoED ed)");
		}
	}
	
	public void encerrar(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Inspecoes SET");
			update.append("	 oid_Usuario = " + ed.getOid_Usuario()); 
			update.append("	,dt_Encerramento = " + getSQLString(ed.getDt_Encerramento()));
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao());
			this.sql.executarUpdate(update.toString());
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"encerrar(Stif_InspecaoED ed)");
		}
	}
	
	public void reabrir(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Inspecoes SET");
			update.append("	 oid_Usuario = " + ed.getOid_Usuario()); 
			update.append("	,dt_Encerramento = NULL");
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao());
			this.sql.executarUpdate(update.toString());
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"reabrir(Stif_InspecaoED ed)");
		}
	}
	
	public void deleta(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Inspecoes ");
			delete.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao()); 
			this.sql.executarUpdate(delete.toString());
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_InspecaoED ed)");
		}
	}

	public List<Stif_InspecaoED> lista(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT i.* ");
			query.append(" 	,replace(i.tx_Inicial,    chr(13), '&#13;' ) as tx_Inicial ");
			query.append(" 	,replace(i.tx_Final,      chr(13), '&#13;' ) as tx_Final ");
			query.append(" 	,replace(i.tx_Assinatura1,chr(13), '&#13;' ) as tx_Assinatura1 ");
			query.append(" 	,replace(i.tx_Assinatura2,chr(13), '&#13;' ) as tx_Assinatura2 ");
			query.append(" FROM Stif_Inspecoes i");
			query.append(" 		LEFT JOIN Empresas e");
			query.append("			ON (e.oid_Empresa = i.oid_Cliente)");
			query.append(" WHERE i.oid_Empresa = " + ed.getOid_Empresa());
			if (ed.getOid_Cliente() > 0) {
				query.append(" AND i.oid_Cliente = "+ed.getOid_Cliente());
			}
			if (!ed.isTodas()) {
				if (ed.isEncerrada()) {
					query.append(" AND i.dt_Encerramento IS NOT NULL");
				} else {
					query.append(" AND i.dt_Encerramento IS NULL");
				}
			}
			query.append(" ORDER BY i.dt_Inspecao");

			final List<Stif_InspecaoED> list = new ArrayList<Stif_InspecaoED>();
			final ResultSet rs = this.sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}

	/**
	 * Verifica se existe algum pneu configurado para a Inspeção informada
	 */
	public boolean verificaPneuConfigurado(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT count(1) as rowCount");
			query.append(" FROM Stif_Inspecoes i");
			query.append(" 		INNER JOIN Stif_Veiculos_Inspecoes vi");
			query.append("			ON (i.oid_Inspecao = vi.oid_Inspecao)");
			query.append(" 		INNER JOIN Stif_Pneus_Inspecoes pi");
			query.append("			ON (pi.oid_Veiculo_Inspecao = vi.oid_Veiculo_Inspecao)");
			query.append(" WHERE i.oid_Empresa = " + ed.getOid_Empresa());
			query.append("   AND i.oid_Inspecao = "+ed.getOid_Inspecao());

			final ResultSet res = this.sql.executarConsulta(query.toString());
			if (res.next()) {
				return res.getInt("rowCount") > 0;
			}
			return false;
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"verificaPneuConfigurado()");
		}
	}

	public Stif_InspecaoED getByRecord(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final List<Stif_InspecaoED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_InspecaoED ed)");
		}
		return new Stif_InspecaoED();
	}

	private Stif_InspecaoED populaRegistro(final ResultSet rs) throws SQLException {
		final Stif_InspecaoED ed = (Stif_InspecaoED) beanProcessor.toBean(rs, Stif_InspecaoED.class);
		ed.setEmpresaED((EmpresaED) beanProcessor.toBean(rs, EmpresaED.class));
		ed.formataMsgStamp();
		return ed;
	}
}