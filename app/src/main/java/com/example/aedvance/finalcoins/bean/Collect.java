package com.example.aedvance.finalcoins.bean;

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
public class Collect extends DataSupportCompat<Collect> {

    private long userId;
    private long questionId;
    private long optionId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public static int getCollectCountByUserId(long userId) {
        List<Collect> collects = where("userId = ?", userId + "").find(Collect.class);
        return collects == null ? 0 : collects.size();
    }
}
