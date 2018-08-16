package com.power;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private PowerView powerView;
    private TextView tv_power_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        powerView = findViewById(R.id.powerView);
        tv_power_value = findViewById(R.id.tv_power_value);

    }


    public void start(View view){
        int random = (int) (Math.random()*100);
        powerView.setPower(random,tv_power_value);
    }
}
