package com.master.rl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.master.ed.BandaED;
import com.master.ed.EmpresaED;
import com.master.ed.Modelo_PneuED;
import com.master.ed.Pneu_DimensaoED;
import com.master.ed.Stif_ConstatacaoED;
import com.master.ed.Stif_InspecaoED;
import com.master.ed.Stif_ParametroED;
import com.master.ed.Stif_Perda_PercentualED;
import com.master.ed.Stif_Pneu_InspecaoED;
import com.master.ed.Stif_ProblemaED;
import com.master.ed.Stif_Problema_PneuED;
import com.master.ed.Stif_Veiculo_InspecaoED;
import com.master.ed.VeiculoED;
import com.master.ed.relatorio.Item;
import com.master.ed.relatorio.Pneu;
import com.master.ed.relatorio.Resumo;
import com.master.ed.relatorio.Veiculo;
import com.master.util.BancoUtil;
import com.master.util.JavaUtil;

/**
 * @author André Valadas
 * @serial STIF- Relatório de Inspeção
 * @since 07/2012
 */
public class Stif_InspecaoRL extends BancoUtil {

	public Stif_InspecaoRL() {
		super();
	}

	private Stif_InspecaoED inspecaoED;
	private final List<Veiculo> veiculos = new ArrayList<Veiculo>();
	private Integer countVeiculosComAnomalias;
	private Integer countPneusComAnomalias;
	private Boolean hasAnomalia = Boolean.FALSE;

	private Integer pneusComProblemaDePressao;
	private Integer pneusSemProblemaDePressao;
	private Double wTotalVlr;

	private final List<Item> pressoes = new ArrayList<Item>();
	private final List<Item> valvulas = new ArrayList<Item>();
	private final List<Item> rodas = new ArrayList<Item>();
	private final List<Item> outros = new ArrayList<Item>();
	private final List<Item> pneus = new ArrayList<Item>();

	private final List<Resumo> resumos = new ArrayList<Resumo>();

	private static final String PRESSAO_OK = "PNEUS COM PRESSÃO CORRETA";
	private static final String PRESSAO_NOK = "PNEUS COM BAIXA OU ALTA PRESSÃO";
	private static final String PRESSAO_TOTAL_KM = "PERDA TOTAL DE KM P/ BAIXA E ALTA PRESSAO %";
	private static final String PRESSAO_TOTAL_PNEUS = "PERDA TOTAL EM NUMERO DE PNEUS";

	private final List<Stif_ConstatacaoED> constatacoes = new ArrayList<Stif_ConstatacaoED>();
	private enum DmTipo { 
		valvulas("2"), rodas("3"), outros("4"), pneus("5");
		private final String codigo;
		private DmTipo(final String dmTipo) {
			this.codigo = dmTipo;
		}
		public String getCodigo() {
			return this.codigo;
		}
	}
	final Comparator<Item> itemNameComparator = new Comparator<Item>() {
		@Override
		public int compare(final Item arg0, final Item arg1) {
			return arg0.getNome().compareTo(arg1.getNome());
		}
	};

	private void initalize() {
		this.veiculos.clear();

		this.countVeiculosComAnomalias = new Integer(0);
		this.countPneusComAnomalias = new Integer(0);

		this.pneusComProblemaDePressao = new Integer(0);
		this.pneusSemProblemaDePressao = new Integer(0);
		this.wTotalVlr = new Double(0);

		this.pressoes.clear();
		this.pressoes.add(new Item(PRESSAO_OK));
		this.pressoes.add(new Item(PRESSAO_NOK));
		this.pressoes.add(new Item(PRESSAO_TOTAL_KM));
		this.pressoes.add(new Item(PRESSAO_TOTAL_PNEUS));

		this.valvulas.clear();
		this.rodas.clear();
		this.outros.clear();
		this.pneus.clear();

		this.resumos.clear();

		this.constatacoes.clear();
	}

