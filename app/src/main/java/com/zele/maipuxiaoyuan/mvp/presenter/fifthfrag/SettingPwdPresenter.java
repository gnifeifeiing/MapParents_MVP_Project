package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.SettingPwdModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.SettingPwdView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      修改密码
 * Autour：          LF
 * Date：            2018/11/24 14:31
 */

public class SettingPwdPresenter extends BasePresenter<SettingPwdView> {

    private SettingPwdModel mSettingPwdModel;

    public SettingPwdPresenter() {
        this.mSettingPwdModel = new SettingPwdModel();
    }

    /**
     * 修改密码
     * @param parameter
     */
    public void updatePwd(Map<String, String> parameter){
        mSettingPwdModel.updatePwd(parameter, new PresenterCallBack<BaseBean>(){

            @Override
            public void onSuccess(BaseBean result) {
                if(getView()!=null){
                    getView().updatePwd(result);
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
