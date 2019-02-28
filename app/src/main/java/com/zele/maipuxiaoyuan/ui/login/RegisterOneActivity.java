package com.zele.maipuxiaoyuan.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.autonavi.amap.mapcore.Md5Utility;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.NetUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.utils.ToastUtil;
import com.zele.maipuxiaoyuan.common.utils.Utils;
import com.zele.maipuxiaoyuan.common.views.TimeButton;
import com.zele.maipuxiaoyuan.mvp.presenter.login.RegisterPresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.RegisterView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      注册
 * Autour：          LF
 * Date：            2018/10/26 14:33
 */
public class RegisterOneActivity extends BaseActivity<RegisterView,RegisterPresenter> implements RegisterView {


    @BindView(R.id.register_backIv)
    ImageView mBackIv;
    @BindView(R.id.register_inputPhoneEt)
    EditText mInputPhoneEt;
    @BindView(R.id.register_timeBtn)
    TimeButton mTimeBtn;
    @BindView(R.id.register_setCodeEt)
    EditText mSetCodeEt;
    @BindView(R.id.register_passwordEt)
    EditText mPasswordEt;
    @BindView(R.id.register_eyeIv)
    ImageView mEyeIv;
    @BindView(R.id.register_eyeRl)
    RelativeLayout mEyeRl;
    @BindView(R.id.register_serviceListTv)
    TextView mServiceListTv;
    @BindView(R.id.register_privacyListTv)
    TextView mPrivacyListTv;
    @BindView(R.id.register_loginBtn)
    Button mLoginBtn;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    private String wopenid;
    private String qopenId;
    private String qq;
    private String weixin;

