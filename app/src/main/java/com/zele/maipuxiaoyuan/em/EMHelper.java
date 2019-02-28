package com.zele.maipuxiaoyuan.em;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.common.dialog.ConfirmTipDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.MyActivityManager;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.em.receiver.CallReceiver;
import com.zele.maipuxiaoyuan.ui.login.LoginActivity;

/**
 * Description:      环信信息类
 * Autour：          LF
 * Date：            2018/12/8 14:14
 */

public class EMHelper {

    private static EMHelper mInstance = null;

    private Context mAppContext;

    private CallReceiver mCallReceiver;
    /**
     * EM链接监听
     */
    private EMConnectionListener mConnectionListener;

    public synchronized static EMHelper getInstance() {
        if (mInstance == null) {
            mInstance = new EMHelper();
        }
        return mInstance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        mAppContext = context;

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        //you need apply & set your own id if you want to use google cloud messaging.
        options.setFCMNumber("324169311137");
        //you need apply & set your own id if you want to use Mi push notification
        options.setMipushConfig("2882303761517426801", "5381742660801");
        //you need apply & set your own id if you want to use Huawei push notification
//        options.setHuaweiPushAppId("10492024");

        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(Constants.EM_DEBUG_MODE_SWITCH);

        setConnectionListener();
    }

    private void setConnectionListener() {
        // create the global connection listener
        mConnectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                Log.e("EMClientListener_hx", String.valueOf(error));
                // 用户删除通知
                if (error == EMError.USER_REMOVED) {
                    onCurrentAccountRemoved();
                }
                // 用户单点登录通知
                else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onConnectionConflict();
                }
            }

            @Override
            public void onConnected() {
                Log.e("EMClientListener_hx", "connect_success");
            }
        };
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (mCallReceiver == null) {
            mCallReceiver = new CallReceiver();
        }

        //register incoming call receiver
        mAppContext.registerReceiver(mCallReceiver, callFilter);
        //register connection listener
        EMClient.getInstance().addConnectionListener(mConnectionListener);
        //register group and contact event listener
//        registerGroupAndContactListener();
        //register message event listener
//        registerMessageListener();
    }

    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 用户单点登录
     */
    protected void onConnectionConflict() {
        //退出环信
        logout(false, null);
        final Activity currActivity = MyActivityManager.getInstance().getCurrentActivity();
        if (!currActivity.isFinishing()) {
            currActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ConfirmTipDialog.Builder builder = new ConfirmTipDialog.Builder();
                    ConfirmTipDialog dialog = builder.setTitleStr("提示")
                            .setContentStr("您的帐号在其他设备登录")
                            .setConfirmType(ConfirmTipDialog.CONFIRM_SINGLE_TYPE)
                            .setCancelable(false)
                            .setClickConfirmListener(new DialogConfirmClickListener() {
                                @Override
                                public void onDialogConfirmClickListener(Dialog dialog) {
                                    SharedPreferenceCache.getInstance().clearUserInfo();
                                    MyApplication.IS_LOGIN = false;
                                    MyApplication.getInstance().finishAllActivity();

                                    currActivity.startActivity(new Intent(currActivity.getApplicationContext(), LoginActivity.class));
                                    currActivity.finish();
                                    dialog.dismiss();
                                }
                            }).Build(currActivity);
                    dialog.show();
                }
            });
        }
    }

    /**
     * 用户移除弹框
     */
    protected void onCurrentAccountRemoved() {
        final Activity currActivity = MyActivityManager.getInstance().getCurrentActivity();
        logout(false, null);
        if (!currActivity.isFinishing()) {
            try {
                currActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ConfirmTipDialog.Builder builder = new ConfirmTipDialog.Builder();
                        ConfirmTipDialog dialog = builder.setTitleStr("Remove the notification")
                                .setContentStr("This user has been removed")
                                .setConfirmType(ConfirmTipDialog.CONFIRM_SINGLE_TYPE)
                                .setCancelable(false)
                                .setClickConfirmListener(new DialogConfirmClickListener() {
                                    @Override
                                    public void onDialogConfirmClickListener(Dialog dialog) {
                                        SharedPreferenceCache.getInstance().clearUserInfo();
                                        MyApplication.IS_LOGIN = false;
                                        MyApplication.getInstance().finishAllActivity();

                                        currActivity.startActivity(new Intent(currActivity.getApplicationContext(), LoginActivity.class));
                                        currActivity.finish();
                                        dialog.dismiss();
                                    }
                                }).Build(currActivity);
                        dialog.show();
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
//                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
//                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }
}
