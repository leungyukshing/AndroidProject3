package com.example.storage2;

import android.graphics.Bitmap;

public class User {
    private String username;
    private String password;
    private Bitmap img;

    public User(String username, String password, Bitmap img) {
        this.username = username;
        this.password = password;
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Bitmap getImg() {
        return img;
    }
}
