package com.example.apple.keyboarddemo.collapse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.apple.keyboarddemo.R;

/**
 * 可折叠的Activity
 */
public class CollapseActivity extends AppCompatActivity {
    private RelativeLayout mLayout;
    private ImageView mIvArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse);
        mLayout = (RelativeLayout) findViewById(R.id.linear);
        mIvArrow = (ImageView)findViewById(R.id.ivArrow);
        initExpandView();
    }

    private ExpandLayout mExpandLayout;

    public void initExpandView() {
        mExpandLayout = (ExpandLayout) findViewById(R.id.expandLayout);
        if (mExpandLayout != null) {
            mExpandLayout.initExpand(false,mIvArrow);
        }
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandLayout.toggleExpand();
            }
        });
    }
}
