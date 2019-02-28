package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.RelationVoBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.dialog.ChoisePicDialog;
import com.zele.maipuxiaoyuan.common.interfaces.DialogChoiseCameraClickListener;
import com.zele.maipuxiaoyuan.common.interfaces.DialogChoiseGalleryClickListener;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.PhotoUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.common.views.XCRoundImageView;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.UserInfoPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.UserInfoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      用户信息
 * Autour：          LF
 * Date：            2018/11/23 17:08
 */
public class UserInfoActivity extends BaseActivity<UserInfoView,UserInfoPresenter> {

    @BindView(R.id.userInfo_backLl)
    LinearLayout mBackLl;
    @BindView(R.id.userInfo_headPicIv)
    ImageView mHeadPicIv;
    @BindView(R.id.userInfo_headPicRl)
    RelativeLayout mHeadPicRl;
    @BindView(R.id.userInfo_phoneTv)
    TextView mPhoneTv;
    @BindView(R.id.userInfo_phoneLl)
    LinearLayout mPhoneLl;
    @BindView(R.id.userInfo_QQTv)
    TextView mQQTv;
    @BindView(R.id.userInfo_QQHeadIv)
    XCRoundImageView mQQHeadIv;
    @BindView(R.id.userInfo_QQStateTv)
    TextView mQQStateTv;
    @BindView(R.id.userInfo_QQLl)
    LinearLayout mQQLl;
    @BindView(R.id.userInfo_WXTv)
    TextView mWXTv;
    @BindView(R.id.userInfo_WXHeadIv)
    XCRoundImageView mWXHeadIv;
    @BindView(R.id.userInfo_WXStateTv)
    TextView mWXStateTv;
    @BindView(R.id.userInfo_WXLl)
    LinearLayout mWXLl;
    @BindView(R.id.userInfo_relateTv)
    TextView mRelateTv;
    @BindView(R.id.userInfo_relateLl)
    LinearLayout mRelateLl;
    @BindView(R.id.userInfo_updatePwdLl)
    LinearLayout mUpdatePwdLl;
    @BindView(R.id.userInfo_submitBtn)
    Button mSubmitBtn;

    private String TAG = "weixin_____qq";
    public static final int GALLERY_CODE = 0; // 图库(相册)
    private static final int REQUEST_PICK_FROM_CAMERA = 1;// 相机
    public static final int IMAGE_COMPLETE = 2; // 结果
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 99;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 100;

    private File mImgFile;
    private Uri mImageCaptureUri;
    private ChoisePicDialog mChoisePicDialog;
    private Bitmap mHeadBitmap;

    private String mSID="";
    private String mUID="";
    private String mRelationId="";
    private String mWOpenId="";
    private String mWXName="";
    private String mQOpenId="";
    private String mQQName="";
    // 关系
    private String mPersonalRelate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(Color.parseColor("#82dc86"));
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 获取父母信息
     */
    private void initData() {
        showProgress("加载中...");
        Map<String,String> parameter=new HashMap<>();
        parameter.put("uid",mUID);
        parameter.put("sid",mSID);
        mPresenter.getParentInfo(parameter);
    }

    /**
     * 获取关系列表
     */
    private void initRelationData() {
        mPresenter.getRelationList();
    }

    private void initView() {
        mUID= SharedPreferenceCache.getInstance().getPres("UserId");
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentsBean.getSid();
        // 设置头像
        String headImgPath = SharedPreferenceCache.getInstance().getPres("HeadImgPath");
        if (!TextUtils.isEmpty(headImgPath)) {
            mHeadBitmap=getLoacalBitmap(headImgPath);
            mHeadPicIv.setImageBitmap(mHeadBitmap);
        }

        String wxHeadPath=SharedPreferenceCache.getInstance().getPres("WXProfileImageUrl");
        if(!TextUtils.isEmpty(wxHeadPath)){
            Glide.with(UserInfoActivity.this).load(wxHeadPath).into(mWXHeadIv);
        }
        String qqHeadPath=SharedPreferenceCache.getInstance().getPres("QQProfileImageUrl");
        if(!TextUtils.isEmpty(qqHeadPath)){
            Glide.with(UserInfoActivity.this).load(qqHeadPath).into(mQQHeadIv);
        }
    }

    @Override
    public UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public UserInfoView createView() {
        return new DataCallBack();
    }

    class DataCallBack implements UserInfoView{

        @Override
        public void onError(String error) {
            cancelProgress();
            SnackbarUtil.ShortSnackbar(mSubmitBtn, error, Snackbar.LENGTH_SHORT).show();
        }

