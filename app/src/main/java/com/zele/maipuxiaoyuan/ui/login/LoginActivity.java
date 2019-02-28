package com.zele.maipuxiaoyuan.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.amap.mapcore.Md5Utility;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.login.LoginPresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.LoginView;
import com.zele.maipuxiaoyuan.ui.main.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      登录
 * Autour：          LF
 * Date：            2018/10/24 14:05
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.title_titleTv)
    TextView mTitleTv;
    @BindView(R.id.login_usernameEt)
    EditText mUsernameEt;
    @BindView(R.id.login_passwordEt)
    EditText mPasswordEt;
    @BindView(R.id.title_backIv)
    ImageView mBackIv;
    @BindView(R.id.login_loginBtn)
    Button mLoginBtn;
    @BindView(R.id.login_registerTv)
    TextView mRegisterTv;
    @BindView(R.id.login_forgetPwdTv)
    TextView mForgetPwdTv;
    @BindView(R.id.login_QQLoginBtn)
    Button mQQLoginBtn;
    @BindView(R.id.login_WXLoginBtn)
    Button mWXLoginBtn;

    private String TAG = "weixin_____qq";

    // 环信密码
    private String mUUPwd;
    private String mUid;
    private String mUserName;

    // 是否是账户登录，否则为微信或qq登录
    private boolean mIsAccountLogin = true;
    private Map<String, String> mQqAndWxMap;
    // 判断是微信还是扣扣
    private SHARE_MEDIA mShareMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mTitleTv.setText("登录");
    }


    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView createView() {
        return this;
    }

    @OnClick({R.id.title_backIv, R.id.login_loginBtn, R.id.login_registerTv, R.id.login_forgetPwdTv, R.id.login_QQLoginBtn, R.id.login_WXLoginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_backIv:
                finish();
                break;
            // 登录
            case R.id.login_loginBtn:
                mIsAccountLogin = true;
                login();
                break;
            // 注册
            case R.id.login_registerTv:
                Intent intent = new Intent(this, RegisterOneActivity.class);
                startActivity(intent);
                break;
            // 忘记密码
            case R.id.login_forgetPwdTv:
                Intent intent2 = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                intent2.putExtra("number", mUsernameEt.getText().toString().trim());
                startActivity(intent2);
                break;
            // QQ登录
            case R.id.login_QQLoginBtn:
                mIsAccountLogin = false;
                authorization(SHARE_MEDIA.QQ);
                break;
            // 微信登录
            case R.id.login_WXLoginBtn:
                mIsAccountLogin = false;
                authorization(SHARE_MEDIA.WEIXIN);
                break;
        }
    }

    /***********************  QQ、微信登录模块代码--Start  ********************************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 授权回调
     *
     * @param share_media 授权类型
     */
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                mQqAndWxMap = map;
                mShareMedia = share_media;
                loginByQqOrWx(share_media, map);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
                SnackbarUtil.ShortSnackbar(mUsernameEt, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }


    /**
     * QQ、微信登录,根据openId 请求登录
     */
    public void loginByQqOrWx(SHARE_MEDIA share_media, Map<String, String> map) {
        Map<String, String> parameter = new HashMap<>();
        if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
            parameter.put("wopenId", map.get("openid"));
        } else {
            parameter.put("qopenId", map.get("openid"));
        }
        mPresenter.login(parameter);
    }

    /***********************  QQ、微信登录模块代码--End  ********************************/


    /**
     * 登录
     */
    private void login() {
        if (checkInput()) {
            //当开始请求登录时，按钮不可点击
            mLoginBtn.setEnabled(false);
            showProgress(getResources().getString(R.string.loading_text));
            Map<String, String> parameter = new HashMap<>();
            parameter.put("userName", mUsernameEt.getText().toString().trim());
            parameter.put("userPwd", Md5Utility.getStringMD5(mPasswordEt.getText().toString()));
            mPresenter.login(parameter);
        }
    }

    /**
     * 检查输入合法性
     *
     * @return
     */
    private boolean checkInput() {
        if (TextUtils.isEmpty(mUsernameEt.getText().toString())) {
            SnackbarUtil.ShortSnackbar(mUsernameEt, getResources().getString(R.string.User_name_cannot_be_empty), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mPasswordEt.getText().toString())) {
            SnackbarUtil.ShortSnackbar(mUsernameEt, getResources().getString(R.string.Password_cannot_be_empty), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 获取登录用户信息
     *
     * @param bean
     */
    @Override
    public void onLoginResult(LoginUserBean bean) {
        cancelProgress();
        try {
            if ("100".equals(bean.getResult())) {
                saveUserInfo(bean.getUser());
            } else if ("101".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mUsernameEt, "该账号已被禁止使用，请联系客服！", Snackbar.LENGTH_LONG).show();
            } else if ("102".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mUsernameEt, "登录有误，请重新登录！", Snackbar.LENGTH_LONG).show();
            } else if ("104".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mUsernameEt, "登录有误，请重新输入！", Snackbar.LENGTH_LONG).show();
            } else if ("108".equals(bean.getResult())) {
                if (mIsAccountLogin) {
                    SnackbarUtil.ShortSnackbar(mUsernameEt, "用户不存在，请先注册用户！", Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, RegisterOneActivity.class);
                    intent.putExtra("nickname", mQqAndWxMap.get("name"));
                    intent.putExtra("openid", mQqAndWxMap.get("openid"));
                    String typeName = "";
                    if (mShareMedia.equals(SHARE_MEDIA.WEIXIN)) {
                        typeName = "wx";
                    }
                    if (mShareMedia.equals(SHARE_MEDIA.QQ)) {
                        typeName = "qq";
                    }
                    intent.putExtra("type", typeName);
                    startActivity(intent);
                }
            } else if ("113".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mUsernameEt, "该微信号已绑定有号，不能重复绑定!", Snackbar.LENGTH_LONG).show();
            } else {
                SnackbarUtil.ShortSnackbar(mUsernameEt, "登录有误，请重新登录！", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mLoginBtn.setEnabled(true);
        }
    }

    /**
     * 获取绑定学生列表信息
     * @param bean
     */
    @Override
    public void BindStudentsBean(BindStudentsBean bean) {
        if ("100".equals(bean.getResult())) {
            MyApplication.IS_LOGIN = true;
            //1.保存数据到sp，学生列表
            String content = JSON.toJSONString(bean);
            if (!TextUtils.isEmpty(content)) {
                SharedPreferenceCache.getInstance().setPres("StudentsContent", content);
            }
            SharedPreferenceCache.getInstance().setPres("StudentsList", JSON.toJSONString(bean.getStudents()));
            //获取学生列表
            List<BindStudentsBean.StudentsBean> list = bean.getStudents();
            if (list != null && list.size() > 0) {
                SharedPreferenceCache.getInstance().setPres("Student", JSON.toJSONString(list.get(0)));
                // 如果改账号绑定过学生信息，那么登陆环信
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(getBaseContext(), "请先绑定学生证", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), BindStudentPhoneActivity.class);
                intent.putExtra("register",true);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mUsernameEt, error, Snackbar.LENGTH_LONG).show();
        mLoginBtn.setEnabled(true);
    }

    /**
     * 登陆成功后、保存个人信息
     *
     * @param user
     */
    private void saveUserInfo(LoginUserBean.User user) {
        MyApplication.IS_LOGIN = true;
        SharedPreferenceCache.getInstance().setPres("IsLogin", "1");

        mUUPwd = user.getUupwd();//环信密码
        mUserName = user.getUserName();
        Log.w("res_account", mUUPwd + "=" + mUserName);
        // 存储账号信息到sp
        SharedPreferenceCache.getInstance().setPres("UserId", user.getUid());
        SharedPreferenceCache.getInstance().setPres("UserName", mUserName);
        SharedPreferenceCache.getInstance().setPres("LoginMobile", user.getMobile());
        SharedPreferenceCache.getInstance().setPres("UUPwd", mUUPwd);
        SharedPreferenceCache.getInstance().setPres("UserBean", JSONObject.toJSONString(user));

        //2.注册友盟别名信息
        MyApplication.getInstance().registerUmengAnalysis(mUserName);
        MyApplication.getInstance().registerUmengPusth(mUserName);

        //获取绑定学生信息
        showProgress(getResources().getString(R.string.loading_text));
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", user.getUid());
        mUid = user.getUid();
        mPresenter.getBindStudents(parameter);
    }
}
