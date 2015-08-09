package com.mingle.sweetpick;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 *
 */
public class DimEffect implements Effect {

    private float Value;

    public DimEffect(float value) {
        Value = value;
    }

    public void setValue(float value) {
        Value = value;
    }

    @Override
    public float getValue() {
        return Value;
    }

    @Override
    public void effect(ViewGroup vp, ImageView view) {
        view.setBackgroundColor(Color.argb((int) (150 * Value), 150, 150, 150));
    }
}
