package com.example.hp.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AllSong_Info implements Serializable{



    private String songname;
    private String artistname;
    private String songAlbum;
    private String songLenght;
    private String albumCover;
    private long albumiD;
    private String songPath;

    public AllSong_Info(String songname, String artistname, String songAlbum, String songLenght, String albumCover, long albumiD, String songPath) {
        this.songname = songname;
        this.artistname = artistname;
        this.songAlbum = songAlbum;
        this.songLenght = songLenght;
        this.albumCover = albumCover;
        this.albumiD = albumiD;
        this.songPath = songPath;
    }

    public AllSong_Info() {
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongLenght() {
        return songLenght;
    }

    public void setSongLenght(String songLenght) {
        this.songLenght = songLenght;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public long getAlbumiD() {
        return albumiD;
    }

    public void setAlbumiD(long albumiD) {
        this.albumiD = albumiD;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    @Override
    public String toString() {
        return "AllSong_Info{" +
                "songname='" + songname + '\'' +
                ", artistname='" + artistname + '\'' +
                ", songAlbum='" + songAlbum + '\'' +
                ", songLenght='" + songLenght + '\'' +
                ", albumCover='" + albumCover + '\'' +
                ", albumiD=" + albumiD +
                ", songPath='" + songPath + '\'' +
                '}';
    }
}
