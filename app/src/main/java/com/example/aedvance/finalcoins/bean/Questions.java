package com.example.aedvance.finalcoins.bean;

/**
 * Created by aedvance on 2017/5/7.
 */
@Deprecated
public class Questions {


    private String question;
    private String author;
    private boolean isLove;
    private boolean option;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }
}
