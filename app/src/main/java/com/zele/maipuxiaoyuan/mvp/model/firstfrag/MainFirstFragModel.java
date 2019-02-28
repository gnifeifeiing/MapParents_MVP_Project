package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.alibaba.fastjson.JSONObject;
import com.zele.maipuxiaoyuan.bean.BannerBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.StudentInfoBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      MainFirstFrag
 * Autour：          LF
 * Date：            2018/11/9 16:29
 */

public class MainFirstFragModel extends BaseModel {

    /**
     * 获取banner图
     * @param patameter
     * @param callBack
     */
    public void getBanner(Map<String, String> patameter, final PresenterCallBack<BannerBean> callBack) {
        Call<BannerBean> call=mService.getBannner(patameter);
        call.enqueue(new MyCallBack<BannerBean>() {
            @Override
            public void onSuccess(BannerBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 获取学生详情
     * @param patameter
     * @param callBack
     */
    public void getStudentDetail(Map<String, String> patameter, final PresenterCallBack<StudentInfoBean> callBack) {
        Call<StudentInfoBean> call=mService.getStudentDetail(patameter);
        call.enqueue(new MyCallBack<StudentInfoBean>() {
            @Override
            public void onSuccess(StudentInfoBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 获取学生学期信息
     * @param patameter
     * @param callBack
     */
    public void getStudentTermsData(Map<String, String> patameter, final PresenterCallBack<JSONObject> callBack) {
        Call<JSONObject> call=mService.getStudentTermsData(patameter);
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

    /**
     * 获取绑定的学生列表
     * @param patameter
     * @param callBack
     */
    public void getBindStudents(Map<String, String> patameter, final PresenterCallBack<BindStudentsBean> callBack) {
        Call<BindStudentsBean> call=mService.getBindStudents(patameter);
        call.enqueue(new MyCallBack<BindStudentsBean>() {
            @Override
            public void onSuccess(BindStudentsBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 获取家长好友列表
     * @param patameter
     * @param callBack
     */
    public void loadClassParents(Map<String, String> patameter, final PresenterCallBack<JSONObject> callBack) {
        Call<JSONObject> call=mService.loadClassParents(patameter);
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

    /**
     * 获取教师好友列表
     * @param patameter
     * @param callBack
     */
    public void loadClassTeacher(Map<String, String> patameter, final PresenterCallBack<JSONObject> callBack) {
        Call<JSONObject> call=mService.loadClassTeacher(patameter);
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
