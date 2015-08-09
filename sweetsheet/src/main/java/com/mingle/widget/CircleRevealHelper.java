package com.mingle.widget;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.mingle.SimpleAnimationListener;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 *
 */
public class CircleRevealHelper {


    public CircleRevealHelper(View view) {
        mView = view;

        if (view instanceof CircleRevealEnable) {
            mCircleRevealEnable = (CircleRevealEnable) view;
        } else {
            throw new RuntimeException("the View must implements CircleRevealEnable ");
        }

    }

    private Path mPath = new Path();

    private View mView;

    private int mAnchorX, mAnchorY;
    private float mRadius;

    private boolean isCircularReveal = false;

    private CircleRevealEnable mCircleRevealEnable;



    public void circularReveal(int centerX, int centerY, float startRadius, float endRadius) {

        this.circularReveal(centerX,centerY,startRadius,endRadius,700,new AccelerateDecelerateInterpolator());


    }



    public void circularReveal(int centerX, int centerY, float startRadius, float endRadius, long duration, Interpolator interpolator) {

        mAnchorX = centerX;
        mAnchorY = centerY;
        if (Build.VERSION.SDK_INT >= 21) {
            Animator animator = ViewAnimationUtils.createCircularReveal(
                    mView,
                    mAnchorX,
                    mAnchorY,
                    startRadius,
                    endRadius);
            animator.setInterpolator(interpolator);
            animator.setDuration(duration);
            animator.start();
        } else {

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startRadius, endRadius);

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRadius = (float) animation.getAnimatedValue();

                    mView.invalidate();
                }
            });
            valueAnimator.setInterpolator(interpolator);
            valueAnimator.addListener(new SimpleAnimationListener() {
                @Override
                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                    isCircularReveal = true;
                }

                @Override
                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    isCircularReveal = false;
                }

                @Override
                public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {
                    isCircularReveal = false;
                }

            });
            valueAnimator.setDuration(duration);
            valueAnimator.start();
        }

    }


    protected void onDraw(Canvas canvas) {

        if (isCircularReveal) {
            canvas.save();
            canvas.translate(0, 0);
            mPath.reset();
            mPath.addCircle(mAnchorX, mAnchorY, mRadius, Path.Direction.CCW);
            canvas.clipPath(mPath, Region.Op.REPLACE);
            mCircleRevealEnable.superOnDraw(canvas);
            canvas.restore();
        } else {
            mCircleRevealEnable.superOnDraw(canvas);
        }
    }


    public interface CircleRevealEnable {


        void superOnDraw(Canvas canvas);

        void circularReveal(int centerX, int centerY, float startRadius, float endRadius, long duration, Interpolator interpolator);

        void circularReveal(int centerX, int centerY, float startRadius, float endRadius);
    }

}
