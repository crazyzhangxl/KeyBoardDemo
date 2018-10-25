package com.example.apple.keyboarddemo.topic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.keyboarddemo.R;

/**
 * @author crazyZhangxl on 2018-10-25 9:52:10.
 * Describe:
 */

public class TopicActivity extends AppCompatActivity {

    private LinearLayout mLlComment;
    private FrameLayout mFmContainer;
    private TextView mTextView;
    private BottomSheetBar mBottomSheetBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        initViews();
        initListener();
    }

    private void initListener() {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBar.show(mTextView.getHint().toString());
            }
        });
    }

    private void initViews() {
        mLlComment = findViewById(R.id.ll_comment);
        mFmContainer = findViewById(R.id.lay_container);
        mTextView = findViewById(R.id.tv_comment);
        mBottomSheetBar = BottomSheetBar.delegation(this);
    }
}
