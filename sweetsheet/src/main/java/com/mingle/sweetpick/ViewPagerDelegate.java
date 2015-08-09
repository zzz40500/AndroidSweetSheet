package com.mingle.sweetpick;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import com.mingle.adapter.ViewpagerAdapter;
import com.mingle.entity.MenuEntity;
import com.mingle.viewhandler.MenuListViewHandler;
import com.mingle.sweetsheet.R;
import com.mingle.widget.FreeGrowUpParentRelativeLayout;
import com.mingle.widget.IndicatorView;
import com.mingle.widget.SweetView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 *
 */
public class ViewPagerDelegate extends Delegate {

    private ArrayList<MenuListViewHandler> mMenuListViewHandlers;
    private IndicatorView mIndicatorView;
    private ViewPager mViewPager;
    private SweetView mSweetView;
    private MenuListViewHandler mMenuListViewHandler;
    private FreeGrowUpParentRelativeLayout mFreeGrowUpParentRelativeLayout;
    private SweetSheet.OnMenuItemClickListener mOnMenuItemClickListener;
    private  List<MenuEntity> mMenuEntities;

    private int mNumColumns=3;
    private int mContentViewHeight;

    public ViewPagerDelegate() {
    }
    public ViewPagerDelegate(int numColumns ) {
        mNumColumns=numColumns;
    }
    public ViewPagerDelegate(int numColumns  ,int contentViewHeight) {
        mNumColumns=numColumns;
        mContentViewHeight=contentViewHeight;
    }

    @Override
    protected View createView() {
        View rootView = LayoutInflater.from(mParentVG.getContext()).inflate(R.layout.layout_vp_sweet, null, false);
        mSweetView = (SweetView) rootView.findViewById(R.id.sv);
        mFreeGrowUpParentRelativeLayout = (FreeGrowUpParentRelativeLayout) rootView.findViewById(R.id.freeGrowUpParentF);

        mIndicatorView = (IndicatorView) rootView.findViewById(R.id.indicatorView);
        mIndicatorView.alphaDismiss(false);
        mSweetView.setAnimationListener(new AnimationImp());
        mViewPager = (ViewPager) rootView.findViewById(R.id.vp);

        if(mContentViewHeight > 0){
            mFreeGrowUpParentRelativeLayout.setContentHeight(mContentViewHeight);
        }

        return rootView;
    }



    public  ViewPagerDelegate setContentHeight(int height){

        if(height >0 && mFreeGrowUpParentRelativeLayout != null){
            mFreeGrowUpParentRelativeLayout.setContentHeight(height);
        }else{
            mContentViewHeight=height;
        }
        return this;

    }


    protected void setMenuList(  List<MenuEntity> menuEntities) {

        mMenuEntities=menuEntities;
        mMenuListViewHandlers = new ArrayList<>();
        int fragmentCount = menuEntities.size() / (mNumColumns*2);
        if (menuEntities.size() % (mNumColumns*2) != 0) {
            fragmentCount += 1;
        }
        for (int i = 0; i < fragmentCount; i++) {

            int lastIndex = Math.min((i + 1) * (mNumColumns*2), menuEntities.size());
            MenuListViewHandler menuListViewHandler = MenuListViewHandler.getInstant
                    (i,mNumColumns, menuEntities.subList(i*(mNumColumns*2),lastIndex));
            menuListViewHandler.setOnMenuItemClickListener(new OnFragmentInteractionListenerImp());
            mMenuListViewHandlers.add(menuListViewHandler);
        }
        mViewPager.setAdapter(new ViewpagerAdapter(mMenuListViewHandlers));
        mIndicatorView.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectPosition(0);

    }


    protected void show() {
        super.show();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(mRootView, lp);
        mSweetView.show();
    }


    @Override
    protected void setOnMenuItemClickListener(SweetSheet.OnMenuItemClickListener onItemClickListener) {
        mOnMenuItemClickListener = onItemClickListener;

    }


    private void selectPosition(int position) {
        mMenuListViewHandler = (MenuListViewHandler) mMenuListViewHandlers.get(position);
    }



    class OnFragmentInteractionListenerImp implements  MenuListViewHandler.OnFragmentInteractionListener{

        @Override
        public void onFragmentInteraction(int index) {
            if (mOnMenuItemClickListener != null) {
                mMenuEntities.get(index);
                if (mOnMenuItemClickListener.onItemClick(index,mMenuEntities.get(index))) {
                    delayedDismiss();
                }
            }
        }
    }

    class AnimationImp implements SweetView.AnimationListener {

        @Override
        public void onStart() {
            mStatus = SweetSheet.Status.SHOWING;
            mIndicatorView.setVisibility(View.INVISIBLE);
            if (mMenuListViewHandler != null) {
                mMenuListViewHandler.animationOnStart();
            }

        }

        @Override
        public void onEnd() {
            mStatus = SweetSheet.Status.SHOW;
            mIndicatorView.alphaShow(true);

            mIndicatorView.setVisibility(View.VISIBLE);

            mIndicatorView.circularReveal(
                    mIndicatorView.getWidth() / 2,
                    mIndicatorView.getHeight() / 2,
                    0,
                    mIndicatorView.getWidth(), 2000, new DecelerateInterpolator());

        }

        @Override
        public void onContentShow() {

            if (mMenuListViewHandler != null) {
                mMenuListViewHandler.notifyAnimation();
            }
        }
    }

}
