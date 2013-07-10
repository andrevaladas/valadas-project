package com.nfe.ws.application;
/**
 * Classe concreta que implementa os métodos da interface SefazServiceConfig
 * Os dados de retorno de cada método, pode ser obtido do WSDL de cada WS.
 * Neste caso, estou usando o serviço NfeRecepcao2 (Enviar Lote) do WS do PR
 * Arquivo: SefazServiceReceptionConfig.java
 * Data   : 16/02/2011
 * Hora   : 13:55:03
 */
public class SefazServiceReceptionConfig extends AbstractSefazServiceConfig {

	@Override
	public String getWebServiceURL() {return "https://homologacao.nfe2.fazenda.pr.gov.br/nfe/NFeRecepcao2";}

	@Override
	public String getDefinitionsTargetNamespaceURI() {return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2";}

	@Override
	public String getSchemaTargetNamespaceURI() {	return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2";}

	@Override
	public String getSoapActionURI() {return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2/nfeRecepcaoLote2";}

	@Override
	public String getServiceName() {	return "NfeRecepcao2";}

	@Override
	public String getPortName() {return "NfeRecepcaoServicePort";}
}
