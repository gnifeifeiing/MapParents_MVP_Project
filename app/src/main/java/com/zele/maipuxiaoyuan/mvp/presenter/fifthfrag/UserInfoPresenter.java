package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.RelationVoBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.UserInfoModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.UserInfoView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      用户信息
 * Autour：          LF
 * Date：            2018/11/27 16:02
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView> {


    private UserInfoModel mUserInfoModel;

    public UserInfoPresenter() {
        this.mUserInfoModel = new UserInfoModel();
    }

    /**
     * 查看我的麦粒记录
     * @param parameter
     */
    public void getParentInfo(Map<String, String> parameter){
        mUserInfoModel.getParentInfo(parameter, new PresenterCallBack<ParentMessageBean>(){

            @Override
            public void onSuccess(ParentMessageBean result) {
                if(getView()!=null){
                    getView().getParentInfo(result);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if(getView()!=null){
                    getView().onError(errMsg);
                }
            }
        });
    }

    /**
     * 获取关系列表
     */
    public void getRelationList(){
        mUserInfoModel.getRelationList( new PresenterCallBack<RelationVoBean>(){

            @Override
            public void onSuccess(RelationVoBean result) {
                if(getView()!=null){
                    getView().getRelationList(result);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if(getView()!=null){
                    getView().onError(errMsg);
                }
            }
        });
    }

    /**
     * 保存家长信息
     * @param parameter
     */
    public void saveParentInfo(Map<String, String> parameter){
        mUserInfoModel.saveParentInfo(parameter, new PresenterCallBack<BaseBean>(){

            @Override
            public void onSuccess(BaseBean result) {
                if(getView()!=null){
                    getView().saveParentInfo(result);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if(getView()!=null){
                    getView().onError(errMsg);
                }
            }
        });
    }
}
