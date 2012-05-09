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
import com.chronosystems.library.utils.ValidatorUtils;
import com.chronosystems.service.local.AsyncService;
import com.chronosystems.service.local.DatabaseHandler;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.DeviceService;

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
		inputEmail.setText("andrevaladas@gmail.com");
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
					protected Entity doInBackground(final String... args) {
						final Device login = new Device();
						login.setEmail(inputEmail.getText().toString());
						login.setPassword(inputPassword.getText().toString());
						final Entity entity = DeviceService.loginUser(login);

						// check for login response
						try {
							if (entity.hasErrors()) {
								return entity;
							} else if (entity.hasDevices()) {
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
								final Intent tabDashboard = new Intent(getApplicationContext(), TabDashboardActivity.class);
								// Close all views before launching Dashboard
								tabDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(tabDashboard);

								// Close Login Screen
								finish();
							} else {
								// Error in login
								entity.addAlert(getString(R.string.incorrectEmailPassword));
								return entity;
							}
						} catch (final Exception e) {
							entity.addError(e.getMessage());
							return entity;
						}
						return entity;
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
		final String email = inputEmail.getText().toString();
		if (email.length() < 1) {
			AlertMessage.show(getString(R.string.emailRequired), this);
			inputEmail.requestFocus();
			return false;
		} else if (!ValidatorUtils.isValidEmail(email)) {
			AlertMessage.show(getString(R.string.invalidEmail), this);
			inputEmail.requestFocus();
			return false;
		}
		if (inputPassword.getText().toString().length() < 1) {
			AlertMessage.show(getString(R.string.passwordRequired), this);
			inputPassword.requestFocus();
			return false;
		}
		return true;
	}
}