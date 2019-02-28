package com.zele.maipuxiaoyuan.common.interfaces;

import android.app.Dialog;

/**
 * Description:      弹出框确定按钮接口
 * Autour：          LF
 * Date：            2018/11/7 10:43
 */

public interface DialogConfirmClickListener<T extends Dialog> {
    void onDialogConfirmClickListener(T dialog);
}
