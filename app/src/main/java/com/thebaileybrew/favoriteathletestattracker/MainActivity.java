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
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

    int TotalPassingYards;
    String formattedTotalPass = "";
    int TotalRushingYards;
    int TotalReceivingYards;

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

    TextView passingYardsTotalDisplay;
    TextView rushingYardsTotalDisplay;
    TextView recvingYardsTotalDisplay;

    BubbleSeekBar bubbleSeekBarPassing;
    BubbleSeekBar bubbleSeekBarRushing;
    BubbleSeekBar bubbleSeekBarReceiving;




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
        pager.setOffscreenPageLimit(3);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        fab.setVisibility(View.GONE);
                        mBottomNavigation.setSelectedIndex(0, true);
                        passingYardsTotalDisplay = findViewById(R.id.passing_yards_actual);
                        passingYardsTotalDisplay.setText(formattedTotalPass);
                        rushingYardsTotalDisplay = findViewById(R.id.rushing_yards_actual);
                        recvingYardsTotalDisplay = findViewById(R.id.receiving_yards_actual);
                        break;
                    case 1:
                        fab.setVisibility(View.GONE);
                        mBottomNavigation.setSelectedIndex(1, true);
                        bubbleSeekBarPassing = findViewById(R.id.seekBar_passing);
                        bubbleSeekBarRushing = findViewById(R.id.seekBar_rushing);
                        bubbleSeekBarReceiving = findViewById(R.id.seekBar_receiving);
                        TextView completedPass = findViewById(R.id.submit_passing_yards);
                        completedPass.setOnClickListener(MainActivity.this);
                        TextView completedRush = findViewById(R.id.submit_rushing_yards);
                        completedRush.setOnClickListener(MainActivity.this);
                        TextView completedCatch = findViewById(R.id.submit_receiving_yards);
                        completedCatch.setOnClickListener(MainActivity.this);
                        bubbleSeekBarPassing.getConfigBuilder().progress(0).build();
                        bubbleSeekBarRushing.getConfigBuilder().progress(0).build();
                        bubbleSeekBarReceiving.getConfigBuilder().progress(0).build();
                        bubbleSeekBarProgressListeners();
                        break;
                    case 2:
                        fab.setVisibility(View.VISIBLE);
                        mBottomNavigation.setSelectedIndex(2, true);
                        break;
                }
            }
        });
    }

    public void bubbleSeekBarProgressListeners() {
        bubbleSeekBarPassing.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                getStringValuesForScoringUpdates();
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        bubbleSeekBarRushing.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                getStringValuesForScoringUpdates();
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        bubbleSeekBarReceiving.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                getStringValuesForScoringUpdates();
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

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
        passingYardsDisplay = findViewById(R.id.passing_yards_seekbar_display);
        BubbleSeekBar bubbleSeekBarPassing = findViewById(R.id.seekBar_passing);
        passingYards = bubbleSeekBarPassing.getProgress();
        String formattedPassing = String.format(Locale.US,"%03d",passingYards);
        passingYardsDisplay.setText(formattedPassing);

        rushingYardsDisplay = findViewById(R.id.rushing_yards_seekbar_display);
        BubbleSeekBar bubbleSeekBarRushing = findViewById(R.id.seekBar_rushing);
        rushingYards = bubbleSeekBarRushing.getProgress();
        String formattedRushing = String.format(Locale.US,"%03d",rushingYards);
        rushingYardsDisplay.setText(formattedRushing);

        recvingYardsDisplay = findViewById(R.id.receiving_yards_seekbar_display);
        BubbleSeekBar bubbleSeekBarReceiving = findViewById(R.id.seekBar_receiving);
        recvingYards = bubbleSeekBarReceiving.getProgress();
        String formattedReceiving = String.format(Locale.US,"%03d",recvingYards);
        recvingYardsDisplay.setText(formattedReceiving);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_passing_yards:
                TotalPassingYards = TotalPassingYards + passingYards;
                formattedTotalPass = String.format(Locale.US,"%04d",TotalPassingYards);
                break;
            case R.id.submit_rushing_yards:
                break;
            case R.id.submit_receiving_yards:
                break;
        }
    }
}



