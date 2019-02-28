/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zele.maipuxiaoyuan.common.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	private static final String TAG = "CommonUtils";

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	static String getString(Context context, int resId) {
		return context.getResources().getString(resId);
	}

	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

	// 返回是否有SD卡
	public static boolean GetSDState() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 应用名
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.applicationInfo.loadLabel(manager).toString(); // 应用名
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * dip转化成px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转化成dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * px转化成sp
	 */
	public static int px2sp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * sp转化成px
	 */
	public static int sp2px(Context context, float spValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (spValue * scale + 0.5f);
	}

	/**
	 * 获取指定HTML标签的指定属性的值
	 *
	 * @param source  要匹配的源文本
	 * @param element 标签名称
	 * @param attr    标签的属性名称
	 * @return 属性值列表
	 */
	public List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1);
			result.add(r);
		}
		return result;
	}
	public static final String REGEX_ID_CARD_15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	public static final String REGEX_ID_CARD_18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
	public static final String REGEX_TELREGEX = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
	public static final String REGEX_HANZI = "([\\u4e00-\\u9fa5]{2,15})";
	//public static final String REGEX_HANZI = "([\\u4E00-\\u9FBF]{1,10}·?[\\u4E00-\\u9FBF]{1,10}){1,5}";

	/**
	 * 判断当前系统时间是否在指定时间的范围内
	 *
	 * @param beginHour
	 *            开始小时，例如22
	 * @param beginMin
	 *            开始小时的分钟数，例如30
	 * @param endHour
	 *            结束小时，例如 8
	 * @param endMin
	 *            结束小时的分钟数，例如0
	 * @return true表示在范围内，否则false
	 */
	public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
		boolean result = false;
		final long aDayInMillis = 1000 * 60 * 60 * 24;
		final long currentTimeMillis = System.currentTimeMillis();

		Time now = new Time();
		now.set(currentTimeMillis);

		Time startTime = new Time();
		startTime.set(currentTimeMillis);
		startTime.hour = beginHour;
		startTime.minute = beginMin;

		Time endTime = new Time();
		endTime.set(currentTimeMillis);
		endTime.hour = endHour;
		endTime.minute = endMin;

		if (!startTime.before(endTime)) {
			// 跨天的特殊情况（比如22:00-8:00）
			startTime.set(startTime.toMillis(true) - aDayInMillis);
			result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
			Time startTimeInThisDay = new Time();
			startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
			if (!now.before(startTimeInThisDay)) {
				result = true;
			}
		} else {
			// 普通情况(比如 8:00 - 14:00)
			result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
		}
		return result;
	}


	public static String getTime() {
		return new SimpleDateFormat("今天 HH:mm", Locale.CHINA).format(new Date());
	}

	public static boolean isPic(String fileName) {
		if(fileName.contains(".jpg")){
			return true;
		}
		if(fileName.contains(".bmp")){
			return true;
		}
		if(fileName.contains(".jpeg")){
			return true;
		}
		if(fileName.contains(".png")){
			return true;
		}
		return false;
	}

	/**
	 * 获取状态栏高度
	 * @return
	 */
	public static int getStatusBar(Context context){
		/**
		 * 获取状态栏高度
		 * */
		int statusBarHeight1 = -1;
		//获取status_bar_height资源的ID
		int resourceId = context.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			//根据资源ID获取响应的尺寸值
			statusBarHeight1 = context.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
		}
		return statusBarHeight1;
	}

	/**
	 * 将毫秒转化成固定格式的时间
	 * 时间格式: yyyy-MM-dd HH:mm:ss
	 *
	 * @param millisecond
	 * @return
	 */
	public static String getDateTimeFromMillisecond(Long millisecond, String formatString){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
		Date date = new Date(millisecond);
		String dateStr = simpleDateFormat.format(date);
		return dateStr;
	}

	/**
	 * 密码校验：6-18位，数字和密码组合
	 * @param pwd
	 * @return
	 */
	public static boolean checkoutPassword(String pwd) {
		Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,18}$");
		Matcher matcher = pattern.matcher(pwd);
		return matcher.matches();
	}

	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay()
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
}
