package com.example.storage2;

import android.graphics.Bitmap;

public class Comment {
    private int id;
    private String commentName;
    private String commentTime;
    private String commentContent;
    private String likeNumber;
    private Bitmap img;
    private Bitmap likeImg;

    public Comment(int id, String name, String time, String content, String likenumber, Bitmap _img, Bitmap _likeImg) {
        this.id = id;
        this.commentName = name;
        this.commentTime = time;
        this.commentContent = content;
        this.likeNumber = likenumber;
        this.img = _img;
        this.likeImg = _likeImg;
    }

    public int getId() {
        return id;
    }

    public String getCommentName() {
        return commentName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public Bitmap getImg() {
        return img;
    }

    public Bitmap getLikeImg() {
        return likeImg;
    }
}
