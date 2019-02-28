package com.zele.maipuxiaoyuan.mvp.presenter.login;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.LoginModel;
import com.zele.maipuxiaoyuan.mvp.view.login.LoginView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      登录
 * Autour：          LF
 * Date：            2018/8/6 14:49
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel mLoginModel;

    public LoginPresenter() {
        this.mLoginModel = new LoginModel();
    }

    /**
     * 登录
     * @param parameter
     */
    public void login(Map<String, String> parameter){
        mLoginModel.login(parameter, new PresenterCallBack<LoginUserBean>(){

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
        mLoginModel.getBindStudents(parameter, new PresenterCallBack<BindStudentsBean>(){

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
     * 通过qq或微信注册
     * @param parameter
     */
    public void loginByQqOrWx(Map<String, String> parameter){
        mLoginModel.LoginByQQOrWx(parameter, new PresenterCallBack<BindStudentsBean>(){

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
