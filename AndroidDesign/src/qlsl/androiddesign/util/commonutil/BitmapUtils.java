package qlsl.androiddesign.util.commonutil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import qlsl.androiddesign.entity.commonentity.BitmapEntity;

public class BitmapUtils {

	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片 www.2cto.com
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

	/**
	 * 旋转图片<br/>
	 * bitmap过大会导致内存溢出<br/>
	 */
	public static Bitmap rotationBitmap(Bitmap bm, int degree) {
		Matrix m = new Matrix();
		m.setRotate(degree, (float) (bm.getWidth() >> 1), (float) (bm.getHeight() >> 1));
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
		return newbm;
	}

	/**
	 * 旋转并自动缩放图片<br/>
	 * scale为新尺寸除旧尺寸<br/>
	 */
	public static Bitmap rotationBitmap(Bitmap bm, int degree, float scale) {
		Matrix m = new Matrix();
		int bitmapWidth = bm.getWidth();
		int bitmapHeight = bm.getHeight();
		m.postRotate(degree, (float) (bitmapWidth >> 1), (float) (bitmapHeight >> 1));
		m.postScale(scale, scale);
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, bitmapWidth, bitmapHeight, m, true);
		return newbm;
	}

	public static Bitmap scaleImg(Bitmap bm, float scale) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(

				drawable.getIntrinsicWidth(),

				drawable.getIntrinsicHeight(),

				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

						: Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

		drawable.draw(canvas);

		return bitmap;

	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 画一个圆角图
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 画一个缺省头像圆角图
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedTextBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 转换图片成圆形
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	public static Bitmap getBitmapFromView(View view) {
		if (view == null) {
			return null;
		}
		Bitmap screenshot;
		screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
		Canvas c = new Canvas(screenshot);
		c.translate(-view.getScrollX(), -view.getScrollY());
		view.draw(c);
		return screenshot;
	}

	// View转换为Bitmap
	public void getDrawingCache(final ImageView sourceImageView, final ImageView destImageView, final int resId) {

		new Handler().postDelayed(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				// 开启bitmap缓存
				sourceImageView.setDrawingCacheEnabled(true);
				// 获取bitmap缓存
				Bitmap mBitmap = sourceImageView.getDrawingCache();
				// 显示 bitmap
				destImageView.setImageBitmap(mBitmap);

				// Bitmap mBitmap = sourceImageView.getDrawingCache();
				// Drawable drawable = (Drawable) new BitmapDrawable(mBitmap);
				// destImageView.setImageDrawable(drawable);

				new Handler().postDelayed(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						// 不再显示bitmap缓存
						// destImageView.setImageBitmap(null);
						destImageView.setImageResource(resId);

						// 使用这句话而不是用上一句话是错误的，空指针调用
						// destImageView.setBackgroundDrawable(null);

						// 关闭bitmap缓存
						sourceImageView.setDrawingCacheEnabled(false);
						// 释放bitmap缓存资源
						sourceImageView.destroyDrawingCache();
					}
				}, 10);
			}
		}, 10);
	}

	// 图片灰化处理
	public Bitmap getGrayBitmap(Context context, int resId) {
		Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
		Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(mGrayBitmap);
		Paint mPaint = new Paint();

		// 创建颜色变换矩阵
		ColorMatrix mColorMatrix = new ColorMatrix();
		// 设置灰度影响范围
		mColorMatrix.setSaturation(0);
		// 创建颜色过滤矩阵
		ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
		// 设置画笔的颜色过滤矩阵
		mPaint.setColorFilter(mColorFilter);
		// 使用处理后的画笔绘制图像
		mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

		return mGrayBitmap;
	}

	// 提取图像Alpha位图
	public Bitmap getAlphaBitmap(Context context, int resId) {
		BitmapDrawable mBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap mBitmap = mBitmapDrawable.getBitmap();

		// BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
		// 注意这两个方法的区别
		// Bitmap mAlphaBitmap =
		// Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(),
		// mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
		Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);

		Canvas mCanvas = new Canvas(mAlphaBitmap);
		Paint mPaint = new Paint();

		mPaint.setColor(Color.BLUE);
		// 从原位图中提取只包含alpha的位图
		Bitmap alphaBitmap = mBitmap.extractAlpha();
		// 在画布上（mAlphaBitmap）绘制alpha位图
		mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

		return mAlphaBitmap;
	}

	public Bitmap getScaleBitmap(Context context, int resId) {
		BitmapDrawable mBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap mBitmap = mBitmapDrawable.getBitmap();
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(0.75f, 0.75f);
		Bitmap mScaleBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

		return mScaleBitmap;
	}

	public Bitmap getRotatedBitmap(Context context, int resId) {
		BitmapDrawable mBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap mBitmap = mBitmapDrawable.getBitmap();
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preRotate(45);
		Bitmap mRotateBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

		return mRotateBitmap;
	}

	public Bitmap getScrewBitmap(Context context, int resId) {
		BitmapDrawable mBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap mBitmap = mBitmapDrawable.getBitmap();
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preSkew(1.0f, 0.15f);
		Bitmap mScrewBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

		return mScrewBitmap;
	}

	@SuppressWarnings("unused")
	private Bitmap getReflectedBitmap(Context context, int resId) {
		BitmapDrawable mBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap mBitmap = mBitmapDrawable.getBitmap();
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		// 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
		matrix.preScale(1, -1);

		// 创建反转后的图片Bitmap对象，图片高是原图的一半。
		// Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, height/2,
		// width, height/2, matrix, false);
		// 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
		// 注意两种createBitmap的不同
		// Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height*3/2,
		// Config.ARGB_8888);

		Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
		Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height * 2, Config.ARGB_8888);

		// 把新建的位图作为画板
		Canvas mCanvas = new Canvas(mReflectedBitmap);
		// 绘制图片
		mCanvas.drawBitmap(mBitmap, 0, 0, null);
		mCanvas.drawBitmap(mInverseBitmap, 0, height, null);

		// 添加倒影的渐变效果
		Paint mPaint = new Paint();
		Shader mShader = new LinearGradient(0, height, 0, mReflectedBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		mPaint.setShader(mShader);
		// 设置叠加模式
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// 绘制遮罩效果
		mCanvas.drawRect(0, height, width, mReflectedBitmap.getHeight(), mPaint);

		return mReflectedBitmap;
	}

	public Bitmap getCropBitmap(Context context, int resId) {
		BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap bitmap = bd.getBitmap();
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap bm = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bm);
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		canvas.drawBitmap(bitmap, 0, 0, mPaint);

		int deltX = 76;
		int deltY = 98;
		DashPathEffect dashStyle = new DashPathEffect(new float[] { 10, 5, 5, 5 }, 2);// 创建虚线边框样式
		RectF faceRect = new RectF(0, 0, 88, 106);
		float[] faceCornerii = new float[] { 30, 30, 30, 30, 75, 75, 75, 75 };
		mPaint = new Paint();// 创建画笔
		mPaint.setColor(0xFF6F8DD5);
		mPaint.setStrokeWidth(6);
		mPaint.setPathEffect(dashStyle);
		Path clip = new Path();// 创建路径
		clip.reset();
		clip.addRoundRect(faceRect, faceCornerii, Direction.CW);// 添加圆角矩形路径
		canvas.save();// 保存画布
		canvas.translate(deltX, deltY);
		canvas.clipPath(clip, Region.Op.DIFFERENCE);
		canvas.drawColor(0xDF222222);
		canvas.drawPath(clip, mPaint);// 绘制路径
		canvas.restore();

		Rect srcRect = new Rect(0, 0, 88, 106);
		srcRect.offset(deltX, deltY);
		PaintFlagsDrawFilter dfd = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.FILTER_BITMAP_FLAG);
		canvas.setDrawFilter(dfd);
		canvas.clipPath(clip);// 使用路径剪切画布
		canvas.drawBitmap(bitmap, srcRect, faceRect, mPaint);

		return bitmap;
	}

	public static byte[] getImageData(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection httpURLconnection = (HttpURLConnection) url.openConnection();
		httpURLconnection.setRequestMethod("GET");
		httpURLconnection.setReadTimeout(6 * 1000);
		InputStream in = null;
		if (httpURLconnection.getResponseCode() == 200) {
			in = httpURLconnection.getInputStream();
			byte[] result = readStream(in);
			in.close();
			return result;

		}
		return null;
	}

	public static byte[] readStream(InputStream in) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		in.close();
		return outputStream.toByteArray();
	}

	/**
	 * 文字转为图片<br/>
	 */
	public static Bitmap TextToBitmap(String text) {
		String lastword = text.subSequence((text.length() - 1), text.length()).toString();
		Bitmap bitmap = Bitmap.createBitmap(120, 120, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		canvas.drawColor(Color.parseColor("#B2EBF2"));
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
		paint.setTextSize(60f);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setColor(Color.parseColor("#00BCD4"));
		float[] widths = new float[1];
		paint.getTextWidths(lastword, widths);
		float fontWidth = (float) Math.ceil(widths[0]);
		FontMetrics fm = paint.getFontMetrics();
		float fontHeight = (float) (Math.ceil(fm.bottom - fm.top));
		canvas.drawText(lastword, (120f - fontWidth) / 2.0f, ((120f - fontHeight) / 2.0f) - fm.ascent, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return getRoundedTextBitmap(bitmap, bitmap.getWidth());
	}

	/**
	 * 获取混合bitmap<br/>
	 */
	public static Bitmap getCombineBitmaps(List<BitmapEntity> mEntityList, Bitmap... bitmaps) {
		Bitmap newBitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
		for (int i = 0; i < mEntityList.size(); i++) {
			newBitmap = mixtureBitmap(newBitmap, bitmaps[i],
					new PointF(mEntityList.get(i).getX(), mEntityList.get(i).getY()));
		}
		return newBitmap;
	}

	/**
	 * 混合<br/>
	 */
	public static Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
		if (first == null || second == null || fromPoint == null) {
			return null;
		}
		Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(), first.getHeight(), Config.ARGB_8888);
		Canvas cv = new Canvas(newBitmap);
		cv.drawColor(Color.parseColor("#DBDFE0"));
		cv.drawBitmap(first, 0, 0, null);
		cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();
		return newBitmap;
	}

}
