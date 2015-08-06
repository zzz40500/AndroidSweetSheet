package com.mingle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;


/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 *
 */
public class ViewpagerAdapter extends FragmentStatePagerAdapter {


    List<Fragment> mFragments = null;
    List<CharSequence> titles = null;

    public ViewpagerAdapter(Fragment fragment, List<Fragment> fragments,
                            List<CharSequence> titles) {
        super(fragment.getChildFragmentManager());
        this.mFragments = fragments;
        this.titles = titles;

    }

    public ViewpagerAdapter(android.support.v4.app.FragmentManager manager, List<Fragment> fragments,
                            List<CharSequence> titles) {
        super(manager);
        this.mFragments = fragments;
        this.titles = titles;

    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    // @Override
    // public int getItemPosition(Object object) {
    // return POSITION_NONE;
    // }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }

}
