
package com.chronosystems.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.chronosystems.service.UserFunctions;

public class DashboardActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * Dashboard Screen for the application
		 * */
		// Check login status in database
		if(UserFunctions.isUserLoggedIn(getApplicationContext())){
			setContentView(R.layout.dashboard);

			final ImageButton btnCheckin = (ImageButton) findViewById(R.id.btnCheckin);
			btnCheckin.setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					final Intent list = new Intent(getApplicationContext(), ListViewActivity.class);
					list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(list);
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

		} else {
			// user is not logged in show login screen
			final Intent login = new Intent(getApplicationContext(), LoginActivity.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			// Closing dashboard screen
			finish();
		}
	}
}