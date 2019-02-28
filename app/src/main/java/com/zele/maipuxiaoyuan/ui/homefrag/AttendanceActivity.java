package com.zele.maipuxiaoyuan.ui.homefrag;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.AttenDanceBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.AttendancePresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.AttendanceView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      考勤
 * Autour：          LF
 * Date：            2018/11/15 15:43
 */
public class AttendanceActivity extends BaseActivity<AttendanceView, AttendancePresenter> implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener, View.OnClickListener {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.attendance_monthDayTv)
    TextView mMonthDayTv;
    @BindView(R.id.attendance_yearTv)
    TextView mYearTv;
    @BindView(R.id.attendance_lunarTv)
    TextView mLunarTv;
    @BindView(R.id.attendance_currentDayTv)
    TextView mCurrentDayTv;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.attendance_headIv)
    ImageView mHeadIv;
    @BindView(R.id.attendance_teacherNameTv)
    TextView mTeacherNameTv;
    @BindView(R.id.attendance_timeTv)
    TextView mTimeTv;
    @BindView(R.id.attendance_attendTv)
    TextView mAttendTv;
    @BindView(R.id.attendance_head2Iv)
    ImageView mHead2Iv;
    @BindView(R.id.attendance_teacherName2Tv)
    TextView mTeacherName2Tv;
    @BindView(R.id.attendance_time2Tv)
    TextView mTime2Tv;
    @BindView(R.id.attendance_attend2Tv)
    TextView mAttend2Tv;
    @BindView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    @BindView(R.id.attendance_explainTv)
    TextView mExplainTv;
    @BindView(R.id.attendance_lateTimesTv)
    TextView mLateTimesTv;
    @BindView(R.id.attendance_qjTimesTv)
    TextView mQjTimesTv;

    private int mYear;
    private String mSID;
    private String mStudentName;
    private String mParCurrentDate;

    private AttenDanceBean.DatasBean mDatas;
    private HashMap<Integer, AttenDanceBean.DatasBean.ListBean> mMonthData=new HashMap<>();
    // 判断某一天考勤是否无数据
    private boolean mIsError=false;
    private Map<String, Calendar> mMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        titleTitleTv.setText("考勤");
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentsBean.getSid();
        if (studentsBean.getUserName()!=null){
            mStudentName=studentsBean.getUserName();
            mExplainTv.setText(studentsBean.getUserName() +"同学，迟到");
        }

        mMonthDayTv.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mYearTv.setText(String.valueOf(mCalendarView.getCurYear()));
        mLunarTv.setText("今日");
        mCurrentDayTv.setText(String.valueOf(mCalendarView.getCurDay()));
        mYear = mCalendarView.getCurYear();

        int maxDays = CommonUtils.getCurrentMonthLastDay();
        // 设置calendar范围
        mCalendarView.setRange(mYear - 2, 1, 1, mYear, mCalendarView.getCurMonth(), maxDays);

        Date date = new Date();
        mParCurrentDate = new SimpleDateFormat("yyyy-MM").format(date);
    }

    private void initData() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("sid", mSID);
        parameter.put("givenDate", mParCurrentDate + "-01");
        mPresenter.getAttendanceData(parameter);
    }

    private void initListener() {
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mMonthDayTv.setOnClickListener(this);
    }

    @Override
    public AttendancePresenter createPresenter() {
        return new AttendancePresenter();
    }

    @Override
    public AttendanceView createView() {
        return new GetAttendanceData();
    }

    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }

    /**
     * 获取考勤数据
     */
    class GetAttendanceData implements AttendanceView {

        @Override
        public void onError(String error) {
            SnackbarUtil.ShortSnackbar(titleBackIv, error, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void getAttendanceData(AttenDanceBean bean) {
            if ("100".equals(bean.getResult())) {
                mDatas = bean.getDatas();
                //当月数据统计,迟到次数,请假次数
                mLateTimesTv.setText(String.valueOf(mDatas.getCd()));
                mQjTimesTv.setText(String.valueOf(mDatas.getQj()));
                List<AttenDanceBean.DatasBean.ListBean> list = mDatas.getList();

                for (AttenDanceBean.DatasBean.ListBean item : list) {
                    String[] split = item.getDate().split("-");
                    if (split.length == 3) {
                        int year=Integer.parseInt(split[0]);
                        int month=Integer.parseInt(split[1]);
                        int day = Integer.parseInt(split[2]);
                        //0未到1已到2请假3迟到,优先级：请假，迟到，已到，未到是老师没发状态
                        if ("2".equals(item.getAm()) || "2".equals(item.getPm())) {
                            mMap.put(getSchemeCalendar(year, month, day, 0xFFaacc44, "假").toString(),
                                    getSchemeCalendar(year, month, day, 0xFFaacc44, "假"));
                        } else if ("3".equals(item.getAm()) || "3".equals(item.getPm())) {
                            mMap.put(getSchemeCalendar(year, month, day, 0xFFedc56d, "迟").toString(),
                                    getSchemeCalendar(year, month, day, 0xFFedc56d, "迟"));
                        } else{
                            mMap.put(getSchemeCalendar(year, month, day, 0xFF40db25, "到").toString(),
                                    getSchemeCalendar(year, month, day, 0xFF40db25, "到"));
                        }
                        //点击某天日期数据时候，显示来校信息
                        mMonthData.put(day, item);
                    }
                }
                mCalendarView.setSchemeDate(mMap);
                refreshDayData(mCalendarView.getCurDay());
                mIsError = false;
            } else {
                mLateTimesTv.setText("0");
                mQjTimesTv.setText("0");
                mIsError = true;
                Toast toast = Toast.makeText(AttendanceActivity.this, "没有查询到数据", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    /**
     * 刷新某天的数据
     * @param day
     */
    private void refreshDayData(int day) {
        try {
            AttenDanceBean.DatasBean.ListBean listBean = mMonthData.get(day);
            String amType = listBean.getAm();
            String am,pm;
            if (!TextUtils.isEmpty(amType)){
                String amTime = "";
                if ("0".equals(amType)){
                    am ="家长您好：老师暂时未发送考勤状态!";
                }else {
                    amTime = listBean.getAmSendTime();
                    am = "家长您好："+mStudentName+"同学于"+mParCurrentDate+"-"+day+"日上午"+processType(amType)+"!";
                }
                mAttendTv.setText(am);
                mTimeTv.setText(amTime);
                mTeacherNameTv.setText(mDatas.getTeacherName()==null?"班主任":mDatas.getTeacherName());

                RequestBuilder builder=Glide.with(mContext).load(R.mipmap.ease_default_avatar);
                Glide.with(AttendanceActivity.this)
                        .load(Constants.SERVER_URL+mDatas.getAvatar())
                        .error(builder)
                        .into(mHeadIv);
            }
            String pmType = listBean.getPm();
            if (!TextUtils.isEmpty(pmType)){
                String pmTime = "";
                if ("0".equals(pmType)){
                    pm ="家长您好：老师暂时未发送考勤状态!";
                }else {
                    pmTime = listBean.getPmSendTime();
                    pm = "家长您好："+mStudentName+"同学于"+mParCurrentDate+"-"+day+"日下午"+processType(pmType)+"!";
                }
                mAttend2Tv.setText(pm);
                mTime2Tv.setText(pmTime);
                mTeacherName2Tv.setText(mDatas.getTeacherName()==null?"班主任":mDatas.getTeacherName());

                RequestBuilder builder=Glide.with(mContext).load(R.mipmap.ease_default_avatar);
                Glide.with(AttendanceActivity.this)
                        .load(Constants.SERVER_URL+mDatas.getAvatar())
                        .error(builder)
                        .into(mHead2Iv);
            }
        }catch (Exception e){
            String s="";
            if (mDatas!=null){
                s = mDatas.getTeacherName() == null ? "" : mDatas.getTeacherName();
            }
            mAttendTv.setText(s+"老师暂时未发送考勤状态!");
            mAttend2Tv.setText(s+"老师暂时未发送考勤状态!");
        }
    }

    private String processType(String am_type) {
        String result ="准时";
        switch (am_type){
            //0未到1已到2请假3迟到
            case "1":
                result ="准时到校";
                break;
            case "2":
                result ="请假";
                break;
            case "3":
                result ="迟到";
                break;
        }
        return result;
    }

    /*********************** -- calanderview监听 -- *************************/
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mLunarTv.setVisibility(View.VISIBLE);
        mYearTv.setVisibility(View.VISIBLE);
        mMonthDayTv.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mYearTv.setText(String.valueOf(calendar.getYear()));
        mLunarTv.setText(calendar.getLunar());
        mYear = calendar.getYear();
        refreshDayData(calendar.getDay());
    }

    @Override
    public void onYearChange(int year) {
        mYear=year;
    }

    @Override
    public void onMonthChange(int year, int month) {
        mYear=year;
        if(month<10){
            mParCurrentDate=String.valueOf(year)+"-0"+String.valueOf(month);
        }else{
            mParCurrentDate=String.valueOf(year)+"-"+String.valueOf(month);
        }
        mMap.clear();
        mMonthData.clear();
        initData();
    }

    @Override
    public void onClick(View v) {
        if (!mCalendarLayout.isExpand()) {
            mCalendarView.showYearSelectLayout(mYear);
            return;
        }
        mCalendarView.showYearSelectLayout(mYear);
        mLunarTv.setVisibility(View.GONE);
        mYearTv.setVisibility(View.GONE);
        mMonthDayTv.setText(String.valueOf(mYear));
    }

}
