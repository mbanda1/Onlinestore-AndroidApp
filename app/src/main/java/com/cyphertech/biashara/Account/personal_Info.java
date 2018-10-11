package com.cyphertech.biashara.Account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Account.utils.volleyRequestQue;
import com.cyphertech.biashara.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class personal_Info extends AppCompatActivity {  //implements View.OnClickListener {

    TextInputEditText name1;
    TextInputEditText name2;
    TextView phone_;
    Button update;
    RadioButton g_male, g_female;
    RadioGroup radioGender;

    person_infoAdapter person;
     String Number = "";
    String gender = null;

    Toolbar toolbar;

    private ProgressWindow progressWindow ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__info);

        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login_If.class));
        }

        //*******************************************//
        person_infoAdapter me = sharedPrefrence.getInstance(this).getUser();





        name1 = findViewById(R.id.my_firstName);
        name2 = findViewById(R.id.my_secondName);
        phone_ = findViewById(R.id.my_phone);
        update = findViewById(R.id.id_submit);
        radioGender = findViewById(R.id.radioGender);
         g_male = findViewById(R.id.radioMale);
         g_female = findViewById(R.id.radioFemale);
         toolbar=findViewById(R.id.personalInfoToolbar);

        progressConfigurations();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int childCount = group.getChildCount();
                 for (int x = 0; x < childCount; x++) {
                     RadioButton btn = (RadioButton) group.getChildAt(x);

                     if(btn.getId()==R.id.radioMale){
                         btn.setText("male");
                     }else if (btn.getId()==R.id.radioFemale){
                         btn.setText("female");
                     }
                     if (btn.getId() == checkedId) {

                         gender=btn.getText().toString();// here gender will contain M or F.

                     }
                 }

                }
        });

        Number = me.getPhone();
         queryOtherData(Number);

         update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 UpdateUserData();
             }
         });

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


    private void queryOtherData(final String phone) {
         String url = "https://biz-point.herokuapp.com/users/" + phone;

            showProgress();
        StringRequest stringRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener <String>() {
                    @Override
                    public void onResponse(String response) {
                         hideProgress();

                        if (response != null) {
                            try {

                                JSONObject object = new JSONObject(response);
                                String message = object.getString("message");


                                if (message.trim().equals("true")) {
                                   JSONObject userJson = object.getJSONObject("user");

                                    phone_.setText(Number, TextView.BufferType.EDITABLE);
                                    name1.setText(userJson.getString("userFirstName"), TextView.BufferType.EDITABLE);
                                    name2.setText(userJson.getString("userLastName"), TextView.BufferType.EDITABLE);

                                        String gd = userJson.getString("userGender");
                                        if (gd.equals("male")){

                                            g_male.setChecked(true);
                                            g_female.setChecked(false);
                                            gender = "male";
                                        }else if (gd.equals("female")) {

                                            g_male.setChecked(false);
                                            g_female.setChecked(true);
                                            gender = "female";
                                        }




                                } else if (message.trim().equals("false")){
                                    Toast.makeText(personal_Info.this, "Something went wrong Try again",
                                            Toast.LENGTH_LONG).show();
                                 }




                            } catch (JSONException e) {
                                // e.printStackTrace();
                                Toast.makeText(personal_Info.this, e.toString(), Toast.LENGTH_LONG).show();

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("HttpClient", "error: " + error.toString());
                        hideProgress();
                         Toast.makeText(personal_Info.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map <String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("user", phone);
                return params;
            }
        };

         volleyRequestQue.getInstance(this).addToRequestQueue(stringRequest);

    }


    ///*******************************************************************************//
    private void UpdateUserData(){
        final String F_name = name1.getText().toString();
        final String S_name = name2.getText().toString();
        final String phone1 = Number;

        String url = "https://biz-point.herokuapp.com/user/" + phone1;

        showProgress();
        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        hideProgress();
                        ///////////
                        if (response != null){
                             try {
                             JSONObject object = new JSONObject(response);
                                String message = object.getString("message");

                                  Snackbar snackbar = Snackbar
                                         .make(findViewById(R.id.idSnack), message, Snackbar.LENGTH_LONG);

                                 snackbar.show();

                            } catch (JSONException e) {


                                 Snackbar snackbar = Snackbar
                                         .make(findViewById(R.id.idSnack), "Something went wrong !", Snackbar.LENGTH_LONG);

                                 snackbar.show();

                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hideProgress();
                        Toast.makeText(personal_Info.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                params.put("userFirstName", F_name);
                params.put("userLastName", S_name);
                params.put("userGender", gender);

                return params;
            }

        };

        volleyRequestQue.getInstance(this).addToRequestQueue(putRequest);
    }
}
