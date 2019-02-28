package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.HelpBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.common.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      帮助与反馈详情
 * Autour：          LF
 * Date：            2018/11/22 16:52
 */
public class HelpDetailsActivity extends BaseActivity {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.helpD_title)
    TextView helpDTitle;
    @BindView(R.id.helpD_webView)
    WebView helpDWebView;

    private HelpBean.Help mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mItem = ((HelpBean.Help) getIntent().getExtras().get("item"));
        titleTitleTv.setText("帮助与反馈");
        helpDTitle.setText(mItem.getTitle());
        helpDWebView.loadDataWithBaseURL(null, mItem.getContext(), "text/html", "utf-8", null);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }
}
