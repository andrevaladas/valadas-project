package com.chronosystems.pulltorefresh.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.chronosystems.pulltorefresh.library.internal.EmptyViewMethodAccessor;
import com.chronosystems.pulltorefresh.library.internal.LoadingLayout;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {

	private LoadingLayout mHeaderLoadingView;
	private LoadingLayout mFooterLoadingView;

	private FrameLayout mLvFooterLoadingFrame;
	private boolean mAddedLvFooter = false;

	class InternalListView extends ListView implements EmptyViewMethodAccessor {

		public InternalListView(final Context context, final AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setAdapter(final ListAdapter adapter) {
			// Add the Footer View at the last possible moment
			if (!mAddedLvFooter && null != mLvFooterLoadingFrame) {
				addFooterView(mLvFooterLoadingFrame, null, false);
				mAddedLvFooter = true;
			}

			super.setAdapter(adapter);
		}

		@Override
		public void setEmptyView(final View emptyView) {
			PullToRefreshListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(final View emptyView) {
			super.setEmptyView(emptyView);
		}

		@Override
		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}

		@Override
		public void draw(final Canvas canvas) {
			/**
			 * This is a bit hacky, but ListView has got a bug in it when using
			 * Header/Footer Views and the list is empty. This masks the issue
			 * so that it doesn't cause an FC. See Issue #66.
			 */
			try {
				super.draw(canvas);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public PullToRefreshListView(final Context context) {
		super(context);
		setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(final Context context, final int mode) {
		super(context, mode);
		setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	@Override
	public void setReleaseLabel(final String releaseLabel, final int mode) {
		super.setReleaseLabel(releaseLabel, mode);

		if (null != mHeaderLoadingView && (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH)) {
			mHeaderLoadingView.setReleaseLabel(releaseLabel);
		}
		if (null != mFooterLoadingView && (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH)) {
			mFooterLoadingView.setReleaseLabel(releaseLabel);
		}
	}

	@Override
	public void setPullLabel(final String pullLabel, final int mode) {
		super.setPullLabel(pullLabel, mode);

		if (null != mHeaderLoadingView && (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH)) {
			mHeaderLoadingView.setPullLabel(pullLabel);
		}
		if (null != mFooterLoadingView && (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH)) {
			mFooterLoadingView.setPullLabel(pullLabel);
		}
	}

	@Override
	public void setRefreshingLabel(final String refreshingLabel, final int mode) {
		super.setRefreshingLabel(refreshingLabel, mode);

		if (null != mHeaderLoadingView && (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH)) {
			mHeaderLoadingView.setRefreshingLabel(refreshingLabel);
		}
		if (null != mFooterLoadingView && (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH)) {
			mFooterLoadingView.setRefreshingLabel(refreshingLabel);
		}
	}

	@Override
	protected final ListView createRefreshableView(final Context context, final AttributeSet attrs) {
		final ListView lv = new InternalListView(context, attrs);

		final int mode = getMode();

		// Get Styles from attrs
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);

		// Add Loading Views
		if (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) {
			final FrameLayout frame = new FrameLayout(context);
			mHeaderLoadingView = new LoadingLayout(context, MODE_PULL_DOWN_TO_REFRESH, a);
			frame.addView(mHeaderLoadingView, FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			mHeaderLoadingView.setVisibility(View.GONE);
			lv.addHeaderView(frame, null, false);
		}
		if (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) {
			mLvFooterLoadingFrame = new FrameLayout(context);
			mFooterLoadingView = new LoadingLayout(context, MODE_PULL_UP_TO_REFRESH, a);
			mLvFooterLoadingFrame.addView(mFooterLoadingView, FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			mFooterLoadingView.setVisibility(View.GONE);
		}

		a.recycle();

		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		return lv;
	}

	@Override
	protected void setRefreshingInternal(final boolean doScroll) {

		// If we're not showing the Refreshing view, or the list is empty, then
		// the header/footer views won't show so we use the
		// normal method
		final ListAdapter adapter = mRefreshableView.getAdapter();
		if (!getShowViewWhileRefreshing() || null == adapter || adapter.isEmpty()) {
			super.setRefreshingInternal(doScroll);
			return;
		}

		super.setRefreshingInternal(false);

		final LoadingLayout originalLoadingLayout, listViewLoadingLayout;
		final int selection, scrollToY;

		switch (getCurrentMode()) {
		case MODE_PULL_UP_TO_REFRESH:
			originalLoadingLayout = getFooterLayout();
			listViewLoadingLayout = mFooterLoadingView;
			selection = mRefreshableView.getCount() - 1;
			scrollToY = getScrollY() - getHeaderHeight();
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			originalLoadingLayout = getHeaderLayout();
			listViewLoadingLayout = mHeaderLoadingView;
			selection = 0;
			scrollToY = getScrollY() + getHeaderHeight();
			break;
		}

		if (doScroll) {
			// We scroll slightly so that the ListView's header/footer is at the
			// same Y position as our normal header/footer
			setHeaderScroll(scrollToY);
		}

		// Hide our original Loading View
		originalLoadingLayout.setVisibility(View.INVISIBLE);

		// Show the ListView Loading View and set it to refresh
		listViewLoadingLayout.setVisibility(View.VISIBLE);
		listViewLoadingLayout.refreshing();

		if (doScroll) {
			// Make sure the ListView is scrolled to show the loading
			// header/footer
			mRefreshableView.setSelection(selection);

			// Smooth scroll as normal
			smoothScrollTo(0);
		}
	}

	@Override
	protected void resetHeader() {

		// If we're not showing the Refreshing view, or the list is empty, then
		// the header/footer views won't show so we use the
		// normal method
		final ListAdapter adapter = mRefreshableView.getAdapter();
		if (!getShowViewWhileRefreshing() || null == adapter || adapter.isEmpty()) {
			super.resetHeader();
			return;
		}

		LoadingLayout originalLoadingLayout;
		LoadingLayout listViewLoadingLayout;

		int scrollToHeight = getHeaderHeight();
		int selection;

		switch (getCurrentMode()) {
		case MODE_PULL_UP_TO_REFRESH:
			originalLoadingLayout = getFooterLayout();
			listViewLoadingLayout = mFooterLoadingView;

			selection = mRefreshableView.getCount() - 1;
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			originalLoadingLayout = getHeaderLayout();
			listViewLoadingLayout = mHeaderLoadingView;
			scrollToHeight *= -1;
			selection = 0;
			break;
		}

		// Set our Original View to Visible
		originalLoadingLayout.setVisibility(View.VISIBLE);

		// Scroll so our View is at the same Y as the ListView header/footer,
		// but only scroll if we've pulled to refresh
		if (getState() != MANUAL_REFRESHING) {
			mRefreshableView.setSelection(selection);
			setHeaderScroll(scrollToHeight);
		}

		// Hide the ListView Header/Footer
		listViewLoadingLayout.setVisibility(View.GONE);

		super.resetHeader();
	}

	@Override
	protected int getNumberInternalHeaderViews() {
		return null != mHeaderLoadingView ? 1 : 0;
	}

	@Override
	protected int getNumberInternalFooterViews() {
		return null != mFooterLoadingView ? 1 : 0;
	}
}
