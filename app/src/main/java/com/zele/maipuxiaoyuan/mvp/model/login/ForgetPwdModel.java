package com.zele.maipuxiaoyuan.mvp.model.login;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      忘记密码
 * Autour：          LF
 * Date：            2018/10/30 16:55
 */

public class ForgetPwdModel extends BaseModel {

    /**
     * 发送验证码
     * @param patameter
     * @param callBack
     */
    public void sendValidationCode(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.sendValidationCodeToForgetPwd(patameter);
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
     * 密码重置
     * @param patameter
     * @param callBack
     */
    public void resetPasswd(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.resetPasswd(patameter);
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
}
