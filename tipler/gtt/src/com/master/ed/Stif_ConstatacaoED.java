package com.master.ed;

/**
 * @author André Valadas
 * @serial STIF - Constatações
 * @serialsince 06/2012
 */
public class Stif_ConstatacaoED extends RelatorioBaseED {

	private static final long serialVersionUID = 3578706152969795639L;

	private long oid_Constatacao;
	private long oid_Veiculo_Inspecao;
	private long oid_Perda_Percentual;
	private long nr_Eixo;

	public long getOid_Constatacao() {
		return oid_Constatacao;
	}
	public void setOid_Constatacao(long oidConstatacao) {
		oid_Constatacao = oidConstatacao;
	}
	public long getOid_Veiculo_Inspecao() {
		return oid_Veiculo_Inspecao;
	}
	public void setOid_Veiculo_Inspecao(long oidVeiculoInspecao) {
		oid_Veiculo_Inspecao = oidVeiculoInspecao;
	}
	public long getOid_Perda_Percentual() {
		return oid_Perda_Percentual;
	}
	public void setOid_Perda_Percentual(long oidPerdaPercentual) {
		oid_Perda_Percentual = oidPerdaPercentual;
	}
	public long getNr_Eixo() {
		return nr_Eixo;
	}
	public void setNr_Eixo(long nrEixo) {
		nr_Eixo = nrEixo;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
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