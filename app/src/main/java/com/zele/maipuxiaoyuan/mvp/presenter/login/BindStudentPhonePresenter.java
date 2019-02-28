package com.zele.maipuxiaoyuan.mvp.presenter.login;

import com.alibaba.fastjson.JSONObject;
import com.zele.maipuxiaoyuan.bean.BindStudentTeacherBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.login.BindStudentPhoneModel;
import com.zele.maipuxiaoyuan.mvp.view.login.BindStudentPhoneView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      绑定学生
 * Autour：          LF
 * Date：            2018/10/29 11:50
 */

public class BindStudentPhonePresenter extends BasePresenter<BindStudentPhoneView> {

    private BindStudentPhoneModel mBindStudentPhoneModel;

    public BindStudentPhonePresenter() {
        this.mBindStudentPhoneModel = new BindStudentPhoneModel();
    }

    /**
     * 根据班主任号码查询班级信息
     * @param phone
     */
    public void getTeacherByPhone(String phone){
        mBindStudentPhoneModel.getTeacherByPhone(phone, new PresenterCallBack<BindStudentTeacherBean>(){

            @Override
            public void onSuccess(BindStudentTeacherBean result) {
                if(getView()!=null){
                    getView().getTeacherByPhone(result);
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
     * 验证学生身份证号是否正确
     * @param patameter
     */
    public void validationStudentIDCard(Map<String, String> patameter){
        mBindStudentPhoneModel.validationStudentIDCard(patameter, new PresenterCallBack<JSONObject>(){

            @Override
            public void onSuccess(JSONObject result) {
                if(getView()!=null){
                    getView().validationStudentIDCard(result);
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
