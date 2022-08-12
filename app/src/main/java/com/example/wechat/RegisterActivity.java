package com.example.wechat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText rg_name,rg_psw;
    Button btn_submit;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=SQLiteDatabase.openOrCreateDatabase(getCacheDir()+"/note",null);
        rg_psw=findViewById(R.id.rg_psw);
        rg_name=findViewById(R.id.rg_name);
        btn_submit=findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(view -> {
            if (rg_name.getText().toString().equals("") || rg_psw.getText().toString().equals("")){
                Toast.makeText(RegisterActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from user where username='"+rg_name.getText().toString()+"'",null);
            if (cursor.moveToNext()){
                Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
            }else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("username", rg_name.getText().toString());
                contentValues.put("password", rg_psw.getText().toString());
                db.insert("user", null, contentValues);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}

