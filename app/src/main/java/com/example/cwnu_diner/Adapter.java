package com.example.cwnu_diner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private static final String TAG = "Adapter";
    List<Data> storeList;

    public Adapter(List<Data> storeList){
        this.storeList = storeList;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.name.setText(storeList.get(position).getName());
        holder.text.setText(storeList.get(position).getText());
        holder.image.setImageResource(storeList.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView name, text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            text = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String curName = name.getText().toString();
            Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
        }
    }
}