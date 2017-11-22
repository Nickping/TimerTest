package com.example.admin.timertest.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.timertest.MainActivity;
import com.example.admin.timertest.R;
import com.example.admin.timertest.dto.HueApi.ConnectionReq;
import com.example.admin.timertest.dto.HueApi.ConnectionRes;
import com.example.admin.timertest.dto.HueApi.Hub;
import com.example.admin.timertest.network.HueService;
import com.example.admin.timertest.network.InternalAddressService;
import com.example.admin.timertest.utils.RetrofitHelper;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.sm_arts.jibcon.R;
//import com.sm_arts.jibcon.ui.splash.tutorial.IntroActivity;

/**
 * Created by admin on 2017-09-15.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    private static final String TAG = FirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"onMessageReceived");

        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        sendPushNotification(title, body);
        Map<String,String> map =remoteMessage.getData();

        switch (body)
        {
            case "getInternalAddress"://내부 주소 알아오기
                getInternalAddress();
                break;
            case "getInternalUserName" ://내부 username 알아오기
                getInternalUserName();
        }
    }

    private void getInternalUserName() {

        HueService service = RetrofitHelper.getInstance().getServices(HueService.class);

        Call<List<ConnectionRes>> c = service.getHueUserName(new ConnectionReq("my_hue_app"));
        c.enqueue(new Callback<List<ConnectionRes>>() {
            @Override
            public void onResponse(Call<List<ConnectionRes>> call, Response<List<ConnectionRes>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"response error",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.body().get(0).error!=null && !TextUtils.isEmpty(response.body().get(0).error.type))
                {
                    Log.d(TAG, "onResponse: "+response.body().get(0).error.toString());
                    Toast.makeText(getApplicationContext(),"링크 버튼을 눌러주세요",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d(TAG, "onResponse: " + response.body().get(0).success.username);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ConnectionRes>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void getInternalAddress() {


        InternalAddressService service = RetrofitHelper.getInstance().getServices(InternalAddressService.class);
        Call<List<Hub>> c = service.getInternalAddress();
        c.enqueue(new Callback<List<Hub>>() {
            @Override
            public void onResponse(Call<List<Hub>> call, Response<List<Hub>> response) {
                Log.d(TAG, "onResponse: " + response.body().get(0).internalipaddress);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Hub>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void sendPushNotification(String title, String body) {
        Log.d(TAG,"sendPushNotification");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("testing","testing");


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // startActivity(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 12 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.jibcon_logo).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jibcon_logo) )
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255,500,2000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
