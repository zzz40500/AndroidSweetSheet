package com.mingle.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mingle.sweetsheet.R;


/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/8.
 * @github: https://github.com/zzz40500
 *
 */
public class FreeGrowUpParentRelativeLayout extends RelativeLayout implements GrowUpParent {


    private float downY;
    private int originalHeight = -1;

    private int parentHeight;
    private int currentHeight;


    private boolean isGrowUp = true;

    private int mOffset =-1;

    public FreeGrowUpParentRelativeLayout(Context context) {
        super(context);
    }

    public FreeGrowUpParentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray=getContext().obtainStyledAttributes(attrs,
                R.styleable.FreeGrowUpParentRelativeLayout);
        mOffset=  typedArray.getDimensionPixelOffset(R.styleable.FreeGrowUpParentRelativeLayout_offset,-1);
        typedArray.recycle();
    }

    public FreeGrowUpParentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FreeGrowUpParentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private boolean checkGrowUpEnable() {
        if (getParent() != null && getParent() instanceof ViewGroup && isGrowUp) {
            parentHeight = ((ViewGroup) getParent()).getHeight();
            return true;
        } else {
            return false;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    private void setOriginalHeight() {
        if (originalHeight == -1) {
            originalHeight = getHeight();
        }
        currentHeight = getHeight();
    }

    private void changeLayoutParamsHeight(int distance) {
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        int height= Math.min(currentHeight + distance, parentHeight+mOffset);

        if(height <originalHeight){
//            height= (int) (originalHeight-(originalHeight-height)*0.05);
            height= originalHeight;
        }
        layoutParams.height =height;
        requestLayout();
    }

    public boolean isGrowUp() {
        return isGrowUp;
    }

    public void setIsGrowUp(boolean isGrowUp) {
        this.isGrowUp = isGrowUp;
    }

    public void reset() {

        if (originalHeight != -1) {
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.height = originalHeight;
            requestLayout();
        }
    }

    @Override
    public boolean onParentHandMotionEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (!checkGrowUpEnable()) {
                    return false;
                }
                downY = event.getRawY();
                setOriginalHeight();
                break;

            case MotionEvent.ACTION_MOVE:
                int distance = (int) (downY - event.getRawY());
                changeLayoutParamsHeight(distance);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }

    public void setContentHeight(int contentHeight){

        originalHeight=-1;
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        layoutParams.height = contentHeight+getContext().getResources().getDimensionPixelOffset(R.dimen.arc_max_height);

        requestLayout();


    }

}
