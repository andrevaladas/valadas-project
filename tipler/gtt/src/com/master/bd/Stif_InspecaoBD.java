package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_ConstatacaoED;
import com.master.ed.Stif_InspecaoED;
import com.master.ed.Stif_ParametroED;
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
			sql.executarUpdate(insert.toString());
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
			sql.executarUpdate(update.toString());
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
			sql.executarUpdate(update.toString());
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
			sql.executarUpdate(update.toString());
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
			sql.executarUpdate(update.toString());
		} catch (final Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"reabrir(Stif_InspecaoED ed)");
		}
	}
	
	public void deleta(final Stif_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Inspecoes ");
			delete.append(" WHERE oid_Inspecao = " + ed.getOid_Inspecao()); 
			sql.executarUpdate(delete.toString());
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

			final ResultSet res = sql.executarConsulta(query.toString());
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
		ed.formataMsgStamp();
		return ed;
	}

	public static void main(final String[] args) {
		new Stif_InspecaoBD().relatorio();
	}
	
	public Stif_InspecaoBD() {
		super();
	}

	private Integer countVeiculos;
	private Integer countVeiculosComAnomalias;
	private Integer countPneus;
	private Integer countPneusComAnomalias;

	private Integer pneusComProblemaDePressao;
	private Integer pneusSemProblemaDePressao;
	private Double wTotalPerc;
	private Double wTotalVlr;

	private Integer countTotalValvulas;
	private Integer countTotalRodas;
	private Integer countTotalOutros;
	private Integer countTotalPneus;

	private Double valorTotalValvulas;
	private Double valorTotalRodas;
	private Double valorTotalOutros;
	private Double valorTotalPneus;

	private final List<Stif_ConstatacaoED> constatacoes = new ArrayList<Stif_ConstatacaoED>();
	private enum DmTipo { 
		valvulas("2"), rodas("3"), outros("4"), pneus("5");

		private String codigo;
		private DmTipo(final String dmTipo) {
			codigo = dmTipo;
		}
		public String getCodigo() {
			return codigo;
		}
	}

	private void initalize() {
		countVeiculos = new Integer(0);
		countVeiculosComAnomalias = new Integer(0);
		countPneus = new Integer(0);
		countPneusComAnomalias = new Integer(0);

		pneusComProblemaDePressao = new Integer(0);
		pneusSemProblemaDePressao = new Integer(0);
		wTotalPerc = new Double(0);
		wTotalVlr = new Double(0);

		countTotalValvulas = new Integer(0);
		countTotalRodas = new Integer(0);
		countTotalOutros = new Integer(0);
		countTotalPneus = new Integer(0);
		
		valorTotalValvulas = new Double(0);
		valorTotalRodas = new Double(0);
		valorTotalOutros = new Double(0);
		valorTotalPneus = new Double(0);
		
		constatacoes.clear();
	}

	@SuppressWarnings("unused")
	public void relatorio() {
		try {
			inicioTransacao();
			initalize();

			//parametro da empresa
			final Stif_ParametroED parametro = findParametro(1);

			final Stif_InspecaoED inspecaoED = findInspecao(1);
			System.out.println(inspecaoED.toString());
			if (inspecaoED != null) {
				final List<Stif_Veiculo_InspecaoED> veiculosInspecaoList = findVeiculosInspecao(inspecaoED.getOid_Inspecao());
				for (final Stif_Veiculo_InspecaoED veiculoInspecaoED : veiculosInspecaoList) {
					/** add veiculo */
					countVeiculos++;

					//carrega as constatacoes por eixo do veiculo
					loadConstatacoesVeiculo(veiculoInspecaoED.getOid_Veiculo_Inspecao());

					final List<Stif_Pneu_InspecaoED> pneusInspecaoList = findPneusInspecao(veiculoInspecaoED.getOid_Veiculo_Inspecao());
					for (final Stif_Pneu_InspecaoED pneuInspecaoED : pneusInspecaoList) {
						/** add pneu */
						countPneus++;
						//carrega constatacao do respectivo eixo
						final Stif_ConstatacaoED constatacaoED = findConstatacaoByNrEixo(pneuInspecaoED.getNr_Eixo());
						final Stif_Perda_PercentualED perdaPercentualED = constatacaoED.getPerdaPercentualED();

						//calcula pressões dos pneus
						double vlPercentual = 0.0;
						final long nrPressao = pneuInspecaoED.getNr_Pressao();
						if (nrPressao >= 150) {
							vlPercentual = perdaPercentualED.getPc_perda_150();
						} else if (nrPressao >= 145) {
							vlPercentual = perdaPercentualED.getPc_perda_145();
						} else if (nrPressao >= 140) {
							vlPercentual = perdaPercentualED.getPc_perda_140();
						} else if (nrPressao >= 135) {
							vlPercentual = perdaPercentualED.getPc_perda_135();
						} else if (nrPressao >= 130) {
							vlPercentual = perdaPercentualED.getPc_perda_130();
						} else if (nrPressao >= 135) {
							vlPercentual = perdaPercentualED.getPc_perda_135();
						} else if (nrPressao >= 130) {
							vlPercentual = perdaPercentualED.getPc_perda_130();
						} else if (nrPressao >= 125) {
							vlPercentual = perdaPercentualED.getPc_perda_125();
						} else if (nrPressao >= 120) {
							vlPercentual = perdaPercentualED.getPc_perda_120();
						} else if (nrPressao >= 115) {
							vlPercentual = perdaPercentualED.getPc_perda_115();
						} else if (nrPressao >= 110) {
							vlPercentual = perdaPercentualED.getPc_perda_110();
						} else if (nrPressao >= 105) {
							vlPercentual = perdaPercentualED.getPc_perda_105();
						} else if (nrPressao >= 100) {
							vlPercentual = perdaPercentualED.getPc_perda_100();
						} else if (nrPressao >= 95) {
							vlPercentual = perdaPercentualED.getPc_perda_95();
						} else if (nrPressao >= 90) {
							vlPercentual = perdaPercentualED.getPc_perda_90();
						} else if (nrPressao >= 85) {
							vlPercentual = perdaPercentualED.getPc_perda_85();
						} else if (nrPressao >= 80) {
							vlPercentual = perdaPercentualED.getPc_perda_80();
						} else if (nrPressao >= 75) {
							vlPercentual = perdaPercentualED.getPc_perda_75();
						} else if (nrPressao >= 70) {
							vlPercentual = perdaPercentualED.getPc_perda_70();
						} else if (nrPressao >= 65) {
							vlPercentual = perdaPercentualED.getPc_perda_65();
						} else if (nrPressao >= 60) {
							vlPercentual = perdaPercentualED.getPc_perda_60();
						} else if (nrPressao >= 55) {
							vlPercentual = perdaPercentualED.getPc_perda_55();
						} else if (nrPressao >= 50) {
							vlPercentual = perdaPercentualED.getPc_perda_50();
						} else if (nrPressao >= 45) {
							vlPercentual = perdaPercentualED.getPc_perda_45();
						} else if (nrPressao >= 40) {
							vlPercentual = perdaPercentualED.getPc_perda_40();
						} else if (nrPressao >= 35) {
							vlPercentual = perdaPercentualED.getPc_perda_35();
						} else if (nrPressao >= 30) {
							vlPercentual = perdaPercentualED.getPc_perda_30();
						} else if (nrPressao >= 25) {
							vlPercentual = perdaPercentualED.getPc_perda_25();
						} else if (nrPressao >= 20) {
							vlPercentual = perdaPercentualED.getPc_perda_20();
						}

						// parametro vida pneu
						double vlParametro = 0.0; 
						final String dmVidaPneu = pneuInspecaoED.getDm_Vida_Pneu();
						if ("N".equals(dmVidaPneu)) {
							//TODO verificar se pega da INSPECAO OU DOS PARAMETROS
							vlParametro = inspecaoED.getVl_pneu_novo();
						} else if ("R1".equals(dmVidaPneu)) {
							vlParametro = inspecaoED.getVl_pneu_r1();
						} else if ("R2".equals(dmVidaPneu)) {
							vlParametro = inspecaoED.getVl_pneu_r2();
						} else if ("R3".equals(dmVidaPneu)) {
							vlParametro = inspecaoED.getVl_pneu_r3();
						} else if ("R4".equals(dmVidaPneu)) {
							vlParametro = inspecaoED.getVl_pneu_r4();
						}

						final double vlPerdaDoPneu = (vlParametro * vlPercentual / 100);
						if (vlPerdaDoPneu > 0) { 
							pneusComProblemaDePressao++;
						} else {
							pneusSemProblemaDePressao++;
						}
						//faz o somatorio geral
						wTotalVlr += vlPerdaDoPneu;
						wTotalPerc += vlPercentual;

						//Problemas Pneus por tipo
						//VALVULAS
						double valorDaPerdaDoProblema = 0.0;
						final List<Stif_Problema_PneuED> problemasValvulas = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.valvulas);
						for (final Stif_Problema_PneuED valvulaED : problemasValvulas) {
							valorDaPerdaDoProblema += valvulaED.getProblemaED().getPc_perda();
						}
						
						vlPercentual = getPercentualIncidenciaPeso(parametro, problemasValvulas.size());
						countTotalValvulas += problemasValvulas.size();
						valorTotalValvulas += valorDaPerdaDoProblema + (valorDaPerdaDoProblema * vlPercentual/100);

						//RODAS
						valorDaPerdaDoProblema = 0.0;
						final List<Stif_Problema_PneuED> problemasRodas = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.rodas);
						for (final Stif_Problema_PneuED rodaED : problemasRodas) {
							valorDaPerdaDoProblema += rodaED.getProblemaED().getPc_perda();
						}
						vlPercentual = getPercentualIncidenciaPeso(parametro, problemasRodas.size());
						countTotalRodas += problemasRodas.size();
						valorTotalRodas += valorDaPerdaDoProblema + (valorDaPerdaDoProblema * vlPercentual/100);

						//OUTROS
						valorDaPerdaDoProblema = 0.0;
						final List<Stif_Problema_PneuED> problemasOutros = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.outros);
						for (final Stif_Problema_PneuED outroED : problemasOutros) {
							valorDaPerdaDoProblema += outroED.getProblemaED().getPc_perda();
						}
						vlPercentual = getPercentualIncidenciaPeso(parametro, problemasOutros.size());
						countTotalOutros += problemasOutros.size();
						valorTotalOutros += valorDaPerdaDoProblema + (valorDaPerdaDoProblema * vlPercentual/100);

						//OUTROS
						valorDaPerdaDoProblema = 0.0;
						final List<Stif_Problema_PneuED> problemasPneus = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.pneus);
						for (final Stif_Problema_PneuED pneuED : problemasPneus) {
							valorDaPerdaDoProblema += pneuED.getProblemaED().getPc_perda();
						}
						vlPercentual = getPercentualIncidenciaPeso(parametro, problemasPneus.size());
						countTotalPneus += problemasPneus.size();
						valorTotalPneus += valorDaPerdaDoProblema + (valorDaPerdaDoProblema * vlPercentual/100);
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			fimTransacao(false);
			System.exit(1);
		}
	}
	private double getPercentualIncidenciaPeso(final Stif_ParametroED parametro, final int qtd_ocorrencia_problema) {
		double vlPercentual = 0.0;
		if (qtd_ocorrencia_problema >= 46) {
			vlPercentual = parametro.getPc_peso_46_50(); 
		} else if (qtd_ocorrencia_problema >= 41) {
			vlPercentual = parametro.getPc_peso_41_45();
		} else if (qtd_ocorrencia_problema >= 36) {
			vlPercentual = parametro.getPc_peso_36_40();
		} else if (qtd_ocorrencia_problema >= 31) {
			vlPercentual = parametro.getPc_peso_31_35();
		} else if (qtd_ocorrencia_problema >= 26) {
			vlPercentual = parametro.getPc_peso_26_30(); 
		} else if (qtd_ocorrencia_problema >= 21) {
			vlPercentual = parametro.getPc_peso_21_25(); 
		} else if (qtd_ocorrencia_problema >= 16) {
			vlPercentual = parametro.getPc_peso_16_20(); 
		} else if (qtd_ocorrencia_problema >= 11) {
			vlPercentual = parametro.getPc_peso_11_15(); 
		} else {
			vlPercentual = parametro.getPc_peso_0_5(); 
		}
		return vlPercentual;
	}
	
	/**
	 * Busca Inspeção
	 */
	private Stif_InspecaoED findInspecao(final long oidInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT i.* ");
		query.append(" FROM Stif_Inspecoes i");
		query.append(" WHERE i.oid_Inspecao = "+oidInspecao);

		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			if (rs.next()) {
				return (Stif_InspecaoED) beanProcessor.toBean(rs, Stif_InspecaoED.class);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca Parametros
	 */
	private Stif_ParametroED findParametro(final long oidEmpresa) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT p.* ");
		query.append(" FROM Stif_Parametros p");
		query.append(" WHERE p.oid_Empresa = "+oidEmpresa);

		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			if (rs.next()) {
				return (Stif_ParametroED) beanProcessor.toBean(rs, Stif_ParametroED.class);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca Veículos Inspecionados
	 */
	private List<Stif_Veiculo_InspecaoED> findVeiculosInspecao(final long oidInspecao) {
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
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Busca as Constatações do Veículo
	 */
	private void loadConstatacoesVeiculo(final long oidVeiculoInspecao) {
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
				constatacaoED.setPerdaPercentualED((Stif_Perda_PercentualED) beanProcessor.toBean(rs, Stif_Perda_PercentualED.class));
				System.out.println("		#"+constatacaoED.toString());
				System.out.println("		   >>>"+constatacaoED.getPerdaPercentualED().toString());
				constatacoes.add(constatacaoED);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	private Stif_ConstatacaoED findConstatacaoByNrEixo(final long nrEixo) {
		for (final Stif_ConstatacaoED constatacao : constatacoes) {
			if (constatacao.getNr_Eixo() == nrEixo) {
				return constatacao; 
			}
		}
		return null;
	}

	/**
	 * Busca Pneus Inspecionados
	 */
	private List<Stif_Pneu_InspecaoED> findPneusInspecao(final long oidVeiculoInspecao) {
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
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Busca Problemas Pneu
	 */
	private List<Stif_Problema_PneuED> findProblemasPneu(final long oidPneuInspecao, final DmTipo dmTipo) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT pp.*,p.*");
		query.append(" FROM Stif_Problemas_Pneus pp");
		query.append(" 	INNER JOIN Stif_Problemas p");
		query.append(" 		ON (pp.oid_Stif_Problema = p.oid_Stif_Problema)");
		query.append(" 	INNER JOIN Stif_Pneus_Inspecoes pi");
		query.append(" 		ON (pp.oid_Pneu_Inspecao = pi.oid_Pneu_Inspecao)");
		query.append(" WHERE pi.oid_Pneu_Inspecao = "+oidPneuInspecao);
		query.append(" AND p.dm_Tipo = "+dmTipo.getCodigo());

		final List<Stif_Problema_PneuED> result = new ArrayList<Stif_Problema_PneuED>();
		try {
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_Problema_PneuED problemaPneuED = (Stif_Problema_PneuED) beanProcessor.toBean(rs, Stif_Problema_PneuED.class);
				problemaPneuED.setProblemaED((Stif_ProblemaED) beanProcessor.toBean(rs, Stif_ProblemaED.class));
				result.add(problemaPneuED);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}