package com.cyphertech.biashara.product;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.cart.Main_cart;
import com.cyphertech.biashara.cart.cartArray;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import static com.cyphertech.biashara.cart.NotificationCountSetClass.setAddToCart;
import static com.cyphertech.biashara.cart.NotificationCountSetClass.setNotifyCount;

public class SingleBrand_Details extends AppCompatActivity {


    public static int notificationCountCart = 0;


    private SimpleDraweeView profileImageView;
    private TextView name;
    private TextView price;
    private TextView description;
    private TextView specifications;
    private TextView addToCart, checkOut;

    private Resources mResourse;

    // private ArrayList<Album> list;

   // @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        mResourse = this.getResources();
        setContentView(R.layout.activity_single_brand__detail);

        //back arrow
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent = getIntent();

        final String p_id = intent.getStringExtra(Brands_CustomAdapter.BRAND_ID);
        final String p_category = intent.getStringExtra(Brands_CustomAdapter.BRAND_CAT);
        final String p_name = intent.getStringExtra(Brands_CustomAdapter.KEY_NAME);
        final String p_picture = intent.getStringExtra(Brands_CustomAdapter.KEY_IMAGE);

        final Double p_price = intent.getDoubleExtra(Brands_CustomAdapter.KEY_PRICE, 0.0);

        final String p_spec = intent.getStringExtra(Brands_CustomAdapter.KEY_SPECIFICATION);
        final String p_desc = intent.getStringExtra(Brands_CustomAdapter.KEY_DESCRIPTION);

        name = findViewById(R.id.brandName);
        profileImageView = findViewById(R.id.image_one);
        price = findViewById(R.id.brandPrice);
        description = findViewById(R.id.brandDesc);
        specifications = findViewById(R.id.spec_one);


        addToCart = findViewById(R.id.text_action_add2cat);
        checkOut = findViewById(R.id.text_action_go2cat);

        name.setText(p_name);
        Picasso.with(this).load(p_picture).into(profileImageView);
        price.setText(p_price.toString());
        description.setText(p_desc);
        specifications.setText(p_spec);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //    Double prcc = Double.parseDouble(p_price);
                if  (cartArray.addCartListImageUri(p_id, p_category, p_name, p_picture, p_price, p_desc, p_desc, 1, p_price)){

                  SingleBrand_Details.notificationCountCart++;
                  setNotifyCount(SingleBrand_Details.notificationCountCart);

                  //icon in main Layout
                    MainActivity.notificationCountCart++;
                    setNotifyCount(MainActivity.notificationCountCart);

                //  cartArray.getCartListImageUri1(p_price);
                    refreshActivity();

                    //Log.i("mrrrrd", p_id + "\n" + p_category);

              } else {
                  Toast.makeText(getBaseContext(),"Item added already In cart.",Toast.LENGTH_SHORT).show();

              }
            }



        });

        checkOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

           /*     cartArray.addCartListImageUri(p_name, p_picture, p_price, p_desc, p_desc);
                SingleBrand_Details.notificationCountCart++;
                setNotifyCount(SingleBrand_Details.notificationCountCart);
                startActivity(new Intent(SingleBrand_Details.this, Main_cart.class));  */

                if  (cartArray.addCartListImageUri(p_id, p_category, p_name, p_picture,  p_price, p_desc, p_desc, 1, p_price)){

                    SingleBrand_Details.notificationCountCart++;
                    setNotifyCount(SingleBrand_Details.notificationCountCart);
                    MainActivity.notificationCountCart++;
                    setNotifyCount(MainActivity.notificationCountCart);
                    refreshActivity();

                }
                startActivity(new Intent(SingleBrand_Details.this, Main_cart.class));

            }

        });



     }

    @Override
    protected void onResume() {
        super.onResume();
      // refreshActivity();
    }

    public void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.cart_only, menu);
        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            getMenuInflater().inflate(R.menu.not_loged_in_home, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.main, menu);

        }

        return true;
     }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
             MenuItem itemm = menu.findItem(R.id.cart_);
            setAddToCart(SingleBrand_Details.this, itemm, notificationCountCart);
            invalidateOptionsMenu();


            invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //****************//

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        if (id == R.id.home_search){
            startActivity(new Intent(SingleBrand_Details.this, SearchProducts.class));
            return true;
        }

        if (id == R.id.cart_) {
            startActivity(new Intent(SingleBrand_Details.this, Main_cart.class));
            return true;
        }


        if (id == R.id.home_nt_login){
            startActivity(new Intent(SingleBrand_Details.this, Login_If.class));
            return true;
        }

        if (id == R.id.home_nt_my_orders){
            startActivity(new Intent(SingleBrand_Details.this, Main_cart.class));
            return true;
        }

        if (id == R.id.home_loggedIn_logout){
            sharedPrefrence.getInstance(this.getApplicationContext()).logout();
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        if (id == R.id.home_loggedIn_orders){
            /// order processing
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
