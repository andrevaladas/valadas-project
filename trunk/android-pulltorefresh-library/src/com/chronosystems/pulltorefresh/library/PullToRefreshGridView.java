package com.chronosystems.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.GridView;

import com.chronosystems.pulltorefresh.library.internal.EmptyViewMethodAccessor;

public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {

	class InternalGridView extends GridView implements EmptyViewMethodAccessor {

		public InternalGridView(final Context context, final AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(final View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(final View emptyView) {
			super.setEmptyView(emptyView);
		}

		@Override
		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshGridView(final Context context) {
		super(context);
	}

	public PullToRefreshGridView(final Context context, final int mode) {
		super(context, mode);
	}

	public PullToRefreshGridView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected final GridView createRefreshableView(final Context context, final AttributeSet attrs) {
		final GridView gv = new InternalGridView(context, attrs);

		// Use Generated ID (from res/values/ids.xml)
		gv.setId(R.id.gridview);
		return gv;
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalGridView) getRefreshableView()).getContextMenuInfo();
	}
}
