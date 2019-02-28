package com.zele.maipuxiaoyuan.mvp.presenter.fourthfrag;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.LoginModel;
import com.zele.maipuxiaoyuan.mvp.view.fourthfrag.StudentCardsView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/14 11:13
 */

public class StudentCardsPresenter extends BasePresenter<StudentCardsView> {


    private LoginModel mLoginModel;

    public StudentCardsPresenter() {
        this.mLoginModel = new LoginModel();
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
                    getView().getBindStudents(result);
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