    boolean mEyeClose = true;
    private String mUserPassword;
    private String mPhoneStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        ButterKnife.bind(this);
        initView();
        initTransData();
    }

    private void initTransData() {
        Bundle bundle = getIntent().getExtras();
        //通过qq或者微信一键登陆的
        if(bundle!=null && bundle.containsKey("type")){
            if(bundle.getString("type")!=null)
                if("wx".equals(bundle.getString("type"))){
                    weixin=bundle.getString("nickname", "");
                    wopenid=bundle.getString("openid", "");
                }else if("qq".equals(bundle.getString("type"))){
                    qq=bundle.getString("nickname", "");
                    qopenId=bundle.getString("openid", "");
                }
        }
    }

    private void initView() {
        mTimeBtn.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setLenght(60 * 1000);
        //点击ScrollView隐藏软键盘
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public RegisterView createView() {
        return this;
    }

    @OnClick({R.id.register_backIv, R.id.register_timeBtn, R.id.register_eyeRl, R.id.register_serviceListTv, R.id.register_privacyListTv, R.id.register_loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.register_backIv:
                finish();
                break;
            // 获取验证码
            case R.id.register_timeBtn:
                requestCode();
                break;
            // 是否显示密码
            case R.id.register_eyeRl:
                if (mEyeClose){
                    mEyeIv.setImageResource(R.mipmap.open_eyes_icon);
                    mPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    mEyeIv.setImageResource(R.mipmap.close_eyes_icon);
                    mPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                int length = mPasswordEt.getText().toString().length();
                if (length!=0) mPasswordEt.setSelection(length);
                mEyeClose=!mEyeClose;
                break;
            case R.id.register_serviceListTv:
                Intent intent_service = new Intent(RegisterOneActivity.this,ArticleActivity.class);
                intent_service.putExtra("title", "服务条款");
                startActivity(intent_service);
                break;
            case R.id.register_privacyListTv:
                Intent intent_privacy = new Intent(RegisterOneActivity.this,
                        ArticleActivity.class);
                intent_privacy.putExtra("title", "隐私条款");
                startActivity(intent_privacy);
                break;
            case R.id.register_loginBtn:
                registerLogin();
                break;
        }
    }

    private void registerLogin() {
        if(checkInput()){
            mPhoneStr = mInputPhoneEt.getText().toString().trim();
            mUserPassword = mPasswordEt.getText().toString().trim();
            String code = mSetCodeEt.getText().toString().trim();
            mLoginBtn.setEnabled(false);
            try {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("wopenId", wopenid);
                parameter.put("qopenId", qopenId);
                parameter.put("qq", qq);
                parameter.put("weixin", weixin);
                parameter.put("userName", mPhoneStr);
                parameter.put("userPwd", URLEncoder.encode(Md5Utility.getStringMD5(mUserPassword), "UTF-8"));
                parameter.put("vercode", code);
                mPresenter.register(parameter);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检测输入是否合法
     * @return
     */
    private boolean checkInput(){
        String phoneStr = mInputPhoneEt.getText().toString().trim();
        String userPassword = mPasswordEt.getText().toString().trim();
        String code = mSetCodeEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)) {
            SnackbarUtil.ShortSnackbar(mInputPhoneEt, getResources().getString(R.string.input_phone), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(phoneStr)) {
            SnackbarUtil.ShortSnackbar(mInputPhoneEt, getResources().getString(R.string.phone_num_error), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(userPassword)) {
            SnackbarUtil.ShortSnackbar(mInputPhoneEt, getResources().getString(R.string.input_password), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (userPassword.length() < 6 || userPassword.length() > 20) {
            SnackbarUtil.ShortSnackbar(mInputPhoneEt, getResources().getString(R.string.input_password_length), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            SnackbarUtil.ShortSnackbar(mInputPhoneEt, getResources().getString(R.string.input_code), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 请求验证码
     */
    private void requestCode() {
        String phone = mInputPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            SnackbarUtil.ShortSnackbar(mLoginBtn, getResources().getString(R.string.input_phone), Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.isMobileNO(phone)) {
            SnackbarUtil.ShortSnackbar(mLoginBtn, getResources().getString(R.string.phone_num_error), Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!NetUtils.isNetworkConnected(getApplicationContext())) {
            SnackbarUtil.ShortSnackbar(mLoginBtn, "请先连接网络", Snackbar.LENGTH_SHORT).show();
            return;
        }

        mTimeBtn.changeStatus();
        Map<String, String> parameter = new HashMap<>();
        parameter.put("mobile", phone);
        mPresenter.sendValidationCode(parameter);
    }

    @Override
    public void onError(String error) {
        mLoginBtn.setEnabled(true);
        SnackbarUtil.ShortSnackbar(mLoginBtn, error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 发送验证码返回的结果
     * @param bean
     */
    @Override
    public void acceptValidationCode(BaseBean bean) {
        if(bean.getResult().equals("100")){
            SnackbarUtil.ShortSnackbar(mLoginBtn, getResources().getString(R.string.send_true), Snackbar.LENGTH_SHORT).show();
            Log.i(RegisterOneActivity.class.getName(),bean.getResult());
        }else{
            Log.i(RegisterOneActivity.class.getName(),bean.getResult());
        }
    }

    /**
     * 注册回调
     * @param bean
     */
    @Override
    public void onRegisterResult(LoginUserBean bean) {
        mLoginBtn.setEnabled(true);
        try {
            if ("100".equals(bean.getResult())) {
                LoginUserBean.User user=bean.getUser();
                // 存储账号信息到sp
                SharedPreferenceCache.getInstance().setPres("UserId", user.getUid());
                SharedPreferenceCache.getInstance().setPres("UserName", mPhoneStr);
                SharedPreferenceCache.getInstance().setPres("LoginMobile", user.getMobile());
                SharedPreferenceCache.getInstance().setPres("CustPassword", mUserPassword);
                SharedPreferenceCache.getInstance().setPres("RegisterStep", "2");
                SharedPreferenceCache.getInstance().setPres("UserBean", JSONObject.toJSONString(user));

                ToastUtil.showToast(RegisterOneActivity.this,"注册成功");
                //跳转到学生卡绑定界面
                Intent intent = new Intent(RegisterOneActivity.this,BindStudentPhoneActivity.class);
                intent.putExtra("uid",user.getUid() + "");
                intent.putExtra("phone",user.getUserName());
                intent.putExtra("register",true);
                startActivity(intent);
            } else if ("102".equals(bean.getResult())) {
                ToastUtil.showToast(RegisterOneActivity.this,"注册失败");
            } else if ("104".equals(bean.getResult())) {
                ToastUtil.showToast(RegisterOneActivity.this,"用户已存在，无法重复注册");
            } else if ("107".equals(bean.getResult())) {
                ToastUtil.showToast(RegisterOneActivity.this,"验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
