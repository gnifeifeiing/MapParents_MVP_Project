package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.zele.maipuxiaoyuan.bean.AttenDanceBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.AttendanceModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.AttendanceView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      考勤
 * Autour：          LF
 * Date：            2018/11/15 17:14
 */

public class AttendancePresenter extends BasePresenter<AttendanceView> {

    private AttendanceModel mAttendanceModel;

    public AttendancePresenter() {
        this.mAttendanceModel = new AttendanceModel();
    }

    /**
     * 获取考勤数据
     * @param parameter
     */
    public void getAttendanceData(Map<String, String> parameter){
        mAttendanceModel.getAttendanceData(parameter, new PresenterCallBack<AttenDanceBean>(){

            @Override
            public void onSuccess(AttenDanceBean result) {
                if(getView()!=null){
                    getView().getAttendanceData(result);
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
