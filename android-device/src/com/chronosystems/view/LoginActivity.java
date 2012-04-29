package com.chronosystems.view;

import static com.chronosystems.model.DeviceField.KEY_CREATED_AT;
import static com.chronosystems.model.DeviceField.KEY_EMAIL;
import static com.chronosystems.model.DeviceField.KEY_NAME;
import static com.chronosystems.model.DeviceField.KEY_SUCCESS;
import static com.chronosystems.model.DeviceField.KEY_UID;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chronosystems.library.DatabaseHandler;
import com.chronosystems.library.UserFunctions;

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
 				String email = inputEmail.getText().toString();
 				String password = inputPassword.getText().toString();

 				/** validate filelds */
 				if (!validateFields(email, password)) {
 					return;
 				}

 				Log.d("Button", "Login");
 				UserFunctions userFunction = new UserFunctions();
 				JSONObject json = userFunction.loginUser(email, password);

 				// check for login response
 				try {
 					if (json != null && json.getString(KEY_SUCCESS) != null) {
						// user successfully logged in
						// Store user details in SQLite Database
						DatabaseHandler db = new DatabaseHandler(getApplicationContext());
						JSONObject json_user = json.getJSONArray(KEY_SUCCESS).getJSONObject(0);

						// Clear all previous data in database
						userFunction.logoutUser(getApplicationContext());
						db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

						// Launch Dashboard Screen
						Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);

						// Close all views before launching Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);
						
						// Close Login Screen
						finish();
 					} else {
 						// Error in login
 						Toast.makeText(getApplicationContext(), "Incorrect username/password", Toast.LENGTH_SHORT).show();
 					}
 				} catch (JSONException e) {
 					e.printStackTrace();
 				}
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