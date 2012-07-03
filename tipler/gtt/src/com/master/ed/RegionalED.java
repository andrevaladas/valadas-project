/*
 * Created on 11/11/2004
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Regionais
 * @serialData 01/2010
 */
public class RegionalED extends RelatorioBaseED {

	private static final long serialVersionUID = -2867318044920540130L;
	
	private long oid_Regional;
	private String cd_Regional;
	private String nm_Regional;
	
	public RegionalED() {
	}

	public String getCd_Regional() {
		return cd_Regional;
	}

	public void setCd_Regional(String cd_Regional) {
		this.cd_Regional = cd_Regional;
	}

	public String getNm_Regional() {
		return nm_Regional;
	}

	public void setNm_Regional(String nm_Regional) {
		this.nm_Regional = nm_Regional;
	}

	public long getOid_Regional() {
		return oid_Regional;
	}

	public void setOid_Regional(long oid_Regional) {
		this.oid_Regional = oid_Regional;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	

}
