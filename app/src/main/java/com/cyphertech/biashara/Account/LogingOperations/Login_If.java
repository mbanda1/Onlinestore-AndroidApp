package com.cyphertech.biashara.Account.LogingOperations;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.cyphertech.biashara.Account.person_infoAdapter;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Account.utils.volleyRequestQue;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Login_If extends AppCompatActivity {

    TextInputLayout textInputPhone, textInputPassword;
    EditText textPhone, textPassword;
    TextView textForgotPassword, textLogin, textRegister;
    Spinner spinnerLog;
    String kenya_code;

    private ProgressWindow progressWindow ;
    Toolbar toolbar;
    private List<person_infoAdapter> personAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__if);



        toolbar = findViewById(R.id.Logintoolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //check If user had alredy logged In
        if (sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
         }

        personAdapter = new ArrayList<>();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });

        textInputPhone = findViewById(R.id.textInputPhone);
        textInputPassword = findViewById(R.id.textInputPassword);

        textPhone = findViewById(R.id.textPhone);
        textPassword = findViewById(R.id.textPassword);
        textRegister = findViewById(R.id.textRegister);

        textForgotPassword = findViewById(R.id.textForgotPassword);
        textLogin = findViewById(R.id.textLoginButton);
        spinnerLog = findViewById(R.id.snipperLog);

        kenya_code = String.valueOf(spinnerLog.getSelectedItem());


        progressConfigurations();


        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity();
            }
        });


        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textPhone.getText().toString().trim().isEmpty() || !textPassword.getText().toString().trim().isEmpty()) {

                    if (!(textPhone.getText().toString().trim().length() < 10)
                            || !(textPhone.getText().toString().trim().length() > 13)) {

                        startLoginMethod(textPhone.getText().toString().trim(), textPassword.getText().toString().trim());
                    } else {
                        Toast.makeText(Login_If.this, "Check your Phone Number Lenght", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(Login_If.this, "Both Phone No. and Password Needed", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        progressWindow = ProgressWindow.getInstance(this);
        ProgressWindowConfiguration progressWindowConfiguration = new ProgressWindowConfiguration();
        progressWindowConfiguration.backgroundColor = Color.parseColor("#32000000") ;
        progressWindowConfiguration.progressColor = Color.WHITE ;
        progressWindow.setConfiguration(progressWindowConfiguration);
    }

    //**************************************************************
    //*********************************** aCTIVITY LOGIN now...........

    private void startLoginMethod(String phone, final String password) {

        if (phone.length() > 9) phone = phone.replaceFirst("^0+(?!$)", "");
        final String finalPhone = kenya_code + phone;


        // RequestQueue queue = Volley.newRequestQueue(this); // this = context
       final String url = "https://biz-point.herokuapp.com/login";

        showProgress();
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        if (response != null){
                         try {

                            JSONObject object = new JSONObject(response);
                            Boolean error = object.getBoolean("error");


                                 if (!error){
                                 JSONObject userJson = object.getJSONObject("message");
                                 person_infoAdapter User = new person_infoAdapter(
                                         userJson.getString("userPhone")
                                 );


//                                 Log.i("mbaz", userJson.getString("userPhone"));
//                                     Log.i("mbaz", userJson.getString("userGender"));
//                                     Log.i("mbaz", userJson.getString("userFirstName"));


                                 sharedPrefrence.getInstance(getApplicationContext()).userLogin(User);
                                 //hideProgress();
                                     Toast.makeText(Login_If.this, "Success Login !", Toast.LENGTH_LONG).show();
                                     finish();
                                     startActivity(new Intent(Login_If.this, MainActivity.class));


                             } else {
                                     hideProgress();
                                  Snackbar snackbar = Snackbar
                                         .make(findViewById(R.id.parentlayout1), object.getString("message"), Snackbar.LENGTH_LONG);snackbar.show();

                                         snackbar.show();

                             }

                           //  hideProgress();

                         } catch (Exception e){
                            hideProgress();
                             Toast.makeText(Login_If.this, "Error "+e.toString(), Toast.LENGTH_LONG).show();

                          }
                         }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error","Error.Response");
                        hideProgress();
                        Toast.makeText(Login_If.this, "Something went wrong. Try Again. !", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map <String, String> params = new HashMap<String, String>();
                params.put("userPhone", finalPhone.trim());
                params.put("userPassword", password.trim());
                return params;
            }
//
//            @Override
//            public Map <String, String> getHeaders() {
//                Map <String, String> params = new HashMap <>();
//                //  params.put("Content-Type","application/x-www-form-urlencoded");
//                params.put("Content-Type", "application/json");
//                return params;
//            }
        };

      // add it to the RequestQueue
        //queue.add(getRequest);    //<  LOCAL RQ >
        volleyRequestQue.getInstance(this).addToRequestQueue(getRequest);
    }



   /* private void SetValues(String userPhone, String userGender, String userFirstName, String userLastName) {
        person_infoAdapter person = new person_infoAdapter(userPhone, userGender, userFirstName, userLastName);
        personAdapter.add(person);
    }*/


    private void SignUpActivity() {
         Intent intent = new Intent(Login_If.this, RegisterActivity.class);
         startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            getMenuInflater().inflate(R.menu.every_where_not_logged_in, menu);
        } else {
            getMenuInflater().inflate(R.menu.every_where_logged_in, menu);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        //not logged In events
        if (id == R.id.not_home){
            Intent i=new Intent(Login_If.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_login) {
           // startActivity(new Intent(Login_If.this, Login_If.class));
            Intent intent = getIntent();
            startActivity(intent);
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(Login_If.this, personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(Login_If.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            startActivity(new Intent(Login_If.this, Login_If.class));
        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(Login_If.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return
                super.onOptionsItemSelected(item);

    }

}
