package com.example.admin.timertest.routes;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.timertest.GlobalApplication;
import com.example.admin.timertest.dto.HueApi.ConnectionReq;
import com.example.admin.timertest.dto.HueApi.ConnectionRes;
import com.example.admin.timertest.dto.HueApi.Hub;
import com.example.admin.timertest.network.HueService;
import com.example.admin.timertest.network.InternalAddressService;
import com.example.admin.timertest.utils.ConvertUtils;
import com.example.admin.timertest.utils.RetrofitHelper;
import com.example.admin.timertest.utils.UrlUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017-11-12.
 */

public class RouterManager {
    private static final String TAG = RouterManager.class.getName();
    public static RouterManager obj = null;

    public static RouterManager getInstance() {
        if (obj == null) {
            synchronized (RouterManager.class) {
                if (obj == null)
                    obj = new RouterManager();
            }
        }
        return obj;
    }



    public void getInternalAddress() {

        InternalAddressService service = RetrofitHelper.getInstance().getServices(InternalAddressService.class);
        Call<List<Hub>> c = service.getInternalAddress();
        HashMap hashmap = new HashMap();
        hashmap.keySet();
        c.enqueue(new Callback<List<Hub>>() {
            @Override
            public void onResponse(Call<List<Hub>> call, Response<List<Hub>> response) {
                Log.d(TAG, "onResponse: " + response.body().get(0).internalipaddress);
                UrlUtils.setUrls("internalAddress", response.body().get(0).internalipaddress);

                Toast.makeText(GlobalApplication.getGlobalApplicationContext(), "Success : " + response.body().get(0).internalipaddress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Hub>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(GlobalApplication.getGlobalApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void getInternalUsername() {
        HueService service = RetrofitHelper.getInstance().getServices(HueService.class);

        Call<List<ConnectionRes>> c = service.getHueUserName(new ConnectionReq("my_hue_app"));
        c.enqueue(new Callback<List<ConnectionRes>>() {
            @Override
            public void onResponse(Call<List<ConnectionRes>> call, Response<List<ConnectionRes>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(GlobalApplication.getGlobalApplicationContext(), "response error", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body().get(0).error != null && !TextUtils.isEmpty(response.body().get(0).error.type)) {
                    Log.d(TAG, "onResponse: " + response.body().get(0).error.toString());
                    Toast.makeText(GlobalApplication.getGlobalApplicationContext(), "링크 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onResponse: " + response.body().get(0).success.username);
                    UrlUtils.setUrls("username", response.body().get(0).success.username);
                    Toast.makeText(GlobalApplication.getGlobalApplicationContext(), "Success" + response.body().get(0).success.username, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ConnectionRes>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(GlobalApplication.getGlobalApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    public void getLights() {
        HueService service = RetrofitHelper.getInstance().getServices(HueService.class);
        Log.d(TAG, "getLights: "+UrlUtils.getStringWithKey("username"));
        Call<HashMap<String,Object>> c = service.getLights(UrlUtils.getStringWithKey("username"));
        c.enqueue(new Callback<HashMap<String, Object>>() {
            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                HashMap<String, Object> result = response.body();
                Log.d(TAG, "onResponse: ");
                for (String key :
                        result.keySet()) {
                    Object item = result.get(key);
                    Log.d(TAG, "onResponse: item=" + item.toString());
                    HueBulb hueBulb = ConvertUtils.convertHueBulb((LinkedTreeMap<String, Object>) item);
                    hueBulb.ID = key;
                    GlobalApplication.getBulbList().put(hueBulb.name,hueBulb);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void turnOnRestroom() {
        HueBulb hueBulb = GlobalApplication.getBulbList().get("Restroom light");
        HueService hueService = RetrofitHelper.getInstance().getServices(HueService.class);
        ConnectionReq connectionReq = new ConnectionReq();
        connectionReq.on = true;
        Call<List<ConnectionRes>> c = hueService.changeBulbState(UrlUtils.getStringWithKey("username"),hueBulb.ID,connectionReq);
        c.enqueue(new Callback<List<ConnectionRes>>() {
            @Override
            public void onResponse(Call<List<ConnectionRes>> call, Response<List<ConnectionRes>> response) {
                Log.d(TAG, "onResponse: turnOnRestroom");
                Log.d(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<List<ConnectionRes>> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: turnOnRestroom");

            }
        });
    }

    public void turnOffRestroom() {
        HueBulb hueBulb = GlobalApplication.getBulbList().get("Restroom light");
        HueService hueService = RetrofitHelper.getInstance().getServices(HueService.class);
        ConnectionReq connectionReq = new ConnectionReq();
        connectionReq.on = false;
        Call<List<ConnectionRes>> c = hueService.changeBulbState(UrlUtils.getStringWithKey("username"),hueBulb.ID,connectionReq);
        c.enqueue(new Callback<List<ConnectionRes>>() {
            @Override
            public void onResponse(Call<List<ConnectionRes>> call, Response<List<ConnectionRes>> response) {
                Log.d(TAG, "onResponse: turnOnRestroom");
                Log.d(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<List<ConnectionRes>> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: turnOnRestroom");

            }
        });
    }
}
