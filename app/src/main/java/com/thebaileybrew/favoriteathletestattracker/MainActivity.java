package com.thebaileybrew.favoriteathletestattracker;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    RVAdapter adapter;
    private List<players> Players;
    TextView playerName;
    TextView playerPosition;
    TextView playerTeam;
    ImageView playerHeadshot;

    public static final int PICK_IMAGE = 1;

    private PagerAdapter mPagerAdapter;
    BottomNavigation mBottomNavigation;
    FloatingActionButton fab;
    ViewPager pager;

    //Integer Values
    int passingYards;
    int rushingYards;
    int recvingYards;
    int touchdowns;
    int passAttempts;
    int passIncomplete;
    int rushAttempts;
    int interceptions;
    int fumbles;
    int TotalPassingYards;
    int TotalRushingYards;
    int TotalReceivingYards;
    String formattedTouchdowns = "";
    String formattedPassAttempts = "";
    String formattedPassComplete = "";
    String formattedRushAttempts = "";
    String formattedInterceptions = "";
    String formattedFumbles = "";
    String formattedTotalPass = "";
    String formattedTotalRush = "";
    String formattedTotalReceiving = "";

    //TextViews For Value Displays on HomePage
    TextView passingYardsDisplay;
    TextView rushingYardsDisplay;
    TextView recvingYardsDisplay;
    TextView touchdownsDisplay;
    TextView passAttemptsDisplay;
    TextView passCompleteDisplay;
    TextView rushAttemptDisplay;
    TextView interceptionDisplay;
    TextView fumblesDisplay;
    TextView passingYardsTotalDisplay;
    TextView rushingYardsTotalDisplay;
    TextView recvingYardsTotalDisplay;

    BubbleSeekBar bubbleSeekBarPassing;
    BubbleSeekBar bubbleSeekBarRushing;
    BubbleSeekBar bubbleSeekBarReceiving;

    EditText playerNameSubmit;
    EditText playerPositionSubmit;
    EditText playerTeamSubmit;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigation = findViewById(R.id.bottomNavigation);
        fab = findViewById(R.id.floating_action_button);
        dialog = new Dialog(MainActivity.this);

        initializeFragments();
        validateTextViews();

        /*
        * Setting the Menu Item Click Listener
        * Initiates the Viewpager and animates the page change
        * Defines if the FAB is visible or invisible on MenuClick
        */
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
                    case 3:
                        fab.setVisibility(View.GONE);
                        pager.setCurrentItem(3, true);
                        break;
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });





    }

    /*
    * Created the initial collection of data for the Player Array on player_settings xml
    * Adds player data for the CardView Recycler
    */
    private void initializeData() {
        Players = new ArrayList<>();
        Players.add(new players("Tom Brady", "Quarterback","New England Patriots", R.drawable.brady_headshot));
        Players.add(new players("Ichiro Suzuki", "Outfielder","Seattle Mariners", R.drawable.suzuki_headshot));
        Players.add(new players("Amari Cooper","Wide Receiver","Oakland Raiders", R.drawable.cooper_headshot));
        Players.add(new players("Leonard Fournette", "Running Back","Jacksonville Jaguars",R.drawable.fournette_headshot));
        Players.add(new players("Giannis Antetokounmpo","Forward/Guard","Milwaukee Bucks", R.drawable.greekfreak_headshot));
    }

    //Sets the adapter for the RecyclerView
    private void initializeAdapter() {
        adapter = new RVAdapter(Players);
        recyclerView.setAdapter(adapter);
    }

    //Creates the list of fragment pages to instantiate with the Viewpager
    public void initializeFragments() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, PlayerHomepageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PlayerStatsKeeperFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PlayerSettingsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,PlayerHistoryFragment.class.getName()));

        mPagerAdapter = new com.thebaileybrew.favoriteathletestattracker.PagerAdapter(super.getSupportFragmentManager(), fragments);
        ViewPager pager = super.findViewById(R.id.viewPager);
        pager.setAdapter(this.mPagerAdapter);
        pager.setOffscreenPageLimit(4);

        //Defines the actions taken with the pager is switched from fragment to fragment
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        fab.setVisibility(View.GONE);
                        mBottomNavigation.setSelectedIndex(0, true);
                        passingYardsTotalDisplay = findViewById(R.id.passing_yards_actual);
                        passingYardsTotalDisplay.setText(formattedTotalPass);
                        rushingYardsTotalDisplay = findViewById(R.id.rushing_yards_actual);
                        rushingYardsTotalDisplay.setText(formattedTotalRush);
                        recvingYardsTotalDisplay = findViewById(R.id.receiving_yards_actual);
                        recvingYardsTotalDisplay.setText(formattedTotalReceiving);
                        passAttemptsDisplay = findViewById(R.id.passing_attempts_actual);
                        passAttemptsDisplay.setText(formattedPassComplete);
                        passCompleteDisplay = findViewById(R.id.passing_complete_actual);
                        passCompleteDisplay.setText(formattedPassAttempts);
                        rushAttemptDisplay = findViewById(R.id.rushing_attempt_actual);
                        rushAttemptDisplay.setText(formattedRushAttempts);
                        touchdownsDisplay = findViewById(R.id.touchdowns_actual);
                        touchdownsDisplay.setText(formattedTouchdowns);
                        fumblesDisplay = findViewById(R.id.fumble_actual);
                        fumblesDisplay.setText(formattedFumbles);
                        interceptionDisplay = findViewById(R.id.interception_actual);
                        interceptionDisplay.setText(formattedInterceptions);
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
                        Button touchdownButton = findViewById(R.id.touchdown_button);
                        Button incompleteButton = findViewById(R.id.incomplete_button);
                        Button fumbleButton = findViewById(R.id.fumble_button);
                        Button interceptionButton = findViewById(R.id.interception_button);
                        touchdownButton.setOnClickListener(MainActivity.this);
                        incompleteButton.setOnClickListener(MainActivity.this);
                        fumbleButton.setOnClickListener(MainActivity.this);
                        interceptionButton.setOnClickListener(MainActivity.this);
                        bubbleSeekBarPassing.getConfigBuilder().progress(0).build();
                        bubbleSeekBarRushing.getConfigBuilder().progress(0).build();
                        bubbleSeekBarReceiving.getConfigBuilder().progress(0).build();
                        bubbleSeekBarProgressListeners();
                        break;
                    case 2:
                        fab.setVisibility(View.VISIBLE);
                        fab.setOnClickListener(MainActivity.this);
                        mBottomNavigation.setSelectedIndex(2, true);
                        recyclerView = findViewById(R.id.main_recycler_view);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        initializeData();
                        initializeAdapter();
                        playerName = findViewById(R.id.player_name_card_textview);
                        playerPosition = findViewById(R.id.player_position_card_textview);
                        playerTeam = findViewById(R.id.player_team_card_textview);
                        playerHeadshot = findViewById(R.id.player_image_card_header);
                        break;
                    case 3:
                        fab.setVisibility(View.GONE);
                        mBottomNavigation.setSelectedIndex(3,true);
                        break;
                }
            }
        });
    }

    //Checks for progress change on the seekBars and gets the values to update scoring on the Homepage
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

    //Defines the TextViews on the HomePage
    public void validateTextViews() {
        passingYardsDisplay = findViewById(R.id.passing_yards_actual);
        rushingYardsDisplay = findViewById(R.id.rushing_yards_actual);
        recvingYardsDisplay = findViewById(R.id.receiving_yards_actual);
        touchdownsDisplay = findViewById(R.id.touchdowns_actual);
        passAttemptsDisplay = findViewById(R.id.passing_attempts_actual);
        passCompleteDisplay = findViewById(R.id.passing_complete_actual);
        rushAttemptDisplay = findViewById(R.id.rushing_attempt_actual);

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
                passAttempts = passAttempts + 1;
                passIncomplete = passIncomplete + 1;
                TotalPassingYards = TotalPassingYards + passingYards;
                formattedTotalPass = String.format(Locale.US,"%04d",TotalPassingYards);
                formattedPassComplete = String.format(Locale.US,"%04d",passIncomplete);
                formattedPassAttempts = String.format(Locale.US,"%04d",passAttempts);
                break;
            case R.id.incomplete_button:
                passIncomplete = passIncomplete + 1;
                formattedPassComplete = String.format(Locale.US,"%04d",passIncomplete);
                break;
            case R.id.touchdown_button:
                touchdowns = touchdowns + 1;
                formattedTouchdowns = String.format(Locale.US,"%04d",touchdowns);
                break;
            case R.id.interception_button:
                interceptions = interceptions + 1;
                formattedInterceptions = String.format(Locale.US,"%04d",interceptions);
                break;
            case R.id.fumble_button:
                fumbles = fumbles + 1;
                formattedFumbles = String.format(Locale.US,"%04d", fumbles);
                break;
            case R.id.submit_rushing_yards:
                rushAttempts = rushAttempts + 1;
                TotalRushingYards = TotalRushingYards + rushingYards;
                formattedTotalRush = String.format(Locale.US,"%04d",TotalRushingYards);
                formattedRushAttempts = String.format(Locale.US,"%04d",rushAttempts);
                break;
            case R.id.submit_receiving_yards:
                TotalReceivingYards = TotalReceivingYards + recvingYards;
                formattedTotalReceiving = String.format(Locale.US,"%04d",TotalReceivingYards);
                break;
            case R.id.floating_action_button:
                dialog.setContentView(R.layout.player_adddetails);
                dialog.show();
                if (dialog.isShowing()) {
                    playerNameSubmit = dialog.findViewById(R.id.player_name_edit_text);
                    playerPositionSubmit = dialog.findViewById(R.id.player_position_edit_text);
                    playerTeamSubmit = dialog.findViewById(R.id.player_team_edit_text);
                    Button submitNewPlayer = dialog.findViewById(R.id.submit_new_player);
                    ImageButton playerHeadshotReq = dialog.findViewById(R.id.player_headshot_upload);
                    playerHeadshotReq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                            getImage.setType("image/*");
                            Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickImage.setType("image/*");
                            Intent chooserIntent = Intent.createChooser(getIntent(),"Select Image");
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickImage});
                            startActivityForResult(chooserIntent, PICK_IMAGE);
                        }
                    });
                    submitNewPlayer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String submitPlayerName = playerNameSubmit.getText().toString();
                            String submitPlayerPosition = playerPositionSubmit.getText().toString();
                            String submitPlayerTeam = playerTeamSubmit.getText().toString();
                            Players.add(new players(submitPlayerName, submitPlayerPosition,submitPlayerTeam, R.drawable.ic_add_a_photo_teal_24dp));
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    });
                }
                break;
        }
    }
}



