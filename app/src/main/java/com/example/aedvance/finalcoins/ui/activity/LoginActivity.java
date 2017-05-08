package com.example.aedvance.finalcoins.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.task.LoginTask;
import com.example.aedvance.finalcoins.util.EasyCallback;
import com.example.aedvance.finalcoins.util.SPUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REGISTER = 0x123;

    EditText et_name, et_pwd;
    Button btn_register, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //更改状态蓝色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.rgb(73, 133, 208));
        }

        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_name.setText(SPUtil.getName());
        if(SPUtil.isAutoLogin()){
            et_pwd.setText(SPUtil.getPwd());
        }

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                RegisterActivity.startForResult(this,REGISTER);
                break;
            case R.id.btn_login:
                String name = et_name.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                new LoginTask(new EasyCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        MainActivity.start(LoginActivity.this);
                    }

                    @Override
                    public void onFailed(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }).execute(name, pwd);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REGISTER&&resultCode==RESULT_OK){
            String name = data.getStringExtra("name");
            et_name.setText(name);
        }
    }
}
