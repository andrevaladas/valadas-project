/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Bandas Tipler
 * @serialData 05/2009
 */
public class Banda_DimensaoED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = -2671739212321648939L;
	
	private long oid_Banda_Dimensao;
	private long oid_Banda;
	private double nr_Largura;
	private double nr_Profundidade;
	private double nr_Peso;
	private double nr_Peso_Metro;
	
	public Banda_DimensaoED () {
	}

	public double getNr_Largura() {
		return nr_Largura;
	}

	public void setNr_Largura(double nr_Largura) {
		this.nr_Largura = nr_Largura;
	}

	public double getNr_Peso() {
		return nr_Peso;
	}

	public void setNr_Peso(double nr_Peso) {
		this.nr_Peso = nr_Peso;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getOid_Banda_Dimensao() {
		return oid_Banda_Dimensao;
	}

	public void setOid_Banda_Dimensao(long oid_Banda_Dimensao) {
		this.oid_Banda_Dimensao = oid_Banda_Dimensao;
	}

	public double getNr_Peso_Metro() {
		return nr_Peso_Metro;
	}

	public void setNr_Peso_Metro(double nr_Peso_Metro) {
		this.nr_Peso_Metro = nr_Peso_Metro;
	}

}	