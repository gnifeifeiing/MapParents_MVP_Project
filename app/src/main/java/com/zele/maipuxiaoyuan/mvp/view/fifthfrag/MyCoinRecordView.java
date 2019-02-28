package com.zele.maipuxiaoyuan.mvp.view.fifthfrag;

import com.zele.maipuxiaoyuan.bean.CoinRecordBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      我的麦粒
 * Autour：          LF
 * Date：            2018/11/23 13:48
 */

public interface MyCoinRecordView extends BaseView {

    // 查看我的麦粒记录
    void getMailiList(CoinRecordBean bean);
}
