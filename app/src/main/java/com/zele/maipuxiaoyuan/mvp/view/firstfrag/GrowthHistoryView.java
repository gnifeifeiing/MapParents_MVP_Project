package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.zele.maipuxiaoyuan.bean.GroupRecordBean;
import com.zele.maipuxiaoyuan.bean.GrowColumDataBean;
import com.zele.maipuxiaoyuan.bean.RadarDataBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      成长历程
 * Autour：          LF
 * Date：            2018/11/19 11:48
 */

public interface GrowthHistoryView extends BaseView {

    // 获取雷达图数据
    void getRadarData(RadarDataBean bean);

    // 成长总览柱状图
    void getColumData(GrowColumDataBean bean);

    // 成长总览列表
    void getGroupRecord(GroupRecordBean bean);
}
