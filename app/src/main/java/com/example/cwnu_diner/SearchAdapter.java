package com.example.cwnu_diner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder> implements Filterable {



    private List<StoreData> dataList;
    private List<StoreData> dataListAll;
    private Context context;

    public SearchAdapter(List<StoreData> items, Context context) {
        this.dataList = items;
        this.dataListAll = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_itemlist, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final StoreData currentItem = dataList.get(position);

        holder.storeName.setText(currentItem.getStoreName());
        holder.starRatingAvg.setText(currentItem.getStarRatingAvg());
        holder.openingHours.setText(currentItem.getOpeningHours());
        holder.tel.setText(currentItem.getTel());
        holder.address.setText(currentItem.getAddress());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               context.startActivity(new Intent(context, StoreClickActivity.class).putExtra("data", currentItem).addFlags(FLAG_ACTIVITY_NEW_TASK));


           }
       });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<StoreData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList=dataListAll;
                dataList=dataListAll;
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StoreData item : dataListAll) {
                    if (item.getStoreName().toLowerCase().contains(filterPattern)
                            ||item.getType().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //dataList.clear();
            dataList = (ArrayList<StoreData>)results.values;
            notifyDataSetChanged();
        }
    };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, starRatingAvg, openingHours, tel, address;

        ItemViewHolder(final View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            starRatingAvg=itemView.findViewById(R.id.star);
            openingHours = itemView.findViewById(R.id.openingHours);
            tel = itemView.findViewById(R.id.tel);
            address = itemView.findViewById(R.id.address);

        }

    }

}

