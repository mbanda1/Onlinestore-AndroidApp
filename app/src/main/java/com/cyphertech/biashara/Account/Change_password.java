package com.cyphertech.biashara.Account;

import android.content.Intent;
import android.os.Bundle;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.R;

import androidx.appcompat.app.AppCompatActivity;

public class Change_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //check If user had alredy logged In
        if (sharedPrefrence.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, Login_If.class));
        }

    }
}
