package com.da.tourandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.da.tourandroid.fragment.AccountFragment;
import com.da.tourandroid.fragment.FavouriteFragment;
import com.da.tourandroid.fragment.HomeFragment;
import com.da.tourandroid.fragment.InvoiceFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 1:
                return new FavouriteFragment();

            case 2:
                return new InvoiceFragment();

            case 3:
                return new AccountFragment();

            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
