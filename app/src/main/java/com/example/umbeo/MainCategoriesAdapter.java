package com.example.umbeo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.room.CategoryModel;

import java.util.List;

public class MainCategoriesAdapter extends RecyclerView.Adapter<MainCategoriesAdapter.ViewHolder> {

    List<com.example.umbeo.room.CategoryModel> modelList;
    Context context;
    UserPreference preference;
    public MainCategoriesAdapter(List<com.example.umbeo.room.CategoryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        preference = new UserPreference(context);
    }


    @NonNull
    @Override
    public MainCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoty, parent, false);

        MainCategoriesAdapter.ViewHolder vh = new MainCategoriesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoriesAdapter.ViewHolder holder, final int position) {

        category_name.setText(modelList.get(position).getCategoryName()+"");
        category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CategoryActivity.class);
                intent.putExtra("category_id",modelList.get(position).getCategoryId());
                intent.putExtra("category_name",modelList.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    TextView category_name;
    CardView category_card;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name = itemView.findViewById(R.id.category_name);
            category_card = itemView.findViewById(R.id.category_card);

            if(preference.getTheme()==1){
                category_name.setTextColor(Color.WHITE);
                category_card.setBackgroundColor(Color.BLACK);
            }
        }
    }
}
