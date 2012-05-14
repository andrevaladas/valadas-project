/**
 * 
 */
package com.chronosystems.core;

import java.io.Serializable;

import javax.persistence.Transient;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * For paginated search
 * @author andrevaladas
 */
@Root
public class FilterEntity extends EntityError implements Serializable {

	private static final long serialVersionUID = -6230944785480582166L;

	/** Pagination */
	@Transient
	@Attribute(required=false)
	private Integer maxRecords;

	@Transient
	@Attribute(required=false)
	private Integer currentPage;

	@Transient
	@Attribute(required=false)
	private Boolean hasNext;

	public Integer getMaxRecords() {
		if (maxRecords == null) {
			maxRecords = 30;
		}
		return maxRecords;
	}
	public void setMaxRecords(final Integer maxRecords) {
		this.maxRecords = maxRecords;
	}

	public Integer getCurrentPage() {
		if (currentPage == null) {
			currentPage = 0;
		}
		return currentPage;
	}
	public void setCurrentPage(final Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Boolean getHasNext() {
		return hasNext;
	}
	public void setHasNext(final Integer totalRecords) {
		this.hasNext = totalRecords > getMaxRecords();
	}
}
