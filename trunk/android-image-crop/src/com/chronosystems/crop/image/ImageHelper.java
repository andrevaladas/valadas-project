/**
 * 
 */
package com.chronosystems.crop.image;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @author Andre Valadas
 *
 */
public class ImageHelper {

	public static Bitmap getRoundedBitmap(final byte[] image) {
		try {
			final ByteArrayInputStream bais = new ByteArrayInputStream(image);
			final BufferedInputStream bis = new BufferedInputStream(bais);
			final Bitmap bitmap = BitmapFactory.decodeStream(bis);
			return getRoundedBitmap(bitmap);
		} catch (final Exception e) {}
		return null;
	}
	public static Bitmap getRoundedBitmap(final Bitmap bitmap) {
		return getRoundedBitmap(bitmap, 10); //default
	}
	public static Bitmap getRoundedBitmap(final Bitmap bitmap, final int pixels) {
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap getResizedBitmap(final Bitmap bm, final int newHeight, final int newWidth) {
		final int width = bm.getWidth();
		final int height = bm.getHeight();
		final float scaleWidth = ((float) newWidth) / width;
		final float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		final Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		final Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

	public static Bitmap decodeToLowResolution(final byte [] image) {
		return decodeToLowResolution(image, 80, 80);
	}
	public static Bitmap decodeToLowResolution(final byte [] image, final int width, final int height) {
		try {
			//Decode image size
			final BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new ByteArrayInputStream(image), null, o);

			//The new size we want to scale to
			final int REQUIRED_SIZE_WIDTH=(int)(width*0.7);
			final int REQUIRED_SIZE_HEIGHT=(int)(height*0.7);

			//Find the correct scale value. It should be the power of 2.
			int width_tmp=o.outWidth, height_tmp=o.outHeight;
			int scale=1;
			while(true){
				if(width_tmp/2<REQUIRED_SIZE_WIDTH || height_tmp/2<REQUIRED_SIZE_HEIGHT) {
					break;
				}
				width_tmp/=2;
				height_tmp/=2;
				scale*=2;
			}

			//Decode with inSampleSize
			final BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			return BitmapFactory.decodeStream(new ByteArrayInputStream(image), null, o2);
		} catch (final OutOfMemoryError e) {
		}
		return null;
	}
}
