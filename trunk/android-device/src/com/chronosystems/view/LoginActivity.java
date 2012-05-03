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

public class LoginActivity extends Activity {
	Button btnLogin;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerScreen;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputEmail.setText("andrevaladas");
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		registerScreen = (TextView) findViewById(R.id.link_to_register);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(final View view) {
				/** validate filelds */
				if (!validateForm()) {
					return;
				}

				//Execute service
				new AsyncService(LoginActivity.this) {
					@Override
					protected String doInBackground(final String... args) {
						final Entity entity = UserFunctions.loginUser(
								inputEmail.getText().toString(),
								inputPassword.getText().toString());

						// check for login response
						try {
							if (entity != null && !entity.getDevices().isEmpty()) {
								// user successfully logged in
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

								// Close Login Screen
								finish();
							} else {
								// Error in login
								return "Incorrect username/password";
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

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(final View v) {
				// Switching to Register screen
				final Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	private boolean validateForm() {
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