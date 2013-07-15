package com.chronosystems.html.core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chronosystems.entity.Imovel;

/**
 * @author André Valadas
 */
public class JsoupExtractor {

	/**
	 * URL princiapl a ser processada
	 * 
	 * @return the URL to connect
	 */
	protected String getURLConnect() {
		return "http://www.penseimoveis.com.br/rs/lista/compra/rs/casa";
	}

	/**
	 * Timeout para conexão
	 * 
	 * @return
	 */
	protected int getTimeout() {
		return 15 * 1000; //15 seconds
	}

	/**
	 * Total de registros para processar
	 * 
	 * @param doc
	 * @return
	 */
	protected Element getTotalRecords(final Document doc) {
		return doc.getElementById("resultadosEncontradosTopo");
	}

	/**
	 * Total de registros por página 
	 * 
	 * @param doc
	 * @return
	 */
	protected Element getTotalRecordsByPage(final Document doc) {
		final Element pagination = doc.getElementById("resultadosPagina");
		final Elements recordsByPageElement = pagination.getElementsByAttribute("selected");
		return recordsByPageElement.get(0);
	}

	/**
	 * Elementos da página
	 * 
	 * @param doc
	 * @return
	 */
	protected Elements getPageElements(final Document doc) {
		return doc.getElementsByClass("boxAnuncioLista");
	}

	/**
	 * Valida se elemento é válido para processar
	 * 
	 * @param element
	 * @return
	 */
	protected boolean validateElementToProcess(final Element element) {
		final Elements valorElement = element.getElementsByClass("anuncioListaValoresValor");
		return valorElement != null && !valorElement.isEmpty();
	}

	/**
	 * @return the empresa value
	 */
	protected Long getEmpresa() {
		return Long.valueOf(1);
	}

	/**
	 * @return the url
	 */
	protected String getUrlText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaBoxImagem").get(0);
		final Elements links = content.getElementsByTag("a");
		if (!links.isEmpty()) {
			return links.get(0).absUrl("href");
		}
		return null;
	}

	/**
	 * @return the codigo
	 */
	protected String getCodigoText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoTitulo").get(0);
		final Elements codigo = content.getElementsByTag("a");
		if (!codigo.isEmpty()) {
			final String absUrl = codigo.get(0).absUrl("href");
			return absUrl.substring(absUrl.lastIndexOf("/")+1);
		}
		return null;
	}

	/**
	 * @return the titulo
	 */
	protected String getTituloText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoTitulo").get(0);
		final Elements title = content.getElementsByTag("a");
		if (!title.isEmpty()) {
			return title.attr("title");
		}
		return null;
	}

	/**
	 * @return the caracteristica
	 */
	protected String getCaracteristicaText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoCaracteristicas").get(0);
		final Elements caracteristica = content.getElementsByTag("strong");
		if (!caracteristica.isEmpty()) {
			return caracteristica.html().trim();
		}
		return null;
	}

	/**
	 * @return the descricao
	 */
	protected String getDescricaoText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoDescricao").get(0);
		final Elements descricao = content.getElementsByTag("a");
		if (!descricao.isEmpty()) {
			return descricao.html().trim();
		}
		return null;
	}

	/**
	 * @return the valor
	 */
	protected String getValorText(final Element doc) {
		final Elements valorElement = doc.getElementsByClass("anuncioListaValoresValor");
		if (!valorElement.isEmpty()) {
			return valorElement.html();
		}
		return null;
	}

	/**
	 * @return the urlImagem
	 */
	protected String getUrlImagemText(final Element doc) {
		final Elements imagem = doc.getElementsByClass("anuncioListaImagem").select("img[src]");
		if (!imagem.isEmpty()) {
			final String absUrl = imagem.get(0).absUrl("src");
			if (absUrl.indexOf("?") > -1) {
				return absUrl.substring(0, absUrl.lastIndexOf("?"));
			}
			return absUrl;
		}
		return null;
	}

	/**
	 * @return the anunciante
	 */
	protected String getAnuncianteText(final Element doc) {
		final Elements anunciante = doc.getElementsByClass("anuncioListaInfoNomeAnunciante");
		if (!anunciante.isEmpty()) {
			return anunciante.attr("title");
		}
		return null;
	}

	/**
	 * Adiciona Imovel processado a lista
	 * 
	 * @param data
	 */
	protected void addElementProcessed(final Imovel data) {
		info(data.getCodigo() + " adicionado.");
	}

	/**
	 * Execute parse html extractor
	 */
	protected void execute() {
		try {
			//TODO charset UTF-8 ????????

			/** main url page */
			final Document doc = Jsoup.connect(getURLConnect()).timeout(getTimeout()).get();
			final String title = doc.title();
			info(title);

			/** total records */
			final String totalRecordsText = getTotalRecords(doc).html();
			info(totalRecordsText);

			/** records by page */
			final String recordsByPageText = getTotalRecordsByPage(doc).html();
			info(recordsByPageText);

			final Integer paginationCount = (Integer.valueOf(totalRecordsText) / Integer.valueOf(recordsByPageText));
			info(paginationCount.toString());

			/** elements to iterate */
			final Elements anuncios = getPageElements(doc);
			for (final Element element : anuncios) {

				/** validate data element */
				if (validateElementToProcess(element)) {

					/* *************** begin ***************** */

					/** data to save */
					final Imovel data = new Imovel();

					/** url */
					final String urlText = getUrlText(element);
					data.setUrl(urlText);
					info(urlText);

					/** code */
					final String codigoText = getCodigoText(element);
					data.setCodigo(codigoText);
					info(codigoText);

					/** title */
					final String tituloText = getTituloText(element);
					data.setTitulo(tituloText);
					info(tituloText);

					/** caracteristicts */
					final String caracteristicaText = getCaracteristicaText(element);
					data.setCaracteristica(caracteristicaText);
					info(caracteristicaText);

					/** desctiption */
					final String descriptionText = getDescricaoText(element);
					data.setDescricao(descriptionText);
					info(descriptionText);

					/** price */
					final String valorText = getValorText(element);
					data.setValor(valorText);
					info(valorText);

					/** url image */
					final String urlImagemText = getUrlImagemText(element);
					data.setUrlImagem(urlImagemText);
					info(urlImagemText);

					/** anunciante */
					final String anuncianteText = getAnuncianteText(element);
					data.setAnunciante(anuncianteText);
					info(anuncianteText);

					/** add sucessfull element processed */
					addElementProcessed(data);

					/* *************** end ******************* */
				}
			}
		} catch (IOException e) {
			error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Registra informações do processo 
	 * 
	 * @param info
	 */
	private void info(final String info) {
		System.out.println("| info: " + info);
	}

	/**
	 * Registra erros do processo 
	 * 
	 * @param error
	 */
	private void error(final String error) {
		System.out.println("| ERROR: " + error);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JsoupExtractor().execute();
	}
}