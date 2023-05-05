package com.example.a23b_11345b_l01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a23b_11345b_l01.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {
    private MaterialButton menu_play_BTN;
    private MaterialButton menu_speed_BTN;
    private boolean isNormalSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        setButtonsClickListeners();
        isNormalSpeed=true;
    }

    private void setButtonsClickListeners() {
        menu_play_BTN.setOnClickListener(v ->openMainActivity());
        menu_speed_BTN.setOnClickListener(v ->toggleSpeed());
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra(MainActivity.KEY_SCORE,score);
//        intent.putExtra(MainActivity.KEY_STATUS,status);
        startActivity(intent);
        finish();
    }

    private void toggleSpeed(){
        if(isNormalSpeed)
            menu_speed_BTN.setText("FAST");
        else
            menu_speed_BTN.setText("NORMAL");
        isNormalSpeed = !isNormalSpeed;
    }

    private void findViews() {
        menu_play_BTN = findViewById(R.id.menu_BTN_play);
        menu_speed_BTN = findViewById(R.id.menu_BTN_speed);
    }
}