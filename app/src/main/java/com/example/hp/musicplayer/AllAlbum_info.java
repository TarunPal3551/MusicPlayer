package com.example.hp.musicplayer;

public class AllAlbum_info {
    String album_name;
    long album_id;
    int no_of_song;
    String artist;
   int alumYear;
    String albumCover;

    public AllAlbum_info(String album_name, long album_id, int no_of_song, String artist, int alumYear, String albumCover) {
        this.album_name = album_name;
        this.album_id = album_id;
        this.no_of_song = no_of_song;
        this.artist = artist;
        this.alumYear = alumYear;
        this.albumCover = albumCover;
    }

    public AllAlbum_info() {

    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }

    public int getNo_of_song() {
        return no_of_song;
    }

    public void setNo_of_song(int no_of_song) {
        this.no_of_song = no_of_song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlumYear() {
        return alumYear;
    }

    public void setAlumYear(int alumYear) {
        this.alumYear = alumYear;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }
}
