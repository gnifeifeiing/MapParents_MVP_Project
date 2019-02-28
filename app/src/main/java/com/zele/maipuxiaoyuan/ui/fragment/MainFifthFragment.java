package com.zele.maipuxiaoyuan.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.adapter.StudentsAdapter;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.ScoreBean;
import com.zele.maipuxiaoyuan.common.base.BaseFragment;
import com.zele.maipuxiaoyuan.common.dialog.ChoiseListItemDialog;
import com.zele.maipuxiaoyuan.common.dialog.ConfirmTipDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.interfaces.DialogListItemClickListener;
import com.zele.maipuxiaoyuan.common.utils.ClearCache;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.Utils;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.PersonalFragPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.PersonalFragView;
import com.zele.maipuxiaoyuan.ui.login.LoginActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.AboutOurActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.HelpActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.MyCoinRecordActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.StudentCardActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.StudentHealthActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.UserInfoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:      个人中心
 * Autour：          LF
 * Date：            2018/10/23 17:11
 */

public class MainFifthFragment extends BaseFragment<PersonalFragView, PersonalFragPresenter> {

    public View mView;
    public Context mContext;
    @BindView(R.id.fragPersonal_avatarIv)
    ImageView mAvatarIv;
    @BindView(R.id.fragPersonal_userNameTv)
    TextView mUserNameTv;
    @BindView(R.id.fragPersonal_relationTv)
    TextView mRelationTv;
    @BindView(R.id.fragPersonal_mobileTv)
    TextView mMobileTv;
    @BindView(R.id.fragPersonal_coinTv)
    TextView mCoinTv;
    @BindView(R.id.fragPersonal_scoreLl)
    LinearLayout mScoreLl;
    @BindView(R.id.fragPersonal_healthLl)
    LinearLayout mHealthLl;
    @BindView(R.id.fragPersonal_changeStuCardLl)
    LinearLayout mChangeStuCardLl;
    @BindView(R.id.fragPersonal_stuManageLl)
    LinearLayout mStuManageLl;
    @BindView(R.id.fragPersonal_giftHouseLl)
    LinearLayout mGiftHouseLl;
    @BindView(R.id.fragPersonal_helpLl)
    LinearLayout mHelpLl;
    @BindView(R.id.fragPersonal_versionCodeTv)
    TextView mVersionCodeTv;
    @BindView(R.id.fragPersonal_aboutOurLl)
    LinearLayout mAboutOurLl;
    @BindView(R.id.fragPersonal_cacheTv)
    TextView mCacheTv;
    @BindView(R.id.fragPersonal_clearLl)
    LinearLayout mClearLl;
    @BindView(R.id.fragPersonal_exitTv)
    TextView mExitTv;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    private LoginUserBean.User mUserBean;
    private String mSID = "";

