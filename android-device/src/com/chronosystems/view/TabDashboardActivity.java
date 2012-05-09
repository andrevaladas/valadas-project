package com.chronosystems.view;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.chronosystems.entity.Entity;
import com.chronosystems.gps.GpsLocationService;
import com.chronosystems.library.dialog.ErrorMessage;
import com.chronosystems.library.dialog.SuccessMessage;
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
			homespec.setIndicator(getString(R.string.home), getResources().getDrawable(R.drawable.icon_photos_tab));
			final Intent homeIntent = new Intent(this, HomeActivity.class);
			homespec.setContent(homeIntent);

			// Tab for Friends
			final TabSpec friendspec = tabHost.newTabSpec(getString(R.string.friends));
			friendspec.setIndicator(getString(R.string.friends), getResources().getDrawable(R.drawable.icon_songs_tab));
			final Intent friendsIntent = new Intent(this, ListViewActivity.class);
			friendspec.setContent(friendsIntent);

			// Tab for Me
			final TabSpec mespec = tabHost.newTabSpec(getString(R.string.me));
			mespec.setIndicator(getString(R.string.me), getResources().getDrawable(R.drawable.icon_videos_tab));
			final Intent meIntent = new Intent(this, LoginActivity.class);
			mespec.setContent(meIntent);

			// Tab for Request
			final TabSpec requestspec = tabHost.newTabSpec(getString(R.string.requests));
			requestspec.setIndicator(getString(R.string.requests), getResources().getDrawable(R.drawable.icon_photos_tab));
			final Intent requestIntent = new Intent(this, ListViewActivity.class);
			requestspec.setContent(requestIntent);

			// Adding all TabSpec to TabHost
			tabHost.addTab(homespec); // Adding home tab
			tabHost.addTab(friendspec); // Adding frineds tab
			tabHost.addTab(mespec); // Adding me tab
			tabHost.addTab(requestspec); // Adding requests tab

			tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#4E4E9C"));
			tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#4E4E9C"));
			tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#4E4E9C"));
			tabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.parseColor("#4E4E9C"));

			//		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			//			public void onTabChanged(final String tabId) {
			//				for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
			//					tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); //unselected
			//				}
			//				tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected
			//			}
			//		});

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
		final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			final GpsLocationService locationService = new GpsLocationService();
			final Location checkin = locationService.checkin(locationManager);
			if (checkin != null) {
				showConfirmCheckinMessage(checkin);
			}
		} else {
			// I open here the preferences to force the user start the GPS
			showAlertMessageNoGps();
		}
	}

	/** confirm checkin */
	private void showConfirmCheckinMessage(final Location location) {
		final AlertDialog.Builder confirm = new AlertDialog.Builder(this);
		confirm.setMessage(getString(R.string.confirmCheckin)+"\n"+LocationUtils.getGeocoderAddress(location, getBaseContext()));
		confirm.setIcon(R.drawable.info);

		confirm.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				new AsyncService(TabDashboardActivity.this) {
					@Override
					protected Entity doInBackground(final String... args) {
						return LocationService.checkinLocation(UserFunctions.getCurrentUser(getApplicationContext()), location);
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
		confirm.show();
	}

	/** Builds an alert message to allow the user the option of enabling GPS */
	private void showAlertMessageNoGps() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(getString(R.string.enableGPS));
		alert.setIcon(R.drawable.warning);
		alert.setCancelable(false);

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				launchGPSOptions();
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				dialog.cancel();
			}
		});
		alert.show();
	}
	/** Launches the SecuritySettings activity */
	private void launchGPSOptions() {
		/* bring up the GPS settings */
		startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
	}
}