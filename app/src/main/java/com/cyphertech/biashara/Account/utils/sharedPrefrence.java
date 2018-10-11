package com.cyphertech.biashara.Account.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.person_infoAdapter;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.cart.cartArray;
import com.cyphertech.biashara.product.SingleBrand_Details;

public class sharedPrefrence {


    private static final String SHARED_PREF_NAME = "biasharaPoint";

    public static final String KEY_USERPHONE = "";
    public static final String KEY_FIRSRNAME = "";
    public static final String KEY_LASTNAME = "";
    public static final String KEY_GENDER = "";


    private static sharedPrefrence mInstance;
    private static Context mCtx;

    private sharedPrefrence(Context context) {
        mCtx = context;
    }

    public static synchronized sharedPrefrence getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new sharedPrefrence(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(person_infoAdapter user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERPHONE, user.getPhone());
//        editor.putString(KEY_GENDER, user.getGender());
        //       editor.putString(KEY_FIRSRNAME, user.getFirstName());
//        editor.putString(KEY_LASTNAME, user.getLastName());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERPHONE, null) != null;
    }

    //this method will give the logged in user
    public person_infoAdapter getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new person_infoAdapter(
                //sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERPHONE, null)
//                sharedPreferences.getString(KEY_GENDER, null),
                //              sharedPreferences.getString(KEY_FIRSRNAME, null)
//                sharedPreferences.getString(KEY_LASTNAME, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        //clear cart and badge count
        cartArray.clearCart(); //clear Our Cart
        MainActivity.notificationCountCart = 0;
        SingleBrand_Details.notificationCountCart = 0;

        mCtx.startActivity(new Intent(mCtx, Login_If.class));
    }

}
