package com.chronosystems.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.chronosystems.R;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Relationship;
import com.chronosystems.library.list.LazyAdapter;
import com.chronosystems.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.chronosystems.pulltorefresh.library.PullToRefreshListView;
import com.chronosystems.service.local.AsyncService;
import com.chronosystems.service.local.AsyncSimpleTask;
import com.chronosystems.service.local.UserFunctions;
import com.chronosystems.service.remote.DeviceService;
import com.chronosystems.service.remote.RelationshipService;

public class ListViewActivity extends Activity implements OnItemClickListener, OnRefreshListener {
	private LazyAdapter adapter;
	private PullToRefreshListView listView;

	private class ListRefreshSearch extends AsyncSimpleTask {
		public ListRefreshSearch(final Context currentContext, final Class<?> backOnError) {
			super(currentContext, backOnError);
		}
		public ListRefreshSearch(final Context currentContext) {
			super(currentContext);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			listView.setRefreshing();
		}

		@Override
		protected Entity doInBackground(final String... args) {
			// Find devices on server
			final Entity filter = new Entity();
			filter.addDevice(UserFunctions.getCurrentUser(getApplicationContext()));
			return DeviceService.search(filter);
		};
		@Override
		protected void onPostExecute(final Entity result) {
			super.onPostExecute(result);
			if (result != null && !result.hasErrors()) {
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						// verify erros
						adapter = new LazyAdapter(ListViewActivity.this, result);
						adapter.notifyDataSetChanged();

						final ListView actualListView = listView.getRefreshableView();
						actualListView.setAdapter(adapter);
					}});
			}
			listView.onRefreshComplete();
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_devices);

		listView = (PullToRefreshListView)findViewById(R.id.pull_refresh_listview);
		listView.setOnRefreshListener(this);
		listView.getRefreshableView().setOnItemClickListener(this);

		new ListRefreshSearch(ListViewActivity.this, TabDashboardActivity.class).execute();
	}

	public void onRefresh() {
		listView.setLastUpdatedLabel(DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL));
		new ListRefreshSearch(ListViewActivity.this, TabDashboardActivity.class).execute();
	}

	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		if(parent.getAdapter() instanceof HeaderViewListAdapter) {
			new AsyncService<Entity>(ListViewActivity.this, "Following... please wait") {
				@Override
				protected Entity doInBackground(final String... args) {
					final HeaderViewListAdapter listAdapter = (HeaderViewListAdapter) parent.getAdapter();
					final LazyAdapter adapter = (LazyAdapter)listAdapter.getWrappedAdapter();
					final Device rowDevice = (Device) adapter.getItem(position-1);

					// register on server
					final Relationship follow = new Relationship();
					final Device currentUser = UserFunctions.getCurrentUser(getApplicationContext());
					final Device follower = new Device();
					follower.setId(currentUser.getId());
					follow.setFollower(follower);

					final Device following = new Device();
					following.setId(rowDevice.getId());
					follow.setFollowing(following);
					return RelationshipService.follow(follow);
				};

				@Override
				protected void onPostExecute(final Entity result) {
					super.onPostExecute(result);
					if (result != null && !result.hasErrors()) {
						// updating UI from Background Thread
						runOnUiThread(new Runnable() {
							public void run() {
								// remove row from list
								adapter.removeDevice(position-1);
								adapter.notifyDataSetInvalidated();
							}
						});
					}
				}
			}.execute();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.search_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			new ListRefreshSearch(ListViewActivity.this, TabDashboardActivity.class).execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}