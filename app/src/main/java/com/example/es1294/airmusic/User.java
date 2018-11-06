package com.example.es1294.airmusic;

import java.sql.Blob;

public class User {
    int id;
    String name, email, username, password;
    String dFullName;
    String dAbout;
    String dArtist1, dArtist2, dArtist3, dArtist4, dArtist5;
    String dGenre1, dGenre2, dGenre3;
    byte[] profilePhoto;

//getters and setters for each item
    //id
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    //name
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    //email
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    //username
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    //password
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    //fullName
    public void setFullName(String fullName){
        this.dFullName = fullName;
    }
    public String getFullName(){
        return this.dFullName;
    }
    //about
    public void setAbout(String about){
        this.dAbout = about;
    }
    public String getAbout(){
        return this.dAbout;
    }
    //artist1
    public void setArtistOne(String artist1){
        this.dArtist1 = artist1;
    }
    public String getArtistOne(){
        return this.dArtist1;
    }
    //artist2
    public void setArtistTwo(String artist2){
        this.dArtist2 = artist2;
    }
    public String getArtistTwo(){
        return this.dArtist2;
    }
    //artist3
    public void setArtistThree(String artist3){
        this.dArtist3 = artist3;
    }
    public String getArtistThree(){
        return this.dArtist3;
    }
    //artist4
    public void setArtistFour(String artist4){
        this.dArtist4 = artist4;
    }
    public String getArtistFour(){
        return this.dArtist4;
    }
    //artist5
    public void setArtistFive(String artist5){
        this.dArtist5 = artist5;
    }
    public String getArtistFive(){
        return this.dArtist5;
    }
    //genre1
    public void setGenreOne(String genre1){
        this.dGenre1 = genre1;
    }
    public String getGenreOne(){
        return this.dGenre1;
    }
    //genre2
    public void setGenreTwo(String genre2){
        this.dGenre2 = genre2;
    }
    public String getGenreTwo(){
        return this.dGenre2;
    }
    //genre3
    public void setGenreThree(String genre3){
        this.dGenre3 = genre3;
    }
    public String getGenreThree(){
        return this.dGenre3;
    }
    public void setProfilePhoto(byte[] data){
        this.profilePhoto = data;
    }
    public byte[] getProfilePhoto(){
        return this.profilePhoto;
    }
//end getters and setters


}
