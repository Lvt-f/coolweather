package com.example.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Vincent on 2017/10/11.
 */

public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cotyCode;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //城市名称
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    //城市代号
    public int getCotyCode() {
        return cotyCode;
    }

    public void setCotyCode(int cotyCode) {
        this.cotyCode = cotyCode;
    }

    public int getProvinceId() {
        return provinceId;
    }
    //城市所属省份id值
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
