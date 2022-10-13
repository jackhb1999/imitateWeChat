package com.hb.imitatewechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private EditText etAccount,etPassword,etPasswordConfirm;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        // getActionBar().setTitle("注册");

        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        etPasswordConfirm = findViewById(R.id.et_password_confirm);
        cbAgree = findViewById(R.id.cb_agree);

        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String userName = etAccount.getText().toString();
        String passWord = etPassword.getText().toString();
        String passWordConfirm = etPasswordConfirm.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(passWord)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
        }else if(!passWord.equals(passWordConfirm)){
            Toast.makeText(this,"输入密码不一致",Toast.LENGTH_LONG).show();
        }else if(!cbAgree.isChecked()){
            Toast.makeText(this,"请同意用户协议",Toast.LENGTH_LONG).show();
            return;
        }

        // 存储注册的用户名、密码
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("account",userName);
        editor.putString("password",passWord);
        editor.apply();

        // 数据回传
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("account",userName);
        bundle.putString("password",passWord);
        bundle.putBoolean("isRemember",true);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);

        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
        this.finish();
    }
}