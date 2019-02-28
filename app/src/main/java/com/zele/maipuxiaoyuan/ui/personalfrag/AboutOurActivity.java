package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.common.base.BaseView;
import com.zele.maipuxiaoyuan.common.dialog.ConfirmTipDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogCancleClickListener;
import com.zele.maipuxiaoyuan.common.interfaces.DialogConfirmClickListener;
import com.zele.maipuxiaoyuan.common.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      关于我们
 * Autour：          LF
 * Date：            2018/11/22 9:46
 */
public class AboutOurActivity extends BaseActivity {

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.aboutOur_versionCodeTv)
    TextView mVersionCodeTv;
    @BindView(R.id.aboutOur_telephoneTv)
    TextView mTelephoneTv;

    private final int REQUEST_CODE_ASK_CALL_PHONE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_our);
        ButterKnife.bind(this);
        mVersionCodeTv.setText(Utils.getVersionName(getApplicationContext()));
        titleTitleTv.setText("麦励家长");
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @OnClick({R.id.title_backIv, R.id.aboutOur_telephoneTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_backIv:
                finish();
                break;
            case R.id.aboutOur_telephoneTv:
                callPhoneDialog();
                break;
        }
    }

    private void callPhoneDialog() {
        ConfirmTipDialog.Builder builder = new ConfirmTipDialog.Builder();
        builder.setTitleStr("客服电话")
                .setContentStr("400-136-8136")
                .setClickCancleListener(new DialogCancleClickListener() {
                    @Override
                    public void onDialogCancleClickListener(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setClickConfirmListener(new DialogConfirmClickListener() {
                    @Override
                    public void onDialogConfirmClickListener(Dialog dialog) {
                        dialog.dismiss();
                        judgePermission();
                    }
                }).Build(this).show();
    }

    private void judgePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AboutOurActivity.this, new String[]{
                        Manifest.permission.CALL_PHONE
                }, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted callDirectly(mobile);
                    callPhone();
                } else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void callPhone() {
        Intent intent2;
        intent2 = new Intent(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel:400-136-8136"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent2);
    }
}
