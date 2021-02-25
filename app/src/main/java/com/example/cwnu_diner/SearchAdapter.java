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

    private List<SearchData> dataList;
    private List<SearchData> dataListAll;
    private Context context;

    public SearchAdapter(List<SearchData> items, Context context) {
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
        final SearchData currentItem = dataList.get(position);

        holder.storeName.setText(currentItem.getStoreName());
        holder.star.setText(currentItem.getStar());
        holder.openingHours.setText(currentItem.getOpeningHours());
        holder.tel.setText(currentItem.getTel());
        holder.address.setText(currentItem.getAddress());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             //  context.startActivity(new Intent(context, StoreClickActivity.class).putExtra("data", currentItem));
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

            List<SearchData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                dataList=dataListAll;
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SearchData item : dataListAll) {
                    if (item.getStoreName().toLowerCase().contains(filterPattern)) {
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
            dataList = (ArrayList<SearchData>)results.values;
            notifyDataSetChanged();
        }
    };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, star, openingHours, tel, address;

        ItemViewHolder(final View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            star = itemView.findViewById(R.id.star);
            openingHours = itemView.findViewById(R.id.openingHours);
            tel = itemView.findViewById(R.id.tel);
            address = itemView.findViewById(R.id.address);

        }

    }

}

