package com.chronosystems.library.list;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.chronosystems.entity.Device;
import com.chronosystems.view.R;

public class ImageLoader {

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private final Map<ImageView, Long> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, Long>());
	ExecutorService executorService;

	public ImageLoader(final Context context){
		fileCache = new FileCache(context);
		executorService=Executors.newFixedThreadPool(5);
	}

	final int stub_id = R.drawable.no_image;
	public void DisplayImage(final Device device, final ImageView imageView) {
		imageViews.put(imageView, device.getId());
		final Bitmap bitmap = memoryCache.get(device.getId().toString());
		if(bitmap!=null) {
			imageView.setImageBitmap(bitmap);
		} else {
			queuePhoto(device, imageView);
			imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(final Device device, final ImageView imageView) {
		final PhotoToLoad p=new PhotoToLoad(device, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(final Device device) {
		final File f=fileCache.getFile(device.getId());

		//from SD cache
		final Bitmap b = decodeFile(f);
		if(b!=null) {
			return b;
		}

		//from web
		try {
			final InputStream is = new ByteArrayInputStream(device.getImage());
			final OutputStream os = new FileOutputStream(f);
			copyStream(is, os);
			os.close();
			final Bitmap bitmap = decodeFile(f);
			return bitmap;
		} catch (final Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	private void copyStream(final InputStream is, final OutputStream os) {
		final int buffer_size=1024;
		try {
			final byte[] bytes=new byte[buffer_size];
			for(;;) {
				final int count=is.read(bytes, 0, buffer_size);
				if(count==-1) {
					break;
				}
				os.write(bytes, 0, count);
			}
		} catch(final Exception ex){}
	}


	//decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(final File f){
		try {
			//decode image size
			final BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f),null,o);

			//Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE=70;
			int width_tmp=o.outWidth, height_tmp=o.outHeight;
			int scale=1;
			while(true){
				if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE) {
					break;
				}
				width_tmp/=2;
				height_tmp/=2;
				scale*=2;
			}

			//decode with inSampleSize
			final BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (final FileNotFoundException e) {}
		return null;
	}

	//Task for the queue
	private class PhotoToLoad {
		public Device device;
		public ImageView imageView;
		public PhotoToLoad(final Device d, final ImageView i){
			device=d;
			imageView=i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;
		PhotosLoader(final PhotoToLoad photoToLoad){
			this.photoToLoad=photoToLoad;
		}

		public void run() {
			if(imageViewReused(photoToLoad)) {
				return;
			}
			final Bitmap bmp=getBitmap(photoToLoad.device);
			memoryCache.put(photoToLoad.device.getId().toString(), bmp);
			final BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
			final Activity a=(Activity)photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(final PhotoToLoad photoToLoad){
		final Long tag = imageViews.get(photoToLoad.imageView);
		if(tag==null || !tag.equals(photoToLoad.device.getId())) {
			return true;
		}
		return false;
	}

	//Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;
		public BitmapDisplayer(final Bitmap b, final PhotoToLoad p){
			bitmap=b;
			photoToLoad=p;
		}

		public void run() {
			if(imageViewReused(photoToLoad)) {
				return;
			}
			if(bitmap!=null) {
				photoToLoad.imageView.setImageBitmap(bitmap);
			} else {
				photoToLoad.imageView.setImageResource(stub_id);
			}
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}
}