    // 切换学生列表
    private List<BindStudentsBean.StudentsBean> mStudents = new ArrayList<>();
    private BindStudentsBean mStudentsBean;
    private ConfirmTipDialog mLogoutDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_fifth, container, false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("UserBean"),
                new TypeReference<LoginUserBean.User>() {
                });
        BindStudentsBean.StudentsBean studentBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentBean.getSid();
        mStudentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("StudentsContent"), new TypeReference<BindStudentsBean>() {
        });
        // 当前家长用户下的学生列表信息
        if (mStudentsBean != null && mStudentsBean.getStudents() != null && mStudentsBean.getStudents().size() > 0) {
            mStudents = mStudentsBean.getStudents();
        }

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.setRefreshing(true);
        initData();
    }

    private void initData() {
        // 查看我的积分
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", mUserBean.getUid());
        mPresenter.getScore(parameter);

        if (mStudents.size() == 0) {
            // 获取绑定学生列表
            mPresenter.getBindStudents(parameter);
        }

        // 获取家长信息
        parameter = new HashMap<>();
        parameter.put("uid", mUserBean.getUid());
        parameter.put("sid", mSID);
        mPresenter.getParentInfo(parameter);

        // 刷新缓存大小
        refreshCacheSize();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        mMobileTv.setText(mUserBean.getUserName() == null ? "" : mUserBean.getUserName());
        mVersionCodeTv.setText(Utils.getVersionName(getActivity().getApplicationContext()));
    }

    @Override
    public PersonalFragPresenter createPresenter() {
        return new PersonalFragPresenter();
    }

    @Override
    public PersonalFragView createView() {
        return new PersonalCallBack();
    }

    class PersonalCallBack implements PersonalFragView {

        @Override
        public void onError(String error) {
            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshing(false);
            }
        }

        /**
         * 查看我的积分
         *
         * @param bean
         */
        @Override
        public void getScore(ScoreBean bean) {
            if (bean != null) {
                ScoreBean.Score score = bean.getDatas();
                if (score != null) {
                    mCoinTv.setText(score.getGold());
                }
            }
        }

        /**
         * 获取绑定学生列表
         *
         * @param bean
         */
        @Override
        public void BindStudentsBean(BindStudentsBean bean) {
            if ("100".equals(bean.getResult())) {
                List<BindStudentsBean.StudentsBean> list = bean.getStudents();
                if (list != null && list.size() > 0) {
                    //列表数据大于1的时候显示
                    mStudents = list;
                }
                // 保存当前家长用户下所有学生的信息、班级信息
                String studentList = SharedPreferenceCache.getInstance().getPres("StudentsContent");
                if (TextUtils.isEmpty(studentList)) {
                    SharedPreferenceCache.getInstance().setPres("StudentsContent", JSON.toJSONString(bean));
                }
                String classNameArr = "";
                for (BindStudentsBean.StudentsBean item : list) {
                    classNameArr += item.getClassName() + ",";
                }
                SharedPreferenceCache.getInstance().setPres("AllStudentsClassNames", classNameArr);
            }
        }

        /**
         * 获取家长信息
         *
         * @param bean
         */
        @Override
        public void getParentInfo(ParentMessageBean bean) {
            if ("100".equals(bean.getResult())) {
                ParentMessageBean.ParentBean parent = bean.getParent();
                if (parent != null) {
                    refreshInfo(parent.getStudentName(), parent.getRelaName(), parent.getAvatar());
                }
            }
            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshing(false);
            }
        }
    }

    @OnClick({R.id.fragPersonal_avatarIv,R.id.fragPersonal_scoreLl, R.id.fragPersonal_healthLl, R.id.fragPersonal_changeStuCardLl, R.id.fragPersonal_stuManageLl, R.id.fragPersonal_giftHouseLl, R.id.fragPersonal_helpLl, R.id.fragPersonal_aboutOurLl, R.id.fragPersonal_clearLl, R.id.fragPersonal_exitTv})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()) {
            // 头像
            case R.id.fragPersonal_avatarIv:
                intent=new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            // 我的麦励
            case R.id.fragPersonal_scoreLl:
                intent=new Intent(getActivity(), MyCoinRecordActivity.class);
                startActivity(intent);
                break;
            // 健康卡
            case R.id.fragPersonal_healthLl:
                intent=new Intent(getActivity(), StudentHealthActivity.class);
                startActivity(intent);
                break;
            // 切换学生证
            case R.id.fragPersonal_changeStuCardLl:
                switchStudent();
                break;
            // 学生信息管理
            case R.id.fragPersonal_stuManageLl:
                intent=new Intent(getActivity(), StudentCardActivity.class);
                startActivity(intent);
                break;
            // 礼品屋
            case R.id.fragPersonal_giftHouseLl:
                break;
            // 帮助与反馈
            case R.id.fragPersonal_helpLl:
                intent=new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
                break;
            // 关于我们
            case R.id.fragPersonal_aboutOurLl:
                intent=new Intent(getActivity(), AboutOurActivity.class);
                startActivity(intent);
                break;
            // 清除缓存
            case R.id.fragPersonal_clearLl:
                ClearCache.clearAllCache(getActivity());
                refreshCacheSize();
                break;
            // 退出登录
            case R.id.fragPersonal_exitTv:
                logout();
                break;
        }
    }

    private void logout() {
        mLogoutDialog=new ConfirmTipDialog.Builder()
                .setContentStr("您确定要退出吗？")
                .setClickConfirmListener(new DialogConfirmClickListener() {
                    @Override
                    public void onDialogConfirmClickListener(Dialog dialog) {
                        SharedPreferenceCache.getInstance().clearUserInfo();
                        MyApplication.IS_LOGIN=false;
                        MyApplication.getInstance().finishAllActivity();

                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        dialog.dismiss();
                    }
                }).Build(getActivity());
        mLogoutDialog.show();
    }

    /**
     * 切换学生
     */
    private void switchStudent() {
        ChoiseListItemDialog.Builder builder = new ChoiseListItemDialog.Builder();
        builder.setAdapter(new StudentsAdapter(getActivity(), mStudents))
                .setTitleStr("切换学生")
                .setItemClickListener(new DialogListItemClickListener<ChoiseListItemDialog>() {
                    @Override
                    public void onDialogListItemClickListener(ChoiseListItemDialog dialog, int position) {
                        dialog.dismiss();
                        if (mStudents.get(position) != null && mStudents.get(position).getSid() != null) {
                            SharedPreferenceCache.getInstance().setPres("Student", JSON.toJSONString(mStudents.get(position)));
                        }
                    }
                }).Build(getActivity()).show();
    }

    /**
     * 清除缓存
     */
    private void refreshCacheSize() {
        String cache = "0.00MB";
        try {
            cache = ClearCache.getTotalCacheSize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCacheTv.setText(cache);
    }

    /**
     * 更新个人信息
     *
     * @param studentName
     * @param relaName
     * @param avatar
     */
    private void refreshInfo(String studentName, String relaName, String avatar) {
        mUserNameTv.setText(studentName == null ? "" : studentName);
        mRelationTv.setText(relaName == null ? "家长" : relaName);
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(getActivity()).load(Constants.SERVER_URL + avatar).into(mAvatarIv);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
