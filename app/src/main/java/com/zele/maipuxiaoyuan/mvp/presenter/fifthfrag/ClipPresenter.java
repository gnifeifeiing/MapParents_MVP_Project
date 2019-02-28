package com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.UploadHeadPicBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.fifthfrag.ClipModel;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.ClipView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/24 10:52
 */

public class ClipPresenter extends BasePresenter<ClipView> {

    private ClipModel mClipModel;

    public ClipPresenter() {
        this.mClipModel = new ClipModel();
    }

    /**
     * 上传头像图片
     * @param file
     */
    public void upLoadHeadPic(MultipartBody.Part file){
        mClipModel.upLoadHeadPic(file, new PresenterCallBack<UploadHeadPicBean>(){

            @Override
            public void onSuccess(UploadHeadPicBean result) {
                if(getView()!=null){
                    getView().upLoadHeadPic(result);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if(getView()!=null){
                    getView().onError(errMsg);
                }
            }
        });
    }

    /**
     * 提交学生对应的图片信息
     * @param parameter
     */
    public void submitStuPicInfo(Map<String, String> parameter){
        mClipModel.submitStuPicInfo(parameter, new PresenterCallBack<BaseBean>(){

            @Override
            public void onSuccess(BaseBean result) {
                if(getView()!=null){
                    getView().submitStuPicInfo(result);
                }
            }

            @Override
            public void onFail(String errMsg) {
                if(getView()!=null){
                    getView().onError(errMsg);
                }
            }
        });
    }
}
