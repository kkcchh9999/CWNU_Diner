package com.example.cwnu_diner;

import java.io.Serializable;

public class StoreData implements Serializable {
    String storeName;
    String address;
    String tel;
    String type;
    String openingHours;
    String starRatingAvg;
    Double latitude;
    Double longitude;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getStarRatingAvg() {
        return starRatingAvg;
    }

    public void setStarRatingAvg(String starRatingAvg) {
        this.starRatingAvg = starRatingAvg;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public StoreData(String storeName, String address, String tel, String type, String openingHours, String starRatingAvg, Double latitude, Double longitude) {
        this.storeName = storeName;
        this.address = address;
        this.tel = tel;
        this.type = type;
        this.openingHours = openingHours;
        this.starRatingAvg = starRatingAvg;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
