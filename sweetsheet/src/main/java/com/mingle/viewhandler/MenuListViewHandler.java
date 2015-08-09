package com.mingle.viewhandler;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mingle.adapter.MenuRVAdapter;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetsheet.R;
import com.mingle.sweetpick.SweetSheet;

import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class MenuListViewHandler {


    private List<MenuEntity> mMenuEntities;
    private int mIndex;

    private int mRvVisibility = View.VISIBLE;
    private OnFragmentInteractionListener mOnFragmentInteractionListener;
    private RecyclerView mRV;
    private MenuRVAdapter mMenuRVAdapter;

    private View mView;


    public static MenuListViewHandler getInstant(int index, List<MenuEntity> menuEntities) {
        MenuListViewHandler menuListViewHandler = new MenuListViewHandler();
        menuListViewHandler.mMenuEntities=menuEntities;
        menuListViewHandler.mIndex=index;
        return menuListViewHandler;
    }


    public void setOnMenuItemClickListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        mOnFragmentInteractionListener = onFragmentInteractionListener;
    }


    public View onCreateView(ViewGroup container) {
        if(mView == null){
            mView =LayoutInflater.from(container.getContext()).inflate(R.layout.layout_grid_menu,container,false);
            onViewCreated(mView);
        }
        return mView;
    }

    public void onViewCreated(View view) {

        if(mMenuEntities == null || mMenuEntities.size()==0){
            return;
        }

        mRV = (RecyclerView) view.findViewById(R.id.rv);
        mRV.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        mRV.setHasFixedSize(true);
        mMenuRVAdapter = new MenuRVAdapter(mMenuEntities, SweetSheet.Type.Viewpager);
        mMenuRVAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mOnFragmentInteractionListener != null) {
                    mOnFragmentInteractionListener.onFragmentInteraction(mIndex * 6 + position);
                }
            }
        });
        mRV.setAdapter(mMenuRVAdapter);
        mRV.setVisibility(mRvVisibility);
    }


    public void animationOnStart() {
        if (mRV != null) {
            mRV.setVisibility(View.GONE);
        } else {
            mRvVisibility = View.GONE;
        }
    }

    public void notifyAnimation() {

        if (mRV != null) {
            mRV.setVisibility(View.VISIBLE);
            mRvVisibility = View.VISIBLE;
            mMenuRVAdapter.notifyAnimation();
        } else {
            mRvVisibility = View.VISIBLE;
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int index);
    }

}