	private void addItem(final List<Item> items, final String nome, final Double valor) {
		for (final Item item : items) {
			if (item.getNome().equals(nome)) {
				item.addQuantidade();
				item.addValor(valor);
				return;
			}
		}
		final Item item = new Item(nome);
		item.addQuantidade();
		item.addValor(valor);
		items.add(item);
	}
	private void addResumo(final String nrFrota, final long nrFogo, final String problemaPneu) {
		final String problemaPneuFormatted = problemaPneu.substring(0,1).toUpperCase() + problemaPneu.substring(1).toLowerCase();
		for (final Resumo resumo : this.resumos) {
			if (resumo.getNrFrota().equals(nrFrota)) {
				resumo.addRelacaoProblemasPneus(nrFogo, problemaPneuFormatted);
				return;
			}
		}
		final Resumo resumo = new Resumo(nrFrota);
		resumo.addRelacaoProblemasPneus(nrFogo, problemaPneuFormatted);
		this.resumos.add(resumo);
	}

	public List<Veiculo> relatorio(final long oidInspecao) {
		try {
			inicioTransacao();
			initalize();
			loadAllProblemas();

			this.inspecaoED = findInspecao(oidInspecao);

			if (this.inspecaoED != null) {
				final List<Stif_Veiculo_InspecaoED> veiculosInspecaoList = findVeiculosInspecao(this.inspecaoED.getOid_Inspecao());
				for (final Stif_Veiculo_InspecaoED veiculoInspecaoED : veiculosInspecaoList) {
					/** add veiculo */
					final VeiculoED veiculoED = veiculoInspecaoED.getVeiculoED();
					final Veiculo veiculo = new Veiculo();
					veiculo.setDm_Tipo_Chassis(veiculoED.getDm_Tipo_Chassis());

					veiculo.setNr_Frota(veiculoED.getNr_Frota());
					veiculo.setNr_Odometro(veiculoInspecaoED.getNr_Odometro());
					veiculo.setNr_Placa(veiculoED.getNr_Placa());
					veiculo.setDm_Tipo_Veiculo(veiculoED.getDm_Tipo_Veiculo());
					veiculo.setNm_Modelo_Veiculo(veiculoED.getNm_Modelo_Veiculo());

					//carrega as constatacoes por eixo do veículo
					loadConstatacoesVeiculo(veiculo, veiculoInspecaoED.getOid_Veiculo_Inspecao());
					this.veiculos.add(veiculo);

					final List<Stif_Pneu_InspecaoED> pneusInspecaoList = findPneusInspecao(veiculoInspecaoED.getOid_Veiculo_Inspecao());
					for (final Stif_Pneu_InspecaoED pneuInspecaoED : pneusInspecaoList) {
						/** pneu */
						final Pneu pneu = new Pneu();
						pneu.setNr_Fogo(pneuInspecaoED.getNr_Fogo());
						pneu.setDm_Vida_Pneu(pneuInspecaoED.getDm_Vida_Pneu_Descricao());
						pneu.setNr_Pressao(pneuInspecaoED.getNr_Pressao());
						pneu.setNr_Mm_Sulco(pneuInspecaoED.getNr_Mm_Sulco());
						pneu.setNm_Pneu_Dimensao(pneuInspecaoED.getPneuDimensaoED().getNm_Pneu_Dimensao());
						pneu.setNm_Modelo_Pneu(pneuInspecaoED.getModeloPneuED().getNM_Modelo_Pneu());
						pneu.setNm_Banda(pneuInspecaoED.getBandaED().getNm_Banda());
						/** add pneu via reflection */
						addPneu(pneuInspecaoED.getDm_Posicao(), veiculo, pneu);

						//carrega constatacao do respectivo eixo
						final Stif_ConstatacaoED constatacaoED = findConstatacaoByNrEixo(pneuInspecaoED.getNr_Eixo());
						final Stif_Perda_PercentualED perdaPercentualED = constatacaoED.getPerdaPercentualED();

						//calcula pressões dos pneus
						final double vlPercentual = getPercentualPerdaPressao(pneuInspecaoED.getNr_Pressao(), perdaPercentualED);

						// parametro vida pneu
						double vlParametroVidaPneu = 0.0; 
						final String dmVidaPneu = pneuInspecaoED.getDm_Vida_Pneu();
						if ("N".equals(dmVidaPneu)) {
							//TODO verificar se pega da INSPECAO OU DOS PARAMETROS
							vlParametroVidaPneu = this.inspecaoED.getVl_pneu_novo();
						} else if ("R1".equals(dmVidaPneu)) {
							vlParametroVidaPneu = this.inspecaoED.getVl_pneu_r1();
						} else if ("R2".equals(dmVidaPneu)) {
							vlParametroVidaPneu = this.inspecaoED.getVl_pneu_r2();
						} else if ("R3".equals(dmVidaPneu)) {
							vlParametroVidaPneu = this.inspecaoED.getVl_pneu_r3();
						} else if ("R4".equals(dmVidaPneu)) {
							vlParametroVidaPneu = this.inspecaoED.getVl_pneu_r4();
						}

						final double vlPerdaDoPneu = (vlParametroVidaPneu * vlPercentual / 100);
						if (vlPerdaDoPneu > 0) { 
							addItem(this.pressoes, PRESSAO_NOK, 0D);
							addResumo(veiculo.getNr_Frota(),pneu.getNr_Fogo(), "Pressão baixa");
							this.pneusComProblemaDePressao++;
						} else {
							addItem(this.pressoes, PRESSAO_OK, 0D);
							this.pneusSemProblemaDePressao++;
						}
						//faz o somatorio geral
						addItem(this.pressoes, PRESSAO_TOTAL_KM, vlPercentual);
						addItem(this.pressoes, PRESSAO_TOTAL_PNEUS, vlPerdaDoPneu / this.inspecaoED.getVl_pneu_novo());

						this.wTotalVlr += vlPerdaDoPneu;

						//Problemas Pneus por tipo
						//VALVULAS
						final List<Stif_Problema_PneuED> problemasValvulas = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.valvulas);
						for (final Stif_Problema_PneuED problemaPneuED : problemasValvulas) {
							final Stif_ProblemaED problemaED = problemaPneuED.getProblemaED();
							addItem(this.valvulas, problemaED.getNm_problema(), (vlParametroVidaPneu * problemaED.getPc_perda()/100));
							addResumo(veiculo.getNr_Frota(),pneu.getNr_Fogo(), problemaED.getNm_problema());
							pneu.addNr_Problemas_Valvulas(problemaED.getCd_problema());
							verificaAnomaliaPneu(problemaED.getDm_problema());
						}
						//RODAS
						final List<Stif_Problema_PneuED> problemasRodas = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.rodas);
						for (final Stif_Problema_PneuED problemaPneuED : problemasRodas) {
							final Stif_ProblemaED problemaED = problemaPneuED.getProblemaED();
							addItem(this.rodas, problemaED.getNm_problema(), (vlParametroVidaPneu * problemaED.getPc_perda()/100));
							addResumo(veiculo.getNr_Frota(),pneu.getNr_Fogo(), problemaED.getNm_problema());
							pneu.addNr_Problemas_Rodas(problemaED.getCd_problema());
							verificaAnomaliaPneu(problemaED.getDm_problema());
						}

						//OUTROS
						final List<Stif_Problema_PneuED> problemasOutros = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.outros);
						for (final Stif_Problema_PneuED problemaPneuED : problemasOutros) {
							final Stif_ProblemaED problemaED = problemaPneuED.getProblemaED();
							addItem(this.outros, problemaED.getNm_problema(), (vlParametroVidaPneu * problemaED.getPc_perda()/100));
							addResumo(veiculo.getNr_Frota(),pneu.getNr_Fogo(), problemaED.getNm_problema());
							pneu.addNr_Problemas_Outros(problemaED.getCd_problema());
							verificaAnomaliaPneu(problemaED.getDm_problema());
						}

						//PNEUS
						final List<Stif_Problema_PneuED> problemasPneus = findProblemasPneu(pneuInspecaoED.getOid_Pneu_Inspecao(), DmTipo.pneus);
						for (final Stif_Problema_PneuED problemaPneuED : problemasPneus) {
							final Stif_ProblemaED problemaED = problemaPneuED.getProblemaED();
							addItem(this.pneus, problemaED.getNm_problema(), (vlParametroVidaPneu * problemaED.getPc_perda()/100));
							addResumo(veiculo.getNr_Frota(),pneu.getNr_Fogo(), problemaED.getNm_problema());
							pneu.addNr_Problemas_Pneus(problemaED.getCd_problema());
							verificaAnomaliaPneu(problemaED.getDm_problema());
						}
					}

					//Verifica se tiveram anomalias nos pneus listados
					if (Boolean.TRUE.equals(this.hasAnomalia)) {
						this.countVeiculosComAnomalias++;
						this.hasAnomalia = Boolean.FALSE;
					}
				}

