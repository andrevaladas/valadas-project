package com.chronosystems.entity.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;

public class EntityUtils {

	
	public static String toXml(final Entity entity) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Entity.class);
			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(entity, baos);
			return baos.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toXml(final Device entity) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Device.class);
			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			final StringWriter sw = new StringWriter();
			marshaller.marshal(entity, sw);
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Deprecated
	public static String toXml(final Location entity) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Location.class);
			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			final StringWriter sw = new StringWriter();
			marshaller.marshal(entity, sw);
			return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Entity getEntity(final String xml) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Entity.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			final Entity entity = (Entity)unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			return entity;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Device getDevice(final String xml) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Device.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			final Device entity = (Device)unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
			return entity;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Device getDevice(final byte[] input) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Device.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			final Device entity = (Device)unmarshaller.unmarshal(new ByteArrayInputStream(input));
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Deprecated
	public static Location getLocation(final String xml) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Location.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			//unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			final Location entity = (Location)unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
			return entity;
		} catch (JAXBException e) {
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
