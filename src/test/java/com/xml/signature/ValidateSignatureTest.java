package com.xml.signature;
import java.io.File;

import org.junit.Test;

import com.xml.signature.ValidateXMLSignature;

/**
 * Validate the signed enveloped document
 * 
 * @author André Valadas
 */
public class ValidateSignatureTest {

	/**
	 * Validate XML Digital Signature
	 */
	@Test
	public void testValidateSignature() throws Exception {
		// Validate generated file by testGenerateSingnature()
		ValidateXMLSignature.validateSignature(new File(ValidateXMLSignature.outputSignedDocument));
	}
}