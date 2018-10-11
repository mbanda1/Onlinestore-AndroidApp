package com.cyphertech.biashara.Account.LogingOperations;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    EditText phpneNo, verficationCode;
    TextView clickToVerify;
    TextView timertext;
    ImageView verifiedImg;

    Timer timer;
    String mVerificationId;
    Boolean mVerified = false;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    EditText passWd;
    TextView REGISTER;
    Spinner spinner;
    ProgressBar progressBar;


    String PHONE = null;
    String PASSWORD = null;
    String kenya_code = null;
    Boolean Pass = null;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //back arrow
//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //check If user had alredy logged In
        if (sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
        }


        phpneNo =  findViewById(R.id.numbered);
        verficationCode =  findViewById(R.id.verificationed);
        clickToVerify =  findViewById(R.id.sendverifybt);
        timertext =  findViewById(R.id.timertv);
        verifiedImg =  findViewById(R.id.verifiedsign);
        mAuth = FirebaseAuth.getInstance();

        spinner = findViewById(R.id.snipper);
        progressBar = findViewById(R.id.progressBarRegister);

        passWd = findViewById(R.id.idPassword);
        REGISTER = findViewById(R.id.idRegister);
        toolbar = findViewById(R.id.regtoolbar);
        setSupportActionBar(toolbar);

        kenya_code = String.valueOf(spinner.getSelectedItem());


