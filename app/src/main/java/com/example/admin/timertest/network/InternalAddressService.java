package com.example.admin.timertest.network;

import com.example.admin.timertest.dto.HueApi.Hub;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2017-11-12.
 */

public interface InternalAddressService {
    @GET("/api/nupnp")
    Call<List<Hub>> getInternalAddress();
}
