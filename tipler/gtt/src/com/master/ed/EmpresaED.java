package com.master.ed;

import com.master.rn.EmpresaRN;
import com.master.util.Excecoes;

/**
 * @author Regis
 * @serial Empresas
 * @serialData 06/2009
 */
public class EmpresaED extends RelatorioBaseED {

	private static final long serialVersionUID = 2197194940022060118L;

	public EmpresaED() {
	}
	
	private long oid_Empresa;
	private long oid_Regional;
	private String nr_Cnpj;
    private String nm_Razao_Social;
    private String nm_Endereco;
    private String nm_Bairro;
    private String nr_Cep;
    private String nm_Inscricao_Estadual;
    private String nm_Cidade;    
    private String cd_Estado;
    private String cd_Pais;
    private String nr_Telefone;
    private String nr_Fax;
    private String nm_Email;
    private String nm_Contato;
    private String dm_Tipo_Frota;
    private String dm_Tipo_Empresa; // "T" Tipler, "C" Concessionarios, "U" Clientes usuários
    private long oid_Concessionaria;
    private long oid_Cliente;
    private long oid_Empresa_Gambiarra; // Isso existe porque o OLUtils  coloca sempre em oid_Empresa o oid da empresa logada.
    
    private transient String nm_Concessionaria;
    private transient String nm_Tipo_Frota;

    private transient String nr_Cnpj_Formatado;
    
    private transient String nm_Regional;
    
    // Campo só para os acessos - Identifica que tipo de empresa está acessando T-Tipler, C-Concessionária, U-Empresa cliente.
    private transient String dm_Tipo_Consulta;
    
    private transient String dm_Modulo;
    
