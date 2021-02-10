package com.example.cwnu_diner;

public class SearchListViewItem {
    private String txt_name ;
    private String txt_text ;

    public void setName(String txt_name) {
        this.txt_name = txt_name ;
    }
    public void setText(String txt_text) {
        this.txt_text = txt_text ;
    }
    public String getNo() {
        return txt_name ;
    }
    public String getText() {
        return txt_text ;
    }
}
