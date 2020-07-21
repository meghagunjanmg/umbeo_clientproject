package com.example.umbeo;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.Storage.UserPreference;
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
    private double price =0;
    AppDatabase db;
     static int total = 0;

     UserPreference preference;
    public CartAdapter(List<CartEntity> data, Context context,AppDatabase db) {
        this.data = data;
        this.context = context;
        this.db = db;

        preference = new UserPreference(context);
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

                updateDB(quant,data.get(i).getProductId());

                total_amount.setText(quant*price+"");
                notifyDataSetChanged();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant = data.get(i).getQuantity();
                name =  data.get(i).getName();
                price =  data.get(i).getPrice();
                quant--;

                if(quant==0){
                    DeleteDB(data.get(i).getProductId());
                }

                quantity.setText(quant+"");

                updateDB(quant,data.get(i).getProductId());

                total_amount.setText(quant*price+"");
                notifyDataSetChanged();
            }
        });

        quantity.setText(data.get(position).getQuantity()+"");
        total_amount.setText(data.get(position).getQuantity()*data.get(position).getPrice()+"");

        CartMainFragment.amounts.add(Double.parseDouble(total_amount.getText().toString()));

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

            if(preference.getTheme()==1){
                item_name.setTextColor(Color.WHITE);
                quantity.setTextColor(Color.WHITE);
                total_amount.setTextColor(Color.WHITE);
            }
        }
    }


    private void updateDB(final int quantity,final String prodId){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().UpdateOne(quantity,prodId);
                    Log.e("roomDB",prodId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void DeleteDB(final String prodId){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().deleteOne(prodId);
                    Log.e("roomDB",prodId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
