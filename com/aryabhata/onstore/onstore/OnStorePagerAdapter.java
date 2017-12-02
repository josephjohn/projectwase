package com.aryabhata.onstore.onstore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class OnStorePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =4;
    private String titles[] ;

    public OnStorePagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new Offers();

            case 1:
                return new Favorites();

            case 2:
                return new NewArrivals();

            case 3:
                return new ClearanceSale();

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

}