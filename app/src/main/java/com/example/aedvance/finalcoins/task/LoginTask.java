package com.example.aedvance.finalcoins.task;

import android.os.AsyncTask;

import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.util.EasyCallback;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginTask extends AsyncTask<String, Void, UserInfo> {

    EasyCallback<Void> callback;

    public LoginTask(EasyCallback<Void> callback) {
        this.callback = callback;
    }

    @Override
    protected UserInfo doInBackground(String... params) {
        String name = params[0];
        String pwd = params[1];
        return UserInfo.login(name,pwd);
    }

    @Override
    protected void onPostExecute(UserInfo userInfo) {
        super.onPostExecute(userInfo);
        if(userInfo==null){
            callback.onFailed("用户名或密码不存在");
        }else{
            callback.onSuccess(null);
        }
    }
}
