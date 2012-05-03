package com.chronosystems.library.maps;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chronosystems.maps.core.view.BalloonOverlayView;
import com.chronosystems.maps.core.view.R;
import com.google.android.maps.OverlayItem;

public class CustomBalloonOverlayView<Item extends OverlayItem> extends BalloonOverlayView<CustomOverlayItem> {

	private TextView title;
	private TextView snippet;
	private ImageView image;

	public CustomBalloonOverlayView(final Context context, final int balloonBottomOffset) {
		super(context, balloonBottomOffset);
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
		// bitmap results would normally be cached, but this is good enough for demo purpose.
		image.setImageResource(R.drawable.icon);
		new FetchImageTask() {
			@Override
			protected void onPostExecute(final Bitmap result) {
				if (result != null) {
					image.setImageBitmap(result);
				}
			}
		}.execute(item.getImageURL());

	}

	private class FetchImageTask extends AsyncTask<String, Integer, Bitmap> {
		@Override
		protected Bitmap doInBackground(final String... arg0) {
			Bitmap b = null;
			try {
				//Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_resource);
				b = BitmapFactory.decodeStream((InputStream) new URL(arg0[0]).getContent());
			} catch (final MalformedURLException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			return b;
		}
	}

}
