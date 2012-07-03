package com.master.ed;

/**
 * @author André Valadas
 * @serial Cadastro de Veículos
 * @since 06/2012
 */
public class VeiculoED extends RelatorioBaseED {

	private static final long serialVersionUID = -7386959830459546154L;

	private long oid_Empresa;
	private long oid_Veiculo;
	private String nr_Frota;
	private String nr_Placa;
	private long oid_Cliente;
	private long dm_Tipo_Veiculo;
	private String nm_Marca_Veiculo;
	private String nm_Modelo_Veiculo;
	private long nr_Ano_Fabricacao;
	private long nr_Ano_Modelo;
	private long dm_Tipo_Piso;
	private long dm_Tipo_Severidade;
	private String nm_Rota;
	private long dm_Tipo_Chassis;

	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oidEmpresa) {
		oid_Empresa = oidEmpresa;
	}
	public long getOid_Veiculo() {
		return oid_Veiculo;
	}
	public void setOid_Veiculo(long oidVeiculo) {
		oid_Veiculo = oidVeiculo;
	}
	public String getNr_Frota() {
		return nr_Frota;
	}
	public void setNr_Frota(String nrFrota) {
		nr_Frota = nrFrota;
	}
	public String getNr_Placa() {
		return nr_Placa;
	}
	public void setNr_Placa(String nrPlaca) {
		nr_Placa = nrPlaca;
	}
	public long getOid_Cliente() {
		return oid_Cliente;
	}
	public void setOid_Cliente(long oidCliente) {
		oid_Cliente = oidCliente;
	}
	public long getDm_Tipo_Veiculo() {
		return dm_Tipo_Veiculo;
	}
	public void setDm_Tipo_Veiculo(long dmTipoVeiculo) {
		dm_Tipo_Veiculo = dmTipoVeiculo;
	}
	public String getNm_Marca_Veiculo() {
		return nm_Marca_Veiculo;
	}
	public void setNm_Marca_Veiculo(String nmMarcaVeiculo) {
		nm_Marca_Veiculo = nmMarcaVeiculo;
	}
	public String getNm_Modelo_Veiculo() {
		return nm_Modelo_Veiculo;
	}
	public void setNm_Modelo_Veiculo(String nmModeloVeiculo) {
		nm_Modelo_Veiculo = nmModeloVeiculo;
	}
	public long getNr_Ano_Fabricacao() {
		return nr_Ano_Fabricacao;
	}
	public void setNr_Ano_Fabricacao(long nrAnoFabricacao) {
		nr_Ano_Fabricacao = nrAnoFabricacao;
	}
	public long getNr_Ano_Modelo() {
		return nr_Ano_Modelo;
	}
	public void setNr_Ano_Modelo(long nrAnoModelo) {
		nr_Ano_Modelo = nrAnoModelo;
	}
	public long getDm_Tipo_Piso() {
		return dm_Tipo_Piso;
	}
	public void setDm_Tipo_Piso(long dmTipoPiso) {
		dm_Tipo_Piso = dmTipoPiso;
	}
	public long getDm_Tipo_Severidade() {
		return dm_Tipo_Severidade;
	}
	public void setDm_Tipo_Severidade(long dmTipoSeveridade) {
		dm_Tipo_Severidade = dmTipoSeveridade;
	}
	public String getNm_Rota() {
		return nm_Rota;
	}
	public void setNm_Rota(String nmRota) {
		nm_Rota = nmRota;
	}
	public long getDm_Tipo_Chassis() {
		return dm_Tipo_Chassis;
	}
	public void setDm_Tipo_Chassis(long dmTipoChassis) {
		dm_Tipo_Chassis = dmTipoChassis;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VeiculoED [dm_Tipo_Chassis=");
		builder.append(dm_Tipo_Chassis);
		builder.append(", dm_Tipo_Piso=");
		builder.append(dm_Tipo_Piso);
		builder.append(", dm_Tipo_Severidade=");
		builder.append(dm_Tipo_Severidade);
		builder.append(", dm_Tipo_Veiculo=");
		builder.append(dm_Tipo_Veiculo);
		builder.append(", nm_Marca_Veiculo=");
		builder.append(nm_Marca_Veiculo);
		builder.append(", nm_Modelo_Veiculo=");
		builder.append(nm_Modelo_Veiculo);
		builder.append(", nm_Rota=");
		builder.append(nm_Rota);
		builder.append(", nr_Ano_Fabricacao=");
		builder.append(nr_Ano_Fabricacao);
		builder.append(", nr_Ano_Modelo=");
		builder.append(nr_Ano_Modelo);
		builder.append(", nr_Frota=");
		builder.append(nr_Frota);
		builder.append(", nr_Placa=");
		builder.append(nr_Placa);
		builder.append(", oid_Cliente=");
		builder.append(oid_Cliente);
		builder.append(", oid_Empresa=");
		builder.append(oid_Empresa);
		builder.append(", oid_Veiculo=");
		builder.append(oid_Veiculo);
		builder.append("]");
		return builder.toString();
	}
}