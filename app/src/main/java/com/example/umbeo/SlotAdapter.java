package com.example.umbeo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlotAdapter extends BaseAdapter {

    HashMap<String,Double> slots;
    Context context;
    private final ArrayList mData;

    public SlotAdapter(HashMap<String, Double> slots, Context context) {
        this.slots = slots;
        this.context = context;
        mData = new ArrayList();
        mData.addAll(slots.entrySet());
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

        // TODO replace findViewById by ViewHolder
        ((RadioButton) result.findViewById(R.id.slot)).setText(item.getKey() + "");
        ((TextView) result.findViewById(R.id.price)).setText("$ "+item.getValue() + "");

        return result;
    }

}