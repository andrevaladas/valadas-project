/*
 * Created on 05/04/2005
 *
 */
package com.master.ed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Tiago
 *
 */
public abstract class RelatorioBaseED extends MasterED {

	private static final long serialVersionUID = 6323428549353921245L;

	public RelatorioBaseED() {
        super();
    }
    public RelatorioBaseED(HttpServletResponse response, String nomeRelatorio) {
        this.response = response;
        this.nomeRelatorio = nomeRelatorio;
    }
    
    /** Variáveis do Jasper **/
    @SuppressWarnings("unchecked")
	private HashMap hashMap; //(OPCIONAL) caso queira incluir algum Parametro diferentes dos DEFAULT(ja vem setado por DEFAULT o PATH_IMAGENS e RELATORIO)
    @SuppressWarnings("unchecked")
	private List lista; //(OBRIGATÓRIA)
    @SuppressWarnings("unchecked")
	private Collection sublista = new ArrayList(); //Para subrelatórios
    @SuppressWarnings("unchecked")
	private Collection sublista1 = new ArrayList(); //Para subrelatórios
    @SuppressWarnings("unchecked")
	private Collection sublista2 = new ArrayList(); //Para subrelatórios
    private HttpServletResponse response; //(OBRIGATÓRIA)
    private HttpServletRequest request; //(OBRIGATÓRIA para o LASZLO)
    private String nomeRelatorio; //(OBRIGATÓRIA)
    private String descFiltro;
    
    //*** Layout
    private String Layout;

    public String getLayout() {
        return Layout;
    }
    public void setLayout(String layout) {
        Layout = layout;
    }
    @SuppressWarnings("unchecked")
	public HashMap getHashMap() {
        return hashMap;
    }
    @SuppressWarnings("unchecked")
	public List getLista() {
        return lista;
    }
    public String getNomeRelatorio() {
        return nomeRelatorio;
    }
    public HttpServletResponse getResponse() {
        return response;
    }
    @SuppressWarnings("unchecked")
	public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }
    @SuppressWarnings("unchecked")
	public void setLista(List lista) {
        this.lista = lista;
    }
    public void setNomeRelatorio(String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    public String getDescFiltro() {
        return this.descFiltro;
    }
    public void setDescFiltro(String descFiltro) {
        this.descFiltro = descFiltro;
    }
    @SuppressWarnings("unchecked")
	public Collection getSublista() {
        return sublista;
    }
    
    @SuppressWarnings("unchecked")
	public void setSublista(Collection sublista) {
        this.sublista = sublista;
    }
    @SuppressWarnings("unchecked")
    public Collection getSublista2() {
        return sublista2;
    }
    @SuppressWarnings("unchecked")
    public void setSublista2(Collection sublista2) {
        this.sublista2 = sublista2;
    }
    @SuppressWarnings("unchecked")
	public Collection getSublista1() {
		return sublista1;
	}
    @SuppressWarnings("unchecked")
	public void setSublista1(Collection sublista1) {
		this.sublista1 = sublista1;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
