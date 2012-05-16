package com.chronosystems.places.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.chronosystems.places.AsyncGooglePlacesService;
import com.chronosystems.places.data.Place;
import com.chronosystems.places.data.PlaceFilter;
import com.chronosystems.places.data.PlaceSearch;
import com.google.android.maps.GeoPoint;

public class GooglePlacesActivity extends Activity {

	ArrayAdapter<String> adapter;
	AutoCompleteTextView textView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		adapter = new ArrayAdapter<String>(this, R.layout.list_item);
		textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		adapter.setNotifyOnChange(true);
		textView.setAdapter(adapter);
		textView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(final CharSequence s, final int start,
					final int before, final int count) {
				if (s.length() % 5 == 0) {
					// we don't want to make an insanely large array, so we
					// clear it each time
					adapter.clear();

					// create the task
					final AsyncGooglePlacesService task = new AsyncGooglePlacesService(GooglePlacesActivity.this){
						// then our post
						@Override
						protected void onPostExecute(final PlaceSearch result) {
							if (result == null) {
								return;
							}
							// updating UI from Background Thread
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									final List<Place> placeResult = result.getResult();
									Log.d("YourApp", "onPostExecute : " + placeResult.size());
									// update the adapter
									adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.list_item);
									adapter.setNotifyOnChange(true);
									// attach the adapter to textview
									textView.setAdapter(adapter);

									for (final Place place : placeResult) {
										Log.d("YourApp", "onPostExecute : result = " + place.getName());
										adapter.add(place.getName());
										adapter.notifyDataSetChanged();
									}
									Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
								}
							});
						}
					};

					// now pass the argument in the textview to the task
					final PlaceFilter placeFilter = new PlaceFilter();
					placeFilter.setLocation(new GeoPoint((int)(-29.7639*1E6), (int)(-51.1446*1E6)));
					task.execute(placeFilter);
				}
			}

			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

			}

			@Override
			public void afterTextChanged(final Editable s) {

			}
		});
	}
}