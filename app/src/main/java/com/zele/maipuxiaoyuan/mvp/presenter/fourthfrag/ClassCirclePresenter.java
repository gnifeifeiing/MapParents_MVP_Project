package com.zele.maipuxiaoyuan.mvp.presenter.fourthfrag;

import com.zele.maipuxiaoyuan.bean.ClassCircleBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fourthfrag.ClassCircleModel;
import com.zele.maipuxiaoyuan.mvp.view.fourthfrag.ClassCircleView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      班级圈
 * Autour：          LF
 * Date：            2018/11/21 16:58
 */

public class ClassCirclePresenter extends BasePresenter<ClassCircleView> {

    private ClassCircleModel mClassCircleModel;

    public ClassCirclePresenter() {
        this.mClassCircleModel = new ClassCircleModel();
    }

    /**
     * 班级圈首页
     * @param parameter
     */
    public void getCircleData(Map<String, String> parameter){
        mClassCircleModel.getCircleData(parameter, new PresenterCallBack<ClassCircleBean>(){

            @Override
            public void onSuccess(ClassCircleBean result) {
                if(getView()!=null){
                    getView().getCircleData(result);
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
