package com.example.admin.timertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.timertest.routes.RouterManager;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements MainView {


    private static final String TAG = MainActivity.class.getName();
    EditText mEditTextTime;
    EditText mEditTextStr;
    Button mButtonSet;
    Button mButtonGetInternalAddress;
    Button mButtonGetUserName;
    Button mButtonGetLights;
    Button mButtonTurnOnRestroom;
    Button mButtonTurnOffRestroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initLayout();


//
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreate: FirebaseToken : " + token);
    }

    private void initLayout() {
        mEditTextTime = (EditText) findViewById(R.id.et_time);
        mEditTextStr = (EditText) findViewById(R.id.et_message);
        mButtonSet = (Button) findViewById(R.id.btn_set);
        mButtonGetInternalAddress = (Button) findViewById(R.id.btn_getInternalAddress);
        mButtonGetLights = (Button) findViewById(R.id.btn_getLights);
        mButtonGetUserName = (Button) findViewById(R.id.btn_getHueApiUsername);
        mButtonTurnOffRestroom = (Button) findViewById(R.id.btn_turnOffRestroom);
        mButtonTurnOnRestroom = (Button) findViewById(R.id.btn_turnOnRestroom);
        mButtonGetInternalAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            RouterManager.getInstance().getInternalAddress();
            }
        });
        mButtonGetUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().getInternalUsername();
            }
        });


        mButtonGetLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().getLights();
            }
        });

        mButtonTurnOnRestroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().turnOnRestroom();
            }
        });
        mButtonTurnOffRestroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().turnOffRestroom();
            }
        });
    }

}
