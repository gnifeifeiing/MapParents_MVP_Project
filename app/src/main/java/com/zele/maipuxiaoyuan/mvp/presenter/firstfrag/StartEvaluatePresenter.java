package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateTagBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.StartEvaluateModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.StartEvaluateView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      星级评价
 * Autour：          LF
 * Date：            2018/12/12 14:38
 */

public class StartEvaluatePresenter extends BasePresenter<StartEvaluateView> {

    private StartEvaluateModel mStartEvaluateModel;

    public StartEvaluatePresenter() {
        this.mStartEvaluateModel = new StartEvaluateModel();
    }

    /**
     * 获取TAG标签列表
     * @param parameter
     */
    public void getTagList(Map<String, String> parameter){
        mStartEvaluateModel.getTagList(parameter, new PresenterCallBack<EvaluateTagBean>(){

            @Override
            public void onSuccess(EvaluateTagBean result) {
                if(getView()!=null){
                    getView().getTagList(result);
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


    /**
     * 综合评价评星详情根据班级请求数据
     * @param parameter
     */
    public void getStudHonors(Map<String, String> parameter){
        mStartEvaluateModel.getStudHonors(parameter, new PresenterCallBack<EvaluateRecordBean>(){

            @Override
            public void onSuccess(EvaluateRecordBean result) {
                if(getView()!=null){
                    getView().getStudHonors(result);
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
