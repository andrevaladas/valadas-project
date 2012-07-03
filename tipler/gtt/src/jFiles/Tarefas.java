//Source file: C:\\eclipse\\workspace\\BugTracking\\src\\jFiles\\Tarefas.java

package jFiles;

import java.util.*;

public class Tarefas 
{
   //- Dados Tarefa
   private Long idTarefa;
   private String titulo;
   private Long idTipoSolicitacao;
   private Long idClassificacaoUsuario;
   private Long idClassificacaoAdmin;
   private String operacaoImpedida;
   private String caminhoMenu;
   private String dtSolicitacao;
   private String hrSolicitacao;
   private Long idUsuarioSolicitacao;
   private Long idSituacaoTarefa;
   private String descricao;
   private String dtOcorrencia;
   private String hrOcorrencia;
   private Long idUsuarioCorrecao;
   private String  nmUsuarioAlteracao;
   
   private String novaDescricao; //Este campo será utilizado p/ histórico.

   //- Campos p/ abertura -
   private String dataPrevista;
   private String dataIni;
   private String horaEstimada;
   private String valor;
   private String centroCusto;

   //- Campos correção
   private String horaUtilizada;
   private String dataFim;
   

   private Double tempoCorrecao;
   
   //- Nome do arquivo em anexo
   private String anexo;
   
/**
 * @return Returns the caminhoMenu.
 */
public final String getCaminhoMenu() {
	return caminhoMenu;
}
/**
 * @param caminhoMenu The caminhoMenu to set.
 */
public final void setCaminhoMenu(String caminhoMenu) {
	this.caminhoMenu = caminhoMenu;
}
/**
 * @return Returns the dataFim.
 */
public final String getDataFim() {
	return dataFim;
}
/**
 * @param dataFim The dataFim to set.
 */
public final void setDataFim(String dataFim) {
	this.dataFim = dataFim;
}
/**
 * @return Returns the dataIni.
 */
public final String getDataIni() {
	return dataIni;
}
/**
 * @param dataIni The dataIni to set.
 */
public final void setDataIni(String dataIni) {
	this.dataIni = dataIni;
}
/**
 * @return Returns the descricao.
 */
public final String getDescricao() {
	return descricao;
}
/**
 * @param descricao The descricao to set.
 */
public final void setDescricao(String descricao) {
	this.descricao = descricao;
}
/**
 * @return Returns the dtOcorrencia.
 */
public final String getDtOcorrencia() {
	return dtOcorrencia;
}
/**
 * @param dtOcorrencia The dtOcorrencia to set.
 */
public final void setDtOcorrencia(String dtOcorrencia) {
	this.dtOcorrencia = dtOcorrencia;
}
/**
 * @return Returns the dtSolicitacao.
 */
public final String getDtSolicitacao() {
	return dtSolicitacao;
}
/**
 * @param dtSolicitacao The dtSolicitacao to set.
 */
public final void setDtSolicitacao(String dtSolicitacao) {
	this.dtSolicitacao = dtSolicitacao;
}
/**
 * @return Returns the hrOcorrencia.
 */
public final String getHrOcorrencia() {
	return hrOcorrencia;
}
/**
 * @param hrOcorrencia The hrOcorrencia to set.
 */
public final void setHrOcorrencia(String hrOcorrencia) {
	this.hrOcorrencia = hrOcorrencia;
}
/**
 * @return Returns the hrSolicitacao.
 */
public final String getHrSolicitacao() {
	return hrSolicitacao;
}
/**
 * @param hrSolicitacao The hrSolicitacao to set.
 */
public final void setHrSolicitacao(String hrSolicitacao) {
	this.hrSolicitacao = hrSolicitacao;
}
/**
 * @return Returns the idClassificacaoAdmin.
 */
public final Long getIdClassificacaoAdmin() {
	return idClassificacaoAdmin;
}
/**
 * @param idClassificacaoAdmin The idClassificacaoAdmin to set.
 */
public final void setIdClassificacaoAdmin(Long idClassificacaoAdmin) {
	this.idClassificacaoAdmin = idClassificacaoAdmin;
}
/**
 * @return Returns the idClassificacaoUsuario.
 */
public final Long getIdClassificacaoUsuario() {
	return idClassificacaoUsuario;
}
/**
 * @param idClassificacaoUsuario The idClassificacaoUsuario to set.
 */
public final void setIdClassificacaoUsuario(Long idClassificacaoUsuario) {
	this.idClassificacaoUsuario = idClassificacaoUsuario;
}
/**
 * @return Returns the idSituacaoTarefa.
 */
public final Long getIdSituacaoTarefa() {
	return idSituacaoTarefa;
}
/**
 * @param idSituacaoTarefa The idSituacaoTarefa to set.
 */
public final void setIdSituacaoTarefa(Long idSituacaoTarefa) {
	this.idSituacaoTarefa = idSituacaoTarefa;
}
/**
 * @return Returns the idTarefa.
 */
public final Long getIdTarefa() {
	return idTarefa;
}
/**
 * @param idTarefa The idTarefa to set.
 */
public final void setIdTarefa(Long idTarefa) {
	this.idTarefa = idTarefa;
}
/**
 * @return Returns the idTipoSolicitacao.
 */
public final Long getIdTipoSolicitacao() {
	return idTipoSolicitacao;
}
/**
 * @param idTipoSolicitacao The idTipoSolicitacao to set.
 */
public final void setIdTipoSolicitacao(Long idTipoSolicitacao) {
	this.idTipoSolicitacao = idTipoSolicitacao;
}
/**
 * @return Returns the idUsuarioCorrecao.
 */
public final Long getIdUsuarioCorrecao() {
	return idUsuarioCorrecao;
}
/**
 * @param idUsuarioCorrecao The idUsuarioCorrecao to set.
 */
public final void setIdUsuarioCorrecao(Long idUsuarioCorrecao) {
	this.idUsuarioCorrecao = idUsuarioCorrecao;
}
/**
 * @return Returns the idUsuarioSolicitacao.
 */
public final Long getIdUsuarioSolicitacao() {
	return idUsuarioSolicitacao;
}
/**
 * @param idUsuarioSolicitacao The idUsuarioSolicitacao to set.
 */
public final void setIdUsuarioSolicitacao(Long idUsuarioSolicitacao) {
	this.idUsuarioSolicitacao = idUsuarioSolicitacao;
}
/**
 * @return Returns the operacaoImpedida.
 */
public final String getOperacaoImpedida() {
	return operacaoImpedida;
}
/**
 * @param operacaoImpedida The operacaoImpedida to set.
 */
public final void setOperacaoImpedida(String operacaoImpedida) {
	this.operacaoImpedida = operacaoImpedida;
}
/**
 * @return Returns the tempoCorrecao.
 */
public final Double getTempoCorrecao() {
	return tempoCorrecao;
}
/**
 * @param tempoCorrecao The tempoCorrecao to set.
 */
public final void setTempoCorrecao(Double tempoCorrecao) {
	this.tempoCorrecao = tempoCorrecao;
}
/**
 * @return Returns the titulo.
 */
public final String getTitulo() {
	return titulo;
}
/**
 * @param titulo The titulo to set.
 */
public final void setTitulo(String titulo) {
	this.titulo = titulo;
}

/** Getter for property anexo.
 * @return Value of property anexo.
 *
 */
public final java.lang.String getAnexo() {
    return anexo;
}

/** Setter for property anexo.
 * @param anexo New value of property anexo.
 *
 */
public final void setAnexo(java.lang.String anexo) {
    this.anexo = anexo;
}

/** Getter for property novaDescricao.
 * @return Value of property novaDescricao.
 *
 */
public final java.lang.String getNovaDescricao() {
    return novaDescricao;
}

/** Setter for property novaDescricao.
 * @param novaDescricao New value of property novaDescricao.
 *
 */
public final void setNovaDescricao(java.lang.String novaDescricao) {
    this.novaDescricao = novaDescricao;
}

/** Getter for property dataPrevista.
 * @return Value of property dataPrevista.
 *
 */
public final java.lang.String getDataPrevista() {
    return dataPrevista;
}

/** Setter for property dataPrevista.
 * @param dataPrevista New value of property dataPrevista.
 *
 */
public final void setDataPrevista(java.lang.String dataPrevista) {
    this.dataPrevista = dataPrevista;
}

/** Getter for property horaEstimada.
 * @return Value of property horaEstimada.
 *
 */
public final java.lang.String getHoraEstimada() {
    return horaEstimada;
}

/** Setter for property horaEstimada.
 * @param horaEstimada New value of property horaEstimada.
 *
 */
public final void setHoraEstimada(java.lang.String horaEstimada) {
    this.horaEstimada = horaEstimada;
}

/** Getter for property valor.
 * @return Value of property valor.
 *
 */
public final java.lang.String getValor() {
    return valor;
}

/** Setter for property valor.
 * @param valor New value of property valor.
 *
 */
public final void setValor(java.lang.String valor) {
    this.valor = valor;
}

/** Getter for property centroCusto.
 * @return Value of property centroCusto.
 *
 */
public final java.lang.String getCentroCusto() {
    return centroCusto;
}

/** Setter for property centroCusto.
 * @param centroCusto New value of property centroCusto.
 *
 */
public final void setCentroCusto(java.lang.String centroCusto) {
    this.centroCusto = centroCusto;
}

/** Getter for property nmUsuarioAlteracao.
 * @return Value of property nmUsuarioAlteracao.
 *
 */
public final java.lang.String getNmUsuarioAlteracao() {
    return nmUsuarioAlteracao;
}

/** Setter for property nmUsuarioAlteracao.
 * @param nmUsuarioAlteracao New value of property nmUsuarioAlteracao.
 *
 */
public final void setNmUsuarioAlteracao(java.lang.String nmUsuarioAlteracao) {
    this.nmUsuarioAlteracao = nmUsuarioAlteracao;
}

}
