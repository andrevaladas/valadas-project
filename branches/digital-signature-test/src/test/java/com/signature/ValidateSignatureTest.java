package com.signature;
import java.io.File;

import org.junit.Test;

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
		Validate.validateSignature(new File(Validate.outputSignedDocument));
	}
}