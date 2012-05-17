
package com.chronosystems.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chronosystems.entity.Device;
import com.chronosystems.service.local.UserFunctions;

public class HomeActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		final TextView displayName = (TextView) findViewById(R.id.displayName);
		final Device device = UserFunctions.getCurrentUser(this);
		displayName.setText(device.getName());
		displayName.setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				final Intent i = new Intent(getApplicationContext(), PlacesHorzScrollListMenu.class);
				startActivity(i);
			}
		});

		final Button btnLogout = (Button) findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(final View view) {
				UserFunctions.logoutUser(getApplicationContext());
				final Intent login = new Intent(getApplicationContext(), LoginActivity.class);
				login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(login);
				// Closing dashboard screen
				finish();
			}
		});
	}
}