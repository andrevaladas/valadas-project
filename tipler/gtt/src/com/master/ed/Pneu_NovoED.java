/*
 * Created on 14/05/2012
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Pneus Novos
 * @serialData 05/2012
 */
public class Pneu_NovoED  extends RelatorioBaseED {

	private static final long serialVersionUID = 5003449522226567117L;
	
	private long oid_Pneu_Novo;
	private String cd_Pneu_Novo;
	private double nr_Mm_Profundidade;
	private long oid_Modelo_Pneu;
	private long oid_Pneu_Dimensao;
	// Campos de outras tabelas
	private String cd_Modelo_Pneu;
	private String nm_Modelo_Pneu;
	private long oid_Fabricante_Pneu;
	private String nm_Fabricante_Pneu;
	private String cd_Pneu_Dimensao;
	private String nm_Pneu_Dimensao;
	private double nr_Mm_Perimetro;
	
	public Pneu_NovoED () {
	}

	public long getOid_Pneu_Novo() {
		return oid_Pneu_Novo;
	}

	public void setOid_Pneu_Novo(long oidPneuNovo) {
		oid_Pneu_Novo = oidPneuNovo;
	}

	public double getNr_Mm_Profundidade() {
		return nr_Mm_Profundidade;
	}

	public void setNr_Mm_Profundidade(double nrMmProfundidade) {
		nr_Mm_Profundidade = nrMmProfundidade;
	}

	public long getOid_Pneu_Dimensao() {
		return oid_Pneu_Dimensao;
	}

	public void setOid_Pneu_Dimensao(long oidPneuDimensao) {
		oid_Pneu_Dimensao = oidPneuDimensao;
	}

	public String getCd_Modelo_Pneu() {
		return cd_Modelo_Pneu;
	}

	public void setCd_Modelo_Pneu(String cdModeloPneu) {
		cd_Modelo_Pneu = cdModeloPneu;
	}

	public String getNm_Modelo_Pneu() {
		return nm_Modelo_Pneu;
	}

	public void setNm_Modelo_Pneu(String nmModeloPneu) {
		nm_Modelo_Pneu = nmModeloPneu;
	}

	public String getNm_Fabricante_Pneu() {
		return nm_Fabricante_Pneu;
	}

	public void setNm_Fabricante_Pneu(String nmFabricantePneu) {
		nm_Fabricante_Pneu = nmFabricantePneu;
	}

	public String getCd_Pneu_Dimensao() {
		return cd_Pneu_Dimensao;
	}

	public void setCd_Pneu_Dimensao(String cdPneuDimensao) {
		cd_Pneu_Dimensao = cdPneuDimensao;
	}

	public String getNm_Pneu_Dimensao() {
		return nm_Pneu_Dimensao;
	}

	public void setNm_Pneu_Dimensao(String nmPneuDimensao) {
		nm_Pneu_Dimensao = nmPneuDimensao;
	}

	public double getNr_Mm_Perimetro() {
		return nr_Mm_Perimetro;
	}

	public void setNr_Mm_Perimetro(double nrMmPerimetro) {
		nr_Mm_Perimetro = nrMmPerimetro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getOid_Modelo_Pneu() {
		return oid_Modelo_Pneu;
	}

	public void setOid_Modelo_Pneu(long oidModeloPneu) {
		oid_Modelo_Pneu = oidModeloPneu;
	}

	public String getCd_Pneu_Novo() {
		return cd_Pneu_Novo;
	}

	public void setCd_Pneu_Novo(String cdPneuNovo) {
		cd_Pneu_Novo = cdPneuNovo;
	}

	public long getOid_Fabricante_Pneu() {
		return oid_Fabricante_Pneu;
	}

	public void setOid_Fabricante_Pneu(long oidFabricantePneu) {
		oid_Fabricante_Pneu = oidFabricantePneu;
	}
	

}	