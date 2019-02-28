package com.zele.maipuxiaoyuan.mvp.view.login;

import com.zele.maipuxiaoyuan.bean.ArticleBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      webview
 * Autour：          LF
 * Date：            2018/10/26 16:52
 */

public interface ArticleView extends BaseView {

    // 获取article信息
    void getArticle(ArticleBean bean);
}
