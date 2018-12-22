package com.example.apple.keyboarddemo.shelter_aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.apple.keyboarddemo.R;

/**
 * @author crazyZhangxl on 2018-10-25 16:17:06.
 * Describe: 活动中软键盘的遮挡问题探究
 */

public class ShelterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_shelter);
    }
}
