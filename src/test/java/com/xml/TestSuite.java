package com.xml;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.xml.schema.ValidateXMLSchemaTest;
import com.xml.signature.GenerateSignatureTest;
import com.xml.signature.ValidateSignatureTest;

/**
 * Classe para testes unitários de assinatura e xmls
 *
 * @author André Valadas
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = {
		GenerateSignatureTest.class,
		ValidateSignatureTest.class,
		ValidateXMLSchemaTest.class
})
public final class TestSuite {

}
