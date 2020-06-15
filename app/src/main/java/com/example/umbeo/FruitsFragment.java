package com.example.umbeo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.Objects;

public class FruitsFragment extends Fragment {

    ImageView lichi, strawbe, orange, pineapple;
    LinearLayout editAddress;
    TextView lichipopupText, strawberryText, orangeText, pineappleText;
    Dialog mDialog;
    ImageView orange_plus, lichi_plus, strawberry_plus;

    AppDatabase db;
    static int staw_count = 0, lichi_count = 0, orange_count = 0;
    LinearLayout straw_linear, orange_linear, lichi_linear;
    ImageView add, remove, add2, remove2, add3, remove3;
    TextView quantity, quantity3, quantity2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fruit_new, container, false);
        straw_linear = v.findViewById(R.id.strawberry_linear);
        add = v.findViewById(R.id.add);
        remove = v.findViewById(R.id.remove);
        quantity = v.findViewById(R.id.quantity);

        orange_linear = v.findViewById(R.id.orange_linear);
        add3 = v.findViewById(R.id.add3);
        remove3 = v.findViewById(R.id.remove3);
        quantity3 = v.findViewById(R.id.quantity3);

        lichi_linear = v.findViewById(R.id.lichi_linear);
        add2 = v.findViewById(R.id.add1);
        remove2 = v.findViewById(R.id.remove1);
        quantity2 = v.findViewById(R.id.quantity1);


        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "database-name").build();


        editAddress = (LinearLayout) v.findViewById(R.id.editAddress);
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });


        LinearLayout strawberry = v.findViewById(R.id.strawberry);
        strawberry.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog("strawbery");
            }
        });

        LinearLayout lichi = v.findViewById(R.id.lichi);
        lichi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                dailog("lichi");
            }
        });


        strawberry.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog("strawbery");
            }
        });


        lichi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                dailog("lichi");
            }
        });

        final LinearLayout orange = v.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog("orange");
            }
        });


        orange_plus = v.findViewById(R.id.orange_plus);
        orange_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count++;
                if (orange_count > 0) {
                    orange_linear.setVisibility(View.VISIBLE);
                    orange_plus.setVisibility(View.GONE);
                    quantity3.setText(orange_count + "");
                } else {

                }

                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 50));
            }
        });
        lichi_plus = v.findViewById(R.id.lichi_plus);
        lichi_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count++;
                if (lichi_count > 0) {
                    lichi_linear.setVisibility(View.VISIBLE);
                    lichi_plus.setVisibility(View.GONE);
                    quantity2.setText(lichi_count + "");
                } else {

                }
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 50));
            }
        });
        strawberry_plus = v.findViewById(R.id.strawberry_plus);
        strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count++;
                if (staw_count > 0) {
                    straw_linear.setVisibility(View.VISIBLE);
                    strawberry_plus.setVisibility(View.GONE);
                    quantity.setText(staw_count + "");
                } else {

                }
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 50));
            }
        });

       /*      orange = (ImageView)v.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

   pineappleText= (TextView)v.findViewById(R.id.pineappletext);
        pineappleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_pineapple, null);
                Button addtocart = mView.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        pineapple=(ImageView)v.findViewById(R.id.pineapple);
        pineapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

        strawbe=(ImageView)v.findViewById(R.id.strawberry);
        strawbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

            }
        });


        strawberryText = (TextView)v.findViewById(R.id.straaaa);
        strawberryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_strawberry, null);
                Button addtocart = mView.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

         */

        return v;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        quantity.setText(staw_count + "");
        if (staw_count == 0) {
            straw_linear.setVisibility(View.GONE);
            strawberry_plus.setVisibility(View.VISIBLE);
        } else {
            straw_linear.setVisibility(View.VISIBLE);
            strawberry_plus.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count++;
                quantity.setText(staw_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 50));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count--;
                if (staw_count == 0) {
                    straw_linear.setVisibility(View.GONE);
                    strawberry_plus.setVisibility(View.VISIBLE);
                }

                quantity.setText(staw_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 50));
            }
        });

        quantity2.setText(lichi_count + "");
        if (lichi_count == 0) {
            lichi_linear.setVisibility(View.GONE);
            lichi_plus.setVisibility(View.VISIBLE);
        } else {
            lichi_linear.setVisibility(View.VISIBLE);
            lichi_plus.setVisibility(View.GONE);
        }
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count++;
                quantity2.setText(lichi_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 50));
            }
        });
        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count--;
                if (lichi_count == 0) {
                    lichi_linear.setVisibility(View.GONE);
                    lichi_plus.setVisibility(View.VISIBLE);
                }

                quantity3.setText(lichi_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 50));
            }
        });

        quantity3.setText(orange_count + "");
        if (orange_count == 0) {
            orange_linear.setVisibility(View.GONE);
            orange_plus.setVisibility(View.VISIBLE);
        } else {
            orange_linear.setVisibility(View.VISIBLE);
            orange_plus.setVisibility(View.GONE);
        }
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count++;
                quantity3.setText(orange_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 50));
            }
        });
        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count--;
                if (orange_count == 0) {
                    orange_linear.setVisibility(View.GONE);
                    orange_plus.setVisibility(View.VISIBLE);
                }

                quantity3.setText(orange_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 50));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dailog(String name) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.generic_dailog, null);
        CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), login.class));
            }
        });
        ImageView img = mView.findViewById(R.id.image);
        Glide.with(Objects.requireNonNull(getContext())).load(getImage(name)).into(img);

        TextView name1 = mView.findViewById(R.id.name);
        name1.setText(name.toUpperCase());

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public int getImage(String imageName) {

        int drawableResourceId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
        return drawableResourceId;
    }


    private void addDB(final CartEntity entity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().insertOne(entity);
                    Log.e("roomDB", entity.getItem_name());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void DeleteDB(final String name) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().deleteOne(name);
                    Log.e("roomDB", name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}