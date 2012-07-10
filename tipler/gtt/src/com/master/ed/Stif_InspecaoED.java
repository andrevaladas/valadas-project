package com.master.ed;

/**
 * @author André Valadas
 * @serial STIF - Inspeções
 * @since 06/2012
 */
public class Stif_InspecaoED extends RelatorioBaseED {

	private static final long serialVersionUID = 2625394321779196460L;

	private long oid_Inspecao;
	private long oid_Empresa;
	private long oid_Usuario;
	private long oid_Cliente;
	private long nr_Veiculos;
	private String dt_Inspecao;
	private String dt_Encerramento;

	//Relatório
	private String tx_Inicial;
	private String tx_Final;
	private String tx_Assinatura1;
	private String tx_Assinatura2;
	private String nm_Signatario;
	private String nm_Tecnico;

	//Cotação de Pneus
	private double vl_pneu_novo;
	private double vl_pneu_r1;
	private double vl_pneu_r2;
	private double vl_pneu_r3;
	private double vl_pneu_r4;

	// List<Stif_Veiculo_InspecaoED>
	private transient String array;
	
	private transient boolean encerrada;
	private transient boolean todas;

	public long getOid_Inspecao() {
		return oid_Inspecao;
	}

	public void setOid_Inspecao(final long oidInspecao) {
		oid_Inspecao = oidInspecao;
	}

	public long getOid_Empresa() {
		return oid_Empresa;
	}

	public void setOid_Empresa(final long oidEmpresa) {
		oid_Empresa = oidEmpresa;
	}

	public long getOid_Usuario() {
		return oid_Usuario;
	}

	public void setOid_Usuario(final long oidUsuario) {
		oid_Usuario = oidUsuario;
	}

	public long getOid_Cliente() {
		return oid_Cliente;
	}

	public void setOid_Cliente(final long oidCliente) {
		oid_Cliente = oidCliente;
	}
	
	public long getNr_Veiculos() {
		return nr_Veiculos;
	}

	public void setNr_Veiculos(final long nrVeiculos) {
		nr_Veiculos = nrVeiculos;
	}

	public String getDt_Inspecao() {
		return dt_Inspecao;
	}

	public void setDt_Inspecao(final String dtInspecao) {
		dt_Inspecao = dtInspecao;
	}

	public String getDt_Encerramento() {
		return dt_Encerramento;
	}

	public void setDt_Encerramento(final String dtEncerramento) {
		dt_Encerramento = dtEncerramento;
	}

	public String getTx_Inicial() {
		return tx_Inicial;
	}

	public void setTx_Inicial(final String txInicial) {
		tx_Inicial = txInicial;
	}

	public String getTx_Final() {
		return tx_Final;
	}

	public void setTx_Final(final String txFinal) {
		tx_Final = txFinal;
	}

	public String getTx_Assinatura1() {
		return tx_Assinatura1;
	}

	public void setTx_Assinatura1(final String txAssinatura1) {
		tx_Assinatura1 = txAssinatura1;
	}

	public String getTx_Assinatura2() {
		return tx_Assinatura2;
	}

	public void setTx_Assinatura2(final String txAssinatura2) {
		tx_Assinatura2 = txAssinatura2;
	}

	public String getNm_Signatario() {
		return nm_Signatario;
	}

	public void setNm_Signatario(final String nmSignatario) {
		nm_Signatario = nmSignatario;
	}

	public String getNm_Tecnico() {
		return nm_Tecnico;
	}

	public void setNm_Tecnico(final String nmTecnico) {
		nm_Tecnico = nmTecnico;
	}

	public double getVl_pneu_novo() {
		return vl_pneu_novo;
	}

	public void setVl_pneu_novo(final double vlPneuNovo) {
		vl_pneu_novo = vlPneuNovo;
	}

	public double getVl_pneu_r1() {
		return vl_pneu_r1;
	}

	public void setVl_pneu_r1(final double vlPneuR1) {
		vl_pneu_r1 = vlPneuR1;
	}

	public double getVl_pneu_r2() {
		return vl_pneu_r2;
	}

	public void setVl_pneu_r2(final double vlPneuR2) {
		vl_pneu_r2 = vlPneuR2;
	}

	public double getVl_pneu_r3() {
		return vl_pneu_r3;
	}

	public void setVl_pneu_r3(final double vlPneuR3) {
		vl_pneu_r3 = vlPneuR3;
	}

	public double getVl_pneu_r4() {
		return vl_pneu_r4;
	}

	public void setVl_pneu_r4(final double vlPneuR4) {
		vl_pneu_r4 = vlPneuR4;
	}

	public String getArray() {
		return array;
	}

	public void setArray(final String array) {
		this.array = array;
	}

	public boolean isEncerrada() {
		return encerrada;
	}

	public void setEncerrada(final boolean encerrada) {
		this.encerrada = encerrada;
	}

	public boolean isTodas() {
		return todas;
	}

	public void setTodas(final boolean todas) {
		this.todas = todas;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Stif_InspecaoED [dt_Encerramento=");
		builder.append(dt_Encerramento);
		builder.append(", dt_Inspecao=");
		builder.append(dt_Inspecao);
		builder.append(", nr_Veiculos=");
		builder.append(nr_Veiculos);
		builder.append(", oid_Cliente=");
		builder.append(oid_Cliente);
		builder.append(", oid_Empresa=");
		builder.append(oid_Empresa);
		builder.append(", oid_Inspecao=");
		builder.append(oid_Inspecao);
		builder.append(", oid_Usuario=");
		builder.append(oid_Usuario);
		builder.append(", vl_pneu_novo=");
		builder.append(vl_pneu_novo);
		builder.append(", vl_pneu_r1=");
		builder.append(vl_pneu_r1);
		builder.append(", vl_pneu_r2=");
		builder.append(vl_pneu_r2);
		builder.append(", vl_pneu_r3=");
		builder.append(vl_pneu_r3);
		builder.append(", vl_pneu_r4=");
		builder.append(vl_pneu_r4);
		builder.append("]");
		return builder.toString();
	}
}	