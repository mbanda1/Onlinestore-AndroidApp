package com.cyphertech.biashara.checkOut;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
  import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.LogingOperations.RegisterActivity;
import com.cyphertech.biashara.Account.person_infoAdapter;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Account.utils.volleyRequestQue;
import com.cyphertech.biashara.Address.Add_Address;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.cart.cartArray;
import com.cyphertech.biashara.product.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;

import static com.cyphertech.biashara.checkOut.Address1_Adapter.ADDRESS_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.NAME_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.ORDER_LIST;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.PHONE_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.REGION_;
import static com.cyphertech.biashara.checkOut.Address1_Adapter.STATION_;


public class Payment extends Fragment {

    ArrayList<Album> object;

    String URL = "https://biz-point.herokuapp.com/order";

    String  name_, phone_, address_, region_, station_;
    String customer_id = "";  //phone Number (USED TO REGISTER THAT ACCOUNT)
    Double finalPrice = 0.00;
    String order_id = "111";
    private ProgressWindow progressWindow ;
    JsonObjectRequest jsonObjectRequest;



    // inflated view declaration

        TextView cartName, cartQuantity, cartPrice, cartTotalPrice;

       TextView checkBox;
       EditText paymentCode;
       TextView cheOutButton;
       ProgressBar progressBar;
       LinearLayout LinearMpesa;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View primaryView = inflater.inflate(R.layout.fragment_log_in__if_not, container, false);
        View view = inflater.inflate(R.layout.fragment_payment_primary, container, false);
        setHasOptionsMenu(true);
        progressConfigurations();

        if (!sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
           // getActivity(). finish();
             startActivity(new Intent(getActivity(), Login_If.class));
        }

        person_infoAdapter me = sharedPrefrence.getInstance(getActivity()).getUser();

        customer_id = me.getPhone();


        //View view = getLayoutInflater().inflate(R.layout.fragment_payment_primary, null);

        ///&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        ///////////////////  INFLIATED VIEW VARIABLES //////////////////////////////////////
        //7777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777


        checkBox = view.findViewById(R.id.checkboxCheck);
        paymentCode = view.findViewById(R.id.codeEnter);
        cheOutButton = view.findViewById(R.id.cheOutButton);
        LinearMpesa = view.findViewById(R.id.LinearMpesa);


        cartName = view.findViewById(R.id.cartName);
        cartQuantity = view.findViewById(R.id.cartQuantity);
        cartPrice = view.findViewById(R.id.cartPrice);
        cartTotalPrice = view.findViewById(R.id.cartTotal);
        progressBar = view.findViewById(R.id.progressMpesa);


        //*************************************************************************************************
        //**********************************************************************************************

        LinearLayout linearLayout = new LinearLayout(getContext());
        TextView DisplayStringArray = new TextView(getActivity());
        linearLayout.addView(DisplayStringArray);
        linearLayout.addView(view);

        ///************************************
        Intent intent = getActivity().getIntent();
        Bundle args0 = intent.getBundleExtra("BUNDLE_N");
        Bundle args1 = intent.getBundleExtra("BUNDLE_P");
        Bundle args2 = intent.getBundleExtra("BUNDLE_A");
        Bundle args3 = intent.getBundleExtra("BUNDLE_R");
        Bundle args4 = intent.getBundleExtra("BUNDLE_L");
        Bundle args5 = intent.getBundleExtra("FINAL_PRICE");



        Bundle args = intent.getBundleExtra("PRODUCT_LIST");

        if (args != null) {
            //object =  args.getParcelableArrayList(ARRAYLIST_MainCart);
            object =  args.getParcelableArrayList(ORDER_LIST);
        //    object = cartArray.getCartListImageUri();


            name_ = (String) args0.getSerializable(NAME_);
            phone_ = (String) args1.getSerializable(PHONE_);
            address_ = (String) args2.getSerializable(ADDRESS_);
            region_ = (String) args3.getSerializable(REGION_);
            station_ = (String) args4.getSerializable(STATION_);
            finalPrice = args5.getDouble(STATION_);


            assert object != null;
            if (object.size() > 0) {
                try {
                     for (Album s : Objects.requireNonNull(object)) {

                   //    DisplayStringArray.append(s.getName() + s.getQuantity() + s.getPrice() + s.getFinalPrice());
                    //    DisplayStringArray.append("\n");
                         cartName.append(s.getName());
                         cartName.append("\n");

                         NumberFormat nm = NumberFormat.getNumberInstance();
                         cartQuantity.append(nm.format(s.getQuantity()));
                         cartQuantity.append("\n");

                        cartPrice.append(nm.format(s.getPrice()));
                        cartPrice.append("\n");

                       // finalPrice = s.getFinalPrice();

                         //primaryView = linearLayout;
                         view = linearLayout;
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error" + e, Toast.LENGTH_SHORT).show();
                }

                NumberFormat nm = NumberFormat.getNumberInstance();
                cartTotalPrice.append(nm.format(finalPrice));
             }
        }


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String one = paymentCode.getText().toString().trim();

                if (TextUtils.isEmpty(one)) {
                    Toast.makeText(getContext(), "Enter Mpesa Code", Toast.LENGTH_LONG).show();
                } else {
                    mpesaValidate(one);
                }
            }
        });

        cheOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     stageFinalOrderDetails(object, order_id, customer_id, finalPrice, name_, phone_, address_, region_, station_);

            }
        });



       // return primaryView;
        return view;
     }




