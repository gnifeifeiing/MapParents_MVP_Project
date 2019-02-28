package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.zele.maipuxiaoyuan.bean.ScheduleBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      课程表
 * Autour：          LF
 * Date：            2018/12/10 14:38
 */

public class ClassScheduleModel extends BaseModel {

    /**
     * 获取课程表数据
     * @param patameter
     * @param callBack
     */
    public void getClassScheduleData(Map<String, String> patameter, final PresenterCallBack<ScheduleBean> callBack) {
        Call<ScheduleBean> call=mService.getClassScheduleData(patameter);
        call.enqueue(new MyCallBack<ScheduleBean>() {
            @Override
            public void onSuccess(ScheduleBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
