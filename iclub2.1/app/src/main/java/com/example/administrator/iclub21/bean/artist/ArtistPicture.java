package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class ArtistPicture implements Parcelable{
    private int artistpictureid;
    private String name;
    private String title;
    private int artistid;

    public int getArtistpictureid() {
        return artistpictureid;
    }

    public void setArtistpictureid(int artistpictureid) {
        this.artistpictureid = artistpictureid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistid() {
        return artistid;
    }

    public void setArtistid(int artistid) {
        this.artistid = artistid;
    }

    @Override
    public String toString() {
        return "ArtistPicture{" +
                "artistpictureid=" + artistpictureid +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", artistid=" + artistid +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.artistpictureid);
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeInt(this.artistid);
    }

    public ArtistPicture() {
    }

    protected ArtistPicture(Parcel in) {
        this.artistpictureid = in.readInt();
        this.name = in.readString();
        this.title = in.readString();
        this.artistid = in.readInt();
    }

    public static final Creator<ArtistPicture> CREATOR = new Creator<ArtistPicture>() {
        public ArtistPicture createFromParcel(Parcel source) {
            return new ArtistPicture(source);
        }

        public ArtistPicture[] newArray(int size) {
            return new ArtistPicture[size];
        }
    };
}
