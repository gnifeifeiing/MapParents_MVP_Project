package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.alibaba.fastjson.JSONObject;
import com.zele.maipuxiaoyuan.bean.BannerBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.StudentInfoBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      firstFragment视图接口
 * Autour：          LF
 * Date：            2018/11/9 16:26
 */

public interface MainFirstFragView extends BaseView {

    // 获取轮播图数据
    void getBanner(BannerBean bean);

    // 获取学生详情
    void getStudentDetail(StudentInfoBean bean);

    // 获取绑定的学生列表
    void getBindStudents(BindStudentsBean bean);

    // 获取学生学期信息
    void getStudentTermsData(JSONObject data);

    // 获取家长好友列表
    void loadClassParents(JSONObject jsonObject);

    // 获取教师好友列表
    void loadClassTeacher(JSONObject jsonObject);
}
