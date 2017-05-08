package com.example.aedvance.finalcoins.util;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface EasyCallback<T> {

    void onSuccess(T t);

    void onFailed(String message);
}
