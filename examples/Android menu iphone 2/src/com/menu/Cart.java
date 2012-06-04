package com.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Cart extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
	}
}