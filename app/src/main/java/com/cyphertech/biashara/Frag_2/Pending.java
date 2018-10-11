package com.cyphertech.biashara.Frag_2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Pending extends Fragment {


    private Context mContext;
    private TextView one;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_pending, container, false);

        if (sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
            startActivity(new Intent(getActivity(), Login_If.class));
        }


        Bundle bundle = this.getArguments();
//        String myValue = bundle.getString("message");

        one = view.findViewById(R.id.oneT);
      //  one.setText(myValue);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
