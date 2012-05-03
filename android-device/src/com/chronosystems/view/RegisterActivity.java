package com.chronosystems.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.library.dialog.AlertMessage;
import com.chronosystems.service.AsyncService;
import com.chronosystems.service.DatabaseHandler;
import com.chronosystems.service.UserFunctions;

public class RegisterActivity extends Activity {

	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	Button btnRegister;
	TextView loginScreen;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		loginScreen = (TextView) findViewById(R.id.link_to_login);

		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(final View view) {
				/** validate filelds */
				if (!validateForm()) {
					return;
				}

				//Execute service
				new AsyncService(RegisterActivity.this) {
					@Override
					protected String doInBackground(final String... args) {

						final Entity entity = UserFunctions.loginUser(
								inputEmail.getText().toString(),
								inputPassword.getText().toString());

						// check for login response
						try {
							if (entity != null && !entity.getDevices().isEmpty()) {
								// user successfully registred
								// Store user details in SQLite Database
								final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
								final Device device = entity.getDevices().get(0);

								// Clear all previous data in database
								UserFunctions.logoutUser(getApplicationContext());
								db.addUser(device.getName(),
										device.getEmail(),
										device.getId().toString(),
										device.getDatecreated().toString());

								// Launch Dashboard Screen
								final Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);

								// Close all views before launching Dashboard
								dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(dashboard);
								// Close Registration Screen
								finish();
							} else {
								// Error in registration
								return "Error occured in registration";
							}
						} catch (final Exception e) {
							e.printStackTrace();
							return e.getMessage();
						}
						return null;
					}
				}.execute();
			}
		});

		// Listening to Login Screen link
		loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(final View view) {
				final Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}

	private boolean validateForm() {
		if (inputFullName.getText().toString().length() < 1) {
			AlertMessage.show("Full name is required.", this);
			inputFullName.requestFocus();
			return false;
		}
		if (inputEmail.getText().toString().length() < 1) {
			AlertMessage.show("Username is required.", this);
			inputEmail.requestFocus();
			return false;
		}
		if (inputPassword.getText().toString().length() < 1) {
			AlertMessage.show("Password is required.", this);
			inputPassword.requestFocus();
			return false;
		}
		return true;
	}
}