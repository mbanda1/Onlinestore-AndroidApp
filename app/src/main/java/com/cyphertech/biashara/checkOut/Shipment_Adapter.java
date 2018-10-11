package com.cyphertech.biashara.checkOut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyphertech.biashara.R;
import com.cyphertech.biashara.product.Album;

import java.util.List;

public class Shipment_Adapter extends RecyclerView.Adapter<Shipment_Adapter.ViewHolder> {

    private List<Album> developersLists;
    private Context context;

    public Shipment_Adapter(List<Album> developersLists, Context applicationContext) {
        this.developersLists = developersLists;
        this.context = applicationContext;
    }

    @Override
    public Shipment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_loginif_adapter, parent, false);
        return new Shipment_Adapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Shipment_Adapter.ViewHolder holder, int position) {

        final Album developersList = developersLists.get(position);

        holder.name.setText(developersList.getName());
        holder.qty.setText(Integer.toString(developersList.getQuantity()));

        Double prc = developersList.getPrice();
        String stringDoubleprice = Double.toString(prc);
        holder.price.setText(stringDoubleprice);
        holder.one.setText("mbanda_one");



    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView qty;
        TextView price;
        TextView one;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nm);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.prc);
            one = itemView.findViewById(R.id.one);

        }
    }
}
