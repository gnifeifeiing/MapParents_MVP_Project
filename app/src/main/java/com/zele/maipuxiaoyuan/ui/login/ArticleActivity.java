package com.zele.maipuxiaoyuan.ui.login;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.ArticleBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.mvp.presenter.login.ArticlePresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.ArticleView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      webview显示
 * Autour：          LF
 * Date：            2018/10/26 16:47
 */
public class ArticleActivity extends BaseActivity<ArticleView, ArticlePresenter> implements ArticleView {

    @BindView(R.id.title_backIv)
    ImageView mBackIv;
    @BindView(R.id.title_titleTv)
    TextView mTitleTv;
    @BindView(R.id.web)
    WebView mWeb;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public ArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }

    @Override
    public ArticleView createView() {
        return this;
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        mTitleTv.setText(title);

        Map<String, String> parameter = new HashMap<>();
        parameter.put("cname", title);
        mPresenter.getArticle(parameter);
    }

    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void getArticle(ArticleBean bean) {
        if ("100".equals(bean.getResult())) {
            mWeb.loadDataWithBaseURL(null, bean.getNews()
                            .get(0).getContext(), "text/html", "utf-8",
                    null);
        }
    }
}
