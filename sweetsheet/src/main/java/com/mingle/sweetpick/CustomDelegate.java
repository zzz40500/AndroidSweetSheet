package com.mingle.sweetpick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetsheet.R;
import com.mingle.widget.CRImageView;
import com.mingle.widget.FreeGrowUpParentRelativeLayout;
import com.mingle.widget.SweetView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 */
public class CustomDelegate extends Delegate {


    private SweetView mSweetView;
    private RelativeLayout mContentRL;
    private CRImageView sliderIm;

    private FreeGrowUpParentRelativeLayout mFreeGrowUpParentRelativeLayout;

    private boolean mIsDragEnable;

    private View mContentView;

    private ViewGroup mAnimationView;
    private AnimationType mContentViewAnimationType;

    private ValueAnimator mContentViewValueAnimation;

    private int mContentViewHeight;
    private int sweetSheetColor;


    public CustomDelegate(boolean dragEnable, AnimationType contentViewAnimationType) {
        mIsDragEnable = dragEnable;
        mContentViewAnimationType = contentViewAnimationType;
    }

    public CustomDelegate(boolean dragEnable, AnimationType contentViewAnimationType, int contentViewHeight) {
        mContentViewHeight = contentViewHeight;
        mContentViewAnimationType = contentViewAnimationType;
        mIsDragEnable = dragEnable;
    }

    @Override
    public View createView() {

        View rootView = LayoutInflater.from(mParentVG.getContext())
                .inflate(R.layout.layout_custom_sweet, null, false);

        mSweetView = (SweetView) rootView.findViewById(R.id.sv);
	mSweetView.setSweetSheetColor(sweetSheetColor);
        mFreeGrowUpParentRelativeLayout = (FreeGrowUpParentRelativeLayout) rootView.findViewById(R.id.freeGrowUpParentF);
        mContentRL = (RelativeLayout) rootView.findViewById(R.id.rl);
        sliderIm = (CRImageView) rootView.findViewById(R.id.sliderIM);
        mSweetView.setAnimationListener(new AnimationImp());


        if (mContentView != null) {
            mContentRL.removeAllViews();
            mContentRL.addView(mContentView);
        }
        if (mContentViewAnimationType == AnimationType.DuangLayoutAnimation) {
            mAnimationView = null;
            if (mContentView instanceof ViewGroup) {
                mAnimationView = (ViewGroup) mContentView;
            } else {
                mAnimationView = mContentRL;
            }
            Animation animation = AnimationUtils.loadAnimation(mContentRL.getContext(),
                    R.anim.item_duang_show);

            LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
            layoutAnimationController.setDelay(0.1f);
            mAnimationView.setLayoutAnimation(layoutAnimationController);
        }

        if(mContentViewHeight > 0){
            mFreeGrowUpParentRelativeLayout.setContentHeight(mContentViewHeight);
        }

        return rootView;
    }

    protected void setMenuList(final List<MenuEntity> menuEntities) {

    }

    public  CustomDelegate setContentHeight(int height){

        if(height >0 && mFreeGrowUpParentRelativeLayout != null){
            mFreeGrowUpParentRelativeLayout.setContentHeight(height);
        }else{
            mContentViewHeight=height;
        }
        return this;

    }

    public RelativeLayout getContentRelativeLayout() {
        return mContentRL;
    }


    protected void show() {
        super.show();
        ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (mRootView.getParent() != null) {
            mParentVG.removeView(mRootView);
        }
        mParentVG.addView(mRootView, lp);
        mSweetView.show();
    }


    public CustomDelegate setCustomView(View view) {

        if (mContentRL != null) {
            mContentRL.removeAllViews();
            mContentRL.addView(view);
        } else {
            mContentView = view;
        }

        return this;
    }

    public CustomDelegate setCustomView(View view, RelativeLayout.LayoutParams params) {
        if (mContentRL != null) {
            mContentRL.removeAllViews();
            mContentRL.addView(view, params);
        } else {
            mContentView = view;
            mContentView.setLayoutParams(params);
        }

        return this;
    }


    /**
     * 当 AnimationType = Custom 时候需要传递一个动画进来.
     */
    public CustomDelegate setCustomViewAnimation(ValueAnimator viewAnimation) {

        mContentViewValueAnimation = viewAnimation;
        return this;
    }

    class AnimationImp implements SweetView.AnimationListener {

        @Override
        public void onStart() {
            mFreeGrowUpParentRelativeLayout.reset();
            mStatus = SweetSheet.Status.SHOWING;
            sliderIm.setVisibility(View.INVISIBLE);
            mContentRL.setVisibility(View.GONE);

        }

        @Override
        public void onEnd() {


            if (mStatus == SweetSheet.Status.SHOWING) {
                mStatus = SweetSheet.Status.SHOW;

                if(mIsDragEnable) {
                    sliderIm.setVisibility(View.VISIBLE);
                    sliderIm.circularReveal(sliderIm.getWidth() / 2, sliderIm.getHeight() / 2, 0, sliderIm.getWidth());
                }
            }

        }

        @Override
        public void onContentShow() {

            mContentRL.setVisibility(View.VISIBLE);

            setContentViewAnimation();
            mContentRL.scheduleLayoutAnimation();
        }
    }

    private void setContentViewAnimation() {

        switch (mContentViewAnimationType) {
            case DuangLayoutAnimation:
                duangLayoutAnimation();
                break;
            case DuangAnimation:
                duangAnimation();
                break;
            case AlphaAnimation:
                alphaAnimation();
                break;
            case Custom:
                if (mContentViewValueAnimation != null) {
                    mContentViewValueAnimation.start();
                } else {
                    throw new RuntimeException("you must invoke setCustomViewAnimation before ");
                }
                break;
        }
    }

    private void alphaAnimation() {


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getContentRelativeLayout(), "alpha", 0, 1);
        objectAnimator.setDuration(1200);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();


    }

    private void duangAnimation() {

        Animation animation = AnimationUtils.loadAnimation(mContentRL.getContext(),
                R.anim.item_duang_show2);
        getContentRelativeLayout().startAnimation(animation);

    }

    private void duangLayoutAnimation() {


        mAnimationView.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFreeGrowUpParentRelativeLayout.setClipChildren(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFreeGrowUpParentRelativeLayout.setClipChildren(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAnimationView.scheduleLayoutAnimation();

    }


    /**
     * 定义了中间视图的动画类型,为四种
     */
    public enum AnimationType {
        //这个用 LayoutAnimation 方式实现的
        DuangLayoutAnimation,
        //这个是对这个 View 进行动画
        DuangAnimation,
        //这个是对这个 View 进行动画,它是简单额alpha 值变化
        AlphaAnimation,
        //自定义的动画,需要调用
        Custom
    }

    public void setSweetSheetColor(int sweetSheetColor) {
        this.sweetSheetColor = sweetSheetColor;
    }
}
