package com.example.cwnu_diner;

public class MainData {
    private int iv_store;
    private String tv_name;
    private String tv_star;
    private String tv_loc;


    public MainData(int iv_store, String tv_name, String tv_star, String tv_loc) {
        this.iv_store = iv_store;
        this.tv_name = tv_name;
        this.tv_star = tv_star;
        this.tv_loc = tv_loc;
    }


    public int getIv_store() {
        return iv_store;
    }

    public void setIv_store(int iv_store) {
        this.iv_store = iv_store;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_star() {
        return tv_star;
    }

    public void setTv_star(String tv_star) {
        this.tv_star = tv_star;
    }

    public String getTv_loc() {
        return tv_loc;
    }

    public void setTv_loc(String tv_loc) {
        this.tv_loc = tv_loc;
    }
}
