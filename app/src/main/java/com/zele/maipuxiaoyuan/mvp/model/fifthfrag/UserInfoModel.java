package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.RelationVoBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      用户信息
 * Autour：          LF
 * Date：            2018/11/27 15:58
 */

public class UserInfoModel extends BaseModel {

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

    /**
     * 获取关系列表
     * @param callBack
     */
    public void getRelationList( final PresenterCallBack<RelationVoBean> callBack) {
        Call<RelationVoBean> call=mService.getRelationList();
        call.enqueue(new MyCallBack<RelationVoBean>() {
            @Override
            public void onSuccess(RelationVoBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 保存家长信息
     * @param patameter
     * @param callBack
     */
    public void saveParentInfo(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.saveParentInfo(patameter);
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
