/**
 * 
 */
package com.chronosystems.places;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Log;

import com.chronosystems.places.data.PlaceFilter;
import com.chronosystems.places.data.PlaceSearch;
import com.chronosystems.places.xml.XMLParser;

/**
 * @author Andre Valadas
 */
public class AsyncGooglePlacesService extends AsyncTask<PlaceFilter, Void, PlaceSearch> {

	@Override
	protected PlaceSearch doInBackground(final PlaceFilter...args) {

		Log.d("AsyncGooglePlacesService", "doInBackground");

		try {
			final PlaceFilter placeFilter = args[0];
			final URL googlePlaces = new URL(placeFilter.getFilterUrl("https://maps.googleapis.com/maps/api/place/search/xml"));
			final URLConnection tc = googlePlaces.openConnection();
			final BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream(), "UTF-8"));

			String line;
			final StringBuffer xml = new StringBuffer();
			while ((line = in.readLine()) != null) {
				xml.append(line);
			}
			return XMLParser.parseXML(xml.toString(), PlaceSearch.class);
		} catch (final Exception e) {
			Log.e("AsyncGooglePlacesService", "doInBackground", e);
		}
		return null;
	}
}
