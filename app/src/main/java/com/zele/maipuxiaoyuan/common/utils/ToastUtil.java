package com.zele.maipuxiaoyuan.common.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToastUtil {
	 private static Toast mToast;
	 public static void showToast(final Context act, final String msg) {
	        // 获得当前线程的名称
	        String threadName = Thread.currentThread().getName();
            if(mToast == null) {
            	if(act!=null&&msg!=null){
            		mToast = Toast.makeText(act, msg, Toast.LENGTH_SHORT);
            	}
            } else {
                mToast.setText(msg);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
	        if(mToast!=null){
	        	mToast.show();
	        }
	        
	    }
	    /**
	     * 显示toast
	     *
	     * @param act
	     * @param msg
	     */
	    public static void showToast(final Activity act, final String msg) {


	        // 获得当前线程的名称
	        String threadName = Thread.currentThread().getName();
	        // 判断是否是主线程，如果是主线程，直接显示toast
	        if ("main".equals(threadName)) {
	            if(mToast == null) {
	            	if(act!=null&&msg!=null){
	            		mToast = Toast.makeText(act, msg, Toast.LENGTH_SHORT);
	            	}
	                
	            } else {
	                mToast.setText(msg);
	                mToast.setDuration(Toast.LENGTH_SHORT);
	            }

	        } else {
	            // 如果不是，主线程，
	            act.runOnUiThread(new Runnable() {
	                @Override
	                public void run() {
	                    if(mToast == null) {
	                        mToast = Toast.makeText(act, msg, Toast.LENGTH_SHORT);
	                    } else {
	                        mToast.setText(msg);
	                        mToast.setDuration(Toast.LENGTH_SHORT);
	                    }
	                }
	            });
	        }
	        if(mToast!=null){
	        	mToast.show();
	        }
	        
	    }
	    public static boolean isMobileNO(String mobiles) {
	        Pattern p = Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$");
	        Matcher m = p.matcher(mobiles);
	        return m.matches();
	    }

}