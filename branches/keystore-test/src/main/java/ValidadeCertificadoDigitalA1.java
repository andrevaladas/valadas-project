

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

/**
 * 
 * @author valadas.cwi
 * @link http://docs.oracle.com/cd/E19509-01/820-3503/ggfgo/index.html
 */
public class ValidadeCertificadoDigitalA1 {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public static void main(String[] args) {
		
		try {
			info("Java Version: " + System.getProperty("java.runtime.version"));
			//String caminhoDoCertificadoDoCliente = "C:/JavaC/NF-e/certificadoDoCliente.pfx";
			String caminhoDoCertificadoDoCliente = ClassLoader.getSystemResource("clientkeystore.jks").getPath();
            String senhaDoCertificadoDoCliente = "Zaffari";

		    //KeyStore keystore = KeyStore.getInstance(("PKCS12"));  
		    KeyStore keystore = KeyStore.getInstance(("JKS"));  
		    keystore.load(new FileInputStream(caminhoDoCertificadoDoCliente), senhaDoCertificadoDoCliente.toCharArray());  

		    Enumeration<String> eAliases = keystore.aliases();  

		    while (eAliases.hasMoreElements()) {  
		        String alias = (String) eAliases.nextElement();  
		        Certificate certificado = (Certificate) keystore.getCertificate(alias);  

		        info("Aliais: " + alias);
		        X509Certificate cert = (X509Certificate) certificado;
		        
		        
		        //FileInputStream fis = new FileInputStream(ClassLoader.getSystemResource("client.csr").getPath());
                //CertificateFactory cf = CertificateFactory.getInstance("X.509");
                //X509Certificate ca = (X509Certificate) cf.generateCertificate(fis);
                //fis.close();
		        
		        
		        cert.verify(cert.getPublicKey());

		        info(cert.getSubjectDN().getName());
		        info("Assinatura: " + cert.getSignature());
		        info("Válido a partir de..: " + dateFormat.format(cert.getNotBefore()));
		        info("Válido até..........: " + dateFormat.format(cert.getNotAfter()));  
		    }  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Info
	 * @param log
	 */
	private static void info(String log) {
		System.out.println("INFO: " + log);
	}
	
}
