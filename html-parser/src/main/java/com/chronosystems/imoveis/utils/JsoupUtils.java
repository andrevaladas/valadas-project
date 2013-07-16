/**
 * 
 */
package com.chronosystems.imoveis.utils;

import java.math.BigDecimal;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Classe utilitária do Jsoup
 * 
 * @author André Valadas
 */
public final class JsoupUtils {

	/**
	 * Converte String para Valor decimal
	 * 
	 * @param stringValue
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(final String stringValue) {
		if (stringValue != null) {
			return new BigDecimal(stringValue.replaceAll("\\.", "").replaceAll(",", "."));
		}
		return null;
	}

	/**
	 * Procura o valor da propriedade no javaScript do documento da página
	 * 
	 * @param doc
	 * @param property
	 * @return
	 */
	public static String getScriptPropertyValue(final Document doc, final String property) {
		final Elements scriptElements = doc.getElementsByTag("script");

		for (final Element element : scriptElements) {
			for (final DataNode node : element.dataNodes()) {
				final String wholeData = node.getWholeData();
				final String propertyValue = getScriptPropertyValue(wholeData, property);
				if (propertyValue != null) {
					return propertyValue;
				}
			}
		}		
		return null;
	}

	/**
	 * Procura o valor da propriedade no javaScript da página
	 * 
	 * @param scriptText
	 * @param property
	 * @return
	 */
	public static String getScriptPropertyValue(final String scriptText, final String property) {
		final int propertyIndex = scriptText.indexOf(property);
		if (propertyIndex > -1) {
			final int initProp = scriptText.indexOf("\"", propertyIndex);
			final int endProp = scriptText.indexOf("\"", initProp+1);
			final String propertyValue = scriptText.substring(initProp+1, endProp).trim();
			
			System.out.println(propertyValue);
			return propertyValue;
		}
		return null;
	}
}