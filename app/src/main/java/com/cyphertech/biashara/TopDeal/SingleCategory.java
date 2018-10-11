package com.cyphertech.biashara.TopDeal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyphertech.biashara.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleCategory extends Fragment {


    public SingleCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_single_category, container, false);

         return view;
    }

}
