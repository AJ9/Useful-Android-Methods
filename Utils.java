package com.rolleragency.esign.utils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class Utils {

	public static Bitmap getScaledBitmap(Intent imageReturnedIntent,
			Context context) {
		Uri selectedImage = imageReturnedIntent.getData();
		InputStream imageStream = null;
		Bitmap bitmapImage = null;
		try {
			imageStream = context.getContentResolver().openInputStream(
					selectedImage);
			BufferedInputStream newBuffer = new BufferedInputStream(imageStream);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(newBuffer, null, options);

			options.inSampleSize = calculateInSampleSize(options, 60, 60);
			options.inJustDecodeBounds = false;

			try {
				newBuffer.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}

			bitmapImage = BitmapFactory.decodeStream(newBuffer, null, options);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bitmapImage == null) {
			try {
				imageStream = context.getContentResolver().openInputStream(
						selectedImage);
				// bitmapImage = BitmapFactory.decodeStream(imageStream);
				System.out.println("Image is small so use inbuilt");
				bitmapImage = BitmapFactory.decodeStream(imageStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return bitmapImage;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}
