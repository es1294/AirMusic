package com.example.es1294.airmusic;

import com.google.firebase.database.Exclude;

import java.sql.Blob;

public class User {
    String authID;
    String email;
    String fullName;
    String about;
    String artist1, artist2, artist3, artist4, artist5;
    String genre1, genre2, genre3;
    String profilePhotoStorageName;

    public User(){}

    public User(String authID, String email, String Fullname, String About, String Artist1, String Artist2, String Artist3, String Artist4, String Artist5, String Genre1, String Genre2, String Genre3, String photoName){
        this.authID = authID;
        this.email = email;
        //this.password = password;
        this.fullName = Fullname;
        this.about = About;
        this.artist1 = Artist1;
        this.artist2 = Artist2;
        this.artist3 = Artist3;
        this.artist4 = Artist4;
        this.artist5 = Artist5;
        this.genre1 = Genre1;
        this.genre2 = Genre2;
        this.genre3 = Genre3;
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
        this.fullName = fullName;
    }
    public String getFullName(){
        return this.fullName;
    }
    //about
    public void setAbout(String about){
        this.about = about;
    }
    public String getAbout(){
        return this.about;
    }
    //artist1
    public void setArtistOne(String artist1){
        this.artist1 = artist1;
    }
    public String getArtistOne(){
        return this.artist1;
    }
    //artist2
    public void setArtistTwo(String artist2){
        this.artist2 = artist2;
    }
    public String getArtistTwo(){
        return this.artist2;
    }
    //artist3
    public void setArtistThree(String artist3){
        this.artist3 = artist3;
    }
    public String getArtistThree(){
        return this.artist3;
    }
    //artist4
    public void setArtistFour(String artist4){
        this.artist4 = artist4;
    }
    public String getArtistFour(){
        return this.artist4;
    }
    //artist5
    public void setArtistFive(String artist5){
        this.artist5 = artist5;
    }
    public String getArtistFive(){
        return this.artist5;
    }
    //genre1
    public void setGenreOne(String genre1){
        this.genre1 = genre1;
    }
    public String getGenreOne(){
        return this.genre1;
    }
    //genre2
    public void setGenreTwo(String genre2){
        this.genre2 = genre2;
    }
    public String getGenreTwo(){
        return this.genre2;
    }
    //genre3
    public void setGenreThree(String genre3){
        this.genre3 = genre3;
    }
    public String getGenreThree(){
        return this.genre3;
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
