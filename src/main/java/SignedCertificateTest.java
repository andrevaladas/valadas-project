import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * 
 */

/**
 * @author valadas.cwi
 * 
 */
public class SignedCertificateTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		KeyStore ks = KeyStore.getInstance("JKS");
		FileInputStream fis = new FileInputStream(ClassLoader.getSystemResource("clientkeystore").getPath());
		ks.load(fis, "Zaffari".toCharArray());
		fis.close();

		tmf.init(ks);

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, tmf.getTrustManagers(), null);
		
		
		
		
		CertificateFactory fac = CertificateFactory.getInstance("X.509");
		FileInputStream is = new FileInputStream(ClassLoader.getSystemResource("clientkeystore").getPath());
		Collection<? extends java.security.cert.Certificate> intermediate;
		try {
			intermediate = fac.generateCertificates(is);
		} finally {
			is.close();
		}
		X509Certificate client = null;
		for (Certificate c : intermediate)
			client = (X509Certificate) c;
		if (client == null)
			throw new IllegalArgumentException("Empty chain.");
		X509CertSelector t = new X509CertSelector();
		t.setCertificate(client);

		
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		CertPath cp = cf.generateCertPath(Arrays.asList(new X509Certificate[] { client }));

		TrustAnchor trustAnchor = new TrustAnchor(client, null);

		CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
		PKIXParameters pkixParams = new PKIXParameters(Collections.singleton(trustAnchor));
		pkixParams.setRevocationEnabled(false);

		cpv.validate(cp, pkixParams);
		
		
		
		
		
		//PKIXBuilderParameters params = new PKIXBuilderParameters(ks, t);
		//CertStoreParameters store = new CollectionCertStoreParameters(intermediate);
		//params.addCertStore(CertStore.getInstance("Collection", store));
		//params.setRevocationEnabled(false);
	}

}
