package com.da.tourandroid.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.da.tourandroid.fragment.InvoiceOnPlanFragment;
import com.da.tourandroid.fragment.InvoiceHistoryFragment;
import com.da.tourandroid.fragment.InvoiceOngoingFragment;

public class InvoiceViewPagerAdapter extends FragmentStatePagerAdapter {
    public InvoiceViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 1:
                return new InvoiceOngoingFragment();

            case 2:
                return new InvoiceHistoryFragment();

            default:
                return new InvoiceOnPlanFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "On Plan";
                break;
            case 1:
                title = "On going";
                break;
            case 2:
                title = "History";
                break;
        }

        return title;
    }
}
