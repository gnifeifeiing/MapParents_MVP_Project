package com.zele.maipuxiaoyuan.mvp.view.login;

import com.alibaba.fastjson.JSONObject;
import com.zele.maipuxiaoyuan.bean.BindStudentTeacherBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;



/**
 * Description:      绑定学生
 * Autour：          LF
 * Date：            2018/10/29 11:48
 */

public interface BindStudentPhoneView extends BaseView {

    // 根据号码获取绑定的教师信息
    void getTeacherByPhone(BindStudentTeacherBean bean);

    // 验证学生身份证号是否正确
    void validationStudentIDCard(JSONObject content);

}
