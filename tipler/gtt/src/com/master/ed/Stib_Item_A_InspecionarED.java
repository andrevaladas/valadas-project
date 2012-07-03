/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STIB - Itens a inspecionar
 * @serialData 03/2012
 */
public class Stib_Item_A_InspecionarED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 1948288686727049370L;
	
	private long oid_Item_A_Inspecionar;
	private String cd_Item_A_Inspecionar;
	private String nm_Item_A_Inspecionar;
	private String nm_Item_A_Inspecionar_E;
	private int nr_Ordem;
	private String dm_Tipo;
	//Campo calculado
	transient String nm_Tipo;
	
	public Stib_Item_A_InspecionarED () {
	}

	public long getOid_Item_A_Inspecionar() {
		return oid_Item_A_Inspecionar;
	}

	public void setOid_Item_A_Inspecionar(long oidItemAInspecionar) {
		oid_Item_A_Inspecionar = oidItemAInspecionar;
	}

	public String getCd_Item_A_Inspecionar() {
		return cd_Item_A_Inspecionar;
	}

	public void setCd_Item_A_Inspecionar(String cdItemAInspecionar) {
		cd_Item_A_Inspecionar = cdItemAInspecionar;
	}

	public String getNm_Item_A_Inspecionar() {
		return nm_Item_A_Inspecionar;
	}

	public void setNm_Item_A_Inspecionar(String nmItemAInspecionar) {
		nm_Item_A_Inspecionar = nmItemAInspecionar;
	}

	public String getNm_Item_A_Inspecionar_E() {
		return nm_Item_A_Inspecionar_E;
	}

	public void setNm_Item_A_Inspecionar_E(String nmItemAInspecionarE) {
		nm_Item_A_Inspecionar_E = nmItemAInspecionarE;
	}

	public int getNr_Ordem() {
		return nr_Ordem;
	}

	public void setNr_Ordem(int nrOrdem) {
		nr_Ordem = nrOrdem;
	}

	public String getDm_Tipo() {
		return dm_Tipo;
	}

	public void setDm_Tipo(String dmTipo) {
		dm_Tipo = dmTipo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNm_Tipo() {
		this.setNm_Tipo("");
		return nm_Tipo;
	}

	public void setNm_Tipo(String nmTipo) {
		nm_Tipo = ("P".equals(this.dm_Tipo) ? "PESSOAL - MÃO DE OBRA" : "EQUIPAMENTOS - INSTALAÇÕES");
	}

}	