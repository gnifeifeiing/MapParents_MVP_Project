package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.UploadHeadPicBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.ImageTools;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.views.ClipImageLayout;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.ClipPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.ClipView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:      处理图片剪裁
 * Autour：          LF
 * Date：            2018/11/24 10:24
 */
public class ClipActivity extends BaseActivity<ClipView,ClipPresenter> implements ClipView {

    @BindView(R.id.clipImageLayout)
    ClipImageLayout mClipImageLayout;
    @BindView(R.id.submitBtn)
    Button mSubmitBtn;

    private String mPath;

    private File mPicFile;
    private String mPicFilePath;
    private String mSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        BindStudentsBean.StudentsBean studentBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentBean.getSid();

        mPath = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(mPath) || !(new File(mPath).exists())) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn, "图片加载失败!", Snackbar.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = ImageTools.convertToBitmap(mPath, 500, 500);
        if (bitmap == null) {
            SnackbarUtil.ShortSnackbar(mSubmitBtn, "图片加载失败!", Snackbar.LENGTH_SHORT).show();
            return;
        }
        mClipImageLayout.setBitmap(bitmap);
    }


    @OnClick(R.id.submitBtn)
    public void onViewClicked() {
        mPicFile = new File(getCacheDir().getAbsolutePath() + "/ClipHeadPhoto/cache/");
        if (!mPicFile.exists())
            mPicFile.mkdirs();
        mPicFilePath = mPicFile.getAbsolutePath() + System.currentTimeMillis() + ".jpg";
        Bitmap bitmap = mClipImageLayout.clip();
        ImageTools.savePhotoToSDCard(bitmap, mPicFilePath);
        File file = new File(mPicFilePath);
        upLoadPic(file);
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void upLoadHeadPic(UploadHeadPicBean bean) {
        if(bean.getResult().equals("100")){
            Map<String,String> parameter=new HashMap<>();
            parameter.put("sid",mSID);
            parameter.put("avatar", bean.getPath());
            mPresenter.submitStuPicInfo(parameter);
        }
    }

    @Override
    public void submitStuPicInfo(BaseBean bean) {
        cancelProgress();
        if(bean.getResult().equals("100")){
            Intent intent = new Intent();
            intent.putExtra("path", mPicFilePath);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void upLoadPic(File file) {
        showProgress("正在提交...");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile", file.getName(), requestFile);
        mPresenter.upLoadHeadPic(body);
    }

    @Override
    public ClipPresenter createPresenter() {
        return new ClipPresenter();
    }

    @Override
    public ClipView createView() {
        return this;
    }
}
