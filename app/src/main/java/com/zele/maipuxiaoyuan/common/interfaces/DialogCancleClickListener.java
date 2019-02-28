package com.zele.maipuxiaoyuan.common.interfaces;

import android.app.Dialog;

/**
 * Description:      弹出框取消按钮接口
 * Autour：          LF
 * Date：            2018/11/7 10:42
 */

public interface DialogCancleClickListener<T extends Dialog> {
    void onDialogCancleClickListener(T dialog);
}
