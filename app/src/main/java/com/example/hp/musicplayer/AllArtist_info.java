package com.example.hp.musicplayer;

public class AllArtist_info {
    String artist_Name;
    long artist_id;
   int artist_albumCount;
    int artist_trackCount;

    public AllArtist_info(String artist_Name, long artist_id, int artist_albumCount, int artist_trackCount) {
        this.artist_Name = artist_Name;
        this.artist_id = artist_id;
        this.artist_albumCount = artist_albumCount;
        this.artist_trackCount = artist_trackCount;
    }

    public String getArtist_Name() {
        return artist_Name;
    }

    public void setArtist_Name(String artist_Name) {
        this.artist_Name = artist_Name;
    }

    public long getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(long artist_id) {
        this.artist_id = artist_id;
    }

    public int getArtist_albumCount() {
        return artist_albumCount;
    }

    public void setArtist_albumCount(int artist_albumCount) {
        this.artist_albumCount = artist_albumCount;
    }

    public int getArtist_trackCount() {
        return artist_trackCount;
    }

    public void setArtist_trackCount(int artist_trackCount) {
        this.artist_trackCount = artist_trackCount;
    }
}
