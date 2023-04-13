package com.example.sgbd;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ReservationPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 2;

    public ReservationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReservationFragment();
            case 1:
                return new LocationFragment();
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Reservation";
            case 1:
                return "Location";
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }
}