package com.chronosystems.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author André Valadas
 */
public class Imovel implements Serializable {

	private static final long serialVersionUID = 4113898195453186664L;

	private Long id;
	private Long empresa;

	private String url;
	private String codigo;

	private String titulo;
	private String caracteristica;
	private String descricao;
	private String valor;
	private String urlImagem;
	private String anunciante;

	private Date dataInclusao;
	private Date dataAlteracao;

	/**
	 * Default Contructor
	 */
	public Imovel() {
	}

	/**
	 * @param empresa
	 */
	public Imovel(Long empresa) {
		super();
		this.empresa = empresa;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the empresa
	 */
	public Long getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the caracteristica
	 */
	public String getCaracteristica() {
		return caracteristica;
	}

	/**
	 * @param caracteristica
	 *            the caracteristica to set
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the urlImagem
	 */
	public String getUrlImagem() {
		return urlImagem;
	}

	/**
	 * @param urlImagem
	 *            the urlImagem to set
	 */
	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	/**
	 * @return the anunciante
	 */
	public String getAnunciante() {
		return anunciante;
	}

	/**
	 * @param anunciante
	 *            the anunciante to set
	 */
	public void setAnunciante(String anunciante) {
		this.anunciante = anunciante;
	}

	/**
	 * @return the dataInclusao
	 */
	public Date getDataInclusao() {
		return dataInclusao;
	}

	/**
	 * @param dataInclusao
	 *            the dataInclusao to set
	 */
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	/**
	 * @return the dataAlteracao
	 */
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	/**
	 * @param dataAlteracao
	 *            the dataAlteracao to set
	 */
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
}