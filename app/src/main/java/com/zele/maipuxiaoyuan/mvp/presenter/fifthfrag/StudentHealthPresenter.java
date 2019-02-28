package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.StuHealthBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.StudentHealthModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.StudentHealthView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      健康卡
 * Autour：          LF
 * Date：            2018/11/22 17:36
 */

public class StudentHealthPresenter extends BasePresenter<StudentHealthView> {

    private StudentHealthModel mStudentHealthModel;

    public StudentHealthPresenter() {
        this.mStudentHealthModel = new StudentHealthModel();
    }

    /**
     * 查看我的积分
     * @param parameter
     */
    public void getStuHealthData(Map<String, String> parameter){
        mStudentHealthModel.getStuHealthData(parameter, new PresenterCallBack<StuHealthBean>(){

            @Override
            public void onSuccess(StuHealthBean result) {
                if(getView()!=null){
                    getView().getStuHealthData(result);
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
