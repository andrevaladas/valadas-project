/**
 * 
 */
package com.chronosystems.core;

import java.io.Serializable;

import javax.persistence.Transient;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * For paginated search
 * @author andrevaladas
 */
@Root
public class TimeEntity implements Serializable {

	private static final long serialVersionUID = -8164005871556572793L;

	/** XML date suport */
	@Transient
	@Element(required=false)
	private Long dateInTime;

	public Long getDateInTime() {
		return dateInTime;
	}
	public void setDateInTime(Long dateInTime) {
		this.dateInTime = dateInTime;
	}
}
