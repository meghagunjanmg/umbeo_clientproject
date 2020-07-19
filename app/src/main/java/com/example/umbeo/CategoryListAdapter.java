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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.Storage.UserPreference;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    List<CategoryModel> modelList;
    Context context;
    UserPreference preference;
    ItemAdapter itemAdapter;
    public CategoryListAdapter(List<CategoryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        preference = new UserPreference(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_card, parent, false);

        CategoryListAdapter.ViewHolder vh = new CategoryListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        category_name.setText(modelList.get(position).getCategoryName()+"");

        if(modelList.get(position).getCategoryId().equals("")){
            view_more.setVisibility(View.GONE);
        }
        view_more.setText("View More");
        view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CategoryActivity.class);
                intent.putExtra("category_id",modelList.get(position).getCategoryId());
                intent.putExtra("category_name",modelList.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });
        itemAdapter = new ItemAdapter(modelList.get(position).getCategoryItems(), context);
        item_recycler.setAdapter(itemAdapter);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    TextView category_name,view_more;
    RecyclerView item_recycler;
    LinearLayout main_linear;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name = itemView.findViewById(R.id.category_name);
            view_more = itemView.findViewById(R.id.view_more);
            main_linear = itemView.findViewById(R.id.main_linear);

            item_recycler = itemView.findViewById(R.id.item_recycler);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
            item_recycler.setLayoutManager(mGridLayoutManager);


            if(preference.getTheme()==1){
                main_linear.setBackgroundColor(Color.BLACK);
                category_name.setTextColor(Color.WHITE);
                view_more.setTextColor(Color.WHITE);
            }
        }
    }
}
