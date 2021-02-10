package com.example.cwnu_diner;

import android.graphics.drawable.Drawable;

/////////////////// ListViewAdapter에 들어가는 ListViewItem 클래스 //////////////////////
public class ListViewItem {
    private Drawable iconDrawable ;
    private String name ;
    private String text ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setName(String name) {
        this.name = name ;
    }
    public void setText(String text) {
        this.text = text ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getName() {
        return this.name ;
    }
    public String getText() {
        return this.text ;
    }
}