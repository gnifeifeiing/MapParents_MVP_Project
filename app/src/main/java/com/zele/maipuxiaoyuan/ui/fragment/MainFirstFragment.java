package com.zele.maipuxiaoyuan.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.StudentsAdapter;
import com.zele.maipuxiaoyuan.bean.BannerBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.StudentInfoBean;
import com.zele.maipuxiaoyuan.bean.StudentsBean;
import com.zele.maipuxiaoyuan.common.base.BaseFragment;
import com.zele.maipuxiaoyuan.common.dialog.ChoiseListItemDialog;
import com.zele.maipuxiaoyuan.common.dialog.ConfirmTipDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.interfaces.DialogListItemClickListener;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.views.GlideImageLoader;
import com.zele.maipuxiaoyuan.mvp.presenter.firstfrag.MainFirstFragPresenter;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.MainFirstFragView;
import com.zele.maipuxiaoyuan.ui.homefrag.AttendanceActivity;
import com.zele.maipuxiaoyuan.ui.homefrag.BooksActivity;
import com.zele.maipuxiaoyuan.ui.homefrag.ClassScheduleActivity;
import com.zele.maipuxiaoyuan.ui.homefrag.GrowthHistoryActivity;
import com.zele.maipuxiaoyuan.ui.homefrag.HallHonorActivity;
import com.zele.maipuxiaoyuan.ui.homefrag.StartEvaluateActivity;
import com.zele.maipuxiaoyuan.ui.main.WebAcitivty;
import com.zele.maipuxiaoyuan.ui.personalfrag.StudentCardActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:      首页
 * Autour：          LF
 * Date：            2018/1/4 16:40
 */

public class MainFirstFragment extends BaseFragment<MainFirstFragView, MainFirstFragPresenter> {


    public View mView;
    public Context mContext;
    @BindView(R.id.mainFg_banner)
    Banner mBanner;
    @BindView(R.id.mainFg_headPicIv)
    ImageView mHeadPicIv;
    @BindView(R.id.mainFg_isChangeIv)
    ImageView mIsChangeIv;
    @BindView(R.id.mainFg_nameTv)
    TextView mNameTv;
    @BindView(R.id.mainFg_achiNameTv)
    TextView mAchiNameTv;
    @BindView(R.id.mainFg_schoolTv)
    TextView mSchoolTv;
    @BindView(R.id.mainFg_classTv)
    TextView mClassTv;
    @BindView(R.id.mainFg_xunzhangIv)
    ImageView mXunzhangIv;
    @BindView(R.id.mainFg_scoreTv)
    TextView mScoreTv;
    @BindView(R.id.mainFg_growDayTv)
    TextView mGrowDayTv;
    @BindView(R.id.mainFg_timesTv)
    TextView mTimesTv;
    @BindView(R.id.mainFg_growthHistoryRl)
    RelativeLayout mGrowthHistoryRl;
    @BindView(R.id.mainFg_zuoYeRl)
    RelativeLayout mZuoYeRl;
    @BindView(R.id.mainFg_remindExamTv)
    TextView mRemindExamTv;
    @BindView(R.id.mainFg_scoreRl)
    RelativeLayout mScoreRl;
    @BindView(R.id.mainFg_remindAttendTv)
    TextView mRemindAttendTv;
    @BindView(R.id.mainFg_kaoqinRl)
    RelativeLayout mKaoqinRl;
    @BindView(R.id.mainFg_reportRl)
    RelativeLayout mReportRl;
    @BindView(R.id.mainFg_gridOneLl)
    LinearLayout mGridOneLl;
    @BindView(R.id.mainFg_classArrangeRl)
    RelativeLayout mClassArrangeRl;
    @BindView(R.id.mainFg_starRl)
    RelativeLayout mStarRl;
    @BindView(R.id.mainFg_contactsRl)
    RelativeLayout mContactsRl;
    @BindView(R.id.mainFg_unknowIconIv)
    ImageView mUnknowIconIv;
    @BindView(R.id.mainFg_unknowNameTv)
    TextView mUnknowNameTv;
    @BindView(R.id.mainFg_unknowRl)
    RelativeLayout mUnknowRl;
    @BindView(R.id.mainFg_gridTwoLl)
    LinearLayout mGridTwoLl;
    @BindView(R.id.mainFg_refresh)
    SwipeRefreshLayout mRefresh;

    Unbinder unbinder;

    private String mUID;
    private String mSID;

    // 切换学生列表
    private  List<BindStudentsBean.StudentsBean> mStudents = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_first, container, false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, mView);
        initDisplayMetrics();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefresh.setRefreshing(true);
        initView();
        initBanner();
        initData();
        initListener();
    }

    private void initView() {
        processHight();
    }

    private void initListener() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    // 获取banner图
    private void initBanner(){
        Map<String, String> parameter = new HashMap<>();
        parameter.put("type", "3");
        mPresenter.getBanner(parameter);
    }

    // 获取学生详情
    private void initData() {
        mUID= SharedPreferenceCache.getInstance().getPres("UserId");
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentsBean.getSid();

        Map<String, String> parameter = new HashMap<>();
        parameter.put("sid", mSID);
        parameter.put("uid", mUID);
        parameter.put("flag", "pre");
        mPresenter.getStudentDetail(parameter);

        parameter = new HashMap<>();
        parameter.put("uid", mUID);
        mPresenter.getBindStudents(parameter);

        parameter = new HashMap<>();
        parameter.put("uid", mUID);
        parameter.put("sid", mSID);
        mPresenter.loadClassParents(parameter);

        parameter = new HashMap<>();
        parameter.put("uid", mUID);
        parameter.put("sid", mSID);
        mPresenter.loadClassParents(parameter);
    }

    @Override
    public MainFirstFragPresenter createPresenter() {
        return new MainFirstFragPresenter();
    }

    @Override
    public MainFirstFragView createView() {
        return new MainFragCallBack();
    }

    class MainFragCallBack implements MainFirstFragView {

        @Override
        public void onError(String error) {
            mRefresh.setRefreshing(false);
            SnackbarUtil.ShortSnackbar(mRefresh, getResources().getString(R.string.get_error), Snackbar.LENGTH_SHORT).show();
        }

        /**
         * 获取banner图
         *
         * @param bannerBean
         */
        @Override
        public void getBanner(BannerBean bannerBean) {
            if ("100".equals(bannerBean.getResult())) {
                List<BannerBean.DatasBean> datas = bannerBean.getDatas();
                //请求banner数据后刷新页面
                initBanner(datas);
                final BannerBean.DatasBean homeActivity = bannerBean.getHomeActivity();
                //初始化敬请期待
                if (homeActivity != null) {
                    try {
                        Glide.with(mContext).load(Constants.SERVER_URL + homeActivity.getPath()).into(mUnknowIconIv);
                        mUnknowNameTv.setText(homeActivity.getName());
                        mUnknowRl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (homeActivity.getAction() == 1) {
                                    //进入网页
                                    Intent intent = new Intent(mContext, WebAcitivty.class);
                                    intent.putExtra("title", homeActivity.getName());
                                    intent.putExtra("url", homeActivity.getUrl());
                                    startActivity(intent);
                                }
//                                else if (homeActivity.getAction() == 3) {
//                                    Intent intent = new Intent(mContext, ClassCircleNewActivity.class);
//                                    intent.putExtra("tag", homeActivity.getUrl());
//                                    startActivity(intent);
//                                }
                            }
                        });
                    } catch (Exception e) {}
                }
            }
        }

        /**
         * 获取学生详情
         * @param bean
         */
        @Override
        public void getStudentDetail(StudentInfoBean bean) {
            mRefresh.setRefreshing(false);
            if ("100".equals(bean.getResult())){
                processData(bean.getStudent());
                //请求学生详情后必须重新请求班级信息
                Map<String, String> parameter = new HashMap<>();
                parameter.put("sid", mSID);
                mPresenter.getStudentTermsData(parameter);
            }else {
                SnackbarUtil.ShortSnackbar(mBanner, "获取学生信息失败，请稍后再试！", Snackbar.LENGTH_LONG).show();
            }
        }

        /**
         * 获取绑定的学生列表
         * @param bean
         */
        @Override
        public void getBindStudents(BindStudentsBean bean) {
            mRefresh.setRefreshing(false);
            try {
                if ("100".equals(bean.getResult())){
                    String studentList =SharedPreferenceCache.getInstance().getPres("StudentsContent");
                    if (TextUtils.isEmpty(studentList)){
                        SharedPreferenceCache.getInstance().setPres("StudentsContent",JSON.toJSONString(bean));
                    }
                    List<BindStudentsBean.StudentsBean> list = bean.getStudents();
                    String classNameArr="";
                    for (BindStudentsBean.StudentsBean item:list){
                        classNameArr+=item.getClassName()+",";
                    }
                    SharedPreferenceCache.getInstance().setPres("AllStudentsClassNames",classNameArr);
                    if (list!=null &&list.size()>0){
                        //列表数据大于1的时候显示
                        mStudents = list;
                        mIsChangeIv.setVisibility(View.VISIBLE);

                        mHeadPicIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switchStudent();
                            }
                        });
                    }else{
                        mIsChangeIv.setVisibility(View.GONE);
                    }
                }
            }catch (Exception e){}
        }

        /**
         * 获取学生学期信息
         * @param data
         */
        @Override
        public void getStudentTermsData(JSONObject data) {
            mRefresh.setRefreshing(false);
            SharedPreferenceCache.getInstance().setPres("StudentTerms",data.toString());
        }

        /**
         * 获取家长好友列表
         * @param jsonObject
         */
        @Override
        public void loadClassParents(JSONObject jsonObject) {
            if ("100".equals(jsonObject.get("result"))) {
                // 保存家长好友的数据到本地
                SharedPreferenceCache.getInstance().setPres("ClassParent",jsonObject.toJSONString());
            }
        }

        /**
         * 获取教师好友列表
         * @param jsonObject
         */
        @Override
        public void loadClassTeacher(JSONObject jsonObject) {
            if ("100".equals(jsonObject.get("result"))) {
                // 保存所有老师的数据到本地
                SharedPreferenceCache.getInstance().setPres("ClassTeacher",jsonObject.toJSONString());
            }
        }
    }

    /**
     * 设置banner图数据
     *
     * @param datas
     */
    private void initBanner(final List<BannerBean.DatasBean> datas) {
        if (datas == null) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        for (BannerBean.DatasBean item : datas) {
            list.add(item.getPath());
        }
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(list);
        //图片的点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //根据type判断跳转页面的类型action 1进入网页，2进入某activity
                BannerBean.DatasBean data = datas.get(position);
                if (data.getAction() == 1) {
                    //进入网页
                    String title = data.getName();
                    String url = data.getUrl();
                    Intent intent = new Intent(mContext, WebAcitivty.class);
                    intent.putExtra("title", title);
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else if (data.getAction() == 2) {
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    /**
     * 处理学生信息
     * @param data
     */
    private void processData(StudentsBean data) {
        //把更新后的信息保存到application中
        try {
            if (!TextUtils.isEmpty(data.getAvatar())){
                //如果图片不位空，显示图片
                String path = Constants.SERVER_URL.substring(0,Constants.SERVER_URL.length()-1)+data.getAvatar();
                if (path!=null){
                    Glide.with(mContext).load(path).into(mHeadPicIv);
                }
            }

            mNameTv.setText(data.getUserName());
            mClassTv.setText(data.getClassName());
            mScoreTv.setText(data.getIntegral()+"");
            mSchoolTv.setText(data.getAccessName());
            mGrowDayTv.setText(data.getDays()+"");
            mTimesTv.setText(data.getTimes()+"");
            mAchiNameTv.setText(data.getAchiName());
            if ("1".equals(data.getNewTermEvent())){
                // 新学期弹框
                showNewTermDailog();
            }
        }catch (Exception e){ }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mainFg_xunzhangIv, R.id.mainFg_growthHistoryRl,R.id.mainFg_kaoqinRl,R.id.mainFg_zuoYeRl, R.id.mainFg_reportRl,
            R.id.mainFg_classArrangeRl,R.id.mainFg_starRl})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            // 荣誉勋章
            case R.id.mainFg_xunzhangIv:
                startActivity(new Intent(getActivity(), HallHonorActivity.class));
                break;
            // 成长历程
            case R.id.mainFg_growthHistoryRl:
                startActivity(new Intent(getActivity(), GrowthHistoryActivity.class));
                break;
            // 作业
            case R.id.mainFg_zuoYeRl:
                intent = new Intent(getContext(), BooksActivity.class);
                intent.putExtra("opop", "011");
                startActivity(intent);
                break;
            // 考勤
            case R.id.mainFg_kaoqinRl:
                startActivity(new Intent(getActivity(), AttendanceActivity.class));
                break;
            // 综合素质报告
            case R.id.mainFg_reportRl:
                intent = new Intent(getActivity(), WebAcitivty.class);
                intent.putExtra("title", "综合素质报告");
                intent.putExtra("url", Constants.SERVER_URL+ "history/reportApp?sid=" + mSID);
                startActivity(intent);
                break;
            // 课程表
            case R.id.mainFg_classArrangeRl:
                startActivity(new Intent(getActivity(), ClassScheduleActivity.class));
                break;
            // 星级评价
            case R.id.mainFg_starRl:
                startActivity(new Intent(getActivity(), StartEvaluateActivity.class));
                break;
        }
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),"成功                                        了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),"失                                            败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),"取消                                          了",Toast.LENGTH_LONG).show();

        }
    };

    /**
     * 自动根据屏幕宽度设置线性布局的高度，这样每个框就能变成正方形
     */
    private void processHight() {
        int gridLineHeight = mWidth / 4;
        ViewGroup.LayoutParams layoutParams1 = mGridOneLl.getLayoutParams();
        layoutParams1.height = gridLineHeight;
        mGridOneLl.setLayoutParams(layoutParams1);
        ViewGroup.LayoutParams layoutParams2 = mGridTwoLl.getLayoutParams();
        layoutParams2.height = gridLineHeight;
        mGridTwoLl.setLayoutParams(layoutParams2);
    }

    /**
     * 新学期弹框
     */
    private void showNewTermDailog() {
        ConfirmTipDialog.Builder builder=new ConfirmTipDialog.Builder();
        ConfirmTipDialog dialog=builder.setTitleStr("尊敬的家长，您好！")
                .setContentStr("     新的一学期开始了，为保证您正常了解自己学生的在校情况，请及时修改学生的年级、班级等信息！")
                .setConfirmType(ConfirmTipDialog.CONFIRM_SINGLE_TYPE)
                .setClickConfirmListener(new DialogConfirmClickListener() {
                    @Override
                    public void onDialogConfirmClickListener(Dialog dialog) {
                        mContext.startActivity(new Intent(getActivity(), StudentCardActivity.class));
                        getActivity().finish();
                        dialog.dismiss();
                    }
                }).Build(getActivity());
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 切换学生
     */
    private void switchStudent(){
        ChoiseListItemDialog.Builder builder=new ChoiseListItemDialog.Builder();
        builder.setAdapter(new StudentsAdapter(getActivity(),mStudents))
                .setTitleStr("切换学生")
                .setItemClickListener(new DialogListItemClickListener<ChoiseListItemDialog>() {
                    @Override
                    public void onDialogListItemClickListener(ChoiseListItemDialog dialog, int position) {
                        dialog.dismiss();
                        if(mStudents.get(position)!=null&&mStudents.get(position).getSid()!=null){
                            SharedPreferenceCache.getInstance().setPres("Student", JSON.toJSONString(mStudents.get(position)));
                            initData();
                        }
                    }
                }).Build(getActivity()).show();
    }
}
