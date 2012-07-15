package com.master.ed.relatorio;

import java.io.Serializable;

/**
 * @author Andre Valadas
 */
public class Problema implements Serializable {

	private static final long serialVersionUID = 213724632559825105L;

	private String descricao;

	public Problema(final String descricao) {
		super();
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}
}