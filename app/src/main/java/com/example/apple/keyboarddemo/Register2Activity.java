package com.example.apple.keyboarddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author crazyZhangxl on 2018-12-22 19:43:19.
 * Describe:
 */

public class Register2Activity extends AppCompatActivity {
    private LinearLayout mLinearLayout;
    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_register2);
        mLinearLayout = findViewById(R.id.ll);
        mRelativeLayout = findViewById(R.id.rl);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
