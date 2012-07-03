/*
 * Created on 11/06/2009
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Bandas Tipler
 * @serialData 07/2009
 */
public class Movimento_Conta_CorrenteED extends RelatorioBaseED {

	private static final long serialVersionUID = -4404801785387669925L;
	
	long oid_Movimento_Conta_Corrente ;
	long oid_Indenizacao ;
	long oid_Concessionaria ;
	long oid_Usuario_Desbloqueio ;
	String dt_Movimento_Conta_Corrente ;
	String dt_Desbloqueio ;
	String dm_Debito_Credito ;
	String dm_Tipo_Movimento ; // I-indenizacao a cliente; D-desconto em fatura; N-negativa de indenização, A-Ajuste
	String dm_Bloqueado ;
	String tx_Bloqueio ;
	String tx_Descricao ;
	long nr_Fatura_Tipler;
	long oid_Movimento_Conta_Corrente_Debito ; // é o oid_Movimento_Conta_Corrente de Débito para um dm_Debito_Credito=C após o desconto da fatura ou negativa de indenização 
	double vl_Movimento_Conta_Corrente ;
	
	// Consulta
	transient String dm_Descontado;
	transient String dt_Movimento_Conta_Corrente_Inicial;	
	transient String dt_Movimento_Conta_Corrente_Final;
	transient double vl_Saldo ;
	transient String nm_Tipo_Movimento;
	transient String nm_Concessionaria;
	transient String nm_Regional;
	transient String dt_Aprovacao;
	transient String dm_Aprovacao;
	transient String nm_Usuario_Tecnico;
	transient String oids;
	transient long oid_Empresa;
	transient long oid_Regional;
	transient String dm_Mes_Ano;
	
