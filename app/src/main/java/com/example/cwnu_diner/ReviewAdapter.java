package com.example.cwnu_diner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ReviewData> arrayList;

    public ReviewAdapter(Context context, ArrayList<ReviewData> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.review_itemlist,parent,false);

        VH holder = new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        ReviewData data = arrayList.get(position);

        vh.tv_review.setText(data.getReview());
        vh.tv_userName.setText(data.getUserID());
        vh.tv_menu.setText(data.getMenu());
        vh.tv_star.setText(data.getStarRating());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tv_review, tv_menu, tv_star, tv_userName;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv_review = itemView.findViewById(R.id.tv_review);
            tv_menu = itemView.findViewById(R.id.tv_menu);
            tv_star = itemView.findViewById(R.id.tv_star);
            tv_userName = itemView.findViewById(R.id.tv_username);
        }
    }
}
