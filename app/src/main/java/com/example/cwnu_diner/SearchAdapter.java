package com.example.cwnu_diner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder> implements Filterable {

    private List<SearchData> dataList;
    private List<SearchData> dataListAll;
    private StoreClick storeClick;

    //constructor
    public SearchAdapter(List<SearchData> items, StoreClick storeClick) {
        dataList = items;
        dataListAll = new ArrayList<>(items);
        this.storeClick = storeClick;
    }

    //interface - 클릭인터페이스
    private onItemListener itemListener;
    public void setOnClickListener(onItemListener listener){
        itemListener = listener;
    }

    //data set changed
    public void dataSetChanged(List<SearchData> exampleList) {
        dataList = exampleList;
        notifyDataSetChanged();
    }

    //1.onCreateViewHolder -------------------------------------------------------
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_itemlist, parent, false);
        return new ItemViewHolder(v);
    }

    //2.onBindViewHolder  -------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        SearchData currentItem = dataList.get(position);

        holder.image.setImageResource(currentItem.getImage());
        holder.name.setText(currentItem.getName());
        holder.text.setText(currentItem.getText());


        if (itemListener != null){
            final int pos = position;
            //final ItemModel item = mItems.get(viewHolder.getAdapterPosition());
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    itemListener.onItemClicked(position);
                    //mListener.onItemClicked(item);
                }
            });
            //버튼등에도 동일하게 지정할 수 있음 holder.버튼이름.setOnClickListener..형식으로
        }
    }

    //3.getItemCount  -------------------------------------------------------
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface StoreClick{
        void storeClick(SearchData data);
    }


    // 데이터 필터 검색 Filterable implement ---------------------------------
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SearchData> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SearchData item : dataListAll) {
                    
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    // 뷰홀더 클래스  ---------------------------------
    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, text;

        ItemViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            text = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storeClick.storeClick(dataList.get(getAdapterPosition()));
                }
            });
        }
    }

    //onclick listener interface
    //1. interface onItemListener 선언
    //2. 내부에서 mListener 선언하고
    // 외부에서 접근가능하도록 setOnClickListener작성
    //3.onBindViewHolder에서 처리
    public interface onItemListener {
        void onItemClicked(int position);
        //void onItemClicked(ItemModel model); 모델값을 넘길수 있음
        //다른버튼도 정의할 수 있음 onShareButtonClicked(int position);
    }
}