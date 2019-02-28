package com.zele.maipuxiaoyuan.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 记录当前可见的Activity
 */
public class MyActivityManager {

	private static MyActivityManager sInstance;

	private WeakReference<Activity> sCurrentActivityWeakRef;

	private MyActivityManager() {}

	public static MyActivityManager getInstance() {
		if(sInstance==null){
			synchronized (MyActivityManager.class){
				if(sInstance==null){
					sInstance= new MyActivityManager();
				}
			}
		}
		return sInstance;
	}

	public Activity getCurrentActivity() {
		Activity currentActivity = null;
		if (sCurrentActivityWeakRef != null) {
			currentActivity = sCurrentActivityWeakRef.get();
		}
		return currentActivity;
	}

	public void setCurrentActivity(Activity activity) {
		sCurrentActivityWeakRef = new WeakReference<>(activity);
	}


	public static boolean isAlive(Object ref) {
		if (ref == null) {
			return false;
		} else {
			if (ref instanceof Service)
				return isServiceAlive((Service) ref);
			if (ref instanceof Activity)
				return isActivityAlive((Activity) ref);
			if (ref instanceof Fragment)
				return isFragmentAlive((Fragment) ref);
			if (ref instanceof android.support.v4.app.Fragment)
				return isV4FragmentAlive((android.support.v4.app.Fragment) ref);
			if (ref instanceof ImageView)
				return isImageAlive((ImageView) ref);
		}
		return true;
	}
	static boolean  isServiceAlive(Service candidate) {
		if (candidate == null)
			return false;
		ActivityManager manager = (ActivityManager) candidate.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(Integer.MAX_VALUE);
		if (services == null)
			return false;
		for (ActivityManager.RunningServiceInfo service : services) {
			if (candidate.getClass().getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	static boolean isActivityAlive(Activity a) {
		if (a == null)
			return false;

		if(Build.VERSION.SDK_INT < 17) {
			if (a.isFinishing())
				return false;
		}else{
			if (a.isFinishing() || a.isDestroyed())
				return false;
		}
		return true;
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	static boolean isFragmentAlive(Fragment fragment) {
		boolean ret = isActivityAlive(fragment.getActivity());
		if (!ret)
			return false;
		if (fragment.isDetached())
			return false;
		return true;
	}
	static boolean isV4FragmentAlive(android.support.v4.app.Fragment fragment) {
		boolean ret = isActivityAlive(fragment.getActivity());
		if (!ret)
			return false;
		if (fragment.isDetached())
			return false;
		return true;
	}
	static boolean isImageAlive(ImageView imageView) {
		Context context = imageView.getContext();
		if (context instanceof Service)
			return isServiceAlive((Service) context);
		if (context instanceof Activity)
			return isActivityAlive((Activity) context);
		return true;
	}
}
