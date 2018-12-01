package com.cyphertech.biashara.Account;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyphertech.biashara.Account.utils.volleyRequestQue;
import com.cyphertech.biashara.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

public class Address2_Adapter extends RecyclerView.Adapter<Address2_Adapter.ViewHolder>{

    // @Nullable View emptyView;

    private List<AccountObject> developersLists;
    private Context context;
     private int lastSelection = -1;

    private OnAddressClickListener onAddressClickListener;


    public interface OnAddressClickListener {
        void onAddressClick(String phone);
    }

        public Address2_Adapter(List<AccountObject> developersLists, Context context, OnAddressClickListener onAddressClickListener) {
        this.developersLists = developersLists;
        this.context = context;
        this.onAddressClickListener = onAddressClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    @Override
    public Address2_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_address, parent, false);
        return new Address2_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final AccountObject developersList1 = developersLists.get(position);
        holder.name.setText(developersList1.getName());
        holder.phone.setText(developersList1.getPhone());
        holder.address.setText(developersList1.getAddress());
        holder.region.setText(developersList1.getRegion());
        holder.station.setText(developersList1.getStation());

        holder.radioButton.setChecked(lastSelection == position);


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddress(developersList1.getPhone().trim());
            }
        });

    }

    private void deleteAddress(String phone) {

        Toast.makeText(Address2_Adapter.this.context, "Thread Under Maintenance \n By: Mbanda Nixon.. ", Toast.LENGTH_SHORT).show();


      /*  RequestQueue queue = Volley.newRequestQueue(this.context);  // this = context

        final String url = "https:/biz-point.herokuapp.com/addressdlt/" + phone;

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Log.d("Error.Response", response);
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);  */
    }


    @Override
    public int getItemCount() {
        return developersLists.size();
    }

    public void removeItem(int position) {
        developersLists.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView name, phone, address, region, station;
        Button Delete;
        RadioButton radioButton;


        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.address_name);
            phone = itemView.findViewById(R.id.address_phone);
            address = itemView.findViewById(R.id.address_address);
            region = itemView.findViewById(R.id.address_region1);
            station = itemView.findViewById(R.id.address_station1);

            Delete = itemView.findViewById(R.id.address_delete);

            radioButton = itemView.findViewById(R.id.stateSwitch);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelection = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(Address2_Adapter.this.context, "Shipping Address set", Toast.LENGTH_SHORT).show();
                    //bundle.putString(NAME_, developersLists.get(getAdapterPosition()).getName());

                }
            });

//            Delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//
//
//            });
        }

    }


}
