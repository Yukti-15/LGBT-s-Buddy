package com.umeeds.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    LayoutInflater inflator;
    ArrayList<String> arrayList;

    public SpinnerAdapter(Context context, ArrayList<String> spinnerArrayList) {
        inflator = LayoutInflater.from(context);
        this.arrayList = spinnerArrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.item_spinner, null);
        TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
        deposit_channerl_.setText(arrayList.get(position));
        return convertView;
    }
}