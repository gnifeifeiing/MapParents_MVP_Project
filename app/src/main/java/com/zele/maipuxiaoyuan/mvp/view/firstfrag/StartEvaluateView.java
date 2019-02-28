package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateTagBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      星级评价
 * Autour：          LF
 * Date：            2018/12/12 14:39
 */

public interface StartEvaluateView extends BaseView {

    // 获取TAG标签列表
    void getTagList(EvaluateTagBean bean);

    // 综合评价评星详情根据班级请求数据
    void getStudHonors(EvaluateRecordBean bean);
}
