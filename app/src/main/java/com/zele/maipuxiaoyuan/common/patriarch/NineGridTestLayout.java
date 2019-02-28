package com.zele.maipuxiaoyuan.common.patriarch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zele.maipuxiaoyuan.R;

import java.util.ArrayList;
import java.util.List;

public class NineGridTestLayout extends NineGridLayout {

	protected static final int MAX_W_H_RATIO = 3;
	public NineGridTestLayout(Context context) {
		super(context);
	}

	public NineGridTestLayout(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.mContext = context;
	}

	@Override
	protected boolean displayOneImage(final RatioImageView imageView,
                                      String url, final int parentWidth) {

		ImageLoaderUtil.displayImage(mContext, imageView, url,
				ImageLoaderUtil.getPhotoImageOption(),
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap bitmap) {
						int w = bitmap.getWidth();
						int h = bitmap.getHeight();

						int newW;
						int newH;
						if (h > w * MAX_W_H_RATIO) {// h:w = 5:3
							newW = parentWidth / 2;
							newH = newW * 5 / 3;
						} else if (h < w) {// h:w = 2:3
							newW = parentWidth * 2 / 3;
							newH = newW * 2 / 3;
						} else {// newH:h = newW :w
							newW = parentWidth / 2;
							newH = h * newW / w;
						}
						setOneImageLayoutParams(imageView, newW, newH);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {

					}
				});
		return false;
	}

	@Override
	protected void displayImage(RatioImageView imageView, String url) {
		ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView,
				ImageLoaderUtil.getPhotoImageOption());
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 获取推荐的宽高和计算模式
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

		// int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		// int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		// //获取孩子的个数
		// int childCount = getChildCount();

		// // if (heightMode == MeasureSpec.UNSPECIFIED) { //出现在listView的item中
		// // if (childCount == 1) {
		// // heightSize = widthSize;
		// // }
		// // if (childCount == 2) {
		// // heightSize = widthSize / 2;
		// // }
		// // if (childCount == 5 || childCount == 6) {
		// // heightSize = widthSize * 2 / 3;
		// // }
		// //
		// // }
		// //
		// // //保存自身的大小
		// setMeasuredDimension(widthSize,heightSize);

		int measureWidth = measureWidth(widthMeasureSpec);
		int measureHeight = measureHeight(heightMeasureSpec);

		// 计算自定义的ViewGroup中所有子控件的大小
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		// 设置自定义的控件MyViewGroup的大小
		setMeasuredDimension(measureWidth, measureHeight);
	}

	private int measureWidth(int pWidthMeasureSpec) {
		int result = 0;
		int widthMode = View.MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
		int widthSize = View.MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸

		switch (widthMode) {
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.EXACTLY:
			result = widthSize;
			break;
		}
		return result;
	}

	private int measureHeight(int pHeightMeasureSpec) {
		int result = 0;

		int heightMode = View.MeasureSpec.getMode(pHeightMeasureSpec);
		int heightSize = View.MeasureSpec.getSize(pHeightMeasureSpec);

		switch (heightMode) {
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.EXACTLY:
			result = heightSize;
			break;
		}
		return result;
	}

	@Override
	protected void onClickImage(int i, String url, List<String> urlList) {
		// Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();

		// 使用ImageLoader加载网络图片
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.showImageOnLoading(R.mipmap.ic_launcher) // 加载中显示的默认图片
				.showImageOnFail(R.mipmap.ic_launcher) // 设置加载失败的默认图片
				.cacheInMemory(true) // 内存缓存
				.cacheOnDisk(true) // sdcard缓存
				.bitmapConfig(Config.RGB_565)// 设置最低配置
				.build();//
		// 点击回帖九宫格，查看大图
		ArrayList<String> url1 = new ArrayList<String>(urlList);
		// imageBrower(mContext.hashCode(), url1);
		imageBrower(i, url1);
	}

	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {

		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		// 开启新的任务栈
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
}
