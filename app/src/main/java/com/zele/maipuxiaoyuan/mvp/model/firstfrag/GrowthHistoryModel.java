package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.zele.maipuxiaoyuan.bean.GroupRecordBean;
import com.zele.maipuxiaoyuan.bean.GrowColumDataBean;
import com.zele.maipuxiaoyuan.bean.RadarDataBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      成长历程
 * Autour：          LF
 * Date：            2018/11/19 11:44
 */

public class GrowthHistoryModel extends BaseModel {

    /**
     * 获取雷达图数据
     * @param patameter
     * @param callBack
     */
    public void getRadarData(Map<String, String> patameter, final PresenterCallBack<RadarDataBean> callBack) {
        Call<RadarDataBean> call=mService.getRadarData(patameter);
        call.enqueue(new MyCallBack<RadarDataBean>() {
            @Override
            public void onSuccess(RadarDataBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 成长总览柱状图
     * @param patameter
     * @param callBack
     */
    public void getColumData(Map<String, String> patameter, final PresenterCallBack<GrowColumDataBean> callBack) {
        Call<GrowColumDataBean> call=mService.getColumData(patameter);
        call.enqueue(new MyCallBack<GrowColumDataBean>() {
            @Override
            public void onSuccess(GrowColumDataBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 成长总览列表
     * @param patameter
     * @param callBack
     */
    public void getGroupRecord(Map<String, String> patameter, final PresenterCallBack<GroupRecordBean> callBack) {
        Call<GroupRecordBean> call=mService.getGroupRecord(patameter);
        call.enqueue(new MyCallBack<GroupRecordBean>() {
            @Override
            public void onSuccess(GroupRecordBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
