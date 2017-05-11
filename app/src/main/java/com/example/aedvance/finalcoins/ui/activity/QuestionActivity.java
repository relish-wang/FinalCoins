package com.example.aedvance.finalcoins.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aedvance.finalcoins.App;
import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.base.BaseActivity;
import com.example.aedvance.finalcoins.bean.Answer;
import com.example.aedvance.finalcoins.bean.Follow;
import com.example.aedvance.finalcoins.bean.Option;
import com.example.aedvance.finalcoins.bean.Question;
import com.example.aedvance.finalcoins.ui.view.RippleButtonView;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class QuestionActivity extends BaseActivity {


    @Override
    protected int layoutId() {
        return R.layout.activity_question;
    }

    Question mQuestion;

    @Override
    protected void parseIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        mQuestion = (Question) bundle.getSerializable("question");
        if (mQuestion == null) finish();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState, Toolbar mToolbar) {
        mToolbar.setTitle(mQuestion.getTitle());
    }

    TextView tv_title, tv_user_name, tv_option1_name, tv_option2_name, tv_option1_rate, tv_option2_rate;
    RippleButtonView btn_interest;
    boolean isFollowing = false;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_option1_name = (TextView) findViewById(R.id.tv_option1_name);
        tv_option2_name = (TextView) findViewById(R.id.tv_option2_name);
        tv_option1_rate = (TextView) findViewById(R.id.tv_option1_rate);
        tv_option2_rate = (TextView) findViewById(R.id.tv_option2_rate);

        btn_interest = (RippleButtonView) findViewById(R.id.btn_interest);

        tv_title.setText(mQuestion.getTitle());
        tv_user_name.setText(mQuestion.getPublisherName());

        isFollowing = Follow.amIFollowing(mQuestion.getUserId());
        btn_interest.setFollowed(isFollowing, true);
        btn_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowing) {
                    Follow.unFollow(mQuestion.getUserId());
                } else {
                    Follow follow = new Follow();
                    follow.setMasterId(mQuestion.getUserId());
                    follow.setFollowerId(App.USER.getId());
                    follow.save();
                }
            }
        });

        List<Option> options = Option.findByQuestionId(mQuestion.getId());
        if (options == null || options.size() != 2) return;
        Option option1 = options.get(0);
        Option option2 = options.get(1);
        long count1 = option1.getCount();
        long count2 = option2.getCount();
        tv_option1_name.setText(option1.getName());
        tv_option2_name.setText(option2.getName());
        Answer answer = Answer.findAnswerByUserIdAndQuestionId(App.USER.getId(), mQuestion.getId());
        if (answer == null) {
            tv_option1_rate.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            tv_option2_rate.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            tv_option1_rate.setVisibility(View.INVISIBLE);
            tv_option2_rate.setVisibility(View.INVISIBLE);
            tv_option1_name.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tv_option2_name.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            return;
        }
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tv_option1_rate.getLayoutParams();
        params1.width = 0;
        params1.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params1.weight = count1;
        tv_option1_rate.setLayoutParams(params1);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) tv_option2_rate.getLayoutParams();
        params2.width = 0;
        params2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params2.weight = count2;
        tv_option2_rate.setLayoutParams(params2);
        tv_option1_rate.setText(String.format(Locale.CHINA, "%.1f%%", count1 * 100f / (count1 * 1.0 + count2 * 1.0)));
        tv_option2_rate.setText(String.format(Locale.CHINA, "%.1f%%", count2 * 100f / (count1 * 1.0 + count2 * 1.0)));
    }

    public static void start(Context context, Question question) {
        Intent intent = new Intent(context, QuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", question);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
