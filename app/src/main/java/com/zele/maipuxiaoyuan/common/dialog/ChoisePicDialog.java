package com.zele.maipuxiaoyuan.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.interfaces.DialogChoiseCameraClickListener;
import com.zele.maipuxiaoyuan.common.interfaces.DialogChoiseGalleryClickListener;

/**
 * Description:      选择图片弹框
 * Autour：          LF
 * Date：            2018/11/23 18:31
 */

public class ChoisePicDialog extends Dialog {
    Activity activity;
    private Button galleryBtn;
    private Button cameraBtn;
    private Button cancleBtn;

    //选择相机
    private DialogChoiseCameraClickListener onDialogChoiseCameraClickListener;
    //选择图库
    private DialogChoiseGalleryClickListener onDialogChoiseGalleryClickListener;

    public ChoisePicDialog(@NonNull Context context, int themeResId, Builder builder) {
        super(context, themeResId);
        this.onDialogChoiseCameraClickListener = builder.onDialogChoiseCameraClickListener;
        this.onDialogChoiseGalleryClickListener = builder.onDialogChoiseGalleryClickListener;
    }

    public ChoisePicDialog(Activity activity, Builder builder) {
        this(activity, R.style.transparentFrameWindowStyle, builder);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.popupwindow_choise_pic, null);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initView();
    }

    private void initView() {
        galleryBtn = findViewById(R.id.pop_galleryBtn);
        cameraBtn = findViewById(R.id.pop_cameraBtn);
        cancleBtn = findViewById(R.id.pop_cancleBtn);

        if (onDialogChoiseCameraClickListener == null) {
            cameraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            cameraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDialogChoiseCameraClickListener.onDialogChoiseCameraClickListener(ChoisePicDialog.this);
                }
            });
        }

        if (onDialogChoiseGalleryClickListener == null) {
            galleryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            galleryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDialogChoiseGalleryClickListener.onDialogChoiseGalleryClickListener(ChoisePicDialog.this);
                }
            });
        }

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //居中
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        this.onWindowAttributesChanged(wl);
        this.setCanceledOnTouchOutside(true);
    }

    public static class Builder{
        //选择相机
        private DialogChoiseCameraClickListener onDialogChoiseCameraClickListener;
        //选择图库
        private DialogChoiseGalleryClickListener onDialogChoiseGalleryClickListener;

        public Builder(){}

        public Builder setOnDialogChoiseCameraClickListener(DialogChoiseCameraClickListener onDialogChoiseCameraClickListener) {
            this.onDialogChoiseCameraClickListener = onDialogChoiseCameraClickListener;
            return this;
        }

        public Builder setOnDialogChoiseGalleryClickListener(DialogChoiseGalleryClickListener onDialogChoiseGalleryClickListener) {
            this.onDialogChoiseGalleryClickListener = onDialogChoiseGalleryClickListener;
            return this;
        }

        public ChoisePicDialog Build(Activity activity){
            return new ChoisePicDialog(activity,this);
        }
    }

}
