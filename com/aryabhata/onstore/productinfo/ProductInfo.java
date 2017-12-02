package com.aryabhata.onstore.productinfo;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.utilities.CatsAdapter;
import com.aryabhata.onstore.utilities.ParallaxFragment;
import com.aryabhata.onstore.utilities.ParallaxPagerTransformer;

/**
 * Created by Sainath on 2/27/2015.
 */
public class ProductInfo extends FragmentActivity {

    ViewPager mPager;
    CatsAdapter mAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_tour);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setBackgroundColor(0xFF000000);

        ParallaxPagerTransformer pt = new ParallaxPagerTransformer((R.id.image));
        pt.setBorder(20);
        //pt.setSpeed(0.2f);
        mPager.setPageTransformer(false, pt);

        mAdapter = new CatsAdapter(this, getSupportFragmentManager());
        mAdapter.setPager(mPager); //only for this transformer

        Bundle b_One = new Bundle();
        b_One.putInt("image", R.drawable.ps_1);
        ParallaxFragment pf_One = new ParallaxFragment();
        pf_One.setArguments(b_One);

        Bundle b_Two = new Bundle();
        b_Two.putInt("image", R.drawable.ps_2);
        ParallaxFragment pf_Two = new ParallaxFragment();
        pf_Two.setArguments(b_Two);

        Bundle b_Three = new Bundle();
        b_Three.putInt("image", R.drawable.ps_3);
        ParallaxFragment pf_Three = new ParallaxFragment();
        pf_Three.setArguments(b_Three);

        Bundle b_Four = new Bundle();
        b_Four.putInt("image", R.drawable.ps_4);
        ParallaxFragment pf_Four = new ParallaxFragment();
        pf_Four.setArguments(b_Four);

        mAdapter.add(pf_One);
        mAdapter.add(pf_Two);
        mAdapter.add(pf_Three);
        mAdapter.add(pf_Four);
        mPager.setAdapter(mAdapter);
    }
}