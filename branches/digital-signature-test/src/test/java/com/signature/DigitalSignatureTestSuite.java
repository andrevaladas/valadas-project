package com.signature;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Classe para testes unit�rios de assinatura
 *
 * @author Andr� Valadas
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = {
		GenerateSignatureTest.class,
		ValidateSignatureTest.class
})
public final class DigitalSignatureTestSuite {

}
