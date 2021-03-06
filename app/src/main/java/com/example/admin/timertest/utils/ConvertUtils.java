package com.example.admin.timertest.utils;

import android.util.Log;

import com.example.admin.timertest.routes.HueBulb;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by admin on 2017-11-12.
 */

public class ConvertUtils {
    private static final String TAG = "ConvertUtils";

    public static HueBulb convertHueBulb(LinkedTreeMap<String, Object> item) {
        HueBulb hueBulb = new HueBulb();
        hueBulb.state = (LinkedTreeMap<String, Object>) item.get("state");
        hueBulb.name = (String)item.get("name");
        Log.d(TAG, "convertHueBulb: hueBulb=" + hueBulb.toString());
        Log.d(TAG, "convertHueBulb: hueBulbState"+hueBulb.state.get("on"));
        return hueBulb;
    }
}