        /**
         * 获取家长信息
         * @param bean
         */
        @Override
        public void getParentInfo(ParentMessageBean bean) {
            if ("100".equals(bean.getResult())) {
                if(mHeadBitmap==null){
                    Glide.with(UserInfoActivity.this).
                            load(Constants.SERVER_URL+bean.getParent().getAvatar()).into(mHeadPicIv);
                }
                mPhoneTv.setText(bean.getParent().getUserName());
                mPersonalRelate = bean.getParent().getRelate() + "";
                mWXName = bean.getParent().getWeixin()==null?"":bean.getParent().getWeixin();
                mQQName = bean.getParent().getQq()==null?"":bean.getParent().getQq();
                if (!TextUtils.isEmpty(mWXName)) {
                    try {
                        String wx = URLDecoder.decode(mWXName, "UTF-8");
                        mWXTv.setText(wx);
                    } catch (UnsupportedEncodingException e) {}
                    mWXStateTv.setVisibility(View.INVISIBLE);
                } else {
                    mWXTv.setText("未绑定");
                    mWXStateTv.setVisibility(View.INVISIBLE);
                }
                if (!TextUtils.isEmpty(mQQName)) {
                    try {
                        String qq = URLDecoder.decode(mQQName, "UTF-8");
                        mQQTv.setText(qq);
                    } catch (UnsupportedEncodingException e) {}
                    mQQStateTv.setVisibility(View.INVISIBLE);
                } else {
                    mQQTv.setText("未绑定");
                    mQQStateTv.setVisibility(View.INVISIBLE);
                }
                initRelationData();
            } else if ("102".equals(bean.getResult())) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "获取失败", Snackbar.LENGTH_SHORT).show();
            }
        }

        /**
         * 获取关系列表
         * @param bean
         */
        @Override
        public void getRelationList(RelationVoBean bean) {
            cancelProgress();
            if ("100".equals(bean.getResult())) {
                for (int i = 0; i < bean.relates.size(); i++) {
                    if (mPersonalRelate.equals(bean.relates.get(i).getId())) {
                        mRelateTv.setText(bean.relates.get(i).getName());
                        mRelationId=bean.relates.get(i).getId();
                    }
                }
            }
        }

        /**
         * 保存修改回调
         * @param bean
         */
        @Override
        public void saveParentInfo(BaseBean bean) {
            cancelProgress();
            if ("100".equals(bean.getResult())) {
                Toast.makeText(getBaseContext(), "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("102".equals(bean.getResult())) {
                Toast.makeText(getBaseContext(), "个人资料修改失败", Toast.LENGTH_SHORT).show();
            } else if ("104".equals(bean.getResult())) {
                Toast.makeText(getBaseContext(), "用户数据不存在", Toast.LENGTH_SHORT).show();
            } else if ("112".equals(bean.getResult()) || "113".equals(bean.getResult())) {
                Toast.makeText(getBaseContext(), "该账号已被使用，不能重复绑定", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.userInfo_backLl, R.id.userInfo_headPicRl, R.id.userInfo_QQLl, R.id.userInfo_WXLl, R.id.userInfo_updatePwdLl, R.id.userInfo_submitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.userInfo_backLl:
                finish();
                break;
            // 头像
            case R.id.userInfo_headPicRl:
                showChoisePicDialog();
                break;
            // qq
            case R.id.userInfo_QQLl:
                if (TextUtils.isEmpty(mQQName)) {
                    authorization(SHARE_MEDIA.QQ);
                } else {
                    SnackbarUtil.ShortSnackbar(mSubmitBtn, "你已经绑定QQ,不能重复绑定！", Snackbar.LENGTH_SHORT).show();
                }
                break;
            // wx
            case R.id.userInfo_WXLl:
                if (TextUtils.isEmpty(mWXName)) {
                    authorization(SHARE_MEDIA.WEIXIN);
                } else {
                    SnackbarUtil.ShortSnackbar(mSubmitBtn, "你已经绑定微信号，不能重复绑定！", Snackbar.LENGTH_SHORT).show();
                }
                break;
            // 修改密码
            case R.id.userInfo_updatePwdLl:
                startActivity(new Intent(this, SettingPwdActivity.class));
                break;
            // 保存修改
            case R.id.userInfo_submitBtn:
                saveInfo();
                break;
        }
    }

    /**
     * 保存
     */
    private void saveInfo() {
        try{
            showProgress("加载中...");
            Map<String,String> parameter=new HashMap<>();
            parameter.put("uid",mUID);
            parameter.put("sid",mSID);
            parameter.put("relate",mRelationId);
            parameter.put("weixin", URLEncoder.encode(mWXName, "utf-8"));
            parameter.put("wopenId",mWOpenId);
            parameter.put("qq",URLEncoder.encode(mQQName, "utf-8"));
            parameter.put("qopenId",mQOpenId);
            mPresenter.saveParentInfo(parameter);
        }catch (Exception e){}
    }

    /**
     * 选择头像弹框
     */
    private void showChoisePicDialog() {
        if (mChoisePicDialog == null) {
            mChoisePicDialog = new ChoisePicDialog.Builder()
                    .setOnDialogChoiseCameraClickListener(new DialogChoiseCameraClickListener() {
                        @Override
                        public void onDialogChoiseCameraClickListener(Dialog dialog) {
                            dialog.dismiss();
                            cameraPic();
                        }
                    })
                    .setOnDialogChoiseGalleryClickListener(new DialogChoiseGalleryClickListener() {
                        @Override
                        public void onDialogChoiseGalleryClickListener(Dialog dialog) {
                            dialog.cancel();
                            if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
                            } else {
                                PhotoUtils.openPic(UserInfoActivity.this, GALLERY_CODE);
                            }
                        }
                    }).Build(this);
        }
        mChoisePicDialog.show();
    }

    /**
     * 调用相机
     */
    private void cameraPic() {
        mImgFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/cache/" + System.currentTimeMillis() + ".jpg");
        mImgFile.getParentFile().mkdirs();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "您已经拒绝过一次", Snackbar.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            //有权限直接调用系统相机拍照
            if (ExistSDCard()) {
                mImageCaptureUri = Uri.fromFile(mImgFile);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    mImageCaptureUri = FileProvider.getUriForFile(this, "com.zele.maipuxiaoyuan.fileProvider", mImgFile);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, mImageCaptureUri, REQUEST_PICK_FROM_CAMERA);
            } else {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "设备没有SD卡", Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
            // 申请相机权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ExistSDCard()) {
                    mImageCaptureUri = Uri.fromFile(mImgFile);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        mImageCaptureUri = FileProvider.getUriForFile(this, "com.zele.maipuxiaoyuan.fileProvider", mImgFile);//通过FileProvider创建一个content类型的Uri
                    PhotoUtils.takePicture(this, mImageCaptureUri, REQUEST_PICK_FROM_CAMERA);
                } else {
                    SnackbarUtil.ShortSnackbar(mSubmitBtn, "设备没有SD卡", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "请允许打开相机!", Snackbar.LENGTH_SHORT).show();
            }
        }
        if (requestCode == STORAGE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PhotoUtils.openPic(this, GALLERY_CODE);
            } else {
                SnackbarUtil.ShortSnackbar(mSubmitBtn, "请允许打操作SDCard!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 获取本地图片bitmap对象
     *
     * @param url
     * @return
     */
    public Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 图片选择及拍照结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            // 相册
            case GALLERY_CODE:
                Uri uri = data.getData();
                Intent intent3 = new Intent(UserInfoActivity.this, ClipActivity.class);
                intent3.putExtra("path", PhotoUtils.getPath(this, uri));
                startActivityForResult(intent3, IMAGE_COMPLETE);
                break;
            //从相机拍摄的照片后进行裁剪动作
            case REQUEST_PICK_FROM_CAMERA:
                Intent intent4 = new Intent(UserInfoActivity.this, ClipActivity.class);
                intent4.putExtra("path", mImgFile.getAbsolutePath());//此uri是拍照之前设置的
                startActivityForResult(intent4, IMAGE_COMPLETE);
                break;
            //对剪裁图片处理
            case IMAGE_COMPLETE:
                try {
                    String temppath = data.getStringExtra("path");
                    SharedPreferenceCache.getInstance().setPres("HeadImgPath", temppath);
                    mHeadPicIv.setImageBitmap(getLoacalBitmap(temppath));
                } catch (Exception e) {
                    Toast.makeText(mContext, "发生未知错误！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mChoisePicDialog != null) {
            mChoisePicDialog = null;
        }
    }

    @SuppressLint("NewApi")
    public String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return getCacheDir() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /***********************  QQ、微信登录模块代码--Start  ********************************/

    /**
     * 授权回调
     *
     * @param share_media 授权类型
     */
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
                    mWOpenId=map.get("openid");
                    mWXName=map.get("name");
                    mWXTv.setText(mWXName);
                    mWXStateTv.setVisibility(View.INVISIBLE);
                    SharedPreferenceCache.getInstance().setPres("WXProfileImageUrl",map.get("profile_image_url"));
                    Glide.with(UserInfoActivity.this).load(map.get("profile_image_url")).into(mWXHeadIv);
                } else {
                    mQOpenId=map.get("openid");
                    mQQName=map.get("name");
                    mQQTv.setText(mQQName);
                    mQQStateTv.setVisibility(View.INVISIBLE);
                    SharedPreferenceCache.getInstance().setPres("QQProfileImageUrl",map.get("profile_image_url"));
                    Glide.with(UserInfoActivity.this).load(map.get("profile_image_url")).into(mQQHeadIv);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
                Toast.makeText(UserInfoActivity.this,"授权失败！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
                Toast.makeText(UserInfoActivity.this,"授权取消！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***********************  QQ、微信登录模块代码--End  ********************************/
}
