package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.zele.maipuxiaoyuan.bean.AttenDanceBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      考勤
 * Autour：          LF
 * Date：            2018/11/15 17:11
 */

public class AttendanceModel extends BaseModel {
    /**
     * 获取考勤数据
     * @param patameter
     * @param callBack
     */
    public void getAttendanceData(Map<String, String> patameter, final PresenterCallBack<AttenDanceBean> callBack) {
        Call<AttenDanceBean> call=mService.getAttendanceData(patameter);
        call.enqueue(new MyCallBack<AttenDanceBean>() {
            @Override
            public void onSuccess(AttenDanceBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
