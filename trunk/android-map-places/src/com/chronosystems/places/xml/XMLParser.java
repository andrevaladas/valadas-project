package com.chronosystems.places.xml;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLParser {

	public static String parseXML(final Object T) {
		try {
			final Serializer serializer = new Persister();
			final StringWriter sw = new StringWriter();
			serializer.write(T, sw);
			return sw.toString();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T>T parseXML(final String xml, final Class<T> clazz) {
		try {
			final Serializer serializer = new Persister();
			final Reader reader = new StringReader(xml);
			return serializer.read(clazz, reader);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
