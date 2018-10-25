package com.example.apple.keyboarddemo.keyboard_num;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.apple.keyboarddemo.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author crazyZhangxl on 2018/10/18.
 * Describe:
 */
public class KeyboardNumUtil {
    private int keyboardLayout;
    private Context mContext;
    private EditText mEditText;
    private KeyboardView mKeyboardView;
    private InputMethodManager mInputMethodManager;
    private NumKeyboardListener mNumKeyboardListener;
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {
                // 取消 -------- 进行隐藏的效果   ---------
                // --------------------------
                if (mNumKeyboardListener != null){
                    mNumKeyboardListener.onClose();
                }
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                // 回退 --------- 删除该数据      ---------
                if (mEditText != null){
                    int start = mEditText.getSelectionStart();
                    if (start >= 1){
                        mEditText.getText().delete(start-1,start);
                    }
                }
            }  else if (primaryCode == Keyboard.KEYCODE_DONE){
                // 确定提交--------------
                if (mNumKeyboardListener != null){
                    mNumKeyboardListener.onConfirm();
                }
            }else {
                // 具体的数值
                String num = Character.toString((char) primaryCode);
                if (mEditText != null){
                    int length = mEditText.getText().toString().length();
                    mEditText.getText().insert(length,num);
                }
                if (mNumKeyboardListener != null){
                    mNumKeyboardListener.onNumberChanged(num);
                }
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public KeyboardNumUtil(Context mContext, KeyboardView keyboardView){
        this.mContext = mContext;
        this.mKeyboardView = keyboardView;
        this.mInputMethodManager = ((InputMethodManager)this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    public void  attachEditText(EditText editText){
        this.mEditText = editText;
    }

    /**
     * 展示keyboard
     */
    public void showNumKeyboard(){
        mKeyboardView.setKeyboard(new Keyboard(this.mContext, R.xml.keyboard_num_woith_dot));
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(this.mOnKeyboardActionListener);
    }


    /**
     * 隐藏系统的软键盘
     */
    private void hideSystemKeyboard(){
        Method localMethod = null;
        try {
            localMethod = EditText.class.getMethod("setShowSoftInputOnFocus", Boolean.TYPE);
            localMethod.setAccessible(true);
            try {
                localMethod.invoke(this.mEditText, Boolean.FALSE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.mInputMethodManager.hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setNumKeyboardListener(NumKeyboardListener numKeyboardListener){
        this.mNumKeyboardListener = numKeyboardListener;
    }


    public  interface NumKeyboardListener{
        void onClose();
        void onConfirm();
        void onNumberChanged(String paramString);
    }
}
