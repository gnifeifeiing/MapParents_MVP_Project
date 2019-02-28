package com.zele.maipuxiaoyuan.mvp.view.login;


import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      登录
 * Autour：          LF
 * Date：            2018/8/6 14:54
 */

public interface LoginView extends BaseView {
    //登录请求
    void onLoginResult(LoginUserBean result);

    //获取绑定学生列表
    void BindStudentsBean(BindStudentsBean bean);

}
