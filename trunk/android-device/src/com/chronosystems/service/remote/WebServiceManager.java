package com.chronosystems.service.remote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.chronosystems.entity.Entity;
import com.chronosystems.entity.util.XMLParser;

/**
 * @author andrevaladas
 */
public class WebServiceManager {
	static Entity result = null;
	static HttpEntity httpEntity = null;

	public static Entity executeRequest(final String url, final Object T) {

		result = new Entity();
		// Making HTTP request
		try {
			final HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			// The default value is zero, that means the timeout is not used.
			final int timeoutConnection = 5000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			final int timeoutSocket = 60000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			// defaultHttpClient
			final DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			final HttpPost httpPost = new HttpPost(ServerAddress.REMOTE.getUrl()+url);
			httpPost.setHeader("Content-type", "text/xml; charset=UTF-8");

			final ByteArrayEntity entity = new ByteArrayEntity(XMLParser.parseXML(T).getBytes("UTF-8"));//Xml.Encoding.UTF_8
			entity.setContentType("text/xml");
			httpPost.setEntity(entity);

			final HttpResponse httpResponse = httpClient.execute(httpPost);
			httpEntity = httpResponse.getEntity();
			//data not found
			if (httpEntity == null) {
				result.addError("Critical server error!");
				return result;
			}
		} catch (final UnsupportedEncodingException e) {
			result.addError(e.getMessage());
		} catch (final ClientProtocolException e) {
			result.addError(e.getMessage());
		} catch (final IOException e) {
			result.addError(e.getMessage());
		}

		if (!result.hasErrors()) {
			try {
				return XMLParser.parseXML(EntityUtils.toString(httpEntity, "UTF-8"), Entity.class);
			} catch (final Exception e) {
				result.addError(e.getMessage());
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
		}
		return result;
	}
}