/*
 * Created on 20/05/2012
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STAS - Dimensoes Sucateados
 * @serialData - 05/2012
 */
public class Stas_Dimensao_SucateadoED  extends RelatorioBaseED {

	private static final long serialVersionUID = -7465866002241295515L;
	private long oid_Dimensao_Sucateado;
	private long oid_Analise;
	private long oid_Pneu_Dimensao;
	private double nr_Twi;
	private double vl_Pneu_Novo;
	private double vl_Recapagem;
	private double vl_Carcaca_R1;
	private double vl_Carcaca_R2;
	private double vl_Carcaca_R3;
	private double vl_Carcaca_R4;
	private double vl_Carcaca_R5;
	
	// Campos de outras tabelas
	private transient String nm_Pneu_Dimensao;
	
	public Stas_Dimensao_SucateadoED(){
	}

	public long getOid_Dimensao_Sucateado() {
		return oid_Dimensao_Sucateado;
	}

	public void setOid_Dimensao_Sucateado(long oidDimensaoSucateado) {
		oid_Dimensao_Sucateado = oidDimensaoSucateado;
	}

	public long getOid_Analise() {
		return oid_Analise;
	}

	public void setOid_Analise(long oidAnalise) {
		oid_Analise = oidAnalise;
	}

	public long getOid_Pneu_Dimensao() {
		return oid_Pneu_Dimensao;
	}

	public void setOid_Pneu_Dimensao(long oidPneuDimensao) {
		oid_Pneu_Dimensao = oidPneuDimensao;
	}

	public double getNr_Twi() {
		return nr_Twi;
	}

	public void setNr_Twi(double nrTwi) {
		nr_Twi = nrTwi;
	}

	public double getVl_Pneu_Novo() {
		return vl_Pneu_Novo;
	}

	public void setVl_Pneu_Novo(double vlPneuNovo) {
		vl_Pneu_Novo = vlPneuNovo;
	}

	public double getVl_Recapagem() {
		return vl_Recapagem;
	}

	public void setVl_Recapagem(double vlRecapagem) {
		vl_Recapagem = vlRecapagem;
	}

	public double getVl_Carcaca_R1() {
		return vl_Carcaca_R1;
	}

	public void setVl_Carcaca_R1(double vlCarcacaR1) {
		vl_Carcaca_R1 = vlCarcacaR1;
	}

	public double getVl_Carcaca_R2() {
		return vl_Carcaca_R2;
	}

	public void setVl_Carcaca_R2(double vlCarcacaR2) {
		vl_Carcaca_R2 = vlCarcacaR2;
	}

	public double getVl_Carcaca_R3() {
		return vl_Carcaca_R3;
	}

	public void setVl_Carcaca_R3(double vlCarcacaR3) {
		vl_Carcaca_R3 = vlCarcacaR3;
	}

	public double getVl_Carcaca_R4() {
		return vl_Carcaca_R4;
	}

	public void setVl_Carcaca_R4(double vlCarcacaR4) {
		vl_Carcaca_R4 = vlCarcacaR4;
	}

	public double getVl_Carcaca_R5() {
		return vl_Carcaca_R5;
	}

	public void setVl_Carcaca_R5(double vlCarcacaR5) {
		vl_Carcaca_R5 = vlCarcacaR5;
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
}	

