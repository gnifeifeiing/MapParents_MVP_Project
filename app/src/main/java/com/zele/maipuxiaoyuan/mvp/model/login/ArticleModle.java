package com.zele.maipuxiaoyuan.mvp.model.login;

import com.zele.maipuxiaoyuan.bean.ArticleBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      条款、服务、帮助
 * Autour：          LF
 * Date：            2018/10/26 16:55
 */

public class ArticleModle extends BaseModel {

    /**
     * 条款、服务、帮助
     * @param patameter
     * @param callBack
     */
    public void getArticle(Map<String, String> patameter, final PresenterCallBack<ArticleBean> callBack) {
        Call<ArticleBean> call=mService.getArticle(patameter);
        call.enqueue(new MyCallBack<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
