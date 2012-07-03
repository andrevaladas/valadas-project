package com.master.util.ed;

import java.util.Locale;

public class Parametro_FixoED {
	
  // Variável da instância para não precisar instanciar esta classe
  private static Parametro_FixoED instancia;
  private String serverName;
  private int port;
  private String contexto;
  private String webmaster;
  private String appPath = "/data/sistema";
  private String DT_Hoje;
  private String HR_Hoje;
  private double VL_Faixa1;
  private double PE_Aliquota_PIS_COFINS;
  private double VL_Faixa2;
  private double VL_Deducao_Faixa1;
  private double VL_Deducao_Faixa2;
  private double PE_Imposto_Faixa1;
  private double PE_Imposto_Faixa2;
  private double PE_Base_Calculo;
  private double VL_Dependente;

  private String DM_Soma_Coleta_Saldo_Frete;
  private String DM_Soma_Carga_Saldo_Frete;
  private String DM_Soma_Descarga_Saldo_Frete;
  private String DM_Soma_Premio_Saldo_Frete;
  private String DM_Soma_Descontos_Saldo_Frete;

  private String DM_Dia_Nao_Util;

  private String DM_Criterio_Comissao;
  private String DM_Calcula_IRRF;
  private String DM_Frete_Cortesia;
  private double VL_Frete_Cortesia;
  private String VL_Reembolso;
  private String DM_Calcula_PIS_COFINS;
  private double PE_Aliquota_COFINS;
  private double PE_Aliquota_PIS;
  private int OID_Tipo_Instrucao_Pago_Total;
  private int OID_Tipo_Instrucao_Taxa_Cobranca;
  private int OID_Tipo_Instrucao_Pago_Cartorio;
  private int OID_Tipo_Instrucao_Pago_Parcial;
  private int OID_Tipo_Instrucao_Devolucao_Nota_Fiscal;
  private int OID_Tipo_Instrucao_Tarifa;
  private int OID_Tipo_Instrucao_Juros;
  private int OID_Tipo_Instrucao_Desconto;
  private int OID_Tipo_Instrucao_Estorno;
  private int OID_Tipo_Instrucao_Imposto_Retido1;
  private int OID_Tipo_Instrucao_Imposto_Retido2;

  private String DM_Vincula_Adto_Ordem_Principal;
  private String DM_Quebra_Faturamento_Tipo_Conhecimento;
  private String DM_Quebra_Faturamento_Unidade;
  private String DM_Quebra_Faturamento_Tipo_Faturamento;
  private String DM_Gera_Lancamento_Contabil;
  private String DM_Gera_Lancamento_Contabil_Acerto;
  private String DM_Inclui_Conhecimento_Acerto;
  private String DM_Inclui_Nota_Fiscal_Servico_Acerto;
  private String DM_Inclui_Ordem_Frete_Acerto;
  private String DM_Inclui_Ordem_Frete_Terceiro_Acerto;
  private String DM_Inclui_Adiantamento_Acerto;

  private String DM_Data_Conhecimento_Acerto;
  private String DM_Data_Ordem_Frete_Acerto;
  private String DM_Data_Nota_Fiscal_Servico_Acerto;
  private String DM_Data_Ordem_Frete_Terceiro_Acerto;
  private String DM_Data_Adiantamento_Acerto;

  private String DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro;
  private String DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento;
  private String DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete;
  private String DM_Gera_Lancamento_Conta_Corrente_Acerto_Contas;
  private int OID_Tipo_Instrucao_Variacao_Cambial_Ativa;
  private int OID_Tipo_Instrucao_Variacao_Cambial_Passiva;
  private int OID_Tipo_Instrucao_Valor_Reembolso;
  private int OID_Tipo_Instrucao_Juros_Reembolso;
  private int OID_Historico_Transferencia_Banco;
  private int OID_Historico_Transferencia_Caixa;
  private int OID_Historico_Compensacao;
  private int OID_Historico_Devolucao_Cheque;
  private int OID_Historico_Cancelamento_Lote_Pagamento;
  private int OID_Historico_Lancamento_Ordem_Frete_Terceiro;
  private int OID_Historico_Lancamento_Ordem_Frete;
  private int OID_Historico_Lancamento_Ordem_Frete_Adiantamento;
  private int OID_Historico_Cancelamento_Ordem_Frete;
  private int OID_Tipo_Ocorrencia_Desconto_CTRC;
  private int OID_Tipo_Ocorrencia_Estorno_CTRC;
  private int OID_Tipo_Ocorrencia_Cancelamento_CTRC;
  private int OID_Tipo_Ocorrencia_Devolucao_CTRC;
  private int OID_Tipo_Ocorrencia_Reentrega_CTRC;
  private int OID_Tipo_Movimento_Produto;
  private String NM_Nivel_Produto1;
  private String NM_Nivel_Produto2;
  private String NM_Nivel_Produto3;
  private String NM_Nivel_Produto4;
  private String NM_Nivel_Produto5;
  private int OID_Deposito;
  private int OID_Operador;
  private int OID_Modal;
  private int OID_Modal_Aereo_Standard;
  private int OID_Modal_Aereo_Expressa;
  private int OID_Modal_Aereo_Emergencial;
  private int OID_Modal_Aereo_Sedex;
  private int OID_Modal_Aereo_Pac;
  private int OID_Modal_Aereo_RodExp;
  private int OID_Embalagem;
  private long oid_Tipo_Pedido;
  private int OID_Tipo_Estoque;
  private int OID_Tipo_Estoque_Devolucao;
  private int OID_Tipo_Estoque_Troca;
  private int OID_Tipo_Pallet;
  private int OID_Localizacao;
  private int OID_Localizacao_Devolucao;
  private int OID_Localizacao_Troca;
  private int OID_Conta_Frete_Terceiros;
  private int OID_Conta_Desconto_Frete;
  private int OID_Conta_Despesa_Bancaria;
  private int OID_Conta_Acerto_Contas;
  private int oid_Tipo_Documento_Nota_Fiscal_Entrada;
  private int oid_Tipo_Documento_Nota_Fiscal_Saida;
  private int oid_Modelo_Nota_Fiscal;
  private int oid_Modelo_NF_DevFornecedor_Dentro_Estado;
  private int oid_Modelo_NF_DevFornecedor_Fora_Estado;
  private String NM_Versao;
  private String NM_Razao_Social_Empresa;
  private String DM_Situacao;
  private String DM_Gera_Compromisso_Movimento_Ordem_Servico;
  private String DM_Atualiza_Movimento_Caixa;
  private String DM_Tipo_Sistema;
  private String DM_Operacao_Sistema;
  private String DM_Perfil_Sistema;
  private String DM_Inclui_ICMS_Parcela_CTRC;
  private String DM_Formulario_Consolidacao_MIC;
  private String DM_Formulario_Consolidacao_MIC_CRT;
  private String DM_Formulario_Duplicata_Proform;
  private String DM_Formulario_Duplicata;
  private String DM_Formulario_Demonstrativo_Cobranca;
  private String DM_Formulario_Protocolo_Cobranca;
  private String DM_Formulario_Duplicata_Internacional;
  private String DM_Formulario_Minuta;
  private String DM_Formulario_Frete_Terceiro;
  private String DM_Formulario_ACT;
  private String DM_Formulario_Conhecimento_Nacional;
  private String DM_Formulario_Conhecimento_Internacional;
  private String DM_Formulario_Conhecimento_Internacional_Verso;
  private String DM_Formulario_Coleta;
  private String DM_Formulario_Ordem_Frete;
  private String DM_Formulario_Ordem_Frete_Adiantamento;
  private String DM_Formulario_Ordem_Abastecimento;
  private String DM_Formulario_Nota_Fiscal;

  private String DM_Formulario_Ordem_Servico;
  private String DM_Formulario_Manifesto;
  private String DM_Formulario_Manifesto_Redespacho;
  private String DM_Formulario_Romaneio_Notal_Fiscal;
  private String DM_Formulario_Acerto_Contas;
  private String DM_Formulario_Romaneio_Entrega;
  private double PE_Aliquota_CSLL;
  private String document_Form_Action;
  private String EDI_Contabil;
  private String EDI_Seguradora;
  private String EDI_Banco;
  private String EDI_Cliente;
  private String EDI_Fiscal;
  private String EDI_Duplicata;
  private boolean completa_TX_Via;
  private String TX_Via_Primeiro_Original_Pt;
  private String TX_Via_Segundo_Original_Pt;
  private String TX_Via_Terceiro_Original_Pt;
  private String TX_Via_Primeiro_Original_Es;
  private String TX_Via_Segundo_Original_Es;
  private String TX_Via_Terceiro_Original_Es;
  private long oid_Tipo_Faturamento_Exportador;
  private long oid_Tipo_Faturamento_Importador;
  private String DM_Liquida_Compromisso;
  private String dm_Impressao_Tramo;
  private boolean NF_Multi_Conhecimento;
  private String PATH_RELATORIOS;
  private String PATH_RELATORIOS_XSL;
  private String PATH_IMAGENS;
  private long NR_Vias_Ordem_Frete_Adiantamento;
  private long NR_Vias_Ordem_Frete;
  private String DM_Saldo_Programado = "N";
  private int OID_Tipo_Servico_Acerto_Contas;
  private int OID_Tipo_Servico_Abastecimento;
  private int OID_Produto;
  private int NR_Conhecimentos_Linha_Fatura;
  private String OID_Pessoa_Padrao_Tabela_Frete;
  private long OID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico;
  private String NM_Modelo_Tabela_Rodoviario = "N";
  private String NM_Modelo_Tabela_Aereo = "N";
  private String NM_Modelo_Tabela_Documento = "N";
  private String CFOP_Padrao;
  private String DM_Tipo_Impressao_Fatura = "XSL";
  private String DM_Tipo_Impressao_Ordem_Frete = "XSL";
  private String palmPath = "/mnt/ftproot";
  private String palmModelo = "E";
  private boolean logTransactions;
  private String DM_Estado_Origem_CFOP;
  private String DM_Estado_Destino_CFOP;
  private int OID_Tipo_Movimento_Transf = 3;
  private int OID_Tipo_Movimento_Ajuste_E = 4;
  private int OID_Tipo_Movimento_Ajuste_S = 5;
  private int OID_Tipo_Movimento_Ajuste_Canc = 8; //Cancelamento
  private int OID_Tipo_Movimento_Ajuste_Exc = 9; //Exclusao
  private String DM_Formulario_AWB;
  private boolean movimento_Ordem_Servico_Duplicada;
  private String textoImprimirCampoNaoValidado = "(não possui)";
  private double PE_Comissao_Motorista;
  private double PE_Base_Comissao_Motorista;
  private String DM_Base_Comissao_Motorista;
  private double PE_Comissao_Motorista_Media;
  private String DM_Limite_Credito_Motorista_Adiantamento_Frete;
  private String DM_Multi_Moeda;
  private String OID_Fornecedor_INSS;
  private String OID_Fornecedor_IRRF;

  private String OID_Fornecedor_COFINS;
  private String OID_Fornecedor_CSLL;
  private String OID_Fornecedor_ISSQN;
  private String OID_Fornecedor_PIS;
  
