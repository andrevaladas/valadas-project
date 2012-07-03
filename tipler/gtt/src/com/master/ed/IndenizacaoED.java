/*
 * Created on 11/06/2009
 *
 */
package com.master.ed;

/**
 * @author Régis Steigleder
 * @serial Bandas Tipler
 * @serialData 06/2009
 */
public class IndenizacaoED  extends RelatorioBaseED {

	private static final long serialVersionUID = 2036797537626281127L;
	
	long oid_Indenizacao ;
	long oid_Recapagem_Garantida ;
	long oid_Empresa ;
	String dt_Indenizacao ;
	String hr_Indenizacao ;
	String dm_Aceita_Por_Parametro ;
	String dm_Dano_Acidente ;
	String dm_Excesso_Carga ;
	String dm_Baixa_Alta_Pressao ;
	String dm_Fora_Aplicacao_Recomendada ;
	String dm_Montagem_Desmontagem_Inadequada ;
	String dm_Mau_Estado;
	String dm_Tubless_Com_Camara ;
	String dm_Substancia_Quimica ;
	double nr_MM;
	String dm_Perda_Total;
	double nr_Perc_Perda_Total ;
	double nr_Perc_Reforma ;
	double nr_Perc_Carcaca ;
	double vl_Indenizacao ;
	String dm_Aprovacao ;
	String dt_Aprovacao ;
	String hr_Aprovacao ;
	long oid_Usuario_Tecnico ;
	String tx_Aprovacao_Motivo ;
	String dt_Registro_Recapagem ;
	String nr_Dot ;
	long nr_Dias_Perda_Total;
	double nr_Perc_Desgaste;
	String tx_Laudo ;

	// Consulta
	transient double vl_Servico ;
	transient String dm_Rejeicao;
	transient long oid_Empresa_Gambiarra;
	transient String nm_Razao_Social;
	transient String dt_Indenizacao_Inicial;
	transient String dt_Indenizacao_Final;
	transient String dt_Aprovacao_Inicial;
	transient String dt_Aprovacao_Final;
	transient String nm_Aprovacao;
	transient String dm_Mes_Ano;
	transient long oid_Regional;
	transient String nm_Regional;
	transient String dm_Ordenacao;
	//Resumo
	transient long nr_Total_Indenizacoes_Periodo;
	transient double vl_Total_Indenizado_Periodo;
	transient long nr_Total_Indenizacoes_Nao_Inspecionadas;
	transient long nr_Total_Indenizacoes_Inspecionadas;
	transient long nr_Total_Indenizacoes_Aprovadas;
	transient long nr_Total_Indenizacoes_Reprovadas;
	transient long nr_Total_Indenizacoes_Rejeitadas; // Sem uso momentaneamente - Rejeitadas na origem não forampara a CC
	
