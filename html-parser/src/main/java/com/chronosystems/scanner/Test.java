package com.chronosystems.scanner;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Test {

	private static String getURLConnect() {
		return "http://www.penseimoveis.com.br/rs/anuncio/venda/casa/rs/porto-alegre/chacara-das-pedras/3658355";
	}

	public static void main(String[] args) throws Exception {
		final Document doc = Jsoup.parse(new URL(getURLConnect()).openStream(), "ISO-8859-1", getURLConnect());
		final Element telefoneTag = doc.getElementsByClass("informacoesTelefone.titlePhoneCustomer").first().parent();
		System.out.println(telefoneTag.text());
	}
	
}
