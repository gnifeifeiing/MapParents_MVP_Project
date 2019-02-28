package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwz on 2017/12/15.
 */

class CircleItemPicsAdapter extends BaseAdapter {
    List<String> list = new ArrayList<>();
    Context context;
    int pic_width =108;
    public CircleItemPicsAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;

    }

    public CircleItemPicsAdapter(Context context, ArrayList<String> picList, int width) {
        this.context = context;
        this.list = picList;
        pic_width = width;
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
        if (convertView  ==null){
            convertView = View.inflate(context, R.layout.item_circle_pic,null);
        }
        ImageView iv_pic = convertView.findViewById(R.id.iv_pic);

        Glide.with(context).load(list.get(position)).into(iv_pic);
        ViewGroup.LayoutParams layoutParams = iv_pic.getLayoutParams();
        int width = CommonUtils.dip2px(context,pic_width);
        layoutParams.height = width;
        iv_pic.setLayoutParams(layoutParams);
        return convertView;
    }
}
