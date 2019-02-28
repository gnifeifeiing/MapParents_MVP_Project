package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwz on 2018/1/2.
 */
public class StudentsAdapter extends BaseAdapter {
    Context context;
    List<BindStudentsBean.StudentsBean> students = new ArrayList<>();

    public StudentsAdapter(Context context, List<BindStudentsBean.StudentsBean> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
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
        convertView = View.inflate(context, R.layout.item_student, null);
        ViewHolder holder = new ViewHolder(convertView);
        BindStudentsBean.StudentsBean data = students.get(position);
        Glide.with(context).load(Constants.SERVER_URL+data.getAvatar()).into(holder.iv_pic);
        holder.tv_name.setText(data.getUserName());
        holder.tv_teacher_des.setText(data.getAccessName()+data.getClassName());
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_teacher_des;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = rootView.findViewById(R.id.iv_pic);
            this.tv_name = rootView.findViewById(R.id.tv_name);
            this.tv_teacher_des = rootView.findViewById(R.id.tv_teacher_des);
        }

    }
}
