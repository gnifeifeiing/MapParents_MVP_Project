package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.ScoreBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      个人中心
 * Autour：          LF
 * Date：            2018/11/21 11:13
 */

public class PersonalFragModel extends BaseModel {

    /**
     * 查看我的积分
     * @param patameter
     * @param callBack
     */
    public void getScore(Map<String, String> patameter, final PresenterCallBack<ScoreBean> callBack) {
        Call<ScoreBean> call=mService.getScore(patameter);
        call.enqueue(new MyCallBack<ScoreBean>() {
            @Override
            public void onSuccess(ScoreBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }


    /**
     * 获取绑定学生列表
     * @param patameter
     * @param callBack
     */
    public void getBindStudents(Map<String, String> patameter, final PresenterCallBack<BindStudentsBean> callBack) {
        Call<BindStudentsBean> call=mService.getBindStudents(patameter);
        call.enqueue(new MyCallBack<BindStudentsBean>() {
            @Override
            public void onSuccess(BindStudentsBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 获取家长信息
     * @param patameter
     * @param callBack
     */
    public void getParentInfo(Map<String, String> patameter, final PresenterCallBack<ParentMessageBean> callBack) {
        Call<ParentMessageBean> call=mService.getParentInfo(patameter);
        call.enqueue(new MyCallBack<ParentMessageBean>() {
            @Override
            public void onSuccess(ParentMessageBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
