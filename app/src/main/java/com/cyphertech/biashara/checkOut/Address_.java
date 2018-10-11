package com.cyphertech.biashara.checkOut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;

import static com.cyphertech.biashara.checkOut.Address1_Adapter.ADDRESS_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.FINAL_PRICE;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.NAME_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.PHONE_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.REGION_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.STATION_;

public class Address_ extends Fragment  {


     ScrollView proccedToPayment;
    TextView selectShippingAddress, proceed_ToPayment;
    LinearLayout addressView;
    TextView nm, phone, add, region, station, finalPrice;

    Button address_change;



    //////////////////////// FACILITATE MOVE TO NEXT PAGE
    private OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }



    /***************************/
    Address_ fragment1;

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        getActivity().getSupportFragmentManager().putFragment(outState, "oneone", fragment1);
//        super.onSaveInstanceState(outState);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_address_detailss, container, false);
        setHasOptionsMenu(true);

        if (!sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
            startActivity(new Intent(getActivity(), Login_If.class));
        }

            fragment1 = new Address_();

        addressView = view.findViewById(R.id.selectedAddress);
         proceed_ToPayment = view.findViewById(R.id.proceed_ToPayment);
         proccedToPayment = view.findViewById(R.id.proceed_ToPayment_scrooler);
         selectShippingAddress = view.findViewById(R.id.selectetShippingAddress);
         address_change = view.findViewById(R.id.address_change);


         Intent intent = getActivity().getIntent();

         // Bundle bundle = this.getArguments();
         Bundle args = intent.getBundleExtra("BUNDLE_N");
         Bundle args1 = intent.getBundleExtra("BUNDLE_P");
         Bundle args2 = intent.getBundleExtra("BUNDLE_A");
         Bundle args3 = intent.getBundleExtra("BUNDLE_R");
         Bundle args4 = intent.getBundleExtra("BUNDLE_L");
         //final Price
         Bundle args5 = intent.getBundleExtra("FINAL_PRICE");

         if (args != null || args1 != null) {
             addressView.setVisibility(View.VISIBLE);

             //*********
             //**   POPULATE OBTAINED ADDRESS THROUGH INTENT
             //8888**
             nm = view.findViewById(R.id.address_name1);
             phone = view.findViewById(R.id.address_phone1);
             add = view.findViewById(R.id.address_address1);
             region = view.findViewById(R.id.address_region12);
             station = view.findViewById(R.id.address_station12);
             finalPrice = view.findViewById(R.id.finalPrice);


             proccedToPayment.setVisibility(View.VISIBLE);
             selectShippingAddress.setVisibility(View.INVISIBLE);

             assert args != null;
             String name_ = (String) args.getSerializable(NAME_);
             String phone_ = (String) args1.getSerializable(PHONE_);
             String address_ = (String) args2.getSerializable(ADDRESS_);
             String region_ = (String) args3.getSerializable(REGION_);
             String station_ = (String) args4.getSerializable(STATION_);

             Double finalP = (Double) args5.getDouble(FINAL_PRICE);

             //Toast.makeText(getActivity(), name_ +"\n"+phone_+"\n"+address_+"\n"+region_+"\n"+station_, Toast.LENGTH_LONG).show();

                          setToRespectiveFields(finalP, name_, phone_, address_, region_, station_);

           }



         proceed_ToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
             }
        });

         selectShippingAddress.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), ChooseAddress_checkOut1.class);
              startActivityForResult(intent, 250);  }
      });
         address_change.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), ChooseAddress_checkOut1.class);
                 startActivity(intent);
             }
         });



        return view;
    }

    private void setToRespectiveFields(Double finalP, String name_, String phone_, String address_, String region_, String station_) {

        String finalP_ = Double.toString(finalP);
        finalPrice.setText(finalP_);
        nm.setText(name_);
         phone.setText(phone_);
         add.setText(address_);
         region.setText(region_);
         station.setText(station_);
    }



    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
            inflater.inflate(R.menu.every_where_not_logged_in, menu);
        } else {
            inflater.inflate(R.menu.every_where_logged_in, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //not logged In events
        if (id == R.id.not_home){
            Intent i=new Intent(getActivity(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_home) {
            startActivity(new Intent(getActivity(), Login_If.class));
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(getActivity(), personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(getActivity(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            startActivity(new Intent(getActivity(), Login_If.class));
        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(getActivity(), personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }


}


