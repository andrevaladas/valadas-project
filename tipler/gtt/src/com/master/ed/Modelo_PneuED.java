/*
 * Created on 12/11/2004
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Modelos de pneus
 * @serialData 06/2007
 */
public class Modelo_PneuED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 7928915324972837643L;
  
	private long oid_Modelo_Pneu;
	private long oid_Empresa;
	private String CD_Modelo_Pneu;
	private String NM_Modelo_Pneu;
	private long oid_Fabricante_Pneu;
	private String nm_Fabricante_Pneu;
	private String dm_Tipo_Pneu;
	
	transient String msg_Stamp;
	transient String nm_Tipo_Pneu;
	
	public String getMsg_Stamp() {
		return msg_Stamp;
	}

	public void setMsg_Stamp(String msg_Stamp) {
		this.msg_Stamp = msg_Stamp;
	}

	public Modelo_PneuED () {
	}

	public String getCD_Modelo_Pneu() {
		return CD_Modelo_Pneu;
	}

	public void setCD_Modelo_Pneu(String modelo_Pneu) {
		CD_Modelo_Pneu = modelo_Pneu;
	}

	public String getNM_Modelo_Pneu() {
		return NM_Modelo_Pneu;
	}
	
	public void setNM_Modelo_Pneu(String modelo_Pneu) {
		NM_Modelo_Pneu = modelo_Pneu;
	}
	
	public long getOid_Empresa() {
		return oid_Empresa;
	}
	
	public void setOid_Empresa(long oid_Empresa) {
		this.oid_Empresa = oid_Empresa;
	}
	
	public long getOid_Fabricante_Pneu() {
		return oid_Fabricante_Pneu;
	}
	
	public void setOid_Fabricante_Pneu(long oid_Fabricante_Pneu) {
		this.oid_Fabricante_Pneu = oid_Fabricante_Pneu;
	}
	
	public long getOid_Modelo_Pneu() {
		return oid_Modelo_Pneu;
	}
	
	public void setOid_Modelo_Pneu(long oid_Modelo_Pneu) {
		this.oid_Modelo_Pneu = oid_Modelo_Pneu;
	}
	
	public String getNm_Fabricante_Pneu() {
		return nm_Fabricante_Pneu;
	}

	public void setNm_Fabricante_Pneu(String nm_Fabricante_Pneu) {
		this.nm_Fabricante_Pneu = nm_Fabricante_Pneu;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDm_Tipo_Pneu() {
		return dm_Tipo_Pneu;
	}

	public void setDm_Tipo_Pneu(String dm_Tipo_Pneu) {
		this.dm_Tipo_Pneu = dm_Tipo_Pneu;
	}

	public String getNm_Tipo_Pneu() {
		if ("R".equals(this.dm_Tipo_Pneu))
			return "RADIAL";
		else
			return "DIAGONAL";	
	}

	public void setNm_Tipo_Pneu(String nm_Tipo_Pneu) {
		this.nm_Tipo_Pneu = nm_Tipo_Pneu;
	}

}
