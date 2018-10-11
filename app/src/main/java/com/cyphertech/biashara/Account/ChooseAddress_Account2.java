package com.cyphertech.biashara.Account;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Address.Add_Address;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChooseAddress_Account2 extends AppCompatActivity implements Address2_Adapter.OnAddressClickListener {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AccountObject> address_list;
    private ProgressBar progressBar;
    TextView noAddressAddOption2;
    String N_Phone;
    Toolbar toolbar;

     Address2_Adapter.OnAddressClickListener onAddressClickListener;


    @Override
    public void onAddressClick(String phone) {
            deleteTheAddree(phone);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_account);

        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login_If.class));
        }

        person_infoAdapter me = sharedPrefrence.getInstance(this).getUser();
        N_Phone = me.getPhone();

        Toast.makeText(ChooseAddress_Account2.this, N_Phone, Toast.LENGTH_LONG).show();


        recyclerView = findViewById(R.id.order_operation_recycle12);
        progressBar = findViewById(R.id.progressBar1);
        noAddressAddOption2 = findViewById(R.id.noAddressAddOption2);
        toolbar=findViewById(R.id.chooseAddress2);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        address_list = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        loadUrlData();


        noAddressAddOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseAddress_Account2.this, Add_Address.class));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadUrlData() {

          String Url_Data = "https://biz-point.herokuapp.com/address";

        Url_Data = Url_Data + "/" + N_Phone;

        progressBar.setVisibility(View.VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url_Data,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);
                String phone = null;

                if (response != null) {
                    try {




                        JSONObject jsonObject = new JSONObject(response);

                        Boolean error = jsonObject.getBoolean("error");

                        if (!error) {
                            String addrs = jsonObject.getString("address");
                            JSONArray array = new JSONArray(addrs);


                            for (int i = 0; i < array.length(); i++) {

                                JSONObject jo = array.getJSONObject(i);

                                String name = jo.getString("name");
                                 phone = jo.getString("phone");
                                String address = jo.getString("address");
                                String region = jo.getString("region");
                                String station = jo.getString("location");
                                Boolean isSelected = true;


                                AccountObject developers = new AccountObject(name, phone, address, region, station, isSelected);
                                address_list.add(developers);

                            }

                        } else {

                            noAddressAddOption2.setVisibility(View.VISIBLE);

                        }

                        adapter = new Address2_Adapter(address_list, getApplicationContext(), onAddressClickListener );
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(ChooseAddress_Account2.this, "ERROR " + e.toString(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ChooseAddress_Account2.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ChooseAddress_Account2.this);
        requestQueue.add(stringRequest);
    }


    //******** DELETE OPPERATION
    private void deleteTheAddree(String phone) {
        Toast.makeText(this, "Hallo " +  phone, Toast.LENGTH_LONG).show();


      /*  RequestQueue queue = Volley.newRequestQueue(this);  // this = context

        final String url = "https:/biz-point.herokuapp.com/addressdlt/" + phone;

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Log.d("Error.Response", response);
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);  */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.accout_menu_2, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        // looged In events
        if (id == R.id.addNew_address) {
            startActivity(new Intent(ChooseAddress_Account2.this, Add_Address.class));
        }


        if (id == R.id.logedIn_home){
            Intent i=new Intent(ChooseAddress_Account2.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            sharedPrefrence.getInstance(this.getApplicationContext()).logout();
            startActivity(new Intent(this, MainActivity.class));
        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(ChooseAddress_Account2.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }


}

