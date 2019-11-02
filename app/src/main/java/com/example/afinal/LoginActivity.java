package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    EditText username;
    EditText password;

    String a ="41610033";
    String b = "123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button.setOnClickListener(this);
    }

    public void Opennew(View btn){
        Intent Opennew = new Intent(this,Refresh.class);
        startActivity(Opennew);


    }


    @Override
    public void onClick(View btn) {
        String username1 = username.getText().toString();
        String password1 = password.getText().toString();
        String ok = "登录成功";
        String fail = "登录失败";
        if (username1.equals("41610033") && password1.equals("123456")) {
            Toast.makeText(LoginActivity.this, ok, Toast.LENGTH_SHORT).show();
            Intent hello = new Intent(this, MainActivity.class);
            startActivity(hello);
        } else {
            Toast.makeText(LoginActivity.this, fail, Toast.LENGTH_SHORT).show();
        }

    }}