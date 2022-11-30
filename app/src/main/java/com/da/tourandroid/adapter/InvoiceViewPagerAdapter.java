package com.da.tourandroid.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.da.tourandroid.fragment.InvoiceDraftFragment;
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
                return new InvoiceHistoryFragment();

            case 2:
                return new InvoiceDraftFragment();

            default:
                return new InvoiceOngoingFragment();
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
                title = "Ongoing";
                break;
            case 1:
                title = "History";
                break;
            case 2:
                title = "Draft";
                break;
        }

        return title;
    }
}
