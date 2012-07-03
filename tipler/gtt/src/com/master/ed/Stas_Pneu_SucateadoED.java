/*
 * Created on 15/05/2012
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STAS - Pneus Sucateados
 * @serialData - 05/2012
 */
public class Stas_Pneu_SucateadoED  extends RelatorioBaseED {

	private static final long serialVersionUID = -2861142185607536615L;
	
	private long oid_Pneu_Sucateado;
	private long oid_Analise;
	private long oid_Pneu_Dimensao;
	private long oid_Modelo_Pneu;
	private long oid_Banda;
	private long oid_Motivo_Sucata;
	private long oid_Banda_Dimensao;
	private String nr_Fogo;
	private int nr_Vida;
	private double nr_Mm_Medido;
	private double nr_Mm_Inicial;
	private double vl_Pneu;
	
	// Campos de outras tabelas
	
	private transient String nm_Pneu_Dimensao;
	private transient String nm_Modelo_Pneu;
	private transient String nm_Banda;
	private transient String nm_Vida;
	private transient String nm_Motivo_Sucata;
	private transient long oid_Fabricante_Banda;
	private transient long oid_Fabricante_Pneu;
	
	// Dados da dimensao sucateada
	
	private transient double nr_Twi;
	private transient double vl_Pneu_Novo;
	private transient double vl_Recapagem;
	private transient double vl_Carcaca_R1;
	private transient double vl_Carcaca_R2;
	private transient double vl_Carcaca_R3;
	private transient double vl_Carcaca_R4;
	private transient double vl_Carcaca_R5;
	
	// Campo para indicar processo
	
	private transient boolean dm_Gravou_Dimensao;
	
	public Stas_Pneu_SucateadoED(){
	}

	public long getOid_Pneu_Sucateado() {
		return oid_Pneu_Sucateado;
	}

	public void setOid_Pneu_Sucateado(long oidPneuSucateado) {
		oid_Pneu_Sucateado = oidPneuSucateado;
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

	public long getOid_Modelo_Pneu() {
		return oid_Modelo_Pneu;
	}

	public void setOid_Modelo_Pneu(long oidModeloPneu) {
		oid_Modelo_Pneu = oidModeloPneu;
	}

	public long getOid_Banda() {
		return oid_Banda;
	}

	public void setOid_Banda(long oidBanda) {
		oid_Banda = oidBanda;
	}

	public long getOid_Motivo_Sucata() {
		return oid_Motivo_Sucata;
	}

	public void setOid_Motivo_Sucata(long oidMotivoSucata) {
		oid_Motivo_Sucata = oidMotivoSucata;
	}

	public String getNr_Fogo() {
		return nr_Fogo;
	}

	public void setNr_Fogo(String nrFogo) {
		nr_Fogo = nrFogo;
	}

	public int getNr_Vida() {
		return nr_Vida;
	}

	public void setNr_Vida(int nrVida) {
		nr_Vida = nrVida;
	}

	public double getNr_Mm_Medido() {
		return nr_Mm_Medido;
	}

	public void setNr_Mm_Medido(double nrMmMedido) {
		nr_Mm_Medido = nrMmMedido;
	}

	public double getNr_Mm_Inicial() {
		return nr_Mm_Inicial;
	}

	public void setNr_Mm_Inicial(double nrMmInicial) {
		nr_Mm_Inicial = nrMmInicial;
	}

	public double getVl_Pneu() {
		return vl_Pneu;
	}

	public void setVl_Pneu(double vlPneu) {
		vl_Pneu = vlPneu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNm_Pneu_Dimensao() {
		return nm_Pneu_Dimensao;
	}

	public void setNm_Pneu_Dimensao(String nmPneuDimensao) {
		nm_Pneu_Dimensao = nmPneuDimensao;
	}

	public String getNm_Modelo_Pneu() {
		return nm_Modelo_Pneu;
	}

	public void setNm_Modelo_Pneu(String nmModeloPneu) {
		nm_Modelo_Pneu = nmModeloPneu;
	}

	public String getNm_Banda() {
		return nm_Banda;
	}

	public void setNm_Banda(String nmBanda) {
		nm_Banda = nmBanda;
	}

	public String getNm_Vida() {
		nm_Vida="ZERO";
		if (this.nr_Vida==0) {
			nm_Vida="NOVO";
		} else {
			nm_Vida="R"+this.nr_Vida;
		} 	
		return nm_Vida;
	}

	public void setNm_Vida(String nmVida) {
		nm_Vida = nmVida;
	}

	public String getNm_Motivo_Sucata() {
		return nm_Motivo_Sucata;
	}

	public void setNm_Motivo_Sucata(String nmMotivoSucata) {
		nm_Motivo_Sucata = nmMotivoSucata;
	}

	public boolean isDm_Gravou_Dimensao() {
		return dm_Gravou_Dimensao;
	}

	public void setDm_Gravou_Dimensao(boolean dmGravouDimensao) {
		dm_Gravou_Dimensao = dmGravouDimensao;
	}

	public long getOid_Fabricante_Banda() {
		return oid_Fabricante_Banda;
	}

	public void setOid_Fabricante_Banda(long oidFabricanteBanda) {
		oid_Fabricante_Banda = oidFabricanteBanda;
	}

	public long getOid_Fabricante_Pneu() {
		return oid_Fabricante_Pneu;
	}

	public void setOid_Fabricante_Pneu(long oidFabricantePneu) {
		oid_Fabricante_Pneu = oidFabricantePneu;
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

	public long getOid_Banda_Dimensao() {
		return oid_Banda_Dimensao;
	}

	public void setOid_Banda_Dimensao(long oidBandaDimensao) {
		oid_Banda_Dimensao = oidBandaDimensao;
	}
	
}	

