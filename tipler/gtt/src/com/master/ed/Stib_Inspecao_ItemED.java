/*
 * Created on 29/09/2007
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial STIB - Itens inspecionados
 * @serialData 04/2012
 */
public class Stib_Inspecao_ItemED  extends RelatorioBaseED {
	
	private static final long serialVersionUID = 5456197355288739528L;
	
	private long oid_Inspecao_Item;
	private long oid_Inspecao;
	private int nr_Ordem;
	private String dm_Tipo;
	private String nm_Item_Inspecionado;
	private boolean dm_Situacao;
	private String nm_Situacao;
	
	// Campos de outras tabelas
	private long oid_Concessionaria;
	private long oid_Usuario;
	private long oid_Cliente;
	private String dt_Inicio;
	private String dt_Fim;
	private String tx_Inicial;
	private String tx_Final;
	private String tx_Assinatura1;
	private String tx_Assinatura2;
	
	
	public Stib_Inspecao_ItemED () {
	}
	
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
	public long getOid_Inspecao_Item() {
		return oid_Inspecao_Item;
	}
	public void setOid_Inspecao_Item(long oidInspecaoItem) {
		oid_Inspecao_Item = oidInspecaoItem;
	}
	public int getNr_Ordem() {
		return nr_Ordem;
	}
	public void setNr_Ordem(int nrOrdem) {
		nr_Ordem = nrOrdem;
	}
	public String getDm_Tipo() {
		return dm_Tipo;
	}
	public void setDm_Tipo(String dmTipo) {
		dm_Tipo = dmTipo;
	}
	public String getNm_Item_Inspecionado() {
		return nm_Item_Inspecionado;
	}
	public void setNm_Item_Inspecionado(String nmItemInspecionado) {
		nm_Item_Inspecionado = nmItemInspecionado;
	}
	public boolean isDm_Situacao() {
		return dm_Situacao;
	}
	public void setDm_Situacao(boolean dmSituacao) {
		dm_Situacao = dmSituacao;
	}
	public String getNm_Situacao() {
		if("E".equals(this.getDm_Lingua()))
			nm_Situacao=this.dm_Situacao?"CONFORME":"NÃO CONFORME";
		else
			nm_Situacao=this.dm_Situacao?"CONFORME":"NO CONFORME";
		return nm_Situacao;
	}
	public void setNm_Situacao(String nmSituacao) {
		nm_Situacao = nmSituacao;
	}

}	