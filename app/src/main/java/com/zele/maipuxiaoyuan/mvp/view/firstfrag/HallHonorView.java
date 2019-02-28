package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.zele.maipuxiaoyuan.bean.HallHonorListBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      荣誉奖状
 * Autour：          LF
 * Date：            2018/11/13 17:46
 */

public interface HallHonorView extends BaseView {

    // 获取荣誉奖状数据
    void getHallHonorData(HallHonorListBean bean);
}
