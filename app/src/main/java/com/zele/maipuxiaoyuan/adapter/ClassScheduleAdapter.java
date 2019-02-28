package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.ScheduleBean;

import java.util.Map;

/**
 * Description:      课程表适配器
 * Autour：          LF
 * Date：            2018/12/10 15:25
 */

public class ClassScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private Map<Integer,ScheduleBean.SchedulesBean.ArrayBean> mList;


    public ClassScheduleAdapter(Context context, Map<Integer,ScheduleBean.SchedulesBean.ArrayBean> list){
        this.mContext=context;
        this.mList=list;
    }

    public void setData(Map<Integer,ScheduleBean.SchedulesBean.ArrayBean> list){
        this.mList=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view =null;
        switch (viewType){
            // 左侧头部
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_class_schedule_head,parent,false);
                viewHolder=new MyHeadViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_class_schedule_group,parent,false);
                viewHolder=new MyGroupViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ScheduleBean.SchedulesBean.ArrayBean bean = mList.get(position);
        if (bean!=null){
            if (mList.get(position).isHead()) {
                MyHeadViewHolder headViewHolder=(MyHeadViewHolder)holder;
                headViewHolder.headTextTv.setText(mList.get(position).getCname());
            } else {
                MyGroupViewHolder groupViewHolder=(MyGroupViewHolder)holder;
                groupViewHolder.groupTextTv.setText(mList.get(position).getCname());
                setTextViewBg(groupViewHolder.groupTextTv,mList.get(position).getCname());
            }
        }
    }

    private void setTextViewBg(TextView tv,String className) {
        if(className.equals("语文")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg1));
        }else if(className.equals("数学")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg2));
        }else if(className.equals("英语")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg3));
        }else if(className.equals("班会")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg4));
        }else if(className.equals("劳动")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg5));
        }else if(className.equals("美术")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg6));
        }else if(className.equals("科学")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg7));
        }else if(className.equals("社团")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg8));
        }else if(className.equals("体育")){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg9));
        }else {
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.class_schedule_tv_bg10));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.size()>0&&mList.get(position)!=null){
            if (mList.get(position).isHead()) {
                return 0;
            } else {
                return 1;
            }
        }else{
            return 1;
        }

    }

    @Override
    public int getItemCount() {
        return 64;
    }

    class MyHeadViewHolder extends RecyclerView.ViewHolder{

        TextView headTextTv;

        public MyHeadViewHolder(View itemView) {
            super(itemView);
            headTextTv=itemView.findViewById(R.id.classScheHead_headTextTv);
        }
    }

    class MyGroupViewHolder extends RecyclerView.ViewHolder{

        TextView groupTextTv;

        public MyGroupViewHolder(View itemView) {
            super(itemView);
            groupTextTv=itemView.findViewById(R.id.classScheGroup_groupTextTv);
        }
    }
}