//     /*************************//
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



    private void stageFinalOrderDetails(ArrayList <Album> order, final String order_id, final String customer_id, final Double finalPrice,
                                        final String A_name, final String A_phone, final String A_address, final String A_region, final String A_station) {


       // RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        final JSONArray jsonArray_List = new JSONArray();


        for (int i = 0; i < order.size(); i++) {

            jsonArray_List.put(order.get(i).getJSONObject());
        }

        final JSONObject jsonBodyObj = new JSONObject();

         try {
            jsonBodyObj.put("user_id", customer_id);
            jsonBodyObj.put("order_id", order_id);

            jsonBodyObj.put("order_List", jsonArray_List);   // full order list (onother JSONoBJECT)
             //finalPrice
             jsonBodyObj.put("finalPrice", finalPrice);
            //shipping address
             jsonBodyObj.put("Address_name", A_name);
             jsonBodyObj.put("Address_phone", A_phone);
             jsonBodyObj.put("Address_address", A_address);
            jsonBodyObj.put("Address_region", A_region);
            jsonBodyObj.put("Address_location", A_station);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBodyObj.toString();
      //  Log.i("orderList", requestBody);


        showProgress();

             jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URL,jsonBodyObj, new Response.Listener <JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {



                   // Log.i("Response", String.valueOf(response));
                   // Toast.makeText(getActivity(), "Order Placed Successfully !", Toast.LENGTH_LONG).show();
                    //  finish();
                    hideProgress();
                    if (response != null) {

                        try {
                             Boolean error = response.getBoolean("error");


                            if (!error){
                                String success = response.getString("message");

                                LinearMpesa.setVisibility(View.VISIBLE);
                                cheOutButton.setVisibility(View.INVISIBLE);

                                dialog2(success);
                                cartArray.clearCart(); //clear Our Cart

                            }else {
                                String fail = response.getString("message");
                                Toast.makeText(getActivity(),  fail, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //VolleyLog.e("Error: ", error.getMessage());
                      hideProgress();
                     // Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    String message = null;
                    if (volleyError instanceof NetworkError) {
                        message = "Cannot connect to Internt, Check your Network !";
                    } else if (volleyError instanceof ServerError) {
                        message = "Server not found, Try after some time!";
                    } else if (volleyError instanceof AuthFailureError) {
                         message = String.valueOf(R.string.networkFailure);

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
                    Networkdialog(message);                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> params = new HashMap <>();
                    params.put("Content-Type", "application/json");

                   return params;

                }


//                @Override
//                public byte[] getBody() {
//                    try {
//                        return requestBody == null ? null : requestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                                requestBody, "utf-8");
//                        return null;
//                    }
//                }

            };

        volleyRequestQue.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

    /******************* VALIDATE MPESA / AIRTEL MONEY TRASACTION ****************************/

    private void mpesaValidate(String code) {

        String url = "https://biz-point.herokuapp.com/mpesa" +"/"+ code;


        JSONObject jsonBodyObj = new JSONObject();

        try {
            //jsonBodyObj.put("pesaCode", code);
            jsonBodyObj.put("userPhone", customer_id);

         } catch (JSONException e) {
            e.printStackTrace();
        }

            progressBar.setVisibility(View.VISIBLE);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, jsonBodyObj, new Response.Listener <JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {

                    progressBar.setVisibility(View.GONE);
                    if (response != null){
                        try {
                             String message = response.getString("message");
                             Boolean error = response.getBoolean("error");

                             if (!error){
                                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                 LinearMpesa.setVisibility(View.GONE);
                                 cheOutButton.setVisibility(View.VISIBLE);
                             } else {
                                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                             }


                        }catch (Exception c) {

                            Toast.makeText(getContext(), "Something went wrong \n Try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No Response Sent \n Try again",
                                Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);

                    VolleyLog.e("Error: ", error.getMessage());
                    Toast.makeText(getContext(), error.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map <String, String> getHeaders() throws AuthFailureError {
                    HashMap <String, String> headers = new HashMap <String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }


            };

            volleyRequestQue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }

     //*****************************************************//

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!sharedPrefrence.getInstance(getActivity()).isLoggedIn()) {
              inflater.inflate(R.menu.every_where_not_logged_in, menu);
        } else {
             inflater.inflate(R.menu.every_where_logged_in, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //not logged In events
        if (id == R.id.not_home){
            Intent i=new Intent(getActivity(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_home) {
            startActivity(new Intent(getActivity(), Login_If.class));
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(getActivity(), personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(getActivity(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            sharedPrefrence.getInstance(getActivity()).logout();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }


        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(getActivity(), personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }

    private void dialog2(String message){
        new OoOAlertDialog.Builder(getActivity())
                .setTitle("BIASHARA Point")
                .setMessage(message + "\n Thank You for Shopping with Us.")
                .setImage(R.drawable.biashara_point)
                .setAnimation(Animation.SLIDE)
                .setPositiveButton("Shop again", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Intent i=new Intent(getActivity(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                })
//                .setNegativeButton("Log In", new OnClickListener() {
//                    @Override
//                    public void onClick() {
//                        finish();
//                    }
//                })
                .build();
    }

    private void Networkdialog(String message) {
        //Toast.makeText(getActivity(),  "Failed", Toast.LENGTH_LONG).show();
         new OoOAlertDialog.Builder(getActivity())

                 .setMessage(message)
                 .setPositiveButtonColor(R.color.album_title)
                .setImage(R.drawable.baseline_wifi_off_black_18dp)
                .setAnimation(Animation.POP)
                .setPositiveButton("Try Again", new OnClickListener() {
                    @Override
                    public void onClick() {

                        showProgress();
                        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f);
                         jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
                        volleyRequestQue.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
                    }
                })
//                .setNegativeButton("Log In", new OnClickListener() {
//                    @Override
//                    public void onClick() {
//                        finish();
//                    }
//                })
                .build();
    }


}
