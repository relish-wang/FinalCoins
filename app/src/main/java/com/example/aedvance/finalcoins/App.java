package com.example.aedvance.finalcoins;

import android.app.Application;
import android.util.Log;

import com.example.aedvance.finalcoins.bean.UserInfo;

import org.litepal.LitePal;

/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class App extends Application {

    public static App CONTEXT;

    public static UserInfo USER;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
        LitePal.initialize(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e("App", e.getMessage());
            }
        });
    }
}
