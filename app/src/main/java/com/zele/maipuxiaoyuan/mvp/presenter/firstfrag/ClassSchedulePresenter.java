package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.zele.maipuxiaoyuan.bean.ScheduleBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.ClassScheduleModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.ClassScheduleView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      课程表
 * Autour：          LF
 * Date：            2018/12/10 14:42
 */

public class ClassSchedulePresenter extends BasePresenter<ClassScheduleView> {

    private ClassScheduleModel mClassScheduleModel;

    public ClassSchedulePresenter() {
        this.mClassScheduleModel = new ClassScheduleModel();
    }

    /**
     * 获取考勤数据
     * @param parameter
     */
    public void getClassScheduleData(Map<String, String> parameter){
        mClassScheduleModel.getClassScheduleData(parameter, new PresenterCallBack<ScheduleBean>(){

            @Override
            public void onSuccess(ScheduleBean result) {
                if(getView()!=null){
                    getView().getClassScheduleData(result);
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
