package com.zele.maipuxiaoyuan.ui.homefrag;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.HallHonorBean;
import com.zele.maipuxiaoyuan.bean.HallHonorListBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.dialog.WarnTipDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.HallHonorPresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.HallHonorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      荣誉勋章
 * Autour：          LF
 * Date：            2018/11/13 17:23
 */
public class HallHonorActivity extends BaseActivity<HallHonorView,HallHonorPresenter> implements HallHonorView {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.hallHonor_headerTv1)
    TextView mHeaderTv1;
    @BindView(R.id.hallHonor_recycler1)
    RecyclerView mRecycler1;
    @BindView(R.id.hallHonor_headerTv2)
    TextView mHeaderTv2;
    @BindView(R.id.hallHonor_recycler2)
    RecyclerView mRecycler2;

    List<HallHonorBean> mList=new ArrayList<>();
    List<HallHonorBean> mList2=new ArrayList<>();

    private RecyclerViewOneAdapter mOneAdapter;
    private RecyclerViewTwoAdapter mTwoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_honor);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});

        showProgress("加载中...");
        Map<String,String> parameter=new HashMap<>();
        parameter.put("sid", studentsBean.getSid());
        mPresenter.getHallHonorData(parameter);
    }

    private void initView() {
        titleTitleTv.setText("荣誉徽章");

        mOneAdapter = new RecyclerViewOneAdapter(this);
        // 声名为瀑布流的布局方式: 2列,垂直方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRecycler1.setLayoutManager(staggeredGridLayoutManager);
        mRecycler1.setAdapter(mOneAdapter);

        mTwoAdapter = new RecyclerViewTwoAdapter(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycler2.setLayoutManager(staggeredGridLayoutManager2);
        mRecycler2.setAdapter(mTwoAdapter);
    }

    @Override
    public HallHonorPresenter createPresenter() {
        return new HallHonorPresenter();
    }

    @Override
    public HallHonorView createView() {
        return this;
    }


    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mRecycler1, error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 获取荣誉奖状数据
     * @param bean
     */
    @Override
    public void getHallHonorData(HallHonorListBean bean) {
        cancelProgress();
        if("100".equals(bean.getResult())){
            List<HallHonorBean> list1 = bean.getDatas();
            if(list1!=null && list1.size()>0){
                mList.clear();
                mList2.clear();
                for(HallHonorBean h:list1){
                    if(h.isCustom()){
                        mList2.add(h);
                    }else{
                        mList.add(h);
                    }
                }
                if(mList.size()==0) {
                    mRecycler1.setVisibility(View.GONE);
                    mHeaderTv1.setVisibility(View.GONE);
                }
                mOneAdapter.notifyDataSetChanged();

                if(mList2.size()==0){
                    mRecycler2.setVisibility(View.GONE);
                    mHeaderTv2.setVisibility(View.GONE);
                }

                if(list1==null || list1.size()==0){
                    setNoData();
                }
                mTwoAdapter.notifyDataSetChanged();
            }else{
                setNoData();
            }
        }
    }

    private void setNoData() {
        mRecycler1.setVisibility(View.GONE);
        mHeaderTv1.setVisibility(View.GONE);
        mRecycler2.setVisibility(View.GONE);
        mHeaderTv2.setVisibility(View.GONE);
        // 弹框提示无数据
        WarnTipDialog.Builder builder=new WarnTipDialog.Builder();
        builder.setConfirmStr("确定")
                .setContentStr("还未获得任何徽章，请继续加油！")
                .setClickConfirmListener(new DialogConfirmClickListener<WarnTipDialog>() {
                    @Override
                    public void onDialogConfirmClickListener(WarnTipDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                }).Build(this).show();
    }

    class RecyclerViewOneAdapter extends RecyclerView.Adapter<RecyclerViewOneAdapter.MyViewHolder>{

        private Context mContext;

        public RecyclerViewOneAdapter(Context context){
            this.mContext=context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_hall_honor_item,parent,false);
            MyViewHolder viewHolder=new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            HallHonorBean item=mList.get(position);
            Glide.with(HallHonorActivity.this).load(Constants.SERVER_URL+item.getSmall()).into(holder.image);
            holder.name.setText(item.getName());
            if(!item.isCustom()) {
                holder.score.setText(item.getPercent()+"");
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name,score;
            ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                score=itemView.findViewById(R.id.score);
                image=itemView.findViewById(R.id.image);
            }
        }
    }

    class RecyclerViewTwoAdapter extends RecyclerView.Adapter<RecyclerViewTwoAdapter.MyViewHolder>{

        private Context mContext;

        public RecyclerViewTwoAdapter(Context context){
            this.mContext=context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_hall_honor_item2,parent,false);
            MyViewHolder viewHolder=new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            HallHonorBean item=mList.get(position);
            Glide.with(HallHonorActivity.this).load(Constants.SERVER_URL+item.getSmall()).into(holder.image);
            holder.name.setText(item.getName());
            if(!item.isCustom()) {
                holder.score.setText(item.getPercent()+"");
            }
        }

        @Override
        public int getItemCount() {
            return mList2.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name,score;
            ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                score=itemView.findViewById(R.id.score);
                image=itemView.findViewById(R.id.image);
            }
        }
    }
}