  private boolean geraCodigoCliente = false;
  private String DM_Conhecimento_Varios_Manifestos;
  private String DM_Tipo_Impressao_Conhecimento_Nacional;
  private String DM_Tipo_Impressao_Minuta;
  private String DM_Tipo_Impressao_Nota_Fiscal_Servico;
  private String DM_Valida_CEP;
  private String DM_Exige_Validade_Lib_Seguradora_Motorista = "N";
  private String dmUsaCTB = "F";
  private String DM_Tipo_Impressao_Coleta;
  private int OID_Tipo_Movimento_Custo_Reentrega;
  private String DM_Formulario_Nota_Fiscal_Servico;
  private String DM_Gera_Compromisso_Motorista_Proprietario;
  private String DM_Tranca_Saldo_Ordem_Frete = "N";
  private String DM_Verifica_CNPJ_CPF_Pessoa = "N";
  private String dm_Wms_Nf_Saida_Numerada = "S";

  private String DM_Calcula_Previsao_Entrega="N";
  private String DT_Inicial_Previsao_Entrega="EMISSAO_CTO";
  private String DM_Comissao_Informada;

  private String DM_Gera_Livro_Fiscal = "N";

  private int OID_Tipo_Faturamento_Real = 0;

  private String NM_Ano_Permisso;

  private int vias_Fatura = 1;

  private String DM_Envia_Email_Eventos = "N";

  private boolean exigePedidoNotaCompra;

  public static synchronized Parametro_FixoED getInstancia () {
    if (instancia == null) {
      instancia = new Parametro_FixoED ();
      Locale loc = new Locale("pt","BR");
      Locale.setDefault(loc);
    }
    return instancia;
  }

  public String getEDI_Seguradora () {
    return EDI_Seguradora;
  }

  public String getEDI_Duplicata () {
    return EDI_Duplicata;
  }

  public String getEDI_Fiscal () {
    return EDI_Fiscal;
  }

  public String getEDI_Contabil () {
    return EDI_Contabil;
  }

  public String getEDI_Cliente () {
    return EDI_Cliente;
  }

  public String getEDI_Banco () {
    return EDI_Banco;
  }

  public String getDM_Liquida_Compromisso () {

    return DM_Liquida_Compromisso;
  }

  public String getNM_Modelo_Tabela_Rodoviario () {
    return NM_Modelo_Tabela_Rodoviario;
  }

  public String getNM_Modelo_Tabela_Documento () {
    return NM_Modelo_Tabela_Documento;
  }

  public String getNM_Modelo_Tabela_Aereo () {
    return NM_Modelo_Tabela_Aereo;
  }

  public int getOID_Historico_Cancelamento_Lote_Pagamento () {
    return OID_Historico_Cancelamento_Lote_Pagamento;
  }

  public long getOID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico () {
    return OID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico;
  }

  public String getDM_Formulario_Minuta () {
    return DM_Formulario_Minuta;
  }

  public int getOID_Historico_Lancamento_Ordem_Frete_Adiantamento () {
    return OID_Historico_Lancamento_Ordem_Frete_Adiantamento;
  }

  public int getOID_Historico_Lancamento_Ordem_Frete () {
    return OID_Historico_Lancamento_Ordem_Frete;
  }

  public String getDM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento () {
    return DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento;
  }

  public String getDM_Gera_Lancamento_Conta_Corrente_Ordem_Frete () {
    return DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete;
  }

  public int getOID_Tipo_Instrucao_Devolucao_Nota_Fiscal () {

    return OID_Tipo_Instrucao_Devolucao_Nota_Fiscal;
  }

  public String getDM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro () {
    return DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro;
  }

  public int getOID_Historico_Lancamento_Ordem_Frete_Terceiro () {
    return OID_Historico_Lancamento_Ordem_Frete_Terceiro;
  }

  public String getDM_Saldo_Programado () {
    return DM_Saldo_Programado;
  }

  public void setDM_Saldo_Programado (String saldo_Programado) {
    DM_Saldo_Programado = saldo_Programado;
  }

  public String getDM_Formulario_Romaneio_Entrega () {
    return this.DM_Formulario_Romaneio_Entrega;
  }

  public void setDM_Formulario_Romaneio_Entrega (String formulario_Romaneio_Entrega) {
    this.DM_Formulario_Romaneio_Entrega = formulario_Romaneio_Entrega;
  }

  public int getOID_Produto () {
    return OID_Produto;
  }

  public void setOID_Produto (int OID_Produto) {
    this.OID_Produto = OID_Produto;
  }

  public int getNR_Conhecimentos_Linha_Fatura () {
    return NR_Conhecimentos_Linha_Fatura;
  }

  public void setNR_Conhecimentos_Linha_Fatura (int NR_Conhecimentos_Linha_Fatura) {
    this.NR_Conhecimentos_Linha_Fatura = NR_Conhecimentos_Linha_Fatura;
  }

  public String getOID_Pessoa_Padrao_Tabela_Frete () {
    return this.OID_Pessoa_Padrao_Tabela_Frete;
  }

  public void setOID_Pessoa_Padrao_Tabela_Frete (String pessoa_Padrao_Tabela_Frete) {
    this.OID_Pessoa_Padrao_Tabela_Frete = pessoa_Padrao_Tabela_Frete;
  }

  public int getOID_Tipo_Estoque_Devolucao () {
    return OID_Tipo_Estoque_Devolucao;
  }

  public void setOID_Tipo_Estoque_Devolucao (int tipo_Estoque_Devolucao) {
    OID_Tipo_Estoque_Devolucao = tipo_Estoque_Devolucao;
  }

  public int getOID_Localizacao_Devolucao () {
    return OID_Localizacao_Devolucao;
  }

  public void setOID_Localizacao_Devolucao (int localizacao_Devolucao) {
    OID_Localizacao_Devolucao = localizacao_Devolucao;
  }

  public int getOID_Localizacao_Troca () {
    return OID_Localizacao_Troca;
  }

  public void setOID_Localizacao_Troca (int localizacao_Troca) {
    OID_Localizacao_Troca = localizacao_Troca;
  }

  public int getOID_Tipo_Estoque_Troca () {
    return OID_Tipo_Estoque_Troca;
  }

  public void setOID_Tipo_Estoque_Troca (int tipo_Estoque_Troca) {
    OID_Tipo_Estoque_Troca = tipo_Estoque_Troca;
  }

  public String getCFOP_Padrao () {
    return this.CFOP_Padrao;
  }

  public void setCFOP_Padrao (String padrao) {
    this.CFOP_Padrao = padrao;
  }

  public boolean isLogTransactions () {
    return logTransactions;
  }

  public void setLogTransactions (boolean logTransactions) {
    this.logTransactions = logTransactions;
  }

  public String getDM_Estado_Destino_CFOP () {
    return DM_Estado_Destino_CFOP;
  }

  public void setDM_Estado_Destino_CFOP (String estado_Destino_CFOP) {
    DM_Estado_Destino_CFOP = estado_Destino_CFOP;
  }

  public String getDM_Estado_Origem_CFOP () {
    return DM_Estado_Origem_CFOP;
  }

  public void setDM_Estado_Origem_CFOP (String estado_Origem_CFOP) {
    DM_Estado_Origem_CFOP = estado_Origem_CFOP;
  }

  public int getOid_Tipo_Movimento_Ajuste_E () {
    return OID_Tipo_Movimento_Ajuste_E;
  }

  public void setOid_Tipo_Movimento_Ajuste_E (int oid_Tipo_Movimento_Ajuste_E) {
    this.OID_Tipo_Movimento_Ajuste_E = oid_Tipo_Movimento_Ajuste_E;
  }

  public int getOid_Tipo_Movimento_Ajuste_S () {
    return OID_Tipo_Movimento_Ajuste_S;
  }

  public void setOid_Tipo_Movimento_Ajuste_S (int oid_Tipo_Movimento_Ajuste_S) {
    this.OID_Tipo_Movimento_Ajuste_S = oid_Tipo_Movimento_Ajuste_S;
  }

  public int getOid_Tipo_Movimento_Transf () {
    return OID_Tipo_Movimento_Transf;
  }

  public void setOid_Tipo_Movimento_Transf (int oid_Tipo_Movimento_Transf) {
    this.OID_Tipo_Movimento_Transf = oid_Tipo_Movimento_Transf;
  }

  public int getOID_Modal_Aereo_Standard () {
    return this.OID_Modal_Aereo_Standard;
  }

  public void setOID_Modal_Aereo_Standard (int modal_Aereo_Standard) {
    this.OID_Modal_Aereo_Standard = modal_Aereo_Standard;
  }

  public String getDM_Formulario_AWB () {
    return this.DM_Formulario_AWB;
  }

  public void setDM_Formulario_AWB (String formulario_AWB) {
    this.DM_Formulario_AWB = formulario_AWB;
  }

  public boolean isMovimento_Ordem_Servico_Duplicada () {
    return this.movimento_Ordem_Servico_Duplicada;
  }

  public int getOid_Modelo_NF_DevFornecedor_Dentro_Estado () {
    return oid_Modelo_NF_DevFornecedor_Dentro_Estado;
  }

  public void setMovimento_Ordem_Servico_Duplicada (boolean movimento_Ordem_Servico_Duplicada) {
    this.movimento_Ordem_Servico_Duplicada = movimento_Ordem_Servico_Duplicada;
  }

  public String getTextoImprimirCampoNaoValidado () {
    return this.textoImprimirCampoNaoValidado;
  }

  public void setTextoImprimirCampoNaoValidado (String textoImprimirCampoNaoValidado) {
    this.textoImprimirCampoNaoValidado = textoImprimirCampoNaoValidado;
  }

  public void setOid_Modelo_NF_DevFornecedor_Dentro_Estado (int oid_Modelo_NF_DevFornecedor_Dentro_Estado) {
    this.oid_Modelo_NF_DevFornecedor_Dentro_Estado = oid_Modelo_NF_DevFornecedor_Dentro_Estado;
  }

  public int getOid_Modelo_NF_DevFornecedor_Fora_Estado () {
    return oid_Modelo_NF_DevFornecedor_Fora_Estado;
  }

  public int getOID_Conta_Acerto_Contas () {
    return OID_Conta_Acerto_Contas;
  }

  public String getDM_Gera_Lancamento_Conta_Corrente_Acerto_Contas () {
    return DM_Gera_Lancamento_Conta_Corrente_Acerto_Contas;
  }

  public String getDM_Multi_Moeda () {
    return DM_Multi_Moeda;
  }

  public double getPE_Comissao_Motorista_Media () {
    return PE_Comissao_Motorista_Media;
  }

  public long getNR_Vias_Ordem_Frete () {
    return NR_Vias_Ordem_Frete;
  }

  public void setOid_Modelo_NF_DevFornecedor_Fora_Estado (int oid_Modelo_NF_DevFornecedor_Fora_Estado) {
    this.oid_Modelo_NF_DevFornecedor_Fora_Estado = oid_Modelo_NF_DevFornecedor_Fora_Estado;
  }

  public void setOID_Conta_Acerto_Contas (int OID_Conta_Acerto_Contas) {
    this.OID_Conta_Acerto_Contas = OID_Conta_Acerto_Contas;
  }

  public void setDM_Gera_Lancamento_Conta_Corrente_Acerto_Contas (String DM_Gera_Lancamento_Conta_Corrente_Acerto_Contas) {
    this.DM_Gera_Lancamento_Conta_Corrente_Acerto_Contas = DM_Gera_Lancamento_Conta_Corrente_Acerto_Contas;
  }

  public void setDM_Multi_Moeda (String DM_Multi_Moeda) {
    this.DM_Multi_Moeda = DM_Multi_Moeda;
  }

  public void setPE_Comissao_Motorista_Media (double PE_Comissao_Motorista_Media) {
    this.PE_Comissao_Motorista_Media = PE_Comissao_Motorista_Media;
  }

  public void setNR_Vias_Ordem_Frete (long NR_Vias_Ordem_Frete) {
    this.NR_Vias_Ordem_Frete = NR_Vias_Ordem_Frete;
  }

