package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.views.MyGridview;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:      星级评价适配器
 * Autour：          LF
 * Date：            2018/12/12 15:26
 */

public class EvaluateRecordAdapter  extends RecyclerView.Adapter<EvaluateRecordAdapter.MyViewHolder>{

    private Context mContext;
    private List<EvaluateRecordBean.HonorsBean> mList =new ArrayList<>();

    public EvaluateRecordAdapter(Context context, List<EvaluateRecordBean.HonorsBean> list){
        this.mContext=context;
        this.mList=list;
    }

    public void setData(List<EvaluateRecordBean.HonorsBean> list){
        this.mList=list;
    }

    @Override
    public EvaluateRecordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_score,parent,false);
        EvaluateRecordAdapter.MyViewHolder viewHolder=new EvaluateRecordAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EvaluateRecordAdapter.MyViewHolder holder, final int position) {
        EvaluateRecordBean.HonorsBean data = mList.get(position);
        if (data!=null){
            /**
             * remark : 封
             * tag : 文明礼貌
             * type : 1000
             * id : 5876
             * addDate : 2016-09-26 01:45
             * sid : 5
             * subTypeName : 德育表现
             * avatar : /upload/20160926/20160926105612.png
             * typeName : 美德星
             * point : 7
             * subType : 1100
             * studentName : 小何
             * addDateTime : 2016-09-26 01:45:59
             * pic
             */
            RequestBuilder builder=Glide.with(mContext).load(R.mipmap.ease_default_avatar);
            Glide.with(mContext).load(Constants.SERVER_URL+data.getAvatar()).error(builder).into(holder.avatarIv);
            holder.nameTv.setText(data.getStudentName());
            holder.starTv.setText("  +"+data.getPoint()+"星");
            holder.timeTv.setText(data.getAddDateTime());
            String remark = "#"+data.getTag()+"#   ";
            SpannableStringBuilder style = new SpannableStringBuilder(remark);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#49c372")),
                    0, remark.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.contentTv.setText(style);
            holder.contentTv.append(data.getRemark());
            holder.tagTv.setText(data.getSubTypeName());

            String thumbPaths = data.getThumb();//略缩图
            String originbPaths = data.getThumb();//原图
            //处理图片
            ArrayList<String > picList = new ArrayList<>();
            ArrayList<String > originPicList = new ArrayList<>();
            if (thumbPaths != null) {
                String[] pics = thumbPaths.split("\\|");
                String[] originPics = originbPaths.split("\\|");
                if (pics.length !=0) {
                    holder.gridView.setVisibility(View.VISIBLE);
                    for (String path : pics){
                        picList.add(Constants.SERVER_URL+path);
                    }
                    for (String path : originPics){
                        originPicList.add(Constants.SERVER_URL+path);
                    }
                    holder.gridView.setAdapter(new CircleItemPicsAdapter(mContext,picList,100));
                    holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//                            Intent intent = new Intent(mContext, FullScreenImageActivity.class);
//                            //传递原图准备全屏使用
//                            intent.putExtra("pos",pos);//第几张照片
//                            intent.putStringArrayListExtra("list",originPicList);
//                            mContext.startActivity(intent);
                        }
                    });
                }else {
                    holder.gridView.setVisibility(View.GONE);
                }
            } else {
                holder.gridView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView avatarIv;
        TextView nameTv, starTv, timeTv,tagTv,contentTv;
        MyGridview gridView;

        public MyViewHolder(View itemView) {
            super(itemView);
            avatarIv=itemView.findViewById(R.id.itemEvaStar_avatarIv);

            nameTv=itemView.findViewById(R.id.itemEvaStar_nameTv);
            starTv=itemView.findViewById(R.id.itemEvaStar_starTv);
            timeTv=itemView.findViewById(R.id.itemEvaStar_timeTv);
            tagTv=itemView.findViewById(R.id.itemEvaStar_tagTv);
            contentTv=itemView.findViewById(R.id.itemEvaStar_contentTv);

            gridView=itemView.findViewById(R.id.gridView);
        }
    }
}
