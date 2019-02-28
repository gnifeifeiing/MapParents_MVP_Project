package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.HelpBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.HelpModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.HelpView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/22 16:30
 */

public class HelpPresenter extends BasePresenter<HelpView> {

    private HelpModel mHelpModel;

    public HelpPresenter() {
        this.mHelpModel = new HelpModel();
    }

    /**
     * 获取帮助列表
     * @param parameter
     */
    public void getHelpList(Map<String, String> parameter){
        mHelpModel.getHelpList(parameter, new PresenterCallBack<HelpBean>(){

            @Override
            public void onSuccess(HelpBean result) {
                if(getView()!=null){
                    getView().getHelpList(result);
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
