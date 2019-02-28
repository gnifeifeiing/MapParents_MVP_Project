package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autonavi.amap.mapcore.Md5Utility;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.SettingPwdPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.SettingPwdView;
import com.zele.maipuxiaoyuan.ui.login.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      修改密码
 * Autour：          LF
 * Date：            2018/11/24 14:09
 */
public class SettingPwdActivity extends BaseActivity<SettingPwdView,SettingPwdPresenter> implements SettingPwdView {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.setPwd_oldPwdEt)
    EditText mOldPwdEt;
    @BindView(R.id.setPwd_newPwdEt)
    EditText mNewPwdEt;
    @BindView(R.id.setPwd_newEyeIv)
    ImageView mNewEyeIv;
    @BindView(R.id.setPwd_repeatPwdEt)
    EditText mRepeatPwdEt;
    @BindView(R.id.setPwd_repeatEyeIv)
    ImageView mRepeatEyeIv;
    @BindView(R.id.setPwd_submitBtn)
    Button mSubmitBtn;

    boolean mNewEyeIsOpen = true;
    boolean mRepeatEyeIsOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pwd);
        ButterKnife.bind(this);
        titleTitleTv.setText("设置新密码");
    }

    private void submitData() {
        // 隐藏输入法
        hideSoftInput();
        if(checkInput()){
            try {
                Map<String,String> parameter=new HashMap<>();
                parameter.put("uid",SharedPreferenceCache.getInstance().getPres("UserId"));
                parameter.put("pwd_old",URLEncoder.encode(Md5Utility.getStringMD5(mOldPwdEt.getText().toString().trim()), "UTF-8"));
                parameter.put("pwd_new",URLEncoder.encode(Md5Utility.getStringMD5(mNewPwdEt.getText().toString().trim() + ""), "UTF-8"));
                parameter.put("flag","0");
                mPresenter.updatePwd(parameter);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkInput(){
        String newPwd=mNewPwdEt.getText().toString().trim();
        String confirmPwd=mRepeatPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd)) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn, getResources().getString(R.string.input_password), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (mNewPwdEt.length() < 6 || mNewPwdEt.length() > 20) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn, getResources().getString(R.string.input_password_length), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn, getResources().getString(R.string.input_confirm_password), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!newPwd.equals(confirmPwd)) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn, getResources().getString(R.string.input_confirm_password_error), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public SettingPwdPresenter createPresenter() {
        return new SettingPwdPresenter();
    }

    @Override
    public SettingPwdView createView() {
        return this;
    }

    @OnClick({R.id.title_backIv, R.id.setPwd_submitBtn, R.id.setPwd_newEyeIv, R.id.setPwd_repeatEyeIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_backIv:
                finish();
                break;
            case R.id.setPwd_submitBtn:
                submitData();
                break;
            case R.id.setPwd_newEyeIv:
                if (mNewEyeIsOpen) {
                    mNewEyeIv.setImageResource(R.mipmap.open_eyes_icon);
                    mNewPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    mNewEyeIv.setImageResource(R.mipmap.close_eyes_icon);
                    mNewPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                int length = mNewPwdEt.getText().toString().trim().length();
                if (length != 0) mNewPwdEt.setSelection(length);
                mNewEyeIsOpen = !mNewEyeIsOpen;
                break;
            case R.id.setPwd_repeatEyeIv:
                if (mRepeatEyeIsOpen){
                    mRepeatEyeIv.setImageResource(R.mipmap.open_eyes_icon);
                    mRepeatPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else {
                    mRepeatEyeIv.setImageResource(R.mipmap.close_eyes_icon);
                    mRepeatPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                int length2 = mRepeatPwdEt.getText().toString().trim().length();
                if (length2!=0) mRepeatPwdEt.setSelection(length2);
                mRepeatEyeIsOpen=!mRepeatEyeIsOpen;
                break;
        }
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mSubmitBtn,error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updatePwd(BaseBean bean) {
        cancelProgress();
        if ("100".equals(bean.getResult())) {
            Toast.makeText(getBaseContext(), "修改成功,请重新登录！", Toast.LENGTH_LONG).show();

            SharedPreferenceCache.getInstance().clearUserInfo();
            MyApplication.IS_LOGIN=false;

            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();

            MyApplication.getInstance().finishAllActivity();
        } else if ("102".equals(bean.getResult())) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn,"修改失败", Snackbar.LENGTH_SHORT).show();
        } else if ("104".equals(bean.getResult())) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn,"用户数据不存在", Snackbar.LENGTH_SHORT).show();
        }
    }
}
