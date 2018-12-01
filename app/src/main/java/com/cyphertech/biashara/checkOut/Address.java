package com.cyphertech.biashara.checkOut;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Others.ViewPager_Scroll_LeftRight;
import com.cyphertech.biashara.R;
import com.cyphertech.biashara.product.Album;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Address extends AppCompatActivity implements Address_.OnButtonClickListener {

    @Override     //next tab
    public void onButtonClicked(View view) {

        int currPos = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currPos + 1);
    }

    @Override    ///    previous tab
    public void onBackPressed() {

        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1,true);
        }else{
            finish();
        }
    }


    //frgament stste
    Fragment shipment;
    ArrayList<Album> object;


    public ViewPager_Scroll_LeftRight viewPager;
    private TabLayout tabLayout;
    int layoutId;

//   private ViewPager_Scroll_LeftRight.SwipeDirection direction;
//    public void setAllowedSwipeDirection(ViewPager_Scroll_LeftRight.SwipeDirection direction) {
//        this.direction = direction;
//    }
        String mGameState;
    TextView selectShippingAddress;
    Address_ fragment1;

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        getSupportFragmentManager().putFragment(outState, "oneone", fragment1);
//        super.onSaveInstanceState(outState);
//    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null) {
//            fragment1 = (Address_) getSupportFragmentManager().getFragment(savedInstanceState, "oneone");
//        }
        setContentView(R.layout.activity_address);

        //back arrow
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //check if lgged In
        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(Address.this, Login_If.class));
        }

            fragment1 = new Address_();
         layoutId = R.layout.fragment_log_in__if_not;

//        Intent intent = this.getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE21");
//        if (args != null) {
//            object = args.getParcelableArrayList(ARRAYLIST_MainCart);
//        }

        if(savedInstanceState != null){
           object = savedInstanceState.getParcelableArrayList("products");
            //shipment = getSupportFragmentManager().getFragment(savedInstanceState, "products");

        }


        viewPager = findViewById(R.id.viewpager_address);
        setupViewPager(viewPager);
        //ALLOW SWIPING NLY FROM LEFT TO RIGHT
          viewPager.setAllowedSwipeDirection(ViewPager_Scroll_LeftRight.SwipeDirection.left);


        tabLayout = findViewById(R.id.tabs_address);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //DISABLING CLICK OF ViewPager Titles
       LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupTabIcons() {
        int[] tabIcons = {
              //  R.mipmap.ic_lock_black_24dp,
                R.mipmap.ic_assignment_black_24dp,
                R.mipmap.ic_payment_black_24dp
        };

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
      //  Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        Address.ViewPagerAdapter_class adapter = new Address.ViewPagerAdapter_class(getSupportFragmentManager());

        // adapter.addFrag(new Zero(), "Account"); // these is never accesed
        adapter.addFrag(new Address_(), "shipment");
//        adapter.addFrag(new Payment_primary(), "Payment");
        adapter.addFrag(new Payment(), "Payment");
        viewPager.setAdapter(adapter);
    }

     //****************************
    ///********** MENU **************

    /* @Override
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
            Intent i=new Intent(Address.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_login) {
            startActivity(new Intent(Address.this, Login_If.class));
        }

        if (id == R.id.not_account) {
            startActivity(new Intent(Address.this, personal_Info.class));
        }

        if (id == R.id.not_saved) {
            ////////
        }



        // looged In events
        if (id == R.id.logedIn_home){
            Intent i=new Intent(Address.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.logedIn_home_logout) {
            startActivity(new Intent(Address.this, Login_If.class));
        }

        if (id == R.id.logedIn_home_account) {
            startActivity(new Intent(Address.this, personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }
 */

    //NEW CLASS ***********************************888

    class ViewPagerAdapter_class extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
         private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter_class(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }



        @Override
        public CharSequence getPageTitle(int position) {

             return mFragmentTitleList.get(position);
        }

    }

}
