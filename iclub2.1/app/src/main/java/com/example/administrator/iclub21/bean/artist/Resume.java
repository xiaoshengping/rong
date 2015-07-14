package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class Resume implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Resume() {
    }

    protected Resume(Parcel in) {
    }

    public static final Creator<Resume> CREATOR = new Creator<Resume>() {
        public Resume createFromParcel(Parcel source) {
            return new Resume(source);
        }

        public Resume[] newArray(int size) {
            return new Resume[size];
        }
    };
}
