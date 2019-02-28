package com.zele.maipuxiaoyuan.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetUtils {

	public static final int NETWORK_WIFI = 1;    // wifi network
	public static final int NETWORK_4G = 4;    // "4G" networks
	public static final int NETWORK_3G = 3;    // "3G" networks
	public static final int NETWORK_2G = 2;    // "2G" networks
	public static final int NETWORK_UNKNOWN = 5;    // unknown network
	public static final int NETWORK_NO = -1;   // no network

	private static final int NETWORK_TYPE_GSM = 16;
	private static final int NETWORK_TYPE_TD_SCDMA = 17;
	private static final int NETWORK_TYPE_IWLAN = 18;

	// 判断网络连接状态,是否有网络
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
 
	// 判断wifi状态
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 判断移动网络
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 获取连接类型
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * 判断网络是否是4G
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
	 *
	 * @param context 上下文
	 * @return {@code true}: 是<br>{@code false}: 不是
	 */
	public static boolean is4G(Context context) {
		NetworkInfo info = getActiveNetworkInfo(context.getApplicationContext());
		return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
	}

	/**
	 * 获取活动网络信息
	 *
	 * @param context 上下文
	 * @return NetworkInfo
	 */
	public static NetworkInfo getActiveNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/**
	 * 获取当前的网络类型(WIFI,2G,3G,4G)
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
	 *
	 * @param context 上下文
	 * @return 网络类型
	 * <ul>
	 * <li>{@link #NETWORK_WIFI   } = 1;</li>
	 * <li>{@link #NETWORK_4G     } = 4;</li>
	 * <li>{@link #NETWORK_3G     } = 3;</li>
	 * <li>{@link #NETWORK_2G     } = 2;</li>
	 * <li>{@link #NETWORK_UNKNOWN} = 5;</li>
	 * <li>{@link #NETWORK_NO     } = -1;</li>
	 * </ul>
	 */
	public static int getNetWorkType(Context context) {
		int netType = NETWORK_NO;
		NetworkInfo info = getActiveNetworkInfo(context.getApplicationContext());
		if (info != null && info.isAvailable()) {

			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				netType = NETWORK_WIFI;
			} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				switch (info.getSubtype()) {

					case NETWORK_TYPE_GSM:
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						netType = NETWORK_2G;
						break;

					case NETWORK_TYPE_TD_SCDMA:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						netType = NETWORK_3G;
						break;

					case NETWORK_TYPE_IWLAN:
					case TelephonyManager.NETWORK_TYPE_LTE:
						netType = NETWORK_4G;
						break;
					default:

						String subtypeName = info.getSubtypeName();
						if (subtypeName.equalsIgnoreCase("TD-SCDMA")
								|| subtypeName.equalsIgnoreCase("WCDMA")
								|| subtypeName.equalsIgnoreCase("CDMA2000")) {
							netType = NETWORK_3G;
						} else {
							netType = NETWORK_UNKNOWN;
						}
						break;
				}
			} else {
				netType = NETWORK_UNKNOWN;
			}
		}
		return netType;
	}

	/**
	 * 获取当前的网络类型(WIFI,2G,3G,4G)
	 * <p>依赖上面的方法</p>
	 *
	 * @param context 上下文
	 * @return 网络类型名称
	 * <ul>
	 * <li>NETWORK_WIFI   </li>
	 * <li>NETWORK_4G     </li>
	 * <li>NETWORK_3G     </li>
	 * <li>NETWORK_2G     </li>
	 * <li>NETWORK_UNKNOWN</li>
	 * <li>NETWORK_NO     </li>
	 * </ul>
	 */
	public static String getNetWorkTypeName(Context context) {
		switch (getNetWorkType(context.getApplicationContext())) {
			case NETWORK_WIFI:
				return "NETWORK_WIFI";
			case NETWORK_4G:
				return "NETWORK_4G";
			case NETWORK_3G:
				return "NETWORK_3G";
			case NETWORK_2G:
				return "NETWORK_2G";
			case NETWORK_NO:
				return "NETWORK_NO";
			default:
				return "NETWORK_UNKNOWN";
		}
	}
}
