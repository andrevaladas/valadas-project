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

public class LoginActivity extends Activity {
	Button btnLogin;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerScreen;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
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

 			public void onClick(View view) {
 				final String email = inputEmail.getText().toString();
 				final String password = inputPassword.getText().toString();

 				/** validate filelds */
 				if (!validateFields(email, password)) {
 					return;
 				}

 				Log.d("Button", "Login");
 				new ExecuteActivity(LoginActivity.this){
 					@Override
 					protected String doInBackground(String... args) {
 						final Entity entity = UserFunctions.loginUser(email, password);

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
 		 						Toast.makeText(getApplicationContext(), "Incorrect username/password", Toast.LENGTH_SHORT).show();
 		 					}
 		 				} catch (Exception e) {
 		 					e.printStackTrace();
 		 				}
 						return null;
 					}
 				}.execute();
 			}
 		});

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
    }
    
    private boolean validateFields(String email, String password) {
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