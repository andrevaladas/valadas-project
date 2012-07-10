package com.master.ed;

/**
 * @author Andr� Valadas
 * @serial STIF - Constata��es
 * @serialsince 06/2012
 */
public class Stif_ConstatacaoED extends RelatorioBaseED {

	private static final long serialVersionUID = 3578706152969795639L;

	private long oid_Constatacao;
	private long oid_Veiculo_Inspecao;
	private long oid_Perda_Percentual;
	private long nr_Eixo;

	private transient Stif_Perda_PercentualED perdaPercentualED;

	public long getOid_Constatacao() {
		return oid_Constatacao;
	}
	public void setOid_Constatacao(final long oidConstatacao) {
		oid_Constatacao = oidConstatacao;
	}
	public long getOid_Veiculo_Inspecao() {
		return oid_Veiculo_Inspecao;
	}
	public void setOid_Veiculo_Inspecao(final long oidVeiculoInspecao) {
		oid_Veiculo_Inspecao = oidVeiculoInspecao;
	}
	public long getOid_Perda_Percentual() {
		return oid_Perda_Percentual;
	}
	public void setOid_Perda_Percentual(final long oidPerdaPercentual) {
		oid_Perda_Percentual = oidPerdaPercentual;
	}
	public long getNr_Eixo() {
		return nr_Eixo;
	}
	public void setNr_Eixo(final long nrEixo) {
		nr_Eixo = nrEixo;
	}

	public Stif_Perda_PercentualED getPerdaPercentualED() {
		return perdaPercentualED;
	}
	public void setPerdaPercentualED(final Stif_Perda_PercentualED perdaPercentualED) {
		this.perdaPercentualED = perdaPercentualED;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Stif_ConstatacaoED [nr_Eixo=");
		builder.append(nr_Eixo);
		builder.append(", oid_Constatacao=");
		builder.append(oid_Constatacao);
		builder.append(", oid_Perda_Percentual=");
		builder.append(oid_Perda_Percentual);
		builder.append(", oid_Veiculo_Inspecao=");
		builder.append(oid_Veiculo_Inspecao);
		builder.append("]");
		return builder.toString();
	}
}