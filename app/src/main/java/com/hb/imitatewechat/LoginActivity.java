package com.hb.imitatewechat;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etAccount,etPassword;
    private CheckBox cbRemember,cbAutoLogin;
    private Button btnLogin;

    private String userName = "";
    private String passWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();

        initData();

        initEvent();

    }


    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
    }

    private void initData() {
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        boolean isRemember = spf.getBoolean("isRemember",false);
        boolean isLogin = spf.getBoolean("isLogin",false);
        String account = spf.getString("account","");
        String password = spf.getString("password","");

        if(isLogin){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("account",account);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        userName = account;
        passWord = password;

        if(isRemember){
            etAccount.setText(account);
            etPassword.setText(password);
            cbRemember.setChecked(true);
        }

    }

    private void initEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"还没有注册账号！",Toast.LENGTH_LONG).show();
                }else if (!TextUtils.equals(account, userName)){
                    Toast.makeText(LoginActivity.this,"用户名有误！",Toast.LENGTH_LONG).show();
                }else if (!TextUtils.equals(password, passWord)){
                    Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                    SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                    SharedPreferences.Editor editor = spf.edit();
                    if (cbRemember.isChecked()){
                        editor.putString("account",account);
                        editor.putString("password",password);
                        editor.putBoolean("isRemember",true);
                        if(cbAutoLogin.isChecked()){
                            editor.putBoolean("isLogin",true);
                        }else {
                            editor.putBoolean("isLogin",false);
                        }
                    }else {
                        editor.putBoolean("isRemember",false);
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("account",account);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        });
    }


    public void toRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();
            if (result.getResultCode() == RESULT_OK && data != null) {
                Bundle bundle = data.getExtras();

                String account = bundle.getString("account","");
                String password = bundle.getString("password","");

                etAccount.setText(account);
                etPassword.setText(password);

                userName = account;
                passWord = password;
            }
        });
        intentActivityResultLauncher.launch(intent);
    }

}