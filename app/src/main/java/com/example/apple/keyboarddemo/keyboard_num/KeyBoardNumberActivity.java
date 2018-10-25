package com.example.apple.keyboarddemo.keyboard_num;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.keyboarddemo.R;

/**
 * @author crazyZhangxl on 2018-10-18 8:36:33.
 * Describe:
 */

public class KeyBoardNumberActivity extends AppCompatActivity implements KeyboardNumUtil.NumKeyboardListener {
    private EditText mEditText;
    private CustomNumKeyboardView mCustomNumKeyboardView;
    private KeyboardNumUtil mKeyboardNumUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_number);

        mEditText = findViewById(R.id.editText);
        mCustomNumKeyboardView = findViewById(R.id.keyboardView);
        mKeyboardNumUtil = new KeyboardNumUtil(this,mCustomNumKeyboardView);
        mKeyboardNumUtil.attachEditText(mEditText);
        mKeyboardNumUtil.setNumKeyboardListener(this);
        mKeyboardNumUtil.showNumKeyboard();
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
