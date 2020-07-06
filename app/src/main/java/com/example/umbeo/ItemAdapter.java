package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.List;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<ItemModel> modelList;
    Context context;
    AppDatabase db;

    int quant;
    String clicked;
    private LayoutInflater layoutInflater;

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

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

     //   byte[] imageByteArray = Base64.decode(modelList.get(position).getImage(), Base64.DEFAULT);
    /*   Picasso.get()
                .load(modelList.get(position).getImage().toString())
                .resize(100, 100)
                .centerCrop()
                .error(R.drawable.strawberry_icon)
                .into(staryberry_image);

     */

/*     strawberry_plus.setOnClickListener(new View.OnClickListener() {
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


        strawberry_name.setText(modelList.get(position).getName()+"");
//        quantity.setText(modelList.get(position).getQuantity()+"");

        Glide.with(context).load(getImage(modelList.get(position).getImage())).into(staryberry_image);


        final ViewHolder viewHolderFinal = holder;
        final ItemModel finalRowItem = modelList.get(position);
        strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.findViewById(R.id.item_linear).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.item_plus).setVisibility(View.GONE);
                int quantity = finalRowItem.getQuantity(); // get the quantity for this row item
                finalRowItem.setQuantity(quantity + 1); // update it by adding 1
                viewHolderFinal.quantity.setText(finalRowItem.getQuantity()+""); // set the new description (that uses the updated qunatity)

                DeleteDB(finalRowItem.getName());
                addDB(new CartEntity(finalRowItem.getName(),finalRowItem.getQuantity(),50));
                Log.e("TESTING","3 "+modelList.get(position).getName()+" "+quant);

            }
        });


        final ViewHolder viewHolderFinal1 = holder;
        final ItemModel finalRowItem1 = modelList.get(position);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = finalRowItem1.getQuantity(); // get the quantity for this row item
                finalRowItem1.setQuantity(quantity + 1); // update it by adding 1
                viewHolderFinal1.quantity.setText(finalRowItem1.getQuantity()+""); // set the new description (that uses the updated qunatity)

                DeleteDB(finalRowItem1.getName());
                addDB(new CartEntity(finalRowItem1.getName(),finalRowItem1.getQuantity(),50));
            }
        });


        final ViewHolder viewHolderFinal2 = holder;
        final ItemModel finalRowItem2 = modelList.get(position);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = finalRowItem2.getQuantity(); // get the quantity for this row item
                finalRowItem2.setQuantity(quantity - 1); // update it by adding 1
                viewHolderFinal2.quantity.setText(finalRowItem2.getQuantity()+""); // set the new description (that uses the updated qunatity)

                DeleteDB(finalRowItem2.getName());
                addDB(new CartEntity(finalRowItem2.getName(),finalRowItem2.getQuantity(),50));


                if(finalRowItem2.getQuantity()==0){
                    holder.itemView.findViewById(R.id.item_linear).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_plus).setVisibility(View.VISIBLE);
                }
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog(modelList.get(position).getName(),modelList.get(position).getQuantity(),modelList.get(position).getImage());
            }
        });
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
    LinearLayout item_linear;

    CardView card;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            strawberry_name = itemView.findViewById(R.id.strawberry_name);
            staryberry_image = itemView.findViewById(R.id.staryberry_image);

            strawberry_plus = itemView.findViewById(R.id.item_plus);
            item_linear = itemView.findViewById(R.id.item_linear);
            remove = itemView.findViewById(R.id.item_remove);
            add = itemView.findViewById(R.id.item_add);
            quantity = itemView.findViewById(R.id.item_quant);

            card = itemView.findViewById(R.id.card);

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



    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dailog(final String name, final int quantity, String image) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert li != null;
        View mView = li.inflate(R.layout.generic_dailog, null);
        CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(Objects.requireNonNull(context).getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(context,HomeScreenActivity.class);
                i.putExtra("Cat",5);
                context.startActivity(i);
            }
        });
        ImageView img = mView.findViewById(R.id.image);
        Glide.with(context).load(getImage(image)).into(img);

        TextView name1 = mView.findViewById(R.id.name);
        name1.setText(name.toUpperCase());

        ImageView add = mView.findViewById(R.id.add);
        ImageView remove = mView.findViewById(R.id.remove);
        final TextView quan = mView.findViewById(R.id.quantity1111);

        quant = quantity;
        quan.setText(quant+"");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;
                quan.setText(quant+"");
                DeleteDB(name);
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant--;
                if(quant==0){
                    //  orange_linear.setVisibility(View.GONE);
                    //  orange_plus.setVisibility(View.VISIBLE);
                    DeleteDB(name);
                    addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
                }
                quan.setText(quant+"");
                DeleteDB(name);
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }




}
