package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      修改密码
 * Autour：          LF
 * Date：            2018/11/24 14:28
 */

public class SettingPwdModel extends BaseModel {

    /**
     * 修改密码
     * @param patameter
     * @param callBack
     */
    public void updatePwd(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.updatePwd(patameter);
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
