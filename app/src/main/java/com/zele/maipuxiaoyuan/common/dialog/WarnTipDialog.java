package com.zele.maipuxiaoyuan.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;

/**
 * Description:      警告弹框
 * Autour：          LF
 * Date：            2018/11/14 9:27
 */

public class WarnTipDialog extends Dialog {

    Activity activity;
    private Button confirmBtn;
    private TextView contentTv;

    private String confirmStr;
    private String contentStr;
    //点击回调
    private DialogConfirmClickListener clickConfirmListener;

    public WarnTipDialog(@NonNull Context context, int themeResId, Builder builder) {
        super(context, themeResId);
        this.confirmStr = builder.confirmStr;
        this.contentStr = builder.contentStr;
        this.clickConfirmListener = builder.clickConfirmListener;
    }

    public WarnTipDialog(Activity activity, Builder builder) {
        this(activity, builder.themeResId, builder);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_warn_tip);
        initView();
    }


    private void initView() {
        confirmBtn = findViewById(R.id.confirmBtn);

        if (this.confirmStr != null && !TextUtils.isEmpty(this.confirmStr)) {
            confirmBtn.setText(this.confirmStr);
        } else {
            confirmBtn.setText("确定");
        }

        contentTv = findViewById(R.id.contentTv);
        contentTv.setText(Html.fromHtml(contentStr));

        if (clickConfirmListener == null) {
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickConfirmListener.onDialogConfirmClickListener(WarnTipDialog.this);
                }
            });
        }

        //居中
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
    }

    public static class Builder {
        //确定按钮文字
        private String confirmStr;
        //内容文字
        private String contentStr;
        //主题
        private int themeResId = -1;

        //确定回调
        private DialogConfirmClickListener clickConfirmListener;

        public Builder() {
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setClickConfirmListener(DialogConfirmClickListener clickConfirmListener) {
            this.clickConfirmListener = clickConfirmListener;
            return this;
        }

        public Builder setConfirmStr(String confirmStr) {
            this.confirmStr = confirmStr;
            return this;
        }

        public Builder setContentStr(String contentStr) {
            this.contentStr = contentStr;
            return this;
        }

        public WarnTipDialog Build(Activity activity) {
            if (themeResId == -1) {
                themeResId = R.style.transparentFrameWindowStyle;
            }
            return new WarnTipDialog(activity, this);
        }

    }
}
