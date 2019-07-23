package com.example.apple.keyboarddemo.focus;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.apple.keyboarddemo.R;

/**
 * created by apple on 2019/7/23
 *
 * 软键盘焦点活动
 */
public class SoftFocusActivity extends AppCompatActivity {
    private View mRootView;
    private EditText mEtSend,mEtTheme;
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
