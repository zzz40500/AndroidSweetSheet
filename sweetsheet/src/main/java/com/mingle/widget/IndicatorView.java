package com.mingle.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import com.mingle.sweetsheet.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 *
 */
public class IndicatorView extends LinearLayout implements ViewPager.OnPageChangeListener,CircleRevealHelper.CircleRevealEnable {

    private ViewPager mViewPager;
    private int mPreSelectPosition=-1;
    private List<View> mIndicators = new ArrayList<>();
    private CircleRevealHelper mCircleRevealHelper;

    public IndicatorView(Context context) {
        super(context);
        init();

    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }
    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);

        mCircleRevealHelper=new CircleRevealHelper(this);
    }


    public void setViewPager(ViewPager viewPager) {

        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        initIndicatorView();
    }

    private void initIndicatorView() {
        this.removeAllViews();
        mIndicators.clear();
        int count = mViewPager.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            addIndicationView();
        }
        setSelect(mViewPager.getCurrentItem());

    }

    private void setSelect(int i) {
        View view = mIndicators.get(i);
        view.setSelected(true);
        if(mPreSelectPosition != -1){
            mIndicators.get(mPreSelectPosition).
            setSelected(false);
        }
        mPreSelectPosition=i;
    }

    private void addIndicationView() {

        View indicatorView = new View(getContext());
        indicatorView.setBackgroundResource(R.drawable.indicator);
        int width = getResources().getDimensionPixelSize(R.dimen.indicator_width);
        int height = getResources().getDimensionPixelSize(R.dimen.indicator_height);
        int padding = getResources().getDimensionPixelSize(R.dimen.indicator_padding);
        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.leftMargin = padding;
        layoutParams.rightMargin = padding;
        this.addView(indicatorView, layoutParams);
        mIndicators.add(indicatorView);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        setSelect( position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void alphaShow(boolean isAnimation){

        if(isAnimation) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0, 1);
            objectAnimator.setDuration(300);
            objectAnimator.start();
        }else{
            ViewHelper.setAlpha(this,1);
        }
    }


    public void alphaDismiss(boolean isAnimation){
        if(isAnimation) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1, 0);
            objectAnimator.setDuration(300);
            objectAnimator.start();
        }else{
            ViewHelper.setAlpha(this,0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCircleRevealHelper.onDraw(canvas);
    }

    @Override
    public void superOnDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void circularReveal(int centerX, int centerY, float startRadius, float endRadius, long duration, Interpolator interpolator) {
        mCircleRevealHelper.circularReveal(centerX,centerY,startRadius,endRadius,duration,interpolator);
    }

    @Override
    public void circularReveal(int centerX, int centerY, float startRadius, float endRadius) {
        mCircleRevealHelper.circularReveal(centerX,centerY,startRadius,endRadius);
    }

}
