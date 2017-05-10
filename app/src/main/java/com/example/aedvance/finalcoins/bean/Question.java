package com.example.aedvance.finalcoins.bean;

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
public class Question extends DataSupportCompat<Question> {

    private long userId;

    @PrimaryKey
    private String title;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Question getMostPopularFor(boolean isMale) {
        List<Option> options;

//        "select * from user where sex = ?";//男、女
        findBySQL("select * from question q, userinfo u where q.userId = u.id and u.sex = ?", isMale ? "0" : "1");//

        return null;
    }

    public static int getQuestionCountById(long userId) {
        List<Question> questions = where("userId = ?", userId + "").find(Question.class);
        return questions == null ? 0 : questions.size();
    }

    public static Question findByTitle(String title) {
        List<Question> questions = where("title = ?", title).find(Question.class);
        return questions == null ? null : questions.size() == 0 ? null : questions.get(0);
    }
}
