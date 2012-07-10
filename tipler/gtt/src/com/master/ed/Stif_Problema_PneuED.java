package com.master.ed;

/**
 * @author André Valadas
 * @serial STIF - Problemas Pneus
 * @serialsince 06/2012
 */
public class Stif_Problema_PneuED extends RelatorioBaseED {

	private static final long serialVersionUID = 5395470942606864740L;

	private long oid_Problema_Pneu;
	private long oid_Pneu_Inspecao;
	private long oid_Stif_Problema;

	private transient Stif_ProblemaED problemaED;
	
	public long getOid_Problema_Pneu() {
		return oid_Problema_Pneu;
	}
	public void setOid_Problema_Pneu(final long oidProblemaPneu) {
		oid_Problema_Pneu = oidProblemaPneu;
	}
	public long getOid_Pneu_Inspecao() {
		return oid_Pneu_Inspecao;
	}
	public void setOid_Pneu_Inspecao(final long oidPneuInspecao) {
		oid_Pneu_Inspecao = oidPneuInspecao;
	}
	public long getOid_Stif_Problema() {
		return oid_Stif_Problema;
	}
	public void setOid_Stif_Problema(final long oidStifProblema) {
		oid_Stif_Problema = oidStifProblema;
	}
	public Stif_ProblemaED getProblemaED() {
		return problemaED;
	}
	public void setProblemaED(final Stif_ProblemaED problemaED) {
		this.problemaED = problemaED;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Stif_Problema_PneuED [oid_Pneu_Inspecao=");
		builder.append(oid_Pneu_Inspecao);
		builder.append(", oid_Problema_Pneu=");
		builder.append(oid_Problema_Pneu);
		builder.append(", oid_Stif_Problema=");
		builder.append(oid_Stif_Problema);
		builder.append("]");
		return builder.toString();
	}
}