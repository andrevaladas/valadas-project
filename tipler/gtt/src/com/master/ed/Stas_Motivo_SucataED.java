/*
 * Created on 01/05/2012
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STAS - Motivo Sucata
 * @serialData 05/2012
 */
public class Stas_Motivo_SucataED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 4198372242249319192L;
	
	private long oid_Motivo_Sucata;
	private String cd_Motivo_Sucata;
	private String nm_Motivo_Sucata;
	private String tx_Motivo_Sucata;
	private String tx_Recomendacao;
	private String nm_Motivo_Sucata_E;
	private String tx_Motivo_Sucata_E;
	private String tx_Recomendacao_E;
	
	private long oid_Usuario;
	
	private String dm_Grava_Imagem;
	
	// Campos para os relatórios de análise
	private int nr_Quantidade_Total;
	private double perc_Quantidade_Total;
	private double nr_Mm_Perdido_Total;
	private double vl_Perdido_Total;
	
	private int nr_Quantidade_Novo;
	private double perc_Quantidade_Novo;
	private double nr_Mm_Perdido_Novo;
	private double vl_Perdido_Novo;
	
	private int nr_Quantidade_R1;
	private double perc_Quantidade_R1;
	private double nr_Mm_Perdido_R1;
	private double vl_Perdido_R1;
	
	private int nr_Quantidade_R2;
	private double perc_Quantidade_R2;
	private double nr_Mm_Perdido_R2;
	private double vl_Perdido_R2;
	
	private int nr_Quantidade_R3;
	private double perc_Quantidade_R3;
	private double nr_Mm_Perdido_R3;
	private double vl_Perdido_R3;
	
	private int nr_Quantidade_R4;
	private double perc_Quantidade_R4;
	private double nr_Mm_Perdido_R4;
	private double vl_Perdido_R4;
	
	private int nr_Quantidade_R5;
	private double perc_Quantidade_R5;
	private double nr_Mm_Perdido_R5;
	private double vl_Perdido_R5;
	
	private String nm_Descricao_Tabela_Marca;
	
	
	public Stas_Motivo_SucataED() {
		
	}

	public long getOid_Motivo_Sucata() {
		return oid_Motivo_Sucata;
	}

	public void setOid_Motivo_Sucata(long oidMotivoSucata) {
		oid_Motivo_Sucata = oidMotivoSucata;
	}

	public String getCd_Motivo_Sucata() {
		return cd_Motivo_Sucata;
	}

	public void setCd_Motivo_Sucata(String cdMotivoSucata) {
		cd_Motivo_Sucata = cdMotivoSucata;
	}

	public String getNm_Motivo_Sucata() {
		return nm_Motivo_Sucata;
	}

	public void setNm_Motivo_Sucata(String nmMotivoSucata) {
		nm_Motivo_Sucata = nmMotivoSucata;
	}

	public String getTx_Motivo_Sucata() {
		return tx_Motivo_Sucata;
	}

	public void setTx_Motivo_Sucata(String txMotivoSucata) {
		tx_Motivo_Sucata = txMotivoSucata;
	}

	public String getTx_Recomendacao() {
		return tx_Recomendacao;
	}

	public void setTx_Recomendacao(String txRecomendacao) {
		tx_Recomendacao = txRecomendacao;
	}

	public String getNm_Motivo_Sucata_E() {
		return nm_Motivo_Sucata_E;
	}

	public void setNm_Motivo_Sucata_E(String nmMotivoSucataE) {
		nm_Motivo_Sucata_E = nmMotivoSucataE;
	}

	public String getTx_Motivo_Sucata_E() {
		return tx_Motivo_Sucata_E;
	}

	public void setTx_Motivo_Sucata_E(String txMotivoSucataE) {
		tx_Motivo_Sucata_E = txMotivoSucataE;
	}

	public String getTx_Recomendacao_E() {
		return tx_Recomendacao_E;
	}

	public void setTx_Recomendacao_E(String txRecomendacaoE) {
		tx_Recomendacao_E = txRecomendacaoE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getOid_Usuario() {
		return oid_Usuario;
	}

	public void setOid_Usuario(long oidUsuario) {
		oid_Usuario = oidUsuario;
	}

	public String getDm_Grava_Imagem() {
		return dm_Grava_Imagem;
	}

	public void setDm_Grava_Imagem(String dmGravaImagem) {
		dm_Grava_Imagem = dmGravaImagem;
	}

	public int getNr_Quantidade_Total() {
		return nr_Quantidade_Total;
	}

	public void setNr_Quantidade_Total(int nrQuantidadeTotal) {
		nr_Quantidade_Total = nrQuantidadeTotal;
	}

	public double getNr_Mm_Perdido_Total() {
		return nr_Mm_Perdido_Total;
	}

	public void setNr_Mm_Perdido_Total(double nrMmPerdidoTotal) {
		nr_Mm_Perdido_Total = nrMmPerdidoTotal;
	}

	public double getVl_Perdido_Total() {
		return vl_Perdido_Total;
	}

	public void setVl_Perdido_Total(double vlPerdidoTotal) {
		vl_Perdido_Total = vlPerdidoTotal;
	}

	public int getNr_Quantidade_Novo() {
		return nr_Quantidade_Novo;
	}

	public void setNr_Quantidade_Novo(int nrQuantidadeNovo) {
		nr_Quantidade_Novo = nrQuantidadeNovo;
	}

	public double getNr_Mm_Perdido_Novo() {
		return nr_Mm_Perdido_Novo;
	}

	public void setNr_Mm_Perdido_Novo(double nrMmPerdidoNovo) {
		nr_Mm_Perdido_Novo = nrMmPerdidoNovo;
	}

	public double getVl_Perdido_Novo() {
		return vl_Perdido_Novo;
	}

	public void setVl_Perdido_Novo(double vlPerdidoNovo) {
		vl_Perdido_Novo = vlPerdidoNovo;
	}

	public int getNr_Quantidade_R1() {
		return nr_Quantidade_R1;
	}

	public void setNr_Quantidade_R1(int nrQuantidadeR1) {
		nr_Quantidade_R1 = nrQuantidadeR1;
	}

	public double getNr_Mm_Perdido_R1() {
		return nr_Mm_Perdido_R1;
	}

	public void setNr_Mm_Perdido_R1(double nrMmPerdidoR1) {
		nr_Mm_Perdido_R1 = nrMmPerdidoR1;
	}

	public double getVl_Perdido_R1() {
		return vl_Perdido_R1;
	}

	public void setVl_Perdido_R1(double vlPerdidoR1) {
		vl_Perdido_R1 = vlPerdidoR1;
	}

	public int getNr_Quantidade_R2() {
		return nr_Quantidade_R2;
	}

	public void setNr_Quantidade_R2(int nrQuantidadeR2) {
		nr_Quantidade_R2 = nrQuantidadeR2;
	}

	public double getNr_Mm_Perdido_R2() {
		return nr_Mm_Perdido_R2;
	}

	public void setNr_Mm_Perdido_R2(double nrMmPerdidoR2) {
		nr_Mm_Perdido_R2 = nrMmPerdidoR2;
	}

	public double getVl_Perdido_R2() {
		return vl_Perdido_R2;
	}

	public void setVl_Perdido_R2(double vlPerdidoR2) {
		vl_Perdido_R2 = vlPerdidoR2;
	}

	public int getNr_Quantidade_R3() {
		return nr_Quantidade_R3;
	}

	public void setNr_Quantidade_R3(int nrQuantidadeR3) {
		nr_Quantidade_R3 = nrQuantidadeR3;
	}

	public double getNr_Mm_Perdido_R3() {
		return nr_Mm_Perdido_R3;
	}

	public void setNr_Mm_Perdido_R3(double nrMmPerdidoR3) {
		nr_Mm_Perdido_R3 = nrMmPerdidoR3;
	}

	public double getVl_Perdido_R3() {
		return vl_Perdido_R3;
	}

	public void setVl_Perdido_R3(double vlPerdidoR3) {
		vl_Perdido_R3 = vlPerdidoR3;
	}

	public int getNr_Quantidade_R4() {
		return nr_Quantidade_R4;
	}

	public void setNr_Quantidade_R4(int nrQuantidadeR4) {
		nr_Quantidade_R4 = nrQuantidadeR4;
	}

	public double getNr_Mm_Perdido_R4() {
		return nr_Mm_Perdido_R4;
	}

	public void setNr_Mm_Perdido_R4(double nrMmPerdidoR4) {
		nr_Mm_Perdido_R4 = nrMmPerdidoR4;
	}

	public double getVl_Perdido_R4() {
		return vl_Perdido_R4;
	}

	public void setVl_Perdido_R4(double vlPerdidoR4) {
		vl_Perdido_R4 = vlPerdidoR4;
	}

	public int getNr_Quantidade_R5() {
		return nr_Quantidade_R5;
	}

	public void setNr_Quantidade_R5(int nrQuantidadeR5) {
		nr_Quantidade_R5 = nrQuantidadeR5;
	}

	public double getNr_Mm_Perdido_R5() {
		return nr_Mm_Perdido_R5;
	}

	public void setNr_Mm_Perdido_R5(double nrMmPerdidoR5) {
		nr_Mm_Perdido_R5 = nrMmPerdidoR5;
	}

	public double getVl_Perdido_R5() {
		return vl_Perdido_R5;
	}

	public void setVl_Perdido_R5(double vlPerdidoR5) {
		vl_Perdido_R5 = vlPerdidoR5;
	}

	public double getPerc_Quantidade_Novo() {
		return perc_Quantidade_Novo;
	}

	public void setPerc_Quantidade_Novo(double percQuantidadeNovo) {
		perc_Quantidade_Novo = percQuantidadeNovo;
	}

	public double getPerc_Quantidade_R1() {
		return perc_Quantidade_R1;
	}

	public void setPerc_Quantidade_R1(double percQuantidadeR1) {
		perc_Quantidade_R1 = percQuantidadeR1;
	}

	public double getPerc_Quantidade_R2() {
		return perc_Quantidade_R2;
	}

	public void setPerc_Quantidade_R2(double percQuantidadeR2) {
		perc_Quantidade_R2 = percQuantidadeR2;
	}

	public double getPerc_Quantidade_R3() {
		return perc_Quantidade_R3;
	}

	public void setPerc_Quantidade_R3(double percQuantidadeR3) {
		perc_Quantidade_R3 = percQuantidadeR3;
	}

	public double getPerc_Quantidade_R4() {
		return perc_Quantidade_R4;
	}

	public void setPerc_Quantidade_R4(double percQuantidadeR4) {
		perc_Quantidade_R4 = percQuantidadeR4;
	}

	public double getPerc_Quantidade_R5() {
		return perc_Quantidade_R5;
	}

	public void setPerc_Quantidade_R5(double percQuantidadeR5) {
		perc_Quantidade_R5 = percQuantidadeR5;
	}

	public double getPerc_Quantidade_Total() {
		return perc_Quantidade_Total;
	}

	public void setPerc_Quantidade_Total(double percQuantidadeTotal) {
		perc_Quantidade_Total = percQuantidadeTotal;
	}

	public String getNm_Descricao_Tabela_Marca() {
		return nm_Descricao_Tabela_Marca;
	}

	public void setNm_Descricao_Tabela_Marca(String nmDescricaoTabelaMarca) {
		nm_Descricao_Tabela_Marca = nmDescricaoTabelaMarca;
	}
	
}	