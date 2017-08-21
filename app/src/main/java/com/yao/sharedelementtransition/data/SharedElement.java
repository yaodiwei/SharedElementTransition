package com.yao.sharedelementtransition.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YAO on 2017/8/20.
 */

public class SharedElement implements Parcelable {

    public int resId;
    public int x;
    public int y;
    public int width;
    public int height;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resId);
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    public SharedElement() {
    }

    protected SharedElement(Parcel in) {
        this.resId = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<SharedElement> CREATOR = new Parcelable.Creator<SharedElement>() {
        @Override
        public SharedElement createFromParcel(Parcel source) {
            return new SharedElement(source);
        }

        @Override
        public SharedElement[] newArray(int size) {
            return new SharedElement[size];
        }
    };
}
