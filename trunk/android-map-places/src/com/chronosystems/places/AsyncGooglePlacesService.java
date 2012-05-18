/**
 * 
 */
package com.chronosystems.places;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chronosystems.places.data.PlaceFilter;
import com.chronosystems.places.data.PlaceSearch;
import com.chronosystems.places.view.R;
import com.chronosystems.places.xml.XMLParser;
import com.chronosystems.ssl.EasySSLSocketFactory;

/**
 * @author Andre Valadas
 */
public class AsyncGooglePlacesService extends AsyncTask<PlaceFilter, Void, PlaceSearch> {

	// Progress Dialog
	private ProgressDialog pDialog;

	private final Context currentContext;
	private String processMessage = "Loading... please wait";

	public AsyncGooglePlacesService(final Context currentContext) {
		super();
		this.currentContext = currentContext;
	}

	public AsyncGooglePlacesService(final Context currentContext, final String processMessage) {
		super();
		this.currentContext = currentContext;
		this.processMessage = processMessage;
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(currentContext);
		pDialog.setMessage(processMessage);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	@Override
	protected PlaceSearch doInBackground(final PlaceFilter...args) {

		Log.d("AsyncGooglePlacesService", "doInBackground");

		try {
			final SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

			final HttpParams params = new BasicHttpParams();
			params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
			params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
			params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

			final ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);
			final DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);

			final PlaceFilter placeFilter = args[0];
			final HttpGet httpGet = new HttpGet(placeFilter.getFilterUrl("https://maps.googleapis.com/maps/api/place/search/xml"));
			final HttpResponse httpGetResponse = httpClient.execute(httpGet);
			return XMLParser.parseXML(EntityUtils.toString(httpGetResponse.getEntity(), "UTF-8"), PlaceSearch.class);
		} catch (final Exception e) {
			Log.e("AsyncGooglePlacesService", "doInBackground", e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(final PlaceSearch result) {
		// dismiss the dialog once done
		pDialog.dismiss();
		if (result == null) {
			// Places no found
			Toast.makeText(currentContext, R.string.placesNotFound, Toast.LENGTH_LONG);
		}
	}
}
