/**
 * 
 */
package com.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author André Valadas
 *
 */
@Entity
public class A implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
	private int id;
	private String name;
	@OneToOne(cascade=CascadeType.ALL)
	private B b;

	public A(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the b
	 */
	public B getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(B b) {
		this.b = b;
	}
}