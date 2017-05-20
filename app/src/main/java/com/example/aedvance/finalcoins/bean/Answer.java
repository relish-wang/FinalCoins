package com.example.aedvance.finalcoins.bean;

import com.example.aedvance.finalcoins.App;

import org.litepal.crud.async.SaveExecutor;

import java.util.List;

import wang.relish.litepalcompat.DataSupportCompat;

/**
 * <pre>
 *     author : aedvance
 *     e-mail : 740847193@qq.com
 *     time   : 2017/5/7
 *     desc   : 回答表
 *     version: 1.0
 * </pre>
 */
public class Answer extends DataSupportCompat<Answer> {

    /**
     * 收藏者
     */
    private long userId;
    private long questionId;
    private long optionId;
    private boolean isCollected;

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

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public static List<Answer> getCollectByCurrentUserId() {
        List<Answer> answers = where("userId = ? and isCollected = ?", App.USER.getId() + "", "1").find(Answer.class);
        return answers;
    }

    public static List<Answer> getCollectByUserId(long id) {
        List<Answer> answers = where("userId = ? and isCollected = ?", id + "", "1").find(Answer.class);
        return answers;
    }

    public static int getCollectCountByUserId(long userId) {
        List<Answer> answers = where("userId = ? and isCollected = ?", userId + "", "1").find(Answer.class);
        return answers == null ? 0 : answers.size();
    }

    public static Answer findAnswerByUserIdAndQuestionId(long userId, long questionId) {
        List<Answer> answers = where("userId = ? and questionId = ?", userId + "", questionId + "").find(Answer.class);
        return (answers == null || answers.size() == 0) ? null : answers.get(0);
    }

    @Override
    public synchronized boolean save() {
        return super.save();
    }

    @Override
    public SaveExecutor saveAsync() {
        return super.saveAsync();
    }
}
