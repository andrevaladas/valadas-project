package com.chronosystems.library.list;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	// All static variables
	public static final String URL = "http://api.androidhive.info/music/music.xml";

	public static void CopyStream(final InputStream is, final OutputStream os) {
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
}