package com.example.cwnu_diner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.ItemViewHolder>{

    ArrayList<ReviewData> reviewList;

    public ReviewAdapter(ArrayList<ReviewData> reviewList){
        this.reviewList=reviewList;
    }

    @Override
    public int getItemCount() {
        return reviewList.size();

    }

    @NonNull
    @Override

    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ItemViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder,final int position) {

        ReviewData review=reviewList.get(position);
        String starRating=Integer.toString(review.getStarRating());

        holder.userID.setText(review.getUserID());
        holder.storeName.setText(review.getStoreName());
        holder.review.setText(review.getReview());
        holder.menu.setText(review.getMenu());
        holder.starRating.setText(starRating);
        holder.date.setText(review.getDate());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView menu;
        TextView userID;
        TextView review;
        TextView date;
        TextView starRating;

        ItemViewHolder(@NonNull View ReviewView) {
            super(ReviewView);

            storeName=ReviewView.findViewById(R.id.txt_storeName);
            menu=ReviewView.findViewById(R.id.txt_menu);
            userID=ReviewView.findViewById(R.id.txt_userID);
            review=ReviewView.findViewById(R.id.txt_review);
            date=ReviewView.findViewById(R.id.txt_date);
            starRating=ReviewView.findViewById(R.id.txt_starRating);

        }
    }
}
