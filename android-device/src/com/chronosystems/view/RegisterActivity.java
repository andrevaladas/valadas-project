package com.chronosystems.view;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chronosystems.crop.image.CropOption;
import com.chronosystems.crop.image.CropOptionAdapter;
import com.chronosystems.crop.image.ImageHelper;
import com.chronosystems.crop.image.R;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.library.dialog.AlertMessage;
import com.chronosystems.library.utils.ValidatorUtils;
import com.chronosystems.service.local.AsyncService;
import com.chronosystems.service.local.DatabaseHandler;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.DeviceService;

public class RegisterActivity extends Activity {

	private static final int PICK_FROM_FILE = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private ImageView picture;
	private Button selectPicture;
	private Uri imageCaptureUri;
	byte[] byteArray;

	private EditText inputFullName;
	private EditText inputEmail;
	private EditText inputPassword;
	private Button btnRegister;
	private TextView loginScreen;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.register);

		picture = (ImageView) findViewById(R.id.userPicture);
		picture.setImageBitmap(ImageHelper.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.no_image)));

		selectPicture = (Button) findViewById(R.id.selectPicture);
		selectPicture.setOnClickListener(new View.OnClickListener() {
			public void onClick(final View view) {
				//pick from file
				final Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
			}
		});

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
					protected Entity doInBackground(final String... args) {

						final Device newDevice = new Device();
						newDevice.setImage(byteArray);
						newDevice.setName(inputFullName.getText().toString());
						newDevice.setEmail(inputEmail.getText().toString());
						newDevice.setPassword(inputPassword.getText().toString());

						final Entity entity = DeviceService.registerUser(newDevice);
						// check for login response
						try {
							if (entity.hasErrors()) {
								return entity;
							} else if (entity.hasDevices()) {
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
								final Intent dashboard = new Intent(getApplicationContext(), TabDashboardActivity.class);

								// Close all views before launching Dashboard
								dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(dashboard);
								// Close Registration Screen
								finish();
							} else {
								// Error in registration
								entity.addAlert(getString(R.string.registrationError));
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
		if (byteArray == null) {
			AlertMessage.show(getString(R.string.pictureRequired), this);
			return false;
		}
		if (inputFullName.getText().toString().length() < 1) {
			AlertMessage.show(getString(R.string.nameRequired), this);
			inputFullName.requestFocus();
			return false;
		}
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

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case PICK_FROM_FILE: {
			imageCaptureUri = data.getData();
			doCrop();
			break;
		}

		case CROP_FROM_CAMERA: {
			final Bundle extras = data.getExtras();
			if (extras != null) {
				final Bitmap photo = extras.getParcelable("data");
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 30, baos);

				final Bitmap photoCompressed = ImageHelper.decodeToLowResolution(baos.toByteArray());
				final ByteArrayOutputStream baosCompressed = new ByteArrayOutputStream();
				photoCompressed.compress(Bitmap.CompressFormat.JPEG, 30, baosCompressed);

				byteArray = baosCompressed.toByteArray();
				picture.setImageBitmap(ImageHelper.getRoundedBitmap(photoCompressed));
			}

			final File f = new File(imageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}
			break;
		}
		}
	}

	private void doCrop() {
		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		final List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
		final int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.setData(imageCaptureUri);
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				final Intent i = new Intent(intent);
				final ResolveInfo res = list.get(0);
				i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {

				final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
				for (final ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent= new Intent(intent);
					co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
					cropOptions.add(co);
				}

				final CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);

				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
					public void onClick( final DialogInterface dialog, final int item ) {
						startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
					}
				});

				builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
					public void onCancel( final DialogInterface dialog ) {
						if (imageCaptureUri != null ) {
							getContentResolver().delete(imageCaptureUri, null, null );
							imageCaptureUri = null;
						}
					}
				} );

				final AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}
}