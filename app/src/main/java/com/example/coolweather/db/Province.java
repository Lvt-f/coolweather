package com.example.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Vincent on 2017/10/11.
 */

public class Province extends DataSupport{
    private int id;
    private String provinceName;
    private int provinceCode;

    //id字段
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //省份名称
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    //省份代号
    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
