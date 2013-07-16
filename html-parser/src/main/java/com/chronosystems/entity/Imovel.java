package com.chronosystems.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chronosystems.entity.enumeration.CategoriaImovel;
import com.chronosystems.entity.enumeration.SimNao;
import com.chronosystems.entity.enumeration.SiteBusca;
import com.chronosystems.entity.enumeration.TipoImovel;

@Entity
public class Imovel implements Serializable {
	private static final long serialVersionUID = -2610532917952796318L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_IMOVEL", unique = true, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "SITE_BUSCA", nullable = false, length = 20)
	private SiteBusca siteBusca;

	@Column(name = "URL_ANUNCIO", nullable = false, length = 255)
	private String urlAnuncio;

	@Column(name = "CODIGO_ANUNCIO", nullable = false, length = 45)
	private String codigoAnuncio;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", nullable = false, length = 2)
	private TipoImovel tipoImovel;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORIA", nullable = false, length = 2)
	private CategoriaImovel categoriaImovel;
	private Integer dormitorios;

	@Column(name = "BOX_GARAGEM")
	private Integer boxGaragem;

	@Column(name = "AREA_TOTAL")
	private BigDecimal areaTotal;

	@Column(name = "AREA_PRIVATIVA")
	private BigDecimal areaPrivativa;
	private BigDecimal valor;

	@Column(nullable = false, length = 1000)
	private String endereco;
	private BigDecimal latitude;
	private BigDecimal longitude;

	@Column(name = "TITULO_RESUMO", nullable = false, length = 255)
	private String tituloResumo;

	@Column(name = "CARACTERISTICA_RESUMO", nullable = false, length = 255)
	private String caracteristicasResumo;

	@Column(name = "DESCRICAO_RESUMO", nullable = false, length = 255)
	private String descricaoResumo;

	@Column(name = "ANUNCIANTE", length = 255)
	private String anunciante;

	@Column(name = "IMG_DESTAQUE", nullable = false, length = 255)
	private String imgDestaque;

	@Column(name = "TOTAL_IMAGENS")
	private Integer totalImagens;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "imovel", cascade = {
			javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE })
	private Set<ImagemImovel> imagens = new HashSet<ImagemImovel>(0);

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INCLUSAO", length = 10)
	private Date dataInclusao;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ALTERACAO", length = 10)
	private Date dataAlteracao;

	@Enumerated(EnumType.STRING)
	@Column(name = "ATIVO", nullable = false, length = 1)
	private SimNao ativo;

	public Imovel() {
	}

	public Imovel(SiteBusca siteBusca) {
		this.siteBusca = siteBusca;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteBusca getSiteBusca() {
		return this.siteBusca;
	}

	public void setSiteBusca(SiteBusca siteBusca) {
		this.siteBusca = siteBusca;
	}

	public String getCodigoAnuncio() {
		return this.codigoAnuncio;
	}

	public void setCodigoAnuncio(String codigoAnuncio) {
		this.codigoAnuncio = codigoAnuncio;
	}

	public String getUrlAnuncio() {
		return this.urlAnuncio;
	}

	public void setUrlAnuncio(String urlAnuncio) {
		this.urlAnuncio = urlAnuncio;
	}

	public TipoImovel getTipoImovel() {
		return this.tipoImovel;
	}

	public void setTipoImovel(TipoImovel tipoImovel) {
		this.tipoImovel = tipoImovel;
	}

	public CategoriaImovel getCategoriaImovel() {
		return this.categoriaImovel;
	}

	public void setCategoriaImovel(CategoriaImovel categoriaImovel) {
		this.categoriaImovel = categoriaImovel;
	}

	public Integer getDormitorios() {
		return this.dormitorios;
	}

	public void setDormitorios(Integer dormitorios) {
		this.dormitorios = dormitorios;
	}

	public Integer getBoxGaragem() {
		return this.boxGaragem;
	}

	public void setBoxGaragem(Integer boxGaragem) {
		this.boxGaragem = boxGaragem;
	}

	public BigDecimal getAreaTotal() {
		return this.areaTotal;
	}

	public void setAreaTotal(BigDecimal areaTotal) {
		this.areaTotal = areaTotal;
	}

	public BigDecimal getAreaPrivativa() {
		return this.areaPrivativa;
	}

	public void setAreaPrivativa(BigDecimal areaPrivativa) {
		this.areaPrivativa = areaPrivativa;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getTituloResumo() {
		return this.tituloResumo;
	}

	public void setTituloResumo(String tituloResumo) {
		this.tituloResumo = tituloResumo;
	}

	public String getCaracteristicasResumo() {
		return this.caracteristicasResumo;
	}

	public void setCaracteristicasResumo(String caracteristicasResumo) {
		this.caracteristicasResumo = caracteristicasResumo;
	}

	public String getDescricaoResumo() {
		return this.descricaoResumo;
	}

	public void setDescricaoResumo(String descricaoResumo) {
		this.descricaoResumo = descricaoResumo;
	}

	public String getAnunciante() {
		return this.anunciante;
	}

	public void setAnunciante(String anunciante) {
		this.anunciante = anunciante;
	}

	public String getImgDestaque() {
		return this.imgDestaque;
	}

	public void setImgDestaque(String imgDestaque) {
		this.imgDestaque = imgDestaque;
	}

	public Integer getTotalImagens() {
		return this.totalImagens;
	}

	public void setTotalImagens(Integer totalImagens) {
		this.totalImagens = totalImagens;
	}

	public Set<ImagemImovel> getImagens() {
		return this.imagens;
	}

	public void setImagens(Set<ImagemImovel> imagens) {
		this.imagens = imagens;
	}

	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return this.dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public SimNao getAtivo() {
		return this.ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}
}