  public int getOID_Historico_Devolucao_Cheque () {
    return OID_Historico_Devolucao_Cheque;
  }

  public void setOID_Historico_Devolucao_Cheque (int historico_Devolucao_Cheque) {
    OID_Historico_Devolucao_Cheque = historico_Devolucao_Cheque;
  }

  public boolean isGeraCodigoCliente () {
    return geraCodigoCliente;
  }

  public void setGeraCodigoCliente (boolean geraCodigoCliente) {
    this.geraCodigoCliente = geraCodigoCliente;
  }

  /**
   * - Contabilidade - por Regis em out/2005
   */
  public String getDmUsaCTB () {
    return dmUsaCTB;
  }

  public void setDmUsaCTB (String dmUsaCTB) {
    this.dmUsaCTB = dmUsaCTB;
  }

  public String getPalmPath () {
    return palmPath;
  }

  public String getDM_Tipo_Impressao_Conhecimento_Nacional () {
    return DM_Tipo_Impressao_Conhecimento_Nacional;
  }

  public int getOID_Tipo_Movimento_Transf () {
    return OID_Tipo_Movimento_Transf;
  }

  public String getDM_Conhecimento_Varios_Manifestos () {

    return DM_Conhecimento_Varios_Manifestos;
  }

  public int getOID_Tipo_Instrucao_Taxa_Cobranca () {
    return OID_Tipo_Instrucao_Taxa_Cobranca;
  }

  public int getOID_Historico_Cancelamento_Ordem_Frete () {
    return OID_Historico_Cancelamento_Ordem_Frete;
  }

  public void setPalmPath (String palmPath) {
    this.palmPath = palmPath;
  }

  public void setDM_Tipo_Impressao_Conhecimento_Nacional (String DM_Tipo_Impressao_Conhecimento_Nacional) {
    this.DM_Tipo_Impressao_Conhecimento_Nacional = DM_Tipo_Impressao_Conhecimento_Nacional;
  }

  public void setOID_Tipo_Movimento_Transf (int OID_Tipo_Movimento_Transf) {
    this.OID_Tipo_Movimento_Transf = OID_Tipo_Movimento_Transf;
  }

  public void setDM_Conhecimento_Varios_Manifestos (String DM_Conhecimento_Varios_Manifestos) {

    this.DM_Conhecimento_Varios_Manifestos = DM_Conhecimento_Varios_Manifestos;
  }

  public void setOID_Tipo_Instrucao_Taxa_Cobranca (int OID_Tipo_Instrucao_Taxa_Cobranca) {
    this.OID_Tipo_Instrucao_Taxa_Cobranca = OID_Tipo_Instrucao_Taxa_Cobranca;
  }

  public void setOID_Historico_Cancelamento_Ordem_Frete (int OID_Historico_Cancelamento_Ordem_Frete) {
    this.OID_Historico_Cancelamento_Ordem_Frete = OID_Historico_Cancelamento_Ordem_Frete;
  }

  public String getOID_Fornecedor_INSS () {
    return OID_Fornecedor_INSS;
  }

  public void setOID_Fornecedor_INSS (String fornecedor_INSS) {
    OID_Fornecedor_INSS = fornecedor_INSS;
  }

  public String getOID_Fornecedor_IRRF () {
    return OID_Fornecedor_IRRF;
  }

  public void setOID_Fornecedor_IRRF (String fornecedor_IRRF) {
    OID_Fornecedor_IRRF = fornecedor_IRRF;
  }

  public String getWebmaster () {
    return webmaster;
  }

  public String getDM_Formulario_Nota_Fiscal_Servico () {
    return DM_Formulario_Nota_Fiscal_Servico;
  }

  public int getOID_Tipo_Movimento_Custo_Reentrega () {
    return OID_Tipo_Movimento_Custo_Reentrega;
  }

  public String getDM_Formulario_Manifesto_Redespacho () {
    return DM_Formulario_Manifesto_Redespacho;
  }

  public String getDM_Formulario_Demonstrativo_Cobranca () {
    return DM_Formulario_Demonstrativo_Cobranca;
  }

  public String getDM_Base_Comissao_Motorista () {
    return DM_Base_Comissao_Motorista;
  }

  public double getPE_Comissao_Motorista () {
    return PE_Comissao_Motorista;
  }

  public double getPE_Base_Comissao_Motorista () {
    return PE_Base_Comissao_Motorista;
  }

  public String getDM_Tipo_Impressao_Coleta () {
    return DM_Tipo_Impressao_Coleta;
  }

  public String getDM_Limite_Credito_Motorista_Adiantamento_Frete () {
    return DM_Limite_Credito_Motorista_Adiantamento_Frete;
  }

  public void setWebmaster (String webmaster) {
    this.webmaster = webmaster;
  }

  public void setDM_Formulario_Nota_Fiscal_Servico (String DM_Formulario_Nota_Fiscal_Servico) {
    this.DM_Formulario_Nota_Fiscal_Servico = DM_Formulario_Nota_Fiscal_Servico;
  }

  public void setOID_Tipo_Movimento_Custo_Reentrega (int OID_Tipo_Movimento_Custo_Reentrega) {
    this.OID_Tipo_Movimento_Custo_Reentrega = OID_Tipo_Movimento_Custo_Reentrega;
  }

  public void setDM_Formulario_Manifesto_Redespacho (String
                                                     DM_Formulario_Manifesto_Redespacho) {
    this.DM_Formulario_Manifesto_Redespacho =
        DM_Formulario_Manifesto_Redespacho;
  }

  public void setDM_Formulario_Demonstrativo_Cobranca (String
                                                       DM_Formulario_Demonstrativo_Cobranca) {
    this.DM_Formulario_Demonstrativo_Cobranca =
        DM_Formulario_Demonstrativo_Cobranca;
  }

  public void setDM_Base_Comissao_Motorista (String DM_Base_Comissao_Motorista) {
    this.DM_Base_Comissao_Motorista = DM_Base_Comissao_Motorista;
  }

  public void setPE_Comissao_Motorista (double PE_Comissao_Motorista) {
    this.PE_Comissao_Motorista = PE_Comissao_Motorista;
  }

  public void setPE_Base_Comissao_Motorista (double PE_Base_Comissao_Motorista) {
    this.PE_Base_Comissao_Motorista = PE_Base_Comissao_Motorista;
  }

  public void setDM_Tipo_Impressao_Coleta (String DM_Tipo_Impressao_Coleta) {
    this.DM_Tipo_Impressao_Coleta = DM_Tipo_Impressao_Coleta;
  }

  public void setDM_Limite_Credito_Motorista_Adiantamento_Frete (String
                                                                 DM_Limite_Credito_Motorista_Adiantamento_Frete) {
    this.DM_Limite_Credito_Motorista_Adiantamento_Frete =
        DM_Limite_Credito_Motorista_Adiantamento_Frete;
  }

  public int getOID_Tipo_Movimento_Ajuste_Canc () {
    return OID_Tipo_Movimento_Ajuste_Canc;
  }

  public void setOID_Tipo_Movimento_Ajuste_Canc (int tipo_Movimento_Ajuste_Canc) {
    OID_Tipo_Movimento_Ajuste_Canc = tipo_Movimento_Ajuste_Canc;
  }

  public int getOID_Tipo_Movimento_Ajuste_Exc () {
    return OID_Tipo_Movimento_Ajuste_Exc;
  }

  public void setOID_Tipo_Movimento_Ajuste_Exc (int tipo_Movimento_Ajuste_Exc) {
    OID_Tipo_Movimento_Ajuste_Exc = tipo_Movimento_Ajuste_Exc;
  }

  public String getDM_Valida_CEP () {
    return DM_Valida_CEP;
  }

  public void setDM_Valida_CEP (String valida_CEP) {
    DM_Valida_CEP = valida_CEP;
  }

  public String getDM_Exige_Validade_Lib_Seguradora_Motorista () {
    return DM_Exige_Validade_Lib_Seguradora_Motorista;
  }

  public void setDM_Exige_Validade_Lib_Seguradora_Motorista (
      String exige_Validade_Lib_Seguradora_Motorista) {
    DM_Exige_Validade_Lib_Seguradora_Motorista = exige_Validade_Lib_Seguradora_Motorista;
  }

  public String getDM_Formulario_Consolidacao_MIC () {
    return DM_Formulario_Consolidacao_MIC;
  }

  public void setDM_Formulario_Consolidacao_MIC (String formulario_Consolidacao_MIC) {
    DM_Formulario_Consolidacao_MIC = formulario_Consolidacao_MIC;
  }

  public String getDM_Formulario_Consolidacao_MIC_CRT () {
    return DM_Formulario_Consolidacao_MIC_CRT;
  }

  public void setDM_Formulario_Consolidacao_MIC_CRT (String formulario_Consolidacao_MIC_CRT) {
    DM_Formulario_Consolidacao_MIC_CRT = formulario_Consolidacao_MIC_CRT;
  }

  public String getDM_Formulario_Duplicata_Proform () {
    return DM_Formulario_Duplicata_Proform;
  }

  public void setDM_Formulario_Duplicata_Proform (String formulario_Duplicata_Proform) {
    DM_Formulario_Duplicata_Proform = formulario_Duplicata_Proform;
  }

  public String getDM_Formulario_Duplicata_Internacional () {
    return DM_Formulario_Duplicata_Internacional;
  }

  public void setDM_Formulario_Duplicata_Internacional (String DM_Formulario_Duplicata_Internacional) {
    this.DM_Formulario_Duplicata_Internacional = DM_Formulario_Duplicata_Internacional;
  }

  public String getDM_Tipo_Impressao_Fatura () {
    return DM_Tipo_Impressao_Fatura;
  }

  public void setDM_Tipo_Impressao_Fatura (String tipo_Impressao_Fatura) {
    DM_Tipo_Impressao_Fatura = tipo_Impressao_Fatura;
  }

  public String getDm_Impressao_Tramo () {
    return dm_Impressao_Tramo;
  }

  public void setDm_Impressao_Tramo (String dm_Impressao_Tramo) {
    this.dm_Impressao_Tramo = dm_Impressao_Tramo;
  }

  public long getOid_Tipo_Faturamento_Exportador () {
    return oid_Tipo_Faturamento_Exportador;
  }

  public void setOid_Tipo_Faturamento_Exportador (long oid_Tipo_Faturamento_Exportador) {
    this.oid_Tipo_Faturamento_Exportador = oid_Tipo_Faturamento_Exportador;
  }

  public long getOid_Tipo_Faturamento_Importador () {
    return oid_Tipo_Faturamento_Importador;
  }

  public void setOid_Tipo_Faturamento_Importador (long oid_Tipo_Faturamento_Importador) {
    this.oid_Tipo_Faturamento_Importador = oid_Tipo_Faturamento_Importador;
  }

  public boolean isCompleta_TX_Via () {
    return completa_TX_Via;
  }

  public void setCompleta_TX_Via (boolean completa_TX_Via) {
    this.completa_TX_Via = completa_TX_Via;
  }

  public String getTX_Via_Primeiro_Original_Es () {
    return TX_Via_Primeiro_Original_Es;
  }

  public void setTX_Via_Primeiro_Original_Es (String via_Primeiro_Original_Es) {
    TX_Via_Primeiro_Original_Es = via_Primeiro_Original_Es;
  }

  public String getTX_Via_Primeiro_Original_Pt () {
    return TX_Via_Primeiro_Original_Pt;
  }

  public void setTX_Via_Primeiro_Original_Pt (String via_Primeiro_Original_Pt) {
    TX_Via_Primeiro_Original_Pt = via_Primeiro_Original_Pt;
  }

