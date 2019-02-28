package com.zele.maipuxiaoyuan.mvp.view.login;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.bean.RealteBean;
import com.zele.maipuxiaoyuan.bean.RegisterTwoDataBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      绑定和学生的关系
 * Autour：          LF
 * Date：            2018/10/30 9:52
 */

public interface BindStudentRelationView extends BaseView {

    // 获取关系列表
    void getRelateList(RealteBean bean);

    // 绑定关系
    void bindStudentRelation(RegisterTwoDataBean bean);

    // 登录
    void onLoginResult(LoginUserBean bean);

    //获取绑定学生列表
    void BindStudentsBean(BindStudentsBean bean);

}
