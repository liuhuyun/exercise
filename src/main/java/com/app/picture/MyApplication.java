package com.app.picture;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @author lhy 1083005240@qq.com
 * @time 2017/8/4 0004  下午 4:42
 * @explain
 */

public class MyApplication extends Application {


    @Override
    public void onCreate () {
        super.onCreate ();

        Logger.init ("TAG");
        OkHttpClient.Builder builder = new OkHttpClient.Builder ();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor ("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel (HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel (Level.INFO);
        builder.addInterceptor (loggingInterceptor);

        //非必要情况，不建议使用，第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //        builder.addInterceptor(new ChuckInterceptor(this));

        //全局的读取超时时间
        builder.readTimeout (OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout (OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout (OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance ().init (this)                       //必须调用初始化
                .setOkHttpClient (builder.build ())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode (CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime (CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount (3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }
}
