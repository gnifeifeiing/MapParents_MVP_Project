package com.zele.maipuxiaoyuan.ui.homefrag;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.ClassScheduleAdapter;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ScheduleBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.views.DottedItemDecoration;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.ClassSchedulePresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.ClassScheduleView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      课程表
 * Autour：          LF
 * Date：            2018/12/10 14:37
 */
public class ClassScheduleActivity extends BaseActivity<ClassScheduleView, ClassSchedulePresenter> implements ClassScheduleView {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.classSche_day1Tv)
    TextView mDay1Tv;
    @BindView(R.id.classSche_manthTv)
    TextView mMonthTv;
    @BindView(R.id.classSche_day2Tv)
    TextView mDay2Tv;
    @BindView(R.id.classSche_day3Tv)
    TextView mDay3Tv;
    @BindView(R.id.classSche_day4Tv)
    TextView mDay4Tv;
    @BindView(R.id.classSche_day5Tv)
    TextView mDay5Tv;
    @BindView(R.id.classSche_day6Tv)
    TextView mDay6Tv;
    @BindView(R.id.classSche_day7Tv)
    TextView mDay7Tv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private String mSID;

    private ClassScheduleAdapter mAdapter;
    private Map<Integer,ScheduleBean.SchedulesBean.ArrayBean> mList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);
        ButterKnife.bind(this);
        initView();
        initData();
        initWeek();
    }

    private void initData() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("sid", mSID);
        mPresenter.getClassScheduleData(parameter);
    }

    private void initView() {
        titleTitleTv.setText("课程表");
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentsBean.getSid();

        mAdapter = new ClassScheduleAdapter(this, mList);
        // 声名为瀑布流的布局方式: 2列,垂直方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.addItemDecoration(new DottedItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //获取当前日期和星期
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-2;
        calendar.add(Calendar.DAY_OF_MONTH,-week);
        //计算出周一是几号
        int firstDay  = calendar.get(Calendar.DAY_OF_MONTH);
        mDay1Tv.setText(firstDay+"");
        mDay2Tv.setText((firstDay+1)+"");
        mDay3Tv.setText((firstDay+2)+"");
        mDay4Tv.setText((firstDay+3)+"");
        mDay5Tv.setText((firstDay+4)+"");
        mDay6Tv.setText((firstDay+5)+"");
        mDay7Tv.setText((firstDay+6)+"");

        int month  =  calendar.get(Calendar.MONTH) + 1;
        mMonthTv.setText(month+"");
    }

    @Override
    public ClassSchedulePresenter createPresenter() {
        return new ClassSchedulePresenter();
    }

    @Override
    public ClassScheduleView createView() {
        return this;
    }

    @Override
    public void onError(String error) {
        SnackbarUtil.ShortSnackbar(titleBackIv, error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 获取课程表数据
     *
     * @param scheduleBean
     */
    @Override
    public void getClassScheduleData(ScheduleBean scheduleBean) {
//        String content = "{\"result\":\"100\",\"schedules\":[{\"array\":[{\"cname\":\"数学\",\"classNo\":1,\"state\":1},{\"cname\":\"语文\",\"classNo\":2,\"state\":1},{\"cname\":\"班会\",\"classNo\":3,\"state\":1},{\"cname\":\"班会\",\"classNo\":4,\"state\":1},{\"cname\":\"数学\",\"classNo\":5,\"state\":1},{\"cname\":\"英语\",\"classNo\":6,\"state\":1},{\"cname\":\"劳动\",\"classNo\":7,\"state\":1}],\"weekin\":\"周一\"},{\"array\":[{\"cname\":\"数学\",\"classNo\":1,\"state\":1},{\"cname\":\"语文\",\"classNo\":2,\"state\":1},{\"cname\":\"美术\",\"classNo\":3,\"state\":1},{\"cname\":\"体育\",\"classNo\":4,\"state\":1},{\"cname\":\"数学\",\"classNo\":5,\"state\":1},{\"cname\":\"英语\",\"classNo\":6,\"state\":1},{\"cname\":\"语文\",\"classNo\":7,\"state\":1}],\"weekin\":\"周二\"},{\"array\":[{\"cname\":\"数学\",\"classNo\":1,\"state\":1},{\"cname\":\"劳动\",\"classNo\":2,\"state\":1},{\"cname\":\"体育\",\"classNo\":3,\"state\":1},{\"cname\":\"语文\",\"classNo\":4,\"state\":1},{\"cname\":\"数学\",\"classNo\":5,\"state\":1},{\"cname\":\"社团\",\"classNo\":6,\"state\":1},{\"cname\":\"社团\",\"classNo\":7,\"state\":1}],\"weekin\":\"周三\"},{\"array\":[{\"cname\":\"劳动\",\"classNo\":1,\"state\":1},{\"cname\":\"数学\",\"classNo\":2,\"state\":1},{\"cname\":\"科学\",\"classNo\":3,\"state\":1},{\"cname\":\"英语\",\"classNo\":4,\"state\":1},{\"cname\":\"语文\",\"classNo\":5,\"state\":1},{\"cname\":\"科学\",\"classNo\":6,\"state\":1},{\"cname\":\"语文\",\"classNo\":7,\"state\":1}],\"weekin\":\"周四\"},{\"array\":[{\"cname\":\"科学\",\"classNo\":1,\"state\":1},{\"cname\":\"英语\",\"classNo\":2,\"state\":1},{\"cname\":\"数学\",\"classNo\":3,\"state\":1},{\"cname\":\"美术\",\"classNo\":4,\"state\":1},{\"cname\":\"体育\",\"classNo\":5,\"state\":1},{\"cname\":\"社团\",\"classNo\":6,\"state\":1},{\"cname\":\"社团\",\"classNo\":7,\"state\":1}],\"weekin\":\"周五\"},{\"array\":[],\"weekin\":\"周六\"},{\"array\":[],\"weekin\":\"周日\"}]}";
//        ScheduleBean scheduleBean = JSON.parseObject(content, ScheduleBean.class);
        if ("100".equals(scheduleBean.getResult())) {
            List<ScheduleBean.SchedulesBean> schedules = scheduleBean.getSchedules();
            //遍历每一格
            ScheduleBean.SchedulesBean.ArrayBean emptyBean=null;
            for(int i=0;i<96;i++){
                if(i%8==0){
                    emptyBean=new ScheduleBean.SchedulesBean.ArrayBean();
                    emptyBean.setCname(i==0?"1":String.valueOf(i/8+1));
                    emptyBean.setHead(true);
                    mList.put(i,emptyBean);
                }else{
                    ScheduleBean.SchedulesBean item=schedules.get(i%8-1);
                    for(ScheduleBean.SchedulesBean.ArrayBean course:item.getArray()){
                        if(course.getClassNo()==(i/8+1)){
                            course.setHead(false);
                            mList.put(i,course);
                            break;
                        }
                    }
                }
            }
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        }else{
            SnackbarUtil.ShortSnackbar(titleBackIv, "获取失败！", Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }
}
