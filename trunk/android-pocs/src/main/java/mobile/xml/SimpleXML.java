/**
 * 
 */
package mobile.xml;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author andrevaladas
 *
 */
@Root
public class SimpleXML {
	@Element
	private String name;
	@Element(required=false)
	private String email;

	@ElementList(required=false, entry="lista", inline=true)
	private List<SimpleXML> lista = new ArrayList<SimpleXML>();


	public SimpleXML() {
		super();
	}
	public SimpleXML(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<SimpleXML> getLista() {
		return lista;
	}
	public void setLista(List<SimpleXML> lista) {
		this.lista = lista;
	}
}
