package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.ClassCircleReplyBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwz on 2017/11/7.
 * 显示评论列表
 */

public class ClassCircleReplyAdapter extends BaseAdapter {
    List<ClassCircleReplyBean.AgreesBean> list = new ArrayList<>();
    private Context context;

    public ClassCircleReplyAdapter(Context context, List<ClassCircleReplyBean.AgreesBean> items) {
        this.context = context;
        list = items;
        Log.w("res_repp",list.size()+"");
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_reply,null);
        TextView reply_name = (TextView) view.findViewById(R.id.reply_name);
        ClassCircleReplyBean.AgreesBean item = list.get(position);
        if (item!=null){
            String relate = item.getRelate()==null?"家长":"("+item.getRelate()+")";
            String before = item.getName()+relate;

            SpannableString spannableString = new SpannableString(before+" : "+item.getContent());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4bc975")),
                    0,before.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            reply_name.setText(spannableString);
        }


        return view;
    }
}
