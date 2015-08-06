package com.mingle.sweetpick;

import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mingle.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 *
 */
public class SweetSheet {


    //方便扩展
    public enum Type {

        RecyclerView, Viewpager
    }

    private ViewGroup mParentVG;

    private Delegate mDelegate;

    public SweetSheet(RelativeLayout re, Type type) {

        mParentVG = re;
        init(type);
    }

    public SweetSheet(FrameLayout fl, Type type) {

        mParentVG = fl;
        init(type);
    }

    public boolean isShow() {

        if (mDelegate.getStatus() == Status.SHOW || mDelegate.getStatus() == Status.SHOWING) {
            return true;
        }
        return false;
    }

    public SweetSheet(ViewGroup vg, Type type) {

        if (vg instanceof FrameLayout || vg instanceof RelativeLayout) {

        } else {
            throw new IllegalStateException("ViewGroup  must FrameLayout or  RelativeLayout.");
        }
        mParentVG = vg;
        init(type);
    }


    private void init(Type type) {

        switch (type) {

            case RecyclerView:
                mDelegate = new SweetRecyclerViewDelegate();
                break;

            case Viewpager:
                mDelegate = new SweetViewPagerDelegate();
                break;

            default:
                break;
        }
        mDelegate.init(mParentVG);

    }


    public void setBackgroundDim(float dim) {
        mDelegate.setBackgroundDim(dim);

    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onItemClickListener) {
        mDelegate.setOnMenuItemClickListener(onItemClickListener);
    }


    public void show() {
        mDelegate.show();
    }

    public void dismiss() {

        mDelegate.dismiss();
    }

    public void toggle() {

        mDelegate.toggle();
    }


    public void setMenuList(FragmentManager fragmentManager, ArrayList<MenuEntity> menuEntities) {

        mDelegate.setMenuList(fragmentManager, menuEntities);
    }

    protected enum Status {

        SHOW, SHOWING, DISMISS, DISMISSING
    }

    public interface OnMenuItemClickListener {

        boolean onItemClick(int position);

    }

}
