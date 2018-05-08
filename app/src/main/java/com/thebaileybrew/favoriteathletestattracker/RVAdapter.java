package com.thebaileybrew.favoriteathletestattracker;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PlayerViewHolder> {

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView playerName;
        TextView playerPosition;
        TextView playerTeam;
        ImageView playerHeadshot;

        PlayerViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            playerName = itemView.findViewById(R.id.player_name_card_textview);
            playerPosition = itemView.findViewById(R.id.player_position_card_textview);
            playerTeam = itemView.findViewById(R.id.player_team_card_textview);
            playerHeadshot = itemView.findViewById(R.id.player_image_card_header);
        }
    }

    private List<players> Players;

    RVAdapter(List<players> Players){
        this.Players = Players;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        PlayerViewHolder pvh = new PlayerViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder playerViewHolder, int i) {
        playerViewHolder.playerName.setText(Players.get(i).name);
        playerViewHolder.playerPosition.setText(Players.get(i).position);
        playerViewHolder.playerTeam.setText(Players.get(i).team);
        playerViewHolder.playerHeadshot.setImageResource(Players.get(i).headshot);
    }

    @Override
    public int getItemCount() {
        return Players.size();
    }


}