  public String getTX_Via_Segundo_Original_Es () {
    return TX_Via_Segundo_Original_Es;
  }

  public void setTX_Via_Segundo_Original_Es (String via_Segundo_Original_Es) {
    TX_Via_Segundo_Original_Es = via_Segundo_Original_Es;
  }

  public String getTX_Via_Segundo_Original_Pt () {
    return TX_Via_Segundo_Original_Pt;
  }

  public void setTX_Via_Segundo_Original_Pt (String via_Segundo_Original_Pt) {
    TX_Via_Segundo_Original_Pt = via_Segundo_Original_Pt;
  }

  public String getTX_Via_Terceiro_Original_Es () {
    return TX_Via_Terceiro_Original_Es;
  }

  public void setTX_Via_Terceiro_Original_Es (String via_Terceiro_Original_Es) {
    TX_Via_Terceiro_Original_Es = via_Terceiro_Original_Es;
  }

  public String getTX_Via_Terceiro_Original_Pt () {
    return TX_Via_Terceiro_Original_Pt;
  }

  public void setTX_Via_Terceiro_Original_Pt (String via_Terceiro_Original_Pt) {
    TX_Via_Terceiro_Original_Pt = via_Terceiro_Original_Pt;
  }

  public String getVL_Reembolso () {
    return VL_Reembolso;
  }

  public void setVL_Reembolso (String reembolso) {
    VL_Reembolso = reembolso;
  }

  public String getDocument_Form_Action () {
    return document_Form_Action;
  }

  public void setDocument_Form_Action (String document_Form_Action) {
    this.document_Form_Action = document_Form_Action;
  }

  public double getPE_Aliquota_CSLL () {
    return PE_Aliquota_CSLL;
  }

  public void setPE_Aliquota_CSLL (double aliquota_CSLL) {
    PE_Aliquota_CSLL = aliquota_CSLL;
  }

  public void setDT_Hoje (String DT_Hoje) {
    this.DT_Hoje = DT_Hoje;
  }

  public String getDT_Hoje () {
    return DT_Hoje;
  }

  public void setHR_Hoje (String HR_Hoje) {
    this.HR_Hoje = HR_Hoje;
  }

  public String getHR_Hoje () {
    return HR_Hoje;
  }

  public void setVL_Faixa1 (double VL_Faixa1) {
    this.VL_Faixa1 = VL_Faixa1;
  }

  public double getVL_Faixa1 () {
    return VL_Faixa1;
  }

  public void setVL_Faixa2 (double VL_Faixa2) {
    this.VL_Faixa2 = VL_Faixa2;
  }

  public double getVL_Faixa2 () {
    return VL_Faixa2;
  }

  public void setVL_Deducao_Faixa1 (double VL_Deducao_Faixa1) {
    this.VL_Deducao_Faixa1 = VL_Deducao_Faixa1;
  }

  public double getVL_Deducao_Faixa1 () {
    return VL_Deducao_Faixa1;
  }

  public void setVL_Deducao_Faixa2 (double VL_Deducao_Faixa2) {
    this.VL_Deducao_Faixa2 = VL_Deducao_Faixa2;
  }

  public double getVL_Deducao_Faixa2 () {
    return VL_Deducao_Faixa2;
  }

  public void setPE_Imposto_Faixa1 (double PE_Imposto_Faixa1) {
    this.PE_Imposto_Faixa1 = PE_Imposto_Faixa1;
  }

  public double getPE_Imposto_Faixa1 () {
    return PE_Imposto_Faixa1;
  }

  public void setPE_Imposto_Faixa2 (double PE_Imposto_Faixa2) {
    this.PE_Imposto_Faixa2 = PE_Imposto_Faixa2;
  }

  public double getPE_Imposto_Faixa2 () {
    return PE_Imposto_Faixa2;
  }

  public void setPE_Base_Calculo (double PE_Base_Calculo) {
    this.PE_Base_Calculo = PE_Base_Calculo;
  }

  public double getPE_Base_Calculo () {
    return PE_Base_Calculo;
  }

  public void setVL_Dependente (double VL_Dependente) {
    this.VL_Dependente = VL_Dependente;
  }

  public double getVL_Dependente () {
    return VL_Dependente;
  }

  public void setOID_Tipo_Ocorrencia_Estorno_CTRC (int OID_Tipo_Ocorrencia_Estorno_CTRC) {
    this.OID_Tipo_Ocorrencia_Estorno_CTRC = OID_Tipo_Ocorrencia_Estorno_CTRC;
  }

  public int getOID_Tipo_Ocorrencia_Estorno_CTRC () {
    return OID_Tipo_Ocorrencia_Estorno_CTRC;
  }

  public void setNM_Empresa (String NM_Empresa) {
    base_EmpresaED.setNM_Empresa (NM_Empresa);
  }

  public String getNM_Empresa () {
    return base_EmpresaED.getNM_Empresa ();
  }

  public void setNM_Base (String NM_Base) {
    base_EmpresaED.setNM_Base (NM_Base);
  }

  public String getNM_Base () {
    return base_EmpresaED.getNM_Base ();
  }


  public int getOID_Modal () {
    return OID_Modal;
  }

  public void setOID_Modal (int OID_Modal) {
    this.OID_Modal = OID_Modal;
  }


  public void setDM_Criterio_Comissao (String DM_Criterio_Comissao) {
    this.DM_Criterio_Comissao = DM_Criterio_Comissao;
  }

  public String getDM_Criterio_Comissao () {
    return DM_Criterio_Comissao;
  }

  public void setDM_Calcula_IRRF (String DM_Calcula_IRRF) {
    this.DM_Calcula_IRRF = DM_Calcula_IRRF;
  }

  public String getDM_Calcula_IRRF () {
    return DM_Calcula_IRRF;
  }

  public void setDM_Frete_Cortesia (String DM_Frete_Cortesia) {
    this.DM_Frete_Cortesia = DM_Frete_Cortesia;
  }

  public String getDM_Frete_Cortesia () {
    return DM_Frete_Cortesia;
  }

  public void setVL_Frete_Cortesia (double VL_Frete_Cortesia) {
    this.VL_Frete_Cortesia = VL_Frete_Cortesia;
  }

  public double getVL_Frete_Cortesia () {
    return VL_Frete_Cortesia;
  }

  public void setDM_Calcula_PIS_COFINS (String DM_Calcula_PIS_COFINS) {
    this.DM_Calcula_PIS_COFINS = DM_Calcula_PIS_COFINS;
  }

  public String getDM_Calcula_PIS_COFINS () {
    return DM_Calcula_PIS_COFINS;
  }

  public void setPE_Aliquota_COFINS (double PE_Aliquota_COFINS) {
    this.PE_Aliquota_COFINS = PE_Aliquota_COFINS;
  }

  public double getPE_Aliquota_COFINS () {
    return PE_Aliquota_COFINS;
  }

  public void setPE_Aliquota_PIS (double PE_Aliquota_PIS) {
    this.PE_Aliquota_PIS = PE_Aliquota_PIS;
  }

  public double getPE_Aliquota_PIS () {
    return PE_Aliquota_PIS;
  }

  public void setOID_Tipo_Instrucao_Pago_Total (int OID_Tipo_Instrucao_Pago_Total) {
    this.OID_Tipo_Instrucao_Pago_Total = OID_Tipo_Instrucao_Pago_Total;
  }

  public int getOID_Tipo_Instrucao_Pago_Total () {
    return OID_Tipo_Instrucao_Pago_Total;
  }

  public void setOID_Tipo_Instrucao_Pago_Cartorio (int OID_Tipo_Instrucao_Pago_Cartorio) {
    this.OID_Tipo_Instrucao_Pago_Cartorio = OID_Tipo_Instrucao_Pago_Cartorio;
  }

  public int getOID_Tipo_Instrucao_Pago_Cartorio () {
    return OID_Tipo_Instrucao_Pago_Cartorio;
  }

  public void setOID_Tipo_Instrucao_Pago_Parcial (int OID_Tipo_Instrucao_Pago_Parcial) {
    this.OID_Tipo_Instrucao_Pago_Parcial = OID_Tipo_Instrucao_Pago_Parcial;
  }

  public int getOID_Tipo_Instrucao_Pago_Parcial () {
    return OID_Tipo_Instrucao_Pago_Parcial;
  }

  public void setOID_Tipo_Instrucao_Tarifa (int OID_Tipo_Instrucao_Tarifa) {
    this.OID_Tipo_Instrucao_Tarifa = OID_Tipo_Instrucao_Tarifa;
  }

  public int getOID_Tipo_Instrucao_Tarifa () {
    return OID_Tipo_Instrucao_Tarifa;
  }

  public void setOID_Tipo_Instrucao_Juros (int OID_Tipo_Instrucao_Juros) {
    this.OID_Tipo_Instrucao_Juros = OID_Tipo_Instrucao_Juros;
  }

  public int getOID_Tipo_Instrucao_Juros () {
    return OID_Tipo_Instrucao_Juros;
  }

  public void setOID_Tipo_Instrucao_Desconto (int OID_Tipo_Instrucao_Desconto) {
    this.OID_Tipo_Instrucao_Desconto = OID_Tipo_Instrucao_Desconto;
  }

  public int getOID_Tipo_Instrucao_Desconto () {
    return OID_Tipo_Instrucao_Desconto;
  }

  public void setDM_Quebra_Faturamento_Tipo_Conhecimento (String DM_Quebra_Faturamento_Tipo_Conhecimento) {
    this.DM_Quebra_Faturamento_Tipo_Conhecimento = DM_Quebra_Faturamento_Tipo_Conhecimento;
  }

  public String getDM_Quebra_Faturamento_Tipo_Conhecimento () {
    return DM_Quebra_Faturamento_Tipo_Conhecimento;
  }

  public void setDM_Quebra_Faturamento_Unidade (String DM_Quebra_Faturamento_Unidade) {
    this.DM_Quebra_Faturamento_Unidade = DM_Quebra_Faturamento_Unidade;
  }

  public String getDM_Quebra_Faturamento_Unidade () {
    return DM_Quebra_Faturamento_Unidade;
  }

  public void setDM_Quebra_Faturamento_Tipo_Faturamento (String DM_Quebra_Faturamento_Tipo_Faturamento) {
    this.DM_Quebra_Faturamento_Tipo_Faturamento = DM_Quebra_Faturamento_Tipo_Faturamento;
  }

  public String getDM_Quebra_Faturamento_Tipo_Faturamento () {
    return DM_Quebra_Faturamento_Tipo_Faturamento;
  }

  public void setDM_Gera_Lancamento_Contabil (String DM_Gera_Lancamento_Contabil) {
    this.DM_Gera_Lancamento_Contabil = DM_Gera_Lancamento_Contabil;
  }

  public String getDM_Gera_Lancamento_Contabil () {
    return DM_Gera_Lancamento_Contabil;
  }

  public int getOID_Tipo_Instrucao_Juros_Reembolso () {
    return OID_Tipo_Instrucao_Juros_Reembolso;
  }

  public void setOID_Tipo_Instrucao_Juros_Reembolso (int OID_Tipo_Instrucao_Juros_Reembolso) {
    this.OID_Tipo_Instrucao_Juros_Reembolso = OID_Tipo_Instrucao_Juros_Reembolso;
  }

  public void setOID_Tipo_Instrucao_Valor_Reembolso (int OID_Tipo_Instrucao_Valor_Reembolso) {
    this.OID_Tipo_Instrucao_Valor_Reembolso = OID_Tipo_Instrucao_Valor_Reembolso;
  }

