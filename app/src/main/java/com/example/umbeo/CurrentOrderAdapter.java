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

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder> {
    List<OrdersList> modelList;
    Context context;

    public CurrentOrderAdapter(List<OrdersList> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_current_order, parent, false);

        CurrentOrderAdapter.ViewHolder vh = new CurrentOrderAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String productName ="";
        for (int i=0;i<modelList.get(position).getProducts().size();i++){
            productName = productName+" "+modelList.get(position).getProducts().get(i).getProduct().getName();
        }
        products.setText(productName+"");

        time.setText(modelList.get(position).getUpdatedAt()+"");
        amount.setText("$"+modelList.get(position).getTotalAmount()+"");

        if(modelList.get(position).getOrderStatus()==0){
            status.setValue(0);
        }
       else if(modelList.get(position).getOrderStatus()==1){
            status.setValue(30);
        }
       else if(modelList.get(position).getOrderStatus()==2){
            status.setValue(60);
        }
       else if(modelList.get(position).getOrderStatus()==3){
            status.setValue(90);
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
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:12345678900"));
                context.startActivity(callIntent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    //CardView card1;
    TextView products,time,amount;
    Slider status;
    Button cancel;
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card1;
        ImageView call_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            products = itemView.findViewById(R.id.products);
            time = itemView.findViewById(R.id.time);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.slider);
            cancel = itemView.findViewById(R.id.cancel);
            card1 = itemView.findViewById(R.id.card1);
            call_btn = itemView.findViewById(R.id.call_btn);


        }
    }
}
