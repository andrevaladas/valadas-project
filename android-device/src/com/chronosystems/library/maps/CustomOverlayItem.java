package com.chronosystems.library.maps;

import android.graphics.Bitmap;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CustomOverlayItem extends OverlayItem {

	protected Bitmap image;

	public CustomOverlayItem(final GeoPoint point, final String title, final String snippet, final Bitmap bitmap) {
		super(point, title, snippet);
		image = bitmap;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(final Bitmap bitmap) {
		this.image = bitmap;
	}

}
