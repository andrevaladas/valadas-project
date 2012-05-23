package com.chronosystems.library.sliding;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.chronosystems.R;

public class SlidingPanel extends LinearLayout {
	private int speed = 300;
	private boolean isOpen = false;
	private Animation fadeOut = null;

	public SlidingPanel(final Context ctxt, final AttributeSet attrs) {
		super(ctxt, attrs);

		final TypedArray a = ctxt.obtainStyledAttributes(attrs, R.styleable.SlidingPanel, 0, 0);
		speed = a.getInt(R.styleable.SlidingPanel_speed, 300);
		a.recycle();
		fadeOut = AnimationUtils.loadAnimation(ctxt, R.anim.fade);
	}

	public boolean toggle() {
		TranslateAnimation anim = null;
		final AnimationSet set = new AnimationSet(true);

		isOpen = !isOpen;

		if (isOpen) {
			setVisibility(View.VISIBLE);
			anim = new TranslateAnimation(0.0f, 0.0f, getHeight(), 0.0f);
		} else {
			anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, getHeight());
			anim.setAnimationListener(collapseListener);
			set.addAnimation(fadeOut);
		}

		set.addAnimation(anim);
		set.setDuration(speed);
		set.setInterpolator(new AccelerateInterpolator(1.0f));
		startAnimation(set);
		return isOpen;
	}

	Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
		public void onAnimationEnd(final Animation animation) {
			setVisibility(View.GONE);
		}

		public void onAnimationRepeat(final Animation animation) {
			// not needed
		}

		public void onAnimationStart(final Animation animation) {
			// not needed
		}
	};
}