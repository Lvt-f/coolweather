package com.example.coolweather.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Vincent on 2017/10/13.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request= new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
