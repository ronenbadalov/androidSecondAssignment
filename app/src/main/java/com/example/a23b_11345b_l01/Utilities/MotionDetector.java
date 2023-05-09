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

    private int stepCounterX = 0;
    private int stepCounterY = 0;
    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public MotionDetector(Context context, MotionCallback motionCallback) {
        Log.d("in","innnnnnnnnnn");
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
                float y = event.values[1];
                Log.d("x",""+x);
                Log.d("y",""+y);
                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }


        };
    }

    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 6.0) {
                stepCounterX++;
                if (motionCallback != null)
                    motionCallback.moveX();
            }
            if (y > 6.0) {
                stepCounterY++;
                if (motionCallback != null)
                    motionCallback.moveY();
            }
        }
    }

    public int getStepsX() {
        return stepCounterX;
    }

    public int getStepsY() {
        return stepCounterY;
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