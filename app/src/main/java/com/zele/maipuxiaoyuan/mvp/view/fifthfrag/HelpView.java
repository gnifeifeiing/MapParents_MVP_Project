package com.zele.maipuxiaoyuan.mvp.view.fifthfrag;

import com.zele.maipuxiaoyuan.bean.HelpBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      帮助与反馈
 * Autour：          LF
 * Date：            2018/11/22 16:30
 */

public interface HelpView extends BaseView {

    // 获取帮助列表
    void getHelpList(HelpBean bean);
}
