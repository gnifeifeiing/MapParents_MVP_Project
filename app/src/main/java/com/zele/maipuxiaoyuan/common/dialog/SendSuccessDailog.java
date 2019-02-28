package com.zele.maipuxiaoyuan.common.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;


/**
 * Created by hyl on 2017/4/18.
 * 评价成功页面
 */

public class SendSuccessDailog extends DialogFragment {

    String msg;
    int fontSize;
    String confirmMsg;
    int confirmColor;
    int backgroud;
    public static SendSuccessDailog newInstance(){
        SendSuccessDailog instance = new SendSuccessDailog();
        instance.setCancelable(true);
        return instance;
    }

    /**
     * 定制弹窗的样式
     * @param backgroud 背景颜色
     * @param msg 提示信息
     * @param fontSize  提示文字大小
     * @param confirmMsg    确认消息
     * @param color     确认按钮颜色
     * @return
     */
    public SendSuccessDailog setMsg(int backgroud, String msg, int fontSize, String confirmMsg, int color){
        this.backgroud = backgroud;
        this.msg= msg;
        this.fontSize = fontSize;
        this.confirmMsg = confirmMsg;
        this.confirmColor = color;
        return this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View layout= inflater.inflate(R.layout.dailog_new_style, null);
        if (backgroud!=0) {
            View view = layout.findViewById(R.id.iv_background);
            view.setBackground(getResources().getDrawable(backgroud));
        }
        //1.设置背景
        //2.设置提醒样式
        if (!TextUtils.isEmpty(msg)) {
            TextView tv_msg = layout.findViewById(R.id.tv_msg);
            tv_msg.setText(msg);
            if (fontSize!=0) tv_msg.setTextSize(14);//DensityUtils.sp2px(getContext(),14));
        }
        //3.设置确认按钮
        TextView tv_confirm=layout.findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(confirmMsg)){
            tv_confirm.setText(confirmMsg);
            if (confirmColor!=0) tv_confirm.setTextColor(getResources().getColor(confirmColor));
        }
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)listener.onClick(v);
                dismiss();
            }
        });
        return layout;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        colorDrawable.setAlpha(60);
        win.setBackgroundDrawable(colorDrawable);
    }



    View.OnClickListener listener;
    public SendSuccessDailog setConfirmListener(View.OnClickListener onClickListener) {
        this.listener = onClickListener;
        return this;
    }
}
