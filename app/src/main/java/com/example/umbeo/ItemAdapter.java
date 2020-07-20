package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<ProductModel> modelList;
    List<CartEntity> cartEntities;
    Context context;
    AppDatabase db;

    int quant;
    String clicked;
    private LayoutInflater layoutInflater;

    UserPreference preference;
    public ItemAdapter(List<ProductModel> modelList, Context context) {
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
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        for(int i = 0;i<cartEntities.size();i++){
            if(cartEntities.get(i).getProductId().equals(modelList.get(position).getId())){
                holder.quantity.setText(cartEntities.get(i).getQuantity()+"");
                modelList.get(position).setQuantity(cartEntities.get(i).getQuantity());
            }
            else holder.quantity.setText(modelList.get(position).getQuantity()+"");
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
            byte[] decodedString = Base64.decode(modelList.get(position).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Glide.with(context).asBitmap().load(decodedByte).into(staryberry_image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ViewHolder viewHolderFinal = holder;
        final ProductModel finalRowItem = modelList.get(position);
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
                Log.e("TESTING","3 "+modelList.get(position).getName()+" "+quant);

            }
        });


        final ViewHolder viewHolderFinal1 = holder;
        final ProductModel finalRowItem1 = modelList.get(position);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = finalRowItem1.getQuantity();
                finalRowItem1.setQuantity(quantity + 1);
                viewHolderFinal1.quantity.setText(finalRowItem1.getQuantity()+"");

                DeleteDB(finalRowItem1.getId());
                addDB(new CartEntity(finalRowItem1.getName(),finalRowItem1.getId(),finalRowItem1.getCategoryId(),finalRowItem1.getSubCategoryId(),finalRowItem1.getSubCategoryId(),finalRowItem1.getQuantity(),Double.parseDouble(finalRowItem1.getPrice()),finalRowItem1.getDiscount()));
            }
        });


        final ViewHolder viewHolderFinal2 = holder;
        final ProductModel finalRowItem2 = modelList.get(position);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = finalRowItem2.getQuantity();
                finalRowItem2.setQuantity(quantity - 1);
                viewHolderFinal2.quantity.setText(finalRowItem2.getQuantity()+"");

                DeleteDB(finalRowItem2.getId());
                addDB(new CartEntity(finalRowItem2.getName(),finalRowItem2.getId(),finalRowItem2.getCategoryId(),finalRowItem2.getSubCategoryId(),finalRowItem2.getSubCategoryId(),finalRowItem2.getQuantity(),Double.parseDouble(finalRowItem2.getPrice()),finalRowItem2.getDiscount()));


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
                dailog(modelList.get(position).getName(),Integer.parseInt(holder.quantity.getText().toString()),modelList.get(position).getImage(),Double.parseDouble(modelList.get(position).getPrice()),
                        modelList.get(position).getDescription(),modelList.get(position).getCategoryId(),modelList.get(position).getSubCategoryId(),modelList.get(position).getId(),modelList.get(position).getDiscount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    TextView strawberry_name;
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
                card.setBackgroundColor(Color.LTGRAY);
                item_linear.setBackgroundColor(Color.LTGRAY);
            }

            if(Integer.parseInt(quantity.getText().toString())==0){
                strawberry_plus.setVisibility(View.VISIBLE);
                item_linear.setVisibility(View.GONE);
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



    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dailog(final String name, final int quantity,final String image,final double prices,final String description,final String cat,final String subCat,final String prodId,final int discount) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert li != null;
        View mView = li.inflate(R.layout.generic_dailog, null);
        final CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(Objects.requireNonNull(context).getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        final TextView quan = mView.findViewById(R.id.quantity1111);



        TextView name1 = mView.findViewById(R.id.name);
        name1.setText(name.toUpperCase());

        ImageView add = mView.findViewById(R.id.add);
        ImageView remove = mView.findViewById(R.id.remove);

        final TextView price = mView.findViewById(R.id.price);
        price.setText("$"+prices);

        quant = quantity;

        quan.setText(quant+"");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;
                quan.setText(quant+"");
                DeleteDB(prodId);
                addDB(new CartEntity(name,prodId,cat,subCat,description,Integer.parseInt(quan.getText().toString()),prices,discount));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant--;
                if(quant==0){
                    DeleteDB(prodId);
                    addDB(new CartEntity(name,prodId,cat,subCat,description,Integer.parseInt(quan.getText().toString()),prices,discount));
                }
                quan.setText(quant+"");
                DeleteDB(prodId);
                addDB(new CartEntity(name,prodId,cat,subCat,description,Integer.parseInt(quan.getText().toString()),prices,discount));
            }
        });


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quan.setText(quant+"");
                DeleteDB(prodId);
                addDB(new CartEntity(name,prodId,cat,subCat,description,Integer.parseInt(quan.getText().toString()),prices,discount));

                dialog.cancel();
            }
        });

        ImageView img = mView.findViewById(R.id.image);
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(context).asBitmap().load(decodedByte).into(img);


    }




}
