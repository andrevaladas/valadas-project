package com.chronosystems.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.util.XMLParser;

@Deprecated
public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() { }

	public JSONObject getJSONFromUrl(final String url, final Device device) {

		// Making HTTP request
		try {
			// defaultHttpClient
			final DefaultHttpClient httpClient = new DefaultHttpClient();
			final HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "text/xml; charset=UTF-8");

			final ByteArrayEntity entity = new ByteArrayEntity(XMLParser.parseXML(device).getBytes("UTF-8"));//Xml.Encoding.UTF_8
			entity.setContentType("text/xml");
			httpPost.setEntity(entity);

			final HttpResponse httpResponse = httpClient.execute(httpPost);
			final HttpEntity httpEntity = httpResponse.getEntity();
			//data not found
			if (httpEntity == null) {
				return null;
			}
			is = httpEntity.getContent();

		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (final ClientProtocolException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			final StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (final Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (final JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;
	}

}