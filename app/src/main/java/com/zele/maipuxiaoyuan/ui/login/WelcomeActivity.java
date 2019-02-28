package com.zele.maipuxiaoyuan.ui.login;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.common.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      登录欢迎页
 * Autour：          LF
 * Date：            2018/10/24 11:20
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_loginBtn)
    Button mLoginBtn;
    @BindView(R.id.welcome_registerBtn)
    Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        setStatusBarColor(R.color.transparent);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.welcome_loginBtn, R.id.welcome_registerBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //登录
            case R.id.welcome_loginBtn:
                toNextActivityAndFinish(LoginActivity.class);
                break;
            //注册
            case R.id.welcome_registerBtn:
                toNextActivityAndFinish(RegisterOneActivity.class);
                break;
        }
    }











    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }
}
