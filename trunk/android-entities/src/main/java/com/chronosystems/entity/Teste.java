package com.chronosystems.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="teste")
public class Teste implements Serializable {
	
	private static final long serialVersionUID = 6246777207650306617L;

	private String name;

	public Teste(String name) {
		super();
		this.name = name;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
