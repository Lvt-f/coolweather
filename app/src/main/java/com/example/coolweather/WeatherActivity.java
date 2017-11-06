package com.example.coolweather;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.Util.HttpUtil;
import com.example.coolweather.Util.Utility;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Vincent on 2017/11/2.
 */

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_weather);
        //初始化各空间
        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime= findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.confort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);
        SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if(weatherString != null){
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherLayout.setVisibility(View.INVISIBLE);
            showWeatherInfo(weather);
        }else {
            //无缓存时去服务器查询天气
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requeatWeather(weatherId);
        }
    }
    /**
     * 根据天气id请求城市天气信息
     * */
    public void requeatWeather(final String weatherId){
        String weatherUrl = "http://guolin.tech/api/weather?cityid="+
                weatherId+"&key=eb354aeafb3f4c1d8a22bf4954daf164";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null && "ok".equals(weather.status)){
                             SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                    Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
    /**
     * 处理并展示Weather实体类中的数据
     * */
    public void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime;
        String degree = weather.now.temperature + "摄氏度";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
         for(Forecast forecast:weather.forecastList){
             View view = LayoutInflater.from(this).inflate(R.layout.forest_item,
                     forecastLayout,false);
             TextView dateText = view.findViewById(R.id.data_text);
             TextView infoText = view.findViewById(R.id.info_text);
             TextView maxText = view.findViewById(R.id.max_text);
             TextView minText = view.findViewById(R.id.min_text);
             dateText.setText(forecast.date);
             infoText.setText(forecast.more.info);
             maxText.setText(forecast.temperature.max);
             minText.setText(forecast.temperature.min);
             forecastLayout.addView(view);
         }
         if(weather.aqi != null){
             aqiText.setText(weather.aqi.city.aqi);
             pm25Text.setText(weather.aqi.city.pm25);
         }
         String comfort = "舒适度:"+weather.suggestion.comfort.info;
         String carWash = "洗车指数："+weather.suggestion.carWash.info;
         String sport = "运动指数："+ weather.suggestion.sport.info;
         comfortText.setText(comfort);
         carWashText.setText(carWash);
         sportText.setText(sport);
         weatherInfoText.setVisibility(View.VISIBLE);
    }
}