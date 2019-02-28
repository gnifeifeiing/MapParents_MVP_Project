package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.zele.maipuxiaoyuan.bean.HallHonorListBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/13 17:53
 */

public class HallHonorModel extends BaseModel {


    /**
     * 获取荣誉奖状数据
     * @param patameter
     * @param callBack
     */
    public void getHallHonorData(Map<String, String> patameter, final PresenterCallBack<HallHonorListBean> callBack) {
        Call<HallHonorListBean> call=mService.getHallHonorData(patameter);
        call.enqueue(new MyCallBack<HallHonorListBean>() {
            @Override
            public void onSuccess(HallHonorListBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
