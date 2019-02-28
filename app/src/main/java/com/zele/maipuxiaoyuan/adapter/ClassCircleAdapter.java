package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ClassCircleBean;
import com.zele.maipuxiaoyuan.bean.ClassCircleReplyBean;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.views.CenteredImageSpan;
import com.zele.maipuxiaoyuan.common.views.CustomerTagHandler;
import com.zele.maipuxiaoyuan.common.views.MyGridview;
import com.zele.maipuxiaoyuan.common.views.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:      班级圈
 * Autour：          LF
 * Date：            2018/11/21 15:14
 */

public class ClassCircleAdapter extends RecyclerView.Adapter<ClassCircleAdapter.MyViewHolder> {

    private Context mContext;
    private List<ClassCircleBean.DatasBean> mList;
    String mType = "1";
    private String mUID;
    private String mSID;
    private String mStudentName;
    private int mWindowWidth;
    HashMap<Integer, Boolean> isAgree = new HashMap<>();

    public ClassCircleAdapter(Context context, List<ClassCircleBean.DatasBean> list,int windowWidth) {
        this.mContext = context;
        this.mList=list;
        this.mUID = SharedPreferenceCache.getInstance().getPres("UserId");
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        this.mSID = studentsBean.getSid();
        this.mStudentName=studentsBean.getUserName();
        this.mWindowWidth=windowWidth;

        processData();
    }

