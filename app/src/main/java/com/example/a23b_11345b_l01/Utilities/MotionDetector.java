package com.example.a23b_11345b_l01.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.a23b_11345b_l01.Interfaces.MotionCallback;

public class MotionDetector {
    private Sensor sensor;

    private SensorManager sensorManager;

    private MotionCallback motionCallback;

    private long timestamp = 0;

    private int currentLane = 0;
    private SensorEventListener sensorEventListener;

    public MotionDetector(Context context, MotionCallback motionCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.motionCallback = motionCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calcCurrentLane(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }


        };
    }

    private void calcCurrentLane(float x) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 2.5 ) {
                if (motionCallback != null){
                    currentLane = 0;
                    motionCallback.moveX();
                }
            }
            if (x <= 4.5 && x > 1.5 ) {
                if (motionCallback != null){
                    currentLane = 1;
                    motionCallback.moveX();
                }
            }
            if (x < 1.5 && x > -1.5 ) {
                if (motionCallback != null){
                    currentLane = 2;
                    motionCallback.moveX();
                }
            }
            if (x <= -1.5 && x > -4.5 ) {
                if (motionCallback != null){
                    currentLane = 3;
                    motionCallback.moveX();
                }
            }
            if (x <= -4.5  ) {
                if (motionCallback != null){
                    currentLane = 4;
                    motionCallback.moveX();
                }
            }

        }
    }

    public int getCurrentLane() {
        return currentLane;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}