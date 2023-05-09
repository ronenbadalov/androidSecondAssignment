package com.example.a23b_11345b_l01;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.a23b_11345b_l01.Logic.GameManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import android.Manifest;
public class MenuActivity extends AppCompatActivity {
    private MaterialButton menu_play_BTN;
    private MaterialButton menu_speed_BTN;
    private MaterialButton menu_sensor_BTN;
    private boolean isNormalSpeed;
    private boolean isSensorMode;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION =123;
    private Location currentLocation;
    FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("test","testtt");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();

        getCurrentLocation();

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
        intent.putExtra(MainActivity.KEY_LOCATION,currentLocation);
        Log.d("test",""+currentLocation);
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

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;
                                Log.d("test",""+location);
                            }
                        }
                    });
        }
    }

    private void findViews() {
        menu_play_BTN = findViewById(R.id.menu_BTN_play);
        menu_speed_BTN = findViewById(R.id.menu_BTN_speed);
        menu_sensor_BTN = findViewById(R.id.menu_BTN_sensor);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("request code",""+requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
            }
        }
    }
}