package com.zele.maipuxiaoyuan.mvp.view.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.RelationVoBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      用户信息
 * Autour：          LF
 * Date：            2018/11/27 16:03
 */

public interface UserInfoView extends BaseView {

    // 获取家长信息
    void getParentInfo(ParentMessageBean bean);

    // 获取关系列表
    void getRelationList(RelationVoBean bean);

    // 保存家长信息
    void saveParentInfo(BaseBean bean);
}