	// Outras tabelas
	transient long nr_Nota_Fiscal;
	transient String nr_Fogo;
	transient String dt_Registro;
	transient String dt_Validade_Garantia;
	transient String dm_Tipo_Pneu;
	transient String nm_Modelo_Pneu;
	transient String nm_Fabricante_Pneu;
	transient String nm_Banda;
	transient String nm_Concessionaria;
	transient String nm_Usuario_Tecnico;
	
	
	public String getDm_Aceita_Por_Parametro() {
		return dm_Aceita_Por_Parametro;
	}
	public void setDm_Aceita_Por_Parametro(String dm_Aceita_Por_Parametro) {
		this.dm_Aceita_Por_Parametro = dm_Aceita_Por_Parametro;
	}
	public String getDm_Aprovacao() {
		return dm_Aprovacao;
	}
	public void setDm_Aprovacao(String dm_Aprovacao) {
		this.dm_Aprovacao = dm_Aprovacao;
	}
	public String getDm_Baixa_Alta_Pressao() {
		return dm_Baixa_Alta_Pressao;
	}
	public void setDm_Baixa_Alta_Pressao(String dm_Baixa_Alta_Pressao) {
		this.dm_Baixa_Alta_Pressao = dm_Baixa_Alta_Pressao;
	}
	public String getDm_Dano_Acidente() {
		return dm_Dano_Acidente;
	}
	public void setDm_Dano_Acidente(String dm_Dano_Acidente) {
		this.dm_Dano_Acidente = dm_Dano_Acidente;
	}
	public String getDm_Excesso_Carga() {
		return dm_Excesso_Carga;
	}
	public void setDm_Excesso_Carga(String dm_Excesso_Carga) {
		this.dm_Excesso_Carga = dm_Excesso_Carga;
	}
	public String getDm_Fora_Aplicacao_Recomendada() {
		return dm_Fora_Aplicacao_Recomendada;
	}
	public void setDm_Fora_Aplicacao_Recomendada(
			String dm_Fora_Aplicacao_Recomendada) {
		this.dm_Fora_Aplicacao_Recomendada = dm_Fora_Aplicacao_Recomendada;
	}
	public String getDm_Montagem_Desmontagem_Inadequada() {
		return dm_Montagem_Desmontagem_Inadequada;
	}
	public void setDm_Montagem_Desmontagem_Inadequada(
			String dm_Montagem_Desmontagem_Inadequada) {
		this.dm_Montagem_Desmontagem_Inadequada = dm_Montagem_Desmontagem_Inadequada;
	}
	public String getDm_Perda_Total() {
		return dm_Perda_Total;
	}
	public void setDm_Perda_Total(String dm_Perda_Total) {
		this.dm_Perda_Total = dm_Perda_Total;
	}
	public String getDm_Substancia_Quimica() {
		return dm_Substancia_Quimica;
	}
	public void setDm_Substancia_Quimica(String dm_Substancia_Quimica) {
		this.dm_Substancia_Quimica = dm_Substancia_Quimica;
	}
	public String getDm_Tubless_Com_Camara() {
		return dm_Tubless_Com_Camara;
	}
	public void setDm_Tubless_Com_Camara(String dm_Tubless_Com_Camara) {
		this.dm_Tubless_Com_Camara = dm_Tubless_Com_Camara;
	}
	public String getDt_Aprovacao() {
		return dt_Aprovacao;
	}
	public void setDt_Aprovacao(String dt_Aprovacao) {
		this.dt_Aprovacao = dt_Aprovacao;
	}
	public String getDt_Indenizacao() {
		return dt_Indenizacao;
	}
	public void setDt_Indenizacao(String dt_Indenizacao) {
		this.dt_Indenizacao = dt_Indenizacao;
	}
	public double getNr_MM() {
		return nr_MM;
	}
	public void setNr_MM(double nr_MM) {
		this.nr_MM = nr_MM;
	}
	public double getNr_Perc_Carcaca() {
		return nr_Perc_Carcaca;
	}
	public void setNr_Perc_Carcaca(double nr_Perc_Carcaca) {
		this.nr_Perc_Carcaca = nr_Perc_Carcaca;
	}
	public double getNr_Perc_Perda_Total() {
		return nr_Perc_Perda_Total;
	}
	public void setNr_Perc_Perda_Total(double nr_Perc_Perda_Total) {
		this.nr_Perc_Perda_Total = nr_Perc_Perda_Total;
	}
	public double getNr_Perc_Reforma() {
		return nr_Perc_Reforma;
	}
	public void setNr_Perc_Reforma(double nr_Perc_Reforma) {
		this.nr_Perc_Reforma = nr_Perc_Reforma;
	}
	public long getOid_Indenizacao() {
		return oid_Indenizacao;
	}
	public void setOid_Indenizacao(long oid_Indenizacao) {
		this.oid_Indenizacao = oid_Indenizacao;
	}
	public long getOid_Recapagem_Garantida() {
		return oid_Recapagem_Garantida;
	}
	public void setOid_Recapagem_Garantida(long oid_Recapagem_Garantida) {
		this.oid_Recapagem_Garantida = oid_Recapagem_Garantida;
	}
	public long getOid_Usuario_Tecnico() {
		return oid_Usuario_Tecnico;
	}
	public void setOid_Usuario_Tecnico(long oid_Usuario_Tecnico) {
		this.oid_Usuario_Tecnico = oid_Usuario_Tecnico;
	}
	public String getTx_Aprovacao_Motivo() {
		return tx_Aprovacao_Motivo;
	}
	public void setTx_Aprovacao_Motivo(String tx_Aprovacao_Motivo) {
		this.tx_Aprovacao_Motivo = tx_Aprovacao_Motivo;
	}
	public double getVl_Indenizacao() {
		return vl_Indenizacao;
	}
	public void setVl_Indenizacao(double vl_Indenizacao) {
		this.vl_Indenizacao = vl_Indenizacao;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getDt_Registro() {
		return dt_Registro;
	}
	public void setDt_Registro(String dt_Registro) {
		this.dt_Registro = dt_Registro;
	}
	public long getNr_Dias_Perda_Total() {
		return nr_Dias_Perda_Total;
	}
	public void setNr_Dias_Perda_Total(long nr_Dias_Perda_Total) {
		this.nr_Dias_Perda_Total = nr_Dias_Perda_Total;
	}
	public double getVl_Servico() {
		return vl_Servico;
	}
	public void setVl_Servico(double vl_Servico) {
		this.vl_Servico = vl_Servico;
	}
	public double getNr_Perc_Desgaste() {
		return nr_Perc_Desgaste;
	}
	public void setNr_Perc_Desgaste(double nr_Perc_Desgaste) {
		this.nr_Perc_Desgaste = nr_Perc_Desgaste;
	}
	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oid_Empresa) {
		this.oid_Empresa = oid_Empresa;
	}
	public String getDm_Mau_Estado() {
		return dm_Mau_Estado;
	}
	public void setDm_Mau_Estado(String dm_Mau_Estado) {
		this.dm_Mau_Estado = dm_Mau_Estado;
	}
	public String getHr_Indenizacao() {
		return hr_Indenizacao;
	}
	public void setHr_Indenizacao(String hr_Indenizacao) {
		this.hr_Indenizacao = hr_Indenizacao;
	}
	public String getNr_Dot() {
		return nr_Dot;
	}
	public void setNr_Dot(String nr_Dot) {
		this.nr_Dot = nr_Dot;
	}
	public String getDm_Rejeicao() {
		return dm_Rejeicao;
	}
	public void setDm_Rejeicao(String dm_Rejeicao) {
		this.dm_Rejeicao = dm_Rejeicao;
	}
	public String getDm_Tipo_Pneu() {
		return dm_Tipo_Pneu;
	}
	public void setDm_Tipo_Pneu(String dm_Tipo_Pneu) {
		this.dm_Tipo_Pneu = dm_Tipo_Pneu;
	}
	public String getDt_Registro_Recapagem() {
		return dt_Registro_Recapagem;
	}
	public void setDt_Registro_Recapagem(String dt_Registro_Recapagem) {
		this.dt_Registro_Recapagem = dt_Registro_Recapagem;
	}
	public String getNm_Banda() {
		return nm_Banda;
	}
	public void setNm_Banda(String nm_Banda) {
		this.nm_Banda = nm_Banda;
	}
	public String getNm_Fabricante_Pneu() {
		return nm_Fabricante_Pneu;
	}
	public void setNm_Fabricante_Pneu(String nm_Fabricante_Pneu) {
		this.nm_Fabricante_Pneu = nm_Fabricante_Pneu;
	}
	public String getNm_Modelo_Pneu() {
		return nm_Modelo_Pneu;
	}
	public void setNm_Modelo_Pneu(String nm_Modelo_Pneu) {
		this.nm_Modelo_Pneu = nm_Modelo_Pneu;
	}
	public String getNr_Fogo() {
		return nr_Fogo;
	}
	public void setNr_Fogo(String nr_Fogo) {
		this.nr_Fogo = nr_Fogo;
	}
	public long getOid_Empresa_Gambiarra() {
		return oid_Empresa_Gambiarra;
	}
	public void setOid_Empresa_Gambiarra(long oid_Empresa_Gambiarra) {
		this.oid_Empresa_Gambiarra = oid_Empresa_Gambiarra;
	}
	public long getNr_Nota_Fiscal() {
		return nr_Nota_Fiscal;
	}
	public void setNr_Nota_Fiscal(long nr_Nota_Fiscal) {
		this.nr_Nota_Fiscal = nr_Nota_Fiscal;
	}
	public String getDt_Validade_Garantia() {
		return dt_Validade_Garantia;
	}
	public void setDt_Validade_Garantia(String dt_Validade_Garantia) {
		this.dt_Validade_Garantia = dt_Validade_Garantia;
	}
	public String getNm_Razao_Social() {
		return nm_Razao_Social;
	}
	public void setNm_Razao_Social(String nm_Razao_Social) {
		this.nm_Razao_Social = nm_Razao_Social;
	}
	public String getHr_Aprovacao() {
		return hr_Aprovacao;
	}
	public void setHr_Aprovacao(String hr_Aprovacao) {
		this.hr_Aprovacao = hr_Aprovacao;
	}
	public String getDt_Indenizacao_Final() {
		return dt_Indenizacao_Final;
	}
	public void setDt_Indenizacao_Final(String dt_Indenizacao_Final) {
		this.dt_Indenizacao_Final = dt_Indenizacao_Final;
	}
	public String getDt_Indenizacao_Inicial() {
		return dt_Indenizacao_Inicial;
	}
	public void setDt_Indenizacao_Inicial(String dt_Indenizacao_Inicial) {
		this.dt_Indenizacao_Inicial = dt_Indenizacao_Inicial;
	}
	public String getDt_Aprovacao_Final() {
		return dt_Aprovacao_Final;
	}
	public void setDt_Aprovacao_Final(String dt_Aprovacao_Final) {
		this.dt_Aprovacao_Final = dt_Aprovacao_Final;
	}
	public String getDt_Aprovacao_Inicial() {
		return dt_Aprovacao_Inicial;
	}
	public void setDt_Aprovacao_Inicial(String dt_Aprovacao_Inicial) {
		this.dt_Aprovacao_Inicial = dt_Aprovacao_Inicial;
	}
	public String getNm_Concessionaria() {
		return nm_Concessionaria;
	}
	public void setNm_Concessionaria(String nm_Concessionaria) {
		this.nm_Concessionaria = nm_Concessionaria;
	}
	public String getNm_Usuario_Tecnico() {
		return nm_Usuario_Tecnico;
	}
	public void setNm_Usuario_Tecnico(String nm_Usuario_Tecnico) {
		this.nm_Usuario_Tecnico = nm_Usuario_Tecnico;
	}
	public String getNm_Aprovacao() {
		return nm_Aprovacao;
	}
	public void setNm_Aprovacao(String nm_Aprovacao) {
		this.nm_Aprovacao = nm_Aprovacao;
	}
	public String getTx_Laudo() {
		return tx_Laudo;
	}
	public void setTx_Laudo(String tx_Laudo) {
		this.tx_Laudo = tx_Laudo;
	}
	public long getNr_Total_Indenizacoes_Periodo() {
		return nr_Total_Indenizacoes_Periodo;
	}
	public void setNr_Total_Indenizacoes_Periodo(long nrTotalIndenizacoesPeriodo) {
		nr_Total_Indenizacoes_Periodo = nrTotalIndenizacoesPeriodo;
	}
	public long getNr_Total_Indenizacoes_Nao_Inspecionadas() {
		return nr_Total_Indenizacoes_Nao_Inspecionadas;
	}
	public void setNr_Total_Indenizacoes_Nao_Inspecionadas(
			long nrTotalIndenizacoesNaoInspecionadas) {
		nr_Total_Indenizacoes_Nao_Inspecionadas = nrTotalIndenizacoesNaoInspecionadas;
	}
	public long getNr_Total_Indenizacoes_Inspecionadas() {
		return nr_Total_Indenizacoes_Inspecionadas;
	}
	public void setNr_Total_Indenizacoes_Inspecionadas(
			long nrTotalIndenizacoesInspecionadas) {
		nr_Total_Indenizacoes_Inspecionadas = nrTotalIndenizacoesInspecionadas;
	}
	public long getNr_Total_Indenizacoes_Aprovadas() {
		return nr_Total_Indenizacoes_Aprovadas;
	}
	public void setNr_Total_Indenizacoes_Aprovadas(long nrTotalIndenizacoesAprovadas) {
		nr_Total_Indenizacoes_Aprovadas = nrTotalIndenizacoesAprovadas;
	}
	public long getNr_Total_Indenizacoes_Rejeitadas() {
		return nr_Total_Indenizacoes_Rejeitadas;
	}
	public void setNr_Total_Indenizacoes_Rejeitadas(
			long nrTotalIndenizacoesRejeitadas) {
		nr_Total_Indenizacoes_Rejeitadas = nrTotalIndenizacoesRejeitadas;
	}
	public long getNr_Total_Indenizacoes_Reprovadas() {
		return nr_Total_Indenizacoes_Reprovadas;
	}
	public void setNr_Total_Indenizacoes_Reprovadas(
			long nrTotalIndenizacoesReprovadas) {
		nr_Total_Indenizacoes_Reprovadas = nrTotalIndenizacoesReprovadas;
	}
	public double getVl_Total_Indenizado_Periodo() {
		return vl_Total_Indenizado_Periodo;
	}
	public void setVl_Total_Indenizado_Periodo(double vlTotalIndenizadoPeriodo) {
		vl_Total_Indenizado_Periodo = vlTotalIndenizadoPeriodo;
	}
	public long getOid_Regional() {
		return oid_Regional;
	}
	public void setOid_Regional(long oidRegional) {
		oid_Regional = oidRegional;
	}
	public String getNm_Regional() {
		return nm_Regional;
	}
	public void setNm_Regional(String nmRegional) {
		nm_Regional = nmRegional;
	}
	public String getDm_Mes_Ano() {
		return dm_Mes_Ano;
	}
	public void setDm_Mes_Ano(String dmMesAno) {
		dm_Mes_Ano = dmMesAno;
	}
	public String getDm_Ordenacao() {
		return dm_Ordenacao;
	}
	public void setDm_Ordenacao(String dmOrdenacao) {
		dm_Ordenacao = dmOrdenacao;
	}


}	