package com.example.cwnu_diner;

import java.util.Date;

public class ReviewData {

    String userID, storeName, review, menu;
    String starRating;
    String date;

    public String getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getReview() {
        return review;
    }

    public String getMenu() {
        return menu;
    }

    public String getStarRating() {
        return starRating;
    }

    public ReviewData(String userID, String storeName, String review, String menu, String starRating, String date)
    {
        this.userID = userID;
        this.storeName = storeName;
        this.review = review;
        this.menu = menu;
        this.starRating = starRating;
        this.date = date;
    }


}