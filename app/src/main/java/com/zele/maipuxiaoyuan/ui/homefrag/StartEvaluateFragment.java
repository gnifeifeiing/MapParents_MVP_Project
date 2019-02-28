package com.zele.maipuxiaoyuan.ui.homefrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.EvaluateRecordAdapter;
import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateTagBean;
import com.zele.maipuxiaoyuan.common.interfaces.FragmentRefreshDataListener;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.StartEvaluatePresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.StartEvaluateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:      星级评价
 * Autour：          LF
 * Date：            2018/12/12 14:11
 */

public class StartEvaluateFragment extends BaseStartEvaluateFragment<StartEvaluateView,StartEvaluatePresenter> implements StartEvaluateView, FragmentRefreshDataListener {

    public View mView;
    public Context mContext;
    private StartEvaluateActivity mActivity;

    @BindView(R.id.startEvaFrg_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.startEvaFrg_refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    // 学生id
    private String mSID;
    // 历史学期id
    private String mTermId = "";
    // 当前学期id
    private String mCurrTermId = "1";
    private String mTagType="";
    private String mEndTime="";
    private String mPosition;

    private EvaluateRecordAdapter mAdapter;
    private List<EvaluateRecordBean.HonorsBean> mList =new ArrayList<>();

    private boolean mIsEnd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_start_evaluate, container, false);
        mContext = getActivity();
        mActivity= (StartEvaluateActivity) getActivity();
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
//        loadData();
    }

    private void initView() {
        Bundle bundle=getArguments();
        if(bundle!=null){
            mPosition=bundle.getString("position");
            mSID=bundle.getString("sid");
            mTagType=bundle.getString("type");
            mEndTime=bundle.getString("endTime");
            mCurrTermId=bundle.getString("curt");
            mTermId=bundle.getString("term");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EvaluateRecordAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mEndTime="";
                loadData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!mIsEnd){
                    mEndTime = mList.size()>0?mList.get(mList.size()-1).getAddDateTime():"";
                    loadData();
                }else {
                    finishLoading();
                    mRefreshLayout.setNoMoreData(true);
                }
            }
        });
    }

    private void finishLoading() {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public StartEvaluatePresenter createPresenter() {
        return new StartEvaluatePresenter();
    }

    @Override
    public StartEvaluateView createView() {
        return this;
    }

    @Override
    protected void loadData() {
        Map<String, String> parameter = new HashMap<>();
        // 学生id
        parameter.put("sid", mSID);
        // 综评大类
        parameter.put("type", mTagType);
        // 综评中类
        parameter.put("subType", "");
        if (!TextUtils.isEmpty(mEndTime)){
            parameter.put("endTime", mEndTime);
        }
        // 空和1表示当前学期，0标识历史学期
        parameter.put("curt", mCurrTermId);
        // 学期ID
        parameter.put("term", mTermId);
        mPresenter.getStudHonors(parameter);
    }

    @Override
    public void onError(String error) {
        finishLoading();
        SnackbarUtil.ShortSnackbar(mRecyclerView, error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * fragment用不到
     * @param bean
     */
    @Override
    public void getTagList(EvaluateTagBean bean) {

    }

    /**
     * 综合评价评星详情根据班级请求数据
     * @param bean
     */
    @Override
    public void getStudHonors(EvaluateRecordBean bean) {
        finishLoading();
        if(bean.getResult().equals("100")){
            if (bean.getHonors()!=null){
                mIsEnd = false;
                mList.addAll(bean.getHonors());
                mAdapter.setData(mList);
                mAdapter.notifyDataSetChanged();
            }else {
                mIsEnd = true;
            }
        }else{
            SnackbarUtil.ShortSnackbar(mRecyclerView, "加载失败！", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refreshData(String sid,String currTermId,String termId) {
        if(getUserVisibleHint()){
            mSID=sid;
            mCurrTermId=currTermId;
            mTermId=termId;
            mEndTime="";
            mList.clear();
            mAdapter.notifyDataSetChanged();
            loadData();
        }
    }
}
