package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class PersonPicture implements Parcelable{

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PersonPicture() {
    }

    protected PersonPicture(Parcel in) {
    }

    public static final Creator<PersonPicture> CREATOR = new Creator<PersonPicture>() {
        public PersonPicture createFromParcel(Parcel source) {
            return new PersonPicture(source);
        }

        public PersonPicture[] newArray(int size) {
            return new PersonPicture[size];
        }
    };
}
