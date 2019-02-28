package com.zele.maipuxiaoyuan.ui.homefrag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.BooksAdapter;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.MessageClassesListviewBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.BooksPresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.BooksView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      作业
 * Autour：          LF
 * Date：            2018/11/17 9:37
 */
public class BooksActivity extends BaseActivity<BooksView, BooksPresenter> {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.book_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.book_refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.book_prelodIv)
    ImageView mPrelodIv;
    @BindView(R.id.book_introduceIv)
    ImageView mIntroduceIv;
    // 区分是作业通知或作业
    private String inType;
    private String mUID;

    List<MessageClassesListviewBean.MessagesBean> mList = new ArrayList<>();
    private BooksAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.bind(this);
        mUID = SharedPreferenceCache.getInstance().getPres("UserId");
        initView();
        initData(null);
    }

    private void initData(String time) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", mUID);
        parameter.put("type", "4");
        parameter.put("endTime", time == null ? "" : time);
        mPresenter.getBooksData(parameter);
    }

    private void initView() {
        inType = getIntent().getStringExtra("opop");
        if (inType.equals("011")) {
            titleTitleTv.setText("作业");
        } else {
            titleTitleTv.setText("作业通知");
        }
        // 是否是第一次进入
        String isFirstIn = SharedPreferenceCache.getInstance().getPres("isFirstInBooks");
        if (isFirstIn.equals("1")) {
            mIntroduceIv.setVisibility(View.VISIBLE);
        }else{
            mIntroduceIv.setVisibility(View.GONE);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BooksAdapter(this, mList, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                initData(null);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mList.size() > 0) {
                    initData(mList.get(mList.size() - 1).getAddDateTime());
                }
            }
        });
    }

    @Override
    public BooksPresenter createPresenter() {
        return new BooksPresenter();
    }

    @Override
    public BooksView createView() {
        return new BooksData();
    }

    @OnClick({R.id.title_backIv,R.id.book_introduceIv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.title_backIv:
                finish();
                break;
            case R.id.book_introduceIv:
                SharedPreferenceCache.getInstance().setPres("isFirstInBooks","0");
                mIntroduceIv.setVisibility(View.GONE);
                break;
        }
    }

    class BooksData implements BooksView {

        @Override
        public void onError(String error) {
            SnackbarUtil.ShortSnackbar(mRecyclerView, error, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void getBooksData(MessageClassesListviewBean bean) {
            if ("102".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mRecyclerView, "暂无数据", Snackbar.LENGTH_SHORT).show();
            } else if ("100".equals(bean.getResult())) {
                mList.addAll(bean.getMessages());
                if (mList == null || mList.size() == 0) {
                    mPrelodIv.setVisibility(View.VISIBLE);
                } else {
                    if (bean.getMessages() != null && bean.getMessages().size() > 0) {
                        mPrelodIv.setVisibility(View.GONE);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        SnackbarUtil.ShortSnackbar(mRecyclerView, "已加载全部数据", Snackbar.LENGTH_SHORT).show();
                    }
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadMore();
                }

            }
        }

        /**
         * 设置已读
         *
         * @param bean
         */
        @Override
        public void setReadByMsgId(BaseBean bean, int position) {
            if ("100".equals(bean.getResult())) {
                mAdapter.setItemStatus(position);
            }
        }
    }
}
