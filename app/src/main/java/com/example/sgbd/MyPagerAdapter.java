package com.example.sgbd;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 2;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ClientFragment();
            case 1:
                return new EmployeeFragment();
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
                return "Clients";
            case 1:
                return "Employees";
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }
}