package com.zele.maipuxiaoyuan.mvp.model.login;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      注册
 * Autour：          LF
 * Date：            2018/10/26 15:24
 */

public class RegisterModel extends BaseModel {

    /**
     * 发送验证码
     * @param patameter
     * @param callBack
     */
    public void sendValidationCode(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.sendValidationCode(patameter);
        call.enqueue(new MyCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 注册
     * @param patameter
     * @param callBack
     */
    public void register(Map<String, String> patameter, final PresenterCallBack<LoginUserBean> callBack) {
        Call<LoginUserBean> call=mService.register(patameter);
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
}
