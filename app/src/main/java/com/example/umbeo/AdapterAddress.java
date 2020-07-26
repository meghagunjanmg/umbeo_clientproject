package com.example.umbeo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbeo.Storage.UserPreference;

import java.util.ArrayList;
import java.util.List;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.ViewHolder> {
    List<String> address;
    Context context;
    UserPreference preference;
    int count=0;
    private RadioButton lastCheckedRB = null;

    public AdapterAddress(List<String> address, Context context) {
        this.address = address;
        this.context = context;
        preference = new UserPreference(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_address_lists, parent, false);

        AdapterAddress.ViewHolder vh = new AdapterAddress.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        addressText.setText(address.get(position)+"");

        try {
            if(preference!=null && preference.getdeliveryAddress().equals(address.get(position))){
                addressText.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addressText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RadioButton checked_rb = (RadioButton) buttonView.findViewById(R.id.address);
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }
                //store the clicked radiobutton
                lastCheckedRB = checked_rb;
                preference.setdeliveryAddress(checked_rb.getText().toString());

                MyAddresses.Back();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                addressText.setVisibility(View.GONE);
                address_et.setVisibility(View.VISIBLE);
                //address_et.setText(address.get(position));
                if(count%2==0){
                    address_et.setVisibility(View.GONE);
                    addressText.setVisibility(View.VISIBLE);

                    MyAddresses.changeAdd(position,  address_et.getText().toString(),context);
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAddresses.removeAdd(position,context);
                address.remove(position);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    RadioButton addressText;
    EditText address_et;
    ImageView edit,remove;
    LinearLayout main_linear;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressText = itemView.findViewById(R.id.address);
              address_et = (EditText) itemView.findViewById(R.id.address_et);
              edit = (ImageView) itemView.findViewById(R.id.edit);
             remove = (ImageView) itemView.findViewById(R.id.remove);
            main_linear = itemView.findViewById(R.id.main_linear);

            if(preference.getTheme()==1){
                addressText.setTextColor(Color.WHITE);
            }

        }
    }
}
