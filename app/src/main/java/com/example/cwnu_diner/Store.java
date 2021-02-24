package com.example.cwnu_diner;

public class Store {
    String storeName;
    String address;
    String tel;
    String type;
    String openingHours;
    String starRatingAvg;
    String latitude;
    String longitude;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Store(String storeName, String address, String tel, String type, String openingHours, String starRatingAvg, String latitude, String longitude) {
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
