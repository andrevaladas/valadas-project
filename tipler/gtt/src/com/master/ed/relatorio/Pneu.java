package com.master.ed.relatorio;

import java.io.Serializable;

/**
 * @author Andre Valadas
 */
public class Pneu implements Serializable {

	private static final long serialVersionUID = -8134615231434131159L;
	
	private long nr_Fogo;
	private String dm_Vida_Pneu;
	private long nr_Pressao;
	private double nr_Mm_Sulco;
	private String nm_Pneu_Dimensao;
	private String nm_Modelo_Pneu;
	private String nm_Banda;
	private String nr_Problemas_Rodas;
	private String nr_Problemas_Valvulas;
	private String nr_Problemas_Pneus;
	private String nr_Problemas_Outros;

	public long getNr_Fogo() {
		return this.nr_Fogo;
	}
	public void setNr_Fogo(final long nrFogo) {
		this.nr_Fogo = nrFogo;
	}
	public String getDm_Vida_Pneu() {
		return this.dm_Vida_Pneu;
	}
	public void setDm_Vida_Pneu(final String dmVidaPneu) {
		this.dm_Vida_Pneu = dmVidaPneu;
	}
	public long getNr_Pressao() {
		return this.nr_Pressao;
	}
	public void setNr_Pressao(final long nrPressao) {
		this.nr_Pressao = nrPressao;
	}
	public double getNr_Mm_Sulco() {
		return this.nr_Mm_Sulco;
	}
	public void setNr_Mm_Sulco(final double nrMmSulco) {
		this.nr_Mm_Sulco = nrMmSulco;
	}
	public String getNm_Pneu_Dimensao() {
		return this.nm_Pneu_Dimensao;
	}
	public void setNm_Pneu_Dimensao(final String nmPneuDimensao) {
		this.nm_Pneu_Dimensao = nmPneuDimensao;
	}
	public String getNm_Modelo_Pneu() {
		return this.nm_Modelo_Pneu;
	}
	public void setNm_Modelo_Pneu(final String nmModeloPneu) {
		this.nm_Modelo_Pneu = nmModeloPneu;
	}
	public String getNm_Banda() {
		return this.nm_Banda;
	}
	public void setNm_Banda(final String nmBanda) {
		this.nm_Banda = nmBanda;
	}
	public String getNr_Problemas_Rodas() {
		return this.nr_Problemas_Rodas;
	}
	public void addNr_Problemas_Rodas(final String nrProblemasRodas) {
		if (this.nr_Problemas_Rodas == null) {
			this.nr_Problemas_Rodas = nrProblemasRodas;
		} else {
			this.nr_Problemas_Rodas += ","+nrProblemasRodas;
		}
	}
	public String getNr_Problemas_Valvulas() {
		return this.nr_Problemas_Valvulas;
	}
	public void addNr_Problemas_Valvulas(final String nrProblemasValvulas) {
		if (this.nr_Problemas_Valvulas == null) {
			this.nr_Problemas_Valvulas = nrProblemasValvulas;
		} else {
			this.nr_Problemas_Valvulas += ","+nrProblemasValvulas;
		}
	}
	public String getNr_Problemas_Pneus() {
		return this.nr_Problemas_Pneus;
	}
	public void addNr_Problemas_Pneus(final String nrProblemasPneus) {
		if (this.nr_Problemas_Pneus == null) {
			this.nr_Problemas_Pneus = nrProblemasPneus;
		} else {
			this.nr_Problemas_Pneus += ","+nrProblemasPneus;
		}
	}
	public String getNr_Problemas_Outros() {
		return this.nr_Problemas_Outros;
	}
	public void addNr_Problemas_Outros(final String nrProblemasOutros) {
		if (this.nr_Problemas_Outros == null) {
			this.nr_Problemas_Outros = nrProblemasOutros;
		} else {
			this.nr_Problemas_Outros += ","+nrProblemasOutros;
		}
	}
}