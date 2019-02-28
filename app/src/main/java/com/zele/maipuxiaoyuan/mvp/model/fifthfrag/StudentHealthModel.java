package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.StuHealthBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      健康卡
 * Autour：          LF
 * Date：            2018/11/22 17:33
 */

public class StudentHealthModel extends BaseModel {


    /**
     * 获取学生当前学期健康数据
     * @param patameter
     * @param callBack
     */
    public void getStuHealthData(Map<String, String> patameter, final PresenterCallBack<StuHealthBean> callBack) {
        Call<StuHealthBean> call=mService.getStuHealthData(patameter);
        call.enqueue(new MyCallBack<StuHealthBean>() {
            @Override
            public void onSuccess(StuHealthBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
