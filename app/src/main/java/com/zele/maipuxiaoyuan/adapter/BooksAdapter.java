package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.MessageClassesListviewBean;
import com.zele.maipuxiaoyuan.common.patriarch.NineGridTestLayout;
import com.zele.maipuxiaoyuan.common.patriarch.NineGridTestModel;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.BooksPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:      作业列表适配器
 * Autour：          LF
 * Date：            2018/11/17 9:44
 */

public class BooksAdapter  extends RecyclerView.Adapter<BooksAdapter.MyViewHolder>{

    private BindStudentsBean.StudentsBean studentsBean;
    private Context mContext;
    private List<MessageClassesListviewBean.MessagesBean> mList;
    private List<NineGridTestModel> mListNineModel;
    List<String> nub = new ArrayList<String>();
    private BooksPresenter mPresenter;
    private String mUID;
    private String mSID;

    public BooksAdapter(Context context, List<MessageClassesListviewBean.MessagesBean> list, BooksPresenter presenter){
        this.mContext=context;
        this.mList=list;
        this.mPresenter=presenter;
        mUID= SharedPreferenceCache.getInstance().getPres("UserId");
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentsBean.getSid();
        mListNineModel=new ArrayList<>();
        studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
    }

    public void setData(List<MessageClassesListviewBean.MessagesBean> list){
        this.mList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_list,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MessageClassesListviewBean.MessagesBean data = mList.get(position);

        String classNames = SharedPreferenceCache.getInstance().getPres("AllStudentsClassNames");
        String[] classNameArr = classNames.substring(0,classNames.length()).split(",");
        String sendClasses = data.getClassIds();
        String name="";
        for (String className:classNameArr){
            if (sendClasses.contains(className)){
                name+=className+",";
            }
        }
        holder.classNameTv.setText(name.substring(0,name.length()));
        holder.dateTv.setText(data.getAddDate());
        holder.subjectsNameTv.setText(data.getTitle());
        String read = data.getRead();
        if ("1".equals(read)){
            holder.unreadView.setVisibility(View.GONE);
            holder.confirmTv.setText("已阅读");
            holder.confirmTv.setTextColor(mContext.getResources().getColor(R.color.text_9));
            holder.confirmTv.setEnabled(false);
        }else {
            holder.unreadView.setVisibility(View.VISIBLE);
            holder.confirmTv.setText("确认已读");
            holder.confirmTv.setTextColor(mContext.getResources().getColor(R.color.primaryBg));
            //标记消息为已读
            holder.confirmTv.setEnabled(true);
            holder.confirmTv.setTag(holder.unreadView);
            holder.confirmTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> parameter=new HashMap<>();
                    parameter.put("uid",mUID);
                    parameter.put("type", "4");
                    parameter.put("sid", mSID);
                    parameter.put("msgId", String.valueOf(mList.get(position).getId()));
                    mPresenter.setReadByMsgId(parameter,position);
                }
            });
        }
        String path = data.getAttach();
        if (path == null) {// 路径为null
            if (data.getMsg() == null) {
                holder.messageTv.setVisibility(View.GONE);
            } else {
                holder.messageTv.setVisibility(View.VISIBLE);
                holder.messageTv.setText(mList.get(position).getMsg());
            }
            holder.soundShowBtn.setVisibility(View.GONE);// 语音
            holder.nineGridLayout.setVisibility(View.GONE);// 图片
        } else {// 路径为不为null
            String[] strarray = path.split("\\|");
            if (strarray.length >= 2) {
                if (data.getMsg() != null) {
                    holder.messageTv.setVisibility(View.VISIBLE);
                    holder.messageTv.setText(data.getMsg());
                } else {
                    holder.messageTv.setVisibility(View.GONE);
                }
                // 显示图片
                holder.soundShowBtn.setVisibility(View.GONE);// 语音
                holder.nineGridLayout.setVisibility(View.VISIBLE);// 图片
                List<String> paththumb = new ArrayList<>();
                for (int b = 0; b < strarray.length; b++) {
                    String pa = strarray[b];
                    if (pa.contains("origin")) {
                        paththumb.add(pa);
                    }
                }
                for (int a = 0; a < paththumb.size(); a++) {
                    String patha = Constants.SERVER_URL+ paththumb.get(a);
                    nub.add(patha);
                }

                NineGridTestModel model = new NineGridTestModel();
                for (int i = 0; i < nub.size(); i++) {
                    model.urlList.add(nub.get(i));
                }
                mListNineModel.add(model);
                nub.clear();
                holder.nineGridLayout.setUrlList(model.urlList);
            } else {
                if (mList.get(position).getMsg() != null) {
                    holder.messageTv.setVisibility(View.GONE);
                } else {
                    holder.messageTv.setVisibility(View.GONE);
                }
                final AnimationDrawable anim4 = (AnimationDrawable) holder.soundShowBtn
                        .getCompoundDrawables()[0];
                // 显示语音
                holder.soundShowBtn.setVisibility(View.VISIBLE);
                holder.nineGridLayout.setVisibility(View.GONE);// 图片
                holder.soundShowBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            MediaPlayer mp = new MediaPlayer();
                            mp.setDataSource(Constants.SERVER_URL+ data.getAttach());
                            mp.prepare();// 准备因音频
                            mp.start();// 播放音频
                            anim4.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    if (mp.isPlaying()) {
                                        anim4.start();
                                    } else {
                                        anim4.selectDrawable(0); // 选择当前动画的第一帧，然后停止
                                        anim4.stop();
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    /**
     * 设置已读状态并更新
     * @param position
     */
    public void setItemStatus(int position){
        mList.get(position).setRead("1");
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        View unreadView;
        TextView subjectsNameTv;
        TextView classNameTv;
        TextView dateTv;
        TextView messageTv;
        TextView confirmTv;

        Button soundShowBtn;
        NineGridTestLayout nineGridLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            unreadView=itemView.findViewById(R.id.itemBook_unreadView);
            subjectsNameTv=itemView.findViewById(R.id.itemBook_subjectsNameTv);
            classNameTv=itemView.findViewById(R.id.itemBook_classNameTv);
            dateTv=itemView.findViewById(R.id.itemBook_dateTv);
            messageTv=itemView.findViewById(R.id.itemBook_messageTv);
            confirmTv=itemView.findViewById(R.id.itemBook_confirmTv);

            soundShowBtn=itemView.findViewById(R.id.itemBook_soundShowBtn);
            nineGridLayout=itemView.findViewById(R.id.itemBook_nineGridLayout);
        }
    }
}

