package com.aryabhata.onstore.onstore;

/**
 * Created by Sainath on 2/27/2015.
 */

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.feedback.FeedBackActivity;
import com.aryabhata.onstore.history.HistoryActivity;
import com.aryabhata.onstore.searchproducts.SearchProductsActivity;
import com.aryabhata.onstore.settings.SettingsActivity;
import com.aryabhata.onstore.shopping.ShoppingActivity;
import com.aryabhata.onstore.utilities.SlidingTabLayout;

public class OnStoreActivity extends ActionBarActivity  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    FragmentManager manager=getFragmentManager();

    private ListView mDrawerList;
    SlidingTabLayout slidingTabLayout;
    ViewPager myViewPager;

    private String onstore[] = new String[]{"Offers", "Favorites", "New Arrivals", "Clearance"};
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        myViewPager = (ViewPager) findViewById(R.id.viewpager);

        mDrawerList.setBackgroundColor(getResources().getColor(R.color.magic_brown));
        toolbar.setBackgroundColor(getResources().getColor(R.color.magic_light_brown));
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.magic_lightest_brown));

        slidingTabLayout.setDistributeEvenly(true);
        myViewPager.setAdapter(new OnStorePagerAdapter(getSupportFragmentManager(), onstore));
        slidingTabLayout.setViewPager(myViewPager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.CYAN;
            }
        });

        /* Add the new features in fragments here */
        String[] values = new String[]{
                "Shopping", "OnStore", "Search Products", "History", "Report Issue", "Feedback",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.magic_blue));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.magic_light_blue));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.magic_lightest_blue));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        Intent Shopping = new Intent(OnStoreActivity.this, ShoppingActivity.class);
                        startActivity(Shopping);
                        finish();

                        break;
                    case 1:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.magic_brown));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.magic_light_brown));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.magic_lightest_brown));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 2:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.blue));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        Intent SearchProducts = new Intent(OnStoreActivity.this, SearchProductsActivity.class);
                        startActivity(SearchProducts);
                        finish();

                        break;
                    case 3:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        Intent History = new Intent(OnStoreActivity.this, HistoryActivity.class);
                        startActivity(History);
                        finish();


                        break;
                    case 4:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.magic_brown));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.magic_light_brown));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.magic_lightest_brown));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        Intent Settings = new Intent(OnStoreActivity.this,SettingsActivity.class);
                        startActivity(Settings);
                        finish();

                        break;
                    case 5:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.blue));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        Intent Feedback = new Intent(OnStoreActivity.this, FeedBackActivity.class);
                        startActivity(Feedback);
                        finish();

                        break;
                    }
                }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;

        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
