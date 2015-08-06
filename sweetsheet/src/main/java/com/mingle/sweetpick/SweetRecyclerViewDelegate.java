package com.mingle.sweetpick;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;

import com.mingle.adapter.MenuRVAdapter;
import com.mingle.entity.MenuEntity;

import com.mingle.sweetsheet.R;
import com.mingle.widget.SweetView;

import java.util.List;
/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 *
 */
public class SweetRecyclerViewDelegate extends Delegate implements SweetView.AnimationListener {

    private SweetView mSweetView;
    private RecyclerView mRV;
    private MenuRVAdapter mMenuRVAdapter;


    @Override
    public View createView() {

        View rootView = LayoutInflater.from(mParentVG.getContext())
                .inflate(R.layout.layout_rv_sweet, null, false);

        mSweetView = (SweetView) rootView.findViewById(R.id.sv);
        mRV = (RecyclerView) rootView.findViewById(R.id.rv);
        mRV.setLayoutManager(new LinearLayoutManager(mParentVG.getContext(), LinearLayoutManager.VERTICAL, false));
        mSweetView.setAnimationListener(this);

        return rootView;
    }

    @Override
    public void onStart() {

        mStatus = SweetSheet.Status.SHOWING;
        mRV.setVisibility(View.GONE);
    }

    @Override
    public void onEnd() {
        mStatus = SweetSheet.Status.SHOW;
    }

    @Override
    public void onContentShow() {

        mRV.setVisibility(View.VISIBLE);
        mRV.setAdapter(mMenuRVAdapter);
        mRV.scheduleLayoutAnimation();
    }

    public void setMenuList(FragmentManager fragmentManager, List<MenuEntity> menuEntities) {

        mMenuRVAdapter = new MenuRVAdapter(menuEntities, SweetSheet.Type.RecyclerView);
        mRV.setAdapter(mMenuRVAdapter);
        mMenuRVAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mOnMenuItemClickListener != null) {
                    if (mOnMenuItemClickListener.onItemClick(position)) {
                        delayedDismiss();
                    }
                }
            }
        });
        mRV.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mRV.setEnabled(false);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRV.setEnabled(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }




    public void show() {
        super.show();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(mRootView, lp);
        mSweetView.show();
    }


}
