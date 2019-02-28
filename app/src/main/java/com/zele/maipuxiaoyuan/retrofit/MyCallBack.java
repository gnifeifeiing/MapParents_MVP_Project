package com.zele.maipuxiaoyuan.retrofit;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.NetUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:      retrofit封装的回调
 * Autour：          LF
 * Date：            2017/7/5 17:37
 */
public abstract class MyCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        //200是服务器有合理响应
        if (response.raw().code() == 200) {
            Log.i("Retrofit-response-info",response.raw().request().toString());
            onSuccess(response.body());
        } else {//失败响应
            Log.e("Retrofit"," code : "+ String.valueOf(response.raw().code())+" ; "+response.raw().request().toString());
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //失败先判断网络状态
        if(!NetUtils.isNetworkConnected(MyApplication.getInstance())){
            onFail(Constants.NETWORK_NO);
            return;
        }
        //再判断失败原因
        if(t instanceof SocketTimeoutException){
            onFail(Constants.TIMEOUT_ERROR);
        }else if(t instanceof ConnectException){
            onFail(Constants.CONNECT_NOT_ERROR);
        }else if(t instanceof JsonSyntaxException){
            onFail(Constants.JSON_SYNTAX_ERROR);
        }else if(t instanceof IllegalStateException){
            onFail(Constants.JSON_SYNTAX_ERROR);
        }else if(t instanceof RuntimeException){
            onFail(Constants.CONNECT_FAIL);
        }else{
            onFail(Constants.CONNECT_FAIL);
        }
        Log.e(MyCallBack.class.getName(),t.toString());
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(String message);
}
