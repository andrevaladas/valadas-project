package com.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class SignIn extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Toast.makeText(this, "SignIn", Toast.LENGTH_SHORT).show();
	}
}