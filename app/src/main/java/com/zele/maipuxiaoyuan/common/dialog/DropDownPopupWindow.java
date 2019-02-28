package com.zele.maipuxiaoyuan.common.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zele.maipuxiaoyuan.R;

/**
 * Description:      构造者模式封装的下拉框
 * Autour：          LF
 * Date：            2018/11/5 10:06
 */

public class DropDownPopupWindow {

    private View anchor;

    private int xoff;

    private int yoff;

    private BaseAdapter adapter;

    private float alpha;

    private AdapterView.OnItemClickListener clickListener;

    public DropDownPopupWindow(DropDownPopupWindow.Builder builder){
        this.anchor=builder.anchor;
        if(builder.xoff==0){
            xoff=0;
        }
        this.xoff=builder.xoff;
        if(builder.yoff==0){
            yoff=0;
        }
        this.yoff=builder.yoff;
        this.alpha=builder.alpha;
        this.adapter=builder.adapter;
        this.clickListener=builder.clickListener;
    }

    public void show(final Activity activity){
        setAlpha(activity,alpha);

        View layout = LayoutInflater.from(activity).inflate(R.layout.task_detail_popupwindow, null);
        ListView listView = layout.findViewById(R.id.lv_popup_list);

        final PopupWindow dropWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dropWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(activity,1.0f);
            }
        });
        // 加上这个,popupwindow中的ListView才可以接收点击事件
        dropWindow.setFocusable(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickListener.onItemClick(parent,view,position,id);
                dropWindow.dismiss();
            }
        });
        // 控制popupwindow的宽度和高度自适应
        listView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        dropWindow.setWidth(listView.getMeasuredWidth());
        dropWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        dropWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ic_dropright));
        dropWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
        dropWindow.showAsDropDown(anchor,xoff,yoff);
    }


    public void setAlpha(Activity activity, float alpha){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }


    public static class Builder{

        //显示位置在哪个控件下边
        private View anchor;

        //背景透明度
        private float alpha;

        //显示的位置在x方向上偏移量
        private int xoff;

        //显示的位置在y方向上偏移量
        private int yoff;

        //列表适配器
        private BaseAdapter adapter;

        //列表点击回调
        private AdapterView.OnItemClickListener clickListener;

        public Builder(){}

        public Builder setAnchor(View anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder setXoff(int xoff) {
            this.xoff = xoff;
            return this;
        }

        public Builder setAlpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder setClickListener(AdapterView.OnItemClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder setYoff(int yoff) {
            this.yoff = yoff;
            return this;
        }

        public Builder setAdapter(BaseAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public DropDownPopupWindow Build(){
            return new DropDownPopupWindow(this);
        }

    }



}