  public void setOID_Tipo_Instrucao_Variacao_Cambial_Ativa (int OID_Tipo_Instrucao_Variacao_Cambial_Ativa) {
    this.OID_Tipo_Instrucao_Variacao_Cambial_Ativa = OID_Tipo_Instrucao_Variacao_Cambial_Ativa;
  }

  public void setOID_Tipo_Instrucao_Variacao_Cambial_Passiva (int OID_Tipo_Instrucao_Variacao_Cambial_Passiva) {
    this.OID_Tipo_Instrucao_Variacao_Cambial_Passiva = OID_Tipo_Instrucao_Variacao_Cambial_Passiva;
  }

  public int getOID_Tipo_Instrucao_Valor_Reembolso () {
    return OID_Tipo_Instrucao_Valor_Reembolso;
  }

  public int getOID_Tipo_Instrucao_Variacao_Cambial_Ativa () {
    return OID_Tipo_Instrucao_Variacao_Cambial_Ativa;
  }

  public int getOID_Tipo_Instrucao_Variacao_Cambial_Passiva () {
    return OID_Tipo_Instrucao_Variacao_Cambial_Passiva;
  }

   public void setOID_Historico_Compensacao (int OID_Historico_Compensacao) {
    this.OID_Historico_Compensacao = OID_Historico_Compensacao;
  }

  public int getOID_Historico_Compensacao () {
    return OID_Historico_Compensacao;
  }

  public int getOID_Tipo_Ocorrencia_Desconto_CTRC () {
    return OID_Tipo_Ocorrencia_Desconto_CTRC;
  }

  public void setOID_Tipo_Ocorrencia_Desconto_CTRC (int OID_Tipo_Ocorrencia_Desconto_CTRC) {
    this.OID_Tipo_Ocorrencia_Desconto_CTRC = OID_Tipo_Ocorrencia_Desconto_CTRC;
  }

   public int getOID_Tipo_Movimento_Produto () {
    return OID_Tipo_Movimento_Produto;
  }

  public void setOID_Tipo_Movimento_Produto (int OID_Tipo_Movimento_Produto) {
    this.OID_Tipo_Movimento_Produto = OID_Tipo_Movimento_Produto;
  }

  public int getOID_Deposito () {
    return OID_Deposito;
  }

  public void setOID_Deposito (int OID_Deposito) {
    this.OID_Deposito = OID_Deposito;
  }

  public int getOID_Operador () {
    return OID_Operador;
  }

  public void setOID_Operador (int OID_Operador) {
    this.OID_Operador = OID_Operador;
  }

  public void setNM_Database_URL (String NM_Database_URL) {
    base_EmpresaED.setNM_Database_URL (NM_Database_URL);
  }

  public String getNM_Database_URL () {
    return base_EmpresaED.getNM_Database_URL ();
  }

  public String getNM_Database_Pwd () {
    return base_EmpresaED.getNM_Database_Pwd ();
  }

  public String getNM_Database_Usuario () {
    return base_EmpresaED.getNM_Database_Usuario ();
  }

  public void setNM_Database_Pwd (String NM_Database_Pwd) {
    base_EmpresaED.setNM_Database_Pwd (NM_Database_Pwd);
  }

  public void setNM_Database_Usuario (String NM_Database_Usuario) {
    base_EmpresaED.setNM_Database_Usuario (NM_Database_Usuario);
  }

  public void setNM_Databese_Sid (String NM_Databese_Sid) {
    base_EmpresaED.setNM_Databese_Sid (NM_Databese_Sid);
  }

  public String getNM_Databese_Sid () {
    return base_EmpresaED.getNM_Databese_Sid ();
  }

  public void setNM_Database_Host (String NM_Database_Host) {
    base_EmpresaED.setNM_Database_Host (NM_Database_Host);
  }

  public void setNM_Database_Port (int NM_Database_Port) {
    base_EmpresaED.setNM_Database_Port (NM_Database_Port);
  }

  public String getNM_Database_Host () {
    return base_EmpresaED.getNM_Database_Host ();
  }

  public int getNM_Database_Port () {
    return base_EmpresaED.getNM_Database_Port ();
  }

  public String getNM_Database_Driver () {
    return base_EmpresaED.getNM_Database_Driver ();
  }

  public void setNM_Database_Driver (String NM_Database_Driver) {
    base_EmpresaED.setNM_Database_Driver (NM_Database_Driver);
  }

  public String getNM_Database_UsuarioBase2 () {
    return base_EmpresaED.getNM_Database_UsuarioBase2 ();
  }

  public void setNM_Database_UsuarioBase2 (String NM_Database_UsuarioBase2) {
    base_EmpresaED.setNM_Database_UsuarioBase2 (NM_Database_UsuarioBase2);
  }

  public String getNM_Database_PwdBase2 () {
    return base_EmpresaED.getNM_Database_PwdBase2 ();
  }

  public void setNM_Database_PwdBase2 (String NM_Database_PwdBase2) {
    base_EmpresaED.setNM_Database_PwdBase2 (NM_Database_PwdBase2);
  }

  public void setNM_Database_URLBase2 (String NM_Database_URLBase2) {
    base_EmpresaED.setNM_Database_URLBase2 (NM_Database_URLBase2);
  }

  public String getNM_Database_URLBase2 () {
    return base_EmpresaED.getNM_Database_URLBase2 ();
  }

  public String getNM_Database_DriverBase2 () {
    return base_EmpresaED.getNM_Database_DriverBase2 ();
  }

  public void setNM_Database_DriverBase2 (String NM_Database_DriverBase2) {
    base_EmpresaED.setNM_Database_DriverBase2 (NM_Database_DriverBase2);
  }

  public void setNM_Nivel_Produto1 (String NM_Nivel_Produto1) {
    this.NM_Nivel_Produto1 = NM_Nivel_Produto1;
  }

  public String getNM_Nivel_Produto1 () {
    return NM_Nivel_Produto1;
  }

  public void setNM_Nivel_Produto2 (String NM_Nivel_Produto2) {
    this.NM_Nivel_Produto2 = NM_Nivel_Produto2;
  }

  public String getNM_Nivel_Produto2 () {
    return NM_Nivel_Produto2;
  }

  public void setNM_Nivel_Produto3 (String NM_Nivel_Produto3) {
    this.NM_Nivel_Produto3 = NM_Nivel_Produto3;
  }

  public String getNM_Nivel_Produto3 () {
    return NM_Nivel_Produto3;
  }

  public void setNM_Nivel_Produto4 (String NM_Nivel_Produto4) {
    this.NM_Nivel_Produto4 = NM_Nivel_Produto4;
  }

  public String getNM_Nivel_Produto4 () {
    return NM_Nivel_Produto4;
  }

  public void setNM_Nivel_Produto5 (String NM_Nivel_Produto5) {
    this.NM_Nivel_Produto5 = NM_Nivel_Produto5;
  }

  public String getNM_Nivel_Produto5 () {
    return NM_Nivel_Produto5;
  }

  public void setOID_Tipo_Pallet (int OID_Tipo_Pallet) {
    this.OID_Tipo_Pallet = OID_Tipo_Pallet;
  }

  public int getOID_Tipo_Pallet () {
    return OID_Tipo_Pallet;
  }

  public void setOID_Embalagem (int OID_Embalagem) {
    this.OID_Embalagem = OID_Embalagem;
  }

  public int getOID_Embalagem () {
    return OID_Embalagem;
  }

  public void setOID_Localizacao (int OID_Localizacao) {
    this.OID_Localizacao = OID_Localizacao;
  }

  public int getOID_Localizacao () {
    return OID_Localizacao;
  }

  public void setOID_Tipo_Estoque (int OID_Tipo_Estoque) {
    this.OID_Tipo_Estoque = OID_Tipo_Estoque;
  }

  public int getOID_Tipo_Estoque () {
    return OID_Tipo_Estoque;
  }

  public void setOID_Conta_Frete_Terceiros (int OID_Conta_Frete_Terceiros) {
    this.OID_Conta_Frete_Terceiros = OID_Conta_Frete_Terceiros;
  }

  public int getOID_Conta_Frete_Terceiros () {
    return OID_Conta_Frete_Terceiros;
  }

  public int getOID_Conta_Desconto_Frete () {
    return OID_Conta_Desconto_Frete;
  }

  public int getOID_Conta_Despesa_Bancaria () {
    return OID_Conta_Despesa_Bancaria;
  }

  public void setOID_Conta_Desconto_Frete (int OID_Conta_Desconto_Frete) {
    this.OID_Conta_Desconto_Frete = OID_Conta_Desconto_Frete;
  }

  public void setOID_Conta_Despesa_Bancaria (int OID_Conta_Despesa_Bancaria) {
    this.OID_Conta_Despesa_Bancaria = OID_Conta_Despesa_Bancaria;
  }

  public int getOID_Tipo_Instrucao_Estorno () {
    return OID_Tipo_Instrucao_Estorno;
  }

  public void setOID_Tipo_Instrucao_Estorno (int OID_Tipo_Instrucao_Estorno) {
    this.OID_Tipo_Instrucao_Estorno = OID_Tipo_Instrucao_Estorno;
  }

  public int getOID_Tipo_Ocorrencia_Cancelamento_CTRC () {
    return OID_Tipo_Ocorrencia_Cancelamento_CTRC;
  }

  public void setOID_Tipo_Ocorrencia_Cancelamento_CTRC (int OID_Tipo_Ocorrencia_Cancelamento_CTRC) {
    this.OID_Tipo_Ocorrencia_Cancelamento_CTRC = OID_Tipo_Ocorrencia_Cancelamento_CTRC;
  }

  public String getNM_Versao () {
    return NM_Versao;
  }

  public void setNM_Razao_Social_Empresa (String NM_Razao_Social_Empresa) {
    this.NM_Razao_Social_Empresa = NM_Razao_Social_Empresa;
  }

  public String getNM_Razao_Social_Empresa () {
    return NM_Razao_Social_Empresa;
  }

  public void setDM_Situacao (String DM_Situacao) {
    this.DM_Situacao = DM_Situacao;
  }

  public String getDM_Situacao () {
    return DM_Situacao;
  }

  public String getDM_Menu () {
    return "GMSUL".equals (getNM_Empresa ()) ? "../principal/gm/" : "../principal/" + getNM_Empresa ().toLowerCase () + "/";
  }

  public String getPATH_IMAGENS () {
    return PATH_IMAGENS;
  }

  public void setPATH_IMAGENS (String path_imagens) {
    PATH_IMAGENS = path_imagens;
  }

  public String getPATH_RELATORIOS () {
    return PATH_RELATORIOS;
  }

  public void setPATH_RELATORIOS (String path_relatorios) {
    PATH_RELATORIOS = path_relatorios;
  }

  public int getOid_Tipo_Documento_Nota_Fiscal_Entrada () {
    return oid_Tipo_Documento_Nota_Fiscal_Entrada;
  }

  public void setOid_Tipo_Documento_Nota_Fiscal_Entrada (int oid_Tipo_Documento_Nota_Fiscal_Entrada) {
    this.oid_Tipo_Documento_Nota_Fiscal_Entrada = oid_Tipo_Documento_Nota_Fiscal_Entrada;
  }

  public String getDM_Gera_Compromisso_Movimento_Ordem_Servico () {
    return DM_Gera_Compromisso_Movimento_Ordem_Servico;
  }

  public void setDM_Gera_Compromisso_Movimento_Ordem_Servico (String DM_Gera_Compromisso_Movimento_Ordem_Servico) {
    this.DM_Gera_Compromisso_Movimento_Ordem_Servico = DM_Gera_Compromisso_Movimento_Ordem_Servico;
  }

  public String getDM_Atualiza_Movimento_Caixa () {
    return DM_Atualiza_Movimento_Caixa;
  }

