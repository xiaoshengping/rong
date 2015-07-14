package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/5/19.
 */
public class ArtistMusic implements Parcelable{
    private int artistmusicid;
    private String musicname;
    private String path;
    private String puttime;
    private int albumid;

    public int getArtistmusicid() {
        return artistmusicid;
    }

    public void setArtistmusicid(int artistmusicid) {
        this.artistmusicid = artistmusicid;
    }

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
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

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    @Override
    public String toString() {
        return "ArtistMusic{" +
                "artistmusicid=" + artistmusicid +
                ", musicname='" + musicname + '\'' +
                ", path='" + path + '\'' +
                ", puttime='" + puttime + '\'' +
                ", albumid=" + albumid +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.artistmusicid);
        dest.writeString(this.musicname);
        dest.writeString(this.path);
        dest.writeString(this.puttime);
        dest.writeInt(this.albumid);
    }

    public ArtistMusic() {
    }

    protected ArtistMusic(Parcel in) {
        this.artistmusicid = in.readInt();
        this.musicname = in.readString();
        this.path = in.readString();
        this.puttime = in.readString();
        this.albumid = in.readInt();
    }

    public static final Creator<ArtistMusic> CREATOR = new Creator<ArtistMusic>() {
        public ArtistMusic createFromParcel(Parcel source) {
            return new ArtistMusic(source);
        }

        public ArtistMusic[] newArray(int size) {
            return new ArtistMusic[size];
        }
    };
}
