package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.zele.maipuxiaoyuan.bean.GroupRecordBean;
import com.zele.maipuxiaoyuan.bean.GrowColumDataBean;
import com.zele.maipuxiaoyuan.bean.RadarDataBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.GrowthHistoryModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.GrowthHistoryView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      成长历程
 * Autour：          LF
 * Date：            2018/11/19 11:49
 */

public class GrowthHistoryPresenter extends BasePresenter<GrowthHistoryView> {

    private GrowthHistoryModel mGrowthHistoryModel;

    public GrowthHistoryPresenter() {
        this.mGrowthHistoryModel = new GrowthHistoryModel();
    }

    /**
     * 获取雷达图数据
     * @param parameter
     */
    public void getRadarData(Map<String, String> parameter){
        mGrowthHistoryModel.getRadarData(parameter, new PresenterCallBack<RadarDataBean>(){

            @Override
            public void onSuccess(RadarDataBean result) {
                if(getView()!=null){
                    getView().getRadarData(result);
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
     * 成长总览柱状图
     * @param parameter
     */
    public void getColumData(Map<String, String> parameter){
        mGrowthHistoryModel.getColumData(parameter, new PresenterCallBack<GrowColumDataBean>(){

            @Override
            public void onSuccess(GrowColumDataBean result) {
                if(getView()!=null){
                    getView().getColumData(result);
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
     * 成长总览列表
     * @param parameter
     */
    public void getGroupRecord(Map<String, String> parameter){
        mGrowthHistoryModel.getGroupRecord(parameter, new PresenterCallBack<GroupRecordBean>(){

            @Override
            public void onSuccess(GroupRecordBean result) {
                if(getView()!=null){
                    getView().getGroupRecord(result);
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
