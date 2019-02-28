package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.alibaba.fastjson.JSONObject;
import com.zele.maipuxiaoyuan.bean.BannerBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.StudentInfoBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.MainFirstFragModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.MainFirstFragView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      MainFirstFrag
 * Autour：          LF
 * Date：            2018/11/9 16:28
 */

public class MainFirstFragPresenter extends BasePresenter<MainFirstFragView> {

    private MainFirstFragModel mMainFirstFragModel;

    public MainFirstFragPresenter() {
        this.mMainFirstFragModel = new MainFirstFragModel();
    }

    /**
     * 获取banner图
     * @param parameter
     */
    public void getBanner(Map<String, String> parameter){
        mMainFirstFragModel.getBanner(parameter, new PresenterCallBack<BannerBean>(){

            @Override
            public void onSuccess(BannerBean result) {
                if(getView()!=null){
                    getView().getBanner(result);
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
     * 获取学生详情
     * @param parameter
     */
    public void getStudentDetail(Map<String, String> parameter){
        mMainFirstFragModel.getStudentDetail(parameter, new PresenterCallBack<StudentInfoBean>(){

            @Override
            public void onSuccess(StudentInfoBean result) {
                if(getView()!=null){
                    getView().getStudentDetail(result);
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
     * 获取学生学期信息
     * @param parameter
     */
    public void getStudentTermsData(Map<String, String> parameter){
        mMainFirstFragModel.getStudentTermsData(parameter, new PresenterCallBack<JSONObject>(){

            @Override
            public void onSuccess(JSONObject result) {
                if(getView()!=null){
                    getView().getStudentTermsData(result);
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
     * 获取绑定的学生列表
     * @param parameter
     */
    public void getBindStudents(Map<String, String> parameter){
        mMainFirstFragModel.getBindStudents(parameter, new PresenterCallBack<BindStudentsBean>(){

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

    /**
     * 获取家长好友列表
     * @param parameter
     */
    public void loadClassParents(Map<String, String> parameter){
        mMainFirstFragModel.loadClassParents(parameter, new PresenterCallBack<JSONObject>(){

            @Override
            public void onSuccess(JSONObject result) {
                if(getView()!=null){
                    getView().loadClassParents(result);
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
     * 获取教师好友列表
     * @param parameter
     */
    public void loadClassTeacher(Map<String, String> parameter){
        mMainFirstFragModel.loadClassTeacher(parameter, new PresenterCallBack<JSONObject>(){

            @Override
            public void onSuccess(JSONObject result) {
                if(getView()!=null){
                    getView().loadClassParents(result);
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
