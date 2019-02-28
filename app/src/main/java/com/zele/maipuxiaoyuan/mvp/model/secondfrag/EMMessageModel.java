package com.zele.maipuxiaoyuan.mvp.model.secondfrag;

import com.zele.maipuxiaoyuan.bean.MessageBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      环信消息
 * Autour：          LF
 * Date：            2018/12/17 10:44
 */

public class EMMessageModel extends BaseModel {

    /**
     * 环信最新消息
     * @param patameter
     * @param callBack
     */
    public void getMsgList(Map<String, String> patameter, final PresenterCallBack<MessageBean> callBack) {
        Call<MessageBean> call=mService.getMsgList(patameter);
        call.enqueue(new MyCallBack<MessageBean>() {
            @Override
            public void onSuccess(MessageBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

}
