package com.yao.sharedelementtransition.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YAO on 2017/8/19.
 */

public class Person implements Parcelable {

    public String name;
    public String avatar;
    public int defaultAvatarResId;
    public String desc;

    public Person(String name, String avatar, int defaultAvatarResId, String desc) {
        this.name = name;
        this.avatar = avatar;
        this.defaultAvatarResId = defaultAvatarResId;
        this.desc = desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeInt(this.defaultAvatarResId);
        dest.writeString(this.desc);
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.avatar = in.readString();
        this.defaultAvatarResId = in.readInt();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
