package com.thebaileybrew.favoriteathletestattracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayerStatsKeeperFragment extends Fragment {
    public static PlayerStatsKeeperFragment newInstance() {
        PlayerStatsKeeperFragment fragment = new PlayerStatsKeeperFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_statkeeper,container, false);
    }
}
