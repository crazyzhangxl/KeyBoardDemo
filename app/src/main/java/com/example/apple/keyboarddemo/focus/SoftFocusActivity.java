package com.example.apple.keyboarddemo.focus;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.apple.keyboarddemo.R;
import com.example.apple.keyboarddemo.RedDotView;
import com.example.apple.keyboarddemo.expand.ExpandTextView;
import com.ycbjie.ycreddotviewlib.YCRedDotView;

/**
 * created by apple on 2019/7/23
 *
 * 软键盘焦点活动
 */
public class SoftFocusActivity extends AppCompatActivity {
    private View mRootView;
    private EditText mEtSend,mEtTheme;
    private ExpandTextView mExpandTextView;
    private LinearLayout mLinear;
    private RedDotView mRedDotView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_focus);
        initViews();
        initListener();
        initData();
    }


    private void initViews() {
        mRootView = findViewById(R.id.llRoot);
        mEtSend = findViewById(R.id.etSend);
        mEtTheme = findViewById(R.id.etTheme);
        mExpandTextView = findViewById(R.id.expandTextView);
        mLinear = findViewById(R.id.linear);
        mRedDotView = findViewById(R.id.redDot);
        mRedDotView.setBadgeCount(0);
    }


    private void initListener() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null){
                    mEtSend.clearFocus();
                    mEtTheme.clearFocus();
                    hideKeyboard(mRootView,getCurrentFocus().getWindowToken());
                }
            }
        });
    }


    private void initData() {
        mEtSend.requestFocus();
        mEtSend.setSelection(mEtSend.getText().toString().length());
        mExpandTextView.setText("我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁我是谁");
    }

    private void initRedDot(){

    }



    private void hideKeyboard(final View view, IBinder windowToken) {
        if(windowToken == null){
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null){
            return;
        }
        boolean active = inputMethodManager.isActive();
        if (active) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
