package com.example.aedvance.finalcoins.bean;

import android.database.Cursor;
import android.text.TextUtils;

import com.example.aedvance.finalcoins.util.SPUtil;

import org.litepal.crud.ClusterQuery;

import java.util.List;

import wang.relish.litepalcompat.DataSupportCompat;
import wang.relish.litepalcompat.PrimaryKey;

/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UserInfo extends DataSupportCompat<UserInfo> {

    @PrimaryKey
    private String name;
    private String pwd;

    private boolean sex;//0:男，1：女
    private String mail;
    private String address;
    private String birth;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getMail() {
        if (TextUtils.isEmpty(mail)) {
            return "未设置";
        }
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        if (TextUtils.isEmpty(birth)) {
            return "未设置";
        }
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public static UserInfo login(String name, String pwd) {
        Cursor cursor = findBySQL("select * from userinfo where name = ? and pwd = ?", name, pwd);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            if (cursor.moveToFirst()) {
                UserInfo u = new UserInfo();
                u.setId(cursor.getLong(cursor.getColumnIndex("id")));
                u.setName(cursor.getString(cursor.getColumnIndex("name")));
                u.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
                u.setSex(cursor.getInt(cursor.getColumnIndex("sex")) == 0);
                u.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                u.setMail(cursor.getString(cursor.getColumnIndex("mail")));
                u.setBirth(cursor.getString(cursor.getColumnIndex("birth")));
                cursor.close();
                SPUtil.saveUser(u);
                SPUtil.setAutoLogin(true);
                return u;
            }
        }
        return null;
    }

    public static String register(UserInfo u) {
        if (isExist(u.getName())) {
            return "用户已注册";
        } else {
            boolean success = u.save();
            return success ? "" : "注册失败";
        }
    }

    public static boolean isExist(String name) {
        ClusterQuery clusterQuery = where("name = ?", name);
        List<UserInfo> userInfos = clusterQuery.find(UserInfo.class);
        return userInfos != null && userInfos.size() > 0;
    }

    public static UserInfo findByUserId(long userId) {
        return UserInfo.find(UserInfo.class, userId);
    }
}
