package com.cyphertech.biashara.checkOut;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyphertech.biashara.Account.AccountObject;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.person_infoAdapter;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Address.Add_Address;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseAddress_checkOut1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AccountObject> address_list;
    private ProgressBar progressBar;
    TextView noAddressAddOption;
 //   private Address1_Adapter.OnItemClickListener onItemClickListener;
    Toolbar toolbar;

    String N_phonee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_an__address);

        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login_If.class));
        }

        person_infoAdapter me = sharedPrefrence.getInstance(this).getUser();
        N_phonee = me.getPhone();

        recyclerView = findViewById(R.id.order_operation_recycle);
        progressBar = findViewById(R.id.progressBar);
        noAddressAddOption = findViewById(R.id.noAddressAddOption);
        toolbar =findViewById(R.id.chooseAddress);

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        noAddressAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseAddress_checkOut1.this, Add_Address.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadUrlData() {
        String Url_Data = "https://biz-point.herokuapp.com/address";
        Url_Data = Url_Data + "/" + N_phonee;

        progressBar.setVisibility(View.VISIBLE);


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Url_Data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);

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
                                String phone = jo.getString("phone");
                                String address = jo.getString("address");
                                String region = jo.getString("region");
                                String station = jo.getString("location");
                                Boolean isSelected = true;


                                AccountObject developers = new AccountObject(name, phone, address, region, station, isSelected);
                                address_list.add(developers);

                            }

                        } else {

                            noAddressAddOption.setVisibility(View.VISIBLE);

                        }

                        adapter = new Address1_Adapter(address_list, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ChooseAddress_checkOut1.this, "ERRPOR "+e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChooseAddress_checkOut1.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.accout_menu_2, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //not logged In events



        // looged In events
        if (id == R.id.addNew_address) {
            startActivity(new Intent(ChooseAddress_checkOut1.this, Add_Address.class));
        }


        if (id == R.id.logedIn_home){
            Intent i=new Intent(ChooseAddress_checkOut1.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            sharedPrefrence.getInstance(this.getApplicationContext()).logout();
            startActivity(new Intent(this, MainActivity.class));
            return true;        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(ChooseAddress_checkOut1.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }


}
