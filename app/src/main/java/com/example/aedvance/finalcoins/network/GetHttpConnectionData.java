package com.example.aedvance.finalcoins.network;

import com.example.aedvance.finalcoins.util.OkHttpInvoker;

import java.io.IOException;

/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/8
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetHttpConnectionData {
    public static String getData(String url) {
        try {
            return OkHttpInvoker.getResult(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
