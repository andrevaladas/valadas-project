package com.chronosystems.model;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.chronosystems.entity.Device;

public class ParseXML {

	
	public static String writeXml(final Device device){
	    XmlSerializer xml = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	        xml.setOutput(writer);
	        xml.startDocument("UTF-8", true);
	        xml.startTag("", "device");

	        final String name = device.getName();
	        if (name != null) {
	        	xml.startTag("", "name");
				xml.text(name);
	            xml.endTag("", "name");
	        }

	        final String email = device.getEmail();
	        if (email != null) {
	            xml.startTag("", "email");
				xml.text(email);
	            xml.endTag("", "email");
	        }

	        final String password = device.getPassword();
	        if (password != null) {
	            xml.startTag("", "password");
				xml.text(password);
	            xml.endTag("", "password");
	        }

	        xml.endTag("", "device");
	        xml.endDocument();
	        return writer.toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}

}
