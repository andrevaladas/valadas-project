package com.nfe.ws.application;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.soap.SOAPException;

public class SefazServiceReceptionTest
{
    public static void main(String[] args) throws SOAPException, Exception
    {
        // Cria a classe de configuração
        SefazServiceConfig config = new SefazServiceReceptionConfig();
        // carrega o xml, aqui deve ter apenas o xml, (<enviNFe xmlns="http://www.portalfiscal.inf.br/nfe" versao="2.00">.....</enviNFe>)
        InputStream xml = new FileInputStream("test/br/com/mysoft/mynfe/ws/application/000000000025534-env-lot.xml");
        // instância a classe de service
        SefazService sefazService = new SefazService(config);
        // chama o método para enviar o xml
        ByteArrayOutputStream result = (ByteArrayOutputStream) sefazService.sendXML(xml, "41");

        if(result != null)
        {
            result.writeTo(System.out);// imprime o retorno da Sefaz
        }
    }
}