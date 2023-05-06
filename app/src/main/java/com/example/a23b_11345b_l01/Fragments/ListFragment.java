package com.example.a23b_11345b_l01.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23b_11345b_l01.Adapters.ScoreAdapter;
import com.example.a23b_11345b_l01.Interfaces.CallBack_SendClick;
import com.example.a23b_11345b_l01.Interfaces.ScoreCallback;
import com.example.a23b_11345b_l01.Models.Score;
import com.example.a23b_11345b_l01.Models.ScoreList;
import com.example.a23b_11345b_l01.R;
import com.example.a23b_11345b_l01.Utilities.MySP3;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView list_LST_scores;
    private AppCompatEditText list_ET_name;
    private MaterialButton list_BTN_send;

    private CallBack_SendClick callBack_sendClick;

    public static final String KEY_SCORE = "KEY_SCORE";

    public void setCallBack_sendClick(CallBack_SendClick callBack_sendClick) {
        this.callBack_sendClick = callBack_sendClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);
        list_BTN_send.setOnClickListener(v -> sendClicked());
        return view;
    }

    private void initViews(View view) {
        int userScore = getArguments().getInt(KEY_SCORE);
        Score userScoreItem = new Score();
        userScoreItem.setScore(userScore);
        userScoreItem.setLan(1);
        userScoreItem.setLat(2);

        String fromSP =  MySP3.getInstance().getString("score-list","");
        Log.d("From SP", fromSP);
        ScoreList scorelistFromJson;
        if(fromSP == ""){
            scorelistFromJson = new ScoreList();
            scorelistFromJson.setName("score-list");
            ArrayList<Score> scoreList = new ArrayList<Score>();
            scoreList.add(userScoreItem);
            scorelistFromJson.setScores(scoreList);
        }else{
            scorelistFromJson = new Gson().fromJson(fromSP, ScoreList.class );
            scorelistFromJson.getScores().add(userScoreItem);
        }
        Comparator<Score> comparator = new Comparator<Score>() {
            @Override
            public int compare(Score obj1, Score obj2) {
                return obj2.getScore() - obj1.getScore();
            }
        };

        Collections.sort(scorelistFromJson.getScores(), comparator);
        ArrayList<Score> firstTenScores = new ArrayList<Score>(scorelistFromJson.getScores().subList(0, Math.min(scorelistFromJson.getScores().size(), 10)));
        String scoreListJson = new Gson().toJson(scorelistFromJson);
        MySP3.getInstance().putString("score-list", scoreListJson);
        Log.d("From JSON", scoreListJson.toString());
        ScoreAdapter scoreAdapter = new ScoreAdapter(getActivity(), scorelistFromJson.getScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_scores.setLayoutManager(linearLayoutManager);
        list_LST_scores.setAdapter(scoreAdapter);
        scoreAdapter.setScoreCallback(new ScoreCallback() {
//            @Override
//            public void favoriteClicked(Score movie, int position) {
//                movie.setFavorite(!movie.isFavorite());
//                main_LST_movies.getAdapter().notifyItemChanged(position);
//            }
//
//            @Override
//            public void itemClicked(Movie movie, int position) {
//                Toast.makeText(MainActivity.this, "" + movie.getTitle(), Toast.LENGTH_SHORT).show();
//            }
        });
    }

    private void sendClicked() {
        if (callBack_sendClick != null)
            callBack_sendClick.userNameChosen(list_ET_name.getText().toString());
    }

    private void findViews(View view) {
        list_LST_scores = view.findViewById(R.id.list_LST_scores);
        list_ET_name = view.findViewById(R.id.list_ET_name);
        list_BTN_send = view.findViewById(R.id.list_BTN_send);
    }
}