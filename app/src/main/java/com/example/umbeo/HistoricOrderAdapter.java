package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.response_data.GetOrders.OrdersList;
import com.google.android.material.slider.Slider;

import java.util.List;

public class HistoricOrderAdapter extends RecyclerView.Adapter<HistoricOrderAdapter.ViewHolder> {
    List<OrdersList> modelList;
    Context context;

    public HistoricOrderAdapter(List<OrdersList> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_orders, parent, false);

        HistoricOrderAdapter.ViewHolder vh = new HistoricOrderAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String productName ="";
        for (int i=0;i<modelList.get(position).getProducts().size();i++){
            productName = productName+" "+modelList.get(position).getProducts().get(i).getProduct().getName();
        }
        products.setText(productName+"");

        String[] date = modelList.get(position).getUpdatedAt().split("T");
        time.setText(date[0]+"");

        amount.setText("$"+modelList.get(position).getTotalAmount()+"");

        status.setText("Delivered");

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,HomeScreenActivity.class);
                i.putExtra("Id",1);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    TextView products,time,amount,status;
    Button repeat;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            products = itemView.findViewById(R.id.products);
            time = itemView.findViewById(R.id.time);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
            repeat = itemView.findViewById(R.id.repeat);




        }
    }
}