				//Recalcula valores totais dos problemas aplicando incidência de peso
				final Stif_ParametroED parametro = findParametro(this.inspecaoED.getOid_Empresa());
				recalculaIncidenciaPeso(this.valvulas, parametro);
				recalculaIncidenciaPeso(this.rodas, parametro);
				recalculaIncidenciaPeso(this.outros, parametro);
				recalculaIncidenciaPeso(this.pneus, parametro);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			fimTransacao(false);
		}
		return this.veiculos;
	}

	private void addPneu(final String dmPosicao, final Veiculo veiculo, final Pneu pneu) {
		try {
			final Method meth = veiculo.getClass().getMethod("set" + dmPosicao.substring(0,1).toUpperCase() + dmPosicao.substring(1).toLowerCase(),Pneu.class);
			meth.invoke(veiculo, pneu);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	private double getPercentualPerdaPressao(final long nrPressao, final Stif_Perda_PercentualED perdaPercentualED) {
		double vlPercentual = 0.0;
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
		return vlPercentual;
	}
	
	private void recalculaIncidenciaPeso(final List<Item> items, final Stif_ParametroED parametro) {
		for (final Item item : items) {
			final double vlPercentual = getPercentualIncidenciaPeso(parametro, item.getQuantidade());
			final double valorTotalValvulas = item.getValor() + (item.getValor() * vlPercentual/100);
			item.setValor(valorTotalValvulas);
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
	
	private void verificaAnomaliaPneu(final String dmProblema) {
		if ("G".equals(dmProblema)) {
			this.countPneusComAnomalias++;
			this.hasAnomalia = Boolean.TRUE;
		}
	}
	
	private void loadAllProblemas() {
		final List<Stif_ProblemaED> valvulasList = findProblemas(DmTipo.valvulas);
		for (final Stif_ProblemaED problemaED : valvulasList) {
			this.valvulas.add(new Item(problemaED.getNm_problema()));
		}
		final List<Stif_ProblemaED> rodasList = findProblemas(DmTipo.rodas);
		for (final Stif_ProblemaED problemaED : rodasList) {
			this.rodas.add(new Item(problemaED.getNm_problema()));
		}
		final List<Stif_ProblemaED> outrosList = findProblemas(DmTipo.outros);
		for (final Stif_ProblemaED problemaED : outrosList) {
			this.outros.add(new Item(problemaED.getNm_problema()));
		}
		final List<Stif_ProblemaED> pneusList = findProblemas(DmTipo.pneus);
		for (final Stif_ProblemaED problemaED : pneusList) {
			this.pneus.add(new Item(problemaED.getNm_problema()));
		}
	}
	public Stif_InspecaoED getInspecaoED() {
		return this.inspecaoED;
	}
	private List<Item> getFormattedList(final List<Item> items) {
		for (final Item item : items) {
			final String nome = item.getNome();
			item.setNome(nome.substring(0, 1).toUpperCase()+nome.substring(1).toLowerCase());
		}
		return items;
	}
	public List<Item> getPressoes() {
		return getFormattedList(this.pressoes);
	}
	public List<Item> getValvulas() {
		Collections.sort(this.valvulas, this.itemNameComparator);
		return getFormattedList(this.valvulas);
	}
	public List<Item> getRodas() {
		Collections.sort(this.rodas, this.itemNameComparator);
		return getFormattedList(this.rodas);
	}
	public List<Item> getOutros() {
		Collections.sort(this.outros, this.itemNameComparator);
		return getFormattedList(this.outros);
	}
	public List<Item> getPneus() {
		Collections.sort(this.pneus, this.itemNameComparator);
		return getFormattedList(this.pneus);
	}
	public List<Resumo> getResumos() {
		Collections.sort(this.resumos, new Comparator<Resumo>() {
			@Override
			public int compare(final Resumo arg0, final Resumo arg1) {
				return arg0.getNrFrota().compareTo(arg1.getNrFrota());
			}
		});
		//TODO máximo 4 veículos
		return this.resumos.subList(0, Math.min(this.resumos.size(), 4));
	}
	public List<Item> getTotalPerdas() {
		final List<Item> totalPerdas = new ArrayList<Item>();
		addItem(totalPerdas, "ITEM I - PRESSÕES", getValorTotalPressao());
		addItem(totalPerdas, "ITEM II - VÁLVULAS", getValorTotal(this.valvulas));
		addItem(totalPerdas, "ITEM III - RODAS E GERMINAÇÕES", getValorTotal(this.rodas));
		addItem(totalPerdas, "ITEM IV - OUTROS", getValorTotal(this.outros));
		addItem(totalPerdas, "ITEM V - PNEUS EM USO", getValorTotal(this.pneus));
		return totalPerdas;
	}
	private Double getValorTotal(final List<Item> items){
		Double valorTotal = 0D;
		for (final Item item : items) {
			valorTotal += item.getValor();
		}
		return valorTotal;
	}
	public Double getValorTotalPressao() {
		return this.wTotalVlr;
	}
	public Integer getCountVeiculosComAnomalias() {
		return this.countVeiculosComAnomalias;
	}
	public Integer getCountPneusComAnomalias() {
		return this.countPneusComAnomalias;
	}
	public Integer getCountVeiculos() {
		return this.veiculos.size();
	}
	public Integer getCountPneus() {
		return this.pneusComProblemaDePressao + this.pneusSemProblemaDePressao;
	}

	/**
	 * Busca Inspeção
	 */
	public Stif_InspecaoED findInspecao(final long oidInspecao) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT * ");
		query.append(" FROM Stif_Inspecoes i");
		query.append(" 	LEFT JOIN Empresas e");
		query.append(" 		ON (e.oid_Empresa = i.oid_Empresa)");//TODO i.oid_Cliente
		query.append(" WHERE i.oid_Inspecao = "+oidInspecao);

		try {
			final ResultSet rs = this.sql.executarConsulta(query.toString());
			if (rs.next()) {
				final Stif_InspecaoED inspecaoED = (Stif_InspecaoED) beanProcessor.toBean(rs, Stif_InspecaoED.class);
				inspecaoED.setEmpresaED((EmpresaED) beanProcessor.toBean(rs, EmpresaED.class));
				return inspecaoED;
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
			final ResultSet rs = this.sql.executarConsulta(query.toString());
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
			final ResultSet rs = this.sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_Veiculo_InspecaoED veiculoInspecaoED = (Stif_Veiculo_InspecaoED) beanProcessor.toBean(rs, Stif_Veiculo_InspecaoED.class);
				veiculoInspecaoED.setVeiculoED((VeiculoED) beanProcessor.toBean(rs, VeiculoED.class));
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
	private void loadConstatacoesVeiculo(final Veiculo veiculo, final long oidVeiculoInspecao) {
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

		try {
			final ResultSet rs = this.sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_ConstatacaoED constatacaoED = (Stif_ConstatacaoED) beanProcessor.toBean(rs, Stif_ConstatacaoED.class);
				constatacaoED.setPerdaPercentualED((Stif_Perda_PercentualED) beanProcessor.toBean(rs, Stif_Perda_PercentualED.class));

				if (constatacaoED.getNr_Eixo() >= 4) {
					veiculo.setNr_Pressao_Eixo_4((long)constatacaoED.getPerdaPercentualED().getPc_perda_padrao());
				} else if (constatacaoED.getNr_Eixo() >= 3) {
					veiculo.setNr_Pressao_Eixo_3((long)constatacaoED.getPerdaPercentualED().getPc_perda_padrao());
				} else if (constatacaoED.getNr_Eixo() >= 2) {
					veiculo.setNr_Pressao_Eixo_2((long)constatacaoED.getPerdaPercentualED().getPc_perda_padrao());
				} else {
					veiculo.setNr_Pressao_Eixo_1((long)constatacaoED.getPerdaPercentualED().getPc_perda_padrao());
				}
				this.constatacoes.add(constatacaoED);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	private Stif_ConstatacaoED findConstatacaoByNrEixo(final long nrEixo) {
		for (final Stif_ConstatacaoED constatacao : this.constatacoes) {
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
		query.append(" SELECT *");
		query.append(" FROM Stif_Pneus_Inspecoes pi");
		query.append(" 	INNER JOIN Stif_Veiculos_Inspecoes vi");
		query.append(" 		ON (pi.oid_Veiculo_Inspecao = vi.oid_Veiculo_Inspecao)");
		query.append(" 	INNER JOIN Pneus_Dimensoes d");
		query.append(" 		ON (pi.oid_Pneu_Dimensao = d.oid_Pneu_Dimensao)");
		query.append(" 	INNER JOIN Modelos_Pneus m");
		query.append(" 		ON (pi.oid_Modelo_Pneu = m.oid_Modelo_Pneu)");
		query.append(" 	INNER JOIN Bandas b");
		query.append(" 		ON (pi.oid_Banda = b.oid_Banda)");
		query.append(" WHERE pi.oid_Veiculo_Inspecao = "+oidVeiculoInspecao);

		final List<Stif_Pneu_InspecaoED> result = new ArrayList<Stif_Pneu_InspecaoED>();
		try {
			final ResultSet rs = this.sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_Pneu_InspecaoED pneuInspecaoED = (Stif_Pneu_InspecaoED) beanProcessor.toBean(rs, Stif_Pneu_InspecaoED.class);
				pneuInspecaoED.setPneuDimensaoED((Pneu_DimensaoED) beanProcessor.toBean(rs, Pneu_DimensaoED.class));
				pneuInspecaoED.setModeloPneuED((Modelo_PneuED) beanProcessor.toBean(rs, Modelo_PneuED.class));
				pneuInspecaoED.setBandaED((BandaED) beanProcessor.toBean(rs, BandaED.class));
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
	private List<Stif_ProblemaED> findProblemas(final DmTipo dmTipo) {
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT p.*");
		query.append(" FROM Stif_Problemas p");
		query.append(" WHERE p.dm_Tipo = "+JavaUtil.getSQLString(dmTipo.getCodigo()));
		query.append(" ORDER BY p.cd_problema");

		final List<Stif_ProblemaED> result = new ArrayList<Stif_ProblemaED>();
		try {
			final ResultSet rs = this.sql.executarConsulta(query.toString());
			while (rs.next()) {
				final Stif_ProblemaED problemaED = (Stif_ProblemaED) beanProcessor.toBean(rs, Stif_ProblemaED.class);
				result.add(problemaED);
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
		query.append("   AND p.dm_Tipo = "+JavaUtil.getSQLString(dmTipo.getCodigo()));
		query.append(" ORDER BY p.cd_problema");

		final List<Stif_Problema_PneuED> result = new ArrayList<Stif_Problema_PneuED>();
		try {
			final ResultSet rs = this.sql.executarConsulta(query.toString());
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