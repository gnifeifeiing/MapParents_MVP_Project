package com.zele.maipuxiaoyuan.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentTeacherBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.dialog.WarnTipDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.login.BindStudentPhonePresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.BindStudentPhoneView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      绑定学生信息
 * Autour：          LF
 * Date：            2018/10/29 9:11
 */
public class BindStudentPhoneActivity extends BaseActivity<BindStudentPhoneView,BindStudentPhonePresenter> implements BindStudentPhoneView {

    @BindView(R.id.bindStudent_backIv)
    ImageView mBackIv;
    @BindView(R.id.bindStudent_phoneEt)
    EditText mPhoneEt;
    @BindView(R.id.bindStudent_contactsTv)
    TextView mContactsTv;
    @BindView(R.id.bindStudent_classInfoTv)
    TextView mClassInfoTv;
    @BindView(R.id.bindStudent_idEt)
    EditText mIdEt;
    @BindView(R.id.bindStudent_delIv)
    ImageView mDelIv;
    @BindView(R.id.bindStudent_nextBtn)
    Button mNextBtn;

    private final int REQUEST_CONTACT = 10;

    private String mClassId;
    private String mSchoolName;
    private String mClassName;
    //学生身份证
    private String mIdCard;
    //班主任电话
    private String mMasterPhone;
    // 是否是从注册进入
    private boolean mIsRegisterIn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_student_phone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mIsRegisterIn=bundle.getBoolean("register");
        }

        mIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>=0){
                    mDelIv.setVisibility(View.VISIBLE);
                }else {
                    mDelIv.setVisibility(View.GONE);
                }
                if (s.length() ==6|s.length()==16){
                    mIdEt.setText(s + "  ");
                    mIdEt.setSelection(mIdEt.getText().toString().length());
                }
                if (s.length()>=22){
                    s.delete(22,s.length());
                }
            }
        });
        mPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null &&s.length()==11){
                    //请求网络获取教师信息
                    mPhoneEt.setEnabled(false);
                    mClassInfoTv.setVisibility(View.VISIBLE);
                    mPresenter.getTeacherByPhone(s.toString().trim());
                }
            }
        });
    }

    @Override
    public BindStudentPhonePresenter createPresenter() {
        return new BindStudentPhonePresenter();
    }

    @Override
    public BindStudentPhoneView createView() {
        return this;
    }

    @OnClick({R.id.bindStudent_backIv, R.id.bindStudent_contactsTv, R.id.bindStudent_nextBtn, R.id.bindStudent_delIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bindStudent_backIv:
                finish();
                break;
            // 通讯录选择
            case R.id.bindStudent_contactsTv:
                contactChoise();
                break;
            // 清空
            case R.id.bindStudent_delIv:
                mIdEt.setText("");
                break;
            // 下一步
            case R.id.bindStudent_nextBtn:
                submit();
                break;
        }
    }

    /**
     * 从通讯录选择
     */
    private void contactChoise() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        }else {
            callContacts();
        }
    }

    /**
     * 打开通讯录
     */
    private void callContacts(){
        try {
            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            Intent intent = new Intent(Intent.ACTION_PICK, uri);
            startActivityForResult(intent,REQUEST_CONTACT);
        }catch (Exception e){ }
    }

    /**
     * 权限申请回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CONTACT)    {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                callContacts();
            }else if (grantResults[0]== PackageManager.PERMISSION_DENIED)        {
                SnackbarUtil.ShortSnackbar(mPhoneEt, "权限申请失败，用户拒绝权限", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取联系人
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONTACT){
            if (data!=null){
                Uri contactUri = data.getData();//该数据URI是一个指向用户所选联系人的定位符
                Cursor c = getContentResolver().query(contactUri, null, null, null, null);
                if (c!=null){
                    if(c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        //根据联系人id获取号码
                        Cursor c2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                        c2.moveToFirst();
                        //通过Cursor c2获得联系人电话
                        String phonenum = c2.getString(c2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.w("resd_pp",phonenum+"");
                        String replace = phonenum.trim().replace(" ", "");
                        mPhoneEt.setText(replace);
                        c2.close();
                    }
                }
                c.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mIsRegisterIn){
            String student = SharedPreferenceCache.getInstance().getPres("Student");
            if (TextUtils.isEmpty(student)) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                try {
                    BindStudentsBean.StudentsBean bean = JSON.parseObject(student, new TypeReference<BindStudentsBean.StudentsBean>() {
                    });
                    if (bean != null && bean.getSid() != null) {
                        finish();
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                } catch (Exception e) {
                } finally {
                    startActivity(new Intent(this, LoginActivity.class));
                }
            }
        }else{
            finish();
        }

    }

    @Override
    public void onError(String error) {
        mPhoneEt.setEnabled(true);
        SnackbarUtil.ShortSnackbar(mPhoneEt, getResources().getString(R.string.get_error), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 根据号码获取绑定的教师信息
     * @param bean
     */
    @Override
    public void getTeacherByPhone(BindStudentTeacherBean bean) {
        mPhoneEt.setEnabled(true);
        if (bean!=null &&"100".equals(bean.getResult())){
            BindStudentTeacherBean.BindTeacherBean data = bean.getData();
            mSchoolName = data.getSchoolName();
            mClassName = data.getClassName();
            mClassId = data.getClassId();
            mClassInfoTv.setText(mSchoolName +"-"+ mClassName +"-"+data.getTeacherName()+"老师");

            // 学校是否需要考勤卡
            SharedPreferenceCache.getInstance().setPres("BindState",bean.getData().getBindState());
        }else {
            SnackbarUtil.ShortSnackbar(mPhoneEt, "班主任号码输入有误", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证学生身份证号是否正确
     * @param jsonObject
     */
    @Override
    public void validationStudentIDCard(JSONObject jsonObject) {
        String result = jsonObject.getString("result");
        if ("100".equals(result)){
            JSONObject data = jsonObject.getJSONObject("data");
            String studentName = data.getString("studentName");
            String avater = data.getString("avater");
            Toast.makeText(this,"身份证信息验证成功",Toast.LENGTH_LONG).show();
            //保存注册的数据
            //如果是注册页面
            SharedPreferenceCache.getInstance().setPres("RegisterStep","3");
            SharedPreferenceCache.getInstance().setPres("RE_StudentName",studentName);
            SharedPreferenceCache.getInstance().setPres("RE_SchoolName",mSchoolName);
            SharedPreferenceCache.getInstance().setPres("RE_ClassId",mClassId);
            SharedPreferenceCache.getInstance().setPres("RE_Id",mIdCard);
            SharedPreferenceCache.getInstance().setPres("RE_MasterPhone",mMasterPhone);
            SharedPreferenceCache.getInstance().setPres("RE_Avater",avater);

            Intent intent = new Intent(this, BindStudentRelationActivity.class);
            startActivity(intent);
        }else {
            WarnTipDialog.Builder builder=new WarnTipDialog.Builder();
            builder.setConfirmStr("好的")
                    .setContentStr("请核对学生身份证号码是否正确！客服电话：4001368136")
                    .setClickConfirmListener(new DialogConfirmClickListener<WarnTipDialog>() {
                        @Override
                        public void onDialogConfirmClickListener(WarnTipDialog dialog) {
                            dialog.dismiss();
                        }
                    }).Build(this).show();
        }
    }

    /**
     * 提交
     */
    private void submit(){
        if(checkInput()){
            Map<String, String> parameter = new HashMap<>();
            parameter.put("classId", mClassId);
            parameter.put("idCard", mIdCard);
            mPresenter.validationStudentIDCard(parameter);
        }
    }

    private boolean checkInput(){
        mMasterPhone=mPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(mMasterPhone)) {
            SnackbarUtil.ShortSnackbar(mPhoneEt, "请输入班主任手机号码", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        mIdCard=mIdEt.getText().toString().trim().replace(" ","");
        if (TextUtils.isEmpty(mIdCard)) {
            SnackbarUtil.ShortSnackbar(mPhoneEt, "请输入学生身份证号码", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
