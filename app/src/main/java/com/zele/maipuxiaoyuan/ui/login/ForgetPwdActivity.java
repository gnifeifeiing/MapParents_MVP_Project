package com.zele.maipuxiaoyuan.ui.login;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.autonavi.amap.mapcore.Md5Utility;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.utils.Utils;
import com.zele.maipuxiaoyuan.common.views.TimeButton;
import com.zele.maipuxiaoyuan.mvp.presenter.login.ForgetPwdPresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.ForgetPwdView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      忘记密码
 * Autour：          LF
 * Date：            2018/10/30 16:53
 */
public class ForgetPwdActivity extends BaseActivity<ForgetPwdView, ForgetPwdPresenter> implements ForgetPwdView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.forgetPwd_phoneEt)
    EditText mPhoneEt;
    @BindView(R.id.forgetPwd_timeBtn)
    TimeButton mTimeBtn;
    @BindView(R.id.forgetPwd_codeEt)
    EditText mCodeEt;
    @BindView(R.id.forgetPwd_pwdEt)
    EditText mPwdEt;
    @BindView(R.id.forgetPwd_okBtn)
    Button forgetPwdOkBtn;

    private String mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        tvTitle.setText("找回密码");
        mTimeBtn.setTextAfter("秒后重新获取").setTextBefore("获取验证码").setLenght(60 * 1000);
    }

    @Override
    public ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter();
    }

    @Override
    public ForgetPwdView createView() {
        return this;
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mPhoneEt, error, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick({R.id.iv_back, R.id.forgetPwd_timeBtn, R.id.forgetPwd_okBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.forgetPwd_timeBtn:
                sendValidationCode();
                break;
            case R.id.forgetPwd_okBtn:
                submitUpdate();
                break;
        }
    }

    /**
     * 提交
     */
    private void submitUpdate() {
        if(checkInputTwo()){
            try {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("userName", mPhoneEt.getText().toString().trim());
                parameter.put("pwd_new", URLEncoder.encode(Md5Utility.getStringMD5(mPwdEt.getText().toString().trim()), "UTF-8"));
                parameter.put("vercode", mCodeEt.getText().toString().trim());
                parameter.put("flag", "2");
                showProgress("密码重置中....");
                mPresenter.acceptValidationCode(parameter);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送验证码
     */
    private void sendValidationCode(){
        if(checkInput()){
            mTimeBtn.changeStatus();
            Map<String, String> parameter = new HashMap<>();
            parameter.put("mobile", mNumber);
            mPresenter.acceptValidationCode(parameter);
        }
    }

    private boolean checkInput(){
        mNumber = mPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(mNumber)) {
            SnackbarUtil.ShortSnackbar(mPhoneEt, "请输入手机号码", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(mNumber)) {
            SnackbarUtil.ShortSnackbar(mPhoneEt, "手机号码输入有误", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkInputTwo(){
        if(checkInput()){
            if(TextUtils.isEmpty(mCodeEt.getText().toString().trim())){
                SnackbarUtil.ShortSnackbar(mPhoneEt, "请输入验证码", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            if(TextUtils.isEmpty(mPwdEt.getText().toString().trim())){
                SnackbarUtil.ShortSnackbar(mPhoneEt, "请输入您的新密码", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 是否已发送验证码
     * @param bean
     */
    @Override
    public void acceptValidationCode(BaseBean bean) {
        if(bean.getResult().equals("100")){
            SnackbarUtil.ShortSnackbar(mPhoneEt, getResources().getString(R.string.send_true), Snackbar.LENGTH_SHORT).show();
            Log.i(RegisterOneActivity.class.getName(),bean.getResult());
        }else{
            Log.i(RegisterOneActivity.class.getName(),bean.getResult());
        }
    }

    /**
     * 密码重置
     * @param bean
     */
    @Override
    public void resetPasswd(BaseBean bean) {
        cancelProgress();
        if(bean.getResult().equals("100")){
            SnackbarUtil.ShortSnackbar(mPhoneEt,"密码重置完成", Snackbar.LENGTH_SHORT).show();
            Log.i(RegisterOneActivity.class.getName(),bean.getResult());
            finish();
        }else{
            Log.i(RegisterOneActivity.class.getName(),bean.getResult());
            SnackbarUtil.ShortSnackbar(mPhoneEt,"密码修改失败", Snackbar.LENGTH_SHORT).show();
        }
    }
}
