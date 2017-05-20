package com.example.aedvance.finalcoins.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.bean.UserInfo;
import com.example.aedvance.finalcoins.ui.activity.define.BaseQuestionsActivity;

import java.util.List;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CollectsActivity extends BaseQuestionsActivity {


    public static void start(Context context,UserInfo u) {
        Intent intent = new Intent(context, CollectsActivity.class);
        intent.putExtra("userInfo",u);
        context.startActivity(intent);
    }

    @Override
    protected List<Question> findQuestions() {
        return Question.findByUserIdCollected(mUser.getId());
    }

    @Override
    protected String title() {
        String title = mUser.getId()== App.USER.getId()?"我":mUser.getName();
        return title+"的收藏";
    }
}
