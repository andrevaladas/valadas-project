package jFiles;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.*;

import com.master.util.Data;


public class Email {

/*
. . .
*/
        private final String mailServer="mail.panazzolo.com.br";
        private final String username="sistema@deltaguialogistica.com.br";
        private final String password="nalthus";
            
	public void sendMail (String subject, String to,String from, String mensagem) throws AddressException, Exception{
		Properties mailProps = null;
		Session mailSession = null;
		Message message = null;
	
		StringTokenizer srtTok = new StringTokenizer(to,";");  
		while (srtTok!=null && srtTok.hasMoreTokens()){
			
			mailProps = new Properties();
			
			mailProps.put("mail.smtp.host", mailServer);
			mailProps.put("mail.smtp.auth", "true");
			
			//mailSession = Session.getDefaultInstance(mailProps, null);
			
			//Autenticador do servidor de saida
			Authenticator auth = new MyAuthenticator();
			mailSession = Session.getInstance(mailProps, auth);
			
			//As duas linhas seguintes de código, colocam no
			//formato de endereços, 
			//supostamente válidos, de email os dados 
			//passados pelos parâmetros to e from.
			
			javax.mail.internet.InternetAddress destinatario = new javax.mail.internet.InternetAddress (srtTok.nextToken());
			javax.mail.internet.InternetAddress remetente = new javax.mail.internet.InternetAddress (from);
			
			//As duas linhas de código a seguir, são 
			//responsáveis por setar os atributos e 
			//propriedas necessárias do objeto message 
			//para que o email seja enviado.
			//inicialização do objeto Message 
			
			message = new MimeMessage (mailSession);
			
			//Definição de quem está enviando o email
			message.setFrom(remetente);
			
			//define o(s) destinatário(s) e qual o tipo do 
			//destinatário.
			//os possíveis tipos de destinatário: TO, CC, BCC
			
			message.setRecipient( Message.RecipientType.TO, destinatario);
			//definição do assunto do email
				
			message.setSubject (subject);
			//definição do conteúdo da mesnagem e do 
			//tipo da mensagem
			
			message.setContent (mensagem.toString(), "text/plain");
			
			message.setSentDate(new Date());
			
			//a linha de código seguinte é a responsável 
			//pelo envio do email
			
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(mailServer, username, password);
			message.saveChanges();
			transport.send(message);
			transport.close();			
			//Transport.send (message);
		}

	}
	
	public void sendMail (String subject, String to,String from, String mensagem, String attachment) throws AddressException, Exception{

		
		//definição do mailserver
		
		Properties mailProps = null;
		Session mailSession = null;
		Message message = null;
		BodyPart messageBodyPart = null;
		Multipart multipart = null;
		DataSource source = null;
		StringTokenizer srtTok = new StringTokenizer(to,";");  
		
		while (srtTok!=null && srtTok.hasMoreTokens()){
	
			mailProps = new Properties();

			mailProps.put("mail.smtp.host", mailServer);
			mailProps.put("mail.smtp.auth", "true");
						
			//mailSession = Session.getDefaultInstance(mailProps, null);		
			
			//Autenticador do servidor de saida
			Authenticator auth = new MyAuthenticator();
			mailSession = Session.getInstance(mailProps, auth);
			//mailSession.setDebug(true);
			
			//As duas linhas seguintes de código, colocam no
			//formato de endereços, 
			//supostamente válidos, de email os dados 
			//passados pelos parâmetros to e from.
			
			javax.mail.internet.InternetAddress destinatario = new javax.mail.internet.InternetAddress (srtTok.nextToken());
			javax.mail.internet.InternetAddress remetente = new javax.mail.internet.InternetAddress (from);
			
			//As duas linhas de código a seguir, são 
			//responsáveis por setar os atributos e 
			//propriedas necessárias do objeto message 
			//para que o email seja enviado.
			//inicialização do objeto Message 
			
			message = new MimeMessage (mailSession);
			
			//Definição de quem está enviando o email
			message.setFrom(remetente);
			
			//define o(s) destinatário(s) e qual o tipo do 
			//destinatário.
			//os possíveis tipos de destinatário: TO, CC, BCC
			
			message.setRecipient( Message.RecipientType.TO, destinatario);
			//definição do assunto do email
				
			message.setSubject (subject);
					
			//Cria o corpo multiplo da mensagem 
			messageBodyPart = new MimeBodyPart();
	
			//Insere o texto na mensagem
			messageBodyPart.setText(mensagem);
	
			multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
	
			//Insere o atachado como segunda parte da mensagem
			String path = "/data/panazzolo/arquivos/embarques/";
			messageBodyPart = new MimeBodyPart();
			source = new FileDataSource(path+attachment);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachment);
			multipart.addBodyPart(messageBodyPart);
	
			//Insere as duas partes do corpo na mensagem
			message.setContent(multipart);
			
			message.setSentDate(new Date());
			
	
			//a linha de código seguinte é a responsável 
			//pelo envio do email
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(mailServer, username, password);
			message.saveChanges();
			transport.send(message);
			transport.close();			
			//Transport.send (message);
		}
	}

}

   class MyAuthenticator extends Authenticator {

private final String username="sistema@deltaguialogistica.com.br";
private final String password="nalthus";

	  public PasswordAuthentication getPasswordAuthentication() {
	  	
	    return new PasswordAuthentication(username, password);
	  }

}


