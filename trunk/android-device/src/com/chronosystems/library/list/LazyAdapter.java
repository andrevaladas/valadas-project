package com.chronosystems.library.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chronosystems.R;
import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

public class LazyAdapter extends BaseAdapter {

	private final Entity entity;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader;

	public LazyAdapter(final Activity activity, final Entity data) {
		this.entity = data;
		this.imageLoader = new ImageLoader(activity.getApplicationContext());
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return entity.getDevices().size();
	}

	public Object getItem(final int position) {
		if(getCount() >= position) {
			return entity.getDevices().get(position);
		}
		return null;
	}

	public Entity getEntity() {
		return entity;
	}
	public void removeDevice(final int position) {
		if(getCount() >= position) {
			entity.getDevices().remove(position);
		}
	}

	public long getItemId(final int position) {
		return position;
	}

	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View vi = convertView;
		if(convertView==null) {
			vi = inflater.inflate(R.layout.list_devices_row, null);
		}

		final TextView title = (TextView)vi.findViewById(R.id.title); // title
		final TextView description = (TextView)vi.findViewById(R.id.description); // description
		//final TextView address = (TextView)vi.findViewById(R.id.address); // address
		//final TextView followingStatus = (TextView)vi.findViewById(R.id.following); // following status
		final ImageView image = (ImageView)vi.findViewById(R.id.list_image); // thumb image

		// device and last location
		final Device device = (Device) getItem(position);

		// Setting all values in listview
		title.setText(device.getName());
		description.setText(device.getEmail());
		//address.setText();
		//followingStatus.setText();
		imageLoader.DisplayImage(device, image);
		return vi;
	}
}