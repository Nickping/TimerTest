package com.example.admin.timertest.utils;

import com.example.admin.timertest.network.InternalAddressService;

import java.util.HashMap;

/**
 * Created by admin on 2017-11-12.
 */

public class UrlUtils {
    private static HashMap<String,String> urls;

    static {
        urls = new HashMap<>();

        String InternalServices = "https://www.meethue.com";//디비에서 가져와야 함
        String HueService = "http://192.168.1.83";

        urls.put(com.example.admin.timertest.network.HueService.class.getName(),HueService);
        urls.put(InternalAddressService.class.getName(),InternalServices);



    }
    public static void setUrls(String key, String body)
    {
        urls.put(key,body);
    }
    public static String getStringWithKey(String key)
    {
        return urls.get(key);
    }
    public static String getUrlWithClassName(String className)
    {return urls.get(className);}
}
