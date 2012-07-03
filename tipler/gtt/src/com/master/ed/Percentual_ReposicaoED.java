/*
 * Created on 9/06/2009
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Percentuais de Reposição da GTT
 * @serialData 06/2009
 */
public class Percentual_ReposicaoED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 3942686717752787811L;
	
	private long oid_Percentual_Reposicao;
	private double nr_Perc_Desgaste_De ;
	private double nr_Perc_Desgaste_Ate ;
	private double nr_Perc_Reforma;
	private double nr_Perc_Carcaca_Vida_1;
	private double nr_Perc_Carcaca_Vida_2;
	private double nr_Perc_Carcaca_Vida_3;

	//Variável para busca
	transient double nr_Perc_Busca;
	
	public double getNr_Perc_Carcaca_Vida_1() {
		return nr_Perc_Carcaca_Vida_1;
	}
	public void setNr_Perc_Carcaca_Vida_1(double nr_Perc_Carcaca_Vida_1) {
		this.nr_Perc_Carcaca_Vida_1 = nr_Perc_Carcaca_Vida_1;
	}
	public double getNr_Perc_Carcaca_Vida_2() {
		return nr_Perc_Carcaca_Vida_2;
	}
	public void setNr_Perc_Carcaca_Vida_2(double nr_Perc_Carcaca_Vida_2) {
		this.nr_Perc_Carcaca_Vida_2 = nr_Perc_Carcaca_Vida_2;
	}
	public double getNr_Perc_Carcaca_Vida_3() {
		return nr_Perc_Carcaca_Vida_3;
	}
	public void setNr_Perc_Carcaca_Vida_3(double nr_Perc_Carcaca_Vida_3) {
		this.nr_Perc_Carcaca_Vida_3 = nr_Perc_Carcaca_Vida_3;
	}
	public double getNr_Perc_Reforma() {
		return nr_Perc_Reforma;
	}
	public void setNr_Perc_Reforma(double nr_Perc_Reforma) {
		this.nr_Perc_Reforma = nr_Perc_Reforma;
	}
	public long getOid_Percentual_Reposicao() {
		return oid_Percentual_Reposicao;
	}
	public void setOid_Percentual_Reposicao(long oid_Percentual_Reposicao) {
		this.oid_Percentual_Reposicao = oid_Percentual_Reposicao;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public double getNr_Perc_Desgaste_Ate() {
		return nr_Perc_Desgaste_Ate;
	}
	public void setNr_Perc_Desgaste_Ate(double nr_Perc_Desgaste_Ate) {
		this.nr_Perc_Desgaste_Ate = nr_Perc_Desgaste_Ate;
	}
	public double getNr_Perc_Desgaste_De() {
		return nr_Perc_Desgaste_De;
	}
	public void setNr_Perc_Desgaste_De(double nr_Perc_Desgaste_De) {
		this.nr_Perc_Desgaste_De = nr_Perc_Desgaste_De;
	}
	public double getNr_Perc_Busca() {
		return nr_Perc_Busca;
	}
	public void setNr_Perc_Busca(double nr_Perc_Busca) {
		this.nr_Perc_Busca = nr_Perc_Busca;
	}
	
	
	
}	