package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.utils.Constants;

import java.util.List;

/**
 * Description:      学生信息管理适配器
 * Autour：          LF
 * Date：            2018/11/14 11:21
 */

public class StudentCardAdapter extends RecyclerView.Adapter<StudentCardAdapter.MyViewHolder>{

    private Context mContext;
    private List<BindStudentsBean.StudentsBean> mList;

    public StudentCardAdapter(Context context, List<BindStudentsBean.StudentsBean> list){
        this.mContext=context;
        this.mList=list;
    }

    public void setData(List<BindStudentsBean.StudentsBean> list){
        this.mList=list;
    }

    @Override
    public StudentCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_student_cards,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudentCardAdapter.MyViewHolder holder, final int position) {
        holder.rootCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑页面和注册页面一个样子
//                Intent intent = new Intent(mContext, EditStudentActivity.class);
//                intent.putExtra("item", mList.get(position));
//                intent.putExtra("isEdit",true);
//                mContext.startActivity(intent);
            }
        });

        holder.nameTv.setText(mList.get(position).getUserName());
        holder.numTv.setText(mList.get(position).getAttendNum());
        holder.schoolNameTv.setText(mList.get(position).getAccessName());
        holder.gradeTv.setText(mList.get(position).getClassName());

        if(mList.get(position).getAvatar()!=null&&!TextUtils.isEmpty(mList.get(position).getAvatar())){
            Glide.with(mContext)
                    .load(Constants.SERVER_URL + mList.get(position).getAvatar())
                    .into(holder.headIv);
        }else{
            Glide.with(mContext)
                    .load(R.mipmap.head)
                    .into(holder.headIv);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv, numTv, gradeTv, schoolNameTv;
        ImageView headIv;
        CardView rootCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.itemStudentCards_nameTv);
            numTv=itemView.findViewById(R.id.itemStudentCards_numTv);
            gradeTv=itemView.findViewById(R.id.itemStudentCards_gradeNameTv);
            schoolNameTv=itemView.findViewById(R.id.itemStudentCards_schoolNameTv);

            headIv=itemView.findViewById(R.id.itemStudentCards_headIv);
            rootCard=itemView.findViewById(R.id.itemStudentCards_rootCard);
        }
    }
}
