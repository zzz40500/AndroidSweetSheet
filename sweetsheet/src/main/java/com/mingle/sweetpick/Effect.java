package com.mingle.sweetpick;

import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 *
 */
public interface Effect {


    float getValue();

    void effect(ViewGroup vp,ImageView view);

}
