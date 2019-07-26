package com.example.apple.keyboarddemo.expand;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.apple.keyboarddemo.R;

/**
 * Created by apple on 2019/7/23.
 * description: 可折叠的TextView
 */
public class ExpandTextView extends LinearLayout implements View.OnClickListener {
    private TextView mTvContent,mTvExpand;

    /*是否有重新绘制*/
    private boolean mRelayout;

    /*默认收起*/
    private boolean mCollapsed = true;

    /*动画执行时间*/
    private int mAnimationDuration = 300;
    /*是否正在执行动画*/
    private boolean mAnimating;

    /*设置内容最大行数，超过隐藏*/
    private int mMaxCollapsedLines = 2;

    /*这个linerlayout容器的高度*/
    private int mCollapsedHeight;

    /*内容tv真实高度（含padding）*/
    private int mTextHeightWithMaxLines;

    /*内容tvMarginTopAmndBottom高度*/
    private int mMarginBetweenTxtAndBottom;

    /*展开图片*/
    private Drawable mExpandDrawable;
    /*收起图片*/
    private Drawable mCollapseDrawable;

    public ExpandTextView(Context context) {
        this(context,null);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 设置orientation 必须为垂直
     * @param orientation
     */
    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.HORIZONTAL == orientation) {
            throw new IllegalArgumentException("ExpandableTextView only supports Vertical Orientation.");
        }
        super.setOrientation(orientation);
    }

    /**
     * 依据参数属性 设置排列方式
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        Log.e("expand", "init: 初始化了" );
        mExpandDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_expand_down);
        mExpandDrawable.setBounds(0,0,25,25);
        mCollapseDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_expand_up);
        mCollapseDrawable.setBounds(0,0,25,25);
        setOrientation(VERTICAL);
        // 在加载前设置不可见...
        setVisibility(GONE);
    }

    /**
     * 渲染完成后初始化view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 如果没有改变则返回
        if (!mRelayout || getVisibility() == View.GONE){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;
        mTvExpand.setVisibility(GONE);
        mTvContent.setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果内容真实行数小于等于最大行数，不处理
        if (mTvContent.getLineCount() <= mMaxCollapsedLines) {
            return;
        }
        // 获取内容tv真实高度（含padding）进行保存
        mTextHeightWithMaxLines = getRealTextViewHeight(mTvContent);

        // 如果是收起状态，重新设置最大行数
        if (mCollapsed) {
            mTvContent.setMaxLines(mMaxCollapsedLines);
        }
        mTvExpand.setVisibility(View.VISIBLE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            mTvContent.post(new Runnable() {
                @Override
                public void run() {
                    mMarginBetweenTxtAndBottom = getHeight() - mTvContent.getHeight();
                }
            });
            // 保存这个容器的测量高度
            mCollapsedHeight = getMeasuredHeight();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 当动画还在执行状态时，拦截事件，不让child处理
        return mAnimating;
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.textview_expand_shink,this);
        mTvContent = findViewById(R.id.expand_content);
        mTvContent.setOnClickListener(this);
        mTvExpand = findViewById(R.id.expand_collapse);
        mTvExpand.setText(mCollapsed ? "全文" : "收起");
        mTvExpand.setCompoundDrawables(null, null, mCollapsed ? mExpandDrawable : mCollapseDrawable, null);
        mTvExpand.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mTvExpand.getVisibility() != VISIBLE){
            return;
        }
        mCollapsed = !mCollapsed;
        mTvExpand.setText(mCollapsed ? "全文" : "收起");
        mTvExpand.setCompoundDrawables(null, null, mCollapsed ? mExpandDrawable : mCollapseDrawable, null);
        // 执行展开/收起动画
        mAnimating = true;
        ValueAnimator valueAnimator;
        if (mCollapsed) {
            valueAnimator = ValueAnimator.ofInt(getHeight(), mCollapsedHeight);
        } else {
            valueAnimator = ValueAnimator.ofInt(getHeight(), getHeight() +
                    mTextHeightWithMaxLines - mTvContent.getHeight());
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                mTvContent.setMaxHeight(animatedValue - mMarginBetweenTxtAndBottom);
                getLayoutParams().height = animatedValue;
                requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                // 动画结束后发送结束的信号
                /// clear the animation flag
                mAnimating = false;
                if (mCollapsed){
                    mTvContent.setMaxLines(mMaxCollapsedLines);
                    mTvContent.setEllipsize(TextUtils.TruncateAt.END);
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setDuration(mAnimationDuration);
        valueAnimator.start();
    }



    /**
     * 设置内容
     * @param text
     */
    public void setText( CharSequence text) {
        mRelayout = true;
        mTvContent.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /**
     * 获取内容
     * @return
     */
    public CharSequence getText() {
        if (mTvContent == null) {
            return "";
        }
        return mTvContent.getText();
    }

    /**
     * 获取内容tv真实高度（含padding）
     * @param textView
     * @return
     */
    private static int getRealTextViewHeight( TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }
}
