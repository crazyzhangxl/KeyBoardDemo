package com.example.apple.keyboarddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author crazyZhangxl on 2018-10-18 8:36:33.
 * Describe:
 */

public class MainActivity extends AppCompatActivity implements KeyboardUtil.NumKeyboardListener {
    private EditText mEditText;
    private CustomNumKeyboardView mCustomNumKeyboardView;
    private KeyboardUtil mKeyboardUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mCustomNumKeyboardView = findViewById(R.id.keyboardView);
        mKeyboardUtil = new KeyboardUtil(this,mCustomNumKeyboardView);
        mKeyboardUtil.attachEditText(mEditText);
        mKeyboardUtil.setNumKeyboardListener(this);
        mKeyboardUtil.showNumKeyboard();
    }

    @Override
    public void onClose() {
        Toast.makeText(this, "关闭软键盘", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirm() {
        Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNumberChanged(String paramString) {
        // 键盘录入时
    }
}