	public String getDm_Bloqueado() {
		return dm_Bloqueado;
	}
	public void setDm_Bloqueado(String dm_Bloqueado) {
		this.dm_Bloqueado = dm_Bloqueado;
	}
	public String getDm_Debito_Credito() {
		return dm_Debito_Credito;
	}
	public void setDm_Debito_Credito(String dm_Debito_Credito) {
		this.dm_Debito_Credito = dm_Debito_Credito;
	}
	public String getDt_Movimento_Conta_Corrente() {
		return dt_Movimento_Conta_Corrente;
	}
	public void setDt_Movimento_Conta_Corrente(String dt_Movimento_Conta_Corrente) {
		this.dt_Movimento_Conta_Corrente = dt_Movimento_Conta_Corrente;
	}
	public long getNr_Fatura_Tipler() {
		return nr_Fatura_Tipler;
	}
	public void setNr_Fatura_Tipler(long nr_Fatura_Tipler) {
		this.nr_Fatura_Tipler = nr_Fatura_Tipler;
	}
	public long getOid_Concessionaria() {
		return oid_Concessionaria;
	}
	public void setOid_Concessionaria(long oid_Concessionaria) {
		this.oid_Concessionaria = oid_Concessionaria;
	}
	public long getOid_Indenizacao() {
		return oid_Indenizacao;
	}
	public void setOid_Indenizacao(long oid_Indenizacao) {
		this.oid_Indenizacao = oid_Indenizacao;
	}
	public long getOid_Movimento_Conta_Corrente() {
		return oid_Movimento_Conta_Corrente;
	}
	public void setOid_Movimento_Conta_Corrente(long oid_Movimento_Conta_Corrente) {
		this.oid_Movimento_Conta_Corrente = oid_Movimento_Conta_Corrente;
	}
	public String getTx_Bloqueio() {
		return tx_Bloqueio;
	}
	public void setTx_Bloqueio(String tx_Bloqueio) {
		this.tx_Bloqueio = tx_Bloqueio;
	}
	public String getTx_Descricao() {
		return tx_Descricao;
	}
	public void setTx_Descricao(String tx_Descricao) {
		this.tx_Descricao = tx_Descricao;
	}
	public double getVl_Movimento_Conta_Corrente() {
		return vl_Movimento_Conta_Corrente;
	}
	public void setVl_Movimento_Conta_Corrente(double vl_Movimento_Conta_Corrente) {
		this.vl_Movimento_Conta_Corrente = vl_Movimento_Conta_Corrente;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public long getOid_Usuario_Desbloqueio() {
		return oid_Usuario_Desbloqueio;
	}
	public void setOid_Usuario_Desbloqueio(long oid_Usuario_Desbloqueio) {
		this.oid_Usuario_Desbloqueio = oid_Usuario_Desbloqueio;
	}
	public String getDt_Desbloqueio() {
		return dt_Desbloqueio;
	}
	public void setDt_Desbloqueio(String dt_Desbloqueio) {
		this.dt_Desbloqueio = dt_Desbloqueio;
	}
	public String getDm_Tipo_Movimento() {
		return dm_Tipo_Movimento;
	}
	public void setDm_Tipo_Movimento(String dm_Tipo_Movimento) {
		this.dm_Tipo_Movimento = dm_Tipo_Movimento;
	}
	public long getOid_Movimento_Conta_Corrente_Debito() {
		return oid_Movimento_Conta_Corrente_Debito;
	}
	public void setOid_Movimento_Conta_Corrente_Debito(
			long oid_Movimento_Conta_Corrente_Debito) {
		this.oid_Movimento_Conta_Corrente_Debito = oid_Movimento_Conta_Corrente_Debito;
	}
	public String getDm_Descontado() {
		return dm_Descontado;
	}
	public void setDm_Descontado(String dm_Descontado) {
		this.dm_Descontado = dm_Descontado;
	}
	public String getDt_Movimento_Conta_Corrente_Final() {
		return dt_Movimento_Conta_Corrente_Final;
	}
	public void setDt_Movimento_Conta_Corrente_Final(
			String dt_Movimento_Conta_Corrente_Final) {
		this.dt_Movimento_Conta_Corrente_Final = dt_Movimento_Conta_Corrente_Final;
	}
	public String getDt_Movimento_Conta_Corrente_Inicial() {
		return dt_Movimento_Conta_Corrente_Inicial;
	}
	public void setDt_Movimento_Conta_Corrente_Inicial(
			String dt_Movimento_Conta_Corrente_Inicial) {
		this.dt_Movimento_Conta_Corrente_Inicial = dt_Movimento_Conta_Corrente_Inicial;
	}
	public String getNm_Tipo_Movimento() {
			if ("I".equals(this.dm_Tipo_Movimento))
				nm_Tipo_Movimento="INDENIZAÇÃO";
			else if ("N".equals(this.dm_Tipo_Movimento))
				nm_Tipo_Movimento="NEGATIVA";
			else if ("D".equals(this.dm_Tipo_Movimento))
				nm_Tipo_Movimento="DESCONTO";
			else if ("A".equals(this.dm_Tipo_Movimento))
				nm_Tipo_Movimento="AJUSTE";
		return nm_Tipo_Movimento;
	}
	public void setNm_Tipo_Movimento(String nm_Tipo_Movimento) {
		this.nm_Tipo_Movimento = nm_Tipo_Movimento;
	}
	public double getVl_Saldo() {
		return vl_Saldo;
	}
	public void setVl_Saldo(double vl_Saldo) {
		this.vl_Saldo = vl_Saldo;
	}
	public String getNm_Concessionaria() {
		return nm_Concessionaria;
	}
	public void setNm_Concessionaria(String nm_Concessionaria) {
		this.nm_Concessionaria = nm_Concessionaria;
	}
	public String getDm_Aprovacao() {
		return dm_Aprovacao;
	}
	public void setDm_Aprovacao(String dm_Aprovacao) {
		this.dm_Aprovacao = dm_Aprovacao;
	}
	public String getDt_Aprovacao() {
		return dt_Aprovacao;
	}
	public void setDt_Aprovacao(String dt_Aprovacao) {
		this.dt_Aprovacao = dt_Aprovacao;
	}
	public String getNm_Usuario_Tecnico() {
		return nm_Usuario_Tecnico;
	}
	public void setNm_Usuario_Tecnico(String nm_Usuario_Tecnico) {
		this.nm_Usuario_Tecnico = nm_Usuario_Tecnico;
	}
	public String getOids() {
		return oids;
	}
	public void setOids(String oids) {
		this.oids = oids;
	}
	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oid_Empresa) {
		this.oid_Empresa = oid_Empresa;
	}
	public String getDm_Mes_Ano() {
		return dm_Mes_Ano;
	}
	public void setDm_Mes_Ano(String dmMesAno) {
		dm_Mes_Ano = dmMesAno;
	}
	public String getNm_Regional() {
		return nm_Regional;
	}
	public void setNm_Regional(String nmRegional) {
		nm_Regional = nmRegional;
	}
	public long getOid_Regional() {
		return oid_Regional;
	}
	public void setOid_Regional(long oidRegional) {
		oid_Regional = oidRegional;
	}
	
}	