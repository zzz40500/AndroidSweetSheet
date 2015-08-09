package com.mingle.sweetpick;

import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by zzz40500 on 15/8/9.
 */
public class NoneEffect implements Effect  {
    @Override
    public float getValue() {
        return 0;
    }
    @Override
    public void effect(ViewGroup vp, ImageView view) {

    }
}
