package com.chronosystems.html.core;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chronosystems.entity.Imovel;
import com.chronosystems.entity.enumeration.SiteBusca;

/**
 * @author André Valadas
 */
public class JsoupExtractor {

	final List<Imovel> result = new ArrayList<>();
	
	/**
	 * URL princiapl a ser processada
	 * 
	 * @return the URL to connect
	 */
	protected String getURLConnect() {
		return "http://www.penseimoveis.com.br/rs/lista/compra/rs/casa";
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
		final Elements recordsByPageElement = pagination
				.getElementsByAttribute("selected");
		return recordsByPageElement.first();
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
	protected SiteBusca getEmpresa() {
		return SiteBusca.PENSE_IMOVEIS;
	}

	/**
	 * @return the url
	 */
	protected String getUrlText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaBoxImagem").first();
		final Elements links = content.getElementsByTag("a");
		if (!links.isEmpty()) {
			return links.first().absUrl("href");
		}
		return null;
	}

	/**
	 * @return the codigo
	 */
	protected String getCodigoText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoTitulo").first();
		final Elements codigo = content.getElementsByTag("a");
		if (!codigo.isEmpty()) {
			final String absUrl = codigo.first().absUrl("href");
			return absUrl.substring(absUrl.lastIndexOf("/") + 1);
		}
		return null;
	}

	/**
	 * @return the titulo
	 */
	protected String getTituloText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoTitulo").first();
		final Elements title = content.getElementsByTag("a");
		if (!title.isEmpty()) {
			return title.attr("title").trim();
		}
		return null;
	}

	/**
	 * @return the caracteristica
	 */
	protected String getCaracteristicaText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoCaracteristicas").first();
		final Elements caracteristica = content.getElementsByTag("strong");
		if (!caracteristica.isEmpty()) {
			return caracteristica.text().trim();
		}
		return null;
	}

	/**
	 * @return the descricao
	 */
	protected String getDescricaoText(final Element doc) {
		final Element content = doc.getElementsByClass("anuncioListaInfoDescricao").first();
		final Elements descricao = content.getElementsByTag("a");
		if (!descricao.isEmpty()) {
			return descricao.text().trim();
		}
		return null;
	}

	/**
	 * @return the valor
	 */
	protected String getValorText(final Element doc) {
		final Elements valorElement = doc.getElementsByClass("anuncioListaValoresValor");
		if (!valorElement.isEmpty()) {
			return valorElement.text().trim();
		}
		return null;
	}

	/**
	 * @return the urlImagem
	 */
	protected String getUrlImagemText(final Element doc) {
		final Elements imagem = doc.getElementsByClass("anuncioListaImagem").select("img[src]");
		if (!imagem.isEmpty()) {
			final String absUrl = imagem.first().absUrl("src");
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
		result.add(data);
		info(data.getCodigoAnuncio() + " adicionado.");
	}

	/**
	 * Execute parse html extractor
	 */
	protected void execute() {
		try {
			/** main url page */
			final Document doc = Jsoup.parse(new URL(getURLConnect()).openStream(), "ISO-8859-1", getURLConnect());
			final String title = doc.title();
			info(title);

			/** total records */
			final String totalRecordsText = getTotalRecords(doc).text();
			info(totalRecordsText);

			/** records by page */
			final String recordsByPageText = getTotalRecordsByPage(doc).text();
			info(recordsByPageText);

			final Integer paginationCount = (Integer.valueOf(totalRecordsText) / Integer.valueOf(recordsByPageText));
			info(paginationCount.toString());

			final ExecutorService executor = Executors.newFixedThreadPool(5);
			final List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();

			/** elements to iterate */
			final Elements anuncios = getPageElements(doc);
			for (final Element element : anuncios) {
				tasks.add(Executors.callable(new Runnable() {
					public void run() {
						/** validate data element */
						if (validateElementToProcess(element)) {

							/* *************** begin ***************** */
							/** data to save */
							final Imovel data = new Imovel(getEmpresa());

							/** url */
							final String urlText = getUrlText(element);
							data.setUrlAnuncio(urlText);
							info(urlText);

							/** code */
							final String codigoText = getCodigoText(element);
							data.setCodigoAnuncio(codigoText);
							info(codigoText);

							/** title */
							final String tituloText = getTituloText(element);
							data.setTituloResumo(tituloText);
							info(tituloText);

							/** caracteristicts */
							final String caracteristicaText = getCaracteristicaText(element);
							data.setCaracteristicasResumo(caracteristicaText);
							info(caracteristicaText);

							/** desctiption */
							final String descriptionText = getDescricaoText(element);
							data.setDescricaoResumo(descriptionText);
							info(descriptionText);

							/** price */
							final String valorText = getValorText(element);
							data.setValor(new BigDecimal(valorText.replaceAll("\\.", "").replaceAll(",", ".")));
							info(valorText);

							/** url image */
							final String urlImagemText = getUrlImagemText(element);
							data.setUrlAnuncio(urlImagemText);
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
				}));
			}

			/** invoke all */
			executor.invokeAll(tasks);
			System.out.println(result.size());
			System.out.println(anuncios.size());
			System.exit(0);
			
		} catch (Exception e) {
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