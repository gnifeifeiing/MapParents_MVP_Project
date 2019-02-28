package com.zele.maipuxiaoyuan.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Description:      Fragment基类
 * Autour：          LF
 * Date：            2018/10/23 11:11
 */

public abstract class BaseFragment<V extends BaseView,P extends BasePresenter<V>> extends Fragment {

    private V mView;
    public P mPresenter;

    public int mHeight;
    public int mWidth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
//        mPresenter=createPresenter();
//        if(mPresenter==null){
//            throw new NullPointerException("presenter:空指针异常...");
//        }
//        mView=createView();
//        if(mView==null){
//            throw new NullPointerException("view:空指针异常...");
//        }
//        mPresenter.attachView(mView);

        mPresenter=createPresenter();
        if(mPresenter!=null){
            mView=createView();
            if(mView!=null){
                mPresenter.attachView(mView);
            }
        }
    }

    public abstract P createPresenter();

    public abstract V createView();

    public void initDisplayMetrics(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = outMetrics.heightPixels;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
