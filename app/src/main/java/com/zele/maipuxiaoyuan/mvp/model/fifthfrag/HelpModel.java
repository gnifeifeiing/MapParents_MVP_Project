package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.HelpBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/22 16:28
 */

public class HelpModel extends BaseModel {

    /**
     * 查看我的积分
     * @param patameter
     * @param callBack
     */
    public void getHelpList(Map<String, String> patameter, final PresenterCallBack<HelpBean> callBack) {
        Call<HelpBean> call=mService.getHelpList(patameter);
        call.enqueue(new MyCallBack<HelpBean>() {
            @Override
            public void onSuccess(HelpBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
