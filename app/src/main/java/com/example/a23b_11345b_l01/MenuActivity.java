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
    private MaterialButton menu_sensor_BTN;
    private boolean isNormalSpeed;
    private boolean isSensorMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        setButtonsClickListeners();
        isNormalSpeed=true;
        isSensorMode=false;
    }

    private void setButtonsClickListeners() {
        menu_play_BTN.setOnClickListener(v ->openMainActivity());
        menu_speed_BTN.setOnClickListener(v ->toggleSpeed());
        menu_sensor_BTN.setOnClickListener(v ->toggleSensors());
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_NORMAL_SPEED,isNormalSpeed);
        intent.putExtra(MainActivity.KEY_IS_SENSOR,isSensorMode);
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

    private void toggleSensors(){
        if(isSensorMode)
            menu_sensor_BTN.setText("OFF");
        else
            menu_sensor_BTN.setText("ON");
        isSensorMode = !isSensorMode;
    }


    private void findViews() {
        menu_play_BTN = findViewById(R.id.menu_BTN_play);
        menu_speed_BTN = findViewById(R.id.menu_BTN_speed);
        menu_sensor_BTN = findViewById(R.id.menu_BTN_sensor);
    }
}