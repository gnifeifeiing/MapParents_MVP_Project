package com.zele.maipuxiaoyuan.ui.homefrag;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.GroupRecordBean;
import com.zele.maipuxiaoyuan.bean.GrowColumDataBean;
import com.zele.maipuxiaoyuan.bean.RadarDataBean;
import com.zele.maipuxiaoyuan.bean.TermBean;
import com.zele.maipuxiaoyuan.bean.TitleValueEntity;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.dialog.DropDownPopupWindow;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.views.SpiderWebChart;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.GrowthHistoryPresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.GrowthHistoryView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      成长历程
 * Autour：          LF
 * Date：            2018/11/19 11:42
 */
public class GrowthHistoryActivity extends BaseActivity<GrowthHistoryView, GrowthHistoryPresenter> implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.change_term)
    ImageView mChangeTerm;
    @BindView(R.id.growthHistory_radarCtv)
    CheckedTextView mRadarCtv;
    @BindView(R.id.growthHistory_lineCtv)
    CheckedTextView mLineCtv;
    @BindView(R.id.growthHistory_radarChart)
    SpiderWebChart mRadarChart;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private LineChart mLineChart;
    private XAxis xAxis;
    private YAxis leftYAxis;

    // 学生id
    private String mSID;
    // 历史学期id
    private String mTermId = "";
    // 当前学期id
    private String mCurrTermId = "1";
    private String mType="";
    private int mPage = 1;
    private int mTotalPage = 1;
    private MyAdapter mAdapter;
    List<GroupRecordBean.HonorsBean> mList = new ArrayList<>();
    ;
    private TermBaseAdapter mTermAdapter;
    private List<TermBean.TermsBean> mTerms = new ArrayList<>();
    private List<GrowColumDataBean.ItemsBean> mItems = new ArrayList<>();
    List<Integer> mZonglan = new ArrayList<>();

    private String[] mTabs = {
            "综合评价", "美德星", "智慧星", "体健星", "文娱星", "勤劳星"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_history);
        ButterKnife.bind(this);
        initView();
        initData();
        initTabData();
    }

    private void initData() {
        mZonglan.clear();
        mItems.clear();
        Map<String, String> paramter = new HashMap<>();
        paramter.put("sid", mSID);
        paramter.put("curt", mCurrTermId);
        paramter.put("term", mTermId);
        // 获取雷达图数据
        mPresenter.getRadarData(paramter);
        // 获取柱状图数据
        mPresenter.getColumData(paramter);
    }

    private void initTabData(){
        Map<String, String> paramter = new HashMap<>();
        paramter.put("sid", mSID);
        paramter.put("curt", mCurrTermId);
        paramter.put("term", mTermId);
        paramter.put("type", mType);
        paramter.put("page", String.valueOf(mPage));
        // 获取列表数据
        mPresenter.getGroupRecord(paramter);
    }

    private void initView() {
        // 学期列表
        String studentTerms = SharedPreferenceCache.getInstance().getPres("StudentTerms");
        if (!TextUtils.isEmpty(studentTerms)) {
            TermBean termBean = new Gson().fromJson(studentTerms, TermBean.class);
            mTerms = termBean.getTerms();
            TermBean.TermsBean termsBean = mTerms.get(mTerms.size() - 1);
            mTermId = String.valueOf(termsBean.getMid());
            mCurrTermId = String.valueOf(termsBean.getCur());
        }
        mTermAdapter = new TermBaseAdapter();
        mTermAdapter.setPos(mTerms.size() > 0 ? mTerms.size() - 1 : 0);

        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentsBean.getSid();

        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[4]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabs[5]));

        mTabLayout.addOnTabSelectedListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setEnableFooterTranslationContent(false);//是否上拉Footer的时候向上平移列表或者内容
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                if (mPage > mTotalPage) {
                    SnackbarUtil.ShortSnackbar(mRecyclerView, "已经到最后一页!", Snackbar.LENGTH_SHORT).show();
                    mRefreshLayout.finishLoadMore();
                    return;
                }
                initTabData();
            }
        });
    }

    @Override
    public GrowthHistoryPresenter createPresenter() {
        return new GrowthHistoryPresenter();
    }

    @Override
    public GrowthHistoryView createView() {
        return new GrowthHistoryCallBack();
    }

    @OnClick({R.id.iv_back, R.id.change_term, R.id.growthHistory_radarCtv, R.id.growthHistory_lineCtv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.change_term:
                changeTerm();
                break;
            case R.id.growthHistory_radarCtv:
                mRadarCtv.setChecked(true);
                mLineCtv.setChecked(false);
                mLineChart.setVisibility(View.GONE);
                mRadarChart.setVisibility(View.VISIBLE);
                break;
            case R.id.growthHistory_lineCtv:
                mRadarCtv.setChecked(false);
                mLineCtv.setChecked(true);
                mLineChart.setVisibility(View.VISIBLE);
                mRadarChart.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:
                mType="";
                break;
            case 1:
                mType="1000";
                break;
            case 2:
                mType="2000";
                break;
            case 3:
                mType="3000";
                break;
            case 4:
                mType="4000";
                break;
            case 5:
                mType="5000";
                break;
        }
        mList.clear();
        mAdapter.notifyDataSetChanged();
        mPage=1;
        mTotalPage=1;
        initTabData();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class GrowthHistoryCallBack implements GrowthHistoryView {

        @Override
        public void onError(String error) {
            SnackbarUtil.ShortSnackbar(mRadarChart, error, Snackbar.LENGTH_SHORT).show();
        }

        /**
         * 获取雷达图数据
         *
         * @param bean
         */
        @Override
        public void getRadarData(RadarDataBean bean) {
            if ("100".equals(bean.getResult())) {
                RadarDataBean.StudentBean student = bean.getStudent();
                // moral, intel, physic, cultrue, labour;
                mZonglan.add(0, student.getMoral());//美德
                mZonglan.add(1, student.getLabour());//劳动
                mZonglan.add(2, student.getCulture());//文娱
                mZonglan.add(3, student.getPhysic());//体育
                mZonglan.add(4, student.getIntel());//智慧
                initSpiderWebChart();
            } else {
                SnackbarUtil.ShortSnackbar(mRadarChart, "暂时还没有数据", Snackbar.LENGTH_SHORT).show();
            }
        }

        /**
         * 获取成长总览柱状图
         *
         * @param bean
         */
        @Override
        public void getColumData(GrowColumDataBean bean) {
            if ("100".equals(bean.getResult())) {
                mItems = bean.getItems();
                if (mItems != null && mItems.size() > 0) {
                    mLineChart = findViewById(R.id.growthHistory_lineChart);
                    initChart();
                    showLineChart(mItems, "", Color.CYAN);
                }
            }
        }

        @Override
        public void getGroupRecord(GroupRecordBean bean) {
            if (bean != null) {
                mTotalPage = bean.getPageTotal();
                List<GroupRecordBean.HonorsBean> honors = bean.getHonors();
                if (honors != null && honors.size() > 0) {
                    mList.addAll(honors);
                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    SnackbarUtil.ShortSnackbar(mRecyclerView, "未获取到数据!", Snackbar.LENGTH_SHORT).show();
                }
            }
            mRefreshLayout.finishLoadMore();
        }
    }

    /**
     * 初始化雷达图
     */
    private void initSpiderWebChart() {
        List<TitleValueEntity> data = new ArrayList<TitleValueEntity>();
        data.add(new TitleValueEntity(getResources().getString(
                R.string.spiderwebchart_title1), mZonglan.get(0)));
        data.add(new TitleValueEntity(getResources().getString(
                R.string.spiderwebchart_title2), mZonglan.get(1)));
        data.add(new TitleValueEntity(getResources().getString(
                R.string.spiderwebchart_title3), mZonglan.get(2)));
        data.add(new TitleValueEntity(getResources().getString(
                R.string.spiderwebchart_title4), mZonglan.get(3)));
        data.add(new TitleValueEntity(getResources().getString(
                R.string.spiderwebchart_title5), mZonglan.get(4)));
        if (mRadarChart != null) {
            mRadarChart.setData(data);
            mRadarChart.setLatitudeNum(3);
            mRadarChart.invalidate();
        }
    }

    /**
     * 初始化图表
     */
    private void initChart() {
        int gray_color = getResources().getColor(R.color.text_9);
        /***图表设置***/
        //是否展示网格线
        mLineChart.setDrawGridBackground(false);
        //是否显示边界
        mLineChart.setDrawBorders(false);
        //是否可以拖动
        mLineChart.setDragEnabled(false);
        //是否有触摸事件
        mLineChart.setTouchEnabled(false);

        //设置XY轴动画效果
        mLineChart.animateY(2500);
        mLineChart.animateX(1500);

        mLineChart.getDescription().setEnabled(false);

        /***XY轴的设置***/
        xAxis = mLineChart.getXAxis();
        leftYAxis = mLineChart.getAxisLeft();
        mLineChart.getAxisRight().setEnabled(false);//不显示右边Y轴
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(1f);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(gray_color);
        xAxis.setAxisLineColor(gray_color);
        xAxis.setAxisLineWidth(1f);
        xAxis.setTextSize(10f);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String month = "月";
                try {
                    month = mItems.get((int) value).getMonth() + "月";
                } catch (Exception e) {

                }
                return month;
            }
        };
        //设置X轴的数据
        xAxis.setValueFormatter(formatter);
        xAxis.setDrawGridLines(false);//设置纵向线条
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(1f);
        leftYAxis.setDrawGridLines(false);//不显示横线
        leftYAxis.setTextColor(gray_color);
        leftYAxis.setAxisLineColor(gray_color);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setTextSize(10f);
        /***折线图例 标签 设置***/
        mLineChart.getLegend().setEnabled(false);
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(List<GrowColumDataBean.ItemsBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            GrowColumDataBean.ItemsBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Log.w("res_data_month", data.getIntegral() + "");
            Entry entry = new Entry(i, (float) data.getIntegral());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        int bg_color = getResources().getColor(R.color.primaryBg);
        lineDataSet.setColor(bg_color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(4f);//外圆半径
        // lineDataSet.setCircleHoleRadius();
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleHoleRadius(3f);//内圆的半径
        lineDataSet.setCircleColor(bg_color);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(bg_color);
        //格式化显示的数据标签
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "星值" + ((int) entry.getY());
            }
        });
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.gradient_color_green));
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 更换学期
     */
    private void changeTerm() {
        DropDownPopupWindow.Builder builder = new DropDownPopupWindow.Builder();
        builder.setAdapter(mTermAdapter)
                .setAlpha(0.3f).setAnchor(mChangeTerm)
                .setClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTermAdapter.setPos(position);
                        TermBean.TermsBean termsBean = mTerms.get(position);
                        mTermId = String.valueOf(termsBean.getMid());
                        mCurrTermId = String.valueOf(termsBean.getCur());
                        initData();
                        mList.clear();
                        mPage=1;
                        mTotalPage=1;
                        initTabData();
                    }
                });
        builder.Build().show(this);
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


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private Context mContext;
        private List<GroupRecordBean.HonorsBean> mList;

        public MyAdapter(Context context, List<GroupRecordBean.HonorsBean> list) {
            this.mContext = context;
            this.mList = list;
        }

        public void setData(List<GroupRecordBean.HonorsBean> list) {
            this.mList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_pingjia_record, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            GroupRecordBean.HonorsBean item = mList.get(position);
            int type = item.getType();
            int pic = R.mipmap.ic_zhihuixing;
            switch (type) {
                case 1000:
                    pic = R.mipmap.ic_meidexing;
                    break;
                case 2000:
                    pic = R.mipmap.ic_zhihuixing;
                    break;
                case 3000:
                    pic = R.mipmap.ic_wenyuxing;
                    break;
                case 4000:
                    pic = R.mipmap.ic_tijianxing;
                    break;
                case 5000:
                    pic = R.mipmap.ic_qinlaoxing;
                    break;
            }
            holder.contentTv.setText(item.getShowStr());
            holder.startIv.setImageResource(pic);
            holder.startIv.setVisibility(View.GONE);
            holder.dateTv.setText(item.getAddDate().substring(5));
            holder.scoreTv.setText(String.valueOf(item.getPoint()));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView startIv;
            TextView dateTv;
            TextView contentTv;
            TextView scoreTv;

            public MyViewHolder(View itemView) {
                super(itemView);
                startIv = itemView.findViewById(R.id.starIv);
                dateTv = itemView.findViewById(R.id.dateTv);
                contentTv = itemView.findViewById(R.id.contentTv);
                scoreTv = itemView.findViewById(R.id.scoreTv);
            }
        }
    }

}
