/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STIB - Parametros
 * @serialData 04/2012
 */
public class Stib_ParametroED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 8253710665738483687L;
	
	private String nm_Parametro;
	private int nr_Percentual_Faixa1_De;
	private int nr_Percentual_Faixa1_Ate;
	private int nr_Percentual_Faixa2_De;
	private int nr_Percentual_Faixa2_Ate;
	private int nr_Percentual_Faixa3_De;
	private int nr_Percentual_Faixa3_Ate;
	
	public String getNm_Parametro() {
		return nm_Parametro;
	}
	public void setNm_Parametro(String nmParametro) {
		nm_Parametro = nmParametro;
	}
	public int getNr_Percentual_Faixa1_De() {
		return nr_Percentual_Faixa1_De;
	}
	public void setNr_Percentual_Faixa1_De(int nrPercentualFaixa1De) {
		nr_Percentual_Faixa1_De = nrPercentualFaixa1De;
	}
	public int getNr_Percentual_Faixa1_Ate() {
		return nr_Percentual_Faixa1_Ate;
	}
	public void setNr_Percentual_Faixa1_Ate(int nrPercentualFaixa1Ate) {
		nr_Percentual_Faixa1_Ate = nrPercentualFaixa1Ate;
	}
	public int getNr_Percentual_Faixa2_De() {
		return nr_Percentual_Faixa2_De;
	}
	public void setNr_Percentual_Faixa2_De(int nrPercentualFaixa2De) {
		nr_Percentual_Faixa2_De = nrPercentualFaixa2De;
	}
	public int getNr_Percentual_Faixa2_Ate() {
		return nr_Percentual_Faixa2_Ate;
	}
	public void setNr_Percentual_Faixa2_Ate(int nrPercentualFaixa2Ate) {
		nr_Percentual_Faixa2_Ate = nrPercentualFaixa2Ate;
	}
	public int getNr_Percentual_Faixa3_De() {
		return nr_Percentual_Faixa3_De;
	}
	public void setNr_Percentual_Faixa3_De(int nrPercentualFaixa3De) {
		nr_Percentual_Faixa3_De = nrPercentualFaixa3De;
	}
	public int getNr_Percentual_Faixa3_Ate() {
		return nr_Percentual_Faixa3_Ate;
	}
	public void setNr_Percentual_Faixa3_Ate(int nrPercentualFaixa3Ate) {
		nr_Percentual_Faixa3_Ate = nrPercentualFaixa3Ate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}	