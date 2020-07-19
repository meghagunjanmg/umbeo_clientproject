package com.example.umbeo;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.umbeo.Storage.UserPreference;

import java.util.ArrayList;
import java.util.List;

import static com.example.umbeo.R.drawable.ic_check;

public class AddressAdapter extends ArrayAdapter<String> {
    List<String> address = new ArrayList<>();
    int item,count=0;
    public SparseBooleanArray checkedState = new SparseBooleanArray();
    UserPreference preference;
    Context context;
    public AddressAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        address = objects;
       this.context = context;

       preference = new UserPreference(context);
    }

    @Override
    public int getCount() {
      return   address.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.my_address_lists, null);
        final CheckBox textView = (CheckBox) v.findViewById(R.id.address);
        final EditText address_et = (EditText) v.findViewById(R.id.address_et);
        final ImageView edit = (ImageView) v.findViewById(R.id.edit);
        ImageView remove = (ImageView) v.findViewById(R.id.remove);

        textView.setText(address.get(position));

        if(preference.getTheme()==1){
            textView.setTextColor(Color.WHITE);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAddresses.selectItem(position);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
              textView.setVisibility(View.GONE);
              address_et.setVisibility(View.VISIBLE);
              //address_et.setText(address.get(position));
              if(count%2==0){
                  address_et.setVisibility(View.GONE);
                  textView.setVisibility(View.VISIBLE);

                  MyAddresses.changeAdd(position,  address_et.getText().toString(),context);
              }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAddresses.removeAdd(position,context);
            }
        });

        return v;

    }

}