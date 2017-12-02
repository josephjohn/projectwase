package com.aryabhata.onstore.shopping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.aryabhata.onstore.shopping.Welcome;
import com.aryabhata.onstore.shopping.Suggestions;
import com.aryabhata.onstore.shopping.ToBuy;
import com.aryabhata.onstore.shopping.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ShoppingPagerAdapter extends FragmentPagerAdapter {
    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;
   // private Context mContext;
    final int PAGE_COUNT =4;
    private String titles[] ;

    public ShoppingPagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer,String>();
        titles=titles2;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new Welcome();

            case 1:
                return new ShoppingCart();

            case 2:
                return new ToBuy();

            case 3:
                return new Suggestions();

        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }



}