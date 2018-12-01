package com.cyphertech.biashara.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.checkOut.Address;
import com.cyphertech.biashara.product.Album;
import com.cyphertech.biashara.product.SingleBrand_Details;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.cyphertech.biashara.cart.NotificationCountSetClass.setNotifyCount;

public class Main_cart extends AppCompatActivity {

    public static final String P_IMAGE = "name";
    public static final String P_NAME = "name";
    public static final int P_PRICE = 0;
    public static final String P_DESC = "name";


    private Context mContext;
    public TextView Price;
    private TextView CheckOut;
    private RecyclerView recyclerView;

    Toolbar toolbar;

    final Boolean logedIn = true;

    private Number TotalPrice = 0;
    public static final String ARRAYLIST_MainCart = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);
        Fresco.initialize(this);


        toolbar = findViewById(R.id.carttoolbar);
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

         mContext = Main_cart.this;

        Price = findViewById(R.id.text_total_price);
        CheckOut = findViewById(R.id.text_check_out);
        recyclerView = findViewById(R.id.recyclerview_cart);


        Double TotalPrice = cartArray.grandTotal();
        String stringTotal = Double.toString(TotalPrice);
        Price.setText(stringTotal);

       // NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
       // Price.setText(format.format(stringTotal));

        //**** populated products //
         final ArrayList<Album> cartlistImageUri = cartArray.getCartListImageUri();

        setCartLayout();   // Buttons to how cart has some product or Empty

        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new Main_cart.MainCart_RecyclerViewAdapter_clas(recyclerView, cartlistImageUri));


        //CHECK OUT FROM HERE

      CheckOut.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


       if (sharedPrefrence.getInstance(Main_cart.this).isLoggedIn()) {

           Intent intent = new Intent(Main_cart.this, Address.class);
           Bundle args = new Bundle();
          // args.putParcelableArrayList(ARRAYLIST_MainCart, cartlistImageUri);
         //  intent.putExtra("BUNDLE21", args);
           startActivity(intent);

        } else {
           Intent intent = new Intent(Main_cart.this, Login_If.class);
           Bundle args = new Bundle();

           startActivity(intent);
       }
          }
      });
    }


    public void refresh() {
      Intent intent = getIntent();
      finish();
       startActivity(intent);

    }


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
            Intent i=new Intent(Main_cart.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_login) {
            startActivity(new Intent(Main_cart.this, Login_If.class));
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(Main_cart.this, personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(Main_cart.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            sharedPrefrence.getInstance(this.getApplicationContext()).logout();
            startActivity(new Intent(this, MainActivity.class));
         }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(Main_cart.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }




    //************************
   // ********* CLASS -(inner class)
   // ***********************
    public class MainCart_RecyclerViewAdapter_clas extends RecyclerView.Adapter<Main_cart.MainCart_RecyclerViewAdapter_clas.ViewHolder> {

        private int minteger = 0;

        private ArrayList<Album> mCartlistImageUri;
        private RecyclerView mRecyclerView;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final TextView p_price, p_name, p_desc;
            public final LinearLayout mLayoutItem, mLayoutRemove ;

            LinearLayout increase, decrease;
            TextView displayInteger ;



            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = view.findViewById(R.id.image_cartlist);
                mLayoutItem = view.findViewById(R.id.layout_item_description);
                mLayoutRemove = view.findViewById(R.id.layout__remove_action);

                p_name = view.findViewById(R.id.p_name);
                p_price = view.findViewById(R.id.p_price);
                p_desc = view.findViewById(R.id.p_desc);


                increase = view.findViewById(R.id.increase_p);
                decrease = view.findViewById(R.id.decrease_p);
                displayInteger = view.findViewById(R.id.integer_number);

            }
        }

        public MainCart_RecyclerViewAdapter_clas(RecyclerView recyclerView, ArrayList<Album> wishlistImageUri) {
            mRecyclerView = recyclerView;
            mCartlistImageUri = wishlistImageUri;
        }

        @Override
        public Main_cart.MainCart_RecyclerViewAdapter_clas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new Main_cart.MainCart_RecyclerViewAdapter_clas.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(Main_cart.MainCart_RecyclerViewAdapter_clas.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final Main_cart.MainCart_RecyclerViewAdapter_clas.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


            //  final Uri uri = Uri.parse(mCartlistImageUri.get(position));
           final Album developersList = mCartlistImageUri.get(position);

            holder.mImageView.setImageURI(developersList.getProductImage());
            holder.p_name.setText(developersList.getName());
            //price
            final Double prc = developersList.getPrice();
            String stringTotal = Double.toString(prc);
            holder.p_price.setText(stringTotal);

            holder.p_desc.setText(developersList.getProductDescription());
            holder.displayInteger.setText(Integer.toString(developersList.getQuantity()));



            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,mCartlistImageUri.get(position));
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);  */
                }
            });

         //   Integer one = developersList.getPrice(position);

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   cartArray imageUrlUtils = new cartArray();
                    imageUrlUtils.removeCartListImageUri(position);
                     notifyDataSetChanged();
                
                     //Decrease notification count
                    SingleBrand_Details.notificationCountCart--;
                    MainActivity.notificationCountCart--;
                    setNotifyCount(SingleBrand_Details.notificationCountCart);
                    setNotifyCount(MainActivity.notificationCountCart);

                    refresh();


                }


            });


            holder.increase.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    minteger = developersList.getQuantity();
                    if(minteger == 10){
                        return;
                    }
                    minteger = minteger + 1;
                    holder.displayInteger.setText(Integer.toString(minteger));

                    //calculate price
                  cartArray.addCartListImageUri2(position, developersList.getBrandId(), developersList.getBrandCategory(),
                          developersList.getName(), developersList.getProductImage(), developersList.getPrice(),
                          developersList.getProductDescription(), developersList.getProductSpecification(), minteger);
                      notifyDataSetChanged();

                    refresh();

                }
            });



            holder.decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minteger = developersList.getQuantity();
//
                    if(minteger == 1){
                        return;
                    }
                     minteger = minteger - 1;
                    holder.displayInteger.setText(Integer.toString(minteger));

                   cartArray.addCartListImageUri2(position, developersList.getBrandId(), developersList.getBrandCategory(),
                           developersList.getName(), developersList.getProductImage(), developersList.getPrice(),
                            developersList.getProductDescription(), developersList.getProductSpecification(), minteger);
                        notifyDataSetChanged();
                    refresh();

                 }

            });

        }




        @Override
        public int getItemCount() {
            return mCartlistImageUri.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

   /*     @Override
        public int getItemViewType(int position) {
         //   return mCartlistImageUri.get(position);
            return 0;
        }  */


    }



    private void setCartLayout() {
        LinearLayout layoutCartItems = findViewById(R.id.layout_cart_items);
        LinearLayout layoutCartPayments = findViewById(R.id.layout_cart_payment);
        LinearLayout layoutCartNoItems = findViewById(R.id.layout_cart_empty);

         if(SingleBrand_Details.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }



}
