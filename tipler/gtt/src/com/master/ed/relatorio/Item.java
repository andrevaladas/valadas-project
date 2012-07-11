package com.master.ed.relatorio;

import java.io.Serializable;

/**
 * @author Andre Valadas
 */
public class Item implements Serializable {

	private static final long serialVersionUID = 7715559925553019556L;

	private String nome;
	private Integer quantidade = 1;
	private Double valor = 0D;

	public Item(final String nome) {
		super();
		this.nome = nome;
	}

	public void addQuantidade() {
		this.quantidade++;
	}
	public void addValor(final Double value) {
		this.valor += value;
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(final String nome) {
		this.nome = nome;
	}
	public Integer getQuantidade() {
		return this.quantidade;
	}
	public void setQuantidade(final Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return this.valor;
	}
	public void setValor(final Double valor) {
		this.valor = valor;
	}
}