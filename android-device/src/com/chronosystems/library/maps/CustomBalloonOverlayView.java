package com.chronosystems.library.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chronosystems.crop.image.ImageHelper;
import com.chronosystems.maps.core.view.BalloonOverlayView;
import com.chronosystems.maps.core.view.R;
import com.google.android.maps.OverlayItem;

public class CustomBalloonOverlayView<Item extends OverlayItem> extends BalloonOverlayView<CustomOverlayItem> {

	private TextView title;
	private TextView snippet;
	private ImageView image;
	private Bitmap noImage;

	public CustomBalloonOverlayView(final Context context, final int balloonBottomOffset) {
		super(context, balloonBottomOffset);
		if (noImage == null) {
			noImage = ImageHelper.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.no_image));
		}
	}

	@Override
	protected void setupView(final Context context, final ViewGroup parent) {

		// inflate our custom layout into parent
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View v = inflater.inflate(R.layout.balloon_overlay, parent);

		// setup our fields
		title = (TextView) v.findViewById(R.id.balloon_item_title);
		snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);
		image = (ImageView) v.findViewById(R.id.balloon_item_image);
	}

	@Override
	protected void setBalloonData(final CustomOverlayItem item, final ViewGroup parent) {

		// map our custom item data to fields
		title.setText(item.getTitle());
		snippet.setText(item.getSnippet());

		// get remote image from network.
		new FetchImageTask() {
			@Override
			protected void onPostExecute(final Bitmap result) {
				if (result != null) {
					image.setImageBitmap(result);
				} else {
					// bitmap results would normally be cached, but this is good enough for demo purpose.
					image.setImageBitmap(noImage);
				}
			}
		}.execute(item.getImage());
	}

	private class FetchImageTask extends AsyncTask<Bitmap, Integer, Bitmap> {
		@Override
		protected Bitmap doInBackground(final Bitmap... params) {
			return params[0];
		}
	}

}
