package com.example.umbeo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    List<CategoryModel> modelList;
    Context context;
    ItemAdapter itemAdapter;
    public CategoryListAdapter(List<CategoryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        category_name.setText(modelList.get(position).getCategoryName()+"");
        view_more.setText("View More");
        itemAdapter = new ItemAdapter(modelList.get(position).getCategoryItems(), context);
        item_recycler.setAdapter(itemAdapter);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    TextView category_name,view_more;
    RecyclerView item_recycler;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name = itemView.findViewById(R.id.category_name);
            view_more = itemView.findViewById(R.id.view_more);
            item_recycler = itemView.findViewById(R.id.item_recycler);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
            item_recycler.setLayoutManager(mGridLayoutManager);

        }
    }
}
