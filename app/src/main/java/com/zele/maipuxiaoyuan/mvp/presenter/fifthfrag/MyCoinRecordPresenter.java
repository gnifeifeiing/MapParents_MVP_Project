package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.CoinRecordBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.MyCoinRecordModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.MyCoinRecordView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      我的麦粒
 * Autour：          LF
 * Date：            2018/11/23 13:48
 */

public class MyCoinRecordPresenter extends BasePresenter<MyCoinRecordView> {


    private MyCoinRecordModel mMyCoinRecordModel;

    public MyCoinRecordPresenter() {
        this.mMyCoinRecordModel = new MyCoinRecordModel();
    }

    /**
     * 查看我的麦粒记录
     * @param parameter
     */
    public void getMailiList(Map<String, String> parameter){
        mMyCoinRecordModel.getMailiList(parameter, new PresenterCallBack<CoinRecordBean>(){

            @Override
            public void onSuccess(CoinRecordBean result) {
                if(getView()!=null){
                    getView().getMailiList(result);
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
