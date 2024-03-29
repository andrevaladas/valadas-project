package com.chronosystems.maps.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.google.android.maps.MapView;

public class TapControlledMapView extends MapView implements OnGestureListener {

	private GestureDetector gd;
	private OnSingleTapListener singleTapListener;

	public TapControlledMapView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		setupGestures();
	}

	public TapControlledMapView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		setupGestures();
	}

	public TapControlledMapView(final Context context, final String apiKey) {
		super(context, apiKey);
		setupGestures();
	}

	private void setupGestures() {
		gd = new GestureDetector(this);

		//set the on Double tap listener
		gd.setOnDoubleTapListener(new OnDoubleTapListener() {

			@Override
			public boolean onSingleTapConfirmed(final MotionEvent e) {
				if (singleTapListener != null) {
					return singleTapListener.onSingleTap(e);
				} else {
					return false;
				}
			}

			@Override
			public boolean onDoubleTap(final MotionEvent e) {
				TapControlledMapView.this.getController().zoomInFixing((int) e.getX(), (int) e.getY());
				return false;
			}

			@Override
			public boolean onDoubleTapEvent(final MotionEvent e) {
				return false;
			}

		});
	}

	@Override
	public boolean onTouchEvent(final MotionEvent ev) {
		if (this.gd.onTouchEvent(ev)) {
			return true;
		} else {
			return super.onTouchEvent(ev);
		}
	}

	public void setOnSingleTapListener(final OnSingleTapListener singleTapListener) {
		this.singleTapListener = singleTapListener;
	}

	@Override
	public boolean onDown(final MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(final MotionEvent e) {}

	@Override
	public boolean onSingleTapUp(final MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX,
			final float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(final MotionEvent e) {}

	@Override
	public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX,
			final float velocityY) {
		return false;
	}

}


