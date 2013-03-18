package com.xml.schema;
import org.junit.Test;

/**
 * Validate XML using a XSD (XML Schema)
 * 
 * @author André Valadas
 */
public class ValidateXMLSchemaTest {

	/**
	 * Validate XSD Schema
	 */
	@Test
	public void testValidateSchema() throws Exception {
		ValidateXMLSchema.validateSchemaDefault();
	}
}