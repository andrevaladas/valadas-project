package com.chronosystems.view;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.chronosystems.R;
import com.chronosystems.service.MyLocationService;
import com.chronosystems.service.ServiceUpdateListener;
import com.chronosystems.service.local.CheckinService;
import com.chronosystems.service.local.UserFunctions;

public class TabDashboardActivity extends TabActivity {
	// Checkin Service
	final CheckinService checkinService = new CheckinService();

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

			/** START THE UPDATE LOCATION SERVICE*/
			MyLocationService.setMainActivity(this);
			final Intent svc = new Intent(getApplicationContext(), MyLocationService.class);
			startService(svc);
			MyLocationService.setUpdateListener(new ServiceUpdateListener() {
				public void update(final String message) {
					Toast.makeText(TabDashboardActivity.this, message, Toast.LENGTH_SHORT);
				}
			});

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

			// Tab for Profile
			//			final TabSpec profileTab = tabHost.newTabSpec(getString(R.string.profile));
			//			profileTab.setIndicator(getString(R.string.profile), getResources().getDrawable(R.drawable.icon_profile_tab));
			//			final Intent profileIntent = new Intent(this, HomeActivity.class);
			//			profileTab.setContent(profileIntent);

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
		final Intent svc = new Intent(this, MyLocationService.class);
		stopService(svc);
		super.onDestroy();
	}
}