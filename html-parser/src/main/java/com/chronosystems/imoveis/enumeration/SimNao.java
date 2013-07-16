package com.chronosystems.imoveis.enumeration;

public enum SimNao {
	S("Sim"), N("Não");

	private String description;

	private SimNao(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}