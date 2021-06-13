package qlsl.androiddesign.transformation;

import com.squareup.picasso.Transformation;

import android.graphics.Bitmap;
import qlsl.androiddesign.util.commonutil.BitmapUtils;

public class RoundTransformation implements Transformation {

	public Bitmap transform(Bitmap source) {
		Bitmap result = BitmapUtils.getRoundedCornerBitmap(source, 10);
		if (result != source) {
			source.recycle();
		}
		return result;
	}

	public String key() {
		return "round";
	}
}
