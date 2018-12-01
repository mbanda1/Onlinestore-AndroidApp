package com.cyphertech.biashara.checkOut;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_primary extends Fragment {

    TextView paywithMpesa, paywithAirtel, paywithTelcom;

    ScrollView mpesaView, airtelView, telcomView;
    TextView completeView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final FragmentActivity fragmentBelongActivity = getActivity();
        final View view = inflater.inflate(R.layout.fragment_payment_primary, container, false);

//        paywithMpesa = view.findViewById(R.id.payMpesa);
//        paywithAirtel = view.findViewById(R.id.payAirtel);
//
//
////
////        mpesaView = view.findViewById(R.id.mpesa_);
////        airtelView = view.findViewById(R.id.airtel_);
//
//
//
//        paywithMpesa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mpesaView.setVisibility(View.VISIBLE);
//                airtelView.setVisibility(View.GONE);
//                telcomView.setVisibility(View.GONE);
//
//                completeView.setVisibility(View.VISIBLE);
//             }
//        });
//
//        paywithAirtel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                airtelView.setVisibility(View.VISIBLE);
//                mpesaView.setVisibility(View.GONE);
//                telcomView.setVisibility(View.GONE);
//
//                completeView.setVisibility(View.VISIBLE);
//            }
//        });
//
//        paywithTelcom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                telcomView.setVisibility(View.VISIBLE);
//                mpesaView.setVisibility(View.GONE);
//                airtelView.setVisibility(View.GONE);
//
//                completeView.setVisibility(View.VISIBLE);
//            }
//        });
//
//        completeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog alertDialog = new AlertDialog.Builder(fragmentBelongActivity).create();
//                alertDialog.setTitle("ORDER SUCCESS");
//                alertDialog.setMessage(getString(R.string.cyphetTech));
//                alertDialog.show();
//
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        alertDialog.dismiss();
//                        startMain();
//                    }
//                }, 3000);
//
//
//
//            }
//        });

        return view;
    }

    private void startMain() {
        Intent i=new Intent(getActivity(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }



}

