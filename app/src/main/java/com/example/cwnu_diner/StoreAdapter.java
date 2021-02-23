package com.example.cwnu_diner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.CustomViewHolder> {

    private ArrayList<StoreData> arrayList;

    public StoreAdapter(ArrayList<StoreData> arrayList) {
        this.arrayList = arrayList;
    }




    @NonNull
    @Override
    public StoreAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override // 추가될때 생명주기
    public void onBindViewHolder(@NonNull final StoreAdapter.CustomViewHolder holder, int position) {
        holder.iv_store.setImageResource(arrayList.get(position).getIv_store());
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_star.setText(arrayList.get(position).getTv_star());
        holder.tv_loc.setText(arrayList.get(position).getTv_loc());

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return (null !=arrayList ? arrayList.size() : 0);
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_store;
        protected TextView tv_name;
        protected TextView tv_star;
        protected TextView tv_loc;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_store = (ImageView) itemView.findViewById(R.id.iv_store);
            this.tv_name =(TextView) itemView.findViewById(R.id.tv_name);
            this.tv_star =(TextView) itemView.findViewById(R.id.tv_star);
            this.tv_loc =(TextView) itemView.findViewById(R.id.tv_loc);
        }
    }
}