//        // add back arrow to toolbar
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.

                Log.d("TAG", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.parentlayout), "Verification Failed !! Invalied verification Code", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.parentlayout), "Verification Failed !! Too many request. Try after some time. ", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        clickToVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickToVerify.getTag().equals(getResources().getString(R.string.tag_send))) {
                    if (!phpneNo.getText().toString().trim().isEmpty() && phpneNo.getText().toString().trim().length() >= 9) {

//
//                        startPhoneNumberVerification(phpneNo.getText().toString().trim());
//                        mVerified = false;
//                        starttimer();
//                        verficationCode.setVisibility(View.VISIBLE);
//                        clickToVerify.setText(R.string.tag_verify);
//                        clickToVerify.setTag(getResources().getString(R.string.tag_verify));

                        try {
                           checkPhoneInDb(phpneNo.getText().toString().trim()); //check in our DB if user alredy registred
//                           if (Pass) {
//                               startPhoneNumberVerification(phpneNo.getText().toString().trim());
//                               mVerified = false;
//                               starttimer();
//                               verficationCode.setVisibility(View.VISIBLE);
//                               clickToVerify.setText(R.string.tag_verify);
//                               clickToVerify.setTag(getResources().getString(R.string.tag_verify));
//                           } else {
//                               callLoginDialogue();
//                           }

                        } catch (Exception e) {
                            Log.e("ERROR : ", e.toString());
                        }
                    }
                    else {
                        phpneNo.setError("Please enter valid mobile number");
                    }
                }

                if (clickToVerify.getTag().equals(getResources().getString(R.string.tag_verify))) {
                    if (!verficationCode.getText().toString().trim().isEmpty() && !mVerified) {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.parentlayout), "Please wait...", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verficationCode.getText().toString().trim());
                        signInWithPhoneAuthCredential(credential);
                    }
                    if (mVerified) {

                        clickToVerify.setVisibility(View.GONE);
                        PHONE = kenya_code + phpneNo.getText().toString().trim();
                        passWd.setVisibility(View.VISIBLE);
                        REGISTER.setVisibility(View.VISIBLE);

                        //startActivity(new Intent(Registration.this, RegisterActivity.class));

                        REGISTER.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (passWd.getText().toString().trim().isEmpty()) {
                                    passWd.setError("Password Needed");
                                } else {
                                    PASSWORD = passWd.getText().toString().trim();
                                    registerToDatabase(PHONE, PASSWORD);
                                }
                            }
                        });
                    }

                }


            }
        });

        timertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phpneNo.getText().toString().trim().isEmpty() && phpneNo.getText().toString().trim().length() >= 9) {
                    resendVerificationCode(phpneNo.getText().toString().trim(), mResendToken);
                    mVerified = false;
                    starttimer();
                    verficationCode.setVisibility(View.VISIBLE);
                    clickToVerify.setText(R.string.tag_verify);
                    clickToVerify.setTag(getResources().getString(R.string.tag_verify));
                    Snackbar snackbar = Snackbar
                            .make( findViewById(R.id.parentlayout), "Resending verification code...", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
        });

    }   ///*********** onCreate END ***********///

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            mVerified = true;
                            timer.cancel();
                            verifiedImg.setVisibility(View.VISIBLE);
                            timertext.setVisibility(View.GONE);
                            phpneNo.setEnabled(false);
                            verficationCode.setVisibility(View.GONE);
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.parentlayout), "Successfully Verified", Snackbar.LENGTH_LONG);

                            snackbar.show();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Snackbar snackbar = Snackbar
                                        .make(findViewById(R.id.parentlayout), "Invalid OTP ! Please enter correct OTP", Snackbar.LENGTH_LONG);

                                snackbar.show();
                            }
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        phoneNumber = kenya_code + phoneNumber;

        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                20,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }

    public void starttimer() {
        timertext.setVisibility(View.VISIBLE);
        timer = new Timer();
        timer.schedule(new TimerTask() {

            int second = 20;

            @Override
            public void run() {
                if (second <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timertext.setText("RESEND CODE");
                            timer.cancel();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timertext.setText("00:" + second--);
                        }
                    });
                }

            }
        }, 0, 1000);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        phoneNumber = kenya_code + phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                20,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    /************************************  1  ****************************************************************/

    private void checkPhoneInDb(String phone) {
         phone = kenya_code + phone;
        String url = "https://biz-point.herokuapp.com/users/" + phone;
        RequestQueue queue = Volley.newRequestQueue(this);   // this = context
        progressBar.setVisibility(View.VISIBLE);

        StringRequest sr = new StringRequest (Request.Method.GET, url,
                new Response.Listener <String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        if (response != null) {
                            try {


                                JSONObject object = new JSONObject(response);
                                String message = object.getString("message");
                                if (message.trim().equals("true")) {
                                     //  Toast.makeText(Registration.this, message, Toast.LENGTH_LONG).show();
                                    //callLoginDialogue();
                                    dialog2();
                                    Pass = false;
                                } else if (message.trim().equals("false")){
                                    // Toast.makeText(Registration.this, message, Toast.LENGTH_LONG).show();
                                    nowProceedWithRegistration();
                                    //Pass = true;
                                }




                            } catch (JSONException e) {
                                // e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("HttpClient", "error: " + error.toString());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map <String, String> params = new HashMap<String, String>();
                params.put("user", PHONE);
                return params;
            }

            @Override
            public Map <String, String> getHeaders() {
                Map <String, String> params = new HashMap <>();
                //  params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        queue.add(sr);

     }

    ///***********************  2   ***********
    private void nowProceedWithRegistration() {
        startPhoneNumberVerification(phpneNo.getText().toString().trim());
        mVerified = false;
        starttimer();
        verficationCode.setVisibility(View.VISIBLE);
        //  fabbutton.setImageResource(R.drawable.baseline_forward_black_18dp);
        clickToVerify.setText(R.string.tag_verify);
        clickToVerify.setTag(getResources().getString(R.string.tag_verify));   //tage called Below for verificaion
    }


    //****************   3 *********************
    private void callLoginDialogue() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);

//Create a custom layout for the dialog box
        LayoutInflater inflater = (LayoutInflater)RegisterActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.exit_registration_dialogue, null, false);

        TextView title = layout.findViewById(R.id.feedBack);
        Button logIn = layout.findViewById(R.id.loginPage);
        Button tryReg = layout.findViewById(R.id.tryRegAgain);

        title.setText(R.string.registrationDialogue);
        dialog.setView(layout);

        final AlertDialog alertDialog = dialog.create();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tryReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    private void dialog2(){
        new OoOAlertDialog.Builder(RegisterActivity.this)
                .setTitle("BIASHARA Point")
                .setMessage("Looks like you alredy got account !")
                .setImage(R.drawable.biashara_point)
                .setAnimation(Animation.POP)
                .setPositiveButton("Try Again", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Intent intent = getIntent();
                         startActivity(intent);
                    }
                })
                .setNegativeButton("Log In Now", new OnClickListener() {
                    @Override
                    public void onClick() {
                        finish();
                     }
                })
                .build();
    }

    //************************  4 ******************
    private void registerToDatabase(final String phone, final String password) {
        // Toast.makeText(Registration.this, phone + "\n" + password, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);   // this = context

        String url = "https://biz-point.herokuapp.com/users";

        progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);


                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String message = object.getString("message");
                                Snackbar snackbar = Snackbar
                                        .make(findViewById(R.id.parentlayout), message, Snackbar.LENGTH_LONG);

                                snackbar.show();


                            } catch (Exception e) {
                                Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                        passWd.setText("");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }else {
                            message = "Check your data and try again";
                        }

                        progressBar.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.parentlayout), message, Snackbar.LENGTH_LONG);

                        snackbar.show();
                        passWd.setText("");
                    }
                })

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userPhone", phone);
                params.put("userPassword", password);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void backToLogin1(View view) {
        finish();
    }



    //**********************************************************************//
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


        //not logged In events
        if (id == R.id.not_home){
            Intent i=new Intent(this.getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_login) {
            finish();
            //startActivity(new Intent(this.getApplicationContext(), Login_If.class));
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(this.getApplicationContext(), personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(this.getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            sharedPrefrence.getInstance(this.getApplicationContext()).logout();
            startActivity(new Intent(this, MainActivity.class));
        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(RegisterActivity.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }

}


