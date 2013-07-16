package com.chronosystems.imoveis.enumeration;

public enum SiteBusca {
	PENSE_IMOVEIS("Pense Imóveis"), AUXILIADORA("Auxiliadora Predial");

	private String description;

	private SiteBusca(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}