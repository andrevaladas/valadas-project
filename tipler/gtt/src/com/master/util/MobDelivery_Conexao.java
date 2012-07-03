package com.master.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MobDelivery_Conexao {

	public  String execute(String urlString)throws Exception {
        
      //cria o objeto url   
      URL url = new URL(urlString);   
  
      //cria o objeto httpurlconnection
      HttpURLConnection connection =
          (HttpURLConnection) url.openConnection();   
         
      //seta o metodo
      connection.setRequestProperty("Request-Method",    "POST");
      
      //seta a variavel para ler o resultado
      connection.setDoInput(true);
      connection.setDoOutput(false);
      
      //conecta com a url destino
      connection.connect();
      
      //abre a conexão pra input
      BufferedReader br =
      	new BufferedReader(new InputStreamReader(connection.getInputStream()));
   
      //le ate o final
      StringBuffer newData = new StringBuffer(10000);
      String s = "";
      while (null != ((s = br.readLine()))) {
          newData.append(s);
      }
      br.close();
      
      //imprime o codigo resultante
      return(new String(newData));
	
	}


}