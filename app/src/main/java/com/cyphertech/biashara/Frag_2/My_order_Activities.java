package com.cyphertech.biashara.Frag_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cyphertech.biashara.Account.LogingOperations.Login_If;
import com.cyphertech.biashara.Account.personal_Info;
import com.cyphertech.biashara.Account.utils.sharedPrefrence;
import com.cyphertech.biashara.MainActivity;
import com.cyphertech.biashara.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.cyphertech.biashara.Fragments.Account.EXTRA_PAGE;

public class My_order_Activities extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending__order);

        //back arrow
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (!sharedPrefrence.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login_If.class));
        }

        viewPager = findViewById(R.id.viewpager_pending_order);
        setupViewPager(viewPager);


        tabLayout = findViewById(R.id.tabs_order);
        tabLayout.setupWithViewPager(viewPager);
       // setupTabIcons();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.account_custom_tab, null);
        tabOne.setText("pending order");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_group_work_white_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.account_custom_tab, null);
        tabTwo.setText("in transit");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0,  R.mipmap.ic_group_work_white_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.account_custom_tab, null);
        tabThree.setText("Completed");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0,  R.mipmap.ic_group_work_white_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);


    }

    private void setupViewPager(ViewPager viewPager) {
        My_order_Activities.ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFrag(new Categories(getContext(), developersLists), "Home");
        adapter.addFrag(new Pending(), "pending order");
        adapter.addFrag(new Pending(), "In transit");
        adapter.addFrag(new PendingOrder2(), "Completed");
          viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Objects.requireNonNull(getIntent().getExtras()).getInt(EXTRA_PAGE,0));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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


            return mFragmentTitleList.get(position);
        }
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

        if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        //not logged In events
        if (id == R.id.not_home){
            Intent i=new Intent(this.getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        if (id == R.id.not_login) {
            startActivity(new Intent(this.getApplicationContext(), Login_If.class));
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
            startActivity(new Intent(this.getApplicationContext(), personal_Info.class));
        }

        if (id == R.id.logedIn_home_saved) {
            ////////
        }


        return super.onOptionsItemSelected(item);

    }

}
