package com.chronosystems.entity.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;

public class EntityUtils {

	public static String toXml(final Device entity) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Device.class);
			final Marshaller marshaller = context.createMarshaller();
			final StringWriter sw = new StringWriter();
			marshaller.marshal(entity, sw);
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String toXml(final Location entity) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Location.class);
			final Marshaller marshaller = context.createMarshaller();
			final StringWriter sw = new StringWriter();
			marshaller.marshal(entity, sw);
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Device getDevice(final String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(Device.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			final Device entity = (Device)unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
			return entity;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Location getLocation(final String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(Location.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			final Location entity = (Location)unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
			return entity;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
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
			System.out.println("DEVICE: "+entity.getId()+" - "+entity.getLogin()+" - "+entity.getName());
		}
	}
	public static void print(final Location entity) {
		if (entity != null) {
			System.out.println("LOCATION: "+entity.getId()+" - "+entity.getLatitude()+" / "+entity.getLongitude()+" : "+entity.getTimeline());
		}
	}
}
