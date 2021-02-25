package com.example.cwnu_diner;

public class MyReviewData {
    String storeName;
    String menu;
    String userID;
    String review;
    String date;
    int starRating;

    public String getStoreName() { return storeName; }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getMenu() { return menu; }

    public void setMenu(String menu) { this.menu=menu; }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getReview() { return review; }

    public void setReview(String review) { this.review = review; }

    public String getDate(){ return date; }

    public void setDate(String date) { this.date = date; }

    public int getStarRating() { return starRating; }

    public void setStarRating(int starRating) { this.starRating = starRating; }

    public MyReviewData(String userID, String storeName, String review, String menu,  int starRating, String date) {
        this.storeName=storeName;
        this.menu=menu;
        this.userID=userID;
        this.review=review;
        this.date=date;
        this.starRating=starRating;
    }
}
