import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class Signed {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CertificateFactory fac = CertificateFactory.getInstance("X.509");
		FileInputStream is = new FileInputStream("client.crt");
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

		KeyStore ks = KeyStore.getInstance("JKS");
		FileInputStream fis = new FileInputStream(ClassLoader.getSystemResource("clientkeystore").getPath());
		ks.load(fis, "Zaffari".toCharArray());
		fis.close();

		PKIXBuilderParameters params = new PKIXBuilderParameters(ks, t);
		CertStoreParameters store = new CollectionCertStoreParameters(
				intermediate);
		params.addCertStore(CertStore.getInstance("Collection", store));
		params.setRevocationEnabled(false);

	}

}
