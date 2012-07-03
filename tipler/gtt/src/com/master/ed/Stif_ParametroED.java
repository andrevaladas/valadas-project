package com.master.ed;

/**
 * @author André Valadas
 * @serial Parametros STIF
 * @serialData 06/2012
 */
public class Stif_ParametroED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = -71409467337279702L;

	private long oid_Empresa;
	private long oid_stif_parametro;
	
	//Cotação de Pneus
	private double vl_pneu_novo;
	private double vl_pneu_r1;
	private double vl_pneu_r2;
	private double vl_pneu_r3;
	private double vl_pneu_r4;
	
	//Pesos para incidências
	private double pc_peso_0_5;
	private double pc_peso_6_10;
	private double pc_peso_11_15;
	private double pc_peso_16_20;
	private double pc_peso_21_25;
	private double pc_peso_26_30;
	private double pc_peso_31_35;
	private double pc_peso_36_40;
	private double pc_peso_41_45;
	private double pc_peso_46_50;
	private double pc_peso_acima_50;

	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oidEmpresa) {
		oid_Empresa = oidEmpresa;
	}
	public long getOid_stif_parametro() {
		return oid_stif_parametro;
	}
	public void setOid_stif_parametro(long oidStifParametro) {
		oid_stif_parametro = oidStifParametro;
	}
	public double getVl_pneu_novo() {
		return vl_pneu_novo;
	}
	public void setVl_pneu_novo(double vlPneuNovo) {
		vl_pneu_novo = vlPneuNovo;
	}
	public double getVl_pneu_r1() {
		return vl_pneu_r1;
	}
	public void setVl_pneu_r1(double vlPneuR1) {
		vl_pneu_r1 = vlPneuR1;
	}
	public double getVl_pneu_r2() {
		return vl_pneu_r2;
	}
	public void setVl_pneu_r2(double vlPneuR2) {
		vl_pneu_r2 = vlPneuR2;
	}
	public double getVl_pneu_r3() {
		return vl_pneu_r3;
	}
	public void setVl_pneu_r3(double vlPneuR3) {
		vl_pneu_r3 = vlPneuR3;
	}
	public double getVl_pneu_r4() {
		return vl_pneu_r4;
	}
	public void setVl_pneu_r4(double vlPneuR4) {
		vl_pneu_r4 = vlPneuR4;
	}
	public double getPc_peso_0_5() {
		return pc_peso_0_5;
	}
	public void setPc_peso_0_5(double pcPeso_0_5) {
		pc_peso_0_5 = pcPeso_0_5;
	}
	public double getPc_peso_6_10() {
		return pc_peso_6_10;
	}
	public void setPc_peso_6_10(double pcPeso_6_10) {
		pc_peso_6_10 = pcPeso_6_10;
	}
	public double getPc_peso_11_15() {
		return pc_peso_11_15;
	}
	public void setPc_peso_11_15(double pcPeso_11_15) {
		pc_peso_11_15 = pcPeso_11_15;
	}
	public double getPc_peso_16_20() {
		return pc_peso_16_20;
	}
	public void setPc_peso_16_20(double pcPeso_16_20) {
		pc_peso_16_20 = pcPeso_16_20;
	}
	public double getPc_peso_21_25() {
		return pc_peso_21_25;
	}
	public void setPc_peso_21_25(double pcPeso_21_25) {
		pc_peso_21_25 = pcPeso_21_25;
	}
	public double getPc_peso_26_30() {
		return pc_peso_26_30;
	}
	public void setPc_peso_26_30(double pcPeso_26_30) {
		pc_peso_26_30 = pcPeso_26_30;
	}
	public double getPc_peso_31_35() {
		return pc_peso_31_35;
	}
	public void setPc_peso_31_35(double pcPeso_31_35) {
		pc_peso_31_35 = pcPeso_31_35;
	}
	public double getPc_peso_36_40() {
		return pc_peso_36_40;
	}
	public void setPc_peso_36_40(double pcPeso_36_40) {
		pc_peso_36_40 = pcPeso_36_40;
	}
	public double getPc_peso_41_45() {
		return pc_peso_41_45;
	}
	public void setPc_peso_41_45(double pcPeso_41_45) {
		pc_peso_41_45 = pcPeso_41_45;
	}
	public double getPc_peso_46_50() {
		return pc_peso_46_50;
	}
	public void setPc_peso_46_50(double pcPeso_46_50) {
		pc_peso_46_50 = pcPeso_46_50;
	}
	public double getPc_peso_acima_50() {
		return pc_peso_acima_50;
	}
	public void setPc_peso_acima_50(double pcPesoAcima_50) {
		pc_peso_acima_50 = pcPesoAcima_50;
	}
}	