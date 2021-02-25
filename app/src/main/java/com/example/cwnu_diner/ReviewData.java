package com.example.cwnu_diner;

public class ReviewData {

    String userID, storeName, review, menu;
    String starRating;

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

    public ReviewData(String userID, String storeName, String review, String menu, String starRating)
    {
        this.userID = userID;
        this.storeName = storeName;
        this.review = review;
        this.menu = menu;
        this.starRating = starRating;
    }


}