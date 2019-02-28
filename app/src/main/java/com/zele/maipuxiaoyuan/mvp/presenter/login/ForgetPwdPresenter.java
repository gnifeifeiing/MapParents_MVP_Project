package com.zele.maipuxiaoyuan.mvp.presenter.login;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.ForgetPwdModel;
import com.zele.maipuxiaoyuan.mvp.view.login.ForgetPwdView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      忘记密码
 * Autour：          LF
 * Date：            2018/10/30 16:55
 */

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView> {

    private ForgetPwdModel mForgetPwdModel;

    public ForgetPwdPresenter() {
        this.mForgetPwdModel = new ForgetPwdModel();
    }

    /**
     * 发送验证码
     * @param patameter
     */
    public void acceptValidationCode(Map<String, String> patameter){
        mForgetPwdModel.sendValidationCode( patameter,new PresenterCallBack<BaseBean>(){

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
     * 密码重置
     * @param patameter
     */
    public void resetPasswd(Map<String, String> patameter){
        mForgetPwdModel.resetPasswd( patameter,new PresenterCallBack<BaseBean>(){

            @Override
            public void onSuccess(BaseBean result) {
                if(getView()!=null){
                    getView().resetPasswd(result);
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
