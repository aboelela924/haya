package com.bignerdranch.android.haya.view.adapters;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ConversationsPagerAdapter extends FragmentStatePagerAdapter {
    List<String> tabTitles;
    List<Bitmap> tabIcons;
    List<Fragment> tabFragments;
    public ConversationsPagerAdapter(FragmentManager fm,List<String> tabTitles, List<Fragment> tabFragments) {
        super(fm);
        this.tabTitles = tabTitles;
        this.tabFragments = tabFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
