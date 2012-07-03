/*
 * Created on 10/06/2009
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Faturamento de concessionaria
 * @serialData 06/2009
 */
public class Faturamento_ConcessionariaED extends RelatorioBaseED {

	private static final long serialVersionUID = 8730269234802245374L;
	
	private long oid_Faturamento_Concessionaria;
	private long oid_Concessionaria;
	private String dm_Mes_Ano;
	private double vl_Faturamento;
	
	private transient String nm_Razao_Social;
		
	public String getDm_Mes_Ano() {
		return dm_Mes_Ano;
	}
	public void setDm_Mes_Ano(String dm_Mes_Ano) {
		this.dm_Mes_Ano = dm_Mes_Ano;
	}
	public long getOid_Faturamento_Concessionaria() {
		return oid_Faturamento_Concessionaria;
	}
	public void setOid_Faturamento_Concessionaria(
			long oid_Faturamento_Concessionaria) {
		this.oid_Faturamento_Concessionaria = oid_Faturamento_Concessionaria;
	}
	public double getVl_Faturamento() {
		return vl_Faturamento;
	}
	public void setVl_Faturamento(double vl_Faturamento) {
		this.vl_Faturamento = vl_Faturamento;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public long getOid_Concessionaria() {
		return oid_Concessionaria;
	}
	public void setOid_Concessionaria(long oid_Concessionaria) {
		this.oid_Concessionaria = oid_Concessionaria;
	}
	public String getNm_Razao_Social() {
		return nm_Razao_Social;
	}
	public void setNm_Razao_Social(String nm_Razao_Social) {
		this.nm_Razao_Social = nm_Razao_Social;
	}
	

	
}
