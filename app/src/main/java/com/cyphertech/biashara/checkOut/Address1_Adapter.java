package com.cyphertech.biashara.checkOut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cyphertech.biashara.Account.AccountObject;
import com.cyphertech.biashara.Account.Address2_Adapter;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.cart.cartArray;
import com.cyphertech.biashara.product.Album;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class Address1_Adapter extends RecyclerView.Adapter<Address1_Adapter.ViewHolder>{

    // @Nullable View emptyView;

    private List<AccountObject> developersLists;
    private Context context;
    private Boolean isSelected = false;
    private int lastSelection = -1;

    static final String NAME_ = "";
    static final String PHONE_ = "";
    static final String ADDRESS_ = "";
    static final String REGION_ = "";
    static final String STATION_ = "";
    static final String ORDER_LIST = "";
    static final String FINAL_PRICE = "";

    ArrayList<Album> OrderListobject;
    Double orderFinalPrice;


//    public interface OnItemClickListener {
//        void onItemClick(String Data, String phone);
//    }
//    private OnItemClickListener onItemClickListener;


    public Address1_Adapter(List<AccountObject> developersLists, Context context) {
        this.developersLists = developersLists;
        this.context = context;
     }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Address1_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_address, parent, false);
        return new Address1_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Address1_Adapter.ViewHolder holder, final int position) {

        final AccountObject developersList1 = developersLists.get(position);
        holder.name.setText(developersList1.getName());
        holder.phone.setText(developersList1.getPhone());
        holder.address.setText(developersList1.getAddress());
        holder.region.setText(developersList1.getRegion());
        holder.station.setText(developersList1.getStation());

        holder.radioButton.setChecked(lastSelection == position);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderListobject = cartArray.getCartListImageUri();
                orderFinalPrice = cartArray.grandTotal();

                int layoutId = R.layout.fragment_log_in__if_not;


                Intent intent = new Intent(v.getContext(), Address.class);
                Bundle bundle = new Bundle();
                Bundle bundle1 = new Bundle();
                Bundle bundle2 = new Bundle();
                Bundle bundle3 = new Bundle();
                Bundle bundle4 = new Bundle();
                Bundle bundle5 = new Bundle();
                Bundle bundle6 = new Bundle();
                /************************************/
                bundle.putString(NAME_, developersList1.getName());
                bundle1.putString(PHONE_, developersList1.getPhone());
                bundle2.putString(ADDRESS_, developersList1.getAddress());
                bundle3.putString(REGION_, developersList1.getRegion());
                bundle4.putString(STATION_, developersList1.getStation());
                bundle5.putParcelableArrayList(ORDER_LIST, OrderListobject);
                bundle6.putDouble(FINAL_PRICE, orderFinalPrice);
                /***************************************/
                 intent.putExtra("BUNDLE_N", bundle);
                intent.putExtra("BUNDLE_P", bundle1);
                intent.putExtra("BUNDLE_A", bundle2);
                intent.putExtra("BUNDLE_R", bundle3);
                intent.putExtra("BUNDLE_L", bundle4);
                //Order List
                intent.putExtra("PRODUCT_LIST", bundle5);
                //Order FinalPrice
                intent.putExtra("FINAL_PRICE", bundle6);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);

              }
        });

    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView name;
        public TextView phone;
        public TextView address;
        public TextView region;
        public TextView station;
        Button Delete;
        public RadioButton radioButton;

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

                    Toast.makeText(Address1_Adapter.this.context, "Shipping Address set", Toast.LENGTH_SHORT).show();
                    //bundle.putString(NAME_, developersLists.get(getAdapterPosition()).getName());

                }
            });


            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Address1_Adapter.this.context, "Thread Under Maintenance \n By: Mbanda Nixon.. ",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }

    }


}
