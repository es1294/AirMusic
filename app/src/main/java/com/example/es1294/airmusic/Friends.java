package com.example.es1294.airmusic;

public class Friends {
    private String name;
    private String songName;
    private String artist;
    private String imageUrl;

    public Friends(String name, String songName,String artist,String imageUrl){
        this.name = name;
        this.songName = songName;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSongName(){
        return songName;
    }
    public void setSongName(String songName){
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
