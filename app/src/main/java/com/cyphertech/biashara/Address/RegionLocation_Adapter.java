package com.cyphertech.biashara.Address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cyphertech.biashara.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RegionLocation_Adapter extends ArrayAdapter {

    private List<RegionLocation_Object> RegionList;

    public RegionLocation_Adapter(@NonNull Context context, int resource, int spinnerText, @NonNull List<RegionLocation_Object> RegionList) {
        super(context, resource, spinnerText, RegionList);
        this.RegionList = RegionList;
    }

    @Nullable
    @Override
    public RegionLocation_Object getItem(int position) {
        return RegionList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         return initView(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    private View initView(int position) {
        RegionLocation_Object state = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.my_region, null);
        TextView textView =  v.findViewById(R.id.spinner_region);
        textView.setText(state.getRegion());
        return v;
    }


}
