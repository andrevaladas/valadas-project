package com.master.ed;

/**
 * @author André Valadas
 * @serial Problemas STIF
 * @since 06/2012
 */
public class Stif_ProblemaED extends RelatorioBaseED {

	private static final long serialVersionUID = 6388744922943019305L;

	private long oid_Empresa;
	private long oid_stif_problema;
	private String cd_problema;
	private String nm_problema;
	private double pc_perda;
	private String dm_tipo;
	private String dm_problema;

	private transient long ordem;

	private transient long oid_Pneu_Inspecao;
	private transient boolean checkItem;

	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oidEmpresa) {
		oid_Empresa = oidEmpresa;
	}
	public long getOid_stif_problema() {
		return oid_stif_problema;
	}
	public void setOid_stif_problema(long oidStifProblema) {
		oid_stif_problema = oidStifProblema;
	}
	public String getCd_problema() {
		return cd_problema;
	}
	public void setCd_problema(String cdProblema) {
		cd_problema = cdProblema;
	}
	public String getNm_problema() {
		return nm_problema;
	}
	public void setNm_problema(String nmProblema) {
		nm_problema = nmProblema;
	}
	public double getPc_perda() {
		return pc_perda;
	}
	public void setPc_perda(double pcPerda) {
		pc_perda = pcPerda;
	}
	public String getDm_tipo() {
		return dm_tipo;
	}
	public void setDm_tipo(String dmTipo) {
		dm_tipo = dmTipo;
	}

	public long getOid_Pneu_Inspecao() {
		return oid_Pneu_Inspecao;
	}
	public void setOid_Pneu_Inspecao(long oidPneuInspecao) {
		oid_Pneu_Inspecao = oidPneuInspecao;
	}
	public boolean isCheckItem() {
		return checkItem;
	}
	public void setCheckItem(boolean checkItem) {
		this.checkItem = checkItem;
	}
	public String getDm_problema() {
		return dm_problema;
	}
	public void setDm_problema(String dmProblema) {
		dm_problema = dmProblema;
	}
	public long getOrdem() {
		return ordem;
	}
	public void setOrdem(long ordem) {
		this.ordem = ordem;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stif_ProblemaED [cd_problema=");
		builder.append(cd_problema);
		builder.append(", dm_problema=");
		builder.append(dm_problema);
		builder.append(", dm_tipo=");
		builder.append(dm_tipo);
		builder.append(", nm_problema=");
		builder.append(nm_problema);
		builder.append(", oid_Empresa=");
		builder.append(oid_Empresa);
		builder.append(", oid_stif_problema=");
		builder.append(oid_stif_problema);
		builder.append(", pc_perda=");
		builder.append(pc_perda);
		builder.append("]");
		return builder.toString();
	}
}