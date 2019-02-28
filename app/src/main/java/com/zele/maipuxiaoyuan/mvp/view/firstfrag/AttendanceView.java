package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.zele.maipuxiaoyuan.bean.AttenDanceBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      考勤
 * Autour：          LF
 * Date：            2018/11/15 17:15
 */

public interface AttendanceView extends BaseView {

    // 获取考勤数据
    void getAttendanceData(AttenDanceBean bean);
}
