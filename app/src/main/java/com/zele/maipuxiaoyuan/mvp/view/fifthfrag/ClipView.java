package com.zele.maipuxiaoyuan.mvp.view.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.UploadHeadPicBean;
import com.zele.maipuxiaoyuan.common.base.BaseView;

/**
 * Description:      图片剪裁
 * Autour：          LF
 * Date：            2018/11/24 10:52
 */

public interface ClipView extends BaseView {

    // 上传头像
    void upLoadHeadPic(UploadHeadPicBean bean);

    // 将图像与学生id绑定
    void submitStuPicInfo(BaseBean bean);
}
