/**
 * 
 */
package com.chronosystems.ssl;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author Andre Valadas
 * 
 */
public class EasyX509TrustManager implements X509TrustManager {

	private X509TrustManager standardTrustManager = null;

	/**
	 * Constructor for EasyX509TrustManager.
	 */
	public EasyX509TrustManager(final KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
		super();
		final TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		factory.init(keystore);
		final TrustManager[] trustmanagers = factory.getTrustManagers();
		if (trustmanagers.length == 0) {
			throw new NoSuchAlgorithmException("no trust manager found");
		}
		this.standardTrustManager = (X509TrustManager) trustmanagers[0];
	}

	/**
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[], String authType)
	 */
	@Override
	public void checkClientTrusted(final X509Certificate[] certificates, final String authType) throws CertificateException {
		standardTrustManager.checkClientTrusted(certificates, authType);
	}

	/**
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[], String authType)
	 */
	@Override
	public void checkServerTrusted(final X509Certificate[] certificates, final String authType) throws CertificateException {
		if ((certificates != null) && (certificates.length == 1)) {
			certificates[0].checkValidity();
		} else {
			// standardTrustManager.checkServerTrusted( certificates, authType);
		}
	}

	/**
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return this.standardTrustManager.getAcceptedIssuers();
	}
}