package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.CoinRecordBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      我的麦粒
 * Autour：          LF
 * Date：            2018/11/23 13:46
 */

public class MyCoinRecordModel extends BaseModel {


    /**
     * 查看我的麦粒记录
     * @param patameter
     * @param callBack
     */
    public void getMailiList(Map<String, String> patameter, final PresenterCallBack<CoinRecordBean> callBack) {
        Call<CoinRecordBean> call=mService.getMailiList(patameter);
        call.enqueue(new MyCallBack<CoinRecordBean>() {
            @Override
            public void onSuccess(CoinRecordBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
