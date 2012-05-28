package com.chronosystems.controller;


public class Contact {

	private Integer id;
	private String firstname;
	private String lastname;
	private String email;
	private String telephone;


	public String getEmail() {
		return email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}

}
