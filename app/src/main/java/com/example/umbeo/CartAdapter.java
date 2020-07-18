package com.example.umbeo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartEntity> data;
    List<Integer> amount = new ArrayList<>();


    Context context;
     int quant = 0;
    private String name ="";
    private int price =0;
    AppDatabase db;
     static int total = 0;

    public CartAdapter(List<CartEntity> data, Context context,AppDatabase db) {
        this.data = data;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        CartAdapter.ViewHolder vh = new CartAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(data.get(position).getQuantity()<=0){
            linearLayout.setVisibility(View.GONE);
           CartMainFragment cartMainFragment = new CartMainFragment();
           cartMainFragment.onResume();
        }

        quantity.setText(data.get(position).getQuantity()+"");
        item_name.setText(data.get(position).getName()+"");


        final int i = position;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant = data.get(i).getQuantity();
                name =  data.get(i).getName();
                price =  data.get(i).getPrice();
                quant++;
                quantity.setText(quant+"");
                DeleteDB(name);
                addDB(new CartEntity(name,data.get(i).getCategoryId(),data.get(i).getSubCategoryId(),data.get(i).getDescription(),Integer.parseInt(quantity.getText().toString()),price));
                total_amount.setText(quant*price+"");

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant = data.get(i).getQuantity();
                name =  data.get(i).getName();
                quant--;
                if(quant==0){
                    //  orange_linear.setVisibility(View.GONE);
                    //  orange_plus.setVisibility(View.VISIBLE);
                    DeleteDB(name);
                    addDB(new CartEntity(name,data.get(i).getCategoryId(),data.get(i).getSubCategoryId(),data.get(i).getDescription(),Integer.parseInt(quantity.getText().toString()),price));
                }
                quantity.setText(quant+"");
                DeleteDB(name);
                addDB(new CartEntity(name,data.get(i).getCategoryId(),data.get(i).getSubCategoryId(),data.get(i).getDescription(),Integer.parseInt(quantity.getText().toString()),price));
                total_amount.setText(quant*price+"");

            }
        });

        quantity.setText(data.get(position).getQuantity()+"");
        total_amount.setText(data.get(position).getQuantity()*data.get(position).getPrice()+"");

            CartMainFragment.amounts.add(Integer.parseInt(total_amount.getText().toString()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    TextView item_name,quantity,total_amount;
    LinearLayout linearLayout;
    ImageView add,remove;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.quantity1111);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            linearLayout = itemView.findViewById(R.id.linear);
            total_amount = itemView.findViewById(R.id.total_amount);
        }
    }



    private void addDB(final CartEntity entity){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().insertOne(entity);
                    Log.e("roomDB",entity.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void DeleteDB(final String name){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().deleteOne(name);
                    Log.e("roomDB",name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
