package com.cyphertech.biashara.Fragments;


import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.cyphertech.biashara.Account.utils.volleyRequestQue;
import com.cyphertech.biashara.Others.ImageModel;
import com.cyphertech.biashara.Others.SlidingImage_Adapter;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.Utility.Categories_ItemObject;
import com.cyphertech.biashara.Utility.Categories_customAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Categories extends Fragment {



    private static final String URL_DATA = "https://api.github.com/search/users?q=language:java+location:lagos";
    private static final String URL_DATA2 = "https://biz-point.herokuapp.com/categories";
    private static final String URL_DATA3 = "https://api.androidhive.info/json/menu.json";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Categories_ItemObject> developersLists;
    private ProgressWindow progressWindow ;
    LinearLayout mRoot;
    StringRequest stringRequest;


    View view;

    // @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_categories, container, false);



        recyclerView = view.findViewById(R.id.brand_recycle);
        progressConfigurations();
        mRoot =  view.findViewById(R.id.parentlayout11);



        developersLists = new ArrayList<>();

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadUrlData();



        return view;


    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if( mLayoutManager.getItemCount() == 0 ){
//            //Do something
//            loadUrlData();
//        }

    }

    //****************** PROGRRESSBAR CALLS
    @Override
    public void onPause() {
        super.onPause();
        hideProgress();
    }

    public void showProgress(){
        progressWindow.showProgress();
    }


    public void hideProgress(){
        progressWindow.hideProgress();
    }

    private void progressConfigurations(){
        progressWindow = ProgressWindow.getInstance(getActivity());
        ProgressWindowConfiguration progressWindowConfiguration = new ProgressWindowConfiguration();
        progressWindowConfiguration.backgroundColor = Color.parseColor("#32000000") ;
        progressWindowConfiguration.progressColor = Color.WHITE ;
        progressWindow.setConfiguration(progressWindowConfiguration);
    }


    private void loadUrlData() {


        showProgress();
         stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //    progressDialog.dismiss();

                try {
                      hideProgress();
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = new JSONArray(response);

                   //JSONArray array = jsonObject.getJSONArray(0);

                    for (int i = 0; i < array.length(); i++){

                        JSONObject jo = array.getJSONObject(i);

                        String id = jo.getString("_id");
                        String pic = jo.getString("categoryPicture");
                        String name = jo.getString("categoryName");

                        String image_url = URL_DATA2 + '/' + id + '/' + pic;

                        Categories_ItemObject developers = new Categories_ItemObject(name, image_url);


                        developersLists.add(developers);

                    }

                    adapter = new Categories_customAdapter(developersLists, getActivity().getApplicationContext());
                     recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                 hideProgress();
               // Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internt, Check your Network !";
                } else if (volleyError instanceof ServerError) {
                    message = "Server not found, Try after some time!";
                } else if (volleyError instanceof AuthFailureError) {
                   // message = "Cannot connect to Internet...Please check your connection!";
                    message = String.valueOf(R.string.networkFailure);
                    //dialog2(message);

                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Check Your Internet connection and try Again !";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Check Your Network !";
                }else {
                    message = "Something went wrong Try Again !";
                }

                  //Snackbar.make(mRoot, message, Snackbar.LENGTH_LONG).show();
                dialog2(message);


            }
        });

       // RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
       // requestQueue.add(stringRequest);
        volleyRequestQue.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }





    private void dialog2(String message) {

       // AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        Dialog dialog=new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen );
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);



        //Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.network_failure, null, false);

        TextView error = layout.findViewById(R.id.errorMessage);
        TextView tryAgain = layout.findViewById(R.id.errorRetry);

        error.setText(message);
        dialog.setView(layout);

        final AlertDialog alertDialog = dialog.create();

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showProgress();
                //stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f);
                stringRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 20 * 1000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 0;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {
                        Log.i("Error", error.toString());
                    }
                });
                 volleyRequestQue.getInstance(getActivity()).addToRequestQueue(stringRequest);
             }
        });

        alertDialog.show();
    }




public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}


