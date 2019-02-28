package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.StuHealthBean;
import com.zele.maipuxiaoyuan.bean.TermBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.dialog.DropDownPopupWindow;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.StudentHealthPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.StudentHealthView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      健康卡
 * Autour：          LF
 * Date：            2018/11/22 17:24
 */
public class StudentHealthActivity extends BaseActivity<StudentHealthView,StudentHealthPresenter> implements StudentHealthView{

    @BindView(R.id.stuHealth_backIv)
    ImageView mBackIv;
    @BindView(R.id.stuHealth_nameTv)
    TextView mNameTv;
    @BindView(R.id.stuHealth_changeIv)
    ImageView mChangeIv;
    @BindView(R.id.stuHealth_weightTv)
    TextView mWeightTv;
    @BindView(R.id.stuHealth_heightTv)
    TextView mHeightTv;
    @BindView(R.id.stuHealth_eyeTv)
    TextView mEyeTv;
    @BindView(R.id.stuHealth_lungTv)
    TextView mLungTv;
    @BindView(R.id.stuHealth_qianquTv)
    TextView mQianquTv;
    @BindView(R.id.stuHealth_50mTv)
    TextView m50mTv;
    @BindView(R.id.stuHealth_tiaoshengTv)
    TextView mTiaoshengTv;
    @BindView(R.id.stuHealth_sexTv)
    TextView mSexTv;
    @BindView(R.id.stuHealth_termTv)
    TextView mTermTv;
    @BindView(R.id.stuHealth_numTv)
    TextView mNumTv;
    @BindView(R.id.stuHealth_shengTv)
    TextView mShengTv;

    private String mSID;
    // 历史学期id
    private String mTermId = "";
    // 当前学期id
    private String mCurrTermId = "1";

    private TermBaseAdapter mTermAdapter;
    private List<TermBean.TermsBean> mTerms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_health);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        BindStudentsBean.StudentsBean studentBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentBean.getSid();

        mNameTv.setText(studentBean.getUserName());
        // 学期列表
        String studentTerms = SharedPreferenceCache.getInstance().getPres("StudentTerms");
        if (!TextUtils.isEmpty(studentTerms)) {
            TermBean termBean = new Gson().fromJson(studentTerms, TermBean.class);
            mTerms = termBean.getTerms();
            TermBean.TermsBean termsBean = mTerms.get(mTerms.size() - 1);
            mTermId = String.valueOf(termsBean.getMid());
            mCurrTermId = String.valueOf(termsBean.getCur());
            mTermTv.setText(termsBean.getName());
        }
        mTermAdapter = new TermBaseAdapter();
        mTermAdapter.setPos(mTerms.size() > 0 ? mTerms.size() - 1 : 0);
    }

    private void initData() {
        showProgress("正在加载...");
        Map<String,String> parameter=new HashMap<>();
        parameter.put("sid",mSID);
        parameter.put("curt",mCurrTermId);
        parameter.put("term",mTermId);
        mPresenter.getStuHealthData(parameter);
    }

    @Override
    public StudentHealthPresenter createPresenter() {
        return new StudentHealthPresenter();
    }

    @Override
    public StudentHealthView createView() {
        return this;
    }

    @OnClick({R.id.stuHealth_backIv, R.id.stuHealth_changeIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stuHealth_backIv:
                finish();
                break;
            case R.id.stuHealth_changeIv:
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
                .setAlpha(0.3f).setAnchor(mChangeIv)
                .setClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTermAdapter.setPos(position);
                        TermBean.TermsBean termsBean = mTerms.get(position);
                        mTermId = String.valueOf(termsBean.getMid());
                        mCurrTermId = String.valueOf(termsBean.getCur());
                        mTermTv.setText(termsBean.getName());
                        initData();
                    }
                });
        builder.Build().show(this);
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mChangeIv, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getStuHealthData(StuHealthBean bean) {
        cancelProgress();
        StuHealthBean.StudentBean student = bean.getStudent();
        if (student != null) {
            mSexTv.setText(student.getSex() == 1 ? "女" : "男");
            mNumTv.setText(student.getNumber());
            mShengTv.setText(student.getBirthday());
            mEyeTv.setText(student.getWeight()==null?"":student.getWeight());
            mLungTv.setText(student.getCapacity()==null?"":student.getCapacity());
            mQianquTv.setText(student.getRope()==null?"":student.getRope());
            m50mTv.setText(student.getSprint()==null?"":student.getSprint());
            mTiaoshengTv.setText(student.getRope()==null?"":student.getRope());
            mWeightTv.setText(student.getWeight()==null?"":student.getWeight()+"kg");
            mHeightTv.setText(student.getHeight()==null?"":student.getHeight()+"cm");
        }
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
