package com.cyphertech.biashara;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.Fragments.Account;
import com.cyphertech.biashara.Fragments.Categories;
import com.cyphertech.biashara.Fragments.TopDeals;
import com.cyphertech.biashara.cart.Main_cart;
import com.cyphertech.biashara.product.SearchProducts;

import java.util.ArrayList;
import java.util.List;

import static com.cyphertech.biashara.cart.NotificationCountSetClass.setAddToCart;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int notificationCountCart = 0;


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.mipmap.ic_home_white_24dp,
                R.mipmap.ic_group_work_white_24dp,
                R.mipmap.ic_perm_identity_white_24dp
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFrag(new Categories(getContext(), developersLists), "Home");
        adapter.addFrag(new Categories(), "Categories");
    adapter.addFrag(new TopDeals(), "Top deals");
        //adapter.addFrag(new TopDeal_One(), "Top deals");
        adapter.addFrag(new Account(), "Account");
        viewPager.setAdapter(adapter);
    }
  //  private ViewPagerAdapter adapter;


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

            // return null to display only the icon
            return null;
        }
    }

    //Close drawer on BackPress
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    //OnResume WE NEED TO UPDATE OUR BADGE COUNT
//    @Override
//    protected void onResume() {
//        super.onResume();
//        refreshActivity();
//    }
//
//    public void refreshActivity() {
//        Intent intent = getIntent();
//        //finish();
//        startActivity(intent);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            setAddToCart(MainActivity.this, itemm, notificationCountCart);
           // invalidateOptionsMenu();

        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //****************//

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement


        if (id == R.id.home_search) {
            startActivity(new Intent(MainActivity.this, SearchProducts.class));
            return true;
        }


        if (id == R.id.cart_) {
            startActivity(new Intent(MainActivity.this, Main_cart.class));
            return true;
        }


        if (id == R.id.home_nt_login){
            startActivity(new Intent(MainActivity.this, Login_If.class));
            return true;
        }

        if (id == R.id.home_nt_my_orders){
            startActivity(new Intent(MainActivity.this, Main_cart.class));
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

    @Override
    protected void onStart() {
        super.onStart();

        //     Intent intent = new Intent(this, Main.class);
        //  startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
