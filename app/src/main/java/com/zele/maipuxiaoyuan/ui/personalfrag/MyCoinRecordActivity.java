package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.CoinRecordAdapter;
import com.zele.maipuxiaoyuan.bean.CoinRecordBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.MyCoinRecordPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.MyCoinRecordView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      我的麦粒
 * Autour：          LF
 * Date：            2018/11/23 11:44
 */
public class MyCoinRecordActivity extends BaseActivity<MyCoinRecordView, MyCoinRecordPresenter> implements MyCoinRecordView {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.mailiRecord_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mailiRecord_refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int mPage = 1;
    private int mTotalPage = 1;
    private String mUID;

    private CoinRecordAdapter mAdapter;
    private List<CoinRecordBean.DatasBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coin_record);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        showProgress("正在加载...");
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", mUID);
        parameter.put("page", String.valueOf(mPage));
        mPresenter.getMailiList(parameter);
    }

    private void initView() {
        titleTitleTv.setText("我的麦粒");
        mUID = SharedPreferenceCache.getInstance().getPres("UserId");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CoinRecordAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                initData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                if (mPage > mTotalPage) {
                    SnackbarUtil.ShortSnackbar(mRefreshLayout, "已经到最后一页", Snackbar.LENGTH_SHORT).show();
                    mRefreshLayout.finishLoadMore();
                    mRefreshLayout.finishRefresh();
                    return;
                }
                initData();
            }
        });
    }

    @Override
    public MyCoinRecordPresenter createPresenter() {
        return new MyCoinRecordPresenter();
    }

    @Override
    public MyCoinRecordView createView() {
        return this;
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        SnackbarUtil.ShortSnackbar(mRefreshLayout, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getMailiList(CoinRecordBean bean) {
        cancelProgress();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        if ("100".equals(bean.getResult())) {
            mList.addAll(bean.getDatas());
            mTotalPage = bean.getPageTotal();
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }
}
