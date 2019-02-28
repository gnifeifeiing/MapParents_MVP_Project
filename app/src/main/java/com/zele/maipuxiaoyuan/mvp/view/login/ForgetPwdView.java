package com.zele.maipuxiaoyuan.mvp.view.login;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      忘记密码
 * Autour：          LF
 * Date：            2018/10/30 16:54
 */

public interface ForgetPwdView extends BaseView {

    // 发送验证码
    void acceptValidationCode(BaseBean bean);

    // 密码重置
    void resetPasswd(BaseBean bean);
}