  public void setDM_Atualiza_Movimento_Caixa (String DM_Atualiza_Movimento_Caixa) {
    this.DM_Atualiza_Movimento_Caixa = DM_Atualiza_Movimento_Caixa;
  }

  public void setDM_Formulario_Duplicata (String DM_Formulario_Duplicata) {
    this.DM_Formulario_Duplicata = DM_Formulario_Duplicata;
  }

  public String getDM_Formulario_Duplicata () {
    return DM_Formulario_Duplicata;
  }

  public String getDM_Inclui_ICMS_Parcela_CTRC () {
    return DM_Inclui_ICMS_Parcela_CTRC;
  }

  public void setDM_Inclui_ICMS_Parcela_CTRC (String DM_Inclui_ICMS_Parcela_CTRC) {
    this.DM_Inclui_ICMS_Parcela_CTRC = DM_Inclui_ICMS_Parcela_CTRC;
  }

  public String getDM_Formulario_Coleta () {
    return DM_Formulario_Coleta;
  }

  public String getDM_Formulario_Conhecimento_Internacional () {
    return DM_Formulario_Conhecimento_Internacional;
  }

  public String getDM_Formulario_Conhecimento_Nacional () {
    return DM_Formulario_Conhecimento_Nacional;
  }

  public String getDM_Formulario_Manifesto () {
    return DM_Formulario_Manifesto;
  }

  public String getDM_Formulario_Nota_Fiscal () {
    return DM_Formulario_Nota_Fiscal;
  }

  public String getDM_Formulario_Ordem_Frete () {
    return DM_Formulario_Ordem_Frete;
  }

  public void setDM_Formulario_Ordem_Frete (String DM_Formulario_Ordem_Frete) {
    this.DM_Formulario_Ordem_Frete = DM_Formulario_Ordem_Frete;
  }

  public void setDM_Formulario_Nota_Fiscal (String DM_Formulario_Nota_Fiscal) {
    this.DM_Formulario_Nota_Fiscal = DM_Formulario_Nota_Fiscal;
  }

  public void setDM_Formulario_Manifesto (String DM_Formulario_Manifesto) {
    this.DM_Formulario_Manifesto = DM_Formulario_Manifesto;
  }

  public void setDM_Formulario_Conhecimento_Nacional (String DM_Formulario_Conhecimento_Nacional) {
    this.DM_Formulario_Conhecimento_Nacional = DM_Formulario_Conhecimento_Nacional;
  }

  public void setDM_Formulario_Conhecimento_Internacional (String DM_Formulario_Conhecimento_Internacional) {
    this.DM_Formulario_Conhecimento_Internacional = DM_Formulario_Conhecimento_Internacional;
  }

  public void setDM_Formulario_Coleta (String DM_Formulario_Coleta) {
    this.DM_Formulario_Coleta = DM_Formulario_Coleta;
  }

  public int getOid_Tipo_Documento_Nota_Fiscal_Saida () {
    return oid_Tipo_Documento_Nota_Fiscal_Saida;
  }

  public void setOid_Tipo_Documento_Nota_Fiscal_Saida (int oid_Tipo_Documento_Nota_Fiscal_Saida) {
    this.oid_Tipo_Documento_Nota_Fiscal_Saida = oid_Tipo_Documento_Nota_Fiscal_Saida;
  }

  public int getOid_Modelo_Nota_Fiscal () {
    return oid_Modelo_Nota_Fiscal;
  }

  public void setOid_Modelo_Nota_Fiscal (int oid_Modelo_Nota_Fiscal) {
    this.oid_Modelo_Nota_Fiscal = oid_Modelo_Nota_Fiscal;
  }

  public void setDM_Tipo_Sistema (String DM_Tipo_Sistema) {
    this.DM_Tipo_Sistema = DM_Tipo_Sistema;
  }

  public String getDM_Tipo_Sistema () {
    return DM_Tipo_Sistema;
  }

  public String getDM_Formulario_Ordem_Frete_Adiantamento () {
    return DM_Formulario_Ordem_Frete_Adiantamento;
  }

  public void setDM_Formulario_Ordem_Frete_Adiantamento (String DM_Formulario_Ordem_Frete_Adiantamento) {
    this.DM_Formulario_Ordem_Frete_Adiantamento = DM_Formulario_Ordem_Frete_Adiantamento;
  }

  public boolean isNF_Multi_Conhecimento () {
    return NF_Multi_Conhecimento;
  }

  public void setNF_Multi_Conhecimento (boolean NF_Multi_Conhecimento) {
    this.NF_Multi_Conhecimento = NF_Multi_Conhecimento;
  }

  public void setNR_Vias_Ordem_Frete_Adiantamento (long NR_Vias_Ordem_Frete_Adiantamento) {
    this.NR_Vias_Ordem_Frete_Adiantamento = NR_Vias_Ordem_Frete_Adiantamento;
  }

  public long getNR_Vias_Ordem_Frete_Adiantamento () {
    return NR_Vias_Ordem_Frete_Adiantamento;
  }

  /**
   * URL que o usuário usou para acessar o sistema (ex.:
   * http://localhost:8180/sistema/)
   */
  public String getUrl () {
    return "http://" + getServerName () + ":" + getPort () + getContexto () + "/";
  }

  /**
   * URL local para acessar o sistema (ex.: http://localhost:8180/sistema/)
   */
  public String getUrlLocal () {
    return "http://127.0.0.1:" + getPort () + getContexto () + "/";
  }

  public String getContexto () {
    return this.contexto;
  }

  public void setContexto (String contexto) {
    this.contexto = contexto;
  }

  public String getServerName () {
    return this.serverName;
  }

  public void setServerName (String serverName) {
    this.serverName = serverName;
  }

  public int getPort () {
    return this.port;
  }

  public int getOID_Tipo_Instrucao_Imposto_Retido2 () {
    return OID_Tipo_Instrucao_Imposto_Retido2;
  }

  public int getOID_Tipo_Instrucao_Imposto_Retido1 () {
    return OID_Tipo_Instrucao_Imposto_Retido1;
  }

  public String getDM_Operacao_Sistema () {
    return DM_Operacao_Sistema;
  }

  public String getDM_Perfil_Sistema () {
    return DM_Perfil_Sistema;
  }

  public String getDM_Formulario_Ordem_Servico () {
    return DM_Formulario_Ordem_Servico;
  }

  public void setPort (int port) {
    this.port = port;
  }

  public void setOID_Tipo_Instrucao_Imposto_Retido2 (int OID_Tipo_Instrucao_Imposto_Retido2) {
    this.OID_Tipo_Instrucao_Imposto_Retido2 = OID_Tipo_Instrucao_Imposto_Retido2;
  }

  public void setOID_Tipo_Instrucao_Imposto_Retido1 (int OID_Tipo_Instrucao_Imposto_Retido1) {
    this.OID_Tipo_Instrucao_Imposto_Retido1 = OID_Tipo_Instrucao_Imposto_Retido1;
  }

  public void setDM_Operacao_Sistema (String DM_Operacao_Sistema) {
    this.DM_Operacao_Sistema = DM_Operacao_Sistema;
  }

  public void setDM_Perfil_Sistema (String DM_Perfil_Sistema) {
    this.DM_Perfil_Sistema = DM_Perfil_Sistema;
  }

  public void setDM_Formulario_Ordem_Servico (String DM_Formulario_Ordem_Servico) {
    this.DM_Formulario_Ordem_Servico = DM_Formulario_Ordem_Servico;
  }

  public void setEDI_Seguradora (String EDI_Seguradora) {
    this.EDI_Seguradora = EDI_Seguradora;
  }

  public void setEDI_Duplicata (String EDI_Duplicata) {
    this.EDI_Duplicata = EDI_Duplicata;
  }

  public void setEDI_Fiscal (String EDI_Fiscal) {
    this.EDI_Fiscal = EDI_Fiscal;
  }

  public void setEDI_Contabil (String EDI_Contabil) {
    this.EDI_Contabil = EDI_Contabil;
  }

  public void setEDI_Cliente (String EDI_Cliente) {
    this.EDI_Cliente = EDI_Cliente;
  }

  public void setEDI_Banco (String EDI_Banco) {
    this.EDI_Banco = EDI_Banco;
  }

  public void setDM_Liquida_Compromisso (String DM_Liquida_Compromisso) {

    this.DM_Liquida_Compromisso = DM_Liquida_Compromisso;
  }

  public void setNM_Modelo_Tabela_Rodoviario (String NM_Modelo_Tabela_Rodoviario) {
    this.NM_Modelo_Tabela_Rodoviario = NM_Modelo_Tabela_Rodoviario;
  }

  public void setNM_Modelo_Tabela_Documento (String NM_Modelo_Tabela_Documento) {
    this.NM_Modelo_Tabela_Documento = NM_Modelo_Tabela_Documento;
  }

  public void setNM_Modelo_Tabela_Aereo (String NM_Modelo_Tabela_Aereo) {
    this.NM_Modelo_Tabela_Aereo = NM_Modelo_Tabela_Aereo;
  }

  public void setOID_Historico_Cancelamento_Lote_Pagamento (int OID_Historico_Cancelamento_Lote_Pagamento) {
    this.OID_Historico_Cancelamento_Lote_Pagamento = OID_Historico_Cancelamento_Lote_Pagamento;
  }

  public void setOID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico (long OID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico) {
    this.OID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico = OID_Tipo_Documento_Faturamento_Nota_Fiscal_Servico;
  }

  public void setDM_Formulario_Minuta (String DM_Formulario_Minuta) {
    this.DM_Formulario_Minuta = DM_Formulario_Minuta;
  }

  public void setOID_Historico_Lancamento_Ordem_Frete_Adiantamento (int OID_Historico_Lancamento_Ordem_Frete_Adiantamento) {
    this.OID_Historico_Lancamento_Ordem_Frete_Adiantamento = OID_Historico_Lancamento_Ordem_Frete_Adiantamento;
  }

  public void setOID_Historico_Lancamento_Ordem_Frete (int OID_Historico_Lancamento_Ordem_Frete) {
    this.OID_Historico_Lancamento_Ordem_Frete = OID_Historico_Lancamento_Ordem_Frete;
  }

  public void setDM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento (String DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento) {
    this.DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento = DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Adiantamento;
  }

  public void setDM_Gera_Lancamento_Conta_Corrente_Ordem_Frete (String DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete) {
    this.DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete = DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete;
  }

  public void setOID_Tipo_Instrucao_Devolucao_Nota_Fiscal (int OID_Tipo_Instrucao_Devolucao_Nota_Fiscal) {
    this.OID_Tipo_Instrucao_Devolucao_Nota_Fiscal = OID_Tipo_Instrucao_Devolucao_Nota_Fiscal;
    this.OID_Tipo_Instrucao_Devolucao_Nota_Fiscal = OID_Tipo_Instrucao_Devolucao_Nota_Fiscal;
  }

  public void setDM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro (String DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro) {
    this.DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro = DM_Gera_Lancamento_Conta_Corrente_Ordem_Frete_Terceiro;
  }

  public void setOID_Historico_Lancamento_Ordem_Frete_Terceiro (int OID_Historico_Lancamento_Ordem_Frete_Terceiro) {
    this.OID_Historico_Lancamento_Ordem_Frete_Terceiro = OID_Historico_Lancamento_Ordem_Frete_Terceiro;
  }

  public void setOID_Tipo_Servico_Acerto_Contas (int OID_Tipo_Servico_Acerto_Contas) {
    this.OID_Tipo_Servico_Acerto_Contas = OID_Tipo_Servico_Acerto_Contas;
  }

