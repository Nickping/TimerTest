package com.example.admin.timertest.dto.HueApi;

/**
 * Created by admin on 2017-11-12.
 */

public class ConnectionReq {
    public String devicetype;
    public Boolean on;

    public ConnectionReq() {
    }

    public ConnectionReq(String devicetype) {
        this.devicetype = devicetype;
    }

}
