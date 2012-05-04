/**
 * 
 */
package mobile.xml;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * @author andrevaladas
 * 
 */
public class XMLParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleXML simpleXML = new SimpleXML();
		simpleXML.setName("André Valadas");
		simpleXML.setEmail("andrevaladas@gmail.com");

		simpleXML.getLista().add(new SimpleXML("Valadas"));

		try {
			final String xml = parseXML(simpleXML);
			SimpleXML obj = parseXML(xml, SimpleXML.class);
			System.out.println(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String parseXML(Object T) {
		try {
			final Serializer serializer = new Persister();
			final StringWriter sw = new StringWriter();
			//sw.write("<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n");
			serializer.write(T, sw);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T>T parseXML(final String xml, Class<T> clazz) {
		try {
			final Serializer serializer = new Persister();
			final Reader reader = new StringReader(xml);
			return serializer.read(clazz, reader, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
