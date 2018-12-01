package com.cyphertech.biashara.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyphertech.biashara.Account.Change_password;
import com.cyphertech.biashara.Account.ChooseAddress_Account2;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Frag_2.My_order_Activities;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TextView address_button, address_personalInfo, change_password, nameLink;
    TextView logOut;

    private LinearLayout pending, InTransit, Completed;

    public static String EXTRA_PAGE = "0";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        logOut = view.findViewById(R.id.address_LogOut);

        //create a switch to display proper Icons when logged in Or Out
        if (sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
              logOut.setVisibility(View.VISIBLE);
        }


        address_button = view.findViewById(R.id.address_button);
        address_personalInfo = view.findViewById(R.id.address_personalInfo);
        change_password = view.findViewById(R.id.address_changePassoworddB);

        pending = view.findViewById(R.id.classPending);
        InTransit = view.findViewById(R.id.classInTransit);
        Completed = view.findViewById(R.id.classCompleted);
        nameLink = view.findViewById(R.id.nameLink);
        logOut = view.findViewById(R.id.address_LogOut);

        address_personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), personal_Info.class);
                startActivity(intent);            }
        });
        address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseAddress_Account2.class);
                startActivity(intent);

            }
        });

        //logOut
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrence.getInstance(getActivity().getApplicationContext()).logout();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Change_password.class);
                startActivity(intent);
            }
        });


      /*************************************************************************/
      pending.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), My_order_Activities.class);
              intent.putExtra(EXTRA_PAGE, 0);
              startActivityForResult(intent, 30);
          }
      });


      InTransit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), My_order_Activities.class);
              intent.putExtra(EXTRA_PAGE, 1);
              startActivityForResult(intent, 31);
          }
      });

      Completed.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), My_order_Activities.class);
              intent.putExtra(EXTRA_PAGE, 2);
              startActivityForResult(intent, 32);
          }
      });

      nameLink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), Login_If.class);
              startActivity(intent);
          }
      });
        return view;
    }




}
