package com.zele.maipuxiaoyuan.common.base;


/**
 * Description:      Presenter基类
 * Autour：          LF
 * Date：            2018/10/23 11:11
 */
public class BasePresenter<V extends BaseView> {

    private V mView;

    public V getView(){
        return  mView;
    }

    public void attachView(V view){
        this.mView = view;
    }

    public void detachView(){
        this.mView = null;
    }

}
