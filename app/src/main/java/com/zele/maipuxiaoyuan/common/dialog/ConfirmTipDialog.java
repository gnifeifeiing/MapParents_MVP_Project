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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.interfaces.DialogCancleClickListener;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;

/**
 * Description:      确认提示弹框
 * Autour：          LF
 * Date：            2018/11/6 11:17
 */

public class ConfirmTipDialog extends Dialog {

    public static int CONFIRM_SINGLE_TYPE = 0;
    public static int CONFIRM_DOUBLE_TYPE = 1;

    Activity activity;
    private Button cancleBtn, confirmBtn;
    private TextView contentTv, titleTv;

    private String confirmStr;
    private String cancelStr;
    private int confirmType = CONFIRM_DOUBLE_TYPE;
    private String contentStr;
    private String titleStr;
    //点击outside是否能够取消
    private boolean cancelable=true;
    //点击回调
    private DialogCancleClickListener clickCancleListener;
    private DialogConfirmClickListener clickConfirmListener;

    public ConfirmTipDialog(@NonNull Context context, int themeResId, Builder builder) {
        super(context, themeResId);
        this.confirmStr = builder.confirmStr;
        this.cancelStr = builder.cancelStr;
        this.confirmType = builder.confirmType;
        this.contentStr = builder.contentStr;
        this.titleStr = builder.titleStr;
        this.cancelable=builder.cancelable;
        this.clickCancleListener = builder.clickCancleListener;
        this.clickConfirmListener = builder.clickConfirmListener;
    }

    public ConfirmTipDialog(Activity activity, Builder builder) {
        this(activity,builder.themeResId, builder);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (confirmType == CONFIRM_DOUBLE_TYPE) {
            this.setContentView(R.layout.dialog_prompf);
        } else {
            this.setContentView(R.layout.dialog_single_prompf);
        }
        initView();
    }


    private void initView() {
        confirmBtn = findViewById(R.id.btn_ok);

        if (this.confirmStr != null && !TextUtils.isEmpty(this.confirmStr)) {
            confirmBtn.setText(this.confirmStr);
        } else {
            confirmBtn.setText("确定");
        }

        titleTv = findViewById(R.id.dialog_title);
        contentTv = findViewById(R.id.dialog_content);

        if (this.titleStr != null && !TextUtils.isEmpty(this.titleStr)) {
            titleTv.setText(this.titleStr);
        } else {
            titleTv.setText("提示");
        }
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
                    clickConfirmListener.onDialogConfirmClickListener(ConfirmTipDialog.this);
                }
            });
        }

        if (confirmType == CONFIRM_DOUBLE_TYPE) {
            cancleBtn = findViewById(R.id.btn_cancel);
            if (this.cancelStr != null && !TextUtils.isEmpty(this.cancelStr)) {
                cancleBtn.setText(this.cancelStr);
            } else {
                cancleBtn.setText("取消");
            }


            if (clickCancleListener == null) {
                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

            } else {
                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCancleListener.onDialogCancleClickListener(ConfirmTipDialog.this);
                    }
                });
            }
        }
        setCancelable(cancelable);
        //居中
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //设置宽度
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (activity.getWindowManager().getDefaultDisplay().getWidth() - CommonUtils.dip2px(activity.getApplicationContext(), 100));
        window.setAttributes(params);
    }

    public static class Builder {
        //确定按钮文字
        private String confirmStr;
        //取消按钮文字
        private String cancelStr;
        //一个按钮还是两个按钮
        private int confirmType=CONFIRM_DOUBLE_TYPE;
        //内容文字
        private String contentStr;
        //标题文字
        private String titleStr;
        //主题
        private int themeResId=-1;
        //点击outside是否能够取消
        private boolean cancelable=true;

        //取消回调
        private DialogCancleClickListener clickCancleListener;
        //确定回调
        private DialogConfirmClickListener clickConfirmListener;

        public Builder() {
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setConfirmType(int confirmType) {
            this.confirmType = confirmType;
            return this;
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setClickCancleListener(DialogCancleClickListener clickCancleListener) {
            this.clickCancleListener = clickCancleListener;
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

        public Builder setCancelStr(String cancelStr) {
            this.cancelStr = cancelStr;
            return this;
        }

        public Builder setContentStr(String contentStr) {
            this.contentStr = contentStr;
            return this;
        }

        public Builder setTitleStr(String titleStr) {
            this.titleStr = titleStr;
            return this;
        }

        public ConfirmTipDialog Build(Activity activity) {
            if(themeResId == -1){
                themeResId=R.style.transparentFrameWindowStyle;
            }
            return new ConfirmTipDialog(activity, this);
        }
    }
}
