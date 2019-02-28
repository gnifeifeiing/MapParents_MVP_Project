package com.zele.maipuxiaoyuan.ui.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.common.base.BaseView;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.ui.login.GuideActivity;
import com.zele.maipuxiaoyuan.ui.login.WelcomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:      欢迎页
 * Autour：          LF
 * Date：            2018/10/24 9:35
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_startImg)
    ImageView mStartImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setStatusBarColor(R.color.transparent);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mStartImg, "Alpha", 0, 1);
        animator.setDuration(2000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                swithActivity();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void swithActivity() {
        boolean isFirst = SharedPreferenceCache.getInstance().getPres("IsFirst").equals("1") ? true : false;
        if (isFirst) {
            toNextActivityAndFinish(GuideActivity.class);
        } else {
            if (MyApplication.IS_LOGIN) {
//                //已经登录
//                //如果学生信息没有绑定
//                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
//                if (MyApplication.getStudent() != null && MyApplication.getStudent().getSid() == null) {
//                    String register_step = sp.getString("register_step", "2");
//                    if ("3".equals(register_step)) {
//                        startActivity(new Intent(getBaseContext(), BindStudentRelationActivity.class));
//                    } else if ("ok".equals(register_step)) {
//                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
//                    } else {
//                        //其他情况都进入第二步
//                        startActivity(new Intent(this, BindStudentPhoneActivity.class));
//                    }
//                }
                toNextActivityAndFinish(MainActivity.class);
            } else {
                //没有登录
                toNextActivityAndFinish(WelcomeActivity.class);
            }
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
