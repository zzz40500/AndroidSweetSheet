package com.mingle.entity;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.ColorInt;


/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class MenuEntity {

    public @DrawableRes int iconId;
    public @ColorInt int titleColor;
    public CharSequence title;
    public Drawable icon;


}
