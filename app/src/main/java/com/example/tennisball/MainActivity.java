package com.example.tennisball;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sm;
    ImageView ball;
    List sensorList;

    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            float newX = ball.getX() - values[0];
            float newY = ball.getY() + values[1];
            if (isValidPosition(newX, newY)) {
                ball.setX(newX);
                ball.setY(newY);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    private boolean isValidPosition(float x, float y){
        //Get DisplayMetrics
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //Calculate height of screen/metric
        Float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //Calculate width of screen/metric
        Float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        if(x > 0 - ball.getMeasuredWidth() / 2  && (x + ball.getMeasuredWidth()) / 2 < dpWidth * 2 && y > 0 - ball.getMeasuredHeight() / 2  && (y - ball.getMeasuredHeight() / 2) < dpHeight * 2)
        {
            Log.i("error", "her");
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        ball = findViewById(R.id.ball);
        sensorList = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(sel, (Sensor) sensorList.get(0), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop(){
        sm.unregisterListener(sel);

        super.onStop();
    }
}