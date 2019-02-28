package com.zele.maipuxiaoyuan.mvp.model.login;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.bean.RealteBean;
import com.zele.maipuxiaoyuan.bean.RegisterTwoDataBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      绑定和学生的关系
 * Autour：          LF
 * Date：            2018/10/30 9:54
 */

public class BindStudentRelationModel extends BaseModel {

    /**
     * 获取关系列表
     * @param callBack
     */
    public void getRelateList(final PresenterCallBack<RealteBean> callBack) {
        Call<RealteBean> call=mService.getRelateList();
        call.enqueue(new MyCallBack<RealteBean>() {
            @Override
            public void onSuccess(RealteBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 绑定学生关系
     * @param patameter
     * @param callBack
     */
    public void bindStudentRelation(Map<String, String> patameter, final PresenterCallBack<RegisterTwoDataBean> callBack) {
        Call<RegisterTwoDataBean> call=mService.bindStudentRelation(patameter);
        call.enqueue(new MyCallBack<RegisterTwoDataBean>() {
            @Override
            public void onSuccess(RegisterTwoDataBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 登录
     * @param patameter
     * @param callBack
     */
    public void login(Map<String, String> patameter, final PresenterCallBack<LoginUserBean> callBack) {
        Call<LoginUserBean> call=mService.login(patameter);
        call.enqueue(new MyCallBack<LoginUserBean>() {
            @Override
            public void onSuccess(LoginUserBean response) {
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
}