    public void setData(List<ClassCircleBean.DatasBean> list) {
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_class_circle, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder hodler, int position) {
        hodler.reportTv.setVisibility(View.GONE);
        //防止列表越界异常
        if (mList.size()>position){
            final ClassCircleBean.DatasBean datasBean = mList.get(position);
            if (datasBean != null) {
                //测量高度，印度设置adpter以后，真正的view才能确定下来
                //2.如果是自己发的信息，显示删除按钮
                if ("4".equals(mType)) {
                    showDeletBtn(hodler,position, datasBean);
                } else if ("3".equals(mType)) {
                    //麦励发布不显示删除按钮
                    hodler.delTv.setVisibility(View.GONE);
                } else {
                    //非老师发帖
                    if (1 != datasBean.getShareType()) {
                        if (mUID.equals(datasBean.getUid())) {
                            //自己发的帖子显示删除
                            showDeletBtn(hodler,position, datasBean);
                        } else {
                            hodler.delTv.setVisibility(View.GONE);
                            //其他家长帖子显示举报
                            hodler.reportTv.setVisibility(View.VISIBLE);
                            hodler.reportTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    if (1 != datasBean.getShareType()) {
//                                        ConfirmDialog confirmDialog = ConfirmDialog.newInstance("", "是否举报此消息？");
//                                        confirmDialog.setOklistener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                reportMsg(datasBean.getId() + "");
//                                            }
//                                        });
//                                        confirmDialog.show(context.getSupportFragmentManager(), "");
//                                    } else {
//                                        Toast.makeText(context, "不可以投诉教师发布的消息！", Toast.LENGTH_SHORT).show();
//                                    }
                                }
                            });
                        }
                    } else {
                        //老师发帖
                        hodler.delTv.setVisibility(View.GONE);
                    }
                }
                //3.发帖人关系描述
                if (1 == datasBean.getShareType()) {
                    hodler.teacherDesTv.setVisibility(View.VISIBLE);
                    //教师的信息标注
                    hodler.teacherDesTv.setText(datasBean.getClassName() == null ? "" : datasBean.getClassName() + datasBean.getCourse() +
                            (datasBean.getMaster() == 1 ? "(班主任)" : ""));
                    hodler.nameTv.setText(datasBean.getTeacherName());//发帖人名字
                } else if (2 == datasBean.getShareType()) {
                    //家长
                    hodler.teacherDesTv.setVisibility(View.INVISIBLE);
                    String s = datasBean.getCourse() == null ? "(家长)" : "(" + datasBean.getCourse() + ")";
                    hodler.nameTv.setText(datasBean.getSname() + s);//发帖人名字
                } else if (3 == datasBean.getShareType()) {
                    hodler.nameTv.setText(datasBean.getParentName());
                    hodler.teacherDesTv.setText("");
                }

                /**
                 * 图片复用是否需要设置tag？
                 */
                //显示图标和标签内容，并且动态显示评论内容
                //第一部分 ：标签
                //第二部分 ：标签内容,默认不显示标签和图标
                int start = -1;
                String tagName = "";
                String html = "";
                //非麦励发布
                /**
                 * 自动获取颜色和图片icon
                 */
                String color = "#49c372";
                String starColor ="#49c372";
                int img;
                if (3!= datasBean.getShareType()) {
                    start  = datasBean.getType();
                    tagName = datasBean.getSubTypeName();
                    color = parseTagColor(datasBean.getType());
                    starColor = parseStarColor(datasBean.getType());
                    if (2 == datasBean.getShareType()) {
                        start = datasBean.getSubType();
                        tagName = datasBean.getSubTypeName();
                        color =  parseTagColor(datasBean.getSubType());
                        starColor = parseStarColor(datasBean.getSubType());
                    }
                    tagName = tagName ==null?"":tagName;
                    if (tagName.equals("其他")){
                        tagName = datasBean.getTag()==null?"批评提醒":datasBean.getTag();
                    }
                    //文字大小只能用big来表示
                    html="<font  color = '"+color+"'><big>&nbsp;&nbsp;&nbsp; &#160;&#160;"+ tagName+"&nbsp;&nbsp;</big></font>";
                }else {
                    //麦粒发布的标题要加入到正文中，带##绿色
                    tagName  = "#"+datasBean.getTitle()+"#";
                    //文字大小只能用big来表示
                    html="<font color = '"+color+"'>"+ tagName+"</font>";
                }

                img = parsePic(start);//非麦粒发布的有图标
                //拼接第一二部分
                //拼凑完整的正文，包括4部分
                Spanned spanned = Html.fromHtml(html, new UrlImageGetter(), new CustomerTagHandler());
                SpannableStringBuilder ssb = (SpannableStringBuilder) spanned;
                //动态设置文本
                if ("3".equals(mType)){
                    //麦粒发布的不需要图片
                    hodler.contentTv.setText(spanned);
                }else {
                    ssb.setSpan(new CenteredImageSpan(mContext, img),//居中对齐
                            0, 4, ImageSpan.ALIGN_BASELINE);
                    hodler.contentTv.setText(ssb);
                }
                //第三部分：拼接正文
                String content = "";
                if (3==datasBean.getShareType()){
                    content = datasBean.getRemark();
                    hodler.contentTv.append(content);
                }else {
                    content = datasBean.getSname()+"同学: "+datasBean.getRemark();
                    //判断是否包含当前绑定的学生姓名
                    //如果评价中包含本账号的学生
                    if (content.contains(mStudentName)){
                        int i = content.indexOf(mStudentName);
                        SpannableStringBuilder style = new SpannableStringBuilder(content);
                        style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.primaryBg)),
                                i, i+mStudentName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        hodler.contentTv.append(style);
                    }else {
                        hodler.contentTv.append(content);
                    }
                }
                //第四部分：加星
                if (datasBean.getPoint()!=0){
                    String star = "  +"+datasBean.getPoint()+"星";
                    try {
                        SpannableStringBuilder point = new SpannableStringBuilder("");
                        if (0!=datasBean.getPoint()){
                            point = new SpannableStringBuilder(star);
                            point.setSpan(new ForegroundColorSpan(Color.parseColor(starColor)),
                                    0,star.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        hodler.contentTv.append(point);
                    }catch (Exception E){
                        hodler.contentTv.append(""+star);
                    }
                }
            }

            //4.显示头像及其他详情
            //解决复用问题
            Glide.with(mContext).load(Constants.SERVER_URL+datasBean.getAvatar()).into(hodler.picHeadIv);

            hodler.dateTv.setText(datasBean.getAddDateTime());//发布日期
            hodler.replyIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showReplyDailog(datasBean);
                }
            });
            if (1 == datasBean.getState()) {
                //说明自己点赞了
                hodler.dingIv.setImageResource(R.mipmap.ic_zan_b);
                isAgree.put(datasBean.getId(), true);
            } else {
                isAgree.put(datasBean.getId(), false);
                hodler.dingIv.setImageResource(R.mipmap.ic_zan_a);
            }
            //5处理图片集合
            ArrayList<String> pics = new ArrayList<>();
            ArrayList<String> big_pics = new ArrayList<>();
            String thumb = datasBean.getThumb();//缩略图
            String pic = datasBean.getPic();
            if (!TextUtils.isEmpty(pic)) {
                String[] split = pic.split("\\|");
                for (String a : split) {
                    if (a.startsWith("/upload")) {
                        a = Constants.SERVER_URL + a;
                    }
                    big_pics.add(a);
                }
            }
            if (!TextUtils.isEmpty(thumb)) {
                hodler.replyRl.setVisibility(View.VISIBLE);
                String[] split = thumb.split("\\|");
                for (String p : split) {
                    if (p.startsWith("/upload")) {
                        p = Constants.SERVER_URL + p;
                    }
                    pics.add(p);
                }
                if (pics.size() == 1) {
                    //如果只有一张图片，等比例缩放图片
                    hodler.singleIv.setVisibility(View.VISIBLE);
                    hodler.singleIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(context, FullScreenImageActivity.class);
//                            //传递原图准备全屏使用
//                            intent.putStringArrayListExtra("list", big_pics);
//                            mContext.startActivity(intent);
                        }
                    });
                    hodler.gridView.setVisibility(View.GONE);
                    //图片定高160dp
                    ViewGroup.LayoutParams lp = hodler.singleIv.getLayoutParams();
                    lp.width = mWindowWidth / 3;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    hodler.singleIv.setLayoutParams(lp);
                    String path = pics.get(0);
                    Glide.with(mContext).load(path).into(hodler.singleIv);
                } else {
                    //如果有多张图片，用gridview 显示
                    hodler.singleIv.setVisibility(View.GONE);
                    hodler.gridView.setVisibility(View.VISIBLE);
                    hodler.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    hodler.gridView.setAdapter(new CircleItemPicsAdapter(mContext, pics));
                    //设置图片的条目点击事件
                    hodler.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(mContext, FullScreenImageActivity.class);
//                        //传递原图准备全屏使用
//                        intent.putExtra("pos", pos);//第几张照片
//                        intent.putStringArrayListExtra("list", big_pics);
//                        singleIv.startActivity(intent);
                        }
                    });
                }
            } else {
                //如果没有图片
                hodler.singleIv.setVisibility(View.GONE);
                hodler.gridView.setVisibility(View.GONE);
            }


            //6.回复消息和点赞模块
            List<ClassCircleReplyBean.AgreesBean> items = datasBean.getItems();
            List<ClassCircleReplyBean.AgreesBean> replys = new ArrayList<>();
            int agrees = 0;
            int reply_num = 0;
            StringBuilder zan_name = new StringBuilder();
            //解析点赞和评论数据
            if (items == null || items.size() == 0) {
                //说明没有点赞和回复
                hodler.replyRl.setVisibility(View.GONE);
                hodler.sanjiaoIv.setVisibility(View.GONE);
            } else {
                hodler.sanjiaoIv.setVisibility(View.VISIBLE);
                hodler.replyRl.setVisibility(View.VISIBLE);
                //解析点赞和评论的数据
                for (ClassCircleReplyBean.AgreesBean item : items) {
                    if (item.getType() == 1) {
                        agrees += 1;
                        //点赞的人
                        if (TextUtils.isEmpty(item.getName())) {
                        } else {
                            String relate = item.getRelate() == null ? "" : "(" + item.getRelate() + ")";
                            zan_name.append(item.getName() + relate + ",");
                        }
                    } else {
                        //回复评论的人
                        reply_num+=1;
                        replys.add(item);
                    }
                }

                //点赞数量
                //6.1点赞的人
                if (agrees == 0) {
                    //没有人点赞时：
                    hodler.zanLl.setVisibility(View.GONE);
                } else {
                    String zans = zan_name.toString();
                    if (zans.endsWith(",")) {
                        zans = zans.substring(0, zans.length() - 1);
                    }
                    hodler.zanLl.setVisibility(View.VISIBLE);
                    hodler.commentNameTv.setText(zans);
                    //当点赞数量不为0的的时候，判断自己是否点过赞
                }

                //6.2 消息回复
                ClassCircleReplyAdapter adapter = new ClassCircleReplyAdapter(mContext, replys);
                hodler.listReply.setAdapter(adapter);
                //判断回复人数
                if (replys.size() == 0) {
                    hodler.divider.setVisibility(View.GONE);
                    hodler.listReply.setVisibility(View.GONE);
                } else {
                    hodler.listReply.setVisibility(View.VISIBLE);
                    if (agrees!=0){
                        hodler.divider.setVisibility(View.VISIBLE);
                    }else {
                        hodler.divider.setVisibility(View.GONE);
                    }
                }

            }
            //点赞和回复数量在最后赋值，有可能为空或者0
            hodler.zanNumTv.setText(agrees==0?"":""+agrees);
            hodler.replyNumTv.setText(reply_num == 0 ? "" : reply_num + "");//消息回复
            //6.1点赞请求
            hodler.dingIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                requsetAgree(datasBean, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView picHeadIv;
        TextView nameTv;
        TextView teacherDesTv;
        TextView contentTv;
        TextView dateTv;
        TextView timeTv;
        ImageView replyIv;
        ImageView dingIv;
        TextView replyNumTv;
        TextView commentNameTv;

        MyListview listReply;
        MyGridview gridView;
        LinearLayout zanLl;
        ImageView singleIv;
        RelativeLayout replyRl;
        ImageView sanjiaoIv;
        View divider;
        TextView delTv;//删除
        TextView reportTv; //举报
        TextView zanNumTv;//点赞人数

        public MyViewHolder(View itemView) {
            super(itemView);
            picHeadIv = itemView.findViewById(R.id.classCircle_picHeadIv);
            nameTv = itemView.findViewById(R.id.classCircle_nameTv);
            teacherDesTv = itemView.findViewById(R.id.classCircle_teacherDesTv);
            contentTv = itemView.findViewById(R.id.classCircle_contentTv);
            dateTv = itemView.findViewById(R.id.classCircle_dateTv);
            timeTv = itemView.findViewById(R.id.classCircle_timeTv);
            replyIv = itemView.findViewById(R.id.classCircle_replyIv);
            dingIv = itemView.findViewById(R.id.classCircle_dingIv);
            replyNumTv = itemView.findViewById(R.id.classCircle_replyNumTv);
            commentNameTv = itemView.findViewById(R.id.classCircle_commentNameTv);

            listReply = itemView.findViewById(R.id.classCircle_listReply);
            gridView = itemView.findViewById(R.id.classCircle_gridView);
            zanLl = itemView.findViewById(R.id.classCircle_zanLl);
            singleIv = itemView.findViewById(R.id.classCircle_singleIv);
            replyRl = itemView.findViewById(R.id.classCircle_replyRl);
            sanjiaoIv = itemView.findViewById(R.id.classCircle_sanjiaoIv);
            divider = itemView.findViewById(R.id.classCircle_divider);
            delTv = itemView.findViewById(R.id.classCircle_delTv);
            reportTv = itemView.findViewById(R.id.classCircle_reportTv);
            zanNumTv = itemView.findViewById(R.id.classCircle_zanNumTv);
        }
    }

    /**
     * 屏蔽批评的数据
     */
    private void processData() {
        if(mList==null)
            return;
        ArrayList<ClassCircleBean.DatasBean> tem = new ArrayList<>();
        for (ClassCircleBean.DatasBean item : mList) {
            //如果是批评提醒并且不是被批评的家长，那么隐藏
            if (item.getType() == 6000 && !item.getSid().contains(mSID)) {
            } else {
                tem.add(item);
            }
        }
        mList = tem;
    }

    /**
     * 显示删除按钮
     * @param position
     * @param datasBean
     */
    private void showDeletBtn(MyViewHolder hodler, int position, ClassCircleBean.DatasBean datasBean) {
        hodler.delTv.setVisibility(View.VISIBLE);
        hodler.delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ConfirmDialog confirmDialog = ConfirmDialog.newInstance("", "是否确认删除此消息？");
//                confirmDialog.setOklistener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        requestDel(position, datasBean);
//                    }
//                });
//                confirmDialog.show(context.getSupportFragmentManager(),"");
            }
        });
    }


    private String parseTagColor(int type){
        String color = "#e95257";
        switch (type){
            case 1000:
                //美德
                color ="#e95257";
                break;
            case 2000:
                //智慧
                color ="#eea500";
                break;
            case 4000:
                //wenyu
                color ="#ec78a0";
                break;
            case 3000:
                //tijian
                color ="#57cfc7";
                break;
            case 5000:
                //勤劳
                color ="#3884f8";
                break;
        }
        return color;
    }
    private String parseStarColor(int type){
        String color = "#e95257";
        switch (type){
            case 1000:
                color ="#e98856";
                break;
            case 2000:
                color ="#f7d800";
                break;
            case 4000:
                color ="#f994ad";
                break;
            case 3000:
                color ="#62e5b6";
                break;
            case 5000:
                color ="#57d8ff";
                break;
        }
        return color;
    }
    private int parsePic(int msyType) {
        //消息类型
        int pic = R.mipmap.ic_meide_icon;
        switch (msyType) {
            case 1000:
                pic = R.mipmap.ic_meide_icon;
                break;
            case 2000:
                pic = R.mipmap.ic_zhihui_icon;
                break;
            case 3000:
                pic = R.mipmap.ic_tijian_icon;
                break;
            case 4000:
                pic = R.mipmap.ic_wenyu_icon;
                break;
            case 5000:
                pic = R.mipmap.ic_qinlao_icon;
                break;
            case 6000:
                pic = R.mipmap.other_icon;
                break;
        }
        return pic;
    }

    public class UrlImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            Log.w("res_src:", "source:" + source);
            Drawable draw = mContext.getResources().getDrawable(parsePic(Integer.parseInt(source)));
            draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
            return draw;
        }
    }
}
