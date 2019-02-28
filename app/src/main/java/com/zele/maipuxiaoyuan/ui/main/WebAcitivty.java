package com.zele.maipuxiaoyuan.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.common.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/9 17:00
 */
public class WebAcitivty extends BaseActivity {

    @BindView(R.id.title_backIv)
    ImageView mBackIv;
    @BindView(R.id.title_titleTv)
    TextView mTitleTv;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_acitivty);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        String title = intent.getStringExtra("title");
        mTitleTv.setText(title);

        String url = intent.getStringExtra("url");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        showProgress(getResources().getString(R.string.loading_text));
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //当进度走到100的时候做自己的操作，我这边是弹出dialog
                if (progress == 100) {
                    if(mProgressDialog!=null)
                        mProgressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.clearMatches();
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
