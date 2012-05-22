package com.chronosystems.entity.util;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;

public class XMLParser {

	public static String parseXML(Object T) {
		try {
			final Serializer serializer = new Persister();
			final StringWriter sw = new StringWriter();
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
			return serializer.read(clazz, reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void printAll(final Entity entity) {
		final List<Device> devices = entity.getDevices();
		for (final Device device : devices) {
			printAll(device);
		}
	}

	public static void printAll(final Device entity) {
		if (entity != null) {
			System.out.println("-------------- ALL LOCATIONS --------------");
			print(entity);
			final List<Location> locations = entity.getLocations();
			for (final Location l : locations) {
				print(l);
			}
		}
	}

	public static void print(final Device entity) {
		if (entity != null) {
			System.out.println("DEVICE: "+entity.getId()+" - "+entity.getEmail()+" - "+entity.getName());
		}
	}
	public static void print(final Location entity) {
		if (entity != null) {
			System.out.println("LOCATION: "+entity.getId()+" - "+entity.getLatitude()+" / "+entity.getLongitude()+" : "+entity.getTimeline());
		}
	}
}
