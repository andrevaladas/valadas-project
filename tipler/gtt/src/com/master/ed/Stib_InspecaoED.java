/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STIB - Inspeções
 * @serialData 04/2012
 */
public class Stib_InspecaoED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = -1227107305178922400L;
	
	
	private long oid_Inspecao;
	private long oid_Concessionaria;
	private long oid_Usuario;
	private long oid_Cliente;
	private String dt_Inicio;
	private String dt_Fim;
	private String tx_Inicial;
	private String tx_Final;
	private String tx_Assinatura1;
	private String tx_Assinatura2;
	private String nm_Signatario;
	private String nm_Tecnico;
	// Array
	private String array;
	
	private long oid_Empresa;
	
	// Campos de outras tabelas
	private transient String nm_Razao_Social;
	private transient String nm_Usuario;
	private transient String nm_Local;
	private transient String nm_Local_Data;
	
	// Campos para consultas
	private transient String dm_Encerradas;
	private transient String dm_Ordenar;
	
	// Campos com quebra para o Laszlo
	private transient String tx_Inicial_OL;
	private transient String tx_Final_OL;
	private transient String tx_Assinatura1_OL;
	private transient String tx_Assinatura2_OL;
	
	private transient String tx_Tratamento;
	

	// Resumo
	private transient String nm_Nota_Equipamento;
	private transient long nr_Percentual_Equipamento;
	private transient String nm_Nota_Pessoal;
	private transient long nr_Percentual_Pessoal;
	private transient String nm_Nota_Geral;
	private transient long nr_Percentual_Geral;	
	
	public long getOid_Inspecao() {
		return oid_Inspecao;
	}
	public void setOid_Inspecao(long oidInspecao) {
		oid_Inspecao = oidInspecao;
	}
	public long getOid_Concessionaria() {
		return oid_Concessionaria;
	}
	public void setOid_Concessionaria(long oidConcessionaria) {
		oid_Concessionaria = oidConcessionaria;
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
	public String getDt_Inicio() {
		return dt_Inicio;
	}
	public void setDt_Inicio(String dtInicio) {
		dt_Inicio = dtInicio;
	}
	public String getDt_Fim() {
		return dt_Fim;
	}
	public void setDt_Fim(String dtFim) {
		dt_Fim = dtFim;
	}
	public String getTx_Inicial() {
		return tx_Inicial;
	}
	public void setTx_Inicial(String txInicial) {
		tx_Inicial = txInicial;
	}
	public String getTx_Final() {
		return tx_Final;
	}
	public void setTx_Final(String txFinal) {
		tx_Final = txFinal;
	}
	public String getTx_Assinatura1() {
		return tx_Assinatura1;
	}
	public void setTx_Assinatura1(String txAssinatura1) {
		tx_Assinatura1 = txAssinatura1;
	}
	public String getTx_Assinatura2() {
		return tx_Assinatura2;
	}
	public void setTx_Assinatura2(String txAssinatura2) {
		tx_Assinatura2 = txAssinatura2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}
	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oidEmpresa) {
		oid_Empresa = oidEmpresa;
	}
	public String getNm_Razao_Social() {
		return nm_Razao_Social;
	}
	public void setNm_Razao_Social(String nmRazaoSocial) {
		nm_Razao_Social = nmRazaoSocial;
	}
	public String getDm_Encerradas() {
		return dm_Encerradas;
	}
	public void setDm_Encerradas(String dmEncerradas) {
		dm_Encerradas = dmEncerradas;
	}
	public String getDm_Ordenar() {
		return dm_Ordenar;
	}
	public void setDm_Ordenar(String dmOrdenar) {
		dm_Ordenar = dmOrdenar;
	}
	public String getNm_Signatario() {
		return nm_Signatario;
	}
	public void setNm_Signatario(String nmSignatario) {
		nm_Signatario = nmSignatario;
	}
	public String getNm_Tecnico() {
		return nm_Tecnico;
	}
	public void setNm_Tecnico(String nmTecnico) {
		nm_Tecnico = nmTecnico;
	}
	public String getNm_Usuario() {
		return nm_Usuario;
	}
	public void setNm_Usuario(String nmUsuario) {
		nm_Usuario = nmUsuario;
	}
	public String getTx_Inicial_OL() {
		return tx_Inicial_OL;
	}
	public void setTx_Inicial_OL(String txInicialOL) {
		tx_Inicial_OL = txInicialOL;
	}
	public String getTx_Final_OL() {
		return tx_Final_OL;
	}
	public void setTx_Final_OL(String txFinalOL) {
		tx_Final_OL = txFinalOL;
	}
	public String getTx_Assinatura1_OL() {
		return tx_Assinatura1_OL;
	}
	public void setTx_Assinatura1_OL(String txAssinatura1OL) {
		tx_Assinatura1_OL = txAssinatura1OL;
	}
	public String getTx_Assinatura2_OL() {
		return tx_Assinatura2_OL;
	}
	public void setTx_Assinatura2_OL(String txAssinatura2OL) {
		tx_Assinatura2_OL = txAssinatura2OL;
	}
	public String getNm_Local() {
		return nm_Local;
	}
	public void setNm_Local(String nmLocal) {
		nm_Local = nmLocal;
	}
	public String getNm_Local_Data() {
		return nm_Local_Data;
	}
	public void setNm_Local_Data(String nmLocalData) {
		nm_Local_Data = nmLocalData;
	}
	public String getTx_Tratamento() {
		return tx_Tratamento;
	}
	public void setTx_Tratamento(String txTratamento) {
		tx_Tratamento = txTratamento;
	}
	public String getNm_Nota_Equipamento() {
		return nm_Nota_Equipamento;
	}
	public void setNm_Nota_Equipamento(String nmNotaEquipamento) {
		nm_Nota_Equipamento = nmNotaEquipamento;
	}
	public long getNr_Percentual_Equipamento() {
		return nr_Percentual_Equipamento;
	}
	public void setNr_Percentual_Equipamento(long nrPercentualEquipamento) {
		nr_Percentual_Equipamento = nrPercentualEquipamento;
	}
	public String getNm_Nota_Pessoal() {
		return nm_Nota_Pessoal;
	}
	public void setNm_Nota_Pessoal(String nmNotaPessoal) {
		nm_Nota_Pessoal = nmNotaPessoal;
	}
	public long getNr_Percentual_Pessoal() {
		return nr_Percentual_Pessoal;
	}
	public void setNr_Percentual_Pessoal(long nrPercentualPessoal) {
		nr_Percentual_Pessoal = nrPercentualPessoal;
	}
	public String getNm_Nota_Geral() {
		return nm_Nota_Geral;
	}
	public void setNm_Nota_Geral(String nmNotaGeral) {
		nm_Nota_Geral = nmNotaGeral;
	}
	public long getNr_Percentual_Geral() {
		return nr_Percentual_Geral;
	}
	public void setNr_Percentual_Geral(long nrPercentualGeral) {
		nr_Percentual_Geral = nrPercentualGeral;
	}

}	