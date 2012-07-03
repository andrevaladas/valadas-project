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
public class Recapagem_GarantidaED  extends RelatorioBaseED {

	private static final long serialVersionUID = -3367600580140777716L;
	
	long oid_Recapagem_Garantida ;
	long oid_Empresa ;
	long oid_Usuario ;
	long oid_Cliente ;
	long oid_Concessionaria ;
	long oid_Banda ;
	long oid_Fabricante_Pneu ;
	long oid_Modelo_Pneu ;
	String dt_Registro ;
	String hr_Registro ;
	long nr_Nota_Fiscal ;
	double vl_Servico ;
	String dm_Reparo ;
	long nr_Quantidade_Reparo ;
	String nr_Fogo ;
	String dm_Tipo_Pneu ;
	String nr_Dot ;
	long nr_Indice_Carga ;
	long nr_Vida ;
	String dm_Dano_Acidente ;
	String dm_Excesso_Carga ;
	String dm_Baixa_Alta_Pressao ;
	String dm_Fora_Aplicacao_Recomendada ;
	String dm_Montagem_Desmontagem_Inadequada ;
	String dm_Mau_estado ;
	String dm_Tubless_Com_Camara ;
	String dm_Substancia_Quimica ;
	String dt_Validade_Garantia ;
	long nr_Certificado ;
	String dt_Emissao_Certificado ;
	String hr_Emissao_Certificado ;
	long oid_Banda_Dimensao ;
	
	String dm_Substituida ; // N=Original , S=Substituida
	long oid_Recapagem_Garantida_Substituta ; // Se > 0 quando dm_Substituida=N é o oid da substituida, quando dm_Substituida=S é o oid da substituta
	
	// Consulta
	transient String dt_Registro_Inicial;
	transient String dt_Registro_Final;
	transient String nr_Cnpj_Cliente;
	transient String dm_Garantia_Expirada;
	transient double nr_MM;
	transient String dm_Perda_Total;
	transient String dm_Indenizada;
	transient String dm_Tela_Consulta; // Usado quando a consulta de garantias for feita pela tela gtt311 da Tipler
	transient long oid_Empresa_Gambiarra;
	transient String dm_Mes_Ano;
	
	// Outras tabelas
	transient String nm_Usuario;
	transient String nm_Razao_Social_Cliente;
	transient String nm_Razao_Social_Concessionaria;
	transient String nm_Banda;
	transient String nm_Fabricante_Pneu;
	transient String nm_Modelo_Pneu;
	transient String dt_Indenizacao;
	transient String nm_Cidade;

	// Calculado
	transient String nm_Vida;
	transient String tx_Indenizacao;
	
	// Para impressão
	transient String tx_Laudo;
	
	// Relatório de resumo de Operações de Concessionárias
	transient long oid_Regional;
	transient String nm_Regional;
	transient String nm_Concessionaria;
	transient long nr_Total_Recapagens_Garantidas;
	//Resumo de indenizacoes
	transient long nr_Total_Indenizacoes_Periodo;
	transient double vl_Total_Indenizado_Periodo;
	transient long nr_Total_Indenizacoes_Nao_Inspecionadas;
	transient long nr_Total_Indenizacoes_Inspecionadas;
	transient long nr_Total_Indenizacoes_Aprovadas;
	transient long nr_Total_Indenizacoes_Reprovadas;
	transient long nr_Total_Indenizacoes_Rejeitadas; // Sem uso momentaneamente - Rejeitadas na origem não forampara a CC
	transient double nr_Perc_Faturamento;
	//
	transient String dm_Esconde_Preco;
	
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
	public String getDm_Mau_estado() {
		return dm_Mau_estado;
	}
	public void setDm_Mau_estado(String dm_Mau_estado) {
		this.dm_Mau_estado = dm_Mau_estado;
	}
	public String getDm_Montagem_Desmontagem_Inadequada() {
		return dm_Montagem_Desmontagem_Inadequada;
	}
	public void setDm_Montagem_Desmontagem_Inadequada(
			String dm_Montagem_Desmontagem_Inadequada) {
		this.dm_Montagem_Desmontagem_Inadequada = dm_Montagem_Desmontagem_Inadequada;
	}
	public String getDm_Reparo() {
		return dm_Reparo;
	}
	public void setDm_Reparo(String dm_Reparo) {
		this.dm_Reparo = dm_Reparo;
	}
	public String getDm_Substancia_Quimica() {
		return dm_Substancia_Quimica;
	}
	public void setDm_Substancia_Quimica(String dm_Substancia_Quimica) {
		this.dm_Substancia_Quimica = dm_Substancia_Quimica;
	}
	public String getDm_Tipo_Pneu() {
		return dm_Tipo_Pneu;
	}
	public void setDm_Tipo_Pneu(String dm_Tipo_Pneu) {
		this.dm_Tipo_Pneu = dm_Tipo_Pneu;
	}
	public String getDm_Tubless_Com_Camara() {
		return dm_Tubless_Com_Camara;
	}
	public void setDm_Tubless_Com_Camara(String dm_Tubless_Com_Camara) {
		this.dm_Tubless_Com_Camara = dm_Tubless_Com_Camara;
	}
	public String getDt_Registro() {
		return dt_Registro;
	}
	public void setDt_Registro(String dt_Registro) {
		this.dt_Registro = dt_Registro;
	}
	public String getHr_Registro() {
		return hr_Registro;
	}
	public void setHr_Registro(String hr_Registro) {
		this.hr_Registro = hr_Registro;
	}
	public long getNr_Certificado() {
		return nr_Certificado;
	}
	public void setNr_Certificado(long nr_Certificado) {
		this.nr_Certificado = nr_Certificado;
	}
	public String getNr_Dot() {
		return nr_Dot;
	}
	public void setNr_Dot(String nr_Dot) {
		this.nr_Dot = nr_Dot;
	}
	public String getNr_Fogo() {
		return nr_Fogo;
	}
	public void setNr_Fogo(String nr_Fogo) {
		this.nr_Fogo = nr_Fogo;
	}
	public long getNr_Indice_Carga() {
		return nr_Indice_Carga;
	}
	public void setNr_Indice_Carga(long nr_Indice_Carga) {
		this.nr_Indice_Carga = nr_Indice_Carga;
	}
	public long getNr_Nota_Fiscal() {
		return nr_Nota_Fiscal;
	}
	public void setNr_Nota_Fiscal(long nr_Nota_Fiscal) {
		this.nr_Nota_Fiscal = nr_Nota_Fiscal;
	}
	public long getNr_Quantidade_Reparo() {
		return nr_Quantidade_Reparo;
	}
	public void setNr_Quantidade_Reparo(long nr_Quantidade_Reparo) {
		this.nr_Quantidade_Reparo = nr_Quantidade_Reparo;
	}
	public long getNr_Vida() {
		return nr_Vida;
	}
	public void setNr_Vida(long nr_Vida) {
		this.nr_Vida = nr_Vida;
	}
	public long getOid_Banda() {
		return oid_Banda;
	}
	public void setOid_Banda(long oid_Banda) {
		this.oid_Banda = oid_Banda;
	}
	public long getOid_Cliente() {
		return oid_Cliente;
	}
	public void setOid_Cliente(long oid_Cliente) {
		this.oid_Cliente = oid_Cliente;
	}
	public long getOid_Concessionaria() {
		return oid_Concessionaria;
	}
	public void setOid_Concessionaria(long oid_Concessionaria) {
		this.oid_Concessionaria = oid_Concessionaria;
	}
	public long getOid_Modelo_Pneu() {
		return oid_Modelo_Pneu;
	}
	public void setOid_Modelo_Pneu(long oid_Modelo_Pneu) {
		this.oid_Modelo_Pneu = oid_Modelo_Pneu;
	}
	public long getOid_Recapagem_Garantida() {
		return oid_Recapagem_Garantida;
	}
	public void setOid_Recapagem_Garantida(long oid_Recapagem_Garantida) {
		this.oid_Recapagem_Garantida = oid_Recapagem_Garantida;
	}
	public long getOid_Usuario() {
		return oid_Usuario;
	}
	public void setOid_Usuario(long oid_Usuario) {
		this.oid_Usuario = oid_Usuario;
	}
	public double getVl_Servico() {
		return vl_Servico;
	}
	public void setVl_Servico(double vl_Servico) {
		this.vl_Servico = vl_Servico;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getDt_Registro_Final() {
		return dt_Registro_Final;
	}
	public void setDt_Registro_Final(String dt_Registro_Final) {
		this.dt_Registro_Final = dt_Registro_Final;
	}
	public String getDt_Registro_Inicial() {
		return dt_Registro_Inicial;
	}
	public void setDt_Registro_Inicial(String dt_Registro_Inicial) {
		this.dt_Registro_Inicial = dt_Registro_Inicial;
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
	public String getNm_Razao_Social_Cliente() {
		return nm_Razao_Social_Cliente;
	}
	public void setNm_Razao_Social_Cliente(String nm_Razao_Social_Cliente) {
		this.nm_Razao_Social_Cliente = nm_Razao_Social_Cliente;
	}
	public String getNm_Razao_Social_Concessionaria() {
		return nm_Razao_Social_Concessionaria;
	}
	public void setNm_Razao_Social_Concessionaria(
			String nm_Razao_Social_Concessionaria) {
		this.nm_Razao_Social_Concessionaria = nm_Razao_Social_Concessionaria;
	}
	public String getNm_Usuario() {
		return nm_Usuario;
	}
	public void setNm_Usuario(String nm_Usuario) {
		this.nm_Usuario = nm_Usuario;
	}
	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oid_Empresa) {
		this.oid_Empresa = oid_Empresa;
	}
	public String getNm_Vida() {
		nm_Vida = this.nr_Vida + "º REC";
		return nm_Vida;
	}
	public void setNm_Vida(String nm_Vida) {
		this.nm_Vida = nm_Vida;
	}
	public long getOid_Fabricante_Pneu() {
		return oid_Fabricante_Pneu;
	}
	public void setOid_Fabricante_Pneu(long oid_Fabricante_Pneu) {
		this.oid_Fabricante_Pneu = oid_Fabricante_Pneu;
	}
	public String getDm_Substituida() {
		return dm_Substituida;
	}
	public void setDm_Substituida(String dm_Substituida) {
		this.dm_Substituida = dm_Substituida;
	}
	public long getOid_Recapagem_Garantida_Substituta() {
		return oid_Recapagem_Garantida_Substituta;
	}
	public void setOid_Recapagem_Garantida_Substituta(
			long oid_Recapagem_Garantida_Substituta) {
		this.oid_Recapagem_Garantida_Substituta = oid_Recapagem_Garantida_Substituta;
	}
	public String getNr_Cnpj_Cliente() {
		return nr_Cnpj_Cliente;
	}
	public void setNr_Cnpj_Cliente(String nr_Cnpj_Cliente) {
		this.nr_Cnpj_Cliente = nr_Cnpj_Cliente;
	}
	public String getDm_Garantia_Expirada() {
		return dm_Garantia_Expirada;
	}
	public void setDm_Garantia_Expirada(String dm_Garantia_Expirada) {
		this.dm_Garantia_Expirada = dm_Garantia_Expirada;
	}
	public double getNr_MM() {
		return nr_MM;
	}
	public void setNr_MM(double nr_MM) {
		this.nr_MM = nr_MM;
	}
	public String getDm_Perda_Total() {
		return dm_Perda_Total;
	}
	public void setDm_Perda_Total(String dm_Perda_Total) {
		this.dm_Perda_Total = dm_Perda_Total;
	}
	public String getDt_Validade_Garantia() {
		return dt_Validade_Garantia;
	}
	public void setDt_Validade_Garantia(String dt_Validade_Garantia) {
		this.dt_Validade_Garantia = dt_Validade_Garantia;
	}
	public String getDm_Indenizada() {
		return dm_Indenizada;
	}
	public void setDm_Indenizada(String dm_Indenizada) {
		this.dm_Indenizada = dm_Indenizada;
	}
	public String getTx_Indenizacao() {
		return tx_Indenizacao;
	}
	public void setTx_Indenizacao(String tx_Indenizacao) {
		this.tx_Indenizacao = tx_Indenizacao;
	}
	public String getDt_Indenizacao() {
		return dt_Indenizacao;
	}
	public void setDt_Indenizacao(String dt_Indenizacao) {
		this.dt_Indenizacao = dt_Indenizacao;
	}
	public String getNm_Cidade() {
		return nm_Cidade;
	}
	public void setNm_Cidade(String nm_Cidade) {
		this.nm_Cidade = nm_Cidade;
	}
	public String getTx_Laudo() {
		return tx_Laudo;
	}
	public void setTx_Laudo(String tx_Laudo) {
		this.tx_Laudo = tx_Laudo;
	}
	public long getOid_Empresa_Gambiarra() {
		return oid_Empresa_Gambiarra;
	}
	public void setOid_Empresa_Gambiarra(long oid_Empresa_Gambiarra) {
		this.oid_Empresa_Gambiarra = oid_Empresa_Gambiarra;
	}
	public String getDm_Tela_Consulta() {
		return dm_Tela_Consulta;
	}
	public void setDm_Tela_Consulta(String dm_Tela_Consulta) {
		this.dm_Tela_Consulta = dm_Tela_Consulta;
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
	public String getNm_Concessionaria() {
		return nm_Concessionaria;
	}
	public void setNm_Concessionaria(String nmConcessionaria) {
		nm_Concessionaria = nmConcessionaria;
	}
	public long getNr_Total_Recapagens_Garantidas() {
		return nr_Total_Recapagens_Garantidas;
	}
	public void setNr_Total_Recapagens_Garantidas(int nrTotalRecapagensGarantidas) {
		nr_Total_Recapagens_Garantidas = nrTotalRecapagensGarantidas;
	}
	public double getNr_Perc_Faturamento() {
		return nr_Perc_Faturamento;
	}
	public void setNr_Perc_Faturamento(double nrPercFaturamento) {
		nr_Perc_Faturamento = nrPercFaturamento;
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
	public String getDm_Mes_Ano() {
		return dm_Mes_Ano;
	}
	public void setDm_Mes_Ano(String dmMesAno) {
		dm_Mes_Ano = dmMesAno;
	}
	public void setNr_Total_Recapagens_Garantidas(long nrTotalRecapagensGarantidas) {
		nr_Total_Recapagens_Garantidas = nrTotalRecapagensGarantidas;
	}
	public String getDt_Emissao_Certificado() {
		return dt_Emissao_Certificado;
	}
	public void setDt_Emissao_Certificado(String dtEmissaoCertificado) {
		dt_Emissao_Certificado = dtEmissaoCertificado;
	}
	public String getHr_Emissao_Certificado() {
		return hr_Emissao_Certificado;
	}
	public void setHr_Emissao_Certificado(String hrEmissaoCertificado) {
		hr_Emissao_Certificado = hrEmissaoCertificado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDm_Esconde_Preco() {
		return dm_Esconde_Preco;
	}
	public void setDm_Esconde_Preco(String dmEscondePreco) {
		dm_Esconde_Preco = dmEscondePreco;
	}
	public long getOid_Banda_Dimensao() {
		return oid_Banda_Dimensao;
	}
	public void setOid_Banda_Dimensao(long oidBandaDimensao) {
		oid_Banda_Dimensao = oidBandaDimensao;
	}

	
}	