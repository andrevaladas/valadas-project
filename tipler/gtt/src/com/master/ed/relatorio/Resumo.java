package com.master.ed.relatorio;

import java.io.Serializable;

/**
 * @author Andre Valadas
 */
public class Resumo implements Serializable {

	private static final long serialVersionUID = 2524608761407523514L;

	private String nrFrota;
	private String relacaoProblemasPneus = "";

	public Resumo(final String nrFrota) {
		super();
		this.nrFrota = nrFrota;
	}

	public String getNrFrota() {
		return this.nrFrota;
	}
	public void setNrFrota(final String nrFrota) {
		this.nrFrota = nrFrota;
	}
	public String getRelacaoProblemasPneus() {
		return this.relacaoProblemasPneus;
	}
	public void addRelacaoProblemasPneus(final long nrFogo, final String problemaPneu) {
		this.relacaoProblemasPneus += nrFogo + " ("+problemaPneu+") ";
	}
}