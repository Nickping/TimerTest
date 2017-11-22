package com.example.admin.timertest;

import android.app.Application;

import com.example.admin.timertest.routes.HueBulb;

import java.util.HashMap;

/**
 * Created by admin on 2017-11-12.
 */

public class GlobalApplication extends Application {
    private static volatile  GlobalApplication obj= null;
    public static HashMap<String,HueBulb> bulbList;

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        bulbList = new HashMap<>();
    }
    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static HashMap<String, HueBulb> getBulbList() {
        return bulbList;
    }

    public static void setBulbList(HashMap<String, HueBulb> bulbList) {
        GlobalApplication.bulbList = bulbList;
    }
}
