package com.cyphertech.biashara.product;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.Utility.Categories_customAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Brands extends AppCompatActivity {

     private static final String URL_DATA = "https://biz-point.herokuapp.com/brands";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Album> developersLists;
    private ProgressWindow progressWindow ;

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);


//       //load screen back to normal when we press back arow
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                finish();
//            }
//        }, 100);

        progressConfigurations();


//        getActionBar().setDisplayHomeAsUpEnabled(true);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Intent intent = getIntent();
        final String catName = intent.getStringExtra(Categories_customAdapter.CATEGORY_NAME);
         String URL_DATA1 = URL_DATA.trim() + '/' + catName;

        // String URL_DATA2 = URLEncoder.encode(URL_DATA1, "UTF-8");
        String URL_DATA2 = URL_DATA1.replaceAll(" " ,"%20");
        loadUrlData(URL_DATA2);


        setTitle(catName);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       // recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        developersLists = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }



    public void showProgress(){
        progressWindow.showProgress();
    }


    public void hideProgress(){
        progressWindow.hideProgress();
    }

    private void progressConfigurations(){
        progressWindow = ProgressWindow.getInstance(this);
        ProgressWindowConfiguration progressWindowConfiguration = new ProgressWindowConfiguration();
        progressWindowConfiguration.backgroundColor = Color.parseColor("#32000000") ;
        progressWindowConfiguration.progressColor = Color.WHITE ;
        progressWindow.setConfiguration(progressWindowConfiguration);
    }

    private void loadUrlData(final String category) {

        // final ProgressDialog progressDialog = new ProgressDialog(this);
       // progressDialog.setMessage("Loading...");
      //  progressDialog.show();

        showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            //    progressDialog.dismiss();

                try {
                    hideProgress();

                    JSONArray array = new JSONArray(response);


                    for (int i = 0; i < array.length(); i++){

                        JSONObject jo = array.getJSONObject(i);

                        String id = jo.getString("_id");
                        //category and type
                        String b_cat = jo.getString("brandCategory");
                        String b_name = jo.getString("brandName");
                        Double b_price = jo.getDouble("brandPrice");
                        String b_spec = jo.getString("brandSpecification");
                        String b_desc = jo.getString("brandDescription");
                        String b_image1 = jo.getString("brandImage_1").trim();

                        String image1 = category + '/' + id + '/' + b_image1;




                        Album developers = new Album(id, b_cat, b_name, image1,b_price, b_spec, b_desc);
                        developersLists.add(developers);

                    }

                    adapter = new Brands_CustomAdapter(developersLists, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                Toast.makeText(Brands.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (sharedPrefrence.getInstance(this).isLoggedIn()) {
//            getMenuInflater().inflate(R.menu.every_where_not_logged_in, menu);
//        } else {
//            getMenuInflater().inflate(R.menu.every_where_logged_in, menu);
//
//        }
//        return true;
//    }

//    @Override
//    public boolean onNavigateUp(){
//        finish();
//        return true;
//    }

       @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            getMenuInflater().inflate(R.menu.every_where_not_logged_in, menu);
        } else {
            getMenuInflater().inflate(R.menu.every_where_logged_in, menu);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
         }

        //not logged In events
        if (id == R.id.not_home){
            Intent i=new Intent(Brands.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_login) {
            startActivity(new Intent(Brands.this, Login_If.class));
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(Brands.this, personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(Brands.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            startActivity(new Intent(Brands.this, Login_If.class));
        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(Brands.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }

}





