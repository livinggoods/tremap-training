package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/22/18.
 */

public class User {
    int id;
    String email, userName, passWord, country, name;

    public User() {
    }

    public User(int id, String email, String userName, String passWord, String country, String name) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.country = country;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
