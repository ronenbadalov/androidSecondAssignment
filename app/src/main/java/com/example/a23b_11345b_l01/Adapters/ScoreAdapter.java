package com.example.a23b_11345b_l01.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23b_11345b_l01.Interfaces.ScoreCallback;
import com.example.a23b_11345b_l01.Models.Score;
import com.example.a23b_11345b_l01.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

private Context context;
private ArrayList<Score> scores;
private ScoreCallback scoreCallback;

public ScoreAdapter(Context context, ArrayList<Score> scores,ScoreCallback scoreCallback) {
        this.context = context;
        this.scores = scores;
        this.scoreCallback = scoreCallback;
        }

@NonNull
@Override
public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("passed VT:", "" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ScoreViewHolder movieViewHolder = new ScoreViewHolder(view);
        return movieViewHolder;
        }

@Override
public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder.score_item_place.setText((position + 1) + ")");
        holder.score_item_score.setText(score.getScore() + " Points");
        holder.score_item_layout.setOnClickListener(v -> {
            scoreCallback.onScoreClick(score.getLan(), score.getLat());
        });
        }

@Override
public int getItemCount() {
        return this.scores == null ? 0 : scores .size();
        }

private Score getItem(int position) {
        return this.scores.get(position);
        }

public class ScoreViewHolder extends RecyclerView.ViewHolder {
    private MaterialTextView score_item_score;
    private MaterialTextView score_item_place;
    private RelativeLayout score_item_layout;

    public ScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        score_item_place = itemView.findViewById(R.id.score_item_place);
        score_item_score = itemView.findViewById(R.id.score_item_score);
        score_item_layout = itemView.findViewById(R.id.score_item_layout);
    }
}
}