package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.StudentCardAdapter;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.fourthfrag.StudentCardsPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fourthfrag.StudentCardsView;
import com.zele.maipuxiaoyuan.ui.login.BindStudentPhoneActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      学生信息卡管理
 * Autour：          LF
 * Date：            2018/11/14 10:47
 */
public class StudentCardActivity extends BaseActivity<StudentCardsView,StudentCardsPresenter> implements StudentCardsView{

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.studentCard_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.studentCard_addFABtn)
    FloatingActionButton mAddFABtn;

    private List<BindStudentsBean.StudentsBean> mList = new ArrayList<>();
    private StudentCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_card);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mAddFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentCardActivity.this,BindStudentPhoneActivity.class);
                Bundle bundle=new Bundle();
                // 表示不是从注册进入
                bundle.putBoolean("register",false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        titleTitleTv.setText("学生信息管理");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new StudentCardAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        Map<String,String> parameter=new HashMap<>();
        parameter.put("uid", SharedPreferenceCache.getInstance().getPres("UserId"));
        mPresenter.getBindStudents(parameter);
    }

    @Override
    public StudentCardsPresenter createPresenter() {
        return new StudentCardsPresenter();
    }

    @Override
    public StudentCardsView createView() {
        return this;
    }

    @OnClick({R.id.title_backIv, R.id.studentCard_addFABtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_backIv:
                finish();
                break;
            case R.id.studentCard_addFABtn:
                break;
        }
    }

    @Override
    public void onError(String error) {
        SnackbarUtil.ShortSnackbar(mRecyclerView, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getBindStudents(BindStudentsBean bean) {
        if ("100".equals(bean.getResult())) {
            mList= bean.getStudents();
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        } else if ("102".equals(bean.getResult())) {
            SnackbarUtil.ShortSnackbar(mRecyclerView, "暂无数据", Snackbar.LENGTH_SHORT).show();
        }
    }
}
