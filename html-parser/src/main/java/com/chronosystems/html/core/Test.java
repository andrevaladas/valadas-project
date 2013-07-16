package com.chronosystems.html.core;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	private static String getURLConnect() {
		return "http://www.penseimoveis.com.br/rs/anuncio/venda/casa/rs/porto-alegre/chacara-das-pedras/3658355";
	}

	public static void main(String[] args) throws Exception {
		final Document doc = Jsoup.parse(new URL(getURLConnect()).openStream(), "ISO-8859-1", getURLConnect());
		final Element telefoneTag = doc.getElementsByClass("informacoesTelefone.titlePhoneCustomer").first().parent();
		System.out.println(telefoneTag.text());
	}
	
	public static void maintest(String[] args) throws Exception {
		final Document doc = Jsoup.parse(new URL(getURLConnect()).openStream(), "ISO-8859-1", getURLConnect());
		final Elements scriptElements = doc.getElementsByTag("script");

		for (final Element element : scriptElements) {
			for (final DataNode node : element.dataNodes()) {
				final String wholeData = node.getWholeData();
				final String latitude = findScriptTextValue(wholeData, "latitude");
				if (latitude != null) {
					final String longitude = findScriptTextValue(wholeData, "longitude");
					final String endereco = findScriptTextValue(wholeData, "enderecoFull");
				}
			}
			System.out.println("-------------------");
		}
	}
	
	private static String findScriptTextValue(final String scriptText, final String property) {
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
