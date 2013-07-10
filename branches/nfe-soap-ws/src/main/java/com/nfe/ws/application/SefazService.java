package com.nfe.ws.application;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.Security;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sun.security.sasl.Provider;

/**
 * Classe respons�vel por criar um Message e consumir o Web Service.
 *
 * Arquivo: SefazService.java
 * Data   : 16/02/2011
 * Hora   : 14:01:27
 */
public class SefazService
{
	private final String webServiceURL;
	private final String definitionsTargetNamespaceURI;
	private final String schemaTargetNamespaceURI;
	private final String soapActionURI;
	private final String serviceName;
	private final String portName;

	/**
	 * Construtor que recebe a interface SefazServiceConfig como parametro
	 * @param config
	 */
	public SefazService(SefazServiceConfig config)
	{
		if (config == null)
        {
            throw new IllegalArgumentException();
        }
		config.setProperties();
		webServiceURL = config.getWebServiceURL();
		definitionsTargetNamespaceURI = config.getDefinitionsTargetNamespaceURI();
		schemaTargetNamespaceURI = config.getSchemaTargetNamespaceURI();
		soapActionURI = config.getSoapActionURI();
		serviceName = config.getServiceName();
		portName = config.getPortName();
	}

	/**
	 * M�todo que envia o xml para o WS
	 * @param xml
	 * @return
	 */
	public OutputStream sendXML(InputStream xml,String uf)throws SOAPException,Exception {
			//preparar as propriedades
			Provider provider = new Provider();
			Security.addProvider(provider);
			//Cria um servi�o a partir do endere�o do WS
			Service service = Service.create(new URL(webServiceURL+"?wsdl"),new QName(definitionsTargetNamespaceURI, serviceName));

			//Cria um Dispatch, que � respons�vel por invocar o servi�o
			Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(schemaTargetNamespaceURI, portName),SOAPMessage.class,Service.Mode.MESSAGE);

			//Configura o RequestContext para a Message solicitada
			Map<String, Object> rc = dispatch.getRequestContext();
	        rc.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
	        rc.put(BindingProvider.SOAPACTION_URI_PROPERTY, soapActionURI);
	        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, webServiceURL);

	        // Cria um SOAPMessage Request, passando a especifica��o SOAP 1.2
	        MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
	        SOAPMessage soapMessage = factory.createMessage();

	        // Optional
	        soapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "false");
	        soapMessage.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "utf-8");

	        // Cria o Header da mensagem
	        SOAPHeader header = soapMessage.getSOAPHeader();
	        SOAPElement nfeCabecMsg = header.addChildElement("nfeCabecMsg", XMLConstants.DEFAULT_NS_PREFIX, definitionsTargetNamespaceURI);
	        nfeCabecMsg.addChildElement("versaoDados").setValue("2.00");
	        nfeCabecMsg.addChildElement("cUF").setValue(uf);

	        // Cria o Body
	        SOAPBody body = soapMessage.getSOAPBody();

	        // Cria um elemento que vai ter os dados do XML
	        SOAPBodyElement nfeDadosMsg = body.addBodyElement(new QName(schemaTargetNamespaceURI, "nfeDadosMsg", XMLConstants.DEFAULT_NS_PREFIX));

	        // Converte o stream (seu xml) para um Objeto DOM
	        DOMResult domResult = new DOMResult();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			InputSource inputSource = new InputSource(xml);
			SAXSource saxSource = new SAXSource(reader, inputSource);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(saxSource, domResult);

	        //obtem o Document
			Document document = (Document) domResult.getNode();
			//cria um elemento a partir o Document
			SOAPElement soapElement = SOAPFactory.newInstance().createElement(document.getDocumentElement());
			//adiciona no Body
	        nfeDadosMsg.addChildElement(soapElement);
	        //imprime o envelope
	        soapMessage.writeTo(System.out);

 	        // invoca o WS e obt�m o retorno
	        SOAPMessage reply = dispatch.invoke(soapMessage);

	        // Retorno
	        OutputStream out = new ByteArrayOutputStream();
	        reply.writeTo(out);
	        return out;
	}
}