package com.zele.maipuxiaoyuan.mvp.view.firstfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.MessageClassesListviewBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      作业
 * Autour：          LF
 * Date：            2018/11/17 14:17
 */

public interface BooksView extends BaseView {

    // 获取作业列表数据
    void getBooksData(MessageClassesListviewBean bean);

    // 设置当前信息为已读
    void setReadByMsgId(BaseBean bean,int position);
}
