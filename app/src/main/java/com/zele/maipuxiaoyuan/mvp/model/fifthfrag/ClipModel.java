package com.zele.maipuxiaoyuan.mvp.model.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.UploadHeadPicBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Description:      上传头像
 * Autour：          LF
 * Date：            2018/11/24 10:46
 */

public class ClipModel extends BaseModel {
    /**
     * 上传头像图片
     * @param file
     * @param callBack
     */
    public void upLoadHeadPic(MultipartBody.Part file, final PresenterCallBack<UploadHeadPicBean> callBack) {
        Call<UploadHeadPicBean> call=mService.upLoadHeadPic(file);
        call.enqueue(new MyCallBack<UploadHeadPicBean>() {
            @Override
            public void onSuccess(UploadHeadPicBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 提交学生对应的图片信息
     * @param patameter
     * @param callBack
     */
    public void submitStuPicInfo(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.submitStuPicInfo(patameter);
        call.enqueue(new MyCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }
}
