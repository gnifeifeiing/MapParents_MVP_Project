package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.zele.maipuxiaoyuan.bean.HallHonorListBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.HallHonorModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.HallHonorView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      荣誉奖状
 * Autour：          LF
 * Date：            2018/11/13 17:52
 */

public class HallHonorPresenter extends BasePresenter<HallHonorView> {

    private HallHonorModel mHallHonorModel;

    public HallHonorPresenter() {
        this.mHallHonorModel = new HallHonorModel();
    }

    /**
     * 获取荣誉奖状数据
     * @param parameter
     */
    public void getHallHonorData(Map<String, String> parameter){
        mHallHonorModel.getHallHonorData(parameter, new PresenterCallBack<HallHonorListBean>(){

            @Override
            public void onSuccess(HallHonorListBean result) {
                if(getView()!=null){
                    getView().getHallHonorData(result);
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