  public int getOID_Tipo_Servico_Acerto_Contas () {
    return OID_Tipo_Servico_Acerto_Contas;
  }

  public String getDM_Formulario_Acerto_Contas () {
    return DM_Formulario_Acerto_Contas;
  }

  public void setDM_Formulario_Acerto_Contas (String DM_Formulario_Acerto_Contas) {
    this.DM_Formulario_Acerto_Contas = DM_Formulario_Acerto_Contas;
  }

  public String getDM_Formulario_Conhecimento_Internacional_Verso () {
    return DM_Formulario_Conhecimento_Internacional_Verso;
  }

  public void setDM_Formulario_Conhecimento_Internacional_Verso (String formulario_Conhecimento_Internacional_Verso) {
    DM_Formulario_Conhecimento_Internacional_Verso = formulario_Conhecimento_Internacional_Verso;
  }

  public String getDM_Gera_Compromisso_Motorista_Proprietario () {
    return DM_Gera_Compromisso_Motorista_Proprietario;
  }

  public void setDM_Gera_Compromisso_Motorista_Proprietario (
      String gera_Compromisso_Motorista_Proprietario) {
    DM_Gera_Compromisso_Motorista_Proprietario = gera_Compromisso_Motorista_Proprietario;
  }


  public String getDM_Tranca_Saldo_Ordem_Frete () {
    return DM_Tranca_Saldo_Ordem_Frete;
  }

  public void setDM_Tranca_Saldo_Ordem_Frete (String tranca_Saldo_Ordem_Frete) {
    DM_Tranca_Saldo_Ordem_Frete = tranca_Saldo_Ordem_Frete;
  }

  public long getOid_Tipo_Pedido () {
    return oid_Tipo_Pedido;
  }

  public String getDM_Vincula_Adto_Ordem_Principal () {
    return DM_Vincula_Adto_Ordem_Principal;
  }

  public void setOid_Tipo_Pedido (long oid_Tipo_Pedido) {
    this.oid_Tipo_Pedido = oid_Tipo_Pedido;
  }

  public void setDM_Vincula_Adto_Ordem_Principal (String DM_Vincula_Adto_Ordem_Principal) {
    this.DM_Vincula_Adto_Ordem_Principal = DM_Vincula_Adto_Ordem_Principal;
  }

  public int getOID_Tipo_Faturamento_Real () {
    return OID_Tipo_Faturamento_Real;
  }

  public void setOID_Tipo_Faturamento_Real (int tipo_Faturamento_Real) {
    OID_Tipo_Faturamento_Real = tipo_Faturamento_Real;
  }

  public String getPalmModelo () {
    return palmModelo;
  }

  public void setPalmModelo (String palmModelo) {
    this.palmModelo = palmModelo;
  }

  public int getVias_Fatura () {
    return vias_Fatura;
  }

  public String getDM_Verifica_CNPJ_CPF_Pessoa () {
    return DM_Verifica_CNPJ_CPF_Pessoa;
  }

  public String getDM_Envia_Email_Eventos () {
    return DM_Envia_Email_Eventos;
  }

  public String getDM_Formulario_Romaneio_Notal_Fiscal () {

    return DM_Formulario_Romaneio_Notal_Fiscal;
  }

  public int getOID_Tipo_Movimento_Ajuste_S () {
    return OID_Tipo_Movimento_Ajuste_S;
  }

  public int getOID_Tipo_Movimento_Ajuste_E () {
    return OID_Tipo_Movimento_Ajuste_E;
  }

  public int getOID_Modal_Aereo_Sedex () {
    return OID_Modal_Aereo_Sedex;
  }

  public int getOID_Modal_Aereo_RodExp () {
    return OID_Modal_Aereo_RodExp;
  }

  public int getOID_Modal_Aereo_Pac () {
    return OID_Modal_Aereo_Pac;
  }

  public int getOID_Modal_Aereo_Expressa () {
    return OID_Modal_Aereo_Expressa;
  }

  public int getOID_Modal_Aereo_Emergencial () {
    return OID_Modal_Aereo_Emergencial;
  }

  public int getOID_Tipo_Ocorrencia_Reentrega_CTRC () {
    return OID_Tipo_Ocorrencia_Reentrega_CTRC;
  }

  public int getOID_Tipo_Ocorrencia_Devolucao_CTRC () {
    return OID_Tipo_Ocorrencia_Devolucao_CTRC;
  }

  public String getDM_Formulario_Ordem_Abastecimento () {
    return DM_Formulario_Ordem_Abastecimento;
  }
    public String getDM_Tipo_Impressao_Minuta () {
    return DM_Tipo_Impressao_Minuta;
  }

  public int getOID_Historico_Transferencia_Caixa () {
    return OID_Historico_Transferencia_Caixa;
  }

  public int getOID_Historico_Transferencia_Banco () {
    return OID_Historico_Transferencia_Banco;
  }

  public String getDM_Tipo_Impressao_Nota_Fiscal_Servico () {
    return DM_Tipo_Impressao_Nota_Fiscal_Servico;
  }

  public int getOID_Tipo_Servico_Abastecimento () {
    return OID_Tipo_Servico_Abastecimento;
  }

  public String getDM_Formulario_ACT () {
    return DM_Formulario_ACT;
  }

  public void setVias_Fatura (int vias_Fatura) {
    this.vias_Fatura = vias_Fatura;
  }

  public void setDM_Verifica_CNPJ_CPF_Pessoa (String DM_Verifica_CNPJ_CPF_Pessoa) {
    this.DM_Verifica_CNPJ_CPF_Pessoa = DM_Verifica_CNPJ_CPF_Pessoa;
  }

  public void setDM_Envia_Email_Eventos (String DM_Envia_Email_Eventos) {
    this.DM_Envia_Email_Eventos = DM_Envia_Email_Eventos;
  }

  public void setDM_Formulario_Romaneio_Notal_Fiscal (String DM_Formulario_Romaneio_Notal_Fiscal) {

    this.DM_Formulario_Romaneio_Notal_Fiscal = DM_Formulario_Romaneio_Notal_Fiscal;
  }

  public void setOID_Tipo_Movimento_Ajuste_S (int OID_Tipo_Movimento_Ajuste_S) {
    this.OID_Tipo_Movimento_Ajuste_S = OID_Tipo_Movimento_Ajuste_S;
  }

  public void setOID_Tipo_Movimento_Ajuste_E (int OID_Tipo_Movimento_Ajuste_E) {
    this.OID_Tipo_Movimento_Ajuste_E = OID_Tipo_Movimento_Ajuste_E;
  }

  public void setOID_Modal_Aereo_Sedex (int OID_Modal_Aereo_Sedex) {
    this.OID_Modal_Aereo_Sedex = OID_Modal_Aereo_Sedex;
  }

  public void setOID_Modal_Aereo_RodExp (int OID_Modal_Aereo_RodExp) {
    this.OID_Modal_Aereo_RodExp = OID_Modal_Aereo_RodExp;
  }

  public void setOID_Modal_Aereo_Pac (int OID_Modal_Aereo_Pac) {
    this.OID_Modal_Aereo_Pac = OID_Modal_Aereo_Pac;
  }

  public void setOID_Modal_Aereo_Expressa (int OID_Modal_Aereo_Expressa) {
    this.OID_Modal_Aereo_Expressa = OID_Modal_Aereo_Expressa;
  }

  public void setOID_Modal_Aereo_Emergencial (int OID_Modal_Aereo_Emergencial) {
    this.OID_Modal_Aereo_Emergencial = OID_Modal_Aereo_Emergencial;
  }


  public void setOID_Tipo_Ocorrencia_Reentrega_CTRC (int OID_Tipo_Ocorrencia_Reentrega_CTRC) {
    this.OID_Tipo_Ocorrencia_Reentrega_CTRC = OID_Tipo_Ocorrencia_Reentrega_CTRC;
  }

  public void setOID_Tipo_Ocorrencia_Devolucao_CTRC (int OID_Tipo_Ocorrencia_Devolucao_CTRC) {
    this.OID_Tipo_Ocorrencia_Devolucao_CTRC = OID_Tipo_Ocorrencia_Devolucao_CTRC;
  }

  public void setDM_Formulario_Ordem_Abastecimento (String DM_Formulario_Ordem_Abastecimento) {
    this.DM_Formulario_Ordem_Abastecimento = DM_Formulario_Ordem_Abastecimento;
  }

  public void setDM_Tipo_Impressao_Minuta (String DM_Tipo_Impressao_Minuta) {
    this.DM_Tipo_Impressao_Minuta = DM_Tipo_Impressao_Minuta;
  }

  public void setOID_Historico_Transferencia_Caixa (int OID_Historico_Transferencia_Caixa) {
    this.OID_Historico_Transferencia_Caixa = OID_Historico_Transferencia_Caixa;
  }

  public void setOID_Historico_Transferencia_Banco (int OID_Historico_Transferencia_Banco) {
    this.OID_Historico_Transferencia_Banco = OID_Historico_Transferencia_Banco;
  }

  public void setDM_Tipo_Impressao_Nota_Fiscal_Servico (String DM_Tipo_Impressao_Nota_Fiscal_Servico) {
    this.DM_Tipo_Impressao_Nota_Fiscal_Servico = DM_Tipo_Impressao_Nota_Fiscal_Servico;
  }

  public void setOID_Tipo_Servico_Abastecimento (int OID_Tipo_Servico_Abastecimento) {
    this.OID_Tipo_Servico_Abastecimento = OID_Tipo_Servico_Abastecimento;
  }

  public void setDM_Formulario_ACT (String DM_Formulario_ACT) {
    this.DM_Formulario_ACT = DM_Formulario_ACT;
  }

  public String getNM_Ano_Permisso () {
    return NM_Ano_Permisso;
  }

  public void setNM_Ano_Permisso (String ano_Permisso) {
    NM_Ano_Permisso = ano_Permisso;
  }

  public double getPE_Aliquota_PIS_COFINS () {
    return PE_Aliquota_PIS_COFINS;
  }

