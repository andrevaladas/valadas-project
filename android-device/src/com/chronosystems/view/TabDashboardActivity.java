package com.chronosystems.view;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.gps.GpsLocationService;
import com.chronosystems.library.dialog.ErrorMessage;
import com.chronosystems.library.dialog.SuccessMessage;
import com.chronosystems.library.utils.GpsUtils;
import com.chronosystems.library.utils.LocationUtils;
import com.chronosystems.service.local.AsyncService;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.LocationService;

public class TabDashboardActivity extends TabActivity {
	TabHost tabHost = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Dashboard Screen for the application
		 * */
		// Check login status in database
		if(UserFunctions.isUserLoggedIn(getApplicationContext())){

			setContentView(R.layout.tab_dashboard);

			tabHost = getTabHost();

			// Tab for Home
			final TabSpec homespec = tabHost.newTabSpec(getString(R.string.home));
			homespec.setIndicator(getString(R.string.home), getResources().getDrawable(R.drawable.icon_home_tab));
			final Intent homeIntent = new Intent(this, HomeActivity.class);
			homespec.setContent(homeIntent);

			final TabSpec tab2 = tabHost.newTabSpec(getString(R.string.profile));
			tab2.setIndicator(getString(R.string.profile), getResources().getDrawable(R.drawable.icon_profile_tab));
			final Intent itab2Intent = new Intent(this, ListViewActivity.class);
			tab2.setContent(itab2Intent);

			// Tab for Friends
			final TabSpec friendspec = tabHost.newTabSpec(getString(R.string.friends));
			friendspec.setIndicator(getString(R.string.friends), getResources().getDrawable(R.drawable.icon_home_tab));
			final Intent friendsIntent = new Intent(this, ListViewActivity.class);
			friendspec.setContent(friendsIntent);

			// Tab for Request
			final TabSpec hometab = tabHost.newTabSpec(getString(R.string.profile));
			hometab.setIndicator(getString(R.string.profile), getResources().getDrawable(R.drawable.icon_profile_tab));
			final Intent requestIntent = new Intent(this, ListViewActivity.class);
			hometab.setContent(requestIntent);

			// Adding all TabSpec to TabHost
			tabHost.addTab(homespec);
			tabHost.addTab(tab2);
			tabHost.addTab(friendspec);
			tabHost.addTab(hometab);

			//			final TabWidget tabWidget = tabHost.getTabWidget();
			//			for (int i = 0; i < tabWidget.getChildCount(); i++) {
			//				if (i == 0) {
			//					tabWidget.getChildAt(i).setBackgroundColor(Color.parseColor("#828282"));
			//				} else {
			//					tabWidget.getChildAt(i).setBackgroundColor(Color.parseColor("#363636"));
			//				}
			//			}

			//			tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			//				public void onTabChanged(final String tabId) {
			//					for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
			//						tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#363636")); //unselected
			//					}
			//					tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#828282")); // selected
			//				}
			//			});

			final ImageButton btnCheckin = (ImageButton) findViewById(R.id.btnCheckin);
			btnCheckin.setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					callCheckin();
				}
			});
		} else {
			// user is not logged in show login screen
			final Intent login = new Intent(getApplicationContext(), LoginActivity.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			// Closing dashboard screen
			finish();
		}
	}

	/**
	 * Checkin Location
	 * @param location
	 */
	private void callCheckin() {
		// Verifica se GPS está ativo
		if (GpsUtils.checkConfiguration(this)){

			// param to set location
			final Device localDevice = new Device();

			// Cria mensagem de confirmação do usuário para checkin
			final AlertDialog.Builder confirm = new AlertDialog.Builder(this);
			confirm.setTitle(getString(R.string.confirmCheckin));
			confirm.setIcon(R.drawable.info);
			confirm.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int id) {
					new AsyncService(TabDashboardActivity.this) {
						@Override
						protected Entity doInBackground(final String... args) {
							// get current user and set current location
							final Device currentUser = UserFunctions.getCurrentUser(getApplicationContext());
							currentUser.setLocations(localDevice.getLocations());

							// send data to server
							return LocationService.checkinLocation(currentUser);
						}
						@Override
						protected void onPostExecute(final Entity result) {
							super.onPostExecute(result);
							if(!result.hasErrors()) {
								SuccessMessage.show(getString(R.string.checkinSuccess), TabDashboardActivity.this);
							} else {
								ErrorMessage.show(getString(R.string.checkinError), TabDashboardActivity.this);
							}
						}
					}.execute();
				}
			});
			confirm.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int id) {
					dialog.cancel();
				}
			});

			// GPS Service
			final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			final GpsLocationService locationService = new GpsLocationService(locationManager);

			// Chama servico de localização do GPS
			new AsyncService(TabDashboardActivity.this, "Checking your location...") {
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					locationService.startService();
				}

				@Override
				protected Entity doInBackground(final String... args) {

					//search for current local for checkin
					final android.location.Location checkin = locationService.getCurrentLocation();

					//create a location checkin
					final Location location = new Location(
							checkin.getLatitude(),
							checkin.getLongitude());

					localDevice.addLocation(location);

					// busca dados da localizacao
					final String geocoderAddress = LocationUtils.getGeocoderAddress(location, getApplicationContext());
					confirm.setMessage(geocoderAddress);

					return null;
				}

				@Override
				protected void onCancelled() {
					super.onCancelled();
					// force shutdown service
					locationService.stopService();
				}

				@Override
				protected void onPostExecute(final Entity result) {
					super.onPostExecute(result);
					// force shutdown service
					locationService.stopService();

					// mostra o alerta de confirmação
					confirm.show();
				}
			}.execute();
		}
	}
}