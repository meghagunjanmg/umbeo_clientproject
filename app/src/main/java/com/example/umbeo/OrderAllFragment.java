package com.example.umbeo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.umbeo.response_data.GetOrders.OrdersList;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.OrderEntity;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderAllFragment extends Fragment {

    List<OrdersList> currentOrder = new ArrayList<>();
    List<OrdersList> historicOrder = new ArrayList<>();

    List<OrderEntity> currentOrders = new ArrayList<>();
    List<OrderEntity> historicOrders = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderAllFragment(List<OrdersList> currentOrder, List<OrdersList> historicOrder) {
        this.currentOrder = currentOrder;
        this.historicOrder = historicOrder;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public OrderAllFragment newInstance(String param1, String param2) {
        OrderAllFragment fragment = new OrderAllFragment(currentOrder, historicOrder);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_all, container, false);
    }

    RecyclerView item_current_order;
    RecyclerView item_history_orders;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        item_current_order = view.findViewById(R.id.item_current_order);
        item_history_orders = view.findViewById(R.id.item_history_orders);

       if(currentOrder.size()>0) {
           List<OrdersList> notcancelled = new ArrayList<>();
           for (OrdersList ordersList : currentOrder) {
               if (ordersList.getCancelledByUser()) {
               } else {
                   notcancelled.add(ordersList);
               }
           }


           item_current_order.setLayoutManager(new LinearLayoutManager(getContext()));
           CurrentOrderAdapter adapter = new CurrentOrderAdapter(notcancelled, getContext());
           item_current_order.setAdapter(adapter);
       }
       else {
           AppExecutors.getInstance().diskIO().execute(new Runnable() {
               @Override
               public void run() {
                   currentOrders = AppDatabase.getInstance(getContext()).orderDao().loadCurrentAll();
               }
           });
           Log.e("Testing", currentOrders.size()+"");
           item_current_order.setLayoutManager(new LinearLayoutManager(getContext()));
           CurrentRoomOrderAdapter adapter = new CurrentRoomOrderAdapter(currentOrders, getContext());
           item_current_order.setAdapter(adapter);
       }


        if(historicOrder.size()>0) {
            item_history_orders.setLayoutManager(new LinearLayoutManager(getContext()));
            HistoricOrderAdapter adapter = new HistoricOrderAdapter(historicOrder, getContext());
            item_history_orders.setAdapter(adapter);
        }
        else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    historicOrders = AppDatabase.getInstance(getContext()).orderDao().loadHistoryAll();

                }
            });
            item_history_orders.setLayoutManager(new LinearLayoutManager(getContext()));
            HistoricRoomOrderAdapter adapter = new HistoricRoomOrderAdapter(historicOrders, getContext());
            item_history_orders.setAdapter(adapter);
        }





       /* final Slider slider = view.findViewById(R.id.slider);
        slider.setValue(60);
        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        ImageView call_btn = view.findViewById(R.id.call_btn);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCall();
            }
        });

        Button cancel =view.findViewById(R.id.cancel);
        final CardView cardView = view.findViewById(R.id.card1);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Are you sure");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                cardView.setVisibility(View.GONE);
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

        Button repeat = view.findViewById(R.id.repeat);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getContext(),HomeScreenActivity.class);
               i.putExtra("Id",1);
               getContext().startActivity(i);
            }
        });
    }



    private void phoneCall(){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:12345678900"));
        getContext().startActivity(callIntent);
    }

        */

    }
}