package com.chronosystems.maps.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.OverlayItem;

/**
 * A view representing a MapView marker information balloon.
 * 
 * @author André Valadas
 */
public class BalloonOverlayView<Item extends OverlayItem> extends FrameLayout {

	private final LinearLayout layout;
	private TextView title;
	private TextView snippet;

	/**
	 * Create a new BalloonOverlayView.
	 * 
	 * @param context - The activity context.
	 * @param balloonBottomOffset - The bottom padding (in pixels) to be applied
	 * when rendering this view.
	 */
	public BalloonOverlayView(final Context context, final int balloonBottomOffset) {

		super(context);

		setPadding(10, 0, 10, balloonBottomOffset);

		layout = new LimitLinearLayout(context);
		layout.setVisibility(VISIBLE);

		setupView(context, layout);

		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.NO_GRAVITY;

		addView(layout, params);

	}

	/**
	 * Inflate and initialize the BalloonOverlayView UI. Override this method
	 * to provide a custom view/layout for the balloon.
	 * 
	 * @param context - The activity context.
	 * @param parent - The root layout into which you must inflate your view.
	 */
	protected void setupView(final Context context, final ViewGroup parent) {

		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View v = inflater.inflate(R.layout.balloon_overlay_core, parent);
		title = (TextView) v.findViewById(R.id.balloon_item_title);
		snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);

	}

	/**
	 * Sets the view data from a given overlay item.
	 * 
	 * @param item - The overlay item containing the relevant view data.
	 */
	public void setData(final Item item) {
		layout.setVisibility(VISIBLE);
		setBalloonData(item, layout);
	}

	/**
	 * Sets the view data from a given overlay item. Override this method to create
	 * your own data/view mappings.
	 * 
	 * @param item - The overlay item containing the relevant view data.
	 * @param parent - The parent layout for this BalloonOverlayView.
	 */
	protected void setBalloonData(final Item item, final ViewGroup parent) {
		if (item.getTitle() != null) {
			title.setVisibility(VISIBLE);
			title.setText(item.getTitle());
		} else {
			title.setText("");
			title.setVisibility(GONE);
		}
		if (item.getSnippet() != null) {
			snippet.setVisibility(VISIBLE);
			snippet.setText(item.getSnippet());
		} else {
			snippet.setText("");
			snippet.setVisibility(GONE);
		}
	}

	private class LimitLinearLayout extends LinearLayout {

		private static final int MAX_WIDTH_DP = 280;

		final float SCALE = getContext().getResources().getDisplayMetrics().density;

		public LimitLinearLayout(final Context context) {
			super(context);
		}

		public LimitLinearLayout(final Context context, final AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
			final int mode = MeasureSpec.getMode(widthMeasureSpec);
			final int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
			final int adjustedMaxWidth = (int)(MAX_WIDTH_DP * SCALE + 0.5f);
			final int adjustedWidth = Math.min(measuredWidth, adjustedMaxWidth);
			final int adjustedWidthMeasureSpec = MeasureSpec.makeMeasureSpec(adjustedWidth, mode);
			super.onMeasure(adjustedWidthMeasureSpec, heightMeasureSpec);
		}
	}

}
