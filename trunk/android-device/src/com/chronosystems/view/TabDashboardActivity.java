package com.chronosystems.view;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.TabActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.chronosystems.R;
import com.chronosystems.library.utils.GpsUtils;
import com.chronosystems.library.utils.QuickPrefsUtils;
import com.chronosystems.service.local.CheckinService;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.tracker.TrackListener;
import com.chronosystems.service.tracker.TrackLocationService;
import com.chronosystems.service.tracker.TrackUpdateListener;
import com.chronosystems.wakeful.WakefulIntentService;

public class TabDashboardActivity extends TabActivity {
	// Checkin Service
	private final CheckinService checkinService = new CheckinService();
	private ImageView locationUpdateIndicator;

	private static final AtomicInteger count = new AtomicInteger();
	private static final AtomicInteger updates = new AtomicInteger();
	private TextView trackCount;

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
			locationUpdateIndicator = (ImageView) findViewById(R.id.location_update_indicator);

			// log updates
			trackCount = (TextView)findViewById(R.id.trackCount);
			if (QuickPrefsUtils.locationUpdateEnabled(this) && QuickPrefsUtils.showLogUpdates(this)) {
				trackCount.setText(String.valueOf(count.get()+" #"+updates.get()));
			}

			/** START THE UPDATE LOCATION SERVICE*/
			if (GpsUtils.checkConfiguration(this)) {
				startTrackLocationService();
			}

			final TabHost tabHost = getTabHost();

			// Tab for Home
			final TabSpec homeTab = tabHost.newTabSpec(getString(R.string.home));
			homeTab.setIndicator(getString(R.string.home), getResources().getDrawable(R.drawable.icon_home_tab));
			final Intent homeIntent = new Intent(this, HomeActivity.class);
			homeTab.setContent(homeIntent);

			// Tab for Following
			final TabSpec followTab = tabHost.newTabSpec(getString(R.string.following));
			followTab.setIndicator(getString(R.string.following), getResources().getDrawable(R.drawable.icon_following_tab));
			final Intent followIntent = new Intent(this, FollowingListViewActivity.class);
			followTab.setContent(followIntent);

			// Tab for Places
			final TabSpec placesTab = tabHost.newTabSpec(getString(R.string.places));
			placesTab.setIndicator(getString(R.string.places), getResources().getDrawable(R.drawable.icon_places_tab));
			final Intent placesIntent = new Intent(this, PlacesViewActivity.class);
			placesTab.setContent(placesIntent);

			final TabSpec searchTab = tabHost.newTabSpec(getString(R.string.search));
			searchTab.setIndicator(getString(R.string.search), getResources().getDrawable(R.drawable.icon_search_tab));
			final Intent searchIntent = new Intent(this, ListViewActivity.class);
			searchTab.setContent(searchIntent);

			// Adding all TabSpec to TabHost
			tabHost.addTab(homeTab);
			tabHost.addTab(followTab);
			tabHost.addTab(placesTab);
			tabHost.addTab(searchTab);
			//tabHost.addTab(profileTab);
			tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				public void onTabChanged(final String tabId) {
					// Call external PlacesViewActivity
					if(placesTab.getTag().equals(tabId)) {
						final Intent i = new Intent(getApplicationContext(), PlacesViewActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}
				}
			});

			final ImageButton btnCheckin = (ImageButton) findViewById(R.id.btnCheckin);
			btnCheckin.setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					checkinService.execute(TabDashboardActivity.this);
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

	private void startTrackLocationService() {
		final TrackListener listener = new TrackListener(new TrackUpdateListener() {
			public void update(final Location location, final float distance, final boolean update) {
				runOnUiThread(
						new Runnable() {
							public void run() {
								count.getAndIncrement();
								if (update) {
									updates.getAndIncrement();
								}
								trackCount.setText(String.valueOf(count.get()+" #"+updates.get()));
								if (location != null) {
									Toast.makeText(getApplicationContext(), "#Location UPDATE ["+update+"]" +
											"\ndistance: "+distance+
											"\n\nlat.: "+location.getLatitude()+
											"\nlog.: "+location.getLongitude()+
											"\naccuracy.: "+location.getAccuracy()+
											"\nspeed.: "+location.getSpeed()+
											"\ntime.: "+new Date(location.getTime()), Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(getApplicationContext(), "LastLocation not found!", Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});
		WakefulIntentService.scheduleAlarms(listener, this, false);
	}

	@Override
	protected void onResume() {
		refreshLocationUpdateIndicator();
		if (!QuickPrefsUtils.showLogUpdates(this)) {
			trackCount.setText(null);
		}
		if (GpsUtils.isEnabled(this)) {
			startTrackLocationService();
		}
		super.onResume();
	}

	private void refreshLocationUpdateIndicator() {
		if (QuickPrefsUtils.locationUpdateEnabled(this)) {
			locationUpdateIndicator.setBackgroundDrawable(getResources().getDrawable(R.drawable.location_update_on));
		} else {
			locationUpdateIndicator.setBackgroundDrawable(getResources().getDrawable(R.drawable.location_update_off));
		}
	}

	public void onLocationUpdateIndicatorClick(final View v) {
		QuickPrefsUtils.changeLocationUpdate(this);
		refreshLocationUpdateIndicator();
		Toast.makeText(this, "Update location "+(QuickPrefsUtils.locationUpdateEnabled(this) ? "on" : "off"), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_preferences:
			startActivity(new Intent(getApplicationContext(), QuickPrefsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		checkinService.stopService();
		super.onPause();
	}

	@Override
	protected void onStop() {
		checkinService.stopService();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		checkinService.stopService();
		final Intent svc = new Intent(this, TrackLocationService.class);
		stopService(svc);
		super.onDestroy();
	}
}