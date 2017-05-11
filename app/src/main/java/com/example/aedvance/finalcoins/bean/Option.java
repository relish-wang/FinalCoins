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
public class Option extends DataSupportCompat<Option> {

    private String name;

    private long questionId;

    private long oppoId;//对立Id

    private long count;

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

    public long getOppoId() {
        return oppoId;
    }

    public void setOppoId(long oppoId) {
        this.oppoId = oppoId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public static List<Option> findByQuestionId(long questionId) {
        return where("questionId = ?"
                , questionId + "").find(Option.class);
    }

    public static List<Option> findByQuestionIdGroupByOptionId(long questionId) {
        return where("questionId = ? group by id"
                , questionId + "").find(Option.class);
    }


    public static long maxId() {
        return max(Option.class, "id", Long.class);
    }
}
