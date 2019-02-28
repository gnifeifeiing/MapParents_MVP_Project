package com.zele.maipuxiaoyuan.mvp.view.fifthfrag;

import com.zele.maipuxiaoyuan.bean.StuHealthBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      健康卡
 * Autour：          LF
 * Date：            2018/11/22 17:36
 */

public interface StudentHealthView extends BaseView {

    // 获取学生当前学期健康数据
    void getStuHealthData(StuHealthBean bean);
}
