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

	public void setOid_Inspecao(long oidInspecao) {
		oid_Inspecao = oidInspecao;
	}

	public long getOid_Empresa() {
		return oid_Empresa;
	}

	public void setOid_Empresa(long oidEmpresa) {
		oid_Empresa = oidEmpresa;
	}

	public long getOid_Usuario() {
		return oid_Usuario;
	}

	public void setOid_Usuario(long oidUsuario) {
		oid_Usuario = oidUsuario;
	}

	public long getOid_Cliente() {
		return oid_Cliente;
	}

	public void setOid_Cliente(long oidCliente) {
		oid_Cliente = oidCliente;
	}
	
	public long getNr_Veiculos() {
		return nr_Veiculos;
	}

	public void setNr_Veiculos(long nrVeiculos) {
		nr_Veiculos = nrVeiculos;
	}

	public String getDt_Inspecao() {
		return dt_Inspecao;
	}

	public void setDt_Inspecao(String dtInspecao) {
		dt_Inspecao = dtInspecao;
	}

	public String getDt_Encerramento() {
		return dt_Encerramento;
	}

	public void setDt_Encerramento(String dtEncerramento) {
		dt_Encerramento = dtEncerramento;
	}

	public double getVl_pneu_novo() {
		return vl_pneu_novo;
	}

	public void setVl_pneu_novo(double vlPneuNovo) {
		vl_pneu_novo = vlPneuNovo;
	}

	public double getVl_pneu_r1() {
		return vl_pneu_r1;
	}

	public void setVl_pneu_r1(double vlPneuR1) {
		vl_pneu_r1 = vlPneuR1;
	}

	public double getVl_pneu_r2() {
		return vl_pneu_r2;
	}

	public void setVl_pneu_r2(double vlPneuR2) {
		vl_pneu_r2 = vlPneuR2;
	}

	public double getVl_pneu_r3() {
		return vl_pneu_r3;
	}

	public void setVl_pneu_r3(double vlPneuR3) {
		vl_pneu_r3 = vlPneuR3;
	}

	public double getVl_pneu_r4() {
		return vl_pneu_r4;
	}

	public void setVl_pneu_r4(double vlPneuR4) {
		vl_pneu_r4 = vlPneuR4;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}

	public boolean isEncerrada() {
		return encerrada;
	}

	public void setEncerrada(boolean encerrada) {
		this.encerrada = encerrada;
	}

	public boolean isTodas() {
		return todas;
	}

	public void setTodas(boolean todas) {
		this.todas = todas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
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