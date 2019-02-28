package com.zele.maipuxiaoyuan.mvp.presenter.firstfrag;

import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.MessageClassesListviewBean;
import com.zele.maipuxiaoyuan.common.base.BasePresenter;
import com.zele.maipuxiaoyuan.mvp.model.firstfrag.BooksModel;
import com.zele.maipuxiaoyuan.mvp.view.firstfrag.BooksView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;

import java.util.Map;

/**
 * Description:      作业
 * Autour：          LF
 * Date：            2018/11/17 14:16
 */

public class BooksPresenter extends BasePresenter<BooksView> {

    private BooksModel mBooksModel;

    public BooksPresenter() {
        this.mBooksModel = new BooksModel();
    }

    /**
     * 获取考勤数据
     * @param parameter
     */
    public void getBooksData(Map<String, String> parameter){
        mBooksModel.getBooksData(parameter, new PresenterCallBack<MessageClassesListviewBean>(){

            @Override
            public void onSuccess(MessageClassesListviewBean result) {
                if(getView()!=null){
                    getView().getBooksData(result);
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
     * 设置当前信息为已读
     * @param parameter
     */
    public void setReadByMsgId(Map<String, String> parameter, final int position){
        mBooksModel.setReadByMsgId(parameter, new PresenterCallBack<BaseBean>(){

            @Override
            public void onSuccess(BaseBean result) {
                if(getView()!=null){
                    getView().setReadByMsgId(result,position);
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
