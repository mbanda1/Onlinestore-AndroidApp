package com.cyphertech.biashara.Frag_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.R;

public class PendingOrder2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.activity_pending_order2, container, false);

        if (sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
            startActivity(new Intent(getActivity(), Login_If.class));
        }


        return view;
    }
}
