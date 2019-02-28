package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.CoinRecordBean;

import java.util.List;

/**
 * Description:      我的麦粒列表适配器
 * Autour：          LF
 * Date：            2018/11/23 11:50
 */

public class CoinRecordAdapter extends RecyclerView.Adapter<CoinRecordAdapter.MyViewHolder>{

    private Context mContext;
    private List<CoinRecordBean.DatasBean> mList;

    public CoinRecordAdapter(Context context, List<CoinRecordBean.DatasBean> list){
        this.mContext=context;
        this.mList=list;
    }

    public void setData(List<CoinRecordBean.DatasBean> list){
        this.mList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coin_record,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CoinRecordBean.DatasBean datasBean = mList.get(position);
        if (datasBean!=null){
            holder.typeTv.setText(getName(datasBean.getType()));
            holder.valueTv.setText("+"+datasBean.getGold()+"麦粒");
            holder.timeTv.setText(datasBean.getAddTimeStr());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView typeTv, timeTv, valueTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            typeTv=itemView.findViewById(R.id.itemCoinRec_typeTv);
            timeTv=itemView.findViewById(R.id.itemCoinRec_timeTv);
            valueTv=itemView.findViewById(R.id.itemCoinRec_valueTv);
        }
    }


    private String getName(int type) {
        /**
         * type: 4每天登录奖励 5连续登录超过5天额外奖励 6每天有效点赞超过20次积分奖励
         * 7评论奖励 8评论额外奖励 9发布奖励 10发布额外奖励
         */
        String name ="";
        switch (type){
            case 4:
                name ="登录";
                break;
            case 5:
                name="本月连续登录5天";
                break;
            case 6:
                name ="点赞20次";
                break;
            case 7:
                name ="评论";
                break;
            case 8:
                name ="评论次数达到5次";
                break;
            case 9:
                name ="发布内容";
                break;
            case 10:
                name ="发布内容达到5次";
                break;
        }
        return name;
    }
}
