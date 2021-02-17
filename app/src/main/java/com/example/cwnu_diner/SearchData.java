package com.example.cwnu_diner;

public class SearchData {
    public int image;
    private String name;
    private String text;

    public SearchData(int image, String name, String text) {
        this.image = image;
        this.name = name;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
