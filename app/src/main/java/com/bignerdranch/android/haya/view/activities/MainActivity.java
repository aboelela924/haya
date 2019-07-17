package com.bignerdranch.android.haya.view.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.view.fragments.TutorialOneFragment;
import com.bignerdranch.android.haya.view.fragments.TutorialThreeFragment;
import com.bignerdranch.android.haya.view.fragments.TutorialTwoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String PAGE_NUMBER = "PAGE_NUBMER";


    public static Intent newIntent(Context context, int pageNumber){
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(PAGE_NUMBER, pageNumber);
        return i;
    }

    @BindView(R.id.view_pager) ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    List<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragments.add(new TutorialOneFragment());
        mFragments.add(new TutorialTwoFragment());
        mFragments.add(new TutorialThreeFragment());

        mPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        if (getIntent().getIntExtra(PAGE_NUMBER,-1) != -1){
            mPager.setCurrentItem(getIntent().getIntExtra(PAGE_NUMBER,0) );
        }
    }

    private class TutorialPagerAdapter extends FragmentStatePagerAdapter{

        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
