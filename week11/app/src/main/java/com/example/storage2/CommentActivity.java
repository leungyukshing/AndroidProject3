package com.example.storage2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CommentActivity extends AppCompatActivity {
    private ListView listView; // 展示comment
    private ArrayList<Comment> mList = new ArrayList<>(); // 存放comment的列表
    private EditText commentBox; // 评论输入框
    private Button send; // 评论发送按钮
    private User user; // 当前登录的用户
    myDB mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mydb = new myDB(CommentActivity.this);
        // 从数据库导入所有评论
        initList();

        // 接受从MainActivity传来的username，根据username获得User对象
        final String username = this.getIntent().getStringExtra("name");
        user = mydb.queryUserByUserName(username);

        listView = (ListView)findViewById(R.id.listview);
        commentBox = (EditText)findViewById(R.id.commentBox);
        send = (Button)findViewById(R.id.btn);

        // 为listView配置adapter
        final MyAdapter adapter = new MyAdapter(CommentActivity.this, mList, username);
        listView.setAdapter(adapter);

        // 为条目中的点赞按钮添加点击事件
        adapter.setOnItemLikeClickListener(new MyAdapter.onItemLikeListener() {
            @Override
            public void onLikeClick(int i) {
                System.out.println("Chose: " + i);
                // 获取当前点赞状态
                boolean isLiked = mydb.checkIsLiked(mList.get(i).getId(), mydb.getUserIdByName(username));
                if (isLiked) {
                    // 若已经点赞，则取消点赞
                    mydb.deleteRelation(mList.get(i).getId(), mydb.getUserIdByName(username));
                }
                else {
                    // 若未点赞，则添加点赞
                    mydb.addRelation(mList.get(i).getId(), mydb.getUserIdByName(username));
                }
                // 更新adapter，重新渲染数据
                adapter.notifyDataSetChanged();
            }
        });

        // ListView item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 从通讯录中获取用户username的电话号码
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + mList.get(i).getCommentName() + "\"", null, null);
                String number = "\nPhone: ";
                if (cursor.getCount() != 0 && cursor.moveToFirst()) {
                    cursor.moveToFirst();
                    do {
                        number += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
                    } while (cursor.moveToNext());
                }
                else {
                    number = "\nPhone number not exist.";
                }
                // 弹出对话框
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CommentActivity.this);
                dialogBuilder.setTitle("Info")
                        .setMessage("Username: " + username + number)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });
        // ListView item长击事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                // 评论是当前用户创建，询问删除
                if (mList.get(i).getCommentName().equals(username)) {
                    // 弹出对话框
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CommentActivity.this);
                    dialogBuilder.setMessage("Delete or not ?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i_) {
                                    // 从数据库中删除该评论
                                    Comment comment = mList.get(i);
                                    mydb.deleteCommentById(comment.getId());
                                    // 从当前页面的list中删除该评论
                                    mList.remove(i);
                                    // 通知adapter更新数据
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
                // 评论是其他用户创建，询问举报
                else {
                    // 弹出对话框
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CommentActivity.this);
                    dialogBuilder.setMessage("Report or not ?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CommentActivity.this, "您已举报", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
                return true;
            }
        });

        // 评论发送按钮点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 评论内容为空
                if (commentBox.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // 获取当前时间
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String time = sdf.format(date);
                    // 新评论的点赞默认为未点赞状态
                    Bitmap bitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.mipmap.white);
                    // 使用随机数作为评论的ID
                    Random random = new Random();
                    int id = random.nextInt(Integer.MAX_VALUE);
                    // 创建Comment，添加到当前页面的list中
                    Comment comment = new Comment(id, user.getUsername(), time, commentBox.getText().toString(), "0", user.getImg(), bitmap);
                    mList.add(comment);

                    // 向数据库中添加评论条目
                    mydb.addComment(id, user.getUsername(), time ,commentBox.getText().toString(),"0", user.getImg());
                    // 通知adapter更新数据
                    adapter.notifyDataSetChanged();
                    // clear input box
                    commentBox.setText("");
                }

            }
        });


    }

    // 从数据库导入评论
    private void initList() {
        if (mydb.getAllComment() == null) {
            mList = new ArrayList<>();
        }
        else
            mList = mydb.getAllComment();
    }
}
