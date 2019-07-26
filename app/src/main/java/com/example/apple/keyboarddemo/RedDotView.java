package com.example.apple.keyboarddemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Created by apple on 2019/7/25.
 * description:
 */
public class RedDotView extends AppCompatTextView {
    public RedDotView(Context context) {
        this(context,null);
    }

    public RedDotView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RedDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        setTextView();
    }

    private void setTextView() {
        setTextColor(Color.WHITE);
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        setPadding(dip2Px(5), dip2Px(1), dip2Px(5), dip2Px(1));
        setBackground(9, Color.parseColor("#f14850"));
        setGravity(Gravity.CENTER);
    }

    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 设置背景颜色
     * @param dipRadius                 半径
     * @param badgeColor                颜色
     */
    private void setBackground(int dipRadius, int badgeColor) {
        int radius = dip2Px(dipRadius);
        float[] radiusArray = new float[]{radius, radius, radius, radius,
                radius, radius, radius, radius};
        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(bgDrawable);
        }
    }

    /**
     * 设置红点的数字
     * @param count                 数字
     */
    public void setBadgeCount(int count) {
        //设置参数，超过99显示99+
        if (count == 0){
            setVisibility(GONE);
            return;
        }
        if (getVisibility() == GONE){
            setVisibility(VISIBLE);
        }
        setBadgeCount(count > 99 ? "99+" : String.valueOf(count));
    }

    private void setBadgeCount(String count) {
        setText(count);
    }
}
