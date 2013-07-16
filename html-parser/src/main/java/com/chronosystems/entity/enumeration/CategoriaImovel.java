package com.chronosystems.entity.enumeration;
/**
 * @author Andre Valadas
 *
 */
public enum CategoriaImovel {
	NO("Novo"), US("Usado"), LA("Lan�amento");

	private String description;
	private CategoriaImovel(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}