package com.zele.maipuxiaoyuan.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.interfaces.DialogListItemClickListener;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/13 13:58
 */

public class ChoiseListItemDialog extends Dialog {

    Activity activity;
    private TextView titleTv;
    private ListView listview;
    private ImageView closeIv;


    private BaseAdapter adapter;
    //标题文字
    private String titleStr;
    //主题
    private int themeResId=-1;
    //item点击回调
    private DialogListItemClickListener itemClickListener;

    public ChoiseListItemDialog(@NonNull Context context, int themeResId, Builder builder) {
        super(context, themeResId);
        this.titleStr = builder.titleStr;
        this.adapter = builder.adapter;
        this.itemClickListener = builder.itemClickListener;
    }

    public ChoiseListItemDialog(Activity activity, Builder builder) {
        this(activity,builder.themeResId, builder);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_choise_list_item);
        initView();
    }


    private void initView() {
        titleTv = findViewById(R.id.choiseListItem_titleTv);
        listview = findViewById(R.id.choiseListItem_listview);
        closeIv=findViewById(R.id.choiseListItem_closeIv);

        if (this.titleStr != null && !TextUtils.isEmpty(this.titleStr)) {
            titleTv.setText(this.titleStr);
        } else {
            titleTv.setText("请选择");
        }

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClickListener.onDialogListItemClickListener(ChoiseListItemDialog.this,position);
            }
        });
        listview.setAdapter(adapter);

        //居中
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //设置宽度
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (activity.getWindowManager().getDefaultDisplay().getWidth() - CommonUtils.dip2px(activity.getApplicationContext(), 50));
        window.setAttributes(params);
    }

    public static class Builder {

        private BaseAdapter adapter;
        //标题文字
        private String titleStr;
        //主题
        private int themeResId=-1;

        //item点击回调
        private DialogListItemClickListener itemClickListener;

        public Builder() {
        }

        public Builder setAdapter(BaseAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setItemClickListener(DialogListItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setTitleStr(String titleStr) {
            this.titleStr = titleStr;
            return this;
        }

        public ChoiseListItemDialog Build(Activity activity) {
            if(themeResId == -1){
                themeResId= R.style.transparentFrameWindowStyle;
            }
            return new ChoiseListItemDialog(activity, this);
        }
    }
}
