/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Bandas Tipler
 * @serialData 01/2010
 */
public class Banda_RelacionamentoED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = -7258682609710557829L;
	
	private long oid_Banda_Relacionamento;
	private long oid_Banda_1;
	private long oid_Banda_2;
	
	transient private String nm_Banda_1;
	transient private String nm_Banda_2;
	transient private String nm_Fabricante_Banda_1;
	transient private String nm_Fabricante_Banda_2;
	
	public Banda_RelacionamentoED () {
	}

	public long getOid_Banda_1() {
		return oid_Banda_1;
	}

	public void setOid_Banda_1(long oid_Banda_1) {
		this.oid_Banda_1 = oid_Banda_1;
	}

	public long getOid_Banda_2() {
		return oid_Banda_2;
	}

	public void setOid_Banda_2(long oid_Banda_2) {
		this.oid_Banda_2 = oid_Banda_2;
	}

	public long getOid_Banda_Relacionamento() {
		return oid_Banda_Relacionamento;
	}

	public void setOid_Banda_Relacionamento(long oid_Banda_Relacionamento) {
		this.oid_Banda_Relacionamento = oid_Banda_Relacionamento;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getNm_Banda_1() {
		return nm_Banda_1;
	}

	public void setNm_Banda_1(String nm_Banda_1) {
		this.nm_Banda_1 = nm_Banda_1;
	}

	public String getNm_Banda_2() {
		return nm_Banda_2;
	}

	public void setNm_Banda_2(String nm_Banda_2) {
		this.nm_Banda_2 = nm_Banda_2;
	}

	public String getNm_Fabricante_Banda_1() {
		return nm_Fabricante_Banda_1;
	}

	public void setNm_Fabricante_Banda_1(String nm_Fabricante_Banda_1) {
		this.nm_Fabricante_Banda_1 = nm_Fabricante_Banda_1;
	}

	public String getNm_Fabricante_Banda_2() {
		return nm_Fabricante_Banda_2;
	}

	public void setNm_Fabricante_Banda_2(String nm_Fabricante_Banda_2) {
		this.nm_Fabricante_Banda_2 = nm_Fabricante_Banda_2;
	}

}	