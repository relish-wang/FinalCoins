package com.example.aedvance.finalcoins.base;

import android.app.Activity;

/**
 * View 基础类
 * Created by Relish on 2016/11/4.
 */
public interface BaseView {

    void showMessage(Object msg);

    void showLoading(boolean isShown);

    Activity getActivity();

    void goBrowser(String url);
}
