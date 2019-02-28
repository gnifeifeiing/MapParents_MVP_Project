package com.zele.maipuxiaoyuan.mvp.view.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.ScoreBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      个人中心
 * Autour：          LF
 * Date：            2018/11/21 11:21
 */

public interface PersonalFragView extends BaseView {

    // 查看我的积分
    void getScore(ScoreBean bean);

    // 获取绑定学生列表
    void BindStudentsBean(BindStudentsBean bean);

    // 获取家长信息
    void getParentInfo(ParentMessageBean bean);
}
