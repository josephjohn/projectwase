package com.aryabhata.onstore.settings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SettingsPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =1;
    private String titles[] ;

    public SettingsPagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            /* Add more Cases and increment Page Count and also the array list in Home Activity*/
            case 0:
                return new Settings();
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