package com.zele.maipuxiaoyuan.retrofit;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:      基础网络请求配置
 * Autour：          LF
 * Date：            2017/7/5 16:36
 */

public class BaseModel {

    protected Retrofit mRetrofit;
    public IApiSeivice mService;

    /**
     * 正式构造服务
     */
    public BaseModel() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(7000, TimeUnit.MILLISECONDS)
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//域名、证书校验
//                .hostnameVerifier(HttpsUtils.getHostnameVerifier())//域名、证书校验
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mService=mRetrofit.create(IApiSeivice.class);
    }

    /**
     * 设置是否需要传AccessToken
     */
    public BaseModel(boolean AccessToken){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
                  Request original = chain.request();

                  Request request = original.newBuilder()
                          .addHeader("Authorization", "Bearer" + " " + SharedPreferenceCache.getInstance().getPres("AccessToken"))
                          .addHeader("Content-Type","application/x-www-form-urlencoded")
                          .method(original.method(), original.body())
                          .build();

                  return chain.proceed(request);
              }
          });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mService=mRetrofit.create(IApiSeivice.class);
    }
}
