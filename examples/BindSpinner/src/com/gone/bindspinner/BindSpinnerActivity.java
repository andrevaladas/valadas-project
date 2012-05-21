/*
 * Jeevan Reddy G
 * Ref: http://developer.android.com/resources/tutorials/views/hello-spinner.html
 */
package com.gone.bindspinner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class BindSpinnerActivity extends Activity {
	static final String[] COUNTRIES = new String[] { "India", "US", "UK",
		"Sri Lanka", "Austraila", "Denmark", "Saudi" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * Binding static Array to the spinner
		 */
		final Spinner spCountry = (Spinner) findViewById(R.id.spCounty);
		final ArrayAdapter<CharSequence> adCountry = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, COUNTRIES);
		adCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCountry.setAdapter(adCountry);

		/*
		 * Binding resource Array to the Spinner Approach 1
		 */
		final Spinner spCities = (Spinner) findViewById(R.id.spCities);
		final String[] cities = getResources().getStringArray(R.array.arr_cities);
		final ArrayAdapter<CharSequence> adCities = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, cities);

		adCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCities.setAdapter(adCities);

		/*
		 * Binding resource Array to the Spinner Approach 2
		 */
		final Spinner spLocalities = (Spinner) findViewById(R.id.spLocality);
		final ArrayAdapter<CharSequence> adLocalities = ArrayAdapter.createFromResource(this, R.array.arr_localities,android.R.layout.simple_spinner_item);
		adLocalities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spLocalities.setAdapter(adLocalities);
	}
}