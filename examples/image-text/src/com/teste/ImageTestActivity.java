package com.teste;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

public class ImageTestActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final TextView textView  = (TextView) findViewById(R.id.textview);
		final SpannableString ss = new SpannableString("#feito!");
		final Drawable d = getResources().getDrawable(R.drawable.icon);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		final ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		textView.setText(ss);
	}
}