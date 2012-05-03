
package com.chronosystems.library.maps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.chronosystems.maps.core.view.BalloonItemizedOverlay;
import com.chronosystems.maps.core.view.BalloonOverlayView;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay<Item extends OverlayItem> extends BalloonItemizedOverlay<CustomOverlayItem> {

	private final ArrayList<CustomOverlayItem> m_overlays = new ArrayList<CustomOverlayItem>();
	private final Context c;

	public CustomItemizedOverlay(final Drawable defaultMarker, final MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
	}

	public void addOverlay(final CustomOverlayItem overlay) {
		m_overlays.add(overlay);
		populate();
	}

	@Override
	protected CustomOverlayItem createItem(final int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(final int index, final CustomOverlayItem item) {
		Toast.makeText(c, "onBalloonTap for overlay index " + index, Toast.LENGTH_LONG).show();
		return true;
	}

	@Override
	protected BalloonOverlayView<CustomOverlayItem> createBalloonOverlayView() {
		// use our custom balloon view with our custom overlay item type:
		return new CustomBalloonOverlayView<CustomOverlayItem>(getMapView().getContext(), getBalloonBottomOffset());
	}

}
