package com.example.a23b_11345b_l01;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a23b_11345b_l01.Fragments.ListFragment;
import com.example.a23b_11345b_l01.Fragments.MapFragment;
import com.example.a23b_11345b_l01.Interfaces.SendMapData;
import com.example.a23b_11345b_l01.Utilities.MySP3;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScoreActivity extends AppCompatActivity{

    private ListFragment listFragment;
    private MapFragment mapFragment;

    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String KEY_LOCATION = "KEY_LOCATION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        MySP3.init(this);
        int score =  getIntent().getIntExtra(KEY_SCORE,0);
        Location currentLocation =  getIntent().getParcelableExtra(KEY_LOCATION);

        initFragments(score,currentLocation);
        beginTransactions();

    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_list, listFragment).commitNow();
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_map, mapFragment).commit();
    }

    private void initFragments(int score,Location currentLocation) {
        listFragment = new ListFragment(sendMapData);
        Bundle args = new Bundle();
        args.putInt(KEY_SCORE, score);
        double[] lanLat = {currentLocation.getLatitude(),currentLocation.getLongitude()};
        args.putDoubleArray(KEY_LOCATION,lanLat);
        listFragment.setArguments(args);

        mapFragment = new MapFragment();
    }

    SendMapData sendMapData = new SendMapData() {

        @Override
        public void sendLocation(double lan, double lat) {
            mapFragment.markLocation(lan,lat);
        }
    };
}