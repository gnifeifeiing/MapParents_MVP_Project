package com.zele.maipuxiaoyuan.mvp.view.fourthfrag;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/14 11:14
 */

public interface StudentCardsView extends BaseView {

    // 获取绑定学生列表
    void getBindStudents(BindStudentsBean bean);
}
