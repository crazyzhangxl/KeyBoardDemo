package com.example.apple.keyboarddemo.top_dismiss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.apple.keyboarddemo.R;

/**
 * @author crazyZhangxl on 2019-1-3 20:29:38.
 * Describe:
 */

public class TopDismissThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_top_dismiss_first);
    }
}
