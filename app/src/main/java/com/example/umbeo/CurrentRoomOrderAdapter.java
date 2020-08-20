package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.MyOrderActivity;
import com.example.umbeo.R;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.response_data.GetOrders.OrdersList;
import com.example.umbeo.room.OrderEntity;
import com.google.android.material.slider.Slider;

import java.util.List;

public class CurrentRoomOrderAdapter extends RecyclerView.Adapter<com.example.umbeo.CurrentRoomOrderAdapter.ViewHolder> {
    List<OrderEntity> modelList;
    Context context;
    UserPreference preference;

    public CurrentRoomOrderAdapter(List<OrderEntity> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        preference = new UserPreference(context);
    }

    @NonNull
    @Override
    public com.example.umbeo.CurrentRoomOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_current_order, parent, false);

        com.example.umbeo.CurrentRoomOrderAdapter.ViewHolder vh = new com.example.umbeo.CurrentRoomOrderAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final com.example.umbeo.CurrentRoomOrderAdapter.ViewHolder holder, final int position) {


        products.setText(modelList.get(position).getProduct_quantity()+"");

        String[] date = modelList.get(position).getDate().split("T");
        time.setText(date[0]+"");
        amount.setText("$"+modelList.get(position).getAmount()+"");


        instruction.setText(modelList.get(position).getInstruction()+"");

        if(modelList.get(position).getConfirmedByShop()) {
            if (modelList.get(position).getStatus() == 0) {
                status.setValue(0);
            } else if (modelList.get(position).getStatus() == 1) {
                status.setValue(30);
            } else if (modelList.get(position).getStatus() == 2) {
                status.setValue(60);
            } else if (modelList.get(position).getStatus() == 3) {
                status.setValue(90);
            }
        }
        else {
            status.setVisibility(View.GONE);
            Waiting.setVisibility(View.VISIBLE);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                holder.card1.setVisibility(View.GONE);
                                MyOrderActivity.cancelOrder(modelList.get(position).getOrderId());
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


        holder.call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+preference.getShopPh()));
                    context.startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    //CardView card1;
    TextView products,time,amount,instruction,Waiting;
    Slider status;
    Button cancel;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card1;
        ImageView call_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            products = itemView.findViewById(R.id.products);
            Waiting = itemView.findViewById(R.id.Waiting);
            time = itemView.findViewById(R.id.time);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.slider);
            cancel = itemView.findViewById(R.id.cancel);
            card1 = itemView.findViewById(R.id.card1);
            instruction = itemView.findViewById(R.id.instruction);
            call_btn = itemView.findViewById(R.id.call_btn);

            status.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            if(preference.getTheme()==1){
                card1.setCardBackgroundColor(Color.parseColor("#F8F8F8"));
            }

        }
    }
}
