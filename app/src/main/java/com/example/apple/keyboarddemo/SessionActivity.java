package com.example.apple.keyboarddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;

/**
 * @author crazyZhangxl on 2018-12-22 16:31:38.
 * Describe: 模拟聊天布局
 */

public class SessionActivity extends AppCompatActivity {
    private EditText mEtContent;
    private Button mBtnSend;
    private ImageView mIvMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_session);
        initViews();
        initListener();
    }

    private void initListener() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtContent.getText().toString().trim().length() > 0) {
                    // 如果输入框中有内容 那么进行更多的切换
                    mBtnSend.setVisibility(View.VISIBLE);
                    mIvMore.setVisibility(View.GONE);
                    // 这个表示用户正在输入 ====
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    mIvMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViews() {
        mEtContent = findViewById(R.id.etContent);
        mBtnSend = findViewById(R.id.btnSend);
        mIvMore = findViewById(R.id.ivMore);

    }
}
