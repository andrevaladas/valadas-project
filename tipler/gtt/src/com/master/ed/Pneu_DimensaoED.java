/*
 * Created on 14/05/2012
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Dimensoes de pneus
 * @serialData 05/2012
 */
public class Pneu_DimensaoED extends RelatorioBaseED {
	
	private static final long serialVersionUID = -2768239443523413058L;
	
	private long oid_Pneu_Dimensao;
	private String cd_Pneu_Dimensao;
	private String nm_Pneu_Dimensao;
	private double nr_Mm_Perimetro;
	
	public Pneu_DimensaoED () {
	}

	public long getOid_Pneu_Dimensao() {
		return oid_Pneu_Dimensao;
	}

	public void setOid_Pneu_Dimensao(long oidPneuDimensao) {
		oid_Pneu_Dimensao = oidPneuDimensao;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getNr_Mm_Perimetro() {
		return nr_Mm_Perimetro;
	}

	public void setNr_Mm_Perimetro(double nrMmPerimetro) {
		nr_Mm_Perimetro = nrMmPerimetro;
	}

}	