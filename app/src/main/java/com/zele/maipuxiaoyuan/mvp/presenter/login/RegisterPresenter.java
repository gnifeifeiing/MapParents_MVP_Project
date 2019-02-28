package com.zele.maipuxiaoyuan.mvp.presenter.login;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.RegisterModel;
import com.zele.maipuxiaoyuan.mvp.view.login.RegisterView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/10/26 15:23
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private RegisterModel mRegisterModel;

    public RegisterPresenter() {
        this.mRegisterModel = new RegisterModel();
    }

    /**
     * 发送验证码
     * @param patameter
     */
    public void sendValidationCode(Map<String, String> patameter) {
        mRegisterModel.sendValidationCode(patameter, new PresenterCallBack<BaseBean>(){

            @Override
            public void onSuccess(BaseBean result) {
                if(getView()!=null){
                    getView().acceptValidationCode(result);
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
     * 注册
     * @param patameter
     */
    public void register(Map<String, String> patameter) {
        mRegisterModel.register(patameter, new PresenterCallBack<LoginUserBean>(){

            @Override
            public void onSuccess(LoginUserBean result) {
                if(getView()!=null){
                    getView().onRegisterResult(result);
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
