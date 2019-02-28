package com.zele.maipuxiaoyuan.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.dcbd.activity.LoginActivity;
//import com.dcbd.activity.MainActivity;
//import com.dcbd.activity.SplashActivity;
//import com.dcbd.activity.topicscontent.activity.ImageGridActivity;
//import com.dcbd.base.BaseApp;
//import com.dcbd.common.CustomToast;
//import com.dcbd.controller.Callback;
//import com.dcbd.controller.CheckversionController;
//import com.dcbd.controller.GetUserInfoController;
//import com.dcbd.controller.CheckversionController.CheckversionCallback;
//import com.dcbd.service.UpdateAppService;

/**
 * 工具类
 */
public class Utils {

	public static File cameraFile;
	public static final int CAMERA_PHOTO = 10002;
	public static final String app_name = "mapedu";

	/**
	 * 获取屏幕高度和宽带
	 * 
	 * @param mContext
	 * @return int[高，宽]
	 */
	public static int[] getScreen(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		int screenWidth = dm.widthPixels;

		// 窗口高度
		int screenHeight = dm.heightPixels;
		int screen[] = { screenHeight, screenWidth };
		return screen;

	}
	/**
	 * Java将Unix时间戳转换成指定格式日期字符串
	 * @param timestampString 时间戳 如："1473048265";
	 * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
	 *
	 * @return 返回结果 如："2016-09-05 16:06:42";
	 */
	public static String TimeStamp2Date(String timestampString, String formats) {
		if (TextUtils.isEmpty(formats))
			formats = "yyyy-MM-dd HH:mm:ss";
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
		return date;
	}

	/**
	 * 是否已经的登录账号
	 * 
	 */
//	public static boolean islogin() {
//		if ("".equals(BaseApp.getInstance().dcbd_info.getLoginPassword())) {
//			return false;
//		} else {
//			return true;
//		}
//	}

	/**
	 * 
	 * @Title: dip2px @Description: TODO(dp转px) @param @param
	 *         context @param @param dpValue @param @return 设定文件 @return int
	 *         返回类型 @throws
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 设置View的高度
	 * 
	 * @param v
	 * @param height
	 */
	public static void setViewHeight2(View v, int height) {
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) v.getLayoutParams(); // 取控件mGrid当前的布局参数
		linearParams.height = height;// 当控件的高强制设成height
		v.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
	}

	/**
	 * 照相获取图片
	 * 
	 * @param mContext
	 * @return 图片路径
	 */
	public static void selectPicFromCamera(Context mContext) {
		if (!Utils.GetSDState()) {
//			CustomToast.showToast(mContext, "SD卡不存在，不能拍照");
			Toast.makeText(mContext, "SD卡不存在，不能拍照", 0).show();
			return;
		}
		cameraFile = new File(Utils.getFileAddress(1), System.currentTimeMillis() + ".png");
		try {
			deleteFile(cameraFile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			cameraFile.getParentFile().mkdirs();
			((Activity) mContext).startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
					.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)), CAMERA_PHOTO);
		}

	}

	/**
	 * 
	 * 将bitmap存为本地文件
	 * 
	 * @param bmp
	 * @param filename
	 * @return
	 * @return
	 */
	public static boolean saveBitmap2file(Bitmap bmp, String filename, Context mContext) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(Utils.getFileAddress(1) + filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return bmp.compress(format, quality, stream);
	}

	/**
	 * 返回SD卡存储路径
	 * @param state
	 *            1：iamge 2:video 3:voice 4:file 其他是cache
	 * @return
	 */
	public static String getFileAddress(int state) {

		String Address = "";
		if (GetSDState()) {
			Address = Environment.getExternalStorageDirectory().getPath() + "/" + app_name + "/";
		}
		// else {
		// Address = .getCacheDir().getAbsolutePath() + "/" +
		// app_name + "/";
		// }
		switch (state) {
		case 1:
			Address = Address + ".iamge/";
			break;
		case 2:
			Address = Address + ".video/";
			break;
		case 3:
			Address = Address + ".voice/";
			break;
		case 4:
			Address = Address + ".file/";
			break;
		default:
			Address = Address + "cache/";
			break;
		}
		File baseFile = new File(Address);
		if (!baseFile.exists()) {
			baseFile.mkdirs();
		}
		return Address;
	}

	// 返回是否有SD卡
	public static boolean GetSDState() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @Title: deleteFile @Description: TODO(删除文件) @param @param file
	 *         设定文件 @return void 返回类型 @throws
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				if(files!=null){
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
			}
			file.delete();
		} else {
		}
	}
	//删除文件
	public static void delFile(String fileName){
		File file = new File("/data/data/com.zele.maipuxiaoyuan/files/" + fileName);
		if(file.isFile() && file.exists()){
			file.delete();
        }
	}
	/**
	 * 获取当前版本号
	 *
	 * @param mContext
	 * @return
	 */
	public static int getVersionCode(Context mContext) {
		PackageManager manager = mContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
			return info.versionCode; // 版本号
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取当前版本名
	 *
	 * @param mContext
	 * @return
	 */
	public static String getVersionName(Context mContext) {
		PackageManager manager = mContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
			return info.versionName; // 版本名
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 应用名
	 *
	 * @param mContext
	 * @return
	 */
	public static String getAppName(Context mContext) {
		PackageManager manager = mContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
			return info.applicationInfo.loadLabel(manager).toString(); // 应用名
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
			return "";
		}
	}

	// Gps是否可用
	public static boolean isGpsEnable(Context mContext) {
		LocationManager locationManager = ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 正则方法验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][356789]\\d{9}";// "[1]"代表第1位为数字1，"[356789]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * 把file转换成String
	 *
	 * @param file
	 * @return
	 */
	public static String fileToBase64(File file) {

		Bitmap bm = getSmallBitmap(file.getAbsolutePath());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 720, 1280);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * base64转为bitmap
	 * 
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

//	@SuppressLint("NewApi")
//	public static void enableStrictMode() {
//		if (Utils.hasGingerbread()) {
//			StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll()
//					.penaltyLog();
//			StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();
//
//			if (Utils.hasHoneycomb()) {
//				threadPolicyBuilder.penaltyFlashScreen();
//				vmPolicyBuilder.setClassInstanceLimit(ImageGridActivity.class, 1);
//			}
//			StrictMode.setThreadPolicy(threadPolicyBuilder.build());
//			StrictMode.setVmPolicy(vmPolicyBuilder.build());
//		}
//
//	}

	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;

	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasKitKat() {
		return Build.VERSION.SDK_INT >= 19;
	}

	public static List<Size> getResolutionList(Camera camera) {
		Parameters parameters = camera.getParameters();
		List<Size> previewSizes = parameters.getSupportedPreviewSizes();
		return previewSizes;
	}

	public static class ResolutionComparator implements Comparator<Size> {

		@Override
		public int compare(Size lhs, Size rhs) {
			if (lhs.height != rhs.height)
				return lhs.height - rhs.height;
			else
				return lhs.width - rhs.width;
		}
	}

	/**
	 * 获取系统当前时间 add by gao yyyy-MM-ddHH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new Date());
		return datetime;
	}

}
