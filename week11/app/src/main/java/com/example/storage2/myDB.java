package com.example.storage2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class myDB extends SQLiteOpenHelper {
    private static final String DB_NAME= "storage";
    private static final String TABLE_NAME_USER = "user";
    private static final String TABLE_NAME_COMMENT = "comment";
    private static final String TABLE_NAME_RELATION = "relation";
    private static final int DB_VERSION = 1;

    public myDB(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
        System.out.println("In DataBase Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("Create table in database");
        // 创建用户表
        String CREATE_TABLE_USER = "CREATE TABLE if not exists "
                + TABLE_NAME_USER
                + " (_id INTEGER PRIMARY KEY, name TEXT, password TEXT, img BLOB)";
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

        // 创建评论表
        String CREATE_TABLE_COMMENT = "CREATE TABLE if not exists "
                + TABLE_NAME_COMMENT
                + " (_id INTEGER PRIMARY KEY, name TEXT, time TEXT, content TEXT, likenumber TEXT, img BLOB)";
        sqLiteDatabase.execSQL(CREATE_TABLE_COMMENT);

        // 创建评论-用户对应表
        String CREATE_TABLE_RELATION = "CREATE TABLE if not exists "
                + TABLE_NAME_RELATION
                + " (_id INTEGER PRIMARY KEY, commentId INTEGER, userId INTEGER ,FOREIGN KEY (commentId) REFERENCES comment (_id) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(CREATE_TABLE_RELATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // 向User表插入User条目
    public void insertUser(String name, String password, Bitmap img) {
        System.out.println("Insert: PS: " + password);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("password", password);

        // 处理用户头像图片
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, os);
        cv.put("img", os.toByteArray());
        db.insert(TABLE_NAME_USER, null, cv);
        db.close();
    }

    // 根据用户名获得User对象
    public User queryUserByUserName(String name) {
        String selection = "name = ?";
        String[] selectionArgs = {name};
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME_USER, null, selection, selectionArgs, null, null, null);
        if (c.getCount() == 0 || !c.moveToFirst()) {
            return null;
        }
        byte[] in = c.getBlob(c.getColumnIndex("img"));
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
        User user = new User(c.getString(1), c.getString(2), bmpout);
        c.close();
        return user;
    }

    // 根据用户名获得User的ID
    public int getUserIdByName(String name) {
        String selection = "name = ?";
        String[] selectionArgs = {name};
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME_USER, null, selection, selectionArgs, null, null, null);
        if (c.getCount() == 0 || !c.moveToFirst()) {
            return -1;
        }
        int id = c.getInt(0);
        c.close();
        return id;
    }

    // 根据用户名获得密码
    public String getPasswordByUserName(String name) {
        String selection = "name = ?";
        String[] selectionArgs = {name};
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME_USER, null, selection, selectionArgs, null, null, null);
        if (c.getCount() == 0 || !c.moveToFirst()) {
            return null;
        }
        String result = c.getString(2);
        System.out.println(result);
        return result;
    }

    // 向comment表中插入Comment条目
    public void addComment(int id, String name, String time, String content, String likenumber, Bitmap img) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("time", time);
        cv.put("content", content);
        cv.put("likenumber", likenumber);

        // 处理用户头像图片
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, os);
        cv.put("img", os.toByteArray());

        db.insert(TABLE_NAME_COMMENT, null, cv);
        db.close();
    }

    // 获得所有的Comment
    public ArrayList<Comment> getAllComment() {
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME_COMMENT, null, null, null, null, null, null);
        if (c.getCount() == 0 || !c.moveToFirst()) {
            return null;
        }
        do {
            // 用户头像图片
            byte[] in1 = c.getBlob(c.getColumnIndex("img"));
            Bitmap bmpout1 = BitmapFactory.decodeByteArray(in1, 0, in1.length);
            int commentId = c.getInt(0);
            int likenumber = getLikeNumberByCommentId(commentId);

            // 默认返回未点赞图片
            Bitmap bitmap=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/white.png"));
            Comment comment = new Comment(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), likenumber+"", bmpout1, bitmap);
            commentArrayList.add(comment);
        }
        while (c.moveToNext());
        c.close();
        return commentArrayList;
    }

    // 根据Comment的ID获取点赞数
    public int getLikeNumberByCommentId(int commentId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "commentId = ?";
        String[] selectionArgs = {commentId + ""};
        Cursor cursor = db.query(TABLE_NAME_RELATION, null,selection, selectionArgs, null, null, null );
        if (cursor.getCount() == 0 || !cursor.moveToFirst()) {
            return 0;
        }
        System.out.println("LikeNumber: " + cursor.getCount());
        return cursor.getCount();
    }

    // 根据Comment的ID和用户ID添加点赞关系
    public void addRelation(int commentId, int userId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("commentId", commentId);
        cv.put("userId", userId);
        db.insert(TABLE_NAME_RELATION, null, cv);
        db.close();
    }

    // 根据Comment的ID在comment表中删除Comment条目
    public void deleteCommentById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = {id + ""};
        int row = db.delete(TABLE_NAME_COMMENT, whereClause, whereArgs);
        db.close();
    }

    // 根据userId和commentId检查是否存在点赞关系
    public boolean checkIsLiked(int commentId, int userId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "commentId = ? and userId = ?";
        String[] selectionArgs = {commentId+"", userId+""};
        Cursor c = db.query(TABLE_NAME_RELATION, null, selection, selectionArgs, null, null, null);
        if (c.getCount() == 0 || !c.moveToFirst()) {
            return false;
        }
        c.close();
        return  true;
    }

    // 根据commentId获取点赞该Comment的用户ID列表
    public  ArrayList<Integer> getLikedListById(int commentId) {
        ArrayList<Integer> likedlist = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String selection = "commentId = ?";
        String[] selectionArgs = {commentId + ""};
        Cursor c = db.query(TABLE_NAME_RELATION, null, selection, selectionArgs, null, null, null);
        if (c.getCount() == 0 || !c.moveToFirst()) {
            return null;
        }
        do {
            System.out.println(c.getInt(2));
            likedlist.add(c.getInt(2));
        } while (c.moveToNext());
        c.close();
        return likedlist;
    }

    // 根据commentId和userId在comment表中删除点赞关系条目
    public void deleteRelation(int commentId, int userId) {
        System.out.println("Delete Relation");
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "commentId = ? and userId = ?";
        String[] whereArgs = {commentId + "", userId + ""};
        db.delete(TABLE_NAME_RELATION, whereClause, whereArgs);
    }
}
