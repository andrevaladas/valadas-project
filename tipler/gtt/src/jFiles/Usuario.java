package jFiles; 
public class Usuario {

    private Long idUsuario;
    private String nome;
    private String unidade;
    private Boolean isHabilitado;
    private String userName;
    private String password;
    private String email;
    private Long idTipoUsuario;
    
	/**
	 * @return Returns the email.
	 */
	public  String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public  void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the idTipoUsuario.
	 */
	public  Long getIdTipoUsuario() {
		return idTipoUsuario;
	}
	/**
	 * @param idTipoUsuario The idTipoUsuario to set.
	 */
	public  void setIdTipoUsuario(Long idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	/**
	 * @return Returns the idUsuario.
	 */
	public  Long getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario The idUsuario to set.
	 */
	public  void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return Returns the isHabilitado.
	 */
	public  Boolean getIsHabilitado() {
		return isHabilitado;
	}
	/**
	 * @param isHabilitado The isHabilitado to set.
	 */
	public  void setIsHabilitado(Boolean isHabilitado) {
		this.isHabilitado = isHabilitado;
	}
	/**
	 * @return Returns the nome.
	 */
	public  String getNome() {
		return nome;
	}
	/**
	 * @param nome The nome to set.
	 */
	public  void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return Returns the password.
	 */
	public  String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public  void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the unidade.
	 */
	public  String getUnidade() {
		return unidade;
	}
	/**
	 * @param unidade The unidade to set.
	 */
	public  void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	/**
	 * @return Returns the userName.
	 */
	public  String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public  void setUserName(String userName) {
		this.userName = userName;
	}
}
