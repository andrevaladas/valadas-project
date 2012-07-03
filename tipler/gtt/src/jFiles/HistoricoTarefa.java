/*
 * HistoricoTarefa.java
 *
 * Created on 26 de Julho de 2004, 14:51
 */

package jFiles;

/**
 *
 * @author  administrador
 */
public class HistoricoTarefa {
  Long idHistorico;
  Long idTarefa;
  String acao;
  String data;
  String hora;
  String descricao;
  String nm_usuario;
    
    /** Creates a new instance of HistoricoTarefa */
    public HistoricoTarefa() {
    }
    
    /** Getter for property idHistorico.
     * @return Value of property idHistorico.
     *
     */
    public final java.lang.Long getIdHistorico() {
        return idHistorico;
    }    

    /** Setter for property idHistorico.
     * @param idHistorico New value of property idHistorico.
     *
     */
    public final void setIdHistorico(java.lang.Long idHistorico) {
        this.idHistorico = idHistorico;
    }    

    /** Getter for property idTarefa.
     * @return Value of property idTarefa.
     *
     */
    public final java.lang.Long getIdTarefa() {
        return idTarefa;
    }
    
    /** Setter for property idTarefa.
     * @param idTarefa New value of property idTarefa.
     *
     */
    public final void setIdTarefa(java.lang.Long idTarefa) {
        this.idTarefa = idTarefa;
    }
    
    /** Getter for property acao.
     * @return Value of property acao.
     *
     */
    public final java.lang.String getAcao() {
        return acao;
    }
    
    /** Setter for property acao.
     * @param acao New value of property acao.
     *
     */
    public final void setAcao(java.lang.String acao) {
        this.acao = acao;
    }
    
    /** Getter for property data.
     * @return Value of property data.
     *
     */
    public final java.lang.String getData() {
        return data;
    }
    
    /** Setter for property data.
     * @param data New value of property data.
     *
     */
    public final void setData(java.lang.String data) {
        this.data = data;
    }
    
    /** Getter for property hora.
     * @return Value of property hora.
     *
     */
    public final java.lang.String getHora() {
        return hora;
    }
    
    /** Setter for property hora.
     * @param hora New value of property hora.
     *
     */
    public final void setHora(java.lang.String hora) {
        this.hora = hora;
    }
    
    /** Getter for property descricao.
     * @return Value of property descricao.
     *
     */
    public final java.lang.String getDescricao() {
        return descricao;
    }
    
    /** Setter for property descricao.
     * @param descricao New value of property descricao.
     *
     */
    public final void setDescricao(java.lang.String descricao) {
        this.descricao = descricao;
    }
    
    /** Getter for property nm_usuario.
     * @return Value of property nm_usuario.
     *
     */
    public final java.lang.String getNm_usuario() {
        return nm_usuario;
    }
    
    /** Setter for property nm_usuario.
     * @param nm_usuario New value of property nm_usuario.
     *
     */
    public final void setNm_usuario(java.lang.String nm_usuario) {
        this.nm_usuario = nm_usuario;
    }
    
}
