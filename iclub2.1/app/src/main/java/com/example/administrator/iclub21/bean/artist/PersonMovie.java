package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class PersonMovie implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PersonMovie() {
    }

    protected PersonMovie(Parcel in) {
    }

    public static final Creator<PersonMovie> CREATOR = new Creator<PersonMovie>() {
        public PersonMovie createFromParcel(Parcel source) {
            return new PersonMovie(source);
        }

        public PersonMovie[] newArray(int size) {
            return new PersonMovie[size];
        }
    };
}
