package com.master.ed;

/**
 * @author André Valadas
 * @serial STIF - Veiculos Inspeções
 * @serialsince 06/2012
 */
public class Stif_Veiculo_InspecaoED extends RelatorioBaseED {

	private static final long serialVersionUID = 2669468254670366328L;

	private long oid_Veiculo_Inspecao;
	private long oid_Inspecao;
	private long oid_Veiculo;
	private long nr_Odometro;
	private String tx_Observacao;
	private String tx_Observacao_Imagem;

	//List<Stif_ConstatacaoED>
	private transient String array;

	private transient VeiculoED veiculoED = new VeiculoED(); 
	
	public long getOid_Veiculo_Inspecao() {
		return oid_Veiculo_Inspecao;
	}
	public void setOid_Veiculo_Inspecao(long oidVeiculoInspecao) {
		oid_Veiculo_Inspecao = oidVeiculoInspecao;
	}
	public long getOid_Inspecao() {
		return oid_Inspecao;
	}
	public void setOid_Inspecao(long oidInspecao) {
		oid_Inspecao = oidInspecao;
	}
	public long getOid_Veiculo() {
		return oid_Veiculo;
	}
	public void setOid_Veiculo(long oidVeiculo) {
		oid_Veiculo = oidVeiculo;
	}
	public long getNr_Odometro() {
		return nr_Odometro;
	}
	public void setNr_Odometro(long nrOdometro) {
		nr_Odometro = nrOdometro;
	}
	public String getTx_Observacao() {
		return tx_Observacao;
	}
	public void setTx_Observacao(String txObservacao) {
		tx_Observacao = txObservacao;
	}
	public String getTx_Observacao_Imagem() {
		return tx_Observacao_Imagem;
	}
	public void setTx_Observacao_Imagem(String txObservacaoImagem) {
		tx_Observacao_Imagem = txObservacaoImagem;
	}
	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}
	public VeiculoED getVeiculoED() {
		return veiculoED;
	}
	public void setVeiculoED(VeiculoED veiculoED) {
		this.veiculoED = veiculoED;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stif_Veiculo_InspecaoED [nr_Odometro=");
		builder.append(nr_Odometro);
		builder.append(", oid_Inspecao=");
		builder.append(oid_Inspecao);
		builder.append(", oid_Veiculo=");
		builder.append(oid_Veiculo);
		builder.append(", oid_Veiculo_Inspecao=");
		builder.append(oid_Veiculo_Inspecao);
		builder.append(", tx_Observacao=");
		builder.append(tx_Observacao);
		builder.append(", tx_Observacao_Imagem=");
		builder.append(tx_Observacao_Imagem);
		builder.append("]");
		return builder.toString();
	}
}