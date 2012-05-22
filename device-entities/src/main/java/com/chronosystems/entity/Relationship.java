/**
 * 
 */
package com.chronosystems.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 */
@Root
@Entity
public class Relationship implements Serializable {

	private static final long serialVersionUID = -3941282140879861009L;

	@Element(required=false)
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Element
	@ManyToOne
	@JoinColumn(name = "ID_FOLLOWER")
	private Device follower;

	@Element
	@ManyToOne
	@JoinColumn(name = "ID_FOLLOWING")
	private Device following;

	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}

	public Device getFollower() {
		return follower;
	}
	public void setFollower(final Device follower) {
		this.follower = follower;
	}

	public Device getFollowing() {
		return following;
	}
	public void setFollowing(final Device following) {
		this.following = following;
	}
}
