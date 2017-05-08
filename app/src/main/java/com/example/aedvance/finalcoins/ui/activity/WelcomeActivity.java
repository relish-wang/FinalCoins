package com.example.aedvance.finalcoins.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.task.LoginTask;
import com.example.aedvance.finalcoins.util.EasyCallback;
import com.example.aedvance.finalcoins.util.SPUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WelcomeActivity extends AppCompatActivity implements BDLocationListener {


    public LocationClient mLocationClient;
    TextView tv_position;

    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(this);
        setContentView(R.layout.activity_welcome);

        tv_position = (TextView) findViewById(R.id.tv_position);

        List<String> permissionList = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            String[] ps = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, ps, 1);
        } else {
            requestLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        final StringBuilder sb = new StringBuilder();
        sb.append(bdLocation.getProvince()).append(" ")
                .append(bdLocation.getCity()).append(" ")
                .append(bdLocation.getDistrict()).append(" ")
                .append(bdLocation.getStreet());
        final StringBuilder currentPosition = new StringBuilder();
        currentPosition
                .append("纬度：").append(bdLocation.getLatitude()).append("\n")
                .append("经度：").append(bdLocation.getLongitude()).append("\n")
                .append("国家：").append(bdLocation.getCountry()).append("\n")
                .append("省：").append(bdLocation.getProvince()).append("\n")
                .append("市：").append(bdLocation.getCity()).append("\n")
                .append("区：").append(bdLocation.getDistrict()).append("\n")
                .append("街道：").append(bdLocation.getStreet()).append("\n")
                .append("定位方式：");
        if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            currentPosition.append("网络");
        } else {
            currentPosition.append("GPS");
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SPUtil.setAddress(sb.toString());
                tv_position.setText(currentPosition.toString());
                go2MainOrLogin();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误！！！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void go2MainOrLogin() {
        if (SPUtil.isAutoLogin()) {
            new LoginTask(new EasyCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    MainActivity.start(WelcomeActivity.this);
                    finish();
                }

                @Override
                public void onFailed(String message) {
                    Toast.makeText(WelcomeActivity.this, message, Toast.LENGTH_SHORT).show();
                    LoginActivity.start(WelcomeActivity.this);
                    finish();
                }
            }).execute(SPUtil.getName(), SPUtil.getPwd());
        } else {
            LoginActivity.start(WelcomeActivity.this);
            finish();
        }
    }
}