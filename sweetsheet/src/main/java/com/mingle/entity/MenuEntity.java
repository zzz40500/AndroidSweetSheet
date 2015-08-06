package com.mingle.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;


/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class MenuEntity implements Parcelable {

    public @DrawableRes int resId;
    public  String name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resId);
        dest.writeString(this.name);
    }

    public MenuEntity() {
    }

    protected MenuEntity(Parcel in) {
        this.resId = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<MenuEntity> CREATOR = new Parcelable.Creator<MenuEntity>() {

        public MenuEntity createFromParcel(Parcel source) {
            return new MenuEntity(source);
        }

        public MenuEntity[] newArray(int size) {
            return new MenuEntity[size];
        }
    };
}
