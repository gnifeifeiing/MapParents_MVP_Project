package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateTagBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      星级评价
 * Autour：          LF
 * Date：            2018/12/12 14:34
 */

public class StartEvaluateModel extends BaseModel {

    /**
     * 获取TAG标签列表
     * @param patameter
     * @param callBack
     */
    public void getTagList(Map<String, String> patameter, final PresenterCallBack<EvaluateTagBean> callBack) {
        Call<EvaluateTagBean> call=mService.getTagList(patameter);
        call.enqueue(new MyCallBack<EvaluateTagBean>() {
            @Override
            public void onSuccess(EvaluateTagBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 综合评价评星详情根据班级请求数据
     * @param patameter
     * @param callBack
     */
    public void getStudHonors(Map<String, String> patameter, final PresenterCallBack<EvaluateRecordBean> callBack) {
        Call<EvaluateRecordBean> call=mService.getStudHonors(patameter);
        call.enqueue(new MyCallBack<EvaluateRecordBean>() {
            @Override
            public void onSuccess(EvaluateRecordBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
