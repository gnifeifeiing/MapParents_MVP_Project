package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.ScoreBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.PersonalFragModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.PersonalFragView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      个人中心
 * Autour：          LF
 * Date：            2018/11/21 11:20
 */

public class PersonalFragPresenter extends BasePresenter<PersonalFragView> {


    private PersonalFragModel mPersonalFragModel;

    public PersonalFragPresenter() {
        this.mPersonalFragModel = new PersonalFragModel();
    }

    /**
     * 查看我的积分
     * @param parameter
     */
    public void getScore(Map<String, String> parameter){
        mPersonalFragModel.getScore(parameter, new PresenterCallBack<ScoreBean>(){

            @Override
            public void onSuccess(ScoreBean result) {
                if(getView()!=null){
                    getView().getScore(result);
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
     * 获取绑定学生列表
     * @param parameter
     */
    public void getBindStudents(Map<String, String> parameter){
        mPersonalFragModel.getBindStudents(parameter, new PresenterCallBack<BindStudentsBean>(){

            @Override
            public void onSuccess(BindStudentsBean result) {
                if(getView()!=null){
                    getView().BindStudentsBean(result);
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
     * 获取家长信息
     * @param parameter
     */
    public void getParentInfo(Map<String, String> parameter){
        mPersonalFragModel.getParentInfo(parameter, new PresenterCallBack<ParentMessageBean>(){

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
}
