package com.example.es1294.airmusic;

public class User {
    int id;
    String name, email, username, password;

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
//end getters and setters


}
