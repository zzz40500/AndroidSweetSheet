package com.mingle.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class MenuListFragment extends Fragment {


    private ArrayList<MenuEntity> mMenuEntities;
    private int mIndex;
    private int mRvVisibility = View.VISIBLE;
    private OnFragmentInteractionListener mOnFragmentInteractionListener;
    private RecyclerView mRV;
    private MenuRVAdapter mMenuRVAdapter;


    public MenuListFragment() {
    }

    public static MenuListFragment getInstant(int index, ArrayList<MenuEntity> menuEntities) {

        MenuListFragment menuListFragment = new MenuListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putParcelableArrayList("mMenuEntities", menuEntities);
        menuListFragment.setArguments(bundle);

        return menuListFragment;
    }


    public void setOnMenuItemClickListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        mOnFragmentInteractionListener = onFragmentInteractionListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIndex = getArguments().getInt("index");
        mMenuEntities = getArguments().getParcelableArrayList("mMenuEntities");

        mRV = (RecyclerView) view.findViewById(R.id.rv);
        mRV.setLayoutManager(new GridLayoutManager(getActivity(), 3));
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
