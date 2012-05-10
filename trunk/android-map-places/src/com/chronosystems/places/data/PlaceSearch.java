/**
 * 
 */
package com.chronosystems.places.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Andre Valadas
 *
 */
@Root(name="PlaceSearchResponse")
public class PlaceSearch implements Serializable {

	private static final long serialVersionUID = 6450355998265215382L;

	@Element(required=false)
	private String status;

	@ElementList(entry="result", inline=true, required=false)
	private List<Place> result = new ArrayList<Place>();

	@Element(required=false)
	private String html_attribution;

	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
	public List<Place> getResult() {
		return result;
	}
	public void setResult(final List<Place> result) {
		this.result = result;
	}
	public String getHtml_attribution() {
		return html_attribution;
	}
	public void setHtml_attribution(final String html_attribution) {
		this.html_attribution = html_attribution;
	}
}
