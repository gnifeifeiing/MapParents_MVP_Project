package com.zele.maipuxiaoyuan.mvp.view.login;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      注册
 * Autour：          LF
 * Date：            2018/10/26 15:22
 */

public interface RegisterView extends BaseView{

    //接受返回验证码
    void acceptValidationCode(BaseBean bean);

    //接受返回验证码
    void onRegisterResult(LoginUserBean bean);
}
