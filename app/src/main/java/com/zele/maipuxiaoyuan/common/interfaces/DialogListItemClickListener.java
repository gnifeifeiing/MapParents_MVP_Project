package com.zele.maipuxiaoyuan.common.interfaces;

import android.app.Dialog;

/**
 * Description:      listview弹框item选择接口
 * Autour：          LF
 * Date：            2018/11/13 14:17
 */

public interface DialogListItemClickListener<T extends Dialog> {
    void onDialogListItemClickListener(T dialog,int position);
}
