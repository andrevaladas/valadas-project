package com.chronosystems.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.service.DatabaseHandler;
import com.chronosystems.service.UserFunctions;

public class RegisterActivity extends Activity {

	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	Button btnRegister;
	TextView loginScreen;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
			public void onClick(View view) {
				String name = inputFullName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();

				/** validate filelds */
 				if (!validateFields(name, email, password)) {
 					return;
 				}

 				Log.d("Button", "Register");
 				final Entity entity = UserFunctions.loginUser(email, password);

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
					}else{
						// Error in registration
						Toast.makeText(getApplicationContext(), "Error occured in registration", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
    }
    
    private boolean validateFields(String name, String email, String password) {
    	if (name.length() < 1) {
			inputFullName.requestFocus();
			Toast.makeText(getApplicationContext(), "Informe seu nome", Toast.LENGTH_SHORT).show();
			return false;
		}
    	if (email.length() < 1) {
			inputEmail.requestFocus();
			Toast.makeText(getApplicationContext(), "Informe seu username", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (password.length() < 1) {
			inputPassword.requestFocus();
			Toast.makeText(getApplicationContext(), "Informe seu password", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
    }
}