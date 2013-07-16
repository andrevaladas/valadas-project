package com.chronosystems.html.core;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chronosystems.imoveis.entity.ImagemImovel;
import com.chronosystems.imoveis.entity.Imovel;
import com.chronosystems.imoveis.enumeration.CategoriaImovel;
import com.chronosystems.imoveis.enumeration.SiteBusca;
import com.chronosystems.imoveis.enumeration.TipoImovel;
import com.chronosystems.imoveis.utils.JsoupUtils;

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
	protected Map<TipoImovel, String> getURLConnect() {
		final Map<TipoImovel, String> urls = new HashMap<>();
		urls.put(TipoImovel.CA, "http://www.penseimoveis.com.br/rs/lista/compra/rs/casa");

		return urls;
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
	 * @return the site to search
	 */
	protected SiteBusca getSiteBusca() {
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
		final Element anunciante = doc.getElementsByClass("dadosAnunciante.nomeAnunciante").first();
		if (anunciante != null) {
			return anunciante.text().trim();
		}
		return null;
	}

	/**
	 * @return telefone
	 */
	protected String getTelefoneText(final Element doc) {
		//TODO Verificar se tem como extrair direto o conteudo HTML ou TEXT completo
		final Element telefoneTag = doc.getElementsByClass("informacoesTelefone.titlePhoneCustomer").first().parent();
		final Elements codigos = telefoneTag.getElementsByClass("subTitlePrePhone");
		final String ddi = codigos.first().text();
		final String ddd = codigos.get(1).text();
		final String numero = telefoneTag.getElementsByClass("subTitlePhoneCustomer").first().text();

		return ddi+ddd+numero;
	}
	
	/**
	 * @return the valor
	 */
	protected String getValorText(final Element doc) {
		final Element valorElement = doc.getElementsByClass("valorImovelConteudo.preco").first();
		if (valorElement != null) {
			return valorElement.text().trim();
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

			/** URLs */
			final Map<TipoImovel, String> urlConnect = getURLConnect();
			final Set<Entry<TipoImovel, String>> entrySet = urlConnect.entrySet();
			for (final Entry<TipoImovel, String> entry : entrySet) {

				/** url da listagem paginada */
				final Document doc = Jsoup.parse(new URL(entry.getValue()).openStream(), "ISO-8859-1", entry.getValue());
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

								/* *************** begin summary ***************** */
								/** Imóvel */
								final Imovel imovel = new Imovel(getSiteBusca());

								/** tipo imóvel */
								final TipoImovel tipoImovel = entry.getKey();
								imovel.setTipoImovel(tipoImovel);
								info(tipoImovel.getDescription());

								/* processa resumo */
								processSummary(imovel, element);

								/* processa detalhamento */
								processDetail(imovel);

								/* add sucessfull element processed */
								addElementProcessed(imovel);
							}
						}
					}));
				}

				/** invoke all */
				executor.invokeAll(tasks);
				System.out.println(result.size());
				System.out.println(anuncios.size());
			}
			System.exit(0);
			
		} catch (Exception e) {
			error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Processa resumo do anúncio
	 * 
	 * @param imovel
	 */
	private void processSummary(final Imovel imovel, final Element element) {
		/** url anúncio */
		final String urlText = getUrlText(element);
		imovel.setUrlAnuncio(urlText);
		info(urlText);

		/** código */
		final String codigoText = getCodigoText(element);
		imovel.setCodigoAnuncio(codigoText);
		info(codigoText);

		/** título */
		final String tituloText = getTituloText(element);
		imovel.setTituloResumo(tituloText);
		info(tituloText);

		/** característica */
		final String caracteristicaText = getCaracteristicaText(element);
		imovel.setCaracteristicasResumo(caracteristicaText);
		info(caracteristicaText);

		/** categoria */
		final CategoriaImovel categoriaImovel = CategoriaImovel.getEnum(caracteristicaText);
		imovel.setCategoriaImovel(categoriaImovel);
		info(categoriaImovel.getDescription());

		/** descrição */
		final String descriptionText = getDescricaoText(element);
		imovel.setDescricaoResumo(descriptionText);
		info(descriptionText);

		/** url image */
		final String urlImagemText = getUrlImagemText(element);
		imovel.setImgDestaque(urlImagemText);
		info(urlImagemText);
	}

	/**
	 * Processa detalhamento do anúncio
	 * 
	 * @param imovel
	 */
	private void processDetail(final Imovel imovel) {
		try {
			/** acessa pagina de detalhamento */
			final Document doc = Jsoup.parse(new URL(imovel.getUrlAnuncio()).openStream(), "ISO-8859-1", imovel.getUrlAnuncio());

			/** anunciante */
			final String anuncianteText = getAnuncianteText(doc);
			imovel.setAnunciante(anuncianteText);
			info(anuncianteText);

			/** telefone */
			final String telefoneText = getTelefoneText(doc);
			imovel.setTelefone(telefoneText);
			info(telefoneText);

			/* processa caracteristicas */
			processCarateristicas(imovel, doc);

			/* processa localização */
			processLocalizacao(imovel, doc);

			/* processa imagens */
			processImagens(imovel, doc);

			/** valor */
			final String valorText = getValorText(doc);
			imovel.setValor(JsoupUtils.stringToBigDecimal(valorText));
			info(valorText);

		} catch (Exception e) {
			error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Processa as características 
	 * 
	 * @param imovel
	 * @param doc
	 */
	private void processCarateristicas(final Imovel imovel, final Document doc) {
		final Elements dormitorios = doc.getElementsByClass("linkDetalhesImovel.detalhes").select("li");
		for (int i = 0; i < dormitorios.size(); i++) {
			final Element element = dormitorios.get(i);
			final String property = element.getElementsByClass("coluna1").text().trim();
			final String value = element.getElementsByClass("coluna2").text().trim();
			if (property.startsWith("Dormitórios")) {
				imovel.setDormitorios(Integer.valueOf(value));
				info(value);
			} else if (property.startsWith("Vagas de garagem")) {
				imovel.setBoxGaragem(Integer.valueOf(value));
				info(value);
			} else if (property.startsWith("Área total")) {
				imovel.setAreaTotal(JsoupUtils.stringToBigDecimal(value));
				info(value);
			} else if (property.startsWith("Área privativa")) {
				imovel.setAreaPrivativa(JsoupUtils.stringToBigDecimal(value));
				info(value);
			}
		}
	}

	/**
	 * Processa localização do imóvel
	 * 
	 * @param imovel
	 * @param doc
	 */
	private void processLocalizacao(final Imovel imovel, final Document doc) {
		final Element map = doc.getElementById("mapaLocalizacao").getElementsByTag("a").first();
		final String googleMaps = map.absUrl("href");

		final int index = googleMaps.indexOf("?ll=");
		final String[] coordenadas = googleMaps.substring(index+4, googleMaps.indexOf("&")).split(",");

		final String latitude = coordenadas[0].trim();
		final String longitude = coordenadas[1].trim();
		final String endereco = JsoupUtils.getScriptPropertyValue(doc, "enderecoFull").trim();

		imovel.setLatitude(new BigDecimal(latitude));
		imovel.setLongitude(new BigDecimal(longitude));
		imovel.setEndereco(endereco);

		info(latitude);
		info(longitude);
		info(endereco);
	}

	/**
	 * Processa as imagens do anúncio
	 * 
	 * @param imovel
	 * @param doc
	 */
	private void processImagens(final Imovel imovel, final Document doc) {
		final Elements imagens = doc.getElementsByClass("imagemBoxRodapeThumb").select("img[src]");
		final List<ImagemImovel> imagensImoveis = new ArrayList<>();

		for (final Element imagem : imagens) {
			final String descricao = imagem.attr("title").trim();
			String absUrl = imagem.absUrl("src");
			if (absUrl.indexOf("?") > -1) {
				absUrl = absUrl.substring(0, absUrl.lastIndexOf("?"));
			}

			final ImagemImovel imagemImovel = new ImagemImovel();
			imagemImovel.setUrl(absUrl);
			imagemImovel.setDescricao(descricao);
			imagemImovel.setImovel(imovel);

			imagensImoveis.add(imagemImovel);

			info(absUrl);
			info(descricao);
		}

		/** atualiza dados do imóvel */
		imovel.setTotalImagens(Integer.valueOf(imagensImoveis.size()));
		imovel.setImagens(new HashSet<>(imagensImoveis));
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