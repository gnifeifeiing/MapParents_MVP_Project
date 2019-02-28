package com.zele.maipuxiaoyuan.mvp.model.login;

import com.alibaba.fastjson.JSONObject;
import com.zele.maipuxiaoyuan.bean.BindStudentTeacherBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      绑定学生信息
 * Autour：          LF
 * Date：            2018/10/29 11:51
 */

public class BindStudentPhoneModel extends BaseModel {

    /**
     * 根据班主任号码查询班级信息
     * @param phone
     * @param callBack
     */
    public void getTeacherByPhone(String phone, final PresenterCallBack<BindStudentTeacherBean> callBack) {
        Call<BindStudentTeacherBean> call=mService.getTeacherByPhone(phone);
        call.enqueue(new MyCallBack<BindStudentTeacherBean>() {
            @Override
            public void onSuccess(BindStudentTeacherBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 验证学生身份证号是否正确
     * @param patameter
     * @param callBack
     */
    public void validationStudentIDCard(Map<String, String> patameter, final PresenterCallBack<JSONObject> callBack) {
        Call<JSONObject> call=mService.validationStudentIDCard(patameter);
        call.enqueue(new MyCallBack<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
