package com.example.admin.timertest.utils;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017-11-12.
 */

public class RetrofitHelper {
    public static RetrofitHelper retrofitHelper = null;
    private HashMap<Class,Object> services = new HashMap<>();

    public static RetrofitHelper getInstance()
    {
        if(retrofitHelper == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if(retrofitHelper == null)
                {
                    retrofitHelper = new RetrofitHelper();
                }
            }
        }
        return retrofitHelper;
    }

    public <T> T getServices(Class<? extends T> type)
    {
        T service = (T) services.get(type);
        if(service == null)
        {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(UrlUtils.getUrlWithClassName(type.getName()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = client.create(type);
            services.put(type,service);

        }
        return service;
    }
}
