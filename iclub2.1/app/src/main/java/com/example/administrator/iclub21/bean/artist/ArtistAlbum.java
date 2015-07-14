package com.example.administrator.iclub21.bean.artist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/5/19.
 */
public class ArtistAlbum implements Parcelable{

    private int artistalbumid;
    private String albumname;
    private String albumdate;
    private String albumicon;
    private String puttime;
    private int artistid;
    private List<ArtistMusic> artistMusic;

    public int getArtistalbumid() {
        return artistalbumid;
    }

    public void setArtistalbumid(int artistalbumid) {
        this.artistalbumid = artistalbumid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getAlbumdate() {
        return albumdate;
    }

    public void setAlbumdate(String albumdate) {
        this.albumdate = albumdate;
    }

    public String getAlbumicon() {
        return albumicon;
    }

    public void setAlbumicon(String albumicon) {
        this.albumicon = albumicon;
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

    public List<ArtistMusic> getArtistMusic() {
        return artistMusic;
    }

    public void setArtistMusic(List<ArtistMusic> artistMusic) {
        this.artistMusic = artistMusic;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.artistalbumid);
        dest.writeString(this.albumname);
        dest.writeString(this.albumdate);
        dest.writeString(this.albumicon);
        dest.writeString(this.puttime);
        dest.writeInt(this.artistid);
        dest.writeTypedList(artistMusic);
    }

    public ArtistAlbum() {
    }

    protected ArtistAlbum(Parcel in) {
        this.artistalbumid = in.readInt();
        this.albumname = in.readString();
        this.albumdate = in.readString();
        this.albumicon = in.readString();
        this.puttime = in.readString();
        this.artistid = in.readInt();
        this.artistMusic = in.createTypedArrayList(ArtistMusic.CREATOR);
    }

    public static final Creator<ArtistAlbum> CREATOR = new Creator<ArtistAlbum>() {
        public ArtistAlbum createFromParcel(Parcel source) {
            return new ArtistAlbum(source);
        }

        public ArtistAlbum[] newArray(int size) {
            return new ArtistAlbum[size];
        }
    };
}
