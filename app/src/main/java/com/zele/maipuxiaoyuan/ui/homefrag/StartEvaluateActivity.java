package com.zele.maipuxiaoyuan.ui.homefrag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.EvaluateRecordAdapter;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateTagBean;
import com.zele.maipuxiaoyuan.bean.TermBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.dialog.DropDownPopupWindow;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.StartEvaluatePresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.StartEvaluateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      星级评价
 * Autour：          LF
 * Date：            2018/12/12 13:55
 */
public class StartEvaluateActivity extends BaseActivity<StartEvaluateView,StartEvaluatePresenter> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.startEva_term)
    ImageView mTermIv;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.startEvaFrg_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.startEvaFrg_refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private List<EvaluateTagBean.TypesBean> mListTitles = new ArrayList<>();;

    // 学生id
    private String mSID;
    // 历史学期id
    private String mTermId = "";
    // 当前学期id
    private String mCurrTermId = "1";
    private String mTagType="";
    private String mEndTime="";
    private TermBaseAdapter mTermAdapter;
    private List<TermBean.TermsBean> mTerms = new ArrayList<>();

    private EvaluateRecordAdapter mAdapter;
    private List<EvaluateRecordBean.HonorsBean> mList =new ArrayList<>();

    private boolean mIsEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_evaluate);
        ButterKnife.bind(this);
        initView();
        initTagData();
        initData();
    }

    private void initData() {
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

    private void initTagData() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("sid", mSID);
        mPresenter.getTagList(parameter);
    }

    private void initView() {
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentsBean.getSid();
        // 学期列表
        String studentTerms = SharedPreferenceCache.getInstance().getPres("StudentTerms");
        if (!TextUtils.isEmpty(studentTerms)) {
            TermBean termBean = new Gson().fromJson(studentTerms, TermBean.class);
            mTerms = termBean.getTerms();
            TermBean.TermsBean termsBean = mTerms.get(mTerms.size() - 1);
            mTermId = String.valueOf(termsBean.getMid());
            mCurrTermId = String.valueOf(termsBean.getCur());
            tvTitle.setText(termsBean.getName());
        }
        mTermAdapter = new TermBaseAdapter();
        mTermAdapter.setPos(mTerms.size() > 0 ? mTerms.size() - 1 : 0);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EvaluateRecordAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mEndTime="";
                initData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!mIsEnd){
                    mEndTime = mList.size()>0?mList.get(mList.size()-1).getAddDateTime():"";
                    initData();
                }else {
                    finishLoading();
                    mRefreshLayout.setNoMoreData(true);
                }
            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mEndTime="";
                mIsEnd = false;
                mRefreshLayout.setNoMoreData(false);
                int position=tab.getPosition();
                if(position==0){
                    mTagType="";
                }else{
                    mTagType=String.valueOf(mListTitles.get(tab.getPosition()).getType());
                }
                mList.clear();
                mAdapter.notifyDataSetChanged();
                initData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public StartEvaluatePresenter createPresenter() {
        return new StartEvaluatePresenter();
    }

    @Override
    public StartEvaluateView createView() {
        return new MyCallBack();
    }

    @OnClick({R.id.iv_back, R.id.startEva_term})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.startEva_term:
                changeTerm();
                break;
        }
    }

    /**
     * 更换学期
     */
    private void changeTerm() {
        DropDownPopupWindow.Builder builder = new DropDownPopupWindow.Builder();
        builder.setAdapter(mTermAdapter)
                .setAlpha(0.3f).setAnchor(mTermIv)
                .setClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTermAdapter.setPos(position);
                        TermBean.TermsBean termsBean = mTerms.get(position);
                        mTermId = String.valueOf(termsBean.getMid());
                        mCurrTermId = String.valueOf(termsBean.getCur());
                        tvTitle.setText(termsBean.getName());
                        mEndTime="";
                        mIsEnd = false;
                        mRefreshLayout.setNoMoreData(false);
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        initData();
                    }
                });
        builder.Build().show(this);
    }


    class MyCallBack implements StartEvaluateView{

        @Override
        public void onError(String error) {
            SnackbarUtil.ShortSnackbar(mTabLayout, error, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void getTagList(EvaluateTagBean bean) {
            if(bean.getResult().equals("100")){
                if (bean!=null&&bean.getTypes()!=null) {
                    mListTitles = bean.getTypes();
                    EvaluateTagBean.TypesBean typesBean=new EvaluateTagBean.TypesBean();
                    typesBean.setTagName("全部评价");
                    mListTitles.add(0,typesBean);
                    initTab();
                }
            }else{
                SnackbarUtil.ShortSnackbar(mTabLayout, "获取标签失败！", Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public void getStudHonors(EvaluateRecordBean bean) {
            finishLoading();
            if(bean.getResult().equals("100")){
                if (bean.getHonors()!=null&&bean.getHonors().size()>0){
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
    }

    private void initTab() {
        for (int i = 0; i < mListTitles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mListTitles.get(i).getTagName()));
        }
    }

    private void finishLoading() {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }


    class TermBaseAdapter extends BaseAdapter {

        private int pos = 0;

        @Override
        public int getCount() {
            return mTerms.size();
        }

        @Override
        public String getItem(int i) {
            return mTerms.get(i).getName();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                TextView view1 = new TextView(mContext);
                view1.setTextSize(14);
                view1.setTextColor(getResources().getColor(R.color.text_6));
                view1.setGravity(Gravity.CENTER);
                view1.setWidth(CommonUtils.dip2px(mContext, 120));
                view1.setHeight(CommonUtils.dip2px(mContext, 40));
                view = view1;
            }
            TextView tv = (TextView) view;
            tv.setText(getItem(i).replaceAll("\\_\\d", ""));
            if (i == pos) {
                tv.setTextColor(getResources().getColor(R.color.primaryBg));
            } else {
                tv.setTextColor(getResources().getColor(R.color.text_3));
            }
            return view;
        }

        public void setPos(int position) {
            this.pos = position;
            notifyDataSetChanged();
        }
    }
}
