package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class ArtistMovie implements Parcelable{
    private int artistmovieid;
    private String moviename;
    private String path;
    private String puttime;
    private int artistid;

    public int getArtistmovieid() {
        return artistmovieid;
    }

    public void setArtistmovieid(int artistmovieid) {
        this.artistmovieid = artistmovieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPuttime() {
        return puttime;
    }

    public void setPuttime(String puttime) {
        this.puttime = puttime;
    }

    public int getArtistid() {
        return artistid;
    }

    public void setArtistid(int artistid) {
        this.artistid = artistid;
    }

    @Override
    public String toString() {
        return "ArtistMovie{" +
                "artistmovieid=" + artistmovieid +
                ", moviename='" + moviename + '\'' +
                ", path='" + path + '\'' +
                ", puttime='" + puttime + '\'' +
                ", artistid=" + artistid +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.artistmovieid);
        dest.writeString(this.moviename);
        dest.writeString(this.path);
        dest.writeString(this.puttime);
        dest.writeInt(this.artistid);
    }

    public ArtistMovie() {
    }

    protected ArtistMovie(Parcel in) {
        this.artistmovieid = in.readInt();
        this.moviename = in.readString();
        this.path = in.readString();
        this.puttime = in.readString();
        this.artistid = in.readInt();
    }

    public static final Creator<ArtistMovie> CREATOR = new Creator<ArtistMovie>() {
        public ArtistMovie createFromParcel(Parcel source) {
            return new ArtistMovie(source);
        }

        public ArtistMovie[] newArray(int size) {
            return new ArtistMovie[size];
        }
    };
}
