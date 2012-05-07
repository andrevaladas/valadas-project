package com.chronosystems.view;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabDashboardActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_dashboard);

		final TabHost tabHost = getTabHost();

		// Tab for Photos
		final TabSpec photospec = tabHost.newTabSpec("Dashboard");
		photospec.setIndicator("Dashboard", getResources().getDrawable(R.drawable.icon_photos_tab));
		final Intent photosIntent = new Intent(this, DashboardActivity.class);
		photospec.setContent(photosIntent);

		// Tab for Songs
		final TabSpec songspec = tabHost.newTabSpec("Friends");
		// setting Title and Icon for the Tab
		songspec.setIndicator("Friends", getResources().getDrawable(R.drawable.icon_songs_tab));
		final Intent songsIntent = new Intent(this, ListViewActivity.class);
		songspec.setContent(songsIntent);

		// Tab for Videos
		final TabSpec videospec = tabHost.newTabSpec("Login");
		videospec.setIndicator("Login", getResources().getDrawable(R.drawable.icon_videos_tab));
		final Intent videosIntent = new Intent(this, LoginActivity.class);
		videospec.setContent(videosIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(photospec); // Adding photos tab
		tabHost.addTab(songspec); // Adding songs tab
		tabHost.addTab(videospec); // Adding videos tab
	}
}