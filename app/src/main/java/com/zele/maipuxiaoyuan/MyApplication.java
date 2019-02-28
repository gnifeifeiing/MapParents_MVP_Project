package com.zele.maipuxiaoyuan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.socialize.PlatformConfig;
import com.zele.maipuxiaoyuan.bean.BlackList;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.utils.MyActivityManager;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.em.EMHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/1/4 15:16
 */

public class MyApplication extends Application  implements Application.ActivityLifecycleCallbacks{
    /**
     * 是否登录
     */
    public static boolean IS_LOGIN = false;
    public LoginUserBean.User mUserBean = null;
    //环信相关
    public static HashMap<String ,BlackList> black = new HashMap<>();
    // 用于存放倒计时时间
    public static Map<String, Long> map;
    private static Stack<Activity> activityStack;
    private static MyApplication singleton;

    private static String mPushAlias;//推送的别名
    public PushAgent mPushAgent;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.primaryBg, android.R.color.white);//全局设置主题颜色
                return new BezierCircleHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        singleton = this;
        //获取登录状态
        IS_LOGIN = SharedPreferenceCache.getInstance().getPres("IsLogin").equals("0") ? false : true;
        Stetho.initializeWithDefaults(this);
        //初始化友盟
        initUM();
        //初始化imageloader
        initImgLoader();
        //初始化环信
        initEM();
        //注册友盟相关服务
        initUserInfo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initUM(){
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.setLogEnabled(true);
    }

    private void initImgLoader(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }

    private void initEM(){
        EMHelper.getInstance().init(singleton);
    }

    {
        //微信
        PlatformConfig.setWeixin("wxc7870f4a7e370103", "8447b3e33b1436e4f534916b22af0f7b");
        //QQ
        PlatformConfig.setQQZone("1105736000", "31a14064a79c8bc877fabe2994ab9ae8");
    }

    /**
     * 设置账号和学生详情信息并注册相关服务
     */
    public void initUserInfo() {
        // 获取账号缓存
        String userBean = SharedPreferenceCache.getInstance().getPres("UserBean");
        if (!TextUtils.isEmpty(userBean)) {
            // 从缓存中获取账号信息
            mUserBean = JSON.parseObject(userBean, LoginUserBean.User.class);
            if (mUserBean != null && mUserBean.getUserName() != null) {
                String user_name = mUserBean.getUserName();
                registerUmengAnalysis(user_name);
                registerUmengPusth(user_name);
            }
        }
    }

    /**
     * 注册友盟统计，需要账号名称
     */
    public void registerUmengAnalysis(String name) {
        // 初始化注册需要账号的服务
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setSessionContinueMillis(30000);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.onProfileSignIn(name);
    }

    /**
     * 注册推送服务
     */
    public void registerUmengPusth(final String name) {
        // 初始化注册需要账号的服务
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                System.out.println("deviceToken=" + deviceToken);
                //服务注册成功后，再改变别名信息
                registerPusthAlias(name);
            }

            @Override
            public void onFailure(String s, String s1) {
                System.out.println("deviceToken=fail");
            }
        });
    }

    /**
     * 注册推送别名，根据账号名推送，必须有账号才调用
     */
    private void registerPusthAlias(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //如果设置过别名了，清空别名;如果更改账号了，别名要更改，以便重新接收推送信息
                if (!TextUtils.isEmpty(mPushAlias)) {
                    mPushAgent.deleteAlias(mPushAlias, "android", new UTrack.ICallBack() {
                        @Override
                        public void onMessage(boolean isSuccess, String message) {
                            if (isSuccess) {
                                mPushAlias = null;
                            }
                        }
                    });
                }
                //清空别名后，如果账号不为空
                if (!TextUtils.isEmpty(name)) {
                    mPushAgent.addAlias(name, "android", new UTrack.ICallBack() {
                        @Override
                        public void onMessage(boolean b, String s) {
                            if (b) {
                                mPushAlias = name;
                            }
                        }
                    });
                    //设置用户id和device_token的一一映射关系，确保同一个alias只对应一台设备：
                    mPushAgent.addAlias(name, "android", new UTrack.ICallBack() {
                        @Override
                        public void onMessage(boolean b, String s) {
                            if (b) {
                                mPushAlias = name;
                            }
                        }
                    });
                } else {
                    Log.w("res_erro", "alias_name is null");
                }
            }
        }).start();
    }

    public static MyApplication getInstance() {
        return singleton;
    }

    /*************************** -- activity栈集合 -- *****************************/
    /**
     * 把Activity添加到栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 是否存在Activity
     */
    public boolean isExistActivity(String activityName) {
        for (Activity a : activityStack) {
            if (a.getClass().getName().equals(activityName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出App
     */
    public void ExitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {}
    }

    /*************************** -- activity生命周期监听接口 -- *****************************/
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        MobclickAgent.onPageStart(activity.getClass().getSimpleName());
        MobclickAgent.onResume(activity);
        MyActivityManager.getInstance().setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPageEnd(activity.getClass().getSimpleName());
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
