package com.example.es1294.airmusic;

/**
 * Created by es1294 on 11/17/18.
 */

public class Music {

    public String artist;
    public String downloadUrl;
    public String title;

    public Music(){

    }

    public Music(String artist, String downloadUrl, String title){
        this.artist = artist;
        this.downloadUrl = downloadUrl;
        this.title = title;

    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Music getMusicClass(){
        return this;
    }
}
