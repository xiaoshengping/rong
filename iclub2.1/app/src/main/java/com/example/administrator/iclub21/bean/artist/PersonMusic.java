package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class PersonMusic implements Parcelable{

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PersonMusic() {
    }

    protected PersonMusic(Parcel in) {
    }

    public static final Creator<PersonMusic> CREATOR = new Creator<PersonMusic>() {
        public PersonMusic createFromParcel(Parcel source) {
            return new PersonMusic(source);
        }

        public PersonMusic[] newArray(int size) {
            return new PersonMusic[size];
        }
    };
}
