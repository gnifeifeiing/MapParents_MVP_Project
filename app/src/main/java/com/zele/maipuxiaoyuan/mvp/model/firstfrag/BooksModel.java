package com.zele.maipuxiaoyuan.mvp.model.firstfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.MessageClassesListviewBean;
import com.zele.maipuxiaoyuan.retrofit.BaseModel;
import com.zele.maipuxiaoyuan.retrofit.MyCallBack;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

import retrofit2.Call;

/**
 * Description:      作业
 * Autour：          LF
 * Date：            2018/11/17 13:50
 */

public class BooksModel extends BaseModel {
    /**
     * 获取考勤数据
     * @param patameter
     * @param callBack
     */
    public void getBooksData(Map<String, String> patameter, final PresenterCallBack<MessageClassesListviewBean> callBack) {
        Call<MessageClassesListviewBean> call=mService.getBooksData(patameter);
        call.enqueue(new MyCallBack<MessageClassesListviewBean>() {
            @Override
            public void onSuccess(MessageClassesListviewBean response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(String message) {
                callBack.onFail(message);
            }
        });
    }

    /**
     * 设置当前信息为已读
     * @param patameter
     * @param callBack
     */
    public void setReadByMsgId(Map<String, String> patameter, final PresenterCallBack<BaseBean> callBack) {
        Call<BaseBean> call=mService.setReadByMsgId(patameter);
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
