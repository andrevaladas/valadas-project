package com.chronosystems.library.list;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.chronosystems.view.R;

public class ImageLoader {

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private final Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;

	public ImageLoader(final Context context){
		fileCache = new FileCache(context);
		executorService=Executors.newFixedThreadPool(5);
	}

	final int stub_id = R.drawable.no_image;
	public void DisplayImage(final String url, final ImageView imageView) {
		imageViews.put(imageView, url);
		final Bitmap bitmap = memoryCache.get(url);
		if(bitmap!=null) {
			imageView.setImageBitmap(bitmap);
		} else {
			queuePhoto(url, imageView);
			imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(final String url, final ImageView imageView) {
		final PhotoToLoad p=new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(final String url) {
		final File f=fileCache.getFile(url);

		//from SD cache
		final Bitmap b = decodeFile(f);
		if(b!=null) {
			return b;
		}

		//from web
		try {
			Bitmap bitmap=null;
			final URL imageUrl = new URL(url);
			final HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			final InputStream is=conn.getInputStream();
			final OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (final Exception ex){
			ex.printStackTrace();
			return null;
		}
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
		public String url;
		public ImageView imageView;
		public PhotoToLoad(final String u, final ImageView i){
			url=u;
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
			final Bitmap bmp=getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if(imageViewReused(photoToLoad)) {
				return;
			}
			final BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
			final Activity a=(Activity)photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(final PhotoToLoad photoToLoad){
		final String tag=imageViews.get(photoToLoad.imageView);
		if(tag==null || !tag.equals(photoToLoad.url)) {
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
