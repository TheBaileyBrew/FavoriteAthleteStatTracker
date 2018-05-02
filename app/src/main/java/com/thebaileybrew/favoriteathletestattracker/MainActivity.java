package com.thebaileybrew.favoriteathletestattracker;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    BottomNavigation mBottomNavigation;
    FloatingActionButton fab;
    ViewPager pager;

    //Integer Values For All Scorable Items
    int passingYards;
    int rushingYards;
    int recvingYards;
    int touchdowns;
    int passAttempts;
    int passComplete;
    int rushAttempts;
    int catches;
    int interceptions;

    //TextViews For Above Integers
    TextView passingYardsDisplay;
    TextView rushingYardsDisplay;
    TextView recvingYardsDisplay;
    TextView touchdownsDisplay;
    TextView passAttemptsDisplay;
    TextView passCompleteDisplay;
    TextView rushAttemptDisplay;
    TextView catchesDisplay;
    TextView interceptionDisplay;




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigation = findViewById(R.id.bottomNavigation);
        fab = findViewById(R.id.floating_action_button);
        initializeFragments();
        validateTextViews();
        mBottomNavigation.setDefaultSelectedIndex(0);
        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                ViewPager pager = findViewById(R.id.viewPager);
                switch (i1) {
                    case 0:
                        fab.setVisibility(View.GONE);
                        pager.setCurrentItem(0,true);
                        break;
                    case 1:
                        fab.setVisibility(View.GONE);
                        pager.setCurrentItem(1,true);
                        break;
                    case 2:
                        fab.setVisibility(View.VISIBLE);
                        pager.setCurrentItem(2,true);
                        break;
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });

    }

    public void initializeFragments() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, PlayerHomepageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PlayerStatsKeeperFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PlayerSettingsFragment.class.getName()));

        mPagerAdapter = new com.thebaileybrew.favoriteathletestattracker.PagerAdapter(super.getSupportFragmentManager(), fragments);
        ViewPager pager = super.findViewById(R.id.viewPager);
        pager.setAdapter(this.mPagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        fab.setVisibility(View.GONE);
                        mBottomNavigation.setSelectedIndex(0, true);
                        break;
                    case 1:
                        fab.setVisibility(View.GONE);
                        mBottomNavigation.setSelectedIndex(1, true);
                        break;
                    case 2:
                        fab.setVisibility(View.VISIBLE);
                        mBottomNavigation.setSelectedIndex(2, true);
                        break;
                }
            }
        });
    }

    public void validateTextViews() {
        passingYardsDisplay = findViewById(R.id.passing_yards_actual);
        rushingYardsDisplay = findViewById(R.id.rushing_yards_actual);
        recvingYardsDisplay = findViewById(R.id.receiving_yards_actual);
        touchdownsDisplay = findViewById(R.id.touchdowns_actual);
        passAttemptsDisplay = findViewById(R.id.passing_attempts_actual);
        passCompleteDisplay = findViewById(R.id.passing_complete_actual);
        rushAttemptDisplay = findViewById(R.id.rushing_attempt_actual);
        catchesDisplay = findViewById(R.id.receiving_attempt_actual);
        interceptionDisplay = findViewById(R.id.interception_actual);
    }

    public void getStringValuesForScoringUpdates() {
        /*
        * This method converts the int values for scoring into Strings
        * These values will be updated to the current user
        */
    }



}



