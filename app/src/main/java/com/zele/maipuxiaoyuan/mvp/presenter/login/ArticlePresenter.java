package com.zele.maipuxiaoyuan.mvp.presenter.login;

import com.zele.maipuxiaoyuan.bean.ArticleBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.ArticleModle;
import com.zele.maipuxiaoyuan.mvp.view.login.ArticleView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      条款、服务、帮助
 * Autour：          LF
 * Date：            2018/10/26 16:54
 */

public class ArticlePresenter extends BasePresenter<ArticleView> {

    private ArticleModle mArticleModle;

    public ArticlePresenter() {
        this.mArticleModle = new ArticleModle();
    }

    /**
     * 条款、服务、帮助
     * @param parameter
     */
    public void getArticle(Map<String, String> parameter){
        mArticleModle.getArticle(parameter, new PresenterCallBack<ArticleBean>(){

            @Override
            public void onSuccess(ArticleBean result) {
                if(getView()!=null){
                    getView().getArticle(result);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if(getView()!=null){
                    getView().onError(errMsg);
                }
            }
        });
    }
}
