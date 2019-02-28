package com.zele.maipuxiaoyuan.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.amap.mapcore.Md5Utility;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.bean.RealteBean;
import com.zele.maipuxiaoyuan.bean.RegisterTwoDataBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.login.BindStudentRelationPresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.BindStudentRelationView;
import com.zele.maipuxiaoyuan.scans.CaptureActivity;
import com.zele.maipuxiaoyuan.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      绑定和学生的关系
 * Autour：          LF
 * Date：            2018/10/30 9:52
 */
public class BindStudentRelationActivity extends BaseActivity<BindStudentRelationView,BindStudentRelationPresenter> implements BindStudentRelationView {


    @BindView(R.id.bindRelation_backIv)
    ImageView mBackIv;
    @BindView(R.id.bindRelation_scanIv)
    ImageView mScanIv;
    @BindView(R.id.bindRelation_picIv)
    ImageView mPicIv;
    @BindView(R.id.bindRelation_nameTv)
    TextView mNameTv;
    @BindView(R.id.bindRelation_schoolTv)
    TextView mSchoolTv;
    @BindView(R.id.bindRelation_idTv)
    TextView mIdTv;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.bindRelation_idNumEt)
    EditText mIdNumEt;
    @BindView(R.id.bindRelation_relationTv)
    TextView mRelationTv;
    @BindView(R.id.bindRelation_relationLL)
    LinearLayout mRelationLL;
    @BindView(R.id.bindRelation_submitBtn)
    Button mSubmitBtn;
    @BindView(R.id.ll_1)
    LinearLayout mLl1;

    private OptionsPickerView<String> mOptionRelate;
    private String mUid;
    private String mMasterPhone;
    private String mClassId;
    private String mStudentName;
    private String mIdCard;
    private String mSchoolName;
    private String mAvater;

    private boolean mNeedId;
    private List<RealteBean.RelatesBean> mRelatebeanList;
    private String mCheckedRealte; //选中的关系id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_student_relation);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mUid = SharedPreferenceCache.getInstance().getPres("UserId");
        mMasterPhone = SharedPreferenceCache.getInstance().getPres("RE_MasterPhone");
        mClassId = SharedPreferenceCache.getInstance().getPres("RE_ClassId");
        mStudentName = SharedPreferenceCache.getInstance().getPres("RE_StudentName");
        mIdCard = SharedPreferenceCache.getInstance().getPres("RE_Id");
        mSchoolName = SharedPreferenceCache.getInstance().getPres("RE_SchoolName");
        mAvater = SharedPreferenceCache.getInstance().getPres("RE_Avater");

        if (!TextUtils.isEmpty(mSchoolName)) {
            mSchoolTv.setText(mSchoolName);
        }
        if (!TextUtils.isEmpty(mStudentName)) {
            mNameTv.setText(mStudentName);
        }
        if (!TextUtils.isEmpty(mIdCard)) {
            mIdTv.setText(mIdCard);
        }
        Glide.with(this).load(mAvater).into(mPicIv);
        //学校是否有考勤卡
        String bindState = SharedPreferenceCache.getInstance().getPres("BindState");
        if ("1".equals(bindState)) {
            mNeedId = true;
            mLl1.setVisibility(View.VISIBLE);
            mScanIv.setVisibility(View.VISIBLE);
        } else {
            mNeedId = false;
            mLl1.setVisibility(View.GONE);
            mScanIv.setVisibility(View.GONE);
        }

        mOptionRelate = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                mCheckedRealte = mRelatebeanList.get(options1).getId() + "";
                mRelationTv.setText(mRelatebeanList.get(options1).getName() + "");
            }
        }).build();
        //获取关系列表
        mPresenter.getRelateList();
    }

    @Override
    public BindStudentRelationPresenter createPresenter() {
        return new BindStudentRelationPresenter();
    }

    @Override
    public BindStudentRelationView createView() {
        return this;
    }

    @OnClick({R.id.bindRelation_backIv, R.id.bindRelation_scanIv, R.id.bindRelation_relationLL, R.id.bindRelation_submitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bindRelation_backIv:
                finish();
                break;
            // 扫一扫
            case R.id.bindRelation_scanIv:
                Intent intentScan = new Intent(getApplicationContext(), CaptureActivity.class);
                startActivityForResult(intentScan, 10);
                break;
            // 关系弹框
            case R.id.bindRelation_relationLL:
                mOptionRelate.show();
                break;
            // 提交
            case R.id.bindRelation_submitBtn:
                submit();
                break;
        }
    }

    private void submit() {
        if(checkInput()){
            showProgress("正在提交数据");
            Map<String, String> parameter = new HashMap<>();
            parameter.put("studentName", mStudentName);
            parameter.put("uid", mUid);
            parameter.put("idCard", mIdCard);
            parameter.put("cid", mClassId);
            parameter.put("mobile", mMasterPhone);
            parameter.put("relate", mCheckedRealte);
            parameter.put("relateName", mRelationTv.getText().toString().trim());
            if (mNeedId){
                //考勤卡
                parameter.put("atnum",mIdNumEt.getText().toString().trim());
            }
            mPresenter.bindStudentRelation(parameter);
        }
    }

    /**
     * 检测输入合法性
     * @return
     */
    private boolean checkInput() {
        String num = mIdNumEt.getText().toString().trim();
        if (mNeedId){
            if (TextUtils.isEmpty(num)) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn,"请右上角扫码或输入学生证编号!", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }

        String relation = mRelationTv.getText().toString().trim();
        if (TextUtils.isEmpty(relation)) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn,"请选择您与学生的关系!", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mSubmitBtn,error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 获取关系列表
     * @param realteBean
     */
    @Override
    public void getRelateList(RealteBean realteBean) {
        if ("100".equals(realteBean.getResult())) {
            mRelatebeanList = realteBean.getRelates();
            if (mRelatebeanList != null) {
                ArrayList<String> relates = new ArrayList<>();
                for (RealteBean.RelatesBean item : mRelatebeanList) {
                    relates.add(item.getName());
                }
                mOptionRelate.setPicker(relates);
                mOptionRelate.setSelectOptions(0);
            }
        }
    }

    /**
     * 绑定学生关系
     * @param bean
     */
    @Override
    public void bindStudentRelation(RegisterTwoDataBean bean) {
        if ("100".equals(bean.getResult())){
            //进入下个页面
            //请求数据，重新进入
            Intent intent = null;
            //如果本地已经加载了学生信息说明已经绑定过了
            String student=SharedPreferenceCache.getInstance().getPres("Student");
            BindStudentsBean.StudentsBean studentsBean= JSONObject.parseObject(student,BindStudentsBean.StudentsBean.class);
            if (!TextUtils.isEmpty(student)&&studentsBean!=null&& studentsBean.getSid()!=null &&!TextUtils.isEmpty(studentsBean.getSid())){
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                SharedPreferenceCache.getInstance().setPres("RegisterStep","ok");
                //注册完成，登录账号
                String username = SharedPreferenceCache.getInstance().getPres("UserName");
                String pwd = SharedPreferenceCache.getInstance().getPres("CustPassword");
                if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(pwd)){
                    //注册完成
                    Map<String, String> parameter = new HashMap<>();
                    parameter.put("userName", username);
                    parameter.put("userPwd", Md5Utility.getStringMD5(pwd));
                    mPresenter.login(parameter);
                }else {
                    //如果用户账号或者密码查找失败
                    Toast.makeText(this,"成功绑定学生数据，请重新登录账号！",Toast.LENGTH_SHORT).show();
                    intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }else {
            SnackbarUtil.ShortSnackbar(mSubmitBtn,bean.getMsg(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoginResult(LoginUserBean bean) {
        try {
            if ("100".equals(bean.getResult())) {
                saveUserInfo(bean.getUser());
            } else if ("101".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "该账号已被禁止使用，请联系客服！", Snackbar.LENGTH_LONG).show();
            } else if ("102".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "登录有误，请重新登录！", Snackbar.LENGTH_LONG).show();
            } else if ("104".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "登录有误，请重新输入！", Snackbar.LENGTH_LONG).show();
            } else if ("108".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "用户不存在，请先注册用户！", Snackbar.LENGTH_LONG).show();
            } else if ("113".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "该微信号已绑定有号，不能重复绑定!", Snackbar.LENGTH_LONG).show();
            } else {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "登录有误，请重新登录！", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void BindStudentsBean(BindStudentsBean bean) {
        cancelProgress();
        if ("100".equals(bean.getResult())) {
            MyApplication.IS_LOGIN = true;
            //1.保存数据到sp，学生列表
            String content = JSON.toJSONString(bean);
            if (!TextUtils.isEmpty(content)) {
                SharedPreferenceCache.getInstance().setPres("StudentsContent", content);
            }
            SharedPreferenceCache.getInstance().setPres("StudentsList", JSON.toJSONString(bean.getStudents()));
            //获取学生列表
            List<BindStudentsBean.StudentsBean> list = bean.getStudents();
            if (list != null && list.size() > 0) {
                SharedPreferenceCache.getInstance().setPres("Student", JSON.toJSONString(list.get(0)));
                // 如果改账号绑定过学生信息，那么登陆环信
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(getBaseContext(), "请先绑定学生证", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), BindStudentPhoneActivity.class);
                intent.putExtra("register",true);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * 登陆成功后、保存个人信息
     *
     * @param user
     */
    private void saveUserInfo(LoginUserBean.User user) {
        // 存储账号信息到sp
        SharedPreferenceCache.getInstance().setPres("UserId", user.getUid());
        SharedPreferenceCache.getInstance().setPres("UserName", user.getUserName());
        SharedPreferenceCache.getInstance().setPres("LoginMobile", user.getMobile());
        SharedPreferenceCache.getInstance().setPres("UUPwd",  user.getUupwd());//环信密码
        SharedPreferenceCache.getInstance().setPres("UserBean", JSONObject.toJSONString(user));

        //2.注册友盟别名信息
        MyApplication.getInstance().registerUmengAnalysis(user.getUserName());
        MyApplication.getInstance().registerUmengPusth(user.getUserName());

        //获取绑定学生信息
        showProgress(getResources().getString(R.string.loading_text));
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", user.getUid());
        mUid = user.getUid();
        mPresenter.getBindStudents(parameter);
    }
}
