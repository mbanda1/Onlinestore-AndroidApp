package com.cyphertech.biashara.Address;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.person_infoAdapter;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Account.utils.volleyRequestQue;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

public class Add_Address extends AppCompatActivity {


    private static final String KEY_REGION = "region";
    private static final String KEY_LOCATION = "location";
    Spinner regionSpinner;
    Spinner locationSpinner;
    private ProgressBar  progressBar;
    AppCompatButton submitButton;

    private EditText _name, _phone, _address;
    String P_Number;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__address);


        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login_If.class));
        }

        person_infoAdapter me = sharedPrefrence.getInstance(this).getUser();
        P_Number = me.getPhone();


        regionSpinner = findViewById(R.id.RegionSpinner);
        locationSpinner = findViewById(R.id.LocationSpinner);
        progressBar = findViewById(R.id.progressBar_loadSpiner);
        submitButton = findViewById(R.id.appCompatButtonRegister);
        _name = findViewById(R.id.TextName);
        _phone = findViewById(R.id.TextPhone);
        _address = findViewById(R.id.TextAddress);

        toolbar = findViewById(R.id.addAddressToolbar);
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

        loadRegionLocation();



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        RegionLocation_Object region  = (RegionLocation_Object) regionSpinner.getSelectedItem();
                        String location = locationSpinner.getSelectedItem().toString();


              //  Toast.makeText(getApplicationContext(), "Selected State: " + region.getRegion()
                        //+ " City: " + location, Toast.LENGTH_SHORT).show();
                final String name = _name.getText().toString();
                final String phone = _phone.getText().toString();
                final String address = _address.getText().toString();

                String rgn = region.getRegion();

                sendNewAddress( name, phone, address, rgn, location);

            }
        });

    }




    //method to POST  a new shipping address for a particuler user

     private void sendNewAddress( final String name, final String phone, final String address,
                                 final String yrRegion, final String yrLocation) {
         String url = "https://biz-point.herokuapp.com/address1";

           url = url +"/"+P_Number;


         JSONObject jsonBodyObj = new JSONObject();

         try {
             //jsonBodyObj.put("userPhone", p_Number);
             jsonBodyObj.put("name", name);
             jsonBodyObj.put("phone", phone);
             jsonBodyObj.put("address", address);
             jsonBodyObj.put("region", yrRegion);
             jsonBodyObj.put("location", yrLocation);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         final String requestBody = jsonBodyObj.toString();


         if (TextUtils.isEmpty(name) || (TextUtils.isEmpty(phone) || (TextUtils.isEmpty(address)) || (TextUtils.isEmpty(yrRegion)
                     || (TextUtils.isEmpty(yrLocation))))) {
    Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show();
         } else {


             progressBar.setVisibility(View.VISIBLE);
             JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                     url, null, new Response.Listener <JSONObject>() {


                 @Override
                 public void onResponse(JSONObject response) {

                         progressBar.setVisibility(View.GONE);
                     if (response != null){
                         try {
                            // JSONObject object = new JSONObject(response);
                             String message = response.getString("message");
                             Snackbar snackbar = Snackbar
                                     .make( findViewById(R.id.addAddressMain), message, Snackbar.LENGTH_LONG);

                             snackbar.show();

                         }catch (Exception c) {

                             Toast.makeText(Add_Address.this, "Something went wrong \n Try again",
                                     Toast.LENGTH_LONG).show();
                         }
                     } else {
                         Toast.makeText(Add_Address.this, "No Response Sent \n Try again",
                                 Toast.LENGTH_LONG).show();
                     }

                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     progressBar.setVisibility(View.GONE);

                     VolleyLog.e("Error: ", error.getMessage());
                     Toast.makeText(Add_Address.this, error.toString(),
                             Toast.LENGTH_LONG).show();
                 }
             }) {
                 @Override
                 public Map <String, String> getHeaders() throws AuthFailureError {
                     HashMap <String, String> headers = new HashMap <String, String>();
                     headers.put("Content-Type", "application/json");
                     return headers;
                 }


                 @Override
                 public byte[] getBody() {
                     try {
                         return requestBody == null ? null : requestBody.getBytes("utf-8");
                     } catch (UnsupportedEncodingException uee) {
                         VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                 requestBody, "utf-8");
                         return null;
                     }
                 }

             };

             volleyRequestQue.getInstance(this).addToRequestQueue(jsonObjectRequest);

         }


       /*     // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(Add_Address.this);

             StringRequest stringRequest = new StringRequest(Request.Method.POST, Region_Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             Toast.makeText(Add_Address.this, "Address capture", Toast.LENGTH_LONG).show();
                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Address.this, "ERROR" + error, Toast.LENGTH_LONG).show();
                }
            }) {
                //adding parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", _name.getText().toString());
                    params.put("phone", _phone.getText().toString());
                    params.put("address", _address.getText().toString());
                    params.put("region", yrRegion);
                    params.put("location", yrLocation);

                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);  */
        }



    // method to populate our region and location spinner
    private void loadRegionLocation() {

        progressBar.setVisibility(View.VISIBLE);

        final List<RegionLocation_Object> regionL = new ArrayList<>();
        final List<String> regions = new ArrayList<>();
        String region_Url = "https://biz-point.herokuapp.com/station";
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                 (Request.Method.GET, region_Url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            //Parse the JSON response array by iterating over it
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                String region = response.getString(KEY_REGION);
                                JSONArray cities = response.getJSONArray(KEY_LOCATION);
                                List<String> locationList = new ArrayList<>();


                                for (int j = 0; j < cities.length(); j++) {
                                    locationList.add(cities.getString(j));
                                }
                                regionL.add(new RegionLocation_Object(region, locationList));
                                regions.add(region);

                            }

                            final RegionLocation_Adapter stateAdapter = new RegionLocation_Adapter(Add_Address.this,
                                    R.layout.my_region, R.id.spinner_region, regionL);
                            regionSpinner.setAdapter(stateAdapter);

                            regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    //Populate City list to the second spinner when
                                    // a state is chosen from the first spinner
                                    RegionLocation_Object locationDetails = stateAdapter.getItem(position);
                                    List<String> locationList = Objects.requireNonNull(locationDetails).getLocation();
                                    ArrayAdapter citiesAdapter = new ArrayAdapter<>(Add_Address.this,
                                            R.layout.my_location, R.id.spinner_location, locationList);
                                    locationSpinner.setAdapter(citiesAdapter);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);

                        //Display error message whenever an error occurs
                        Toast.makeText(Add_Address.this.getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        volleyRequestQue.getInstance(this).addToRequestQueue(jsArrayRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.address_add_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(Add_Address.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }


        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(Add_Address.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_logout) {
            sharedPrefrence.getInstance(this.getApplicationContext()).logout();
            startActivity(new Intent(this, MainActivity.class));
        }


        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }

}
