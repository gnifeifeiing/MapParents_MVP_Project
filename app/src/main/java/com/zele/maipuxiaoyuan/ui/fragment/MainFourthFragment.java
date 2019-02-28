package com.zele.maipuxiaoyuan.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.ClassCircleAdapter;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ClassCircleBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.common.base.BaseFragment;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.fourthfrag.ClassCirclePresenter;
import com.zele.maipuxiaoyuan.mvp.view.fourthfrag.ClassCircleView;
import com.zele.maipuxiaoyuan.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:      班级圈
 * Autour：          LF
 * Date：            2018/1/4 16:56
 */

public class MainFourthFragment extends BaseFragment<ClassCircleView, ClassCirclePresenter> implements ClassCircleView, TabLayout.OnTabSelectedListener {

    public View mView;
    public Context mContext;
    private MainActivity mActivity;
    Unbinder unbinder;

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.patriarchShare_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.patriarchShare_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.patriarchShare_refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.patriarchShare_errorRefreshRl)
    RelativeLayout mErrorRefreshRl;
    @BindView(R.id.patriarchShare_circleHeadIV)
    ImageView mCircleHeadIV;
    @BindView(R.id.patriarchShare_msgTv)
    TextView mMsgTv;
    @BindView(R.id.patriarchShare_remindRl)
    RelativeLayout mRemindRl;

    private ClassCircleAdapter mAdapter;
    List<ClassCircleBean.DatasBean> mList = new ArrayList<>();
    private ClassCircleBean mUnreadData;
    private List<ClassCircleBean.UnReadBean.UnreadData> mUnreadMsg;

    private String mSID="";
    private String mUID="";
    private String mClassID="";
    private String mType="1";

    private int mPage=1;
    private int mPageTotal=1;

    private String[] mTabs = {
            "班级", "学生动态", "麦励", "我的发布"
    };

    private boolean mIsFirstLoad=true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_fourth, container, false);
        mContext = getActivity();
        mActivity = (MainActivity) getActivity();
        unbinder = ButterKnife.bind(this, mView);
        titleBackIv.setVisibility(View.GONE);
        titleTitleTv.setText("班级圈");
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListData();
    }

    private void initListData() {
        if(mIsFirstLoad){
            mActivity.showProgress("加载中...");
        }
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", mUID);
        parameter.put("queryType", mType);
        parameter.put("sid", mSID);
        parameter.put("classId", mClassID);
        parameter.put("page", String.valueOf(mPage));
        mPresenter.getCircleData(parameter);
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        BindStudentsBean.StudentsBean studentBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentBean.getSid();
        mClassID=studentBean.getClassId();
        LoginUserBean.User userBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("UserBean"),
                new TypeReference<LoginUserBean.User>() {
                });
        mUID=userBean.getUid();

        
        mAdapter = new ClassCircleAdapter(getActivity(), mList, width);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setEnableFooterTranslationContent(false);//是否上拉Footer的时候向上平移列表或者内容
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                mPage=1;
                mPageTotal=1;
                initListData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                if(mPage>mPageTotal){
                    SnackbarUtil.ShortSnackbar(mRefreshLayout, "已经到最后一页!", Snackbar.LENGTH_SHORT).show();
                    mRefreshLayout.finishLoadMore();
                    mRefreshLayout.finishRefresh();
                    return;
                }
                initListData();
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[3]));
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public ClassCirclePresenter createPresenter() {
        return new ClassCirclePresenter();
    }

    @Override
    public ClassCircleView createView() {
        return this;
    }


    @Override
    public void onError(String error) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mActivity.cancelProgress();
        SnackbarUtil.ShortSnackbar(mRefreshLayout, error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 获取班级圈数据
     * @param bean
     */
    @Override
    public void getCircleData(ClassCircleBean bean) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mIsFirstLoad=false;
        mActivity.cancelProgress();
        if ("100".equals(bean.getResult())) {
            mPageTotal = bean.getPageTotal();
            //未读消息
            mUnreadData = bean;
            mUnreadMsg = bean.getUnRead().getData();
            if (mUnreadMsg !=null&& mUnreadMsg.size()>0){
                //有未读消息
                mRemindRl.setVisibility(View.VISIBLE);
                //获取第一条数据的头像
                String path = Constants.SERVER_URL+mUnreadData.getUnRead().getData().get(0).getAvatar();
                Glide.with(mContext).load(path).into(mCircleHeadIV);
                mMsgTv.setText(mUnreadMsg.size()+"条新消息");
            }else {
                //没有未读消息
                mRemindRl.setVisibility(View.GONE);
            }
            //消息列表处理
            List<ClassCircleBean.DatasBean> list = bean.getDatas();
            if (list != null && list.size() > 0) {
                mList.addAll(list);
                mAdapter.setData(mList);
                mAdapter.notifyDataSetChanged();
            }else {
                //已经没有数据了
                SnackbarUtil.ShortSnackbar(mRefreshLayout, "没有数据了", Snackbar.LENGTH_SHORT).show();
            }
        }else {
            //显示加载失败的页面
            mErrorRefreshRl.setVisibility(View.VISIBLE);
            mErrorRefreshRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.clear();
                    mPage=1;
                    mPageTotal=1;
                    initListData();
                }
            });
        }
    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:
                mType="1";
                break;
            case 1:
                mType="2";
                break;
            case 2:
                mType="3";
                break;
            case 3:
                mType="4";
                break;
        }
        mIsFirstLoad=true;
        mList.clear();
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        mPage=1;
        mPageTotal=1;
        initListData();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
