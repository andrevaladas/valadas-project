package com.menu;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidMenuActivity extends TabActivity {

	public static Button btnRed; // Works as a badge
	//Declared static; so it can be accessed from all other Activities
	public static TabHost tabHost;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.host);

		tabHost = (TabHost)findViewById(android.R.id.tabhost);

		final TabSpec homeTabSpec = tabHost.newTabSpec("tid1");
		final TabSpec signinTabSpec = tabHost.newTabSpec("tid2");
		final TabSpec cartTabSpec = tabHost.newTabSpec("tid3");
		final TabSpec moreTabSpec = tabHost.newTabSpec("tid4");
		final TabSpec searchTabSpec = tabHost.newTabSpec("tid5");

		//Make Intents to your Activities or ActivityGroups
		final Intent intent1 = new Intent(this, Cart.class);
		final Intent intent2 = new Intent(this, Home.class);
		final Intent intent3 = new Intent(this, SignIn.class);
		final Intent intent4 = new Intent(this, Search.class);
		final Intent intent5 = new Intent(this, More.class);

		final LayoutInflater layoutInflater = getLayoutInflater();
		final View layout_with_badge = layoutInflater.inflate(R.layout.cartbottom, null);
		layout_with_badge.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		btnRed = (Button) layout_with_badge.findViewById(R.id.redbtn);

		final String cnt = String.valueOf("1");// Number on the badge

		btnRed.setBackgroundDrawable(getResources().getDrawable(R.drawable.indicator));

		btnRed.setText(cnt);
		btnRed.setOnClickListener(new OnClickListener() {

			//@Override
			@Override
			public void onClick(final View v) {
				tabHost.setCurrentTab(2);
			}
		});

		ImageView img1 = new ImageView(this);
		img1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		img1.setImageDrawable(getResources().getDrawable(R.drawable.indicator));

		//valadas
		//cartTabSpec.setIndicator(layout_with_badge).setContent(intent1);
		cartTabSpec.setIndicator(new ImageView(this)).setContent(intent1);

		Drawable d = getResources().getDrawable(R.drawable.homeselector);
		img1 = new ImageView(this);
		img1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		img1.setImageDrawable(d);
		homeTabSpec.setIndicator(layout_with_badge).setContent(intent2);

		d = getResources().getDrawable(R.drawable.signinselector);
		img1 = new ImageView(this);
		img1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		img1.setImageDrawable(d);
		signinTabSpec.setIndicator(img1).setContent(intent3);

		d = getResources().getDrawable(R.drawable.searchselector);
		img1 = new ImageView(this);
		img1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		img1.setImageDrawable(d);
		searchTabSpec.setIndicator(img1).setContent(intent4);

		d = getResources().getDrawable(R.drawable.moreselector);
		img1 = new ImageView(this);
		img1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		img1.setImageDrawable(d);
		moreTabSpec.setIndicator(img1).setContent(intent5);

		/* Add tabSpec to the TabHost to display. */
		tabHost.addTab(homeTabSpec);
		tabHost.addTab(signinTabSpec);
		tabHost.addTab(cartTabSpec);
		tabHost.addTab(searchTabSpec);
		tabHost.addTab(moreTabSpec);

	}
}