package com.example.umbeo;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<ItemModel> modelList;
    Context context;
    AppDatabase db;

    int staw_count;
    int clicked;

    public ItemAdapter(List<ItemModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        if (db == null) {
            db = AppDatabase.getInstance(context);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fruits_card, parent, false);

        ItemAdapter.ViewHolder vh = new ItemAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder holder, final int position) {
        strawberry_name.setText(modelList.get(position).getName());

     //   byte[] imageByteArray = Base64.decode(modelList.get(position).getImage(), Base64.DEFAULT);
    /*    Picasso.get()
                .load(modelList.get(position).getImage().toString())
                .resize(100, 100)
                .centerCrop()
                .error(R.drawable.strawberry_icon)
                .into(staryberry_image);

     strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = position;

                staw_count++;
                quantity.setText(staw_count+"");
                if(staw_count>0){
                    strawberry_linear.setVisibility(View.VISIBLE);
                    strawberry_plus.setVisibility(View.GONE);
                }
                else {

                }
                Toast.makeText(context, "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(modelList.get(clicked).getName());
                addDB(new CartEntity(modelList.get(clicked).getName(),Integer.parseInt(quantity.getText().toString()),50));

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = position;

                staw_count++;
                quantity.setText(staw_count+"");
                Toast.makeText(context, "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(modelList.get(clicked).getName());
                addDB(new CartEntity(modelList.get(clicked).getName(),Integer.parseInt(quantity.getText().toString()),50));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = position;

                staw_count--;
                if(staw_count==0){
                    strawberry_linear.setVisibility(View.GONE);
                    strawberry_plus.setVisibility(View.VISIBLE);
                    DeleteDB(modelList.get(clicked).getName());
                }

                quantity.setText(staw_count+"");
                Toast.makeText(context, "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(modelList.get(clicked).getName());
                addDB(new CartEntity(modelList.get(clicked).getName(),Integer.parseInt(quantity.getText().toString()),50));
            }
        });

     */


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    TextView strawberry_name;
    TextView quantity;
    ImageView staryberry_image;
    FrameLayout strawberry_plus;
    ImageView remove;
    ImageView add;
    LinearLayout strawberry_linear;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            strawberry_name = itemView.findViewById(R.id.strawberry_name);
            staryberry_image = itemView.findViewById(R.id.staryberry_image);

            strawberry_plus = itemView.findViewById(R.id.strawberry_plus);
            strawberry_linear = itemView.findViewById(R.id.strawberry_linear);
            remove = itemView.findViewById(R.id.remove);
            add = itemView.findViewById(R.id.add);
            quantity = itemView.findViewById(R.id.quantity);


        }
    }



    private void addDB(final CartEntity entity){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().insertOne(entity);
                    Log.e("roomDB",entity.getItem_name());
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
