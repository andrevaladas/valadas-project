package com.chronosystems.entity.enumeration;
/**
 * @author Andre Valadas
 *
 */
public enum CategoriaImovel {
	NO("Novo"), US("Usado"), LA("Lançamento");

	private String description;
	private CategoriaImovel(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}