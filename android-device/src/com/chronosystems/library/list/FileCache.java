package com.chronosystems.library.list;

import java.io.File;

import android.content.Context;

public class FileCache {

	private File cacheDir;

	public FileCache(final Context context){
		//Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
		} else {
			cacheDir=context.getCacheDir();
		}
		if(!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public File getFile(final Long id){
		//I identify images by hashcode. Not a perfect solution, good for the demo.
		final String filename=String.valueOf(id.hashCode());
		//Another possible solution (thanks to grantland)
		//String filename = URLEncoder.encode(url);
		final File f = new File(cacheDir, filename);
		return f;

	}

	public void clear(){
		final File[] files=cacheDir.listFiles();
		if(files==null) {
			return;
		}
		for(final File f:files) {
			f.delete();
		}
	}

}