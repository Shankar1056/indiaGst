package com.bigappcompany.gstindia.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ViewPagerAdapter;
import com.bigappcompany.gstindia.fragment.LawFragment;
import com.bigappcompany.gstindia.fragment.OtherFragment;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 * @signed_off_by Samuel Robert <sam@spotsoon.com>
 */

@SuppressWarnings("ConstantConditions")
public class LawActivity extends GSTActivity implements TabLayout.OnTabSelectedListener {
    public static final String EXTRA_LAW_TYPE = "extra_law_type";
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_law);
        super.onCreate(savedInstanceState);

        int position = getIntent().getIntExtra(EXTRA_LAW_TYPE, 0);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        setActivityTheme(position);

        ViewPager mLawVP = (ViewPager) findViewById(R.id.vp_law);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments(),
                getResources().getStringArray(R.array.titles_law));
        mLawVP.setAdapter(adapter);
        mLawVP.setCurrentItem(position);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_law);
        tabLayout.setupWithViewPager(mLawVP);
        tabLayout.addOnTabSelectedListener(this);
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        for (String law : getResources().getStringArray(R.array.laws)) {
            Bundle args = new Bundle();
            args.putString(EXTRA_TYPE, law);

            Fragment fragment = new LawFragment();
            fragment.setArguments(args);

            fragments.add(fragment);
        }

        fragments.add(new OtherFragment());

        return fragments;
    }

    @SuppressWarnings("deprecation")
    private void setActivityTheme(int position) {
        LAW law = LAW.values()[position];

        int colorPrimary = getResources().getColor(getResources().getIdentifier("color" + law.name(),
                "color", getPackageName()));
        int colorPrimaryDark = getResources().getColor(getResources().getIdentifier("colorDark" + law.name(),
                "color", getPackageName()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorPrimaryDark);
        }

        appBarLayout.setBackgroundColor(colorPrimary);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setActivityTheme(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
