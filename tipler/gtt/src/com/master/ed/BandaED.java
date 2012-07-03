/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Bandas Tipler
 * @serialData 05/2009
 * 
 * 
 * 
 * Aplicação; Rodoviario, Urbano, Severo, Leves, Pavimentados, Misto, Não pavimentado
 * Tipo de Pneu (Construção); Radial/Diagonal, Radial, Diagonal
 * Eixo Recomendado; Tração, Livre, Livre de rebocados , Tração moderada, Qualquer
 *  
 */
public class BandaED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 2263295955710086386L;
	
	private long oid_Banda;
	private String cd_Banda;
	private String nm_Banda;
	private double nr_Largura;
	private double nr_Profundidade;
	private long oid_Fabricante_Banda;
	private String dm_Aplicacao;
	private String dm_Eixo;
	private String dm_Tipo_Pneu;
	private String tx_Descricao;
	private String dm_Fora_Uso;
	
	private long oid_Usuario;
	
	private String dm_Grava_Imagem;
	
	
	// REMOVER APOS ACERTOS NAS BANDAS DA TIPLER
	
	private long oid_Banda_Substituta;
	private String dm_Substituir;
	private String dm_Escapa_Substituida;
	
	// campos de outras tabelas
	
	private String nm_Fabricante_Banda;
	private String nm_Aplicacao;
	private String nm_Eixo;
	private String nm_Tipo_Pneu;
	private String nm_Fora_Uso;
	
	
	public BandaED () {
	}

	public String getCd_Banda() {
		return cd_Banda;
	}

	public void setCd_Banda(String cd_Banda) {
		this.cd_Banda = cd_Banda;
	}

	public String getNm_Banda() {
		return nm_Banda;
	}

	public void setNm_Banda(String nm_Banda) {
		this.nm_Banda = nm_Banda;
	}

	public double getNr_Largura() {
		return nr_Largura;
	}

	public void setNr_Largura(double nr_Largura) {
		this.nr_Largura = nr_Largura;
	}

	public double getNr_Profundidade() {
		return nr_Profundidade;
	}

	public void setNr_Profundidade(double nr_Profundidade) {
		this.nr_Profundidade = nr_Profundidade;
	}

	public long getOid_Banda() {
		return oid_Banda;
	}

	public void setOid_Banda(long oid_Banda) {
		this.oid_Banda = oid_Banda;
	}

	public long getOid_Fabricante_Banda() {
		return oid_Fabricante_Banda;
	}

	public void setOid_Fabricante_Banda(long oid_Fabricante_Banda) {
		this.oid_Fabricante_Banda = oid_Fabricante_Banda;
	}

	public String getDm_Aplicacao() {
		return dm_Aplicacao;
	}

	public void setDm_Aplicacao(String dm_Aplicacao) {
		this.dm_Aplicacao = dm_Aplicacao;
	}

	public String getDm_Grava_Imagem() {
		return dm_Grava_Imagem;
	}

	public void setDm_Grava_Imagem(String dm_Grava_Imagem) {
		this.dm_Grava_Imagem = dm_Grava_Imagem;
	}

	public long getOid_Usuario() {
		return oid_Usuario;
	}

	public void setOid_Usuario(long oid_Usuario) {
		this.oid_Usuario = oid_Usuario;
	}

	public String getDm_Eixo() {
		return dm_Eixo;
	}

	public void setDm_Eixo(String dm_Eixo) {
		this.dm_Eixo = dm_Eixo;
	}

	public String getDm_Tipo_Pneu() {
		return dm_Tipo_Pneu;
	}

	public void setDm_Tipo_Pneu(String dm_Tipo_Pneu) {
		this.dm_Tipo_Pneu = dm_Tipo_Pneu;
	}

	public String getTx_Descricao() {
		return tx_Descricao;
	}

	public void setTx_Descricao(String tx_Descricao) {
		this.tx_Descricao = tx_Descricao;
	}

	public String getNm_Fabricante_Banda() {
		return nm_Fabricante_Banda;
	}

	public void setNm_Fabricante_Banda(String nm_Fabricante_Banda) {
		this.nm_Fabricante_Banda = nm_Fabricante_Banda;
	}

	public String getNm_Aplicacao() {
		return nm_Aplicacao;
	}

	public void setNm_Aplicacao(String nm_Aplicacao) {
		this.nm_Aplicacao = nm_Aplicacao;
	}

	public String getNm_Eixo() {
		return nm_Eixo;
	}

	public void setNm_Eixo(String nm_Eixo) {
		this.nm_Eixo = nm_Eixo;
	}

	public String getNm_Tipo_Pneu() {
		return nm_Tipo_Pneu;
	}

	public void setNm_Tipo_Pneu(String nm_Tipo_Pneu) {
		this.nm_Tipo_Pneu = nm_Tipo_Pneu;
	}

	public String getDm_Substituir() {
		return dm_Substituir;
	}

	public void setDm_Substituir(String dm_Substituir) {
		this.dm_Substituir = dm_Substituir;
	}

	public long getOid_Banda_Substituta() {
		return oid_Banda_Substituta;
	}

	public void setOid_Banda_Substituta(long oid_Banda_Substituta) {
		this.oid_Banda_Substituta = oid_Banda_Substituta;
	}

	public String getDm_Escapa_Substituida() {
		return dm_Escapa_Substituida;
	}

	public void setDm_Escapa_Substituida(String dm_Escapa_Substituida) {
		this.dm_Escapa_Substituida = dm_Escapa_Substituida;
	}

	public String getDm_Fora_Uso() {
		return dm_Fora_Uso;
	}

	public void setDm_Fora_Uso(String dm_Fora_Uso) {
		this.dm_Fora_Uso = dm_Fora_Uso;
	}

	public String getNm_Fora_Uso() {
		return nm_Fora_Uso;
	}

	public void setNm_Fora_Uso(String nm_Fora_Uso) {
		this.nm_Fora_Uso = nm_Fora_Uso;
	}


}	