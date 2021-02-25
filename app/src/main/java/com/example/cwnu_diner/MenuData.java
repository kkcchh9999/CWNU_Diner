package com.example.cwnu_diner;

import java.io.Serializable;

public class MenuData implements Serializable {
    String menu, storeName, menuType;
    int price;

    public MenuData(String menu, String storeName, int price, String menuType){
        this.menu = menu;
        this.storeName = storeName;
        this.price = price;
        this.menuType = menuType;

    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

}
