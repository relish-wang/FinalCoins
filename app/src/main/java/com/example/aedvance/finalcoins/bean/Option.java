package com.example.aedvance.finalcoins.bean;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import wang.relish.litepalcompat.DataSupportCompat;

/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Option extends DataSupportCompat<Option> {

    private String name;

    private long questionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }


    public static List<Option> findByQuestionId(long questionId) {
        Cursor cursor = DataSupportCompat
                .findBySQL("select * from option where questionId = ? group by questionId"
                        , questionId + "");
        List<Option> options = new ArrayList<>();
        if (cursor == null || cursor.getCount() == 0) {
            return options;
        }
        if (cursor.moveToFirst()) {
            do {
                Option option = new Option();
                option.setId(cursor.getLong(cursor.getColumnIndex("id")));
                option.setName(cursor.getString(cursor.getColumnIndex("name")));
                option.setQuestionId(cursor.getLong(cursor.getColumnIndex("questionId")));
                options.add(option);
            } while (cursor.moveToNext());
        }
        return options;
    }
}
