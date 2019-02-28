package com.zele.maipuxiaoyuan.mvp.presenter.login;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.bean.RealteBean;
import com.zele.maipuxiaoyuan.bean.RegisterTwoDataBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.BindStudentRelationModel;
import com.zele.maipuxiaoyuan.mvp.view.login.BindStudentRelationView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      绑定和学生的关系
 * Autour：          LF
 * Date：            2018/10/30 9:53
 */

public class BindStudentRelationPresenter extends BasePresenter<BindStudentRelationView> {

    private BindStudentRelationModel mBindStudentRelationModel;

    public BindStudentRelationPresenter() {
        this.mBindStudentRelationModel = new BindStudentRelationModel();
    }

    /**
     * 获取关系列表
     */
    public void getRelateList(){
        mBindStudentRelationModel.getRelateList( new PresenterCallBack<RealteBean>(){

            @Override
            public void onSuccess(RealteBean result) {
                if(getView()!=null){
                    getView().getRelateList(result);
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
     * 绑定学生关系
     * @param patameter
     */
    public void bindStudentRelation(Map<String, String> patameter){
        mBindStudentRelationModel.bindStudentRelation(patameter, new PresenterCallBack<RegisterTwoDataBean>(){

            @Override
            public void onSuccess(RegisterTwoDataBean result) {
                if(getView()!=null){
                    getView().bindStudentRelation(result);
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
     * 登录
     * @param parameter
     */
    public void login(Map<String, String> parameter){
        mBindStudentRelationModel.login(parameter, new PresenterCallBack<LoginUserBean>(){

            @Override
            public void onSuccess(LoginUserBean result) {
                if(getView()!=null){
                    getView().onLoginResult(result);
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
        mBindStudentRelationModel.getBindStudents(parameter, new PresenterCallBack<BindStudentsBean>(){

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
}
