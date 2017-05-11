package com.example.aedvance.finalcoins.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.aedvance.finalcoins.R;

/**
 * Created by eric.zhang on 2016/6/23.
 */
public class RippleButtonView extends android.support.v7.widget.AppCompatButton {

    int[] mLocation = new int[2];
    float mCenterX;
    float mCenterY;
    float mRevealRadius;
    boolean isInterest = false;
    boolean mShouldDoAnimation = false;
    Paint mPaint = new Paint();

    int colorDark, colorLight;

    public RippleButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        colorDark = ContextCompat.getColor(getContext(), R.color.colorDark);
        colorLight = ContextCompat.getColor(getContext(), R.color.colorLight);
    }

    private boolean isValidClick(float x, float y) {
        if (x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                mCenterX = event.getX();
                mCenterY = event.getY();
                mRevealRadius = 0f;

                mShouldDoAnimation = true;
                setFollowed(!isInterest, mShouldDoAnimation);
                return true;
        }
        return false;
    }

    public void setFollowed(boolean isFollowed, boolean needAnimate) {
        isInterest = isFollowed;
        if (needAnimate) {
            ValueAnimator animator = ObjectAnimator.ofFloat(this, "empty", 0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            animator.setDuration(500L);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRevealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }

            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (!isInterest) {
                        setTextColor(Color.WHITE);
                        setBackgroundColor(colorLight);
                        setText("+关注");
                    } else {
                        setTextColor(Color.WHITE);
                        setBackgroundColor(colorDark);
                        setText("已关注");
                    }
                    mShouldDoAnimation = false;
                    mRevealRadius = 0;
                    invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInterest) {
            mPaint.setColor(colorDark);
        } else {
            mPaint.setColor(colorLight);
        }//设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);

    }

}
