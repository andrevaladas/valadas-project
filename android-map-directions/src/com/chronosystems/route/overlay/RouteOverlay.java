package com.chronosystems.route.overlay;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class RouteOverlay extends Overlay {

	private final GeoPoint gp1;
	private final GeoPoint gp2;
	private final int color;

	public RouteOverlay(final GeoPoint gp1, final GeoPoint gp2, final int color) {
		this.gp1 = gp1;
		this.gp2 = gp2;
		this.color = color;
	}

	@Override
	public void draw(final Canvas canvas, final MapView mapView, final boolean shadow) {
		final Projection projection = mapView.getProjection();
		final Paint paint = new Paint();
		final Point point = new Point();
		projection.toPixels(gp1, point);
		paint.setColor(color);
		final Point point2 = new Point();
		projection.toPixels(gp2, point2);
		paint.setStrokeWidth(5);
		paint.setAlpha(120);
		canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
		super.draw(canvas, mapView, shadow);
	}
}
