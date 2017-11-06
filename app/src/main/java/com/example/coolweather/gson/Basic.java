package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vincent on 2017/10/31.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
