package com.xml.schema;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This is a simple example of validating an XML using a XSD (XML Schema)
 */
public class ValidateXMLSchema {

	public final static String schemaFileName = "howto.xsd";
	public final static String xmlFileName = "howto.xml";

	public static void main(String[] args) throws Exception {
		validateSchemaDefault();
	}

	public static void validateSchemaDefault() {
		try {
			final Source schemaFile = new StreamSource(new File(ClassLoader.getSystemResource(schemaFileName).getPath()));
			final Source xmlFile = new StreamSource(new File(ClassLoader.getSystemResource(xmlFileName).getPath()));

			validateSchema(schemaFile, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void validateSchema(final Source xsd, final Source xml) throws Exception {

		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(xsd);

		final Validator validator = schema.newValidator();
		validator.setErrorHandler(new ErrorHandler() {
			public void warning(SAXParseException e) throws SAXException {
				System.out.println("WARNING: " + e.getMessage()); // do nothing
			}

			public void error(SAXParseException e) throws SAXException {
				System.out.println("ERROR : " + e.getMessage());
				throw e;
			}

			public void fatalError(SAXParseException e) throws SAXException {
				System.out.println("FATAL : " + e.getMessage());
				throw e;
			}
		});

		try {
			validator.validate(xml);
			System.out.println(xml.getSystemId() + " is valid");
		} catch (SAXException e) {
			System.out.println(xml.getSystemId() + " is NOT valid");
			System.out.println("Reason: " + e.getLocalizedMessage());
			throw e;
		}
	}
}