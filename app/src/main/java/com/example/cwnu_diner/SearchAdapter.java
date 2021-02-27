package com.example.cwnu_diner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private List<MenuData> menuData;
    private List<MenuData> menuDataAll;
    private Context context;
    private String userID;

    public SearchAdapter(List<StoreData> items, List<MenuData> menuitems, String userID, Context context) {
        this.dataList = items;
        this.dataListAll = items;
        this.menuData = menuitems;
        this.menuDataAll = menuitems;
        this.context = context;
        this.userID = userID;
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
        if(currentItem.getStarRatingAvg().equals("null")){
            holder.starRatingAvg.setText(" ");
        }
        else{
            holder.starRatingAvg.setText("★ "+currentItem.getStarRatingAvg());
        }
        holder.openingHours.setText(currentItem.getOpeningHours());
        holder.tel.setText(currentItem.getTel());
        holder.address.setText(currentItem.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoreClickActivity.class);
                intent.putExtra("data", currentItem);
                intent.putExtra("userID", userID);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
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

            List<StoreData> store_filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                store_filteredList=dataListAll;
                dataList=dataListAll;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(StoreData data : dataListAll){
                    if (data.getType().contains(filterPattern) || data.getStoreName().contains(filterPattern)) { //Store -> type, storeName
                        store_filteredList.add(data);
                    }
                    else{
                        for(MenuData menu : menuDataAll){
                            if(data.getStoreName().equals(menu.getStoreName())){
                                if(menu.getMenu().contains(filterPattern)||menu.getMenuType().contains(filterPattern)){
                                    store_filteredList.add(data);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = store_filteredList;
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
        TextView menu, menuType;

        ItemViewHolder(final View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            starRatingAvg=itemView.findViewById(R.id.star);
            openingHours = itemView.findViewById(R.id.openingHours);
            tel = itemView.findViewById(R.id.tel);
            address = itemView.findViewById(R.id.address);

            menu= itemView.findViewById(R.id.menu);
            menuType= itemView.findViewById(R.id.menuType);


        }

    }

}

