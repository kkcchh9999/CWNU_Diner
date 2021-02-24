package com.example.cwnu_diner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Store> stores;

    public StoreAdapter(Context context, ArrayList<Store> stores){
        this.context=context;
        this.stores=stores;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View userView=inflater.inflate(R.layout.search_itemlist,parent,false);

        VH holder = new VH(userView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh= (VH) holder;

        Store store= stores.get(position);

        vh.storeName.setText(store.getStoreName());
        vh.address.setText(store.getAddress());
        vh.tel.setText(store.getTel());
        vh.starRatingAvg.setText(store.getStarRatingAvg());
        vh.openingHours.setText(store.getOpeningHours());
    }

    @Override
    public int getItemCount() {
       return stores.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView address;
        TextView tel;
        TextView openingHours;
        TextView starRatingAvg;
        public VH(@NonNull View storeView) {
            super(storeView);

            storeName=storeView.findViewById(R.id.storeName);
            address=storeView.findViewById(R.id.address);
            tel=storeView.findViewById(R.id.tel);
            openingHours=storeView.findViewById(R.id.openingHours);
            starRatingAvg=storeView.findViewById(R.id.star);


        }
    }

}
