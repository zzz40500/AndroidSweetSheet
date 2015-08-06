package com.mingle.sweetpick;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingle.adapter.ViewpagerAdapter;
import com.mingle.entity.MenuEntity;
import com.mingle.fragment.MenuListFragment;
import com.mingle.sweetsheet.R;
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
public class SweetViewPagerDelegate extends Delegate implements SweetView.AnimationListener, MenuListFragment.OnFragmentInteractionListener {

    private ArrayList<Fragment> mFragments;
    private IndicatorView mIndicatorView;
    private ViewPager mViewPager;
    private SweetView mSweetView;
    private MenuListFragment mMenuListFragment;
    private SweetSheet.OnMenuItemClickListener mOnMenuItemClickListener;

    @Override
    public View createView() {
        View rootView = LayoutInflater.from(mParentVG.getContext()).inflate(R.layout.layout_vp_sweet, null, false);
        mSweetView = (SweetView) rootView.findViewById(R.id.sv);
        mIndicatorView = (IndicatorView) rootView.findViewById(R.id.indicatorView);
        mIndicatorView.alphaDismiss(false);
        mSweetView.setAnimationListener(this);
        mViewPager = (ViewPager) rootView.findViewById(R.id.vp);
        return rootView;
    }

    @Override
    public void onStart() {
        mStatus = SweetSheet.Status.SHOWING;
        mIndicatorView.alphaDismiss(false);
        if (mMenuListFragment != null) {
            mMenuListFragment.animationOnStart();
        }

    }

    @Override
    public void onEnd() {
        mStatus = SweetSheet.Status.SHOW;
        mIndicatorView.alphaShow(true);

    }

    @Override
    public void onContentShow() {

        if (mMenuListFragment != null) {
            mMenuListFragment.notifyAnimation();
        }
    }


    public void setMenuList(FragmentManager fragmentManager, List<MenuEntity> menuEntities) {

        mFragments = new ArrayList<>();
        int fragmentCount = menuEntities.size() / 6;
        if (menuEntities.size() % 6 != 0) {
            fragmentCount += 1;
        }
        for (int i = 0; i < fragmentCount; i++) {

            int lastIndex = Math.min((i + 1) * 6, menuEntities.size());
            ArrayList<MenuEntity> temps = new ArrayList<>();
            for (int x = i * 6; x < lastIndex; x++) {
                temps.add(menuEntities.get(x));
            }

            MenuListFragment menuListFragment = MenuListFragment.getInstant
                    (i, temps);
            menuListFragment.setOnMenuItemClickListener(this);

            mFragments.add(menuListFragment);
        }
        mViewPager.setAdapter(new ViewpagerAdapter(fragmentManager, mFragments, null));
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


    public void show() {
        super.show();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(mRootView, lp);
        mSweetView.show();
    }


    @Override
    public void setOnMenuItemClickListener(SweetSheet.OnMenuItemClickListener onItemClickListener) {
        mOnMenuItemClickListener = onItemClickListener;

    }


    private void selectPosition(int position) {
        mMenuListFragment = (MenuListFragment) mFragments.get(position);
    }

    @Override
    public void onFragmentInteraction(int index) {

        if (mOnMenuItemClickListener != null) {
            if (mOnMenuItemClickListener.onItemClick(index)) {
               delayedDismiss();
            }
        }


    }
}
