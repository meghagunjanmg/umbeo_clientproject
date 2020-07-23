package com.example.umbeo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.umbeo.Storage.UserPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlotAdapter extends BaseAdapter {

    HashMap<String,Double> slots;
    Context context;
    private final ArrayList mData;
    UserPreference preference;

    public SlotAdapter(HashMap<String, Double> slots, Context context) {
        this.slots = slots;
        this.context = context;
        mData = new ArrayList();
        mData.addAll(slots.entrySet());

        preference = new UserPreference(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Double> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, Double> item = getItem(position);

        if(preference.getTheme()==1) {
            ((RadioButton) result.findViewById(R.id.slot)).setText(item.getKey() + "");
            ((RadioButton) result.findViewById(R.id.slot)).setTextColor(Color.WHITE);
            ((TextView) result.findViewById(R.id.price)).setText("$ " + item.getValue() + "");
            ((TextView) result.findViewById(R.id.price)).setTextColor(Color.WHITE);
        }
        else {
            ((RadioButton) result.findViewById(R.id.slot)).setText(item.getKey() + "");
            ((RadioButton) result.findViewById(R.id.slot)).setTextColor(Color.BLACK);
            ((TextView) result.findViewById(R.id.price)).setText("$ " + item.getValue() + "");
            ((TextView) result.findViewById(R.id.price)).setTextColor(Color.BLACK);
        }
        return result;
    }

}