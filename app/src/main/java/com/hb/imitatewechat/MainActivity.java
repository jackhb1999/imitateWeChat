package com.hb.imitatewechat;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = findViewById(R.id.tv_content);
        Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        tvContent.setText("欢迎你：" + account);

    }

    public void logout(View view) {
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("isLogin",false);
        editor.apply();

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}