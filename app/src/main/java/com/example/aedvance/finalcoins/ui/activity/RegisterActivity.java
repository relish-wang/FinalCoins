package com.example.aedvance.finalcoins.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.util.SPUtil;
import com.example.aedvance.finalcoins.util.TimeUtil;

import java.util.Calendar;

/**
 * Created by aedvance on 2017/5/2.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_pwd, et_repeat_pwd;
    Spinner sp_sex;
    TextView tv_birth;
    TextView tv_return_login;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //更改状态蓝色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.rgb(73, 133, 208));
        }
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_repeat_pwd = (EditText) findViewById(R.id.et_repeat_pwd);
        sp_sex = (Spinner) findViewById(R.id.sp_sex);
        sp_sex.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"男", "女"}));
        sp_sex.setSelection(0);
        sp_sex.setSelected(true);

        tv_birth = (TextView) findViewById(R.id.tv_birth);
        tv_return_login = (TextView) findViewById(R.id.tv_return_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_birth.setOnClickListener(this);
        tv_return_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_birth:
                String birth = tv_birth.getText().toString().trim();
                Calendar calendar;
                if (TextUtils.isEmpty(birth)) {
                    calendar = Calendar.getInstance();
                } else {
                    calendar = TimeUtil.datetime2Calendar(birth);
                }
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_birth.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.tv_return_login:
                finish();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        String name = et_name.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        String repeat_pwd = et_repeat_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            show("用户名不得为空");
            et_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            show("密码不得为空");
            et_pwd.requestFocus();
        }
        if (!TextUtils.equals(pwd, repeat_pwd)) {
            show("两次密码输入不一致");
            et_repeat_pwd.requestFocus();
            return;
        }
        boolean isMale = TextUtils.equals(sp_sex.getSelectedItem().toString(), "男");
        String birth = tv_birth.getText().toString();
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setPwd(pwd);
        userInfo.setAddress(SPUtil.getAddress());
        userInfo.setBirth(birth);
        userInfo.setSex(isMale);
        new RegisterTask().execute(userInfo);
    }

    public static void startForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    private class RegisterTask extends AsyncTask<UserInfo, Void, String> {

        UserInfo u;

        @Override
        protected String doInBackground(UserInfo... params) {
            UserInfo u = params[0];
            this.u = u;
            return UserInfo.register(u);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (TextUtils.isEmpty(s)) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("注册")
                        .setMessage("恭喜用户[" + u.getName() + "]注册成功!")
                        .setPositiveButton("回到登录页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("name", u.getName());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void show(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
