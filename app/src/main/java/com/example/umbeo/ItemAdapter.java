package com.example.umbeo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.InputFilter;
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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;
import com.example.umbeo.room.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<ProductEntity> modelList;
    List<CartEntity> cartEntities;
    Context context;
    AppDatabase db;

    int quant;


    UserPreference preference;
    public ItemAdapter(List<ProductEntity> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;

        if (db == null) {
            db = AppDatabase.getInstance(context);
        }

        preference = new UserPreference(context);
        cartEntities = new ArrayList<>();

        loadAll();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(modelList.get(position).getDiscount()==null){
            modelList.get(position).setDiscount(0);
        }


        holder.quantity.setFilters(new InputFilter[]{ new InputFilterMinMax("1", modelList.get(position).getQuantityAvailable().toString())});

        description.setText(modelList.get(position).getUnit()+"");


        for(int i = 0;i<cartEntities.size();i++){
            if(cartEntities.get(i).getProductId().equals(modelList.get(position).getId())){
                holder.quantity.setText(cartEntities.get(i).getQuantity()+"");
                modelList.get(position).setQuantity(cartEntities.get(i).getQuantity());
                if(cartEntities.get(i).getQuantity()==0){
                    DeleteDB(cartEntities.get(i).getProductId());
                    holder.itemView.findViewById(R.id.item_linear).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_plus).setVisibility(View.VISIBLE);
                }
            }
            else {
                holder.quantity.setText(modelList.get(position).getQuantity()+"");
            }
        }

        strawberry_name.setText(modelList.get(position).getName()+"");
        price.setText("$"+modelList.get(position).getPrice());

        try {
            double PriceOld = Double.parseDouble(modelList.get(position).getPrice())+
                    ((modelList.get(position).getDiscount().floatValue()/100)*Double.parseDouble(modelList.get(position).getPrice()));

            crossed.setText("$"+String.format("%.2f",PriceOld));
        } catch (Exception e) {
            e.printStackTrace();
        }

        saved.setText("Save "+modelList.get(position).getDiscount()+"%");


        if(modelList.get(position).getQuantity()>0){
            holder.itemView.findViewById(R.id.item_linear).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.item_plus).setVisibility(View.GONE);
        }
        else {
            holder.itemView.findViewById(R.id.item_linear).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.item_plus).setVisibility(View.VISIBLE);
        }

        try {
           // byte[] decodedString = Base64.decode(modelList.get(position).getImage(), Base64.DEFAULT);
           // Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //decodedByte.setPixel(36,36,Color.WHITE);
           // Glide.with(context).asBitmap().load(decodedByte).into(staryberry_image);


           Bitmap bitmap = Utils.Base64ToBitmap(modelList.get(position).getImage());
           staryberry_image.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        final ViewHolder viewHolderFinal = holder;
        final ProductEntity finalRowItem = modelList.get(position);
        strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.findViewById(R.id.item_linear).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.item_plus).setVisibility(View.GONE);
                int quantity = finalRowItem.getQuantity();
                finalRowItem.setQuantity(quantity + 1);
                viewHolderFinal.quantity.setText(finalRowItem.getQuantity()+"");

                DeleteDB(finalRowItem.getId());
                addDB(new CartEntity(finalRowItem.getName(),finalRowItem.getId(),finalRowItem.getCategoryId(),finalRowItem.getSubCategoryId(),finalRowItem.getSubCategoryId(),finalRowItem.getQuantity(),Double.parseDouble(finalRowItem.getPrice()),finalRowItem.getDiscount()));

                notifyDataSetChanged();
            }
        });


        final ViewHolder viewHolderFinal1 = holder;
        final ProductEntity finalRowItem1 = modelList.get(position);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = finalRowItem1.getQuantity();
                finalRowItem1.setQuantity(quantity + 1);
                viewHolderFinal1.quantity.setText(finalRowItem1.getQuantity()+"");

                updateDB(finalRowItem1.getQuantity(),finalRowItem1.getId());
                notifyDataSetChanged();
            }
        });


        final ViewHolder viewHolderFinal2 = holder;
        final ProductEntity finalRowItem2 = modelList.get(position);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = finalRowItem2.getQuantity();
                finalRowItem2.setQuantity(quantity - 1);
                if(finalRowItem2.getQuantity()==0){
                    holder.itemView.findViewById(R.id.item_linear).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_plus).setVisibility(View.VISIBLE);

                    updateDB(finalRowItem1.getQuantity(),finalRowItem1.getId());
                    notifyDataSetChanged();
                }
            else {
                    viewHolderFinal2.quantity.setText(finalRowItem2.getQuantity()+"");
                    updateDB(finalRowItem2.getQuantity(),finalRowItem2.getId());
                }
                notifyDataSetChanged();
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant_1 = 0;
                try {
                    quant_1 = Integer.parseInt(holder.quantity.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    quant_1 =0;
                }
                dailog(modelList.get(position).getName(),quant_1,modelList.get(position).getImage(),Double.parseDouble(modelList.get(position).getPrice()),
                        modelList.get(position).getUnit(),modelList.get(position).getCategoryId(),modelList.get(position).getSubCategoryId(),modelList.get(position).getId(),modelList.get(position).getDiscount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    TextView strawberry_name;
    TextView description;
    ImageView staryberry_image;
    CardView strawberry_plus;
    ImageView remove;
    ImageView add;
    LinearLayout item_linear,linear;
    TextView price;
    CardView card;
    TextView crossed,saved;



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            strawberry_name = itemView.findViewById(R.id.strawberry_name);
            description = itemView.findViewById(R.id.description);
            staryberry_image = itemView.findViewById(R.id.staryberry_image);
            price = itemView.findViewById(R.id.price);
            crossed = itemView.findViewById(R.id.crossed);
            saved = itemView.findViewById(R.id.saved);

            strawberry_plus = itemView.findViewById(R.id.item_plus);
            item_linear = itemView.findViewById(R.id.item_linear);
            remove = itemView.findViewById(R.id.item_remove);
            add = itemView.findViewById(R.id.item_add);
            quantity = itemView.findViewById(R.id.item_quant);
            linear = itemView.findViewById(R.id.linear);

            card = itemView.findViewById(R.id.card);



            if(preference.getTheme()==1){
                card.setCardBackgroundColor(Color.parseColor("#F6F6F6"));
                item_linear.setBackgroundColor(Color.parseColor("#F6F6F6"));
            }

            if(Integer.parseInt(quantity.getText().toString())>0){
                item_linear.setVisibility(View.VISIBLE);
                strawberry_plus.setVisibility(View.GONE);
            }
            else {
                item_linear.setVisibility(View.GONE);
                strawberry_plus.setVisibility(View.VISIBLE);
            }
        }
    }

    private void loadAll(){
        db.cartDao().getAll().observe((LifecycleOwner) context, new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> Entities) {
                cartEntities = Entities;
                notifyDataSetChanged();
            }
        });
    }

    public void addDB(final CartEntity entity){
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
    public void updateDB(final int quantity, final String prodId){
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

    public void DeleteDB(final String prodId){
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



    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }



    @SuppressLint("NewApi")
    private void dailog(final String name, final int quantity, final String image, final double prices, final String description, final String cat, final String subCat, final String prodId, final int discount) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert li != null;
        View mView = li.inflate(R.layout.generic_dailog, null);
        final CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(Objects.requireNonNull(context).getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);
        TextView descrip = mView.findViewById(R.id.description);

        descrip.setText(description+"");


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        TextView back = mView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });




        final TextView quan = mView.findViewById(R.id.quantity1111);
        quan.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10000")});



        TextView name1 = mView.findViewById(R.id.name);
        name1.setText(name.toUpperCase());

        ImageView add = mView.findViewById(R.id.add);
        final ImageView remove = mView.findViewById(R.id.remove);

        final TextView price = mView.findViewById(R.id.price);
        price.setText("$"+prices);

        quant = quantity;

        quan.setText(quant+"");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;
                quan.setText(quant+"");

                updateDB(quant,prodId);
                 if(quant>=1){
                    remove.setClickable(true);
                }
                notifyDataSetChanged();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant--;
                if(quant==0){
                    DeleteDB(prodId);
                    remove.setClickable(false);
                }
                else if(quant>=1){
                    remove.setClickable(true);
                }
                quan.setText(quant+"");

                updateDB(quant,prodId);

                notifyDataSetChanged();
            }
        });


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quan.setText(quant+"");
                DeleteDB(prodId);
                addDB(new CartEntity(name,prodId,cat,subCat,description,Integer.parseInt(quan.getText().toString()),prices,discount));

                notifyDataSetChanged();

                dialog.cancel();
            }
        });

        ImageView img = mView.findViewById(R.id.image);
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(context).asBitmap().load(decodedByte).into(img);


    }




}
