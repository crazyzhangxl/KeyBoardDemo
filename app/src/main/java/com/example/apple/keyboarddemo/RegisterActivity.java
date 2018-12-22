package com.example.apple.keyboarddemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.apple.keyboarddemo.shelter_aty.ShelterActivity;

/**
 * @author crazyZhangxl on 2018-12-22 13:27:56.
 * Describe:
 */

public class RegisterActivity extends AppCompatActivity {
    private Button mBtn;
    private LinearLayout mLinearLayout;
    private int mButtonHeight;
    private ViewTreeObserver mTreeObserver;
    private ViewTreeObserver.OnGlobalLayoutListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initListener();
    }

    private void initListener() {

        findViewById(R.id.ivToolbarNavigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoft();
                startActivity(new Intent(RegisterActivity.this, ShelterActivity.class));
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        buttonBeyondKeyboardLayout(mLinearLayout,mBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initViews() {
        mLinearLayout = findViewById(R.id.content);
        mBtn = findViewById(R.id.btnNext);

    }

    /**
     * 移动的布局
     * @param root     需要移动的布局
     * @param button   最底部的布局
     */
    private void buttonBeyondKeyboardLayout(final  View root,final View button) {
        mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("测试", "-------统计测量------" );
                Rect rect = new Rect();
                // 获得当前窗口的可视区域的大小
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                // 当前窗口的高度 - 可见区域的底部 = 不可见窗体的大小[即软件盘的高度]
                int rootInvisibleHeight = getWindow().getDecorView().getHeight() - rect.bottom;

                // 事件为弹出软键盘时，软件盘至少有100px吧 这里100可以替换为软件盘的高度
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    // 获得按钮即所要检测的视图的高
                    button.getLocationInWindow(location);
                    // 按钮的底部------
                    int buttonHeight = button.getHeight() + location[1];
                    // **************  判断是否需要进行移动 **************
                    if (rect.bottom > buttonHeight) {
                        // 如果不需要移动那么直接移除
                        if (mListener != null && mTreeObserver.isAlive()) {
                            mTreeObserver.removeOnGlobalLayoutListener(this);
                        }
                        mListener = null;
                    } else {
                        // 计算向上移动的高度
                        mButtonHeight = (buttonHeight - rect.bottom + dpToPx(20));
                        Log.e("软键盘", "onGlobalLayout:   = "+mButtonHeight);
                        // 注意scroll,移动的是内容
                        root.scrollTo(0, mButtonHeight);
                    }
                } else {
                    // 软键盘隐藏进行还原
                    root.scrollTo(0, 0);
                }
            }
        };
        mTreeObserver = root.getViewTreeObserver();
        mTreeObserver.addOnGlobalLayoutListener(mListener);
    }

    @Override
    protected void onDestroy() {
        if (mListener != null && mTreeObserver != null && mTreeObserver.isAlive()) {
            mTreeObserver.removeOnGlobalLayoutListener(mListener);
        }
        super.onDestroy();
    }

    private void hideSoft(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘 imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        // 延迟个0.1秒是因为 可能推出太快导致软键盘刚刚被回收，前面的界面出现断层
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int dpToPx(int dp){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(displayMetrics.density*dp);
    }

    /**
     * 用于测试是否弹出dialog会重新绘制布局
     *
     * ------------- 经过测试是不会影响布局的[可以放心的使用] ----------
     */
    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("测试标题")
                .setMessage("只是用于测试弹出框是否影响布局重绘制")
                .setNegativeButton("取消", null)
                .setPositiveButton("对", null)
                .create();
        alertDialog.show();
    }
}
