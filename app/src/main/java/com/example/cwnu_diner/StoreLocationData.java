package com.example.cwnu_diner;
// Store map에 표기될 데이터 저장
public class StoreLocationData {

    String storename;
    String type;
    double lat;
    double lng;

    public StoreLocationData(String storename, String type, double lat, double lng)
    {
        this.storename = storename;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
    }


    public String getStorename() {
        return storename;
    }

    public String getType() {
        return type;
    }

    public double getLat() {
        return lat;
    }


    public double getLng() {
        return lng;
    }

}
