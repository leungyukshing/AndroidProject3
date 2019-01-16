package com.example.storage2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.opengl.Visibility;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;

    // 登录界面
    private  ConstraintLayout loginPanel; // 登陆界面布局
    private EditText username; // 用户名输入框
    private EditText password; // 密码输入框
    private Button ok; // OK按钮
    private Button clear; // CLEAR按钮

    // 注册界面
    private ConstraintLayout registerPanel; // 注册界面布局
    private EditText usernameRegister; // 用户名输入框
    private EditText passwordRegister; // 密码输入框
    private EditText confirmPassword; // 确认密码输入框
    private Button okRegister; // OK按钮
    private Button clearRegister; // CLEAR按钮
    private ImageView add; // 添加用户头像

    private boolean isChoosen = false; // 判断用户是否选择了头像的变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup)findViewById(R.id.btngroup);

        loginPanel = (ConstraintLayout)findViewById(R.id.loginPanel);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        ok = (Button)findViewById(R.id.ok);
        clear = (Button)findViewById(R.id.clear);

        registerPanel = (ConstraintLayout)findViewById(R.id.registerPanel);
        usernameRegister = (EditText)findViewById(R.id.usernameRegister);
        passwordRegister = (EditText)findViewById(R.id.passwordRegister);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        okRegister = (Button)findViewById(R.id.okRegister);
        clearRegister = (Button)findViewById(R.id.clearRegister);
        add = (ImageView)findViewById(R.id.add);

        // 默认选中Login
        radioGroup.check(R.id.login);

        // 连接数据库
        final myDB mydb = new myDB(MainActivity.this);

        // 登录界面OK按钮事件点击
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String pw = password.getText().toString();
                // 用户名为空
                if (userName.equals("")) {
                    Toast.makeText(MainActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                // 密码为空
                else if (pw.equals("")) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                // 用户名不存在
                else if (mydb.queryUserByUserName(userName) == null) {
                    Toast.makeText(MainActivity.this, "Username not existed.", Toast.LENGTH_SHORT).show();
                }
                // 密码错误
                else if (!pw.equals(mydb.getPasswordByUserName(userName))) {
                    Toast.makeText(MainActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                }
                // 成功登陆
                else {
                    // 跳转到CommentActivity界面，传username
                    Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                    intent.putExtra("name", userName);
                    startActivity(intent);
                }
            }
        });

        // clear Button onClick Listener
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });

        // 注册界面OK按钮事件监听
        okRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = usernameRegister.getText().toString();
                String pw = passwordRegister.getText().toString();
                String pwConfirm = confirmPassword.getText().toString();
                // 用户名为空
                if (userName.equals("")) {
                    Toast.makeText(MainActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                // 密码为空
                else if (pw.equals("")) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                // 密码与确认密码不一致
                else if (!pw.equals(pwConfirm)) {
                    System.out.println("In");
                    Toast.makeText(view.getContext(), "Password Mismatch.", Toast.LENGTH_SHORT).show();
                }
                // 用户名已存在
                else if (mydb.queryUserByUserName(userName) != null) {
                    Toast.makeText(view.getContext(), "Username already existed.", Toast.LENGTH_SHORT).show();
                }
                // 成功注册
                else {
                    Bitmap img;
                    // 用户从图库选择图片作为头像
                    if (isChoosen) {
                         img = ((BitmapDrawable) add.getDrawable()).getBitmap();
                    }
                    // 用户未选择头像，默认提供
                    else {
                        img = BitmapFactory.decodeResource(view.getContext().getResources(), R.mipmap.me);
                    }
                    // 向数据库插入新用户条目
                    mydb.insertUser(userName, pw, img);
                    // 跳转到CommentActivity页面，传username
                    Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                    intent.putExtra("name", userName);
                    startActivity(intent);
                }
            }
        });

        // clear Button onClick Listener
        clearRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameRegister.setText("");
                passwordRegister.setText("");
                confirmPassword.setText("");
            }
        });

        // 切换按钮（不清除username）
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.login) {
                    username.setText(usernameRegister.getText().toString());
                    password.setText("");
                    loginPanel.setVisibility(View.VISIBLE);
                    registerPanel.setVisibility(View.INVISIBLE);
                }
                else {
                    usernameRegister.setText(username.getText().toString());
                    passwordRegister.setText("");
                    confirmPassword.setText("");
                    loginPanel.setVisibility(View.INVISIBLE);
                    registerPanel.setVisibility(View.VISIBLE);
                }
            }
        });

        // 选择图片点击事件
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 激活系统图库，选择一张图片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
    }

    // 响应用户选择图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            System.out.println("Result For Choose");
            isChoosen = true;
            // 得到图片的全路径

            Uri uri = data.getData();

            // 通过路径加载图片

            //这里省去了图片缩放操作，如果图片过大，可能会导致内存泄漏

            //图片缩放的实现，请看：https://blog.csdn.net/reality_jie_blog/article/details/16891095

            this.add.setImageURI(uri);



            // 获取图片的缩略图，可能为空！

            // Bitmap bitmap = data.getParcelableExtra("data");

            // this.iv_image.setImageBitmap(bitmap);



        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
