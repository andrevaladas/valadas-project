package com.chronosystems.library.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CustomOverlayItem extends OverlayItem {

	protected String mImageURL;

	public CustomOverlayItem(final GeoPoint point, final String title, final String snippet, final String imageURL) {
		super(point, title, snippet);
		mImageURL = imageURL;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(final String imageURL) {
		this.mImageURL = imageURL;
	}

}
