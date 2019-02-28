package com.zele.maipuxiaoyuan.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.common.base.BaseFragment;
import com.zele.maipuxiaoyuan.mvp.presenter.login.LoginPresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.LoginView;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/1/4 16:55
 */

public class MainThirdFragment extends BaseFragment<LoginView,LoginPresenter> {

    public View mView;
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_third, container, false);
        mContext = getActivity();
        return mView;
    }

    @Override
    public LoginPresenter createPresenter() {
        return null;
    }

    @Override
    public LoginView createView() {
        return null;
    }
}
