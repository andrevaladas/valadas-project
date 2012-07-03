/*
 * Created on 0/06/2009
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Parametros GTT
 * @serialData 06/2009
 */
public class Parametro_GttED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 7036513480465161676L;

	private long oid_Parametro_Gtt;
	private double nr_Twi;
	private double nr_Perc_Perda_0_30;
	private double nr_Perc_Perda_31_60;
	private double nr_Perc_Perda_61_90;
	private double nr_Perc_Perda_91_120;
	
	public double getNr_Perc_Perda_0_30() {
		return nr_Perc_Perda_0_30;
	}
	public void setNr_Perc_Perda_0_30(double nr_Perc_Perda_0_30) {
		this.nr_Perc_Perda_0_30 = nr_Perc_Perda_0_30;
	}
	public double getNr_Perc_Perda_31_60() {
		return nr_Perc_Perda_31_60;
	}
	public void setNr_Perc_Perda_31_60(double nr_Perc_Perda_31_60) {
		this.nr_Perc_Perda_31_60 = nr_Perc_Perda_31_60;
	}
	public double getNr_Perc_Perda_61_90() {
		return nr_Perc_Perda_61_90;
	}
	public void setNr_Perc_Perda_61_90(double nr_Perc_Perda_61_90) {
		this.nr_Perc_Perda_61_90 = nr_Perc_Perda_61_90;
	}
	public double getNr_Perc_Perda_91_120() {
		return nr_Perc_Perda_91_120;
	}
	public void setNr_Perc_Perda_91_120(double nr_Perc_Perda_91_120) {
		this.nr_Perc_Perda_91_120 = nr_Perc_Perda_91_120;
	}
	public double getNr_Twi() {
		return nr_Twi;
	}
	public void setNr_Twi(double nr_Twi) {
		this.nr_Twi = nr_Twi;
	}
	public long getOid_Parametro_Gtt() {
		return oid_Parametro_Gtt;
	}
	public void setOid_Parametro_Gtt(long oid_Parametro_Gtt) {
		this.oid_Parametro_Gtt = oid_Parametro_Gtt;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}	