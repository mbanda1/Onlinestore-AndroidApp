package com.cyphertech.biashara.TopDeal;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.Utility.Categories_ItemObject;
import com.cyphertech.biashara.Utility.Topdeal_CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopDeal_One extends Fragment implements Topdeal_CustomAdapter.OnImageClickListener  {

    private static final String URL_DATA2 = "https://biz-point.herokuapp.com/categories";

    private static final String URL_DATA = "https://biz-point.herokuapp.com/brands";
    protected static final String CATEGORY_NAME1 = "";


     RecyclerView categories_recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Categories_ItemObject> categories_List;
    private Topdeal_CustomAdapter.OnImageClickListener onImageClickListener;


    //for brands
    final FragmentActivity fragmentBelongActivity = getActivity();
    String image_Name;

    @Override
    public void onImageClick(String imageName) {
        image_Name = imageName;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_top_deal__one, container, false);

        this.onImageClickListener = new Topdeal_CustomAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(String imageName) {
                //Toast.makeText(getActivity(), imageName, Toast.LENGTH_LONG).show();

                if(getActivity() != null) {
                    TopDeal_two topDealTwo =new TopDeal_two();
                    Bundle bundle=new Bundle();
                    bundle.putString(CATEGORY_NAME1,imageName );
                    topDealTwo.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.topdealThree, topDealTwo).commit();


                    // FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //   Fragment rightFragment = fragmentManager.findFragmentById(R.id.fragment_two);
                 //   TextView rightFragmentTextView = Objects.requireNonNull(rightFragment.getView()).findViewById(R.id.test_text);
                 //   rightFragmentTextView.setText(imageName);

                 //   fragmentManager.beginTransaction().replace(R.id.fragment_two, topDealTwo).commit();


                }

            }
        };


        categories_recyclerView = view.findViewById(R.id.linear_categories);
        // categories_recyclerView = view.findViewById(R.id.linear_brands);
        categories_List = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        categories_recyclerView.setLayoutManager(mLayoutManager);
        categories_recyclerView.addItemDecoration(new TopDeal_One.GridSpacingItemDecoration(1,0, true));
        categories_recyclerView.setItemAnimator(new DefaultItemAnimator());
        categories_recyclerView.setAdapter(adapter);


        loadUrlData();

        return view;
    }

    private void loadUrlData() {

        // final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setMessage("Loading...");
        //  progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //    progressDialog.dismiss();

                try {

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


                        categories_List.add(developers);

                    }

                    //FRAGMENT > adapter = new Topdeal_CustomAdapter(categories_List, (TopDeal_One) getApplicationContext());
                    adapter = new Topdeal_CustomAdapter(categories_List,  getActivity().getApplicationContext(), onImageClickListener);

                    categories_recyclerView.setAdapter(adapter);

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



}













/*
public class TopDeal_One extends AppCompatActivity implements Topdeal_CustomAdapter.OnImageClickListener  {

    private static final String URL_DATA2 = "https://biz-point.herokuapp.com/categories";

    private static final String URL_DATA = "https://biz-point.herokuapp.com/brands";


    private RecyclerView categories_recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Categories_ItemObject> categories_List;
     private Topdeal_CustomAdapter.OnImageClickListener onImageClickListener;


    //for brands
    final FragmentActivity fragmentBelongActivity = this;
    String image_Name;

    @Override
    public void onImageClick(String imageName) {
        image_Name = imageName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_deal__one);

        this.onImageClickListener = new Topdeal_CustomAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(String imageName) {
                Toast.makeText(TopDeal_One.this, imageName, Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = fragmentBelongActivity.getSupportFragmentManager();
                Fragment rightFragment = fragmentManager.findFragmentById(R.id.fragment_two);
                TextView rightFragmentTextView = Objects.requireNonNull(rightFragment.getView()).findViewById(R.id.test_text);
                rightFragmentTextView.setText(image_Name);

            }
        };



        categories_recyclerView = findViewById(R.id.linear_categories);
        // categories_recyclerView = view.findViewById(R.id.linear_brands);
        categories_List = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        categories_recyclerView.setLayoutManager(mLayoutManager);
        categories_recyclerView.addItemDecoration(new TopDeal_One.GridSpacingItemDecoration(1,0, true));
        categories_recyclerView.setItemAnimator(new DefaultItemAnimator());
        categories_recyclerView.setAdapter(adapter);


        loadUrlData();


    }




    private void loadUrlData() {

        // final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setMessage("Loading...");
        //  progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //    progressDialog.dismiss();

                try {

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


                        categories_List.add(developers);

                    }

                   //FRAGMENT > adapter = new Topdeal_CustomAdapter(categories_List, (TopDeal_One) getApplicationContext());
                    adapter = new Topdeal_CustomAdapter(categories_List,  getApplicationContext(), onImageClickListener);

                    categories_recyclerView.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(TopDeal_One.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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



}   */
