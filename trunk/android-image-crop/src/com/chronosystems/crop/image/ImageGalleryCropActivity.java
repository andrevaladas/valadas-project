package com.chronosystems.crop.image;

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
import android.widget.ImageView;
import android.widget.Toast;

public class ImageGalleryCropActivity extends Activity {
	private Uri mImageCaptureUri;
	private ImageView mImageView;
	byte[] byteArray;

	private static final int PICK_FROM_FILE = 1;
	private static final int CROP_FROM_CAMERA = 2;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button button = (Button) findViewById(R.id.btn_crop);
		mImageView = (ImageView) findViewById(R.id.iv_photo);
		mImageView.setImageBitmap(ImageHelper.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo)));

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				//pick from file
				final Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
			}
		});
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case PICK_FROM_FILE: {
			mImageCaptureUri = data.getData();
			doCrop();
			break;
		}

		case CROP_FROM_CAMERA: {
			final Bundle extras = data.getExtras();
			if (extras != null) {
				final Bitmap photo = extras.getParcelable("data");
				final ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byteArray = stream.toByteArray();
				mImageView.setImageBitmap(ImageHelper.getRoundedBitmap(photo));
			}

			final File f = new File(mImageCaptureUri.getPath());
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