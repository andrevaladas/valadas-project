package net.londatiga.android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Uri mImageCaptureUri;
	private ImageView mImageView;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		final String [] items = new String [] {"Take from camera", "Select from gallery"};
		final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Image");
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick( final DialogInterface dialog, final int item ) { //pick from camera
				if (item == 0) {
					final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
							"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

					try {
						intent.putExtra("return-data", true);
						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (final ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else { //pick from file
					final Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		} );

		final AlertDialog dialog = builder.create();
		final Button button = (Button) findViewById(R.id.btn_crop);
		mImageView = (ImageView) findViewById(R.id.iv_photo);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				dialog.show();
			}
		});
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case PICK_FROM_CAMERA:
			doCrop();
			break;

		case PICK_FROM_FILE:
			mImageCaptureUri = data.getData();
			doCrop();
			break;

		case CROP_FROM_CAMERA:
			final Bundle extras = data.getExtras();
			if (extras != null) {
				final Bitmap photo = extras.getParcelable("data");
				mImageView.setImageBitmap(photo);
			}

			final File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}
			break;
		}
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		final List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
		final int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

			return;
		} else {
			intent.setData(mImageCaptureUri);

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
					@Override
					public void onClick( final DialogInterface dialog, final int item ) {
						startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
					}
				});

				builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel( final DialogInterface dialog ) {

						if (mImageCaptureUri != null ) {
							getContentResolver().delete(mImageCaptureUri, null, null );
							mImageCaptureUri = null;
						}
					}
				} );

				final AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}
}