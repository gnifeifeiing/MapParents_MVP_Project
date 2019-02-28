
package com.zele.maipuxiaoyuan.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.umeng.message.PushAgent;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.utils.NetUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;

import java.util.List;
import java.util.Map;

/**
 * Description:      Activity基类
 * Autour：          LF
 * Date：            2018/10/23 11:11
 */

public abstract class BaseActivity<V extends BaseView,P extends BasePresenter<V>> extends AppCompatActivity {

    public P mPresenter;
    private V mView;

    public Dialog mProgressDialog;
    public Context mContext;

    // 用户移除弹框是否显示
    private boolean mIsAccountRemovedDialogShow=false;
    // 单点登录弹框是否显示
    private boolean mIsConflictDialogShow=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        MyApplication.getInstance().addActivity(this);
        mContext=this;
        init();
        //如果环信没有登录
        if(!EMClient.getInstance().isConnected()){
            LoginUserBean.User user =  JSON.parseObject(SharedPreferenceCache.getInstance().getPres("UserBean"), new TypeReference<LoginUserBean.User>() {});
            if(user!=null && !TextUtils.isEmpty(user.getUserName())) {
                loginEM(user.getUserName(), user.getUupwd());
            }
        }
    }

    private void loginEM(String currentUsername, String currentPassword) {
        if (!NetUtils.isNetworkConnected(MyApplication.getInstance())) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty_EM, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty_EM, Toast.LENGTH_SHORT).show();
            return;
        }

        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("EMClientLogin_hx","login_success");
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();

                for (EMConversation conversation : allConversations.values()) {

                    List<EMMessage> list = conversation.getAllMessages();
                    for (EMMessage item : list) {
                        String msgId = item.getMsgId();
                    }
                }
            }
            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                Log.e("EMClientLogin_hx","login_fail:"+message);
            }
        });
    }

    private void init(){
        mPresenter=createPresenter();
        if(mPresenter==null){
            return;
        }
        mView=createView();
        if(mPresenter==null){
            return;
        }
        mPresenter.attachView(mView);
    }

    public abstract P createPresenter();

    public abstract V createView();

    /**
     * 跳转到下一个Ac并结束当前Ac
     * @param cls   跳转Ac
     */
    protected void toNextActivityAndFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * 设置状态栏颜色
     * @param color
     */
    public void setStatusBarColor(int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            color = (color ==0? R.color.primaryBg:color);
            window.setStatusBarColor(color);
        }
    }

    //调用隐藏系统默认的输入法
    public void hideSoftInput() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //获取输入法打开的状态
    public boolean showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }

    public void showProgress(String strMsg) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        mProgressDialog = new Dialog(this, R.style.progress_dialog);
        mProgressDialog.setContentView(R.layout.dialog_loading);
        mProgressDialog.setCanceledOnTouchOutside(false);

        TextView msg = mProgressDialog.findViewById(R.id.tipTextView);
        ImageView iv = mProgressDialog.findViewById(R.id.img);
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.loadingdialog_anim);
        // 使用ImageView显示动画
        iv.startAnimation(hyperspaceJumpAnimation);
        if (strMsg.isEmpty())
            msg.setText("正在加载");
        else
            msg.setText(strMsg);
        try {
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        MyApplication.getInstance().finishActivity(this);
    }
}
