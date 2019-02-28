package com.zele.maipuxiaoyuan.mvp.model.login;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      登录
 * Autour：          LF
 * Date：            2018/8/6 14:44
 */

public class LoginModel extends BaseModel {

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

    /**
     * 通过qq或微信注册
     * @param patameter
     * @param callBack
     */
    public void LoginByQQOrWx(Map<String, String> patameter, final PresenterCallBack<BindStudentsBean> callBack){
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
