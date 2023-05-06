package com.example.a23b_11345b_l01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a23b_11345b_l01.Fragments.ListFragment;
import com.example.a23b_11345b_l01.Fragments.MapFragment;
import com.example.a23b_11345b_l01.Interfaces.CallBack_SendClick;
import com.example.a23b_11345b_l01.Utilities.MySP3;

public class ScoreActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    public static final String KEY_SCORE = "KEY_SCORE";
    private CallBack_SendClick callBack_sendClick = new CallBack_SendClick() {
        @Override
        public void userNameChosen(String name) {
            mapFragment.zoomOnRecord(name);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        MySP3.init(this);

        int score =  getIntent().getIntExtra(KEY_SCORE,0);

        initFragments(score);

        beginTransactions();
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_map, mapFragment).commit();
    }

    private void initFragments(int score) {
        listFragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_SCORE, score);
        listFragment.setArguments(args);

        listFragment.setCallBack_sendClick(callBack_sendClick);
        mapFragment = new MapFragment();
    }
}