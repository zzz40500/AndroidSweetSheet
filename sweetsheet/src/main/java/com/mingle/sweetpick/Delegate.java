package com.mingle.sweetpick;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.mingle.SimpleAnimationListener;
import com.mingle.entity.MenuEntity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public abstract class Delegate implements View.OnClickListener {


    protected SweetSheet.Status mStatus = SweetSheet.Status.DISMISS;

    protected ViewGroup mParentVG;
    protected View mRootView;
    private View mBg;

    protected SweetSheet.OnMenuItemClickListener mOnMenuItemClickListener;

    public void init(ViewGroup parentVG) {
        mParentVG = parentVG;
        mBg = new View(parentVG.getContext());
        mRootView = createView();
        mBg.setOnClickListener(this);
    }

    protected abstract View createView();

    protected abstract void setMenuList(FragmentManager fragmentManager, List<MenuEntity> list);

    protected void toggle() {

        switch (mStatus) {

            case SHOW:
                dismiss();
                break;

            case DISMISS:
                show();
                break;

            default:
                break;
        }
    }

    protected void show() {

        if (getStatus() != SweetSheet.Status.DISMISS) {
            return;
        }
        mBg.setClickable(true);
        showShowdown();

    }


    protected void showShowdown() {

        ViewHelper.setTranslationY(mRootView, 0);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(mBg, lp);
        ViewHelper.setAlpha(mBg, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 0, 1);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }

    protected void dismissShowdown() {


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 1, 0);
        objectAnimator.setDuration(400);
        objectAnimator.start();
        objectAnimator.addListener(new SimpleAnimationListener() {

            @Override
            public void onAnimationEnd(Animator animation) {

                mParentVG.removeView(mBg);
            }


        });
    }


    public void dismiss() {
        if (getStatus() != SweetSheet.Status.SHOW) {
            return;
        }
        mBg.setClickable(false);
        dismissShowdown();

        ObjectAnimator translationOut = ObjectAnimator.ofFloat(mRootView,
                "translationY", 0, mRootView.getHeight());
        translationOut.setDuration(600);
        translationOut.setInterpolator(new DecelerateInterpolator());
        translationOut.addListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mStatus =
                        SweetSheet.Status.DISMISSING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = SweetSheet.Status.DISMISS;
                mParentVG.removeView(mRootView);
            }

        });
        translationOut.start();


    }

    protected void setBackgroundDim(float dim) {

        mBg.setBackgroundColor(Color.argb((int) (150 * dim), 150, 150, 150));

    }

    public void setOnMenuItemClickListener(SweetSheet.OnMenuItemClickListener onItemClickListener) {
        mOnMenuItemClickListener = onItemClickListener;
    }


    protected void delayedDismiss() {


        mParentVG.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 300);
    }

    public SweetSheet.Status getStatus() {
        return mStatus;
    }

    @Override
    public void onClick(View v) {
        dismiss();


    }
}
