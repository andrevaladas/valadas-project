package com.chronosystems.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class PullToRefreshWebView extends PullToRefreshBase<WebView> {

	private final OnRefreshListener defaultOnRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			mRefreshableView.reload();
		}

	};

	private final WebChromeClient defaultWebChromeClient = new WebChromeClient() {

		@Override
		public void onProgressChanged(final WebView view, final int newProgress) {
			if (newProgress == 100) {
				onRefreshComplete();
			}
		}

	};

	public PullToRefreshWebView(final Context context) {
		super(context);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		setOnRefreshListener(defaultOnRefreshListener);
		mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public PullToRefreshWebView(final Context context, final int mode) {
		super(context, mode);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		setOnRefreshListener(defaultOnRefreshListener);
		mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public PullToRefreshWebView(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		setOnRefreshListener(defaultOnRefreshListener);
		mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	@Override
	protected WebView createRefreshableView(final Context context, final AttributeSet attrs) {
		final WebView webView = new WebView(context, attrs);

		webView.setId(R.id.webview);
		return webView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		final int exactContentHeight = (int) Math.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale());
		return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
	}
}
