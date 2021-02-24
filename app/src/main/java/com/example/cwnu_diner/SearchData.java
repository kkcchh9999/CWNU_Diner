package com.example.cwnu_diner;

import java.io.Serializable;

public class SearchData implements Serializable {

    private String storeName;
    private String star;
    private String openingHours;
    private String tel;
    private String address;
    private String type;

    public SearchData(String storeName, String star, String openingHours, String tel, String address, String type) {
        this.storeName = storeName;
        this.star = star;
        this.openingHours = openingHours;
        this.tel = tel;
        this.address = address;
        this.type = type;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) { this.star = star; }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) { this.tel = tel; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address; }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

}

