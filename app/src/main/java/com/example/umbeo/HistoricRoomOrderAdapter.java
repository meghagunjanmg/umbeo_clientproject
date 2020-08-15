
   package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.response_data.GetOrders.OrdersList;
import com.example.umbeo.room.OrderEntity;
import com.google.android.material.slider.Slider;

import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

    public class HistoricRoomOrderAdapter extends RecyclerView.Adapter<com.example.umbeo.HistoricRoomOrderAdapter.ViewHolder> {
        List<OrderEntity> modelList;
        Context context;
        UserPreference preference;

        public HistoricRoomOrderAdapter(List<OrderEntity> modelList, Context context) {
            this.modelList = modelList;
            this.context = context;
            preference = new UserPreference(context);
        }

        @NonNull
        @Override
        public com.example.umbeo.HistoricRoomOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_orders, parent, false);

            com.example.umbeo.HistoricRoomOrderAdapter.ViewHolder vh = new com.example.umbeo.HistoricRoomOrderAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final com.example.umbeo.HistoricRoomOrderAdapter.ViewHolder holder, int position) {

            products.setText(modelList.get(position).getProduct_quantity()+"");

            instruction.setText(modelList.get(position).getInstruction()+"");

            String[] date = modelList.get(position).getDate().split("T");
            time.setText(date[0]+"");

            amount.setText("$"+modelList.get(position).getAmount()+"");

            status.setText("Delivered");

            repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,HomeScreenActivity.class);
                    i.putExtra("Id",1);
                    context.startActivity(i);
                    Bungee.fade(context);
                }
            });
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }


        TextView products,time,amount,status,instruction;
        Button repeat;
        public class ViewHolder extends RecyclerView.ViewHolder {
            CardView card1;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                products = itemView.findViewById(R.id.products);
                time = itemView.findViewById(R.id.time);
                amount = itemView.findViewById(R.id.amount);
                status = itemView.findViewById(R.id.status);
                repeat = itemView.findViewById(R.id.repeat);
                card1 = itemView.findViewById(R.id.card1);
                instruction = itemView.findViewById(R.id.instruction);

                if(preference.getTheme()==1){
                    card1.setCardBackgroundColor(Color.parseColor("#F8F8F8"));
                }
            }
        }
    }
