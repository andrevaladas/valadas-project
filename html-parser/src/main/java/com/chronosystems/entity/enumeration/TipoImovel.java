package com.chronosystems.entity.enumeration;

public enum TipoImovel {
	CA("Casa"), AP("Apartamento"), CO("Comercial"), TE("Terreno"), BO("Box/Garagem");

	private String description;

	private TipoImovel(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}