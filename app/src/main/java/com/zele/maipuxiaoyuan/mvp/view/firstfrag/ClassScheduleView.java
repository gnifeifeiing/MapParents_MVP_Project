package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.zele.maipuxiaoyuan.bean.ScheduleBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      课程表
 * Autour：          LF
 * Date：            2018/12/10 14:42
 */

public interface ClassScheduleView extends BaseView {

    // 获取课程表数据
    void getClassScheduleData(ScheduleBean bean);

}
