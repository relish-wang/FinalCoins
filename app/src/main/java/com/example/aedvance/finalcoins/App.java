package com.example.aedvance.finalcoins;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.example.aedvance.finalcoins.bean.UserInfo;

import org.litepal.LitePal;

import java.util.WeakHashMap;

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
    private static WeakHashMap<String, Activity> mActivities = new WeakHashMap<>();

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


    /**
     * Activity入栈
     *
     * @param activity activity
     */
    public static synchronized void addActivity(Activity activity) {
        mActivities.put(activity.getClass().getName(), activity);
    }

    /**
     * Activity出栈
     *
     * @param activityNames activity名
     */
    public static synchronized void removeActivities(String... activityNames) {
        for (String activityClassName : activityNames) {
            Activity activity = mActivities.get(activityClassName);
            if (activity != null) {
                activity.finish();
            }
            mActivities.remove(activityClassName);
        }
    }

    /**
     * 退出Activity
     */
    public static void exitApp() {
        Object[] allActivityNames = mActivities.keySet().toArray();
        String[] names = new String[allActivityNames.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = allActivityNames[i].toString();
        }
        removeActivities(names);
    }

}