  public void setPE_Aliquota_PIS_COFINS (double aliquota_PIS_COFINS) {
    PE_Aliquota_PIS_COFINS = aliquota_PIS_COFINS;
  }

public String getDM_Gera_Livro_Fiscal() {
	return DM_Gera_Livro_Fiscal;
}
public void setDM_Gera_Livro_Fiscal(String gera_Livro_Fiscal) {
	DM_Gera_Livro_Fiscal = gera_Livro_Fiscal;
}

public String getDM_Formulario_Protocolo_Cobranca() {
	return DM_Formulario_Protocolo_Cobranca;
}

public void setDM_Formulario_Protocolo_Cobranca (String DM_Formulario_Protocolo_Cobranca) {
    this.DM_Formulario_Protocolo_Cobranca = DM_Formulario_Protocolo_Cobranca;
}

public String getDm_Wms_Nf_Saida_Numerada() {
	return dm_Wms_Nf_Saida_Numerada;
}

public String getDM_Tipo_Impressao_Ordem_Frete() {
	return DM_Tipo_Impressao_Ordem_Frete;
}

public void setDM_Tipo_Impressao_Ordem_Frete(String tipo_Impressao_Ordem_Frete) {
	DM_Tipo_Impressao_Ordem_Frete = tipo_Impressao_Ordem_Frete;
}


public String getPATH_RELATORIOS_XSL() {
	return PATH_RELATORIOS_XSL;
}

public void setPATH_RELATORIOS_XSL(String path_relatorios_xsl) {
	PATH_RELATORIOS_XSL = path_relatorios_xsl;
}

public String getDM_Calcula_Previsao_Entrega() {
	return DM_Calcula_Previsao_Entrega;
}

public void setDM_Calcula_Previsao_Entrega(String calcula_Prazo_Entrega) {
	DM_Calcula_Previsao_Entrega = calcula_Prazo_Entrega;
}

public String getDM_Soma_Carga_Saldo_Frete() {
	return DM_Soma_Carga_Saldo_Frete;
}

public void setDM_Soma_Carga_Saldo_Frete(String soma_Carga_Saldo_Frete) {
	DM_Soma_Carga_Saldo_Frete = soma_Carga_Saldo_Frete;
}

public String getDM_Soma_Coleta_Saldo_Frete() {
	return DM_Soma_Coleta_Saldo_Frete;
}

public void setDM_Soma_Coleta_Saldo_Frete(String soma_Coleta_Saldo_Frete) {
	DM_Soma_Coleta_Saldo_Frete = soma_Coleta_Saldo_Frete;
}

public String getDM_Soma_Descarga_Saldo_Frete() {
	return DM_Soma_Descarga_Saldo_Frete;
}

public void setDM_Soma_Descarga_Saldo_Frete(String soma_Descarga_Saldo_Frete) {
	DM_Soma_Descarga_Saldo_Frete = soma_Descarga_Saldo_Frete;
}

public String getDM_Soma_Descontos_Saldo_Frete() {
	return DM_Soma_Descontos_Saldo_Frete;
}

public void setDM_Soma_Descontos_Saldo_Frete(String soma_Descontos_Saldo_Frete) {
	DM_Soma_Descontos_Saldo_Frete = soma_Descontos_Saldo_Frete;
}

public String getDM_Soma_Premio_Saldo_Frete() {
	return DM_Soma_Premio_Saldo_Frete;
}

public void setDM_Soma_Premio_Saldo_Frete(String soma_Premio_Saldo_Frete) {
	DM_Soma_Premio_Saldo_Frete = soma_Premio_Saldo_Frete;
}
public String getDM_Dia_Nao_Util() {
	return DM_Dia_Nao_Util;
}

public void setDM_Dia_Nao_Util(String dia_Util_Feriado) {
	DM_Dia_Nao_Util = dia_Util_Feriado;
}

public String getDT_Inicial_Previsao_Entrega() {
	return DT_Inicial_Previsao_Entrega;
}

public void setDT_Inicial_Previsao_Entrega(
		String data_Inicial_Previsao_Entrega) {
	DT_Inicial_Previsao_Entrega = data_Inicial_Previsao_Entrega;
}

public String getDM_Comissao_Informada() {
	return DM_Comissao_Informada;
}

public void setDM_Comissao_Informada(String comissao_Informada) {
	DM_Comissao_Informada = comissao_Informada;
}

public String getDM_Gera_Lancamento_Contabil_Acerto() {
	return DM_Gera_Lancamento_Contabil_Acerto;
}

public void setDM_Gera_Lancamento_Contabil_Acerto(
		String gera_Lancamento_Contabil_Acerto) {
	DM_Gera_Lancamento_Contabil_Acerto = gera_Lancamento_Contabil_Acerto;
}

public boolean isExigePedidoNotaCompra() {
	return exigePedidoNotaCompra;
}

public void setExigePedidoNotaCompra(boolean exigePedidoNotaCompra) {
	this.exigePedidoNotaCompra = exigePedidoNotaCompra;
}

public String getOID_Fornecedor_COFINS() {
	return OID_Fornecedor_COFINS;
}

public void setOID_Fornecedor_COFINS(String fornecedor_COFINS) {
	OID_Fornecedor_COFINS = fornecedor_COFINS;
}

public String getOID_Fornecedor_CSLL() {
	return OID_Fornecedor_CSLL;
}

public void setOID_Fornecedor_CSLL(String fornecedor_CSLL) {
	OID_Fornecedor_CSLL = fornecedor_CSLL;
}

public String getOID_Fornecedor_ISSQN() {
	return OID_Fornecedor_ISSQN;
}

public void setOID_Fornecedor_ISSQN(String fornecedor_ISSQN) {
	OID_Fornecedor_ISSQN = fornecedor_ISSQN;
}

public String getOID_Fornecedor_PIS() {
	return OID_Fornecedor_PIS;
}

public void setOID_Fornecedor_PIS(String fornecedor_PIS) {
	OID_Fornecedor_PIS = fornecedor_PIS;
}

public String getDM_Inclui_Adiantamento_Acerto() {
	return DM_Inclui_Adiantamento_Acerto;
}

public void setDM_Inclui_Adiantamento_Acerto(String inclui_Adiantamento_Acerto) {
	DM_Inclui_Adiantamento_Acerto = inclui_Adiantamento_Acerto;
}

public String getDM_Inclui_Conhecimento_Acerto() {
	return DM_Inclui_Conhecimento_Acerto;
}

public void setDM_Inclui_Conhecimento_Acerto(String inclui_Conhecimento_Acerto) {
	DM_Inclui_Conhecimento_Acerto = inclui_Conhecimento_Acerto;
}

public String getDM_Inclui_Ordem_Frete_Acerto() {
	return DM_Inclui_Ordem_Frete_Acerto;
}

public void setDM_Inclui_Ordem_Frete_Acerto(String inclui_Ordem_Frete_Acerto) {
	DM_Inclui_Ordem_Frete_Acerto = inclui_Ordem_Frete_Acerto;
}

public String getDM_Inclui_Ordem_Frete_Terceiro_Acerto() {
	return DM_Inclui_Ordem_Frete_Terceiro_Acerto;
}

public void setDM_Inclui_Ordem_Frete_Terceiro_Acerto(
		String inclui_Ordem_Frete_Terceiro_Acerto) {
	DM_Inclui_Ordem_Frete_Terceiro_Acerto = inclui_Ordem_Frete_Terceiro_Acerto;
}

public String getDM_Data_Adiantamento_Acerto() {
	return DM_Data_Adiantamento_Acerto;
}

public void setDM_Data_Adiantamento_Acerto(String data_Adiantamento_Acerto) {
	DM_Data_Adiantamento_Acerto = data_Adiantamento_Acerto;
}

public String getDM_Data_Conhecimento_Acerto() {
	return DM_Data_Conhecimento_Acerto;
}

public void setDM_Data_Conhecimento_Acerto(String data_Conhecimento_Acerto) {
	DM_Data_Conhecimento_Acerto = data_Conhecimento_Acerto;
}

public String getDM_Data_Ordem_Frete_Acerto() {
	return DM_Data_Ordem_Frete_Acerto;
}

public void setDM_Data_Ordem_Frete_Acerto(String data_Ordem_Frete_Acerto) {
	DM_Data_Ordem_Frete_Acerto = data_Ordem_Frete_Acerto;
}

public String getDM_Data_Ordem_Frete_Terceiro_Acerto() {
	return DM_Data_Ordem_Frete_Terceiro_Acerto;
}

public void setDM_Data_Ordem_Frete_Terceiro_Acerto(
		String data_Ordem_Frete_Terceiro_Acerto) {
	DM_Data_Ordem_Frete_Terceiro_Acerto = data_Ordem_Frete_Terceiro_Acerto;
}

public String getDM_Formulario_Frete_Terceiro() {
	return DM_Formulario_Frete_Terceiro;
}

public void setDM_Formulario_Frete_Terceiro(String formulario_Frete_Terceiro) {
	DM_Formulario_Frete_Terceiro = formulario_Frete_Terceiro;
}


public String getDM_Data_Nota_Fiscal_Servico_Acerto() {
	return DM_Data_Nota_Fiscal_Servico_Acerto;
}

public void setDM_Data_Nota_Fiscal_Servico_Acerto(
		String data_Nota_Fiscal_Servico_Acerto) {
	DM_Data_Nota_Fiscal_Servico_Acerto = data_Nota_Fiscal_Servico_Acerto;
}

public String getDM_Inclui_Nota_Fiscal_Servico_Acerto() {
	return DM_Inclui_Nota_Fiscal_Servico_Acerto;
}

public void setDM_Inclui_Nota_Fiscal_Servico_Acerto(
		String inclui_Nota_Fiscal_Servico_Acerto) {
	DM_Inclui_Nota_Fiscal_Servico_Acerto = inclui_Nota_Fiscal_Servico_Acerto;
}

private Base_EmpresaED base_EmpresaED = new Base_EmpresaED ();

  public Parametro_FixoED () {

    if ("GTT".equals (getNM_Empresa ())) {
        if ("SERVIDOR".equals (getNM_Base ())) {
          setNM_Database_Host ("//127.0.0.1:5433");
          setNM_Database_Port (5433);
          setNM_Database_URL ("jdbc:postgresql://127.0.0.1:5433/tipler");
          setNM_Database_Driver ("org.postgresql.Driver");
          setNM_Database_Usuario ("postgres");
          setNM_Database_Pwd ("");
          PATH_RELATORIOS = "/data/sgtt/relatoriosJasper/";
          PATH_IMAGENS = "/data/sgtt/relatoriosJasper/imagens/";
          appPath = "/data/sgtt";
          System.setProperty ("pathImgBanda" , "/data/sgtt/Imagens/bandas/");
        }
        else if ("REGIS".equals (getNM_Base ())) {
          setNM_Database_Host ("//127.0.0.1:5433");
          setNM_Database_Port (5433);
          setNM_Database_URL ("jdbc:postgresql://127.0.0.1:5433/tipler");
          setNM_Database_Driver ("org.postgresql.Driver");
          setNM_Database_Usuario ("postgres");
          setNM_Database_Pwd ("postgres");
          PATH_RELATORIOS = "C:/cpoint/workspace/gtt/webroot/relatoriosJasper/";
          PATH_IMAGENS = "C:/cpoint/workspace/gtt/webroot/relatoriosJasper/imagens/";
          appPath = "C:/cpoint/workspace/gtt/webroot/";
          System.setProperty ("pathImgBanda" , "C:/cpoint/workspace/gtt/webroot/Imagens/bandas/");
        }
        else if ("DEMO".equals (getNM_Base ())) {
            setNM_Database_Host ("//127.0.0.1:5433");
            setNM_Database_Port (5433);
            setNM_Database_URL ("jdbc:postgresql://127.0.0.1:5433/tiplerdemo");
            setNM_Database_Driver ("org.postgresql.Driver");
            setNM_Database_Usuario ("postgres");
            setNM_Database_Pwd ("postgres");
            PATH_RELATORIOS = "/data/sgttdemo/relatoriosJasper/";
            PATH_IMAGENS = "/data/sgttdemo/relatoriosJasper/imagens/";
            appPath = "/data/sgttdemo/";
            System.setProperty ("pathImgBanda" , "Imagens/imgBanda/");
          }
      } else
      if ("STCB".equals (getNM_Empresa ())) {
    	  if ("SERVIDOR".equals (getNM_Base ())) {
    		  setNM_Database_Host ("//127.0.0.1:5433");
	          setNM_Database_Port (5433);
	          setNM_Database_URL ("jdbc:postgresql://127.0.0.1:5433/tipler");
	          setNM_Database_Driver ("org.postgresql.Driver");
	          setNM_Database_Usuario ("postgres");
	          setNM_Database_Pwd ("");
	          PATH_RELATORIOS = "/data/stcb/relatoriosJasper/";
	          PATH_IMAGENS = "/data/stcb/relatoriosJasper/imagens/";
	          appPath = "/data/stcb";
	          System.setProperty ("pathImgBanda" , "/data/stcb/Imagens/bandas/");
    	  }
      }
    	  
    
  }

public String getAppPath() {
	return appPath;
}

public void setAppPath(String appPath) {
	this.appPath = appPath;
}
   
}


