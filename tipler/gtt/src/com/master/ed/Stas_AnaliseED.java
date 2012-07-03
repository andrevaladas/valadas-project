/*
 * Created on 14/05/2012
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STAS - Análises
 * @serialData - 05/2012
 */
public class Stas_AnaliseED  extends RelatorioBaseED {

	private static final long serialVersionUID = -5168857540713224341L;
	
	private long oid_Analise;
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
	private int nr_Quantidade_Pneus;
	private double nr_Mm_Calculo_Perda;
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
	private transient String oid_Analise_In;
	
	// Campos para os relatórios de análise
	private int nr_Quantidade_Total;
	private double nr_Mm_Perdido_Total;
	private double vl_Perdido_Total;
	
	private int nr_Quantidade_Novo;
	private double nr_Mm_Perdido_Novo;
	private double vl_Perdido_Novo;
	
	private int nr_Quantidade_R1;
	private double nr_Mm_Perdido_R1;
	private double vl_Perdido_R1;
	
	private int nr_Quantidade_R2;
	private double nr_Mm_Perdido_R2;
	private double vl_Perdido_R2;
	private int nr_Quantidade_R3;
	private double nr_Mm_Perdido_R3;
	private double vl_Perdido_R3;
	private int nr_Quantidade_R4;
	private double nr_Mm_Perdido_R4;
	private double vl_Perdido_R4;
	private int nr_Quantidade_R5;
	private double nr_Mm_Perdido_R5;
	private double vl_Perdido_R5;
	
	// Campos com quebra para o Laszlo
	private transient String tx_Inicial_OL;
	private transient String tx_Final_OL;
	private transient String tx_Assinatura1_OL;
	private transient String tx_Assinatura2_OL;
	
	private transient String tx_Tratamento;

	// Resumo
	private String dm_Tabela_Interna;
	