	public String getCd_Estado() {
		return cd_Estado;
	}
	public void setCd_Estado(String cd_Estado) {
		this.cd_Estado = cd_Estado;
	}
	public String getNm_Bairro() {
		return nm_Bairro;
	}
	public void setNm_Bairro(String nm_Bairro) {
		this.nm_Bairro = nm_Bairro;
	}
	public String getNm_Cidade() {
		return nm_Cidade;
	}
	public void setNm_Cidade(String nm_Cidade) {
		this.nm_Cidade = nm_Cidade;
	}
	public String getNm_Endereco() {
		return nm_Endereco;
	}
	public void setNm_Endereco(String nm_Endereco) {
		this.nm_Endereco = nm_Endereco;
	}
	public String getNm_Inscricao_Estadual() {
		return nm_Inscricao_Estadual;
	}
	public void setNm_Inscricao_Estadual(String nm_Inscricao_Estadual) {
		this.nm_Inscricao_Estadual = nm_Inscricao_Estadual;
	}
	public String getNm_Razao_Social() {
		return nm_Razao_Social;
	}
	public void setNm_Razao_Social(String nm_Razao_Social) {
		this.nm_Razao_Social = nm_Razao_Social;
	}
	public String getNr_Cep() {
		return nr_Cep;
	}
	public void setNr_Cep(String nr_Cep) {
		this.nr_Cep = nr_Cep;
	}
	public String getNr_Fax() {
		return nr_Fax;
	}
	public void setNr_Fax(String nr_Fax) {
		this.nr_Fax = nr_Fax;
	}
	public String getNr_Telefone() {
		return nr_Telefone;
	}
	public void setNr_Telefone(String nr_Telefone) {
		this.nr_Telefone = nr_Telefone;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getDm_Tipo_Frota() {
		return dm_Tipo_Frota;
	}
	public void setDm_Tipo_Frota(String dm_Tipo_Frota) {
		this.dm_Tipo_Frota = dm_Tipo_Frota;
	}
	public String getNm_Tipo_Frota() {
		return nm_Tipo_Frota;
	}
	public void setNm_Tipo_Frota(String nm_Tipo_Frota) {
		this.nm_Tipo_Frota = nm_Tipo_Frota;
	}
	public String getNr_Cnpj() {
		return nr_Cnpj;
	}
	public void setNr_Cnpj(String nr_Cnpj) {
		this.nr_Cnpj = nr_Cnpj;
	}
	public long getOid_Empresa() {
		return oid_Empresa;
	}
	public void setOid_Empresa(long oid_Empresa) {
		this.oid_Empresa = oid_Empresa;
	}
	public String getNm_Email() {
		return nm_Email;
	}
	public void setNm_Email(String nm_Email) {
		this.nm_Email = nm_Email;
	}
	public String getNm_Contato() {
		return nm_Contato;
	}
	public void setNm_Contato(String nm_Contato) {
		this.nm_Contato = nm_Contato;
	}
	public String getNm_Concessionaria() {
		return nm_Concessionaria;
	}
	public void setNm_Concessionaria(String nm_Concessionaria) {
		this.nm_Concessionaria = nm_Concessionaria;
	}
	public String getDm_Tipo_Empresa() {
		return dm_Tipo_Empresa;
	}
	public void setDm_Tipo_Empresa(String dm_Tipo_Empresa) {
		this.dm_Tipo_Empresa = dm_Tipo_Empresa;
	}
	public long getOid_Concessionaria() {
		return oid_Concessionaria;
	}
	public void setOid_Concessionaria(long oid_Concessionaria) {
		this.oid_Concessionaria = oid_Concessionaria;
	}

	/***
	 * isCodigoExistente()
	 * Verifica se o codigo deste ed já existe cadastrado 
	 * @return boolean
	 * @throws Excecoes
	 */
	public boolean isCodigoExistente()  throws Excecoes {
		boolean dmVolta = true;
		EmpresaED ed = new EmpresaED();   	// Instancia ed para a busca
		ed.setNr_Cnpj(this.getNr_Cnpj()); 				// Busca o registro
		ed = new EmpresaRN().getByRecord(ed);      // Retorna o registro no mesmo ed
		if (ed.getOid_Empresa()== 0){                   // Se registro não existir retorna false
			dmVolta = false;
		}else{
			if (ed.getOid_Empresa()== this.getOid_Empresa()){ 	// Se registro é o mesmo oid retorna false	
				dmVolta =  false;
			}	
		}
		return dmVolta;
	}
	
	/**
	 * isIncluidoOK
	 * Verifica se a empresa já está cadastrada
	 * Retorna false se existir e não inclui
	 * @return boolean
	 * @throws Excecoes
	 */
	public boolean isIncluidoOK() throws Excecoes {
		if (this.isCodigoExistente() == false){
			EmpresaRN rn = new EmpresaRN();
			rn.inclui(this);
			return true;
		}else{
			return false;
		}
	}
	public String getDm_Tipo_Consulta() {
		return dm_Tipo_Consulta;
	}
	public void setDm_Tipo_Consulta(String dm_Tipo_Consulta) {
		this.dm_Tipo_Consulta = dm_Tipo_Consulta;
	}
	public long getOid_Empresa_Gambiarra() {
		return oid_Empresa_Gambiarra;
	}
	public void setOid_Empresa_Gambiarra(long oid_Empresa_Gambiarra) {
		this.oid_Empresa_Gambiarra = oid_Empresa_Gambiarra;
	}
	public long getOid_Cliente() {
		return oid_Cliente;
	}
	public void setOid_Cliente(long oid_Cliente) {
		this.oid_Cliente = oid_Cliente;
	}
	public String getNr_Cnpj_Formatado() {
		String vCnpj = this.nr_Cnpj;
		
		if (vCnpj.length()>11) {
				nr_Cnpj_Formatado = vCnpj.substring(0,2)+"."+vCnpj.substring(2,5)+"."+vCnpj.substring(5,8)+"/"+vCnpj.substring(8,12)+"-"+vCnpj.substring(12,14);
		} else {
			nr_Cnpj_Formatado = vCnpj.substring(0,3)+"."+vCnpj.substring(3,6)+"."+vCnpj.substring(6,9)+"-"+vCnpj.substring(9,11);
		}
					
		return nr_Cnpj_Formatado;
	}
	public void setNr_Cnpj_Formatado(String nr_Cnpj_Formatado) {
		this.nr_Cnpj_Formatado = nr_Cnpj_Formatado;
	}
	public long getOid_Regional() {
		return oid_Regional;
	}
	public void setOid_Regional(long oid_Regional) {
		this.oid_Regional = oid_Regional;
	}
	public String getNm_Regional() {
		return nm_Regional;
	}
	public void setNm_Regional(String nm_Regional) {
		this.nm_Regional = nm_Regional;
	}
	public String getDm_Modulo() {
		return dm_Modulo;
	}
	public void setDm_Modulo(String dmModulo) {
		dm_Modulo = dmModulo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCd_Pais() {
		return cd_Pais;
	}
	public void setCd_Pais(String cdPais) {
		cd_Pais = cdPais;
	}

}
