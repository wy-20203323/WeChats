package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class LoginActivity extends AppCompatActivity {
    EditText lg_name,lg_psw;
    Button btn_login;
    Button btn_register;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lg_name=findViewById(R.id.lg_name);
        lg_psw=findViewById(R.id.lg_psw);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        //登录注册部分数据库
        db=SQLiteDatabase.openOrCreateDatabase(getCacheDir()+"/note",null);
        try {
            db.execSQL("create table user(username varchar(100),password varchar(100))");
        } catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences=getSharedPreferences("user",0);
        lg_name.setText(sharedPreferences.getString("lg_name",""));
        lg_psw.setText(sharedPreferences.getString("lg_psw",""));
        btn_login.setOnClickListener(view -> {
            if (lg_name.getText().toString().equals("") || lg_psw.getText().toString().equals("")){
                Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from user where username='"+lg_name.getText().toString()+"'",null);
            if (cursor.moveToNext()){
                if (cursor.getString(1).equals(lg_psw.getText().toString())){
                    SharedPreferences.Editor editor=getSharedPreferences("lg_name",0).edit();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    editor.apply();
                }else {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
            }
        });
        btn_register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
    }

}