	public long getOid_Analise() {
		return oid_Analise;
	}
	public void setOid_Inspecao(long oidAnalise) {
		oid_Analise = oidAnalise;
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
	public void setOid_Analise(long oidAnalise) {
		oid_Analise = oidAnalise;
	}
	public int getNr_Quantidade_Pneus() {
		return nr_Quantidade_Pneus;
	}
	public void setNr_Quantidade_Pneus(int nrQuantidadePneus) {
		nr_Quantidade_Pneus = nrQuantidadePneus;
	}
	public String getOid_Analise_In() {
		return oid_Analise_In;
	}
	public void setOid_Analise_In(String oidAnaliseIn) {
		oid_Analise_In = oidAnaliseIn;
	}
	public double getNr_Mm_Calculo_Perda() {
		return nr_Mm_Calculo_Perda;
	}
	public void setNr_Mm_Calculo_Perda(double nrMmCalculoPerda) {
		nr_Mm_Calculo_Perda = nrMmCalculoPerda;
	}
	public String getDm_Tabela_Interna() {
		return dm_Tabela_Interna;
	}
	public void setDm_Tabela_Interna(String dmTabelaInterna) {
		dm_Tabela_Interna = dmTabelaInterna;
	}
	public int getNr_Quantidade_Total() {
		return nr_Quantidade_Total;
	}
	public void setNr_Quantidade_Total(int nrQuantidadeTotal) {
		nr_Quantidade_Total = nrQuantidadeTotal;
	}
	public double getNr_Mm_Perdido_Total() {
		return nr_Mm_Perdido_Total;
	}
	public void setNr_Mm_Perdido_Total(double nrMmPerdidoTotal) {
		nr_Mm_Perdido_Total = nrMmPerdidoTotal;
	}
	public double getVl_Perdido_Total() {
		return vl_Perdido_Total;
	}
	public void setVl_Perdido_Total(double vlPerdidoTotal) {
		vl_Perdido_Total = vlPerdidoTotal;
	}
	public int getNr_Quantidade_R1() {
		return nr_Quantidade_R1;
	}
	public void setNr_Quantidade_R1(int nrQuantidadeR1) {
		nr_Quantidade_R1 = nrQuantidadeR1;
	}
	public double getNr_Mm_Perdido_R1() {
		return nr_Mm_Perdido_R1;
	}
	public void setNr_Mm_Perdido_R1(double nrMmPerdidoR1) {
		nr_Mm_Perdido_R1 = nrMmPerdidoR1;
	}
	public double getVl_Perdido_R1() {
		return vl_Perdido_R1;
	}
	public void setVl_Perdido_R1(double vlPerdidoR1) {
		vl_Perdido_R1 = vlPerdidoR1;
	}
	public int getNr_Quantidade_R2() {
		return nr_Quantidade_R2;
	}
	public void setNr_Quantidade_R2(int nrQuantidadeR2) {
		nr_Quantidade_R2 = nrQuantidadeR2;
	}
	public double getNr_Mm_Perdido_R2() {
		return nr_Mm_Perdido_R2;
	}
	public void setNr_Mm_Perdido_R2(double nrMmPerdidoR2) {
		nr_Mm_Perdido_R2 = nrMmPerdidoR2;
	}
	public double getVl_Perdido_R2() {
		return vl_Perdido_R2;
	}
	public void setVl_Perdido_R2(double vlPerdidoR2) {
		vl_Perdido_R2 = vlPerdidoR2;
	}
	public int getNr_Quantidade_R3() {
		return nr_Quantidade_R3;
	}
	public void setNr_Quantidade_R3(int nrQuantidadeR3) {
		nr_Quantidade_R3 = nrQuantidadeR3;
	}
	public double getNr_Mm_Perdido_R3() {
		return nr_Mm_Perdido_R3;
	}
	public void setNr_Mm_Perdido_R3(double nrMmPerdidoR3) {
		nr_Mm_Perdido_R3 = nrMmPerdidoR3;
	}
	public double getVl_Perdido_R3() {
		return vl_Perdido_R3;
	}
	public void setVl_Perdido_R3(double vlPerdidoR3) {
		vl_Perdido_R3 = vlPerdidoR3;
	}
	public int getNr_Quantidade_R4() {
		return nr_Quantidade_R4;
	}
	public void setNr_Quantidade_R4(int nrQuantidadeR4) {
		nr_Quantidade_R4 = nrQuantidadeR4;
	}
	public double getNr_Mm_Perdido_R4() {
		return nr_Mm_Perdido_R4;
	}
	public void setNr_Mm_Perdido_R4(double nrMmPerdidoR4) {
		nr_Mm_Perdido_R4 = nrMmPerdidoR4;
	}
	public double getVl_Perdido_R4() {
		return vl_Perdido_R4;
	}
	public void setVl_Perdido_R4(double vlPerdidoR4) {
		vl_Perdido_R4 = vlPerdidoR4;
	}
	public int getNr_Quantidade_R5() {
		return nr_Quantidade_R5;
	}
	public void setNr_Quantidade_R5(int nrQuantidadeR5) {
		nr_Quantidade_R5 = nrQuantidadeR5;
	}
	public double getNr_Mm_Perdido_R5() {
		return nr_Mm_Perdido_R5;
	}
	public void setNr_Mm_Perdido_R5(double nrMmPerdidoR5) {
		nr_Mm_Perdido_R5 = nrMmPerdidoR5;
	}
	public double getVl_Perdido_R5() {
		return vl_Perdido_R5;
	}
	public void setVl_Perdido_R5(double vlPerdidoR5) {
		vl_Perdido_R5 = vlPerdidoR5;
	}
	public int getNr_Quantidade_Novo() {
		return nr_Quantidade_Novo;
	}
	public void setNr_Quantidade_Novo(int nrQuantidadeNovo) {
		nr_Quantidade_Novo = nrQuantidadeNovo;
	}
	public double getNr_Mm_Perdido_Novo() {
		return nr_Mm_Perdido_Novo;
	}
	public void setNr_Mm_Perdido_Novo(double nrMmPerdidoNovo) {
		nr_Mm_Perdido_Novo = nrMmPerdidoNovo;
	}
	public double getVl_Perdido_Novo() {
		return vl_Perdido_Novo;
	}
	public void setVl_Perdido_Novo(double vlPerdidoNovo) {
		vl_Perdido_Novo = vlPerdidoNovo;
	}

}	