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
			
			//As duas linhas seguintes de c�digo, colocam no
			//formato de endere�os, 
			//supostamente v�lidos, de email os dados 
			//passados pelos par�metros to e from.
			
			javax.mail.internet.InternetAddress destinatario = new javax.mail.internet.InternetAddress (srtTok.nextToken());
			javax.mail.internet.InternetAddress remetente = new javax.mail.internet.InternetAddress (from);
			
			//As duas linhas de c�digo a seguir, s�o 
			//respons�veis por setar os atributos e 
			//propriedas necess�rias do objeto message 
			//para que o email seja enviado.
			//inicializa��o do objeto Message 
			
			message = new MimeMessage (mailSession);
			
			//Defini��o de quem est� enviando o email
			message.setFrom(remetente);
			
			//define o(s) destinat�rio(s) e qual o tipo do 
			//destinat�rio.
			//os poss�veis tipos de destinat�rio: TO, CC, BCC
			
			message.setRecipient( Message.RecipientType.TO, destinatario);
			//defini��o do assunto do email
				
			message.setSubject (subject);
			//defini��o do conte�do da mesnagem e do 
			//tipo da mensagem
			
			message.setContent (mensagem.toString(), "text/plain");
			
			message.setSentDate(new Date());
			
			//a linha de c�digo seguinte � a respons�vel 
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

		
		//defini��o do mailserver
		
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
			
			//As duas linhas seguintes de c�digo, colocam no
			//formato de endere�os, 
			//supostamente v�lidos, de email os dados 
			//passados pelos par�metros to e from.
			
			javax.mail.internet.InternetAddress destinatario = new javax.mail.internet.InternetAddress (srtTok.nextToken());
			javax.mail.internet.InternetAddress remetente = new javax.mail.internet.InternetAddress (from);
			
			//As duas linhas de c�digo a seguir, s�o 
			//respons�veis por setar os atributos e 
			//propriedas necess�rias do objeto message 
			//para que o email seja enviado.
			//inicializa��o do objeto Message 
			
			message = new MimeMessage (mailSession);
			
			//Defini��o de quem est� enviando o email
			message.setFrom(remetente);
			
			//define o(s) destinat�rio(s) e qual o tipo do 
			//destinat�rio.
			//os poss�veis tipos de destinat�rio: TO, CC, BCC
			
			message.setRecipient( Message.RecipientType.TO, destinatario);
			//defini��o do assunto do email
				
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
			
	
			//a linha de c�digo seguinte � a respons�vel 
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


