package com.zele.maipuxiaoyuan.ui.login;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.GuideAdapter;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.common.base.BaseView;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:      登录前引导页
 * Autour：          LF
 * Date：            2018/10/24 11:03
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.guideVp)
    ViewPager mGuideVp;

    //引导图片的资源id
    private int[] mResIds = { R.mipmap.s_1, R.mipmap.s_2};
    //引导图片的集合
    private List<ImageView> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setColor(R.color.transparent);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化引导图片和引导点
     * ps：可以直接把引导点放到图片上去
     */
    private void initData() {
        for (int i = 0; i < mResIds.length; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(mResIds[i]);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            RelativeLayout.LayoutParams lpImg = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            img.setLayoutParams(lpImg);
            mList.add(img);
        }
        GuideAdapter adapter = new GuideAdapter(mList);
        mGuideVp.setAdapter(adapter);

        //最后一个页面设置点击事件监听
        mList.get(mResIds.length - 1).setOnClickListener(this);
    }



    public void setColor(int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            color = (color ==0? R.color.primaryBg:color);
            window.setStatusBarColor(color);
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

    @Override
    public void onClick(View v) {
        SharedPreferenceCache.getInstance().setPres("IsFirst","0");
        toNextActivityAndFinish(WelcomeActivity.class);
    }
}
