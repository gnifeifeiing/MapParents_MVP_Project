package com.zele.maipuxiaoyuan.mvp.model.fourthfrag;

import com.zele.maipuxiaoyuan.bean.ClassCircleBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      班级圈
 * Autour：          LF
 * Date：            2018/11/21 16:56
 */

public class ClassCircleModel extends BaseModel {
    /**
     * 班级圈首页
     * @param patameter
     * @param callBack
     */
    public void getCircleData(Map<String, String> patameter, final PresenterCallBack<ClassCircleBean> callBack) {
        Call<ClassCircleBean> call=mService.getCircleData(patameter);
        call.enqueue(new MyCallBack<ClassCircleBean>() {
            @Override
            public void onSuccess(ClassCircleBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
