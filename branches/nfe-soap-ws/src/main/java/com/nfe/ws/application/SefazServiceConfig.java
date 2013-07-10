package com.nfe.ws.application;

public interface SefazServiceConfig {
	void setProperties();

	String getWebServiceURL();

	String getDefinitionsTargetNamespaceURI();

	String getSchemaTargetNamespaceURI();

	String getSoapActionURI();

	String getServiceName();

	String getPortName();
}