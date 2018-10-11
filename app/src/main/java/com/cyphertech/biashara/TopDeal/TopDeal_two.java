package com.cyphertech.biashara.TopDeal;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.product.Album;
import com.cyphertech.biashara.product.Brands_CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopDeal_two extends Fragment {

    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    private List<Album> BrandLists;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String URL_DATA = "https://biz-point.herokuapp.com/brands";

    private TextView fragmentRightTextView;

    // scrolling Limit
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_deal_two, container, false);

        fragmentRightTextView = view.findViewById(R.id.test_text);
        recyclerView2 = view.findViewById(R.id.recyclerview_top_three);


        Bundle arguments = getArguments();
        if(arguments != null) {

            final String catName = arguments.getString(TopDeal_One.CATEGORY_NAME1);
            String URL_DATA1 = URL_DATA.trim() + '/' + catName;
            String URL_DATA2 = URL_DATA1.replaceAll(" " ,"%20");
            loadClickedCategory(URL_DATA2);
            getActivity().setTitle(catName);


            BrandLists = new ArrayList<>();
             mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView2.setLayoutManager(mLayoutManager);
            recyclerView2.addItemDecoration(new TopDeal_two.GridSpacingItemDecoration(2, 1, false));
            recyclerView2.setItemAnimator(new DefaultItemAnimator());
            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView2.setHasFixedSize(true);
            fragmentRightTextView.setVisibility(View.GONE);   //prsonal


    }


        fragmentRightTextView.setText("No Load Bar ! \n Click on Category and wait it to load Here.");
        return view;
    }



    private void loadClickedCategory(final String category) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //    progressDialog.dismiss();

                try {

                    JSONArray array = new JSONArray(response);


                    for (int i = 0; i < array.length(); i++){

                        JSONObject jo = array.getJSONObject(i);

                        String id = jo.getString("_id");
                        String b_cat = jo.getString("brandCategory");
                        String b_name = jo.getString("brandName");
                        Double b_price = jo.getDouble("brandPrice");
                        String b_spec = jo.getString("brandSpecification");
                        String b_desc = jo.getString("brandDescription");
                        String b_image1 = jo.getString("brandImage_1").trim();

                        String image1 = category + '/' + id + '/' + b_image1;




                        Album developers = new Album(id, b_cat, b_name, image1,b_price, b_spec, b_desc);
                        BrandLists.add(developers);

                    }

                    adapter2 = new Brands_CustomAdapter(BrandLists, getActivity().getApplicationContext());
                    recyclerView2.setAdapter(adapter2);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
