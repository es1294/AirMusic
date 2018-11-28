package com.example.es1294.airmusic;

import java.sql.Blob;

public class User {
    String authID;
    String email;
    String FullName;
    String About;
    String Artist1, Artist2, Artist3, Artist4, Artist5;
    String Genre1, Genre2, Genre3;
    String profilePhotoStorageName;

    public User(){}

    public User(String authID, String email, String Fullname, String About, String Artist1, String Artist2, String Artist3, String Artist4, String Artist5, String Genre1, String Genre2, String Genre3, String photoName){
        this.authID = authID;
        this.email = email;
        //this.password = password;
        this.FullName = Fullname;
        this.About = About;
        this.Artist1 = Artist1;
        this.Artist2 = Artist2;
        this.Artist3 = Artist3;
        this.Artist4 = Artist4;
        this.Artist5 = Artist5;
        this.Genre1 = Genre1;
        this.Genre2 = Genre2;
        this.Genre3 = Genre3;
        this.profilePhotoStorageName = photoName;
        //this.profilePhotoStorageName = profilePhotoStorageName;
    }

//getters and setters for each item
    //authID
    public void setAuthID(String authID){
        this.authID = authID;
    }
    public String getAuthID(){
        return this.authID;
    }
    //email
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    //fullName
    public void setFullName(String fullName){
        this.FullName = fullName;
    }
    public String getFullName(){
        return this.FullName;
    }
    //about
    public void setAbout(String about){
        this.About = about;
    }
    public String getAbout(){
        return this.About;
    }
    //artist1
    public void setArtistOne(String artist1){
        this.Artist1 = artist1;
    }
    public String getArtistOne(){
        return this.Artist1;
    }
    //artist2
    public void setArtistTwo(String artist2){
        this.Artist2 = artist2;
    }
    public String getArtistTwo(){
        return this.Artist2;
    }
    //artist3
    public void setArtistThree(String artist3){
        this.Artist3 = artist3;
    }
    public String getArtistThree(){
        return this.Artist3;
    }
    //artist4
    public void setArtistFour(String artist4){
        this.Artist4 = artist4;
    }
    public String getArtistFour(){
        return this.Artist4;
    }
    //artist5
    public void setArtistFive(String artist5){
        this.Artist5 = artist5;
    }
    public String getArtistFive(){
        return this.Artist5;
    }
    //genre1
    public void setGenreOne(String genre1){
        this.Genre1 = genre1;
    }
    public String getGenreOne(){
        return this.Genre1;
    }
    //genre2
    public void setGenreTwo(String genre2){
        this.Genre2 = genre2;
    }
    public String getGenreTwo(){
        return this.Genre2;
    }
    //genre3
    public void setGenreThree(String genre3){
        this.Genre3 = genre3;
    }
    public String getGenreThree(){
        return this.Genre3;
    }
    //profile name in storage
    public void setProfilePhotoStorageName(String profilePhotoStorageName){
        this.profilePhotoStorageName = profilePhotoStorageName;
    }
    public String getProfilePhotoStorageName(){
        return this.profilePhotoStorageName;
    }
//end getters and setters
}
