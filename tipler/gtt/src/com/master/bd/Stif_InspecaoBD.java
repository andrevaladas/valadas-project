package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_ConstatacaoED;
import com.master.ed.Stif_InspecaoED;
import com.master.ed.Stif_Perda_PercentualED;
import com.master.ed.Stif_Pneu_InspecaoED;
import com.master.ed.Stif_ProblemaED;
import com.master.ed.Stif_Problema_PneuED;
import com.master.ed.Stif_Veiculo_InspecaoED;
import com.master.ed.VeiculoED;
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

	public Stif_InspecaoBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_InspecaoED inclui(Stif_InspecaoED ed) throws Excecoes {
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
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_InspecaoED ed)");
		}
	}

	public void altera(Stif_InspecaoED ed) throws Excecoes {
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
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_InspecaoED ed)");
		}
	}

	public void encerrar(Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Inspecoes SET");
			update.append("	 oid_Usuario = " + ed.getOid_Usuario()); 
			update.append("	,dt_Encerramento = " + getSQLString(ed.getDt_Encerramento()));
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"encerrar(Stif_InspecaoED ed)");
		}
	}
	
	public void reabrir(Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Inspecoes SET");
			update.append("	 oid_Usuario = " + ed.getOid_Usuario()); 
			update.append("	,dt_Encerramento = NULL");
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"reabrir(Stif_InspecaoED ed)");
		}
	}
	
	public void deleta(Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Inspecoes ");
			delete.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao()); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_InspecaoED ed)");
		}
	}

	public List<Stif_InspecaoED> lista(Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT i.* FROM Stif_Inspecoes i");
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
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}

	/**
	 * Verifica se existe algum pneu configurado para a Inspeção informada
	 */
	public boolean verificaPneuConfigurado(Stif_InspecaoED ed) throws Excecoes {
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

			final ResultSet res = sql.executarConsulta(query.toString());
			if (res.next()) {
				return res.getInt("rowCount") > 0;
			}
			return false;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"verificaPneuConfigurado()");
		}
	}

	public Stif_InspecaoED getByRecord(Stif_InspecaoED ed) throws Excecoes {
		try {
			final List<Stif_InspecaoED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_InspecaoED ed)");
		}
		return new Stif_InspecaoED();
	}

	private Stif_InspecaoED populaRegistro(ResultSet rs) throws SQLException {
		Stif_InspecaoED ed = (Stif_InspecaoED) beanProcessor.toBean(rs, Stif_InspecaoED.class);
		ed.formataMsgStamp();
		return ed;
	}

	public static void main(String[] args) {
		new Stif_InspecaoBD().relatorio();
	}
	
	public Stif_InspecaoBD() {
		super();
	}

	@SuppressWarnings("unused")
	public void relatorio() {
		try {
			this.inicioTransacao();
			
			final Stif_InspecaoED inspecaoED = findInspecao(1);
			System.out.println(inspecaoED.toString());
			if (inspecaoED != null) {
				final List<Stif_Veiculo_InspecaoED> veiculosInspecaoList = findVeiculosInspecao(inspecaoED.getOid_Inspecao());
				for (final Stif_Veiculo_InspecaoED veiculoInspecaoED : veiculosInspecaoList) {
					final List<Stif_ConstatacaoED> constatacoesVeiculoList = findConstatacoesVeiculo(veiculoInspecaoED.getOid_Veiculo_Inspecao());
					final List<Stif_Pneu_InspecaoED> pneusInspecaoList = findPneusInspecao(veiculoInspecaoED.getOid_Veiculo_Inspecao());
					for (final Stif_Pneu_InspecaoED pneuInspecaoED : pneusInspecaoList) {
						final List<Stif_Problema_PneuED> problemasPneuList = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.fimTransacao(false);
			System.exit(1);
		}
	}

	/**
	 * Busca Inspeção
	 */
	private Stif_InspecaoED findInspecao(long oidInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT i.* ");
		query.append(" FROM Stif_Inspecoes i");
		query.append(" WHERE i.oid_Inspecao = "+oidInspecao);

		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			if (rs.next()) {
				return (Stif_InspecaoED) beanProcessor.toBean(rs, Stif_InspecaoED.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca Veículos Inspecionados
	 */
	private List<Stif_Veiculo_InspecaoED> findVeiculosInspecao(long oidInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT *");
		query.append(" FROM Stif_Veiculos_Inspecoes vi");
		query.append(" 	INNER JOIN Stif_Inspecoes i");
		query.append(" 		ON (i.oid_Inspecao = vi.oid_Inspecao)");
		query.append(" 	INNER JOIN Veiculos v");
		query.append(" 		ON (v.oid_Veiculo = vi.oid_Veiculo)");
		query.append(" WHERE i.oid_Inspecao = "+oidInspecao);

		final List<Stif_Veiculo_InspecaoED> result = new ArrayList<Stif_Veiculo_InspecaoED>();
		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_Veiculo_InspecaoED veiculoInspecaoED = (Stif_Veiculo_InspecaoED) beanProcessor.toBean(rs, Stif_Veiculo_InspecaoED.class);
				final VeiculoED veiculo = (VeiculoED) beanProcessor.toBean(rs, VeiculoED.class);
				System.out.println("	#"+veiculoInspecaoED.toString());
				System.out.println("	   >>>"+veiculo.toString());
				result.add(veiculoInspecaoED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Busca as Constatações do Veículo
	 */
	private List<Stif_ConstatacaoED> findConstatacoesVeiculo(long oidVeiculoInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT DISTINCT c.*, pp.* ");
		query.append(" FROM Stif_Constatacoes c");
		query.append(" 	INNER JOIN Stif_Perdas_Percentuais pp");
		query.append(" 		ON (c.oid_Perda_Percentual = pp.oid_Perda_Percentual)");
		query.append(" 	INNER JOIN Stif_Pneus_Inspecoes pi");
		query.append(" 		ON (c.oid_Veiculo_Inspecao = pi.oid_Veiculo_Inspecao)");
		query.append(" 	INNER JOIN Stif_Veiculos_Inspecoes vi");
		query.append(" 		ON (pi.oid_Veiculo_Inspecao = vi.oid_Veiculo_Inspecao)");
		query.append(" 	INNER JOIN Stif_Inspecoes i");
		query.append(" 		ON (i.oid_Inspecao = vi.oid_Inspecao)");
		query.append(" WHERE c.oid_Veiculo_Inspecao = "+oidVeiculoInspecao);

		final List<Stif_ConstatacaoED> result = new ArrayList<Stif_ConstatacaoED>();
		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_ConstatacaoED constatacaoED = (Stif_ConstatacaoED) beanProcessor.toBean(rs, Stif_ConstatacaoED.class);
				final Stif_Perda_PercentualED perdaPercentualED = (Stif_Perda_PercentualED) beanProcessor.toBean(rs, Stif_Perda_PercentualED.class);
				System.out.println("		#"+constatacaoED.toString());
				System.out.println("		   >>>"+perdaPercentualED.toString());
				result.add(constatacaoED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Busca Pneus Inspecionados
	 */
	private List<Stif_Pneu_InspecaoED> findPneusInspecao(long oidVeiculoInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT pi.*");
		query.append(" FROM Stif_Pneus_Inspecoes pi");
		query.append(" 	INNER JOIN Stif_Veiculos_Inspecoes vi");
		query.append(" 		ON (pi.oid_Veiculo_Inspecao = vi.oid_Veiculo_Inspecao)");
		query.append(" 	INNER JOIN Stif_Inspecoes i");
		query.append(" 		ON (i.oid_Inspecao = vi.oid_Inspecao)");
		query.append(" WHERE pi.oid_Veiculo_Inspecao = "+oidVeiculoInspecao);

		final List<Stif_Pneu_InspecaoED> result = new ArrayList<Stif_Pneu_InspecaoED>();
		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_Pneu_InspecaoED pneuInspecaoED = (Stif_Pneu_InspecaoED) beanProcessor.toBean(rs, Stif_Pneu_InspecaoED.class);
				System.out.println("		#"+pneuInspecaoED.toString());
				result.add(pneuInspecaoED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Busca Problemas Pneu
	 */
	private List<Stif_Problema_PneuED> findProblemasPneu(long oidPneuInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT pp.*,p.*");
		query.append(" FROM Stif_Problemas_Pneus pp");
		query.append(" 	INNER JOIN Stif_Problemas p");
		query.append(" 		ON (pp.oid_Stif_Problema = p.oid_Stif_Problema)");
		query.append(" 	INNER JOIN Stif_Pneus_Inspecoes pi");
		query.append(" 		ON (pp.oid_Pneu_Inspecao = pi.oid_Pneu_Inspecao)");
		query.append(" WHERE pi.oid_Pneu_Inspecao = "+oidPneuInspecao);

		final List<Stif_Problema_PneuED> result = new ArrayList<Stif_Problema_PneuED>();
		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_Problema_PneuED problemaPneuED = (Stif_Problema_PneuED) beanProcessor.toBean(rs, Stif_Problema_PneuED.class);
				final Stif_ProblemaED problemaED = (Stif_ProblemaED) beanProcessor.toBean(rs, Stif_ProblemaED.class);
				System.out.println("			#"+problemaPneuED.toString());
				System.out.println("			   >>>"+problemaED.toString());
				result.add(problemaPneuED